package edu.byu.cstaheli.cs453.common.log;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by cstaheli on 6/10/2017.
 */
class QueryLogsTest
{
    @Test
    void getLogsWithSameSessionId()
    {
        QueryLogs queryLogs = new QueryLogs(new ArrayList<>());
        queryLogs.add(new QueryLog("20938485750", "scheduled movie screning"));
        queryLogs.add(new QueryLog("20938485750", "scheduled movie screening"));
        queryLogs.add(new QueryLog("20938485750", "scheduled movie screening"));
        queryLogs.add(new QueryLog("20938485789", "prision"));
        queryLogs.add(new QueryLog("20938485789", "prision"));
        queryLogs.add(new QueryLog("20938485789", "prison"));

        Map<String, List<QueryLog>> logsWithSameSessionId = queryLogs.getSessionIdMapping();
        assertEquals(2, logsWithSameSessionId.size());
        List<QueryLog> sameSession = logsWithSameSessionId.get("20938485789");
        assertEquals(3, sameSession.size());
    }

    @Test
    void testGetNumberOfCorrections()
    {
        QueryLogs queryLogs = new QueryLogs(new ArrayList<>());
        queryLogs.add(new QueryLog("01", "Movie atcor"));
        queryLogs.add(new QueryLog("01", "Movie atcor"));
        queryLogs.add(new QueryLog("01", "Movie actor"));
        queryLogs.add(new QueryLog("01", "Movie actor"));
        queryLogs.add(new QueryLog("02", "Award winning axtor"));
        queryLogs.add(new QueryLog("02", "Award winning actor"));
        queryLogs.add(new QueryLog("03", "Soap opera axtor"));
        queryLogs.add(new QueryLog("03", "Soap opera actor"));
        queryLogs.add(new QueryLog("04", "movie actor"));

        int corrections = queryLogs.numberOfCorrections("axtor", "actor");
        assertEquals(2, corrections);
        corrections = queryLogs.numberOfCorrections("atcor", "actor");
        assertEquals(1, corrections);
    }
}