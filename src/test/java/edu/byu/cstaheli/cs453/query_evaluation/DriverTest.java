package edu.byu.cstaheli.cs453.query_evaluation;

import edu.byu.cstaheli.cs453.document_ranking.index.Index;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DriverTest
{
    @Test
    void readInCorpus()
    {
        Driver driver = new Driver();
        driver.readInCorpus("src/main/resources");
        Index index = driver.getIndex();
        assertEquals(32194, index.totalSize());
        assertEquals(322, index.size());

    }

}