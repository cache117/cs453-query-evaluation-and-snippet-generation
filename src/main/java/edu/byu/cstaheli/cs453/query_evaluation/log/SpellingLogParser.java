package edu.byu.cstaheli.cs453.query_evaluation.log;

import edu.byu.cstaheli.cs453.common.log.Log;
import edu.byu.cstaheli.cs453.common.log.LogParser;

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
public class SpellingLogParser extends LogParser
{
    public SpellingLogParser(String fileName)
    {
        super(fileName);
    }

    protected Predicate<String> getLineFilter()
    {
        return line -> !line.isEmpty();
    }

    protected Function<String, String[]> getLineSplitter()
    {
        return line -> line.split("\t");
    }

    protected Log parseQueryLogFromLine(String[] line)
    {
        String anonId = line[0];
        String query = line[1];
        return new QueryLog(anonId, query);
    }
}
