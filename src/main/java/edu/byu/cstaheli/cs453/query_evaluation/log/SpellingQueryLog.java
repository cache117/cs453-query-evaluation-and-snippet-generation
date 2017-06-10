package edu.byu.cstaheli.cs453.query_evaluation.log;

import edu.byu.cstaheli.cs453.common.log.Log;

/**
 * A representation of a Spelling Query Log.
 */
public class SpellingQueryLog extends Log
{
    private final String anonId;
    private final String query;

    /**
     * Creates the log from it's constituent parts.
     * @param anonId the id of the person who wrote the log.
     * @param query the query the person typed. This is probably misspelled.
     */
    public SpellingQueryLog(String anonId, String query)
    {
        this.anonId = anonId;
        this.query = query;
    }

    /**
     * Gets the id of the person who wrote the log.
     * @return the id of the person who wrote the log.
     */
    public String getAnonId()
    {
        return anonId;
    }

    /**
     * Gets the query the person typed.
     * @return the query the person typed.
     */
    public String getQuery()
    {
        return query;
    }
}
