package edu.byu.cstaheli.cs453.query_evaluation.spelling;

import edu.byu.cstaheli.cs453.common.util.DocumentProcessingFactory;

import java.util.*;

/**
 * Created by cstaheli on 6/10/2017.
 */
public class SpellChecker
{
    private String originalQuery;
    private String correctedQuery;

    /**
     * Creates a SpellChecker to correct queries.
     */
    public SpellChecker()
    {

    }

    /**
     * Gets the corrected version of the query (correcting misspellings).
     *
     * @param originalQuery the original query to correct, if necessary.
     * @return the corrected version of the query.
     */
    public String correctQuery(String originalQuery)
    {
        this.originalQuery = originalQuery;
        String[] individualWords = originalQuery.split(" ");
        for (int i = 0; i < individualWords.length; i++)
        {
            Set<String> suggestions = SpellingSuggester.getSpellingSuggestions(individualWords[i]);
            individualWords[i] = getMostLikelySuggestion(suggestions, originalQuery);
        }
        this.correctedQuery = String.join(" ", individualWords);
        return this.correctedQuery;
    }

    /**
     * Gets the most likely suggestion from a set of suggestions.
     *
     * @param suggestions  the set of suggestions for the original word.
     * @param originalWord the originally typed word.
     * @return the most likely suggestion from a set of suggestions.
     */
    String getMostLikelySuggestion(Set<String> suggestions, String originalWord)
    {
        if (suggestions.size() == 1)
        {
            return new ArrayList<>(suggestions).get(0);
        }
        else
        {
            Map<String, Double> probabilities = new HashMap<>();
            for (String suggestion : suggestions)
            {
                probabilities.put(suggestion, getProbabilityOfSuggestion(suggestion, originalWord));
            }
            // Returns the item with the highest probability
            return getSuggestionWithHighestProbability(probabilities);
        }
    }

    public String getMisspelledWordFromQuery(String query)
    {
        String[] queryArray = query.split(" ");
        for (String word : queryArray)
        {
            if (!DocumentProcessingFactory.getDictionaryInstance().wordExists(word))
            {
                return word;
            }
        }
        return null;
    }

    /**
     * Gets the item with the maximum probability.
     *
     * @param probabilities a {@link Map} from words to probabilities.
     * @return the item with the maximum probability.
     */
    String getSuggestionWithHighestProbability(Map<String, Double> probabilities)
    {
        return Collections.max(probabilities.entrySet(), Map.Entry.comparingByValue())
                .getKey();
    }

    /**
     * Gets the probability of <code>suggestion</code> being the intended word when <code>originalWord</code> was entered.
     *
     * @param suggestion   the suggested word.
     * @param originalWord the original word.
     * @return the probability of the suggested word being correct.
     */
    Double getProbabilityOfSuggestion(String suggestion, String originalWord)
    {
        if (suggestion.equals(originalWord))
        {
            return 1.0;
        }
        else
        {
            return new Random().nextDouble();
        }
    }

    /**
     * Gets the corrected query.
     *
     * @return the corrected query.
     */
    public String getCorrectedQuery()
    {
        return correctedQuery;
    }

    /**
     * Gets the original query.
     *
     * @return the original query.
     */
    public String getOriginalQuery()
    {
        return originalQuery;
    }
}
