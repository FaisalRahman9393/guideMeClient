package guideMe;

/**
 * Created by Faze on 07/01/2017.
 */
public class guideMeDB {
    public static void main(String[] args) {

        try {
            DBConnection guideMeConnector = new DBConnection();
            guideMeConnector.getData();
        }catch(Exception ex) {
            //Already handled.
        }
    }

}
