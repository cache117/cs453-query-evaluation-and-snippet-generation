package edu.byu.cstaheli.cs453.document_ranking.process;

import com.sun.istack.internal.Nullable;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Contains functionality to parse Wiki documents into a map of strings and the frequency of those words.
 */
public class DocumentProcessor
{
    private Map<String, Integer> wordCounts;

    /**
     * Creates a {@link DocumentProcessor} from a file.
     *
     * @param fileName the name of the file to process.
     * @see LineProcessor
     */
    public DocumentProcessor(String fileName)
    {
        wordCounts = new TreeMap<>();
        try
        {
            readFile(fileName)
                    .stream()
                    .filter(line -> line.length() > 0)
                    .forEach(line -> addToWordCounts(new LineProcessor(line).getProcessedWords()));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Reads the file into a list of strings, representing each line.
     *
     * @param fileName the name of the file to read.
     * @return a list of the lines in the file.
     * @throws IOException if an I/O error occurs reading from the file or a malformed or unmappable byte sequence
     *                     is read
     */
    private List<String> readFile(String fileName) throws IOException
    {
        //A lot of the files aren't encoded with UTF-8
        return Files.readAllLines(Paths.get(fileName), Charset.forName("ISO-8859-1"));
    }

    /**
     * Gets the count of the word in the processed file.
     * @param word the word to get the count of.
     * @return the count of the word in the processed file.
     */
    @Nullable
    public Integer getCount(String word)
    {
        return wordCounts.get(word);
    }

    /**
     * Gets all of the word counts for all words in the processed file.
     * @return all of the word counts for all words in the processed file.
     */
    public Map<String, Integer> getWordCounts()
    {
        return wordCounts;
    }

    /**
     * Adds words to the processor. These will be stored and tallied as necessary.
     * @param words the new words to add.
     */
    private void addToWordCounts(List<String> words)
    {
        for (String word : words)
        {
            Integer count = wordCounts.get(word);
            wordCounts.put(word, (count == null) ? 1 : count + 1);
        }
    }
}
