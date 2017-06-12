package edu.byu.cstaheli.cs453.common.util;

import edu.byu.cstaheli.cs453.document_ranking.index.DocumentIndex;
import edu.byu.cstaheli.cs453.document_ranking.index.Index;
import edu.byu.cstaheli.cs453.query_evaluation.log.SpellingLogParser;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Holds instances of the classes that should only have one instance.
 */
public class DocumentProcessingFactory
{
    private static Index indexInstance;
    private static Dictionary dictionaryInstance;
    private static StopWordsRemover stopWordsRemoverInstance;
    private static SpellingLogParser spellingLogParserInstance;
    private static NameFinderME nameFinderInstance;

    /**
     * Gets the one instance to the Index, since there is a bit of overhead in creating the Index.
     *
     * @return the instance of the Index.
     */
    public static Index getIndexInstance()
    {
        if (indexInstance == null)
        {
            indexInstance = new DocumentIndex();
        }
        return indexInstance;
    }

    public static void setIndexInstance(Index index)
    {
        indexInstance = index;
    }

    /**
     * Gets an instance of the Dictionary, since creating one is a bit of an overhead, and all instances should
     * be the same.
     *
     * @return an instance of the Dictionary.
     */
    public static Dictionary getDictionaryInstance()
    {
        if (dictionaryInstance == null)
        {
            dictionaryInstance = new FileDictionary();
        }
        return dictionaryInstance;
    }

    public static void setDictionaryInstance(Dictionary dictionary)
    {
        dictionaryInstance = dictionary;
    }

    /**
     * Gets the instance of stop words, since there shouldn't be more than one.
     *
     * @return the instance of stop words
     */
    public static StopWordsRemover getStopWordsRemoverInstance()
    {
        if (stopWordsRemoverInstance == null)
        {
            stopWordsRemoverInstance = new StopWordsRemover();
        }
        return stopWordsRemoverInstance;
    }

    /**
     * Gets the instance of spelling log parser, since there shouldn't be more than one.
     *
     * @return the instance of spelling log parser.
     */
    public static SpellingLogParser getSpellingLogParserInstance()
    {
        if (spellingLogParserInstance == null)
        {
            spellingLogParserInstance = new SpellingLogParser("src/main/resources/query_log.txt");
        }
        return spellingLogParserInstance;
    }

    public static NameFinderME getNameFinderInstance()
    {
        if (nameFinderInstance == null)
        {
            try
            {
                InputStream modelIn = new FileInputStream("src/main/resources/en-ner-person.bin");
                TokenNameFinderModel model = new TokenNameFinderModel(modelIn);
                nameFinderInstance = new NameFinderME(model);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return nameFinderInstance;
    }
}
