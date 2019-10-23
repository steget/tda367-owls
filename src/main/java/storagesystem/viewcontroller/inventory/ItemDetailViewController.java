package storagesystem.viewcontroller.inventory;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
 * Controls a detailed view of an item. Can be used to book an item.
 *
 * @author Jonathan Eksberg, Carl Lindh, Pär Aronsson
 */

public class ItemDetailViewController extends AnchorPane {
    private final IReservable reservableItem;
    private final Team itemOwner;
    private List<Location> locationList;
    private ObservableList<String> locationNames;
    private List<DetailedItemViewListener> detailListeners = new ArrayList<>();
    private List<saveButtonClickedListener> saveButtonListeners = new ArrayList<>();


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


    ItemDetailViewController(IReservable reservableItem) {
        this.reservableItem = reservableItem;
        this.itemOwner = StoreIT.getCurrentOrganisation().getItemOwner(reservableItem);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/inventory/ItemDetailView.fxml"));
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
        updateAllVisibleFields();
        closeButtonImageView.setImage(new Image("pictures/close-button.png"));
        switch (reservableItem.getCondition()) {
            case BAD:
                itemPageConditionSlider.setValue(0);
                break;
            case GOOD:
                itemPageConditionSlider.setValue(1);
                break;
            case GREAT:
                itemPageConditionSlider.setValue(2);
                break;
        }


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

    /**
     * Fill all fields from the item
     */
    private void updateAllVisibleFields() {
        setNameLabel(reservableItem.getName());
        setDescription(reservableItem.getDescription());
        setUserRequirements(reservableItem.getUserRequirements());
        setIDLabel("" + reservableItem.getID());
        setAmountLabel("" + reservableItem.getAmount());
        setConditionSlider(reservableItem.getCondition());
        setReservableBtn(reservableItem.isReservable());
        setImage(PictureHandler.getItemImage(reservableItem.getID(), itemPageNameTA.getText()));
        setTeamOwnerLabel(itemOwner.getName());
        setReservableChoiceBox();
        setLocationChoicebox();
    }

    private void setLocationChoicebox() {
        ObservableList<String> locationNames = FXCollections.observableArrayList();
        for (Location l : locationList) {
            locationNames.add(l.getName());
        }
        itemPageLocationChoicebox.setItems(locationNames);

        for (String s : locationNames) {
            if (s.equals(StoreIT.getCurrentOrganisation().getLocation(reservableItem.getLocationID()).getName())) {
                itemPageLocationChoicebox.getSelectionModel().select(s);
            }
        }


    }

    private void setReservableChoiceBox() {
        if (reservableItem.isReservable()) {
            isReservableChoiceBox.getSelectionModel().select(0);
        } else {
            isReservableChoiceBox.getSelectionModel().select(1);
        }

    }

    @FXML
    protected void reserveBtnPressed() {
        //TODO: create a new reservation if possible
    }

    @FXML
    private void closeDetailView() {
        for (DetailedItemViewListener l : detailListeners) {
            l.detailItemViewClicked();
        }
    }

    private void setConditionSlider(Condition condition) {
        int value = 0;
        switch (condition) {
            case BAD:
                value = 0;
                break;
            case GOOD:
                value = 1;
                break;
            case GREAT:
                value = 2;
                break;
            default:
                break;
        }
        itemPageConditionSlider.setValue(value);
    }

    private void setDescription(String string) {
        itemPageDescriptionTA.setText(string);
    }

    private void setUserRequirements(String string) {
        itemPageUserRequirementsTA.setText(string);
    }

    private void setImage(Image image) {
        itemPageImageView.setImage(image);
    }

    private void setNameLabel(String name) {
        itemPageNameTA.setText(name);
    }

    private void setIDLabel(String id) {
        itemPageIDLabel.setText("ID: " + id);
    }

    private void setAmountLabel(String amount) {
        itemPageAmountTA.setText(amount);
    }


    private void setTeamOwnerLabel(String teamOwner) {
        itemPageTeamOwnerLabel.setText("Owner: " + teamOwner);
    }

    private void setReservableBtn(boolean reservable) {
        itemPageReserveBtn.setDisable(!reservable);
    }


    /**
     * adds a listener to this object.
     *
     * @param listener
     */
    public void addDetailListener(DetailedItemViewListener listener) {
        detailListeners.add(listener);
    }

    public void addSaveButtonListener(saveButtonClickedListener listener) {
        saveButtonListeners.add(listener);
    }


    /**
     * This method is called when user is in its own inventory.
     * It changes all variables so that they can be edited by the user.
     * The default setting is that the variables are non-editable.
     */
    public void editItem() {

        itemPageReserveBtn.setVisible(false);
        itemPageAmountTA.setEditable(true);
        itemPageSaveButton.setVisible(true);
        itemPageDescriptionTA.setEditable(true);
        itemPageUserRequirementsTA.setEditable(true);
        itemPageNameTA.setEditable(true);
        isReservableChoiceBox.setDisable(false);
        editPane.toFront();
        itemPageConditionSlider.setDisable(false);
        itemPageImageView.setOnMouseClicked(event -> {
            changeItemImage();
        });
    }

    /**
     * Saves the changes to the current item.
     */
    public void saveItem() {

        reservableItem.setName(itemPageNameTA.getText());
        reservableItem.setDescription(itemPageDescriptionTA.getText());
        reservableItem.setUserRequirements(itemPageUserRequirementsTA.getText());
        reservableItem.setReservable(isReservableChoiceBox.getSelectionModel().getSelectedIndex() == 0);
        saveCondition((int) itemPageConditionSlider.getValue());
        saveLocation(itemPageLocationChoicebox.getValue().toString());
        reservableItem.setAmount(Integer.valueOf(itemPageAmountTA.getText()));
        for (saveButtonClickedListener l : saveButtonListeners) {
            l.saveButtonClicked();
        }
    }

    /**
     * Recieves a string. loops through the location lists' name. If the strings match,
     * it saves the location that matched the string as the new location to the item.
     *
     * @param locationName is used to get the correct location from the list of locations.
     */
    private void saveLocation(String locationName) {

        for (Location l : locationList) {
            if (l.getName().equals(locationName)) {
                reservableItem.setLocationID(l.getID());
            }
        }

    }

    private void saveCondition(int value) {
        switch (value) {
            case 0:
                reservableItem.setCondition(Condition.BAD);
                break;
            case 1:
                reservableItem.setCondition(Condition.GOOD);
                break;
            case 2:
                reservableItem.setCondition(Condition.GREAT);
                break;
        }
    }

    /**
     * This method opens the clickers filemanager in which the user may choose an image.
     * The image then get saved as a bufferedImage which then becomes an Image that can be displayed to the item.
     * The method also writes the bufferedImage to the location "recources/pictures/items"
     * where it then can be called from.
     */
    @FXML
    void changeItemImage() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("jpg", "*.jpg"), new FileChooser.ExtensionFilter("png", "*.png"), new FileChooser.ExtensionFilter("jpeg", "*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            try {
                BufferedImage selectedImage = ImageIO.read(selectedFile);
                PictureHandler.saveItemImagePic(selectedImage, reservableItem.getID(), itemPageNameTA.getText());
                itemPageImageView.setImage(PictureHandler.getItemImage(reservableItem.getID(), itemPageNameTA.getText()));

            } catch (IOException exception) {
                System.out.println("Can't read image: " + selectedFile.getPath());
                //todo add an animation for when an image cant be read.
            }
        }
    }


    /**
     * Listener interface for DetailedItemView
     */
    interface DetailedItemViewListener {
        void detailItemViewClicked();
    }

    /**
     * Listener interface for saveButtonClicked
     */
    interface saveButtonClickedListener {
        void saveButtonClicked();
    }
}