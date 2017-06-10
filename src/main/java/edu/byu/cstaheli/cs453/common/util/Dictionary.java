package edu.byu.cstaheli.cs453.common.util;

import java.util.Set;

/**
 * Created by cstaheli on 6/10/2017.
 */
public interface Dictionary
{
    /**
     * Determines if the given word exists in the dictionary, meaning that it is a correctly spelled word.
     * @param word the word to check.
     * @return true if the word is found in the dictionary, false otherwise.
     */
    default boolean wordExists(String word)
    {
        return getDictionaryWords().contains(word);
    }

    /**
     * Gets all of the words in the dictionary.
     * @return all of the words in the dictionary.
     */
    Set<String> getDictionaryWords();
}
