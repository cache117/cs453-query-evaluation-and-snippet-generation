package edu.byu.cstaheli.cs453.query_evaluation.log;

import edu.byu.cstaheli.cs453.common.log.Log;

/**
 * Created by cstaheli on 6/9/2017.
 */
public class QueryLog extends Log
{
    private final String anonId;
    private final String query;

    public QueryLog(String anonId, String query)
    {
        this.anonId = anonId;
        this.query = query;
    }

    public String getAnonId()
    {
        return anonId;
    }

    public String getQuery()
    {
        return query;
    }
}
