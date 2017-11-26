package guideMeServer;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * - Before deploying the server, make sure Email Service is On inside the RegisterServlet
 * - The admin username and password: -u admin, -p guidemeisawesome
 */
/**
 * This is the servlet for the login/ index process
 */
@WebServlet("/LogIn")
public class LogInServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    Connection con;
    java.sql.Statement st = null;
    ResultSet rs;
    String email;

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String buttonPressed	=	request.getParameter("pagename");
         StringBuilder hexString = null;

        //f.rahman@lancaster.ac.uk
        //password:hello
        //Once it is fixed: http://stackoverflow.com/questions/13331478/matching-java-sha2-output-vs-mysql-sha2-output

        /**
         * If log in is selected then execute the following...
         */
        if(buttonPressed.equals("login")){

            /**
             * Hashing the password for caparison later...
             */
            mySqlINIT();
            String userName	=	request.getParameter("txtUserName");
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hash = digest.digest(request.getParameter("txtPassword").getBytes("UTF-8"));

                hexString = new StringBuilder();
                for (int i: hash) {
                    hexString.append(Integer.toHexString(0XFF & i));
                }
                System.out.println(hexString);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            try {
                /**
                 * Check if Email is correct
                 */
                rs = mySQLGetter("users");
                while (rs.next()) {
                    email = rs.getString("Email");
                    if (email.equals(userName)){
                        /**
                         * Check if password is correct
                         */
                        String pass = rs.getString("Password");
                        //System.out.println(pass);
                        if (hexString.toString().equals(pass.toString())){

                            /**
                             * User is authenticated, getting information about the user from the database
                             */

                            //Quick admin check - Checks if the user is admin if so, the page is redirected.
                            if (userName.equals("admin")){
                                response.sendRedirect("admin.jsp");
                                break;//Break out of this as user is admin and not a lecturer.
                            }


                            ResultSet currentUser;
                            Statement statement = con.createStatement ();
                            currentUser = statement.executeQuery("SELECT * FROM Academics WHERE Email = '"+email+"';");

                            if(currentUser.next()) {
                                String nameOfAcademic = currentUser.getString("Name");
                                System.out.println(nameOfAcademic);
                                String roleOfAcademic = currentUser.getString("Role");
                                String departmentOfAcademic = currentUser.getString("Department");
                                String emailOfAcademic = currentUser.getString("Email");
                                String officeOfAcademic = currentUser.getString("Office");
                                String numberOfAcademic = currentUser.getString("Number");
                                String availabilityOfAcademic = currentUser.getString("Availability");

                                request.setAttribute("nameOfAcademic",nameOfAcademic);
                                request.setAttribute("roleOfAcademic",roleOfAcademic);
                                request.setAttribute("departmentOfAcademic",departmentOfAcademic);
                                request.setAttribute("emailOfAcademic",emailOfAcademic);
                                request.setAttribute("officeOfAcademic",officeOfAcademic);
                                request.setAttribute("numberOfAcademic",numberOfAcademic);
                                request.setAttribute("availabilityOfAcademic",availabilityOfAcademic);
                                RequestDispatcher rd=request.getRequestDispatcher("authenticated.jsp");
                                rd.forward(request,response);
                                //response.sendRedirect("authenticated.jsp");
                                break;
                            }
                            break;

                        }

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogInServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
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
    /**
     * A function that queries the mySQL database
     * @return
     */
    public ResultSet mySQLGetter2 (String tableNamethis, String Email) throws Exception{
        ResultSet thisRS;
        java.sql.Statement thisST = null;
        thisRS = thisST.executeQuery("SELECT * FROM "+tableNamethis+" WHERE Email = '"+Email+"';");
        return thisRS;
    }

}