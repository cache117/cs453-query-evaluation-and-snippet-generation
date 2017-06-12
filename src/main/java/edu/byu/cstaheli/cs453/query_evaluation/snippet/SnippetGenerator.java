package edu.byu.cstaheli.cs453.query_evaluation.snippet;

import edu.byu.cstaheli.cs453.common.util.DocumentProcessingFactory;
import edu.byu.cstaheli.cs453.common.util.StopWordsRemover;
import edu.byu.cstaheli.cs453.document_ranking.index.Index;
import edu.byu.cstaheli.cs453.document_ranking.process.LineProcessor;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.sentdetect.SentenceDetector;
import opennlp.tools.util.Span;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A class that generates snippets given a query and a document id
 */
public class SnippetGenerator
{
    private final String query;
    private final int documentId;
    private Index index;

    /**
     * Creates a snippet generator from the given query and document id.
     * @param query the relevant query.
     * @param documentId the document to generate snippets from.
     */
    public SnippetGenerator(String query, int documentId)
    {
        this.query = query;
        this.documentId = documentId;
        index = DocumentProcessingFactory.getIndexInstance();
    }

    /**
     * Generates two snippets from the document that are deemed most important. These sentences will be separated by
     * an ellipses (...).
     * @return the two snippets relevant to the given query and document id.
     */
    public String generateSnippets()
    {
        try
        {
            String file = FileUtils.readFileToString(new File("src/main/resources/documents/Doc (" + documentId + ").txt"), Charset.forName("ISO-8859-1"));

            SentenceDetector detector = DocumentProcessingFactory.getSentenceDetectorInstance();
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

    /**
     * Gets the weights of each of the given sentences. The weights are the importance of each sentence with relation
     * to the query.
     * @param sentences the sentences to weight.
     * @return a map from the sentences to their weights.
     */
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

            //Required Features (minus headings, since that doesn't apply)
            double firstSentenceWeight = getSentenceNumberWeight(i + 1);
            double overlap = (double) findQueryOverlap(uniqueWordsInQuery, uniqueWordsInSentence) / sanitizedSentence.length();
            double totalOverlap = (double) findTotalQueryOverLap(uniqueWordsInQuery, sanitizedSentence) / sanitizedSentence.length();
            double sentenceSignificanceFactor = getSignificanceFactorOfSentence(sanitizedSentence.split(" "), significanceFactor);
            double longestContiguousRun = (double) getLongestContiguousRun(sanitizedSentence, uniqueWordsInQuery) / sanitizedSentence.length();

            //My own features, which are weighted less that the previous features
            double numberOfUniqueWords = (double) uniqueWordsInSentence.size() / sanitizedSentence.length() * 3;
            double numberOfCapitalizedWords = (double) getNumberOfCapitalizedWords(sentence) / sanitizedSentence.length() * 3;
            double numberOfNamedEntities = (double) getNumberOfNamedEntities(sentence) / sanitizedSentence.length() * 3;

            //Total the weights and store that
            double totalSentenceWeight = (firstSentenceWeight + overlap + totalOverlap + sentenceSignificanceFactor +
                    longestContiguousRun + numberOfUniqueWords + numberOfCapitalizedWords + numberOfNamedEntities);

            sentenceWeights.put(sentence, totalSentenceWeight);
        }
        return sentenceWeights;
    }

    /**
     * Determines how many named entities there are in the given sentence.
     * @param sentence the given sentence.
     * @return the number of named entities there are in the given sentence.
     */
    private int getNumberOfNamedEntities(String sentence)
    {
        NameFinderME nameFinder = DocumentProcessingFactory.getNameFinderInstance();
        if (nameFinder != null)
        {
            Span[] spans = nameFinder.find(sentence.split(" "));
            nameFinder.clearAdaptiveData();
            return spans.length;
        }
        else
        {
            return 0;
        }
    }

    /**
     * First and second sentences should have higher weight. This is not 0 based. If iterating, pass in the index + 1.
     *
     * @param sentenceNumber the sentence number.
     * @return 1 if sentenceNumber is 1 or 2, 0 otherwise.
     */
    private int getSentenceNumberWeight(int sentenceNumber)
    {
        return (sentenceNumber < 3) ? 1 : 0;
    }

