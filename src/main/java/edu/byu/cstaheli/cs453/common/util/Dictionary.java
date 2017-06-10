package edu.byu.cstaheli.cs453.common.util;

import java.io.*;
import java.nio.charset.Charset;
import java.text.Normalizer;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * A representation of a dictionary, containing valid spellings of words.
 */
public class Dictionary
{
    private final Set<String> dictionaryWords;
    private static Dictionary _instance;

    /**
     * Gets an instance of the Dictionary, since creating one is a bit of an overhead, and all instances should
     * be the same.
     * @return an instance of the Dictionary.
     */
    public static Dictionary getInstance()
    {
        if (_instance == null)
        {
            _instance = new Dictionary();
        }
        return _instance;
    }

    /**
     * Creates a dictionary of all the words contained in the dictionary file. This file must be located at
     * src/main/resources/dictionary.txt or else the Dictionary will not be created.
     */
    private Dictionary()
    {
        dictionaryWords = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/resources/dictionary.txt"), Charset.forName("ISO-8859-1"))))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                //Remove ' from words
                dictionaryWords.add(sanitizeString(line));
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Sanitizes a line by removing accents and apostrophes.
     * @param line the line to sanitize.
     * @return the line without accents and apostrophes.
     */
    private static String sanitizeString(String line)
    {
        return deAccent(line.replace("\'", ""));
    }

    /**
     * Removes accents from words for simplification purposes.
     * @param string the string to remove accents from.
     * @return the string with all of the accents removed
     */
    private static String deAccent(String string) {
        String nfdNormalizedString = Normalizer.normalize(string, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }

    /**
     * Determines if the given word exists in the dictionary, meaning that it is a correctly spelled word.
     * @param word the word to check.
     * @return true if the word is found in the dictionary, false otherwise.
     */
    public boolean wordExists(String word)
    {
        return dictionaryWords.contains(word);
    }

    /**
     * Gets all of the words in the dictionary.
     * @return all of the words in the dictionary.
     */
    public Set<String> getDictionaryWords()
    {
        return dictionaryWords;
    }
}
