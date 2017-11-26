/**
 * This is the controller for the Lock Screen
 */

package guideMe;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class LockScreenController implements Initializable, SceneInt {

    GuideMeSceneContainer myController;
    @Override
    public void initialize(URL url, ResourceBundle r) {
        // TODO
    }

    public void setScreenParent(GuideMeSceneContainer s){
        myController = s;
    }

    @FXML
    private void goToMainMenu(ActionEvent event){
        myController.applyScene(GuideMeLauncher.mainMenuID);
    }

/**
    @FXML
    private void goToScreen3(ActionEvent event){
        myController.applyScene(GuideMeLauncher.lecturersID);
    }
    **/
}