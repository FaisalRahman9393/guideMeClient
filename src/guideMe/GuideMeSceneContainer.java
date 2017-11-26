/**
 * @Author Faisal Rahman
 *
 * GuideMe - University help program for navigation around the campus
 * Computer Science (BSc) Final year project
 *
 *
 */
package guideMe;

import java.util.HashMap;
import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.event.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.layout.StackPane;
import javafx.util.*;

public class GuideMeSceneContainer extends StackPane {

    /**
     * For adding scenes to the HashMap
     * @param name - Name the scene is going to be stored as
     * @param scene - The scene to be stored
     */
    public void sceneMap(String name, Node scene) {
        allScenes.put(name, scene);
    }

    /**
     * Gets a Scene name and returns the related node
     * @param nameOfScene - Name of the scene to get
     * @return - The scene as Node
     */
    public Node sceneGetter(String nameOfScene) {
        return allScenes.get(nameOfScene);
    }

    /**
     * Retrieves the FXMLs of scenes,
     * Inserts the scenes to the AllScenes HashMap,
     * Gives the scene to the controller.
     * @param sceneName - Name of the scene to load in to the sceneMap
     * @param fxmlPath - The path of the fxml file
     * @return
     */
    //Loads all the FXML files...
    public boolean loadScene(String name, String resource) {
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource(resource));
            Parent loadScreen = (Parent) myLoader.load();
            SceneInt myScreenControler = ((SceneInt) myLoader.getController());
            myScreenControler.setScreenParent(this);
            sceneMap(name, loadScreen);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * This method shows a given scene and
     * Does some animation on the scene
     * @param nameOfScene - Name of the scene to be gotten from the HashMap
     * @return True if success.
     */
    public boolean applyScene(final String nameOfScene) {
        if (allScenes.get(nameOfScene) != null) {   //screen loaded
            final DoubleProperty sceneOpacity = opacityProperty();

            if (!getChildren().isEmpty()) {
                Timeline fade = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(sceneOpacity, 1.0)),
                        new KeyFrame(new Duration(1000), new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent t) {
                                //Remove the current scene
                                getChildren().remove(0);
                                //Get the new scene from the HashMap
                                getChildren().add(0, allScenes.get(nameOfScene));
                                Timeline fadeIn = new Timeline(
                                        new KeyFrame(Duration.ZERO, new KeyValue(sceneOpacity, 0.0)),
                                        new KeyFrame(new Duration(800), new KeyValue(sceneOpacity, 1.0)));
                                fadeIn.play();
                            }
                        }, new KeyValue(sceneOpacity, 0.0)));
                fade.play();

            } else {
                //When no scene displayed...
                setOpacity(0.0);
                getChildren().add(allScenes.get(nameOfScene));
                Timeline fadeIn = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(sceneOpacity, 0.0)),
                        new KeyFrame(new Duration(2500), new KeyValue(sceneOpacity, 1.0)));
                fadeIn.play();
            }
            return true;
        } else {
            System.out.println("There was an error when trying to load the scene \n");
            return false;
        }
    }

    /**
     * A simple HashMap for the scenes to be contained,
     * Gets a scene name for reference
     */
    //Scenes are stored here for retrieval
    private HashMap<String, Node> allScenes = new HashMap<>();

    public GuideMeSceneContainer() {
        super();
    }
}