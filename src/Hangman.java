import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent; // events like pressing button (high-level events)
import java.awt.event.ActionListener; // listens to the actions
import java.awt.event.WindowAdapter; // receiving window events like closing
import java.awt.event.WindowEvent; // low-level events (like closing, iconifying etc.)
/**
 * This class represents a Hangman game. It extends JFrame and implements ActionListener.
 */
public class Hangman extends JFrame implements ActionListener {
    // counting the number of incorrect guesses player has made
    private int incorrectGuesses;
    // storing the challenge from the WordDB here
    private String[] wordChallenge;
    private final WordDB wordDB;
    private JLabel hangmanImage, categoryLabel, hiddenWordLabel, resultLabel, wordLabel;
    private JButton[] letterButtons;
    private JDialog resultDialog;
    private Font customFont;
    /**
     * Constructor for the Hangman game. Initializes the game and adds GUI components.
     */
    public Hangman(){
        super("Hangman Game");
        setSize(CommonConstants.FRAME_SIZE);
        setDefaultCloseOperation(EXIT_ON_CLOSE); // the application will exit when the window is closed
        setLocationRelativeTo(null); // the window will be centered on the screen
        setLayout(null); // no layout manager (manually setting the size and position of components)
        setResizable(false); // the window cannot be resized
        getContentPane().setBackground(CommonConstants.BACKGROUND_COLOR);
        // init vars
        wordDB = new WordDB();
        letterButtons = new JButton[26];
        wordChallenge = wordDB.loadChallenge();
        customFont = CustomTools.createFont(CommonConstants.FONT_PATH);
        createResultDialog();
        addGuiComponents();
    }
    /**
     * Adds GUI components to the game.
     */
    private void addGuiComponents(){
        // hangman image
        hangmanImage = CustomTools.loadImage(CommonConstants.IMAGE_PATH);
        // at top left corner (0, 0)
        hangmanImage.setBounds(0, 0, hangmanImage.getPreferredSize().width, hangmanImage.getPreferredSize().height);
        // category display
        categoryLabel = new JLabel(wordChallenge[0]);
        categoryLabel.setFont(customFont.deriveFont(30f));
        categoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        categoryLabel.setOpaque(true);
        categoryLabel.setForeground(Color.WHITE);
        categoryLabel.setBackground(CommonConstants.SECONDARY_COLOR);
        categoryLabel.setBorder(BorderFactory.createLineBorder(CommonConstants.SECONDARY_COLOR));
        categoryLabel.setBounds(
                0,
                hangmanImage.getPreferredSize().height - 28,
                CommonConstants.FRAME_SIZE.width,
                categoryLabel.getPreferredSize().height
        );
        // hidden word
        hiddenWordLabel = new JLabel(CustomTools.hideWords(wordChallenge[1]));
        hiddenWordLabel.setFont(customFont.deriveFont(64f));
        hiddenWordLabel.setForeground(Color.WHITE);
        hiddenWordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        hiddenWordLabel.setBounds(
                0,
                categoryLabel.getY() + categoryLabel.getPreferredSize().height + 50,
                CommonConstants.FRAME_SIZE.width,
                hiddenWordLabel.getPreferredSize().height
        );
        // letter buttons
        GridLayout gridLayout = new GridLayout(4, 7);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(
                -5,
                hiddenWordLabel.getY() + hiddenWordLabel.getPreferredSize().height,
                CommonConstants.BUTTON_PANEL_SIZE.width,
                CommonConstants.BUTTON_PANEL_SIZE.height
        );
        buttonPanel.setLayout(gridLayout);
        // creating the letter buttons
        for(char c = 'A'; c <= 'Z'; c++){
            JButton button = new JButton(Character.toString(c));
            button.setBackground(CommonConstants.PRIMARY_COLOR);
            button.setFont(customFont.deriveFont(22f));
            button.setForeground(Color.WHITE);
            button.addActionListener(this);
            // using ASCII values to calculate the current index
            int currentIndex = c - 'A';

            letterButtons[currentIndex] = button;
            buttonPanel.add(letterButtons[currentIndex]);
        }
        // reset button
        JButton resetButton = new JButton("Reset");
        resetButton.setFont(customFont.deriveFont(22f));
        resetButton.setForeground(Color.WHITE);
        resetButton.setBackground(CommonConstants.SECONDARY_COLOR);
        resetButton.addActionListener(this);
        buttonPanel.add(resetButton);
        // quit button
        JButton quitButton = new JButton("Quit");
        quitButton.setFont(customFont.deriveFont(22f));
        quitButton.setForeground(Color.WHITE);
        quitButton.setBackground(CommonConstants.SECONDARY_COLOR);
        quitButton.addActionListener(this);
        buttonPanel.add(quitButton);

        getContentPane().add(categoryLabel);
        getContentPane().add(hangmanImage);
        getContentPane().add(hiddenWordLabel);
        getContentPane().add(buttonPanel);
    }
    /**
     * Handles action events from the GUI components.
     * @param e the action event.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if(command.equals("Reset") || command.equals("Restart")){
            resetGame();

            if(command.equals("Restart")){
                resultDialog.setVisible(false);
            }
        }else if(command.equals("Quit")){
            dispose();
            return;
        }else{
            // letter buttons
            // disabling button
            JButton button = (JButton) e.getSource();
            button.setEnabled(false);
            // checking if the word contains the user's guess,
            if(wordChallenge[1].contains(command)){
                // indicating that the user got it right
                button.setBackground(Color.GREEN);
                // storing the hidden word in a char array, so updating the hidden text
                char[] hiddenWord = hiddenWordLabel.getText().toCharArray();
                for(int i = 0; i < wordChallenge[1].length(); i++){
                    // updating _ to correct letter
                    if(wordChallenge[1].charAt(i) == command.charAt(0)){
                        hiddenWord[i] = command.charAt(0);
                    }
                }
                // updating hiddenWordLabel
                hiddenWordLabel.setText(String.valueOf(hiddenWord));
                // the user guessed the word right
                if(!hiddenWordLabel.getText().contains("*")){
                    // displaying dialog with success result
                    resultLabel.setText("Congrats, You got it right!");
                    resultDialog.setVisible(true);
                }
            }else{
                // indicating that the user chose the wrong letter
                button.setBackground(Color.RED);
                // increasing incorrect counter
                ++incorrectGuesses;
                // updating hangman image
                CustomTools.updateImage(hangmanImage, "resources/" + (incorrectGuesses + 1) + ".png");
                // user failed to guess word right
                if(incorrectGuesses >= 6){
                    // displaying result dialog with game over label
                    resultLabel.setText("You couldn't find the word, Try Again?");
                    resultDialog.setVisible(true);
                }
            }
            wordLabel.setText("Word: " + wordChallenge[1]);
        }
    }

    /**
     * Creates the result dialog.
     */
    private void createResultDialog(){
        resultDialog = new JDialog();
        resultDialog.setTitle("Result");
        resultDialog.setSize(CommonConstants.RESULT_DIALOG_SIZE);
        resultDialog.getContentPane().setBackground(CommonConstants.BACKGROUND_COLOR);
        resultDialog.setResizable(false);
        resultDialog.setLocationRelativeTo(this);
        resultDialog.setModal(true);
        resultDialog.setLayout(new GridLayout(3, 1));
        resultDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                resetGame();
            }
        });

        resultLabel = new JLabel();
        resultLabel.setForeground(Color.WHITE);
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);

        wordLabel = new JLabel();
        wordLabel.setForeground(Color.WHITE);
        wordLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton restartButton = new JButton("Restart");
        restartButton.setForeground(Color.WHITE);
        restartButton.setBackground(CommonConstants.SECONDARY_COLOR);
        restartButton.addActionListener(this);

        resultDialog.add(resultLabel);
        resultDialog.add(wordLabel);
        resultDialog.add(restartButton);
    }

    /**
     * Resets the game to its initial state.
     */
    private void resetGame(){
        // loading new challenge
        wordChallenge = wordDB.loadChallenge();
        incorrectGuesses = 0;

        // loading starting image
        CustomTools.updateImage(hangmanImage, CommonConstants.IMAGE_PATH);

        // updating category
        categoryLabel.setText(wordChallenge[0]);

        // updating hiddenWord
        String hiddenWord = CustomTools.hideWords(wordChallenge[1]);
        hiddenWordLabel.setText(hiddenWord);

        // enabling all buttons again
        for(int i = 0; i < letterButtons.length; i++){
            letterButtons[i].setEnabled(true);
            letterButtons[i].setBackground(CommonConstants.PRIMARY_COLOR);
        }
    }
}