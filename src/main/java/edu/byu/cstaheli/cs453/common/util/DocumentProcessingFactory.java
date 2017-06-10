package edu.byu.cstaheli.cs453.common.util;

import edu.byu.cstaheli.cs453.document_ranking.index.DocumentIndex;
import edu.byu.cstaheli.cs453.document_ranking.index.Index;

/**
 * Created by cstaheli on 6/10/2017.
 */
public class DocumentProcessingFactory
{
    private static Index indexInstance;
    private static Dictionary dictionaryInstance;

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

    private static StopWordsRemover stopWordsRemoverInstance;

    /**
     * Gets the instance of stop words, since there shouldn't be more than one.
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
}
