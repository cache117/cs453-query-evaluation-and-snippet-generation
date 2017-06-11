package edu.byu.cstaheli.cs453.query_evaluation.spelling;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SpellingSuggesterTest
{

    @Test
    void getSpellingSuggestions()
    {
        Set<String> spellingSuggestions = SpellingSuggester.getSpellingSuggestions("prisson");
        assertTrue(spellingSuggestions.contains("prison"));
        assertTrue(spellingSuggestions.contains("poison"));
        assertFalse(spellingSuggestions.contains("prisson"));
        assertFalse(spellingSuggestions.contains("priso"));

        spellingSuggestions = SpellingSuggester.getSpellingSuggestions("cuort");

        assertTrue(spellingSuggestions.contains("court"));
    }

    @Test
    void getWordsWithSameSoundexCode()
    {
        Set<String> spellingSuggestions = SpellingSuggester.getWordsWithSameSoundexCode("prisson");
        assertTrue(spellingSuggestions.contains("prison"));
    }
}