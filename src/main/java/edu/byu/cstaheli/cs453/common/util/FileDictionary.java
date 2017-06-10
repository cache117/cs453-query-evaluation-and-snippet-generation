package edu.byu.cstaheli.cs453.common.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.Normalizer;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * A representation of a dictionary, containing valid spellings of words.
 */
public class FileDictionary implements Dictionary
{
    private final Set<String> dictionaryWords;

    /**
     * Creates a dictionary of all the words contained in the dictionary file. This file must be located at
     * src/main/resources/dictionary.txt or else the Dictionary will not be created.
     */
    public FileDictionary()
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
     *
     * @param line the line to sanitize.
     * @return the line without accents and apostrophes.
     */
    private static String sanitizeString(String line)
    {
        return deAccent(line.replace("\'", ""));
    }

    /**
     * Removes accents from words for simplification purposes.
     *
     * @param string the string to remove accents from.
     * @return the string with all of the accents removed
     */
    private static String deAccent(String string)
    {
        String nfdNormalizedString = Normalizer.normalize(string, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }

    @Override
    public Set<String> getDictionaryWords()
    {
        return dictionaryWords;
    }
}
