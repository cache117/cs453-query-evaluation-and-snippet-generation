package edu.byu.cstaheli.cs453.common.log;

import edu.byu.cstaheli.cs453.query_evaluation.log.QueryLog;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by cstaheli on 6/9/2017.
 */
public abstract class LogParser
{
    private List<Log> queryLogs;
    public LogParser(String fileName)
    {
        queryLogs = parseFile(fileName);
    }

    final protected List<Log> parseFile(String fileName)
    {
        try
        {
            List<String[]> lines = getLinesFromFile(fileName);

            //Set the initial size to the number of lines - 1 (not including the header)
            List<Log> queryLogs = new ArrayList<>(lines.size() - 1);

            //The first line is the header. Skip it.
            for (int i = 1; i < lines.size(); i++)
            {
                String[] line = lines.get(i);
                queryLogs.add(parseQueryLogFromLine(line));
            }
            return queryLogs;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    final protected List<String[]> getLinesFromFile(String fileName) throws IOException
    {
        return readFile(fileName)
                .stream()
                .filter(getLineFilter())
                .map(getLineSplitter())
                .collect(Collectors.toList());
    }

    protected abstract Predicate<String> getLineFilter();

    protected abstract Function<String, String[]> getLineSplitter();

    protected abstract Log parseQueryLogFromLine(String[] line);

    protected final List<String> readFile(String fileName) throws IOException
    {
        return Files.readAllLines(Paths.get(fileName), getFileCharset());
    }

    protected Charset getFileCharset()
    {
        return Charset.forName("UTF-8");
    }

    public List<Log> getQueryLogs()
    {
        return queryLogs;
    }
}
