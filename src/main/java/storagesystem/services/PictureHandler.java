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
     * Drops the alpha channel of the buffered image sent in. Should be used when creating a buffered image from JavaFX Image.
     * @param src
     * @return
     */
    public static BufferedImage dropAlphaChannel(BufferedImage src) {
        BufferedImage convertedImg = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_RGB);
        convertedImg.getGraphics().drawImage(src, 0, 0, null);

        return convertedImg;
    }

    /**
     * Retrieves the items searchpath.
     *
     * @param itemID
     * @param itemName
     * @return the image URL that can be used to read/write the file
     */
    public static String getImageUrl(int itemID, String itemName) {

        return "/pictures/items/" + itemID + "-" + itemName + ".jpg";
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
        String filePath = "src/main/resources" + getImageUrl(itemID, itemName);
        try {
            img = SwingFXUtils.toFXImage(ImageIO.read(new File(filePath)), null);
        } catch (IOException o) {
            System.out.println("Could not read file: " + filePath);
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
        String filePath = "src/main/resources" + getImageUrl(itemID, itemName);

        try {
            ImageIO.write(file, "jpg", new File(filePath));
        } catch (IOException exception) {
            System.out.println("Could not read file: " + filePath);

        }
    }

}