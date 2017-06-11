package edu.byu.cstaheli.cs453.common.log;

/**
 * A representation of an entry in a log.
 */
public class QueryLog
{
    protected final String sessionId;
    private final String query;

    public QueryLog(String sessionId, String query)
    {
        this.query = query;
        this.sessionId = sessionId;
    }

    public boolean queryContainsWord(String word)
    {
        return query.contains(word);
    }

    /**
     * Gets the query the person typed.
     *
     * @return the query the person typed.
     */
    public String getQuery()
    {
        return query;
    }

    /**
     * Gets the session Id of the Query log.
     * @return the session Id of the Query log.
     */
    public String getSessionId()
    {
        return sessionId;
    }

    public boolean sameExceptIndex(int index, String other)
    {
        String[] thisQuery = this.query.split(" ");
        String[] otherQuery = other.split(" ");
        if (thisQuery.length != otherQuery.length) return false;
        for (int i = 0; i < thisQuery.length; ++i)
        {
            if (i != index)
            {
                if (!thisQuery[i].equals(otherQuery[i]))
                {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QueryLog queryLog = (QueryLog) o;

        if (!sessionId.equals(queryLog.sessionId)) return false;
        return query.equals(queryLog.query);
    }

    @Override
    public int hashCode()
    {
        int result = sessionId.hashCode();
        result = 31 * result + query.hashCode();
        return result;
    }
}
