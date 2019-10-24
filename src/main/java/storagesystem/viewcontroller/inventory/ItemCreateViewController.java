package storagesystem.viewcontroller.inventory;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import storagesystem.model.*;
import storagesystem.services.PictureHandler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates an item and is able to save the item to an organisation.
 *
 * @author Pär Aronsson
 */

public class ItemCreateViewController extends AnchorPane {
    private final Team itemOwner;
    private List<Location> locationList;
    private ObservableList<String> locationNames;
    private List<RemoveCreateViewListener> removeCreateViewListeners = new ArrayList<>();
    private List<CreateItemButtonListener> createButtonListeners = new ArrayList<>();


    @FXML
    private AnchorPane contentPane;
    @FXML
    private ImageView itemPageImageView;
    @FXML
    private ImageView closeButtonImageView;
    @FXML
    private TextArea itemPageNameTA;
    @FXML
    private Label itemPageIDLabel;
    @FXML
    private Label itemPageTeamOwnerLabel;
    @FXML
    private Slider itemPageConditionSlider;
    @FXML
    private TextArea itemPageDescriptionTA;
    @FXML
    private TextArea itemPageUserRequirementsTA;
    @FXML
    private ChoiceBox isReservableChoiceBox;
    @FXML
    private TextArea itemPageAmountTA;
    @FXML
    private ChoiceBox itemPageLocationChoicebox;
    @FXML
    private Label imageErrorMsgLabel;
    @FXML
    Button itemPageReserveBtn;
    @FXML
    Button itemPageSaveButton;
    @FXML
    private Pane editPane;


    ItemCreateViewController() {


        this.itemOwner = StoreIT.getCurrentTeam();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/inventory/ItemCreateView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        initialize();
    }


    private void initialize() {
        locationList = StoreIT.getCurrentOrganisation().getLocations();
        isReservableChoiceBox.setItems(FXCollections.observableArrayList("True", "False"));
        closeButtonImageView.setImage(new Image("pictures/close-button.png"));
        itemPageImageView.setImage(new Image("pictures/items/unknown-item-image.png"));

        fillLocationChoicebox();
        itemPageConditionSlider.setValue(1);


        // Listener for the Amount textarea.
        // "\\d" represents a digit. [0-9] and if the value is not a digit then the program replaces it with empty space.
        itemPageAmountTA.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    itemPageAmountTA.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        //consume click so the box doesn't close itself
        contentPane.setOnMouseClicked(Event::consume);
        editPane.setOnMouseClicked(Event::consume);
        //todo two modes in the pane so you can either just view the info or edit the item
    }

    private void fillLocationChoicebox() {
        ObservableList<String> locationNames = FXCollections.observableArrayList();
        for (Location l : locationList) {
            locationNames.add(l.getName());
        }
        itemPageLocationChoicebox.setItems(locationNames);


    }


    @FXML
    private void closeCreateItemView() {
        for (RemoveCreateViewListener l : removeCreateViewListeners) {
            l.removeCreateView();
        }
    }

    private void setTeamOwnerLabel(String teamOwner) {
        itemPageTeamOwnerLabel.setText("Owner: " + teamOwner);
    }

    /**
     * adds a listener to this object.
     *
     * @param listener
     */
    public void addRemoveCreateViewListener(RemoveCreateViewListener listener) {
        removeCreateViewListeners.add(listener);
    }

    public void addCreateItemButtonListener(CreateItemButtonListener listener) {
        createButtonListeners.add(listener);
    }


    /**
     * Creates an IReservable item which gets filled with the data in the different boxes from "ItemCreateView.fxml"
     * It saves the item to the currentorganisation which also connects the item to a team.
     */
    @FXML
    public void createItem() {

        IReservable item = new Item(itemPageNameTA.getText(), itemPageDescriptionTA.getText(), itemPageUserRequirementsTA.getText(), (int) Integer.valueOf(itemPageAmountTA.getText()), saveCondition((int) itemPageConditionSlider.getValue()), isReservableChoiceBox.getSelectionModel().getSelectedIndex() == 0, getLocation(itemPageLocationChoicebox.getSelectionModel().getSelectedItem().toString()));
        BufferedImage tmpImg = SwingFXUtils.fromFXImage(PictureHandler.getItemImage(999), null);

        //Sets the temporary image 999.jpg as the new items image with the url named after the new items ID.
        PictureHandler.saveItemImagePic(tmpImg, item.getID());
        StoreIT.getCurrentOrganisation().addItem(item, StoreIT.getCurrentTeam());

        for (CreateItemButtonListener l : createButtonListeners) {
            l.createButtonClicked();
        }
    }

    /**
     * Recieves a string. loops through the location lists' name. If the strings match,
     * it saves the location that matched the string as the new location to the item.
     *
     * @param locationName is used to get the correct location from the list of locations.
     * @return the locationnumber that can be matched to a location ID.
     */
    private int getLocation(String locationName) {

        for (Location l : locationList) {
            if (l.getName().equals(locationName)) {
                return l.getID();
            }
        }

        return 0;
    }

    /**
     * saves the condition of the item.
     *
     * @param value is an int that comes from what condition ID  you choose in the conditionChoicebox
     * @return a condition value that can be stored in an Item
     */
    private Condition saveCondition(int value) {
        switch (value) {
            case 0:
                return Condition.BAD;
            case 1:
                return Condition.GOOD;
            case 2:
                return Condition.GREAT;
        }
        return null;
    }

    /**
     * This method opens the clickers filemanager in which the user may choose an image.
     * The image then get saved as a bufferedImage which then becomes an Image that can be displayed to the item.
     * The method also writes the bufferedImage to the location "recources/pictures/items"
     * where it then can be called from.
     */
    @FXML
    void createTempImage() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("jpg", "*.jpg"), new FileChooser.ExtensionFilter("png", "*.png"), new FileChooser.ExtensionFilter("jpeg", "*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            try {
                BufferedImage selectedImage = ImageIO.read(selectedFile);
                PictureHandler.saveItemImagePic(selectedImage, 9999);
                itemPageImageView.setImage(PictureHandler.getItemImage(9999));

            } catch (IOException exception) {
                System.out.println("Can't read image: " + selectedFile.getPath());
            }
        }
    }


    /**
     * Listener interface for DetailedItemView
     */
    interface RemoveCreateViewListener {
        void removeCreateView();
    }

    /**
     * Listener interface for saveButtonClicked
     */
    interface CreateItemButtonListener {
        void createButtonClicked();
    }

}