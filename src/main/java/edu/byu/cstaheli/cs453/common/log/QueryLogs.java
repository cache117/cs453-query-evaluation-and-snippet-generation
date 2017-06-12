package edu.byu.cstaheli.cs453.common.log;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A representation of a list of QueryLog.
 */
public class QueryLogs
{
    private List<QueryLog> logs;

    /**
     * Creates a QueryLogs with the given logs. This can be empty, but must not be null.
     * @param logs the existing logs to add.
     */
    public QueryLogs(List<QueryLog> logs)
    {
        this.logs = logs;
    }

    /**
     * Adds a log to the logs.
     * @param log the log to add.
     */
    public void add(QueryLog log)
    {
        logs.add(log);
    }

    /**
     * Gets a log from the logs.
     * @param index the index of the log to get.
     * @return the log at the given index.
     */
    public QueryLog get(int index)
    {
        return logs.get(index);
    }

    /**
     * Gets the number of query logs.
     * @return the number of query logs.
     */
    public int size()
    {
        return logs.size();
    }

    /**
     * Gets the query logs that contain the given word at some point in their query.
     * @param word the given word.
     * @return the query logs that contain the given word.
     */
    public List<QueryLog> getQueryLogsThatContainWord(String word)
    {
        return logs
                .stream()
                .filter(queryLog -> queryLog.queryContainsWord(word))
                .collect(Collectors.toList());
    }

    /**
     * Gets all of the session ids that have the given word.
     * @param word the given word.
     * @return all of the session ids that have the given word.
     */
    public Set<String> sessionIdsWithWord(String word)
    {
        return getQueryLogsThatContainWord(word)
                .stream()
                .map(QueryLog::getSessionId)
                .collect(Collectors.toSet());
    }

    /**
     * Determines the number of sessions in which misspelledWord was corrected to suggestedWord.
     * @param misspelledWord the misspelled word.
     * @param suggestedWord the correctly-spelled suggested word.
     * @return the number of sessions corrections happened.
     */
    public int numberOfCorrections(String misspelledWord, String suggestedWord)
    {
        return (int) sessionIdsWithWord(misspelledWord)
                .stream()
                .map(sessionId ->
                {
                    long count = getLogsWithSameSessionId(sessionId)
                            .stream()
                            .filter(queryLog -> queryLog.queryContainsWord(suggestedWord))
                            .count();
                    return (count > 0) ? 1 : 0;
                }
                ).count();
    }

    /**
     * Gets the number of times that any query was corrected to have suggestedWord in it.
     * @param suggestedWord the correctly spelled suggested word.
     * @return the number of sessions corrections happened.
     */
    public int numberOfCorrections(String suggestedWord)
    {
        return sessionIdsWithWord(suggestedWord)
                .size();
    }

    /**
     * Maps the Session Ids to their logs.
     * @return the map of Session ids to logs.
     */
    public Map<String, List<QueryLog>> getSessionIdMapping()
    {
        return logs
                .stream()
                .collect(Collectors.groupingBy(QueryLog::getSessionId));
    }

    /**
     * Gets all the logs that have the given session id.
     * @param sessionId the given session id.
     * @return the logs that have the given session id.
     */
    public List<QueryLog> getLogsWithSameSessionId(String sessionId)
    {
        return getSessionIdMapping()
                .get(sessionId);
    }
}
