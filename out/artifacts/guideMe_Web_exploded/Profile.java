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
@WebServlet("/Profile")
public class Profile extends HttpServlet {
    private static final long serialVersionUID = 1L;
    Connection con;
    java.sql.Statement st = null;
    ResultSet rs;
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String buttonPressed	=	request.getParameter("pagename");

        if(buttonPressed.equals("profileForm")) {
            //Get the inputs from the jsp page for changing information about the lecturer
            String nameOfAcademic	=	request.getParameter("academicName");
            String departmentOfAcademic	=	request.getParameter("academicDepartment");
            String roleOfAcademic	=	request.getParameter("academicRole");
            String officeOfAcademic	=	request.getParameter("academicOffice");
            String numberOfAcademic	=	request.getParameter("academicNumber");
            String availabilityOfAcademic	=	request.getParameter("academicAvailability");
            String emailOfAcadmic	=	request.getParameter("academicEmail");

            System.out.println(officeOfAcademic+"\n"+emailOfAcadmic);

            mySqlINIT();
            try {
                Statement statement = con.createStatement ();
                statement.executeUpdate("UPDATE Academics SET " +
                        "Name = '"+nameOfAcademic+"', " +
                        "Role = '"+roleOfAcademic+"', " +
                        "Department = '"+departmentOfAcademic+"', " +
                        "Office = '"+officeOfAcademic+"', " +
                        "Number = '"+numberOfAcademic+"', " +
                        "Availability = '"+availabilityOfAcademic+"' " +
                        "WHERE Email = '"+emailOfAcadmic+"';");
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            response.sendRedirect("rightPin.jsp");
        }

        if(buttonPressed.equals("adminForm")) {
            //Get the inputs from the jsp page for changing information about the lecturer
            String nameOfAcademic	=	request.getParameter("academicName");
            String departmentOfAcademic	=	request.getParameter("academicDepartment");
            String roleOfAcademic	=	request.getParameter("academicRole");
            String officeOfAcademic	=	request.getParameter("academicOffice");
            String numberOfAcademic	=	request.getParameter("academicNumber");
            String availabilityOfAcademic	=	request.getParameter("academicAvailability");
            String emailOfAcadmic	=	request.getParameter("academicEmail");

            System.out.println(officeOfAcademic+"\n"+emailOfAcadmic);

            mySqlINIT();
            try {
                Statement statement = con.createStatement ();
                statement.executeUpdate("INSERT  INTO Academics (Name, Role, Department, Email, Office, Number, Availability) " +
                        "VALUES ('"+nameOfAcademic+"', '"+roleOfAcademic+"', '"+departmentOfAcademic+"', '"+emailOfAcadmic+"', '"+officeOfAcademic+"', '"+numberOfAcademic+"', '"+availabilityOfAcademic+"');");
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            response.sendRedirect("rightPin.jsp");
        }
    }

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Profile() {
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