package edu.byu.cstaheli.cs453.document_ranking.process;

import edu.byu.cstaheli.cs453.common.util.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains functionality to parse lines into a list of strings. This sanitizes the words and strips out the non-words,
 * stopwords, and stems the words.
 * @see WordTokenizer
 * @see StopWordsRemover
 * @see PorterStemmer
 */
public class LineProcessor
{
    private final List<String> processedWords;
    private StopWordsRemover stopWordsRemover;
    /**
     * Creates a Line processor from a line.
     * @param line the line to process.
     * @see WordTokenizer
     * @see StopWordsRemover
     * @see PorterStemmer
     */
    public LineProcessor(String line)
    {
        this(DocumentProcessingFactory.getStopWordsRemoverInstance(), line);
    }

    public LineProcessor(StopWordsRemover stopWordsRemover, String line)
    {
        this(stopWordsRemover, line, true, true);
    }

    public LineProcessor(StopWordsRemover stopWordsRemover, String line, boolean removeStopwords, boolean stemWords)
    {
        setStopWordsRemover(stopWordsRemover);
        List<String> words = new WordTokenizer(line).getWords();

        List<String> nonStopwords = (removeStopwords) ? removeStopwords(words) : words;
        List<String> stemmedWords = (stemWords) ? stemWords(nonStopwords) : nonStopwords;
        processedWords = stemmedWords;
    }

    public LineProcessor(String line, boolean removeStopwords, boolean stemWords)
    {
        this(DocumentProcessingFactory.getStopWordsRemoverInstance(), line, removeStopwords, stemWords);
    }

    public void setStopWordsRemover(StopWordsRemover stopWordsRemover)
    {
        this.stopWordsRemover = stopWordsRemover;
    }

    /**
     * Gets the processed words that were processed from the line.
     * @return the processed words that were processed from the line.
     */
    public List<String> getProcessedWords()
    {
        return processedWords;
    }

    /**
     * Stems the given words and returns them.
     * @param words the words to them.
     * @return the stemmed versions of the given words.
     * @see PorterStemmer
     */
    private List<String> stemWords(List<String> words)
    {
        List<String> stemmedWords = new ArrayList<>();
        for (String word : words)
        {
            String stemmedWord = new PorterStemmer().stem(word);
            //Determine if word was actually stemmed
            if (!"Invalid term".equals(stemmedWord) && !"No term entered".equals(stemmedWord))
            {
                stemmedWords.add(stemmedWord);
            }
        }
        return stemmedWords;
    }

    /**
     * Removes the stopwords from the list of words.
     * @param words the words to remove stopwords from.
     * @return a list without stopwords in it.
     * @see StopWordsRemover
     */
    private List<String> removeStopwords(List<String> words)
    {
        List<String> nonStopwords = new ArrayList<>();
        for (String word : words)
        {
            if (!stopWordsRemover.contains(word))
            {
                nonStopwords.add(word);
            }
        }
        return nonStopwords;
    }
}
