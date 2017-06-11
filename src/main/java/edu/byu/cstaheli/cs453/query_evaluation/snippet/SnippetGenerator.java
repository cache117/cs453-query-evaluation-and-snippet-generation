package edu.byu.cstaheli.cs453.query_evaluation.snippet;

import opennlp.tools.sentdetect.NewlineSentenceDetector;
import opennlp.tools.sentdetect.SentenceDetector;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by cstaheli on 6/11/2017.
 */
public class SnippetGenerator
{
    private final String query;
    private final int documentId;

    public SnippetGenerator(String query, int documentId)
    {
        this.query = query;
        this.documentId = documentId;
    }

    public String generateSnippets()
    {
        try
        {
            String file = FileUtils.readFileToString(new File("src/main/resources/documents/Doc (" + documentId + ").txt"), Charset.forName("ISO-8859-1"));

            SentenceDetector detector = new SentenceDetectorME(new SentenceModel(new File("src/main/resources/en-sent.bin")));
            String[] sentences = detector.sentDetect(file);
            return "";
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return "";
    }
}
