package guideMeServer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
/**
 * - Before deploying the server, make sure Email Service is On inside the RegisterServlet
 * - The admin username and password: -u admin, -p guidemeisawesome
 */

/**
 * This is the servlet for the login/ index process
 */
@WebServlet("/Facilities")
public class facilitiesManager extends HttpServlet {
    private static final long serialVersionUID = 1L;
    Connection con;
    Statement st = null;
    ResultSet rs;
    String email;
    String selectedFacility;

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String buttonPressed	=	request.getParameter("pagename");

        if(buttonPressed.equals("facilitiesFormAddNew")){
            String name	=	request.getParameter("facilityName");
            String location	=	request.getParameter("facilityLocation");
            String openingHours	=	request.getParameter("facilityOpeningHours");
            String info	=	request.getParameter("facilityInfo");

            mySqlINIT();
            try {
                Statement statement = con.createStatement ();
                statement.executeUpdate("INSERT  INTO facilities (Name, Location, OpeningHours, Information) " +
                        "VALUES ('"+name+"', '"+location+"', '"+openingHours+"', '"+info+"');");
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }



        if(buttonPressed.equals("facilitiesFormEditPressed")){
            /**
             * A list will be created to send to the jsp file
             */
            ArrayList<String> list = new ArrayList<>();

            try {
                mySqlINIT();
                rs = mySQLGetter("facilities");
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
            RequestDispatcher rd=request.getRequestDispatcher("adminFacilitiesEditCurrentPre.jsp");
            rd.forward(request,response);

        }


        if(buttonPressed.equals("chosenFacility")){
            selectedFacility  = request.getParameter("selectedFac");

            ResultSet facilityRec;
            try {
                Statement statement = con.createStatement ();
                facilityRec = statement.executeQuery("SELECT * FROM Facilities WHERE Name = '"+selectedFacility+"';");
                if(facilityRec.next()) {
                    String nameOfFac = facilityRec.getString("Name");
                    String locationOfFac = facilityRec.getString("Location");
                    String hoursOfFac = facilityRec.getString("OpeningHours");
                    String infoOfFac = facilityRec.getString("Information");

                    request.setAttribute("nameOfFacility",nameOfFac);
                    request.setAttribute("locationOfFacility",locationOfFac);
                    request.setAttribute("hoursOfFacility",hoursOfFac);
                    request.setAttribute("infoOfFacility",infoOfFac);
                    RequestDispatcher rd=request.getRequestDispatcher("adminFacilitiesEditCurrent.jsp");
                    rd.forward(request,response);
                    //response.sendRedirect("authenticated.jsp");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(buttonPressed.equals("facilitiesFormEditUpdate")){
            String locationofFac	=	request.getParameter("facilitiesLocation");
            String hoursOfFac	=	request.getParameter("facilitiesHours");
            String descr	=	request.getParameter("facilitiesInfo");
            try {
                Statement statement = con.createStatement ();
                statement.executeUpdate("UPDATE Facilities SET " +
                        "OpeningHours = '"+hoursOfFac+"', " +
                        "Location = '"+locationofFac+"', " +
                        "Information = '"+descr+"' WHERE Name = '"+selectedFacility+"';");
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * @see HttpServlet#HttpServlet()
     */
    public facilitiesManager() {
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