package edu.byu.cstaheli.cs453.query_evaluation;

import edu.byu.cstaheli.cs453.document_ranking.index.Index;
import edu.byu.cstaheli.cs453.document_ranking.index.IndexEntry;
import edu.byu.cstaheli.cs453.document_ranking.process.DocumentProcessor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by cstaheli on 6/5/2017.
 */
public class Driver
{
    private Index index;

    public Driver()
    {
        index = Index.getInstance();
    }

    public static void main(String[] args)
    {
        Driver driver = new Driver();
        driver.readInCorpus("src/main/resources");
        String[] query = {
                //"movi action",
                "sentenced to prision",
                "open cuort case",
                "entretainment group",
                "tv axtor",
                "movie",
                "scheduled movie screning",
        };
        for (String queryString : query)
        {

        }
    }

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
}
