package edu.byu.cstaheli.cs453.query_evaluation.snippet;

import com.google.common.collect.Ordering;
import edu.byu.cstaheli.cs453.common.util.DocumentProcessingFactory;
import edu.byu.cstaheli.cs453.document_ranking.index.Index;
import edu.byu.cstaheli.cs453.document_ranking.process.LineProcessor;
import opennlp.tools.sentdetect.SentenceDetector;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by cstaheli on 6/11/2017.
 */
public class SnippetGenerator
{
    private final String query;
    private final int documentId;
    private Index index;

    public SnippetGenerator(String query, int documentId)
    {
        this.query = query;
        this.documentId = documentId;
        index = DocumentProcessingFactory.getIndexInstance();
    }

    public String generateSnippets()
    {
        try
        {
            String file = FileUtils.readFileToString(new File("src/main/resources/documents/Doc (" + documentId + ").txt"), Charset.forName("ISO-8859-1"));

            SentenceDetector detector = new SentenceDetectorME(new SentenceModel(new File("src/main/resources/en-sent.bin")));
            String[] sentences = detector.sentDetect(file);
            if (sentences.length == 1)
            {
                return sentences[0];
            }
            else
            {
                Map<String, Double> sentenceWeights = getSentenceWeights(sentences);
                List<String> snippets = sentenceWeights
                        .entrySet()
                        .stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .limit(2)
                        .map(Map.Entry::getKey)
                        .collect(Collectors.toList());

                return snippets.get(0) + " ... " + snippets.get(1);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return "";
    }

    private Map<String, Double> getSentenceWeights(String[] sentences)
    {
        Map<String, Double> sentenceWeights = new HashMap<>(sentences.length);
        int numberOfSentences = sentences.length;
        double significanceFactor = calculateGeneralSignificanceFactor(numberOfSentences);
        Set<String> uniqueWordsInQuery = getUniqueWordsInString(query);
        for (int i = 0; i < sentences.length; i++)
        {
            String sentence = sentences[i];
            String sanitizedSentence = String.join(" ", new LineProcessor(sentence, true, false).getProcessedWords());
            Set<String> uniqueWordsInSentence = getUniqueWordsInString(sanitizedSentence);
            //First or second sentence
            double firstSentenceWeight = (i < 3) ? 1.0 : 0;
            double overlap = (double) findQueryOverlap(uniqueWordsInQuery, uniqueWordsInSentence) / sanitizedSentence.length();
            double totalOverlap = (double) findTotalQueryOverLap(uniqueWordsInQuery, sanitizedSentence) / sanitizedSentence.length();
            double sentenceSignificanceFactor = getSignificanceFactorOfSentence(sanitizedSentence.split(" "), significanceFactor);
            double totalSentenceWeight = (firstSentenceWeight + overlap + totalOverlap + sentenceSignificanceFactor) / 4;
            sentenceWeights.put(sentence, totalSentenceWeight);
        }
        return sentenceWeights;
    }

    private double getSignificanceFactorOfSentence(String[] sentence, double significanceFactor)
    {
        double sentenceSignificanceFactor = 0;
        for (String word : sentence)
        {
            int frequencyOfWordInDocument = index.getFrequencyOfWordInDocument(word, documentId);
            if (frequencyOfWordInDocument >= significanceFactor)
            {
                sentenceSignificanceFactor += 1;
            }
        }
        return sentenceSignificanceFactor / sentence.length;
    }

    private int findTotalQueryOverLap(Set<String> uniqueWordsInQuery, String sentence)
    {
        int count = 0;
        for (String word : uniqueWordsInQuery)
        {
            count += StringUtils.countMatches(sentence, word);
        }
        return count;
    }

    private int findQueryOverlap(Set<String> uniqueWordsInQuery, Set<String> uniqueWordsInSentence)
    {
        int counter = 0;
        for (String word : uniqueWordsInQuery)
        {
            if (uniqueWordsInSentence.contains(word))
            {
                ++counter;
            }
        }
        return counter;
    }

    private double calculateGeneralSignificanceFactor(int numberOfSentences)
    {
        if (numberOfSentences < 25)
        {
            return 7d - (0.1 * (25d - numberOfSentences));
        }
        else if (numberOfSentences >= 25 && numberOfSentences <= 40)
        {
            return 7d;
        }
        else
        {
            return 7d + (0.1 * (numberOfSentences - 40d));
        }
    }

    private Set<String> getUniqueWordsInString(String string)
    {
        Set<String> uniqueWords = new HashSet<>();
        uniqueWords.addAll(Arrays.asList(string.split(" ")));
        return uniqueWords;
    }
}
