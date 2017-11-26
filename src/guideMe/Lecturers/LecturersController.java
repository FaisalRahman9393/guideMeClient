/**
 * This is the controller for the Lock Screen
 */

package guideMe.Lecturers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lynden.gmapsfx.service.elevation.*;
import guideMe.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.control.TextField;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.*;
import com.lynden.gmapsfx.service.directions.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class LecturersController implements Initializable, SceneInt, MapComponentInitializedListener, DirectionsServiceCallback{

    GuideMeSceneContainer myContainer;
    String selectedDepartment;
    String selectedLecturer;
    ObservableList<String> departList = FXCollections.observableArrayList();
    ObservableList<String> lectList = FXCollections.observableArrayList();
    @FXML Label selectedLecturerLabel; //The lecturer's name
    @FXML Label selectedDepartmentLabel; //The lecturer's department
    @FXML Label selectedPositionLabel; //The lecturer's number
    @FXML Label selectedOfficeLabel; //The lecturer's office
    @FXML Label selectedEmailLabel; //The lecturer's email
    @FXML Label selectedNumberLabel; //The lecturer's number
    @FXML Label selectedAvailabilityLabel; //The lecturer's number
    @FXML Pane profilePane;
    @FXML Pane backgroundPane;
    protected DirectionsService directionsService;
    protected DirectionsPane directionsPane;
    @FXML
    protected GoogleMapView mapView;
    protected DirectionsServiceCallback d;
    protected ElevationServiceCallback el;
    AuthenticatedAPICall apiCall = new AuthenticatedAPICall();

    @FXML
    protected TextField fromTextField;

    @FXML
    protected TextField toTextField;
    String pointB;


    @Override
    public void initialize(URL url, ResourceBundle r) {
        selectedLecturer = null;
        lecturerListView.getItems().clear();
        departmentListView.getItems().clear();
        profilePane.setVisible(false);
        backgroundPane.setVisible(false);
        mapView.addMapInializedListener(this);
        mapView.setVisible(false);
        getDepartments();
    }

    /**
     * Map is set up on call
     */
    public void mapInitialized() {
        MapOptions options = new MapOptions();

        options.center(new LatLong(54.0104, -2.7877))
                .zoomControl(true)
                .mapMaker(true)
                .overviewMapControl(false)
                .mapType(MapTypeIdEnum.TERRAIN);
       mapView.createMap(options,false);
        directionsService = new DirectionsService();
        directionsPane = mapView.getDirec();

    }
    @Override
    public void directionsReceived(DirectionsResult results,DirectionStatus status) {
    }

    /**
     * The list where all departments will be stored
     */
    @FXML
    private ListView<String> departmentListView;
    /**
     * The list where all lecturers will be stored
     */
    @FXML
    private ListView<String> lecturerListView;

    public void setScreenParent(GuideMeSceneContainer s) {
        myContainer = s;
    }

    @FXML
    private void goToMainMenu(ActionEvent event) {
        myContainer.applyScene(GuideMeLauncher.mainMenuID);

        //getDepartments();
    }

    @FXML
    private void lockScreen(ActionEvent event) {
        myContainer.applyScene(GuideMeLauncher.lockScreenID);
        //getDepartments();
    }

    /**
     * When one of the departments is selected the following function is called
     */
    @FXML
    public void clickedOnDepartments(){
        System.out.println(MainMenuController.thisDepartment);
        selectedDepartment = departmentListView.getSelectionModel().getSelectedItem();
        System.out.println("clicked on " + selectedDepartment);


        try {
            ArrayList<String> list;
            String x = apiCall.guideMeServer.locationByTable("Departments",selectedDepartment);

            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<String>>() {}.getType();
            list = gson.fromJson(x, type);

            pointB = list.get(0);

        } catch (Exception e) {
            e.printStackTrace();
        }
        departmentListView.setItems(lectList);
        getLecturers();
    }

    /**
     * A function to pull all departments from the database
     */
    public void getDepartments(){
        departmentListView.setVisible(true);
        try {
            String x = apiCall.guideMeServer.returnATableDepartment("departments");
            ArrayList<String> list;

            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<String>>() {}.getType();
            list = gson.fromJson(x, type);

            for (int i = 0; i < list.size();i++){
                departList.add(list.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        departmentListView.setItems(departList);
    }

    /**
     * This functions gets the name of all lecturers from the database
     */
    public void getLecturers(){
        departmentListView.setVisible(false);
        lecturerListView.setVisible(true);
        try {
            String x = apiCall.guideMeServer.academicsByDepartment(selectedDepartment);
            ArrayList<String> list;

            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<String>>() {}.getType();
            list = gson.fromJson(x, type);

            for (int i = 0; i < list.size();i++){
                lectList.add(list.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        lecturerListView.setItems(lectList);
    }

    /**
     * Function is called upon clicking one of the lecturers
     */
    public void clickedOnLecturers(){
        mapInitialized();
        mapView.setVisible(true);

        DirectionsRequest request = new DirectionsRequest("Lancaster university, "+MainMenuController.thisDepartment+"", pointB, TravelModes.WALKING);
        directionsService.getRoute(request, this, new DirectionsRenderer(true, mapView.getMap(), directionsPane));
        mapView.getMap().showDirectionsPane();


        selectedLecturer = lecturerListView.getSelectionModel().getSelectedItem();
        profilePane.setVisible(true);
        backgroundPane.setVisible(true);
        //mapPane.setVisible(true);

        try {
            String x = apiCall.guideMeServer.getOneAcademicInfo(selectedLecturer);
            ArrayList<String> list;

            Object obj= JSONValue.parse(x);
            JSONObject jsonObject = (JSONObject) obj;

            selectedLecturerLabel.setText(jsonObject.get("Name").toString());
            selectedDepartmentLabel.setText(jsonObject.get("Department").toString());
            selectedPositionLabel.setText(jsonObject.get("Role").toString());
            selectedOfficeLabel.setText(jsonObject.get("Office").toString());
            selectedNumberLabel.setText(jsonObject.get("Number").toString());
            selectedEmailLabel.setText(jsonObject.get("Email").toString());
            selectedAvailabilityLabel.setText(jsonObject.get("Availability").toString());

            System.out.println(jsonObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        lecturerListView.setVisible(false);
    }
}
