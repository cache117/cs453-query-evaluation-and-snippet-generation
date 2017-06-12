package edu.byu.cstaheli.cs453.query_evaluation.log;

import edu.byu.cstaheli.cs453.common.log.LogParser;
import edu.byu.cstaheli.cs453.common.log.QueryLog;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Contains functionality to parse Spelling Query QueryLogs.
 */
public class SpellingLogParser extends LogParser
{
    /**
     * Creates a {@link SpellingLogParser} from a file.
     *
     * @param fileName the name of the file to create from.
     */
    public SpellingLogParser(String fileName)
    {
        super(fileName);
    }

    /**
     * Gets the filter for the lines that should be read in. This filter will only keep non-empty lines.
     *
     * @return the filter for non-empty lines.
     */
    @Override
    protected Predicate<String> getLineFilter()
    {
        return line -> !line.isEmpty();
    }

    /**
     * Gets the line splitter for this log parser. This will split lines based on tabs.
     * @return the line splitter based on tabs.
     */
    @Override
    protected Function<String, String[]> getLineSplitter()
    {
        return line -> line.split("\t");
    }

    /**
     * Parses the log into a QueryLog.
     * @param line the line to parse from.
     * @return the QueryLog from the line.
     */
    @Override
    protected QueryLog parseQueryLogFromLine(String[] line)
    {
        String sessionId = line[0];
        String query = line[1];
        return new QueryLog(sessionId, query);
    }
}
