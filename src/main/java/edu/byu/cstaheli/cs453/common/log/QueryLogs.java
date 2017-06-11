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

    public QueryLogs(List<QueryLog> logs)
    {
        this.logs = logs;
    }

    public void add(QueryLog log)
    {
        logs.add(log);
    }

    public QueryLog get(int index)
    {
        return logs.get(index);
    }

    public int size()
    {
        return logs.size();
    }

    public List<QueryLog> getQueryLogsThatContainWord(String word)
    {
        return logs
                .stream()
                .filter(queryLog -> queryLog.queryContainsWord(word))
                .collect(Collectors.toList());
    }

    public List<QueryLog> getSameQueryLogsExceptForWordAtIndex(String query, int index)
    {
        return logs
                .stream()
                .filter(queryLog -> queryLog.sameExceptIndex(index, query))
                .collect(Collectors.toList());
    }

    public List<QueryLog> getLogsWithQuery(String query)
    {
        return logs
                .stream()
                .filter(queryLog -> queryLog
                        .getQuery()
                        .equals(query)
                )
                .collect(Collectors.toList());
    }

    public int getFrequencyOfWordInAllQueries(String word)
    {
        return getQueryLogsThatContainWord(word)
                .size();
    }

    public int getCoOccurenceOfQueries(String originalQuery, String suggestedQuery)
    {
        List<QueryLog> originalQueryLogs = getLogsWithQuery(originalQuery);
        List<QueryLog> suggestedLogs = getLogsWithQuery(suggestedQuery);
        return suggestedLogs.size();
    }

    public Set<String> sessionIdsWithWord(String word)
    {
        return getQueryLogsThatContainWord(word)
                .stream()
                .map(QueryLog::getSessionId)
                .collect(Collectors.toSet());
    }

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

    public Map<String, List<QueryLog>> getSessionIdMapping()
    {
        return logs
                .stream()
                .collect(Collectors.groupingBy(QueryLog::getSessionId));
    }

    public List<QueryLog> getLogsWithSameSessionId(String sessionId)
    {
        return getSessionIdMapping()
                .get(sessionId);
    }
}
