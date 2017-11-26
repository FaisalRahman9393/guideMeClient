package guideMeServer;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.concurrent.ThreadLocalRandom;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This is the servlet for the registration process
 */
@WebServlet("/RegMe")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    Connection con;
    java.sql.Statement st = null;
    ResultSet rs;
    String emailEntered = null;
    int ranNum;

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String buttonPressed	=	request.getParameter("pagename");

        /**
         * This code is executed the registration button is pressed....
         */
        if(buttonPressed.equals("regmail")) {
            emailEntered	=	request.getParameter("txtUserName");
            mySqlINIT();
            try {
                /**
                 * Check if the user is a lecturer by comparing the email entered
                 * with the emails on the database
                 */
                rs = mySQLGetter("Academics");
                while (rs.next()) {
                    String name = rs.getString("Email");
                    if (name.equals(emailEntered)){
                        /**
                         * If the email address is the same, send the lecutrer a six dig. pin through email
                         */
                        ranNum = ThreadLocalRandom.current().nextInt(111111,999999);
                        System.out.println("Pin generated:  "+ranNum);

                        EmailService m = new EmailService(emailEntered,"Thank you for registering with Guide Me.\n" +
                                "Your secret pin is: "+ranNum+".\n" +
                                "Please enter this number in the web form to complete the registration process\n\n" +
                                "Thank you.");

                        /**
                         * Now send to next stage of registration
                         */
                        response.sendRedirect("registrationFinal.jsp");
                        return;
                    }
                }
                response.sendRedirect("wrongPassword.jsp");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        /**
         * Final stage of the registration process
         *
         * Here, the lecturer enters the pin received in email, followed by a password
         */
        if(buttonPressed.equals("regreg")){
            StringBuilder hexString = null;
            //Remove any spaces from the input
            emailEntered = emailEntered.replaceAll("\\s","");
            String pin = Integer.toString(ranNum);
            String pinEntered	=	request.getParameter("secPin");
            String passwordEntered	=	request.getParameter("password");
            System.out.println("Pin entered:  " + pinEntered);
            if (pin.equals(pinEntered)){

                try {
                    MessageDigest digest = MessageDigest.getInstance("SHA-256");
                    byte[] hash = digest.digest(passwordEntered.getBytes("UTF-8"));

                    hexString = new StringBuilder();
                    for (int i: hash) {
                        hexString.append(Integer.toHexString(0XFF & i));
                    }
                    System.out.println(hexString);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

                /**
                 * If new...
                 */
                //f.rahman@lancaster.ac.uk
                //password:hello
                try {
                    boolean aNewUserIsHere = true;

                    ResultSet checkPass = mySQLGetter("users");
                    while (checkPass.next()) {
                        String email = checkPass.getString("Email");
                        /**
                         * If an email is already registered, we simply change the password
                         */
                        if (email.equals(emailEntered)){
                            Statement st = con.createStatement();
                            st.executeUpdate("UPDATE users SET Password= '"+hexString+"' WHERE Email='"+emailEntered+"';");
                            aNewUserIsHere=false; //No new user is found :(
                            break;
                        }
                    }
                    /**
                     * If an email has not been registered before, we add a new entry
                     */
                    if (aNewUserIsHere) {
                        Statement st = con.createStatement();
                        st.executeUpdate("INSERT INTO users ( Email, Password) VALUES ( '"+emailEntered+"','"+hexString+"');");
                    }
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                response.sendRedirect("rightPin.jsp");
            }
            else {response.sendRedirect("wrongPin.jsp");}


        }
    }

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    /**
     * A function that makes the initial connection to the database
     */
    public void mySqlINIT (){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/guideme", "root", "");
            st = con.createStatement();
        }catch(Exception e) {
            System.out.println("Error while trying to make a connection to the database");
        }
    }

    /**
     * A function that queries the mySQL database
     * @return
     */
    public ResultSet mySQLGetter (String tableName) throws Exception{
        String query = "SELECT * FROM "+tableName+";";
        rs = st.executeQuery(query);
        return rs;
    }

}