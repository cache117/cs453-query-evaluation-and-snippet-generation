package edu.byu.cstaheli.cs453.query_evaluation;

import edu.byu.cstaheli.cs453.common.util.DocumentProcessingFactory;
import edu.byu.cstaheli.cs453.document_ranking.index.Index;
import edu.byu.cstaheli.cs453.document_ranking.index.IndexEntry;
import edu.byu.cstaheli.cs453.document_ranking.process.DocumentProcessor;
import edu.byu.cstaheli.cs453.document_ranking.query.Query;
import edu.byu.cstaheli.cs453.document_ranking.query.QueryResult;
import edu.byu.cstaheli.cs453.query_evaluation.snippet.SnippetGenerator;
import edu.byu.cstaheli.cs453.query_evaluation.spelling.SoundexCode;
import edu.byu.cstaheli.cs453.query_evaluation.spelling.SpellChecker;
import edu.byu.cstaheli.cs453.query_evaluation.spelling.SpellingSuggester;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Stream;

/**
 * The Driver for Project 3.
 */
public class Driver
{
    private Index index;

    public Driver(Index index)
    {
        setIndex(index);
    }

    public static void main(String[] args)
    {
        Driver driver = new Driver(DocumentProcessingFactory.getIndexInstance());
        driver.readInCorpus("src/main/resources/documents");
        String[] queries = {
                //"movi action",
                "sentenced to prision",
                "open cuort case",
                "entretainment group",
                "tv axtor",
                "movie",
                "scheduled movie screning",
        };
        SpellChecker spellChecker = new SpellChecker();
        for (String query : queries)
        {
            String correctedQuery = spellChecker.correctQuery(query);
            SortedSet<QueryResult> results = driver.runQuery(correctedQuery);
            String misspelledWord = spellChecker.getMisspelledWordFromQuery(query);
            driver.handleResults(query, misspelledWord, correctedQuery, results);
        }
    }

    private void handleResults(String originalQuery, String misspelledWord, String correctedQuery, SortedSet<QueryResult> results)
    {
        System.out.format("Original Query: %s\tCorrectedQuery: %s\n", originalQuery, correctedQuery);
        String soundexCode = SoundexCode.encode(misspelledWord);
        Set<String> suggestions = SpellingSuggester.getSpellingSuggestions(misspelledWord);
        System.out.format("Suggested Corrections: %s\n", String.join(", ", suggestions));
        for (QueryResult result : results)
        {
            int documentId = result.getDocumentId();
            System.out.format("Doc: %s\n%s", documentId, new SnippetGenerator(correctedQuery, documentId).generateSnippets());
        }
    }

    private SortedSet<QueryResult> runQuery(String queryString)
    {
        Query query = new Query(queryString);
        return query.getResults();
    }

    /**
     * Reads in all of the files from the corpus located in the given director.
     *
     * @param directory the directory to read in from.
     */
    public void readInCorpus(String directory)
    {
        try (Stream<Path> paths = Files.walk(Paths.get(directory)))
        {
            paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".txt"))
                    .filter(path -> path.toString().contains("Doc"))
                    .forEach(filePath ->
                    {
                        String fileName = filePath.toString();
                        DocumentProcessor documentProcessor = new DocumentProcessor(fileName);

                        Map<String, Integer> wordCounts = documentProcessor.getWordCounts();
                        for (Map.Entry<String, Integer> entry : wordCounts.entrySet())
                        {
                            IndexEntry indexEntry = new IndexEntry(entry.getKey(), entry.getValue(), Integer.parseInt(fileName.replaceAll("[^0-9]", "")));
                            index.addEntry(indexEntry);
                        }
                    });
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public Index getIndex()
    {
        return index;
    }

    public void setIndex(Index index)
    {
        this.index = index;
    }
}
