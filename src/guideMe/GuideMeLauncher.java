/**
 * @Author Faisal Rahman
 *
 * GuideMe - University help program for navigation around the campus
 * Computer Science (BSc) Final year project
 *
 * This is a simple launcher program and it contains all the FXML files to be loaded
 */

package guideMe;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 *
 * Key for the maps API: AIzaSyBIVYjXNV1GSYJNALTuSEiJeu7Que8YRVU
 *
 * Command line tools must be installed: https://developer.apple.com/download/more/
 *
 */

public class GuideMeLauncher extends Application {


    public static String lockScreenID = "LockScreen";
    public static String mainMenuID = "MainMenu";
    //public static String lecturersID = "Lecturers";
    //public static String departmentsID = "Departments";

    private static String lockScreenFXML = "LockScreen.fxml";
    private static String mainMenuFXML = "MainMenu.fxml";
    //private static String lecturersFXML = "./Lecturers/Lecturers.fxml";
    //private static String departmentsFXML = "./Departments/Departments.fxml";

    @Override
    public void start(Stage primaryStage) {

        GuideMeSceneContainer myContainer = new GuideMeSceneContainer();
        myContainer.loadScene(GuideMeLauncher.lockScreenID, GuideMeLauncher.lockScreenFXML);
        myContainer.loadScene(GuideMeLauncher.mainMenuID, GuideMeLauncher.mainMenuFXML);
        //myContainer.loadScene(GuideMeLauncher.lecturersID, GuideMeLauncher.lecturersFXML);
       // myContainer.loadScene(GuideMeLauncher.departmentsID, GuideMeLauncher.departmentsFXML);

        myContainer.applyScene(GuideMeLauncher.lockScreenID);

        Group root = new Group();
        root.getChildren().addAll(myContainer);
        Scene scene = new Scene(root);
        primaryStage.setTitle("GuideME - Directions for the lancaster university campus");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        System.setProperty("java.net.useSystemProxies", "true");
        launch(args);
    }
}