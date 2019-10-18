package storagesystem.controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import storagesystem.StorageSystem;
import storagesystem.model.Condition;
import storagesystem.model.IReservable;
import storagesystem.model.Item;
import storagesystem.model.Team;
import storagesystem.services.PictureHandler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Controls a detailed view of an item. Can be used to book an item.
 *
 * @author Jonathan Eksberg, Carl Lindh
 */
public class DetailedItemViewController extends AnchorPane {
    private final IReservable reservableItem;
    private final Team itemOwner;

    @FXML
    private AnchorPane rootPane;
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
    private Label itemPageAmountLabel;
    @FXML
    private Label itemPageLocationLabel;
    @FXML
    private Label itemPageTeamOwnerLabel;
    @FXML
    private Label itemPageReservableLabel;
    @FXML
    private Slider itemPageConditionSlider;
    @FXML
    private TextArea itemPageDescriptionTA;
    @FXML
    private TextArea itemPageUserRequirementsTA;
    @FXML
    Button itemPageReserveBtn;
    @FXML
    Button itemPageSaveButton;

    private boolean isInEditMode;

    private PictureHandler pictureHandler = new PictureHandler();

    DetailedItemViewController(IReservable reservableItem, Team itemOwner) {
        this.reservableItem = reservableItem;
        this.itemOwner = itemOwner;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/detailedItemView.fxml"));
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
        updateAllVisibleFields();
        closeButtonImageView.setImage(new Image("pictures/close-button.png"));

        //consume click so the box doesn't close itself
        contentPane.setOnMouseClicked(Event::consume);
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
        setReservableLabel("" + reservableItem.isReservable());
        setReservableBtn(reservableItem.isReservable());
        setLocationLabel(reservableItem.getLocation().getName());
        setImage(reservableItem.getImage());
        setTeamOwnerLabel(itemOwner.getName());
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
        itemPageAmountLabel.setText("Amount: " + amount);
    }

    private void setLocationLabel(String location) {
        itemPageLocationLabel.setText("Location: " + location);
    }

    private void setTeamOwnerLabel(String teamOwner) {
        itemPageTeamOwnerLabel.setText("Owner: " + teamOwner);
    }

    private void setReservableLabel(String reservable) {
        itemPageReservableLabel.setText("Reservable: " + reservable);
    }

    private void setReservableBtn(boolean reservable) {
        itemPageReserveBtn.setDisable(!reservable);
    }

    /**
     * Below this line contains listener methods
     */
    private List<DetailedItemViewListener> detailListeners = new ArrayList<>();
    private List<saveButtonClickedListener> saveButtonListeners = new ArrayList<>();


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

/******************************************************************************************************
 * methods below is about creating and saving an iReservable.
 ******************************************************************************************************/

    /**
     * this method creates a new item and registers it to the current team.
     *
     * @author Pär Aronsson
     */
    public void editItem() {

        itemPageDescriptionTA.setEditable(true);
        itemPageUserRequirementsTA.setEditable(true);
        itemPageNameTA.setEditable(true);

        itemPageImageView.setOnMouseClicked(event -> {
            changeItemImage();
        });


    }

    public void saveItem(){

        reservableItem.setName(itemPageNameTA.getText());
        reservableItem.setDescription(itemPageDescriptionTA.getText());
        reservableItem.setUserRequirements(itemPageUserRequirementsTA.getText());
        reservableItem.setImage(itemPageImageView.getImage());


        //todo koppla ihop changeprofilepic metoden så man kan redigera den i realtid.

        for(saveButtonClickedListener l: saveButtonListeners){
            l.saveButtonClicked();
        }
    }


    @FXML
    void changeItemImage(){

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("jpg", "*.jpg"), new FileChooser.ExtensionFilter("png", "*.png"), new FileChooser.ExtensionFilter("jpeg", "*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if(selectedFile != null){
            try {
                BufferedImage selectedImage = ImageIO.read(selectedFile);
                pictureHandler.saveProfilePic(selectedImage, itemPageNameTA.getText());

            } catch(IOException exception){
                System.out.println("Can't read image: " + selectedFile.getPath());
            }
        }

    }



    /**
     * listener method.
     */
    interface DetailedItemViewListener {
        void detailItemViewClicked();
    }

    /**
     * listens to the savebutton
     */
    interface saveButtonClickedListener{
       void saveButtonClicked();
    }
}





