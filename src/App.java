import javax.swing.*;

/**
 * The App class contains the main method to start the Hangman game application.
 */
public class App {

    /**
     * The main method of the Hangman game application.
     * 
     * @param args the command-line arguments (not used)
     */
    public static void main(String[] args) {
        // InvokeLater method is used to ensure that all user interface updates
        // are performed from the Event Dispatch Thread (EDT), which is a special
        // thread responsible for running all user interface-related activities.
        SwingUtilities.invokeLater(new Runnable() {
            /**
             * Run method to create and display the Hangman game GUI.
             */
            @Override
            public void run() {
                // Create a new instance of Hangman GUI and make it visible.
                new Hangman().setVisible(true);
            }
        });
    }
}

