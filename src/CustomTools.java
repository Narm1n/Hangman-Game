import javax.imageio.ImageIO; // ImageIO -> class that provides methods for reading images
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage; // BufferedImage -> class that allows pixel manipulation on an image
// In this code it's used to read an image from a file and then convert it into an icon for a JLabel (loadImage, UpdateImage methods)
import java.io.File; // File -> class used to represent file and directory pathnames
import java.io.IOException;
import java.io.InputStream; // InputStream -> abstract class that represents an input stream of bytes
// In this code it's used to read image and font files from the application's resources 

/**
 * The CustomTools class provides utility methods for handling images, fonts, and text manipulation.
 */
public class CustomTools {
    /**
     * Loads an image from the specified resource and creates a JLabel with the image as an icon.
     *
     * @param resource the path to the image resource
     * @return a JLabel containing the loaded image as an icon, or null if an error occurs
     */
    public static JLabel loadImage(String resource){
        BufferedImage image;
        try{
            InputStream inputStream = CustomTools.class.getResourceAsStream(resource);
            image = ImageIO.read(inputStream);
            return new JLabel(new ImageIcon(image));
        }
        catch(Exception e){
            System.out.println("Error: " + e);
        }
        return null;
    }

    /**
     * Updates the icon of a JLabel with a new image loaded from the specified resource.
     *
     * @param imageContainer the JLabel whose icon will be updated
     * @param resource the path to the new image resource
     */
    public static void updateImage(JLabel imageContainer, String resource){
        BufferedImage image;
        try{
            InputStream inputStream = CustomTools.class.getResourceAsStream(resource);
            image = ImageIO.read(inputStream);
            imageContainer.setIcon(new ImageIcon(image));
        }catch(IOException e){
            System.out.println("Error: " + e);
        }
    }

    /**
     * Creates a font from the specified resource file.
     *
     * @param resource the path to the font resource
     * @return the created Font object, or null if an error occurs
     */
    public static Font createFont(String resource){
        // getting font file path
        String filePath = CustomTools.class.getClassLoader().getResource(resource).getPath();

        // checking for empty spaces in path (bug)
        if(filePath.contains("%20")){
            filePath = filePath.replaceAll("%20", " ");
        }

        // creating font
        try{
            File customFontFile = new File(filePath);
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, customFontFile);
            return customFont;
        }catch(Exception e){
            System.out.println("Error: " + e); //FontFormatException 
        }
        return null;
    }

    /**
     * Hides non-space characters in the provided word by replacing them with asterisks.
     *
     * @param word the word to be hidden
     * @return the hidden word with non-space characters replaced by asterisks
     */
    public static String hideWords(String word){
        String hiddenWord = "";
        for(int i = 0; i < word.length(); i++){
            if(!(word.charAt(i) == ' ')){
                hiddenWord += "*";
            }else{
                hiddenWord += " ";
            }
        }
        return hiddenWord;
    }
}