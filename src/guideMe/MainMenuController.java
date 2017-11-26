package guideMe;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

public class MainMenuController implements Initializable , SceneInt {

    public static String departmentsID = "Departments";
    private static String departmentsFXML = "./Departments/Departments.fxml";
    public static String lecturersID = "Lecturers";
    private static String lecturersFXML = "./Lecturers/Lecturers.fxml";
    public static String shopsID = "Shops";
    private static String shopsFXML = "./Shops/Shops.fxml";
    public static String facilitiesID = "Facilities";
    private static String facilitiesFXML = "./Facilities/Facilities.fxml";

    GuideMeSceneContainer myContainer;
    @FXML private ChoiceBox currentDepartment;
    public static String thisDepartment;
    DBConnection database = new DBConnection(); //Creating a database connection

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Get the list of departments and set it to the drop down box
        currentDepartment.setItems(departmentsList);
        //Get default department
        currentDepartment.getSelectionModel().select(3);
        //Set default department to the department of point A
        set(currentDepartment.getValue().toString());

        /**
         * When a new department is selected the default department is chnaged.
         */
        currentDepartment.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<String>() {
                    public void changed(ObservableValue<? extends String> observable,
                                        String departmentBefore, String departmentAfter) {

                        //TODO CODE FOR depart location
                        set(departmentAfter);
                    }
                });
    }

    public void setScreenParent(GuideMeSceneContainer s){
        myContainer = s;
    }

    @FXML
    private void lockScreen(ActionEvent event){
        myContainer.applyScene(GuideMeLauncher.lockScreenID);
    }


    @FXML
    private void goToLecturers(ActionEvent event){
        myContainer.loadScene(MainMenuController.lecturersID, MainMenuController.lecturersFXML);
        myContainer.applyScene(MainMenuController.lecturersID);
    }

    @FXML
    private void goToDepartments(ActionEvent event){
        myContainer.loadScene(MainMenuController.departmentsID, MainMenuController.departmentsFXML);
        myContainer.applyScene(MainMenuController.departmentsID);
    }

    @FXML
    private void goToShops(ActionEvent event){
        myContainer.loadScene(MainMenuController.shopsID, MainMenuController.shopsFXML);
        myContainer.applyScene(MainMenuController.shopsID);
    }

    @FXML
    private void goToFacilities(ActionEvent event){
        myContainer.loadScene(MainMenuController.facilitiesID, MainMenuController.facilitiesFXML);
        myContainer.applyScene(MainMenuController.facilitiesID);
    }


    public static String pointAGetter(String whatDepartment){
        String x = whatDepartment;
        return x;
    }
    public String get() {
        return thisDepartment;
    }

    public void set(String thisDpar) {

        try {
            database.rs = database.st.executeQuery("SELECT Location FROM Departments WHERE departments.name = '"+thisDpar+"' ;");
            if(database.rs.next()) {
                String location = database.rs.getString("Location");
                System.out.println(location);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        thisDepartment = thisDpar;
    }
    /**
     * This is a list of all departments at lancaster university and is used by the drop down box.
     * The box is then used to determine the point A of directions.
     */
    ObservableList <String> departmentsList = FXCollections.observableArrayList("Accounting and Finance",
            "Biomedical and Life Sciences", "Chemistry", "Computing and communications",
            "Economics", "Engineering", "English and Creative writing", "Entrepreneurship, strategy & innovation",
            "Faculty of Health and Medicine", "Health Research", "History",
            "Institute for the contemporary arts", "Lancaster Environment Centre", "Lancaster Medical School",
            "Languages and culture", "Law School", "Leadership and Management", "Linguistics and English Language", "Management Science",
            "Marketing", "Mathematics and statics", "Physics", "Politics, philosophy and religion",
            "Psychology", "Sociology"
    );
}