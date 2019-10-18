package storagesystem.services;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PictureHandler {

    private String getImageFilePath(String itemName){
        //String myFile = ".src" + File.separatorChar + "main" + File.separatorChar resources\pictures";

        return  itemName + ".jpg";
    }

    public void saveProfilePic(BufferedImage file, String itemName){

        try{
            ImageIO.write(file, "jpg", new File(getImageFilePath(itemName)));
        } catch(IOException exception){

        }
    }
}
