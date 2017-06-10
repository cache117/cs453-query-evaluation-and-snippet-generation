package edu.byu.cstaheli.cs453.common.log;

import edu.byu.cstaheli.cs453.query_evaluation.log.QueryLog;
import edu.byu.cstaheli.cs453.query_evaluation.log.SpellingLogParser;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by cstaheli on 6/9/2017.
 */
class LogParserTest
{
    @Test
    void parseFile()
    {
        String resources = "src/main/resources";
        LogParser parser = new SpellingLogParser(resources + "/query_log.txt");
        List<Log> queryLogs = parser.getQueryLogs();

        assertEquals(573, queryLogs.size());

        QueryLog log = (QueryLog) queryLogs.get(572);
        assertEquals("20938485884", log.getAnonId());

        log = (QueryLog) queryLogs.get(460);
        assertEquals("definition of scrrening", log.getQuery());
    }

}