package storeit.services;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This class handles the the writing and reading of images in the program.
 * This class handles where images gets saved and the naming of them.
 * It also is the one that reads the image and returns it to the requester.
 * @author Pär Aronsson, Carl Lindh
 */
public class PictureHandler {

    private static String defaultImagePath = "src/main/resources/pictures/items/unknown-item-image.png";

    /**
     * Retrieves the items searchpath.
     *
     * @param itemID
     * @return the image URL that can be used to read/write the file
     */
    private static String getImageUrl(int itemID) {

        return "/pictures/items/" + itemID + ".jpg";
    }

    /**
     * Uses ID and Name to get an image from a location. It reads from a specified path and then returns an Image.
     *
     * @param itemID
     * @return the file requested as an image
     */
    public static Image getItemImage(int itemID) {
        Image img = null;
        String filePath = "src/main/resources" + getImageUrl(itemID);
        try {
            img = SwingFXUtils.toFXImage(ImageIO.read(new File(filePath)), null);
        } catch (IOException o) {
            System.out.println("Could not read file: " + filePath);
            try {
                img = SwingFXUtils.toFXImage(ImageIO.read(new File(defaultImagePath)), null);
            } catch (IOException e) {
                System.out.println("Could not read default file: " + defaultImagePath);
            }
        }

        return img;

    }

    /**
     * Saves a BufferedImage as a .jpg, in the pictures/items folder and names it in the format ID-name.jpg,
     * where ID is the entered itemID and name is the itemName
     *
     * @param file
     * @param itemID
     */
    public static void saveItemImagePic(BufferedImage file, int itemID) {
        String filePath = "src/main/resources" + getImageUrl(itemID);

        try {
            ImageIO.write(file, "jpg", new File(filePath));
        } catch (IOException exception) {
            System.out.println("Could not read file: " + filePath);

        }
    }

}