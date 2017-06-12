package edu.byu.cstaheli.cs453.query_evaluation.snippet;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by cstaheli on 6/11/2017.
 */
class SnippetGeneratorTest
{
    @Test
    void generateSnippets()
    {
        SnippetGenerator snippetGenerator = new SnippetGenerator("abuse", 2);
        String snippets = snippetGenerator.generateSnippets();
        assertTrue(snippets.contains("abuse"));

    }

    @Test
    void testLongestContiguousRun()
    {
        SnippetGenerator snippetGenerator = new SnippetGenerator("abuse", 2);
        Set<String> queryWords = new HashSet<>(4);
        queryWords.add("potatoes");
        queryWords.add("are");
        queryWords.add("good");
        queryWords.add("you");
        int longestContiguousRun = snippetGenerator.getLongestContiguousRun("potatoes are good for you", queryWords);
        assertEquals(3, longestContiguousRun);

        longestContiguousRun = snippetGenerator.getLongestContiguousRun("potatoes are good for potatoes are good you", queryWords);
        assertEquals(4, longestContiguousRun);
    }
}