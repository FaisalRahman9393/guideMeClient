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
@WebServlet("/Shops")
public class shopsManager extends HttpServlet {
    private static final long serialVersionUID = 1L;
    Connection con;
    Statement st = null;
    ResultSet rs;
    String email;
    String selectedShop;

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String buttonPressed	=	request.getParameter("pagename");

        if(buttonPressed.equals("shopFormAddNew")){
            String name	=	request.getParameter("shopName");
            String location	=	request.getParameter("shopLocation");
            String openingHours	=	request.getParameter("shopOpeningHours");
            String info	=	request.getParameter("shopInfo");

            mySqlINIT();
            try {
                Statement statement = con.createStatement ();
                statement.executeUpdate("INSERT  INTO Shops (Name, Location, OpeningHours, Information) " +
                        "VALUES ('"+name+"', '"+location+"', '"+openingHours+"', '"+info+"');");
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(buttonPressed.equals("shopsFormEditPressed")){
            /**
             * A list will be created to send to the jsp file
             */
            ArrayList<String> list = new ArrayList<>();

            try {
                mySqlINIT();
                rs = mySQLGetter("shops");
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
            RequestDispatcher rd=request.getRequestDispatcher("adminShopsEditCurrentPre.jsp");
            rd.forward(request,response);

        }


        if(buttonPressed.equals("chosenShop")){
            selectedShop  = request.getParameter("selectedShop");

            ResultSet shopRec;
            try {
                Statement statement = con.createStatement ();
                shopRec = statement.executeQuery("SELECT * FROM Shops WHERE Name = '"+selectedShop+"';");
                if(shopRec.next()) {
                    String nameOfShop = shopRec.getString("Name");
                    String locationOfShop = shopRec.getString("Location");
                    String hoursOfShop = shopRec.getString("OpeningHours");
                    String infoOfShop = shopRec.getString("Information");

                    request.setAttribute("nameOfShop",nameOfShop);
                    request.setAttribute("locationOfShop",locationOfShop);
                    request.setAttribute("hoursOfShop",hoursOfShop);
                    request.setAttribute("infoOfShop",infoOfShop);
                    RequestDispatcher rd=request.getRequestDispatcher("adminShopsEditCurrent.jsp");
                    rd.forward(request,response);
                    //response.sendRedirect("authenticated.jsp");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(buttonPressed.equals("shopFormEditUpdate")){
            System.out.println(selectedShop);
            String locationofShop	=	request.getParameter("shopLocation");
            String hoursOfShop	=	request.getParameter("shopOpeningHours");
            String descr	=	request.getParameter("shopInfo");
            try {
                Statement statement = con.createStatement ();
                statement.executeUpdate("UPDATE Shops SET " +
                        "OpeningHours = '"+hoursOfShop+"', " +
                        "Location = '"+locationofShop+"', " +
                        "Information = '"+descr+"' WHERE Name = '"+selectedShop+"';");
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @see HttpServlet#HttpServlet()
     */
    public shopsManager() {
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