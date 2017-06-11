package edu.byu.cstaheli.cs453.query_evaluation.spelling;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SpellCheckerTest
{
    private SpellChecker spellChecker;

    @BeforeEach
    void setUp()
    {
        spellChecker = new SpellChecker();
    }

    @Test
    void getMostLikelySuggestion()
    {
        Set<String> suggestions = Collections.singleton("suggestion");
        String mostLikelySuggestion = spellChecker.getMostLikelySuggestion(suggestions, "suggesstion");
        assertEquals("suggestion", mostLikelySuggestion);

        suggestions = new HashSet<>(3);
        suggestions.add("potato");
        suggestions.add("potatoes");
        suggestions.add("pot");
        mostLikelySuggestion = spellChecker.getMostLikelySuggestion(suggestions, "potatoe");
        assertEquals("something", mostLikelySuggestion);

    }

    @Test
    void getSuggestionWithHighestProbability()
    {
    }

    @Test
    void getProbabilityOfSuggestion()
    {
        double probability = spellChecker.getProbabilityOfSuggestion("suggestion", "suggestion");
        assertEquals(1.0, probability);


    }

    @Test
    void getCorrectedQuery()
    {
    }

}