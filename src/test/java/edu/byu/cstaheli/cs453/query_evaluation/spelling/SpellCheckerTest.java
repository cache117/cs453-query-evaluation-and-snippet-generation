package edu.byu.cstaheli.cs453.query_evaluation.spelling;

import edu.byu.cstaheli.cs453.common.util.DocumentProcessingFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SpellCheckerTest
{

    @Test
    void getSpellingSuggestions()
    {
        Set<String> spellingSuggestions = SpellChecker.getSpellingSuggestions("prisson");
        assertTrue(spellingSuggestions.contains("prison"));
        assertTrue(spellingSuggestions.contains("poison"));
        assertFalse(spellingSuggestions.contains("prisson"));
        assertFalse(spellingSuggestions.contains("priso"));

        spellingSuggestions = SpellChecker.getSpellingSuggestions("cuort");

        assertTrue(spellingSuggestions.contains("court"));
    }

    @Test
    void getWordsWithSameSoundexCode()
    {
        Set<String> spellingSuggestions = SpellChecker.getWordsWithSameSoundexCode("prisson");
        assertTrue(spellingSuggestions.contains("prison"));
    }
}