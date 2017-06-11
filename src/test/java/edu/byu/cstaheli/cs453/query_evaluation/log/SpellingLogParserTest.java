package edu.byu.cstaheli.cs453.query_evaluation.log;

import edu.byu.cstaheli.cs453.common.log.LogParser;
import edu.byu.cstaheli.cs453.common.log.QueryLog;
import edu.byu.cstaheli.cs453.common.log.QueryLogs;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by cstaheli on 6/10/2017.
 */
class SpellingLogParserTest
{
    private String fileName = "src/main/resources/query_logs.txt";

    @Test
    void parseFile()
    {
        String resources = "src/main/resources";
        LogParser parser = new SpellingLogParser(resources + "/query_log.txt");
        QueryLogs queryLogs = parser.getQueryLogs();

        assertEquals(573, queryLogs.size());

        QueryLog log = queryLogs.get(572);
        assertEquals("20938485884", log.getSessionId());

        log = queryLogs.get(460);
        assertEquals("definition of scrrening", log.getQuery());
    }
}