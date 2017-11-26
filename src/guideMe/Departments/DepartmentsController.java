/**
 * This is the controller for the Lock Screen
 */

package guideMe.Departments;

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
import javafx.scene.control.TextArea;
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

public class DepartmentsController implements Initializable, SceneInt, MapComponentInitializedListener, DirectionsServiceCallback{

    private GuideMeSceneContainer myContainer;
    private String selectedDepartment;
    private String selectedLecturer;
    private ObservableList<String> departList = FXCollections.observableArrayList();
    @FXML Label selectedLecturerLabel; //The lecturer's name
    @FXML Label selectedDepartmentLabel; //The lecturer's department
    @FXML Label selectedPositionLabel; //The lecturer's number
    @FXML TextArea selectedOfficeLabel; //The lecturer's office
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
        displayData();
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
     * Function is called upon clicking one of the lecturers
     */
    public void displayData(){
        mapInitialized();
        mapView.setVisible(true);

        DirectionsRequest request = new DirectionsRequest("Lancaster university, "+MainMenuController.thisDepartment+"", pointB, TravelModes.WALKING);
        directionsService.getRoute(request, this, new DirectionsRenderer(true, mapView.getMap(), directionsPane));
        mapView.getMap().showDirectionsPane();


        profilePane.setVisible(true);
        backgroundPane.setVisible(true);
        //mapPane.setVisible(true);

        try {
            String x = apiCall.guideMeServer.returnATableWhereDepartment("Departments","name",selectedDepartment);
            ArrayList<String> list;

            Object obj= JSONValue.parse(x);
            JSONObject jsonObject = (JSONObject) obj;

            selectedLecturerLabel.setText(jsonObject.get("Name").toString());
            selectedDepartmentLabel.setText(jsonObject.get("Location").toString());
            selectedPositionLabel.setText(jsonObject.get("Faculty").toString());
            selectedOfficeLabel.setText(jsonObject.get("Info").toString());
            System.out.println(jsonObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        departmentListView.setVisible(false);
    }
}
