package edu.byu.cstaheli.cs453.query_evaluation.spelling;

import edu.byu.cstaheli.cs453.common.util.Dictionary;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by cstaheli on 6/9/2017.
 */
public class SpellChecker
{
    public static Set<String> getSpellingSuggestions(String word)
    {
        if (Dictionary.getInstance().wordExists(word))
        {
            return Collections.singleton(word);
        }
        Set<String> wordsWithTwoEditDistance = getWordsWithTwoEditDistance(word);
        Set<String> sameSoundexCode = getWordsWithSameSoundexCode(word);
        Set<String> spellingSuggestions = new HashSet<>(wordsWithTwoEditDistance);
        //spellingSuggestions.addAll(sameSoundexCode);

        return spellingSuggestions;
    }

    public static Set<String> getWordsWithSameSoundexCode(String word)
    {
        Set<String> dictionaryWords = Dictionary.getInstance().getDictionaryWords();
        return dictionaryWords
                .stream()
                .filter(s -> SoundexCode.wordsHaveSameEncoding(s, word))
                .collect(Collectors.toSet());
    }

    public static Set<String> getWordsWithTwoEditDistance(String word, Set<String> words)
    {
        return words
                .stream()
                .filter(s -> new LevenshteinDistance(s, word).calculateDistance() <= 2)
                .collect(Collectors.toSet());
    }

    public static Set<String> getWordsWithTwoEditDistance(String word)
    {
        return getWordsWithTwoEditDistance(word, Dictionary.getInstance().getDictionaryWords());
    }
}

