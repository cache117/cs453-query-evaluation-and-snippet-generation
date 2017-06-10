package edu.byu.cstaheli.cs453.common.log;

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
 * The basic functionality behind a Log parser. Logs can differ, to a degree, but most pieces are the same.
 */
public abstract class LogParser
{
    private List<Log> logs;

    public LogParser(String fileName)
    {
        logs = parseFile(fileName);
    }

    /**
     * A template method that parses a file. For this to work, {@link LogParser#getLineFilter()},
     * {@link LogParser#getLineSplitter()}, and {@link LogParser#parseQueryLogFromLine(String[])} must be implemented
     * by a child class.
     *
     * @param fileName the name of the file to parse.
     * @return a list of all of the logs in the file.
     */
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

    /**
     * Gets all of the lines from a file.
     *
     * @param fileName the name of the file.
     * @return the List of all of the lines, split by {@link LogParser#getLineSplitter()} and filtered by
     * {@link LogParser#getLineFilter()}
     * @throws IOException if the file can't be read in for some reason.
     */
    final protected List<String[]> getLinesFromFile(String fileName) throws IOException
    {
        return readFile(fileName)
                .stream()
                .filter(getLineFilter())
                .map(getLineSplitter())
                .collect(Collectors.toList());
    }

    /**
     * Method that must be overridden by a child class. Part of the template method. This method should determine which
     * lines should be skipped in the log. For example, it could keep only lines that are not empty.
     *
     * @return the Predicate (probably in lambda form) that represents the line filter.
     */
    protected abstract Predicate<String> getLineFilter();

    /**
     * Method that must be overridden by a child class. Part of the template method. This method should determine how
     * to split the lines into an array of string. For example, it could split based on a tab.
     *
     * @return The function (probably in lambda form) that splits the lines.
     */
    protected abstract Function<String, String[]> getLineSplitter();

    /**
     * Method that must be overridden by a child class. Part of the template method. This should return a
     * <code>Log</code> that represents the actual contents of the Log file.
     *
     * @param line the line to parse from.
     * @return the <code>Log</code> representation of the line.
     */
    protected abstract Log parseQueryLogFromLine(String[] line);

    /**
     * Reads all of the lines of the file into a list.
     *
     * @param fileName the name of the file to read.
     * @return a list of all of the lines of the file.
     * @throws IOException if an I/O error occurs reading from the file or a malformed or unmappable byte
     *                     sequence is read.
     */
    protected final List<String> readFile(String fileName) throws IOException
    {
        return Files.readAllLines(Paths.get(fileName), getFileCharset());
    }

    /**
     * Determines what Charset should be used to read in the log file. This defaults to UTF-8, but can and should be
     * overridden if necessary.
     * @return the charset to use when reading in the file.
     * @see LogParser#readFile(String)
     */
    protected Charset getFileCharset()
    {
        return Charset.forName("UTF-8");
    }

    /**
     * Gets all of the logs that were parsed from the file.
     * @return the logs that were parsed from the file.
     */
    public List<Log> getLogs()
    {
        return logs;
    }
}
