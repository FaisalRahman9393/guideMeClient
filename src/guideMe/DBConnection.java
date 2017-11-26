package guideMe;
import java.sql.*;
/**
 * Created by Faze on 10/01/2017.
 * For making the initial connection to the mySQL database
 */
public class DBConnection {
    private Connection con;
    public java.sql.Statement st;
    public ResultSet rs;
    public DBConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/guideme", "root", "");
            st = con.createStatement();
        }catch(Exception ex) {
            System.out.println("Error while trying to make a connection to the database");
        }
    }
    public void getData(){
        try {
            String query = "SELECT * FROM Departments;";
            rs = st.executeQuery(query);

            while (rs.next()){
                String name= rs.getString("Name");
                System.out.println(name);
            }
        }catch(Exception ex) {
            System.out.println("Error while trying to get data from the database");
        }
    }
}
