package edu.byu.cstaheli.cs453.common.log;

import edu.byu.cstaheli.cs453.query_evaluation.log.SpellingQueryLog;
import edu.byu.cstaheli.cs453.query_evaluation.log.SpellingLogParser;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LogParserTest
{
    @Test
    void parseFile()
    {
        String resources = "src/main/resources";
        LogParser parser = new SpellingLogParser(resources + "/query_log.txt");
        List<Log> queryLogs = parser.getLogs();

        assertEquals(573, queryLogs.size());

        SpellingQueryLog log = (SpellingQueryLog) queryLogs.get(572);
        assertEquals("20938485884", log.getAnonId());

        log = (SpellingQueryLog) queryLogs.get(460);
        assertEquals("definition of scrrening", log.getQuery());
    }

}