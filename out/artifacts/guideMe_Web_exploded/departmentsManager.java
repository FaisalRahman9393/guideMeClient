package guideMeServer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
/**
 * - Before deploying the server, make sure Email Service is On inside the RegisterServlet
 * - The admin username and password: -u admin, -p guidemeisawesome
 */

/**
 * This is the servlet for the login/ index process
 */
@WebServlet("/Departments")
public class departmentsManager extends HttpServlet {
    private static final long serialVersionUID = 1L;
    Connection con;
    Statement st = null;
    ResultSet rs;
    String email;
    String selectedDepart;

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String buttonPressed	=	request.getParameter("pagename");

        if(buttonPressed.equals("departmentsFormAddNew")){
            String name	=	request.getParameter("departmentName");
            String location	=	request.getParameter("departmentLocation");
            String faculty	=	request.getParameter("departmentFaculty");
            String info	=	request.getParameter("departmentInfo");

            mySqlINIT();
            try {
                Statement statement = con.createStatement ();
                statement.executeUpdate("INSERT  INTO departments (Name, Location, Faculty, Information) " +
                        "VALUES ('"+name+"', '"+location+"', '"+faculty+"', '"+info+"');");
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        if(buttonPressed.equals("departmentsFormEditPressed")){
            /**
             * A list will be created to send to the jsp file
             */
            ArrayList<String> list = new ArrayList<>();

            try {
                mySqlINIT();
                rs = mySQLGetter("Departments");
                while (rs.next()) {
                    String name = rs.getString("name");
                    list.add(name);
                    System.out.println(list.size());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            /**
             * This list will then be passed to the servlet for drop down menu
             */

            request.setAttribute("list",list);
            RequestDispatcher rd=request.getRequestDispatcher("adminDepartmentsEditCurrentPre.jsp");
            rd.forward(request,response);

        }

        if(buttonPressed.equals("departmentsFormAddNew")){
            String name	=	request.getParameter("departmentName");
            String location	=	request.getParameter("departmentLocation");
            String faculty	=	request.getParameter("departmentFaculty");
            String info	=	request.getParameter("departmentInfo");

            mySqlINIT();
            try {
                Statement statement = con.createStatement ();
                statement.executeUpdate("INSERT  INTO departments (Name, Location, Faculty, Information) " +
                        "VALUES ('"+name+"', '"+location+"', '"+faculty+"', '"+info+"');");
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        if(buttonPressed.equals("chosenDepartment")){
            selectedDepart  = request.getParameter("selectedDepart");


            ResultSet departmentRec;
            try {
                Statement statement = con.createStatement ();
                departmentRec = statement.executeQuery("SELECT * FROM Departments WHERE Name = '"+selectedDepart+"';");
                if(departmentRec.next()) {
                    String nameOfDep = departmentRec.getString("Name");
                    String locationOfDep = departmentRec.getString("Location");
                    String facultyOfDep = departmentRec.getString("Faculty");
                    String infoOfDep = departmentRec.getString("Information");

                    request.setAttribute("nameOfDep",nameOfDep);
                    request.setAttribute("locationOfDep",locationOfDep);
                    request.setAttribute("facultyOfDep",facultyOfDep);
                    request.setAttribute("infoOfDep",infoOfDep);
                    RequestDispatcher rd=request.getRequestDispatcher("adminDepartmentsEditCurrent.jsp");
                    rd.forward(request,response);
                    //response.sendRedirect("authenticated.jsp");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(buttonPressed.equals("departmentsFormEditUpdate")){
            String locationofDEP	=	request.getParameter("departmentLocation");
            String facOfDept	=	request.getParameter("departmentFac");
            String descr	=	request.getParameter("departmentInfo");
            try {
                Statement statement = con.createStatement ();
                statement.executeUpdate("UPDATE Departments SET " +
                        "Faculty = '"+facOfDept+"', " +
                        "Location = '"+locationofDEP+"', " +
                        "Information = '"+descr+"' WHERE Name = '"+selectedDepart+"';");
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * @see HttpServlet#HttpServlet()
     */
    public departmentsManager() {
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
        Statement thisST = null;
        thisRS = thisST.executeQuery("SELECT * FROM "+tableNamethis+" WHERE Email = '"+Email+"';");
        return thisRS;
    }



}