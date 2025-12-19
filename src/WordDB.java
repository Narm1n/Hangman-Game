import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

/**
 * The WordDB class represents a database of words categorized by different categories.
 * It provides methods to load words randomly from the database.
 */
public class WordDB {
    // key -> category, value -> words
    private HashMap<String, String[]> wordList;

    // used for picking random categories (It is not possible to get index with HashMap)
    private ArrayList<String> categories;

    /**
     * Constructs a new WordDB object and initializes the word list and categories.
     * Reads data from a file and populates the word list and categories.
     */
    public WordDB() {
        wordList = new HashMap<>();
        categories = new ArrayList<>();
        
        // reading data from file line by line -> BufferedReader
        try (BufferedReader reader = new BufferedReader(new FileReader(getClass().getClassLoader().getResource(CommonConstants.DATA_PATH).getPath()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String category = parts[0];
                categories.add(category);
                String[] values = Arrays.copyOfRange(parts, 1, parts.length);
                wordList.put(category, values);
            }
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    /**
     * Loads a random word challenge from the word database.
     * 
     * @return An array containing the category and the word chosen randomly.
     */
    public String[] loadChallenge(){
        Random rand = new Random();

        // generating random number to choose category
        String category = categories.get(rand.nextInt(categories.size()));

        // generating random number to choose the value from category
        String[] categoryValues = wordList.get(category);
        String word = categoryValues[rand.nextInt(categoryValues.length)];

        // [0] -> category and [1] -> word
        return new String[]{category.toUpperCase(), word.toUpperCase()};
    }
}

