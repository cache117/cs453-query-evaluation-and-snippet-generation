package edu.byu.cstaheli.cs453.query_evaluation.snippet;

import org.junit.jupiter.api.Test;

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
    }

}