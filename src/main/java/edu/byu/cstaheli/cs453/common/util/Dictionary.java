package edu.byu.cstaheli.cs453.common.util;

import java.io.*;
import java.nio.charset.Charset;
import java.text.Normalizer;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by cstaheli on 5/8/2017.
 */
public class Dictionary
{
    private Set<String> dictionaryWords;
    private static Dictionary _instance;

    public static Dictionary getInstance()
    {
        if (_instance == null)
        {
            _instance = new Dictionary();
        }
        return _instance;
    }

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

    private static String sanitizeString(String line)
    {
        return deAccent(line.replace("\'", ""));
    }

    private static String deAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }

    public boolean wordExists(String word)
    {
        return dictionaryWords.contains(word);
    }

    public Set<String> getDictionaryWords()
    {
        return dictionaryWords;
    }
}
