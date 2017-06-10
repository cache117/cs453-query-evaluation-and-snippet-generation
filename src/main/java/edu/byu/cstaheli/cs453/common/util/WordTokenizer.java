package edu.byu.cstaheli.cs453.common.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements functionality useful for tokenizing a file.
 */
public class WordTokenizer
{
    private List<String> tokens;

    /**
     * Creates a {@link WordTokenizer} from a line.
     * @param line the line to tokenize.
     */
    public WordTokenizer(String line)
    {
        tokens = new ArrayList<>();
        parseLine(line);
    }

    /**
     * Parses the line into tokens.
     * @param line the line to tokenize.
     */
    private void parseLine(String line)
    {
        String[] words = line.split("\\s+");
        for (String word : words)
        {
            if (word.indexOf('-') != -1)
            {
                parseHyphenatedWord(word);
            }
            else
            {
                parseWord(word);
            }
        }
    }

    /**
     * Parses an individual word into a token. This will strip unwanted characters and santize it.
     * @param word the word to tokenize.
     */
    private void parseWord(String word)
    {
        word = stripExtraCharacters(word);
        //words with numbers shouldn't be added
        if (!wordContainsNumbers(word))
        {
            tokens.add(word.toLowerCase());
        }
    }

    /**
     * Determines if the word contains numbers.
     * @param word the word to check.
     * @return true if the word contains numbers, false otherwise.
     */
    private boolean wordContainsNumbers(String word)
    {
        return word.matches(".*\\d+.*");
    }

    /**
     * Strips the extra characters form the word. These will be any non-word characters.
     * @param word the word to remove the extra characters from.
     * @return the word without the extra characters.
     */
    private String stripExtraCharacters(String word)
    {
        return word.replaceAll("[^\\w]+", "");
    }

    /**
     * Parses a hyphenated word into either two words or one. This depends on if the combined word is a word in the
     * dictionary.
     * @param word the word with one or more hyphens in it.
     */
    private void parseHyphenatedWord(String word)
    {
        String[] hyphenatedWords = word.split("-");
        for (int i = 0; i < hyphenatedWords.length; ++i)
        {
            hyphenatedWords[i] = stripExtraCharacters(hyphenatedWords[i]);
        }
        String concatenatedWord = concatenateHyphenatedWords(hyphenatedWords);
        if (Dictionary.getInstance().wordExists(concatenatedWord))
        {
            parseWord(concatenatedWord);
        }
        else
        {
            for (String hyphenatedWord : hyphenatedWords)
            {
                parseWord(hyphenatedWord);
            }
        }
    }

    /**
     * Concatenates a list of hyphenated words into one word. This won't necessarily be a real word, but might.
     * @param hyphenatedWords the list of hyphenated words to concatenate.
     * @return the concatenated word of all of the hyphenated words.
     */
    private String concatenateHyphenatedWords(String[] hyphenatedWords)
    {
        StringBuilder concatenatedWordBuilder = new StringBuilder();
        for (String hyphenatedWord : hyphenatedWords)
        {
            concatenatedWordBuilder.append(hyphenatedWord);
        }
        return concatenatedWordBuilder.toString();
    }

    /**
     * Gets all of the sanitized tokens/words.
     * @return the sanitized tokens/words.
     */
    public List<String> getWords()
    {
        return tokens;
    }
}