    /**
     * Gets the number of capitalized words in the given sentence.
     * @param sentence the given sentence.
     * @return the number of capitalized words in the given sentence.
     */
    private int getNumberOfCapitalizedWords(String sentence)
    {
        int counter = 0;
        for (String word : sentence.split(" "))
        {
            if (!word.equals(word.toLowerCase()))
            {
                ++counter;
            }
        }
        return counter;
    }

    /**
     * Gets the number of the longest contiguous run of query words in the sentence.
     * @param sanitizedSentence the sentence.
     * @param uniqueWordsInQuery the set of words in the query
     * @return the number of the longest contiguous run of query words in the sentence.
     */
    int getLongestContiguousRun(String sanitizedSentence, Set<String> uniqueWordsInQuery)
    {
        int maxCount = 0, count = 0;
        for (String word : sanitizedSentence.split(" "))
        {
            if (uniqueWordsInQuery.contains(word))
            {
                ++count;
                //Save the counter if it is better
                if (count > maxCount)
                {
                    maxCount = count;
                }
            }
            else
            {
                //Reset the counter. Save it if it is better
                if (count > maxCount)
                {
                    maxCount = count;
                }
                count = 0;
            }
        }
        return maxCount;
    }

    /**
     * Determines the significance factor of the sentence. A word is considered significant if it is not a stopword and
     * it appears in the document more times than the significanceFactor threshold.
     * @param sentence the sentence to check.
     * @param significanceFactor the general significance factor to beat. This should be determined by
     *                           {@link SnippetGenerator#calculateGeneralSignificanceFactor(int)}}. This is a threshold
     *                           that must be beaten for a word in a sentence to be considered significant.
     * @return the significance factor of the sentence.
     */
    private double getSignificanceFactorOfSentence(String[] sentence, double significanceFactor)
    {
        double sentenceSignificanceFactor = 0;
        for (String word : sentence)
        {
            StopWordsRemover stopWordsRemover = DocumentProcessingFactory.getStopWordsRemoverInstance();
            if (!stopWordsRemover.contains(word))
            {
                int frequencyOfWordInDocument = index.getFrequencyOfWordInDocument(word, documentId);
                if (frequencyOfWordInDocument >= significanceFactor)
                {
                    sentenceSignificanceFactor += 1;
                }
            }
        }
        return sentenceSignificanceFactor / sentence.length;
    }

    /**
     * Finds the total number of words that overlap from the query and the sentence. This includes duplicates.
     * @param uniqueWordsInQuery the set of unique words in the query.
     * @param sentence the sentence to check.
     * @return the total number of words that overlap from the query and the sentence.
     */
    private int findTotalQueryOverLap(Set<String> uniqueWordsInQuery, String sentence)
    {
        int count = 0;
        for (String word : uniqueWordsInQuery)
        {
            count += StringUtils.countMatches(sentence, word);
        }
        return count;
    }


    /**
     * Finds the total number of distinct words that overlap from the query and the sentence. This  does not
     * include duplicates.
     * @param uniqueWordsInQuery the set of unique words in the query.
     * @param uniqueWordsInSentence the set of unique words in the sentence to check against the query.
     * @return the total number of words that overlap from the query and the sentence.
     */
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

    /**
     * Determines the threshold for a word to be considered significant. This is based on the number of sentences.
     * @param numberOfSentences the number of sentences.
     * @return the threshold for a word to be considered significant.
     */
    private double calculateGeneralSignificanceFactor(int numberOfSentences)
    {
        if (numberOfSentences < 25)
        {
            return 7d - (0.1 * (25d - numberOfSentences));
        }
        else if (25 <= numberOfSentences && numberOfSentences <= 40)
        {
            return 7d;
        }
        else
        {
            return 7d + (0.1 * (numberOfSentences - 40d));
        }
    }

    /**
     * Gets the unique words in a string.
     * @param string the given string.
     * @return the unique words in a string.
     */
    private Set<String> getUniqueWordsInString(String string)
    {
        Set<String> uniqueWords = new HashSet<>();
        uniqueWords.addAll(Arrays.asList(string.split(" ")));
        return uniqueWords;
    }
}
