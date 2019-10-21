package storagesystem.services;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * this class handles the the writing and reading of images in the program.
 * this class handles where images gets saved and the naming of them.
 * It also is the one that reads the image and returns it to the requester.
 */
public class PictureHandler {
    /**
     * Retrieves the items searchpath.
     *
     * @param itemID
     * @param itemName
     * @return the image URL that can be used to read/write the file
     */
    private String getImageFilePath(String itemID, String itemName) {
        //String myFile = ".src" + File.separatorChar + "main" + File.separatorChar resources\pictures";

        return "src/main/resources/pictures/items" + File.separatorChar + itemID + "-" + itemName + ".jpg";
    }

    /**
     * convertes a file to an image that can be used.
     *
     * @param itemID
     * @param itemName
     * @return the file requested as an image
     */
    public Image getItemImage(String itemID, String itemName) {
        Image img = null;
        try {
            img = SwingFXUtils.toFXImage(ImageIO.read(new File(getImageFilePath(itemID, itemName))), null);
        } catch (IOException o) {
            System.out.println("Could not read file: " + getImageFilePath(itemID, itemName));
        }

        return img;

    }

    /**
     * Saves a requested file to the pictures/items folder and renames it to the items ID-name.jpg
     *
     * @param file
     * @param itemID
     * @param itemName
     */
    public void saveItemImagePic(BufferedImage file, String itemID, String itemName) {

        try {
            ImageIO.write(file, "jpg", new File(getImageFilePath(itemID, itemName)));
        } catch (IOException exception) {
            System.out.println("Could not read file: " + getImageFilePath(itemID, itemName));

        }
    }

}