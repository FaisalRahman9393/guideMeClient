/**
 * This is the controller for the Lock Screen
 */

package guideMe.Facilities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.DirectionsPane;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.service.directions.*;
import com.lynden.gmapsfx.service.elevation.ElevationServiceCallback;
import guideMe.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FacilitiesController implements Initializable, SceneInt, MapComponentInitializedListener, DirectionsServiceCallback{

    private GuideMeSceneContainer myContainer;
    private String selectedDepartment;
    private ObservableList<String> departList = FXCollections.observableArrayList();
    @FXML Label facilityName; //The facility's name
    @FXML Label facilityLocation; //The facility's department
    @FXML Label facilityHours; //The facility's number
    @FXML TextArea facilityInfo; //The facility's office
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
            String x = apiCall.guideMeServer.locationByTable("Facilities",selectedDepartment);

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
            String x = apiCall.guideMeServer.returnATableDepartment("Facilities");
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
     * Function is called upon clicking one of the shops
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
            String x = apiCall.guideMeServer.returnATableWhereFac("Facilities","name",selectedDepartment);
            ArrayList<String> list;

            Object obj= JSONValue.parse(x);
            JSONObject jsonObject = (JSONObject) obj;

            facilityName.setText(jsonObject.get("Name").toString());
            facilityLocation.setText(jsonObject.get("Location").toString());
            facilityHours.setText(jsonObject.get("Hours").toString());
            facilityInfo.setText(jsonObject.get("Info").toString());
            System.out.println(jsonObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        departmentListView.setVisible(false);
    }
}
