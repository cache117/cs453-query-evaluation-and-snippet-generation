package edu.byu.cstaheli.cs453.query_evaluation.spelling;

import edu.byu.cstaheli.cs453.common.util.DocumentProcessingFactory;
import edu.byu.cstaheli.cs453.common.util.FileDictionary;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Used to get spelling suggestions for a word.
 */
public class SpellingSuggester
{
    /**
     * Gets all of the possible spelling suggestions for <code>word</code>, using edit distance and SoundexCodes.
     *
     * @param word the word to check.
     * @return valid spelling suggestions.
     */
    static Set<String> getSpellingSuggestions(String word)
    {
        if (DocumentProcessingFactory.getDictionaryInstance().wordExists(word))
        {
            return Collections.singleton(word);
        }
        Set<String> wordsWithTwoEditDistance = getWordsWithTwoEditDistance(word);
        Set<String> sameSoundexCode = getWordsWithSameSoundexCode(word);
        Set<String> spellingSuggestions = new HashSet<>(wordsWithTwoEditDistance);
        //spellingSuggestions.addAll(sameSoundexCode);

        return spellingSuggestions;
    }

    /**
     * Determines which words from the dictionary have the same soundex code as <code>word</code>.
     *
     * @param word the word to check against.
     * @return the words that have the same soundex code.
     * @see FileDictionary#getDictionaryWords()
     * @see SoundexCode#wordsHaveSameEncoding(String, String)
     */
    static Set<String> getWordsWithSameSoundexCode(String word)
    {
        return getWordsWithSameSoundexCode(word, DocumentProcessingFactory.getDictionaryInstance().getDictionaryWords());
    }

    /**
     * Determines which words from <code>words</code> have the same soundex code as <code>word</code>.
     *
     * @param word  the word to check against.
     * @param words the set of words to compare.
     * @return the words that have the same soundex code.
     * @see SoundexCode#wordsHaveSameEncoding(String, String)
     */
    static Set<String> getWordsWithSameSoundexCode(String word, Set<String> words)
    {
        return words
                .stream()
                .filter(dictionaryWord -> SoundexCode.wordsHaveSameEncoding(dictionaryWord, word))
                .collect(Collectors.toSet());
    }

    /**
     * Determines which words from <code>words</code> have edit distance 2 from <code>word</code>.
     *
     * @param word  the word to check against.
     * @param words the set of words to compare.
     * @return the words that have edit distance two.
     * @see LevenshteinDistance#calculateDistance()
     */
    static Set<String> getWordsWithTwoEditDistance(String word, Set<String> words)
    {
        return words
                .stream()
                .filter(dictionaryWord -> new LevenshteinDistance(dictionaryWord, word).calculateDistance() <= 2)
                .collect(Collectors.toSet());
    }

    /**
     * Determines which words from the dictionary have edit distance 2 from <code>word</code>.
     *
     * @param word the word to check against.
     * @return the words that have edit distance two.
     * @see FileDictionary#getDictionaryWords()
     * @see LevenshteinDistance#calculateDistance()
     */
    static Set<String> getWordsWithTwoEditDistance(String word)
    {
        return getWordsWithTwoEditDistance(word, DocumentProcessingFactory.getDictionaryInstance().getDictionaryWords());
    }
}

