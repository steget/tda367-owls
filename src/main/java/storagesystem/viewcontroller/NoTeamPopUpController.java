package storagesystem.viewcontroller;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class NoTeamPopUpController extends AnchorPane {


    public NoTeamPopUpController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/noTeamPopUp.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);

        try {
            fxmlLoader.load();
        } catch (
                IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
