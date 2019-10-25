package storagesystem.viewcontroller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import storagesystem.model.StoreIT;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Jonathan Eksberg
 * Controller for the frame
 */
public class FrameworkController implements Initializable, ILoadUI {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private AnchorPane centerPane;
    @FXML
    private Button teamButton;
    @FXML
    private Button userButton;
    @FXML
    private Button yourInventoryButton;
    @FXML
    private Button allItemsButton;
    @FXML
    private Button reservationsButton;
    @FXML
    private Pane frameTopPane;
    @FXML
    private BorderPane borderPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadUI("/allItems/allItemsList.fxml");
    }

    @FXML
    void userButtonPressed(){ loadUI("/settings/userPage.fxml"); }

    @FXML
    private void teamButtonPressed(){
        if(StoreIT.getCurrentOrganisation().getUsersTeams(StoreIT.getCurrentUser()).size() > 0){
            loadUI("/settings/teamPage.fxml");
        } else{
            noTeamPopUp();
        }
    }

    @FXML
    void allItemsButtonPressed() {
        loadUI("/allItems/allItemsList.fxml");
    }

    @FXML
    void reservationsButtonPressed() {
        loadUI("/reservations/reservations.fxml");
    }

    @FXML
    private void yourInventoryButtonPressed(){

        if(StoreIT.getCurrentOrganisation().isUserPartOfTeam(StoreIT.getCurrentUser())){
            loadUI("/inventory/inventory.fxml");
        }else{
            noTeamPopUp();
        }
    }

    /**
     * When the logOutButton is pressed the root is switched and you are taken back to the loginPage
     */
    @FXML
    private void logOutButtonPressed(){
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/login/loginPage.fxml"));
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            Logger.getLogger(FrameworkController.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    /**
     * Method to avoid repetitive code.
     *
     * @param ui The url to a fxml file.
     */
    @Override
    public void loadUI(String ui) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(ui));
        } catch (IOException e) {
            Logger.getLogger(FrameworkController.class.getName()).log(Level.SEVERE, null, e);
        }
        borderPane.setCenter(root);
    }

    private void noTeamPopUp(){
        NoTeamPopUpController popUp = new NoTeamPopUpController();
        rootPane.getChildren().add(popUp);
        AbstractFader.fadeTransition(popUp, 6);
    }
}