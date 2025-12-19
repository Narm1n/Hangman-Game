import java.awt.*; // Abstract Window Toolkit (AWT) -> GUI components
/**
 * The CommonConstants class contains common constants used throughout the application.
 */
public class CommonConstants {
    /**
     * The path to the data file.
     */
    public static final String DATA_PATH = "resources/data.txt"; // public static final -> accessible from anywhere
    /**
     * The path to the image file.
     */
    public static final String IMAGE_PATH = "resources/1.png";
    /**
     * The path to the font file.
     */
    public static final String FONT_PATH = "resources/mainfont.ttf";
    /**
     * The primary color used in the GUI.
     */
    public static final Color PRIMARY_COLOR = Color.decode("#1F5776");
    /**
     * The secondary color used in the GUI.
     */
    public static final Color SECONDARY_COLOR = Color.RED;
    /**
     * The background color used in the GUI.
     */
    public static final Color BACKGROUND_COLOR = Color.decode("#101820");
    /**
     * The size of the main frame.
     */
    public static final Dimension FRAME_SIZE = new Dimension(540, 760);
    /**
     * The size of the button panel.
     */
    public static final Dimension BUTTON_PANEL_SIZE = new Dimension(FRAME_SIZE.width, (int)(FRAME_SIZE.height * 0.42));
    /**
     * The size of the result dialog.
     */
    public static final Dimension RESULT_DIALOG_SIZE = new Dimension((int)(FRAME_SIZE.width/2), (int)(FRAME_SIZE.height/6));
}


