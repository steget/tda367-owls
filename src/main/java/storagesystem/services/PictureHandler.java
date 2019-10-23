package storagesystem.services;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class handles the the writing and reading of images in the program.
 * This class handles where images gets saved and the naming of them.
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
    public static String getImageFilePath(int itemID, String itemName) {

        return "src/main/resources/pictures/items" + File.separatorChar + itemID + "-" + itemName + ".jpg";
    }

    /**
     * Uses ID and Name to get an image from a location. It reads from a specified path and then returns an Image.
     *
     * @param itemID
     * @param itemName
     * @return the file requested as an image
     */
    public static Image getItemImage(int itemID, String itemName) {
        Image img = null;
        try {
            img = SwingFXUtils.toFXImage(ImageIO.read(new File(getImageFilePath(itemID, itemName))), null);
        } catch (IOException o) {
            System.out.println("Could not read file: " + getImageFilePath(itemID, itemName));
        }

        return img;

    }

    /**
     * Saves a BufferedImage as a .jpg, in the pictures/items folder and names it in the format ID-name.jpg,
     * where ID is the entered itemID and name is the itemName
     *
     * @param file
     * @param itemID
     * @param itemName
     */
    public static void saveItemImagePic(BufferedImage file, int itemID, String itemName) {

        try {
            ImageIO.write(file, "jpg", new File(getImageFilePath(itemID, itemName)));
        } catch (IOException exception) {
            System.out.println("Could not read file: " + getImageFilePath(itemID, itemName));

        }
    }

}