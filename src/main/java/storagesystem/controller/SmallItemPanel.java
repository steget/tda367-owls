package storagesystem.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import storagesystem.model.IReservable;

import java.io.IOException;

public class SmallItemPanel extends AnchorPane {

    private IReservable reservableItem;

    IReservable getReservableItem() {
        return reservableItem;
    }

    public SmallItemPanel(IReservable reservableItem){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("smallItemPanel.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.reservableItem = reservableItem;
    }
}
