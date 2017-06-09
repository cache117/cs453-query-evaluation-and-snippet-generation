package edu.byu.cstaheli.cs453.query_evaluation.spelling;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LevenshteinDistanceTest
{
    @Test
    void getDistance()
    {
        int distance = new LevenshteinDistance("kitten", "sitting").calculateDistance();
        assertEquals(3, distance);

        distance = new LevenshteinDistance("sunday", "saturday").calculateDistance();
        assertEquals(3, distance);

        distance = new LevenshteinDistance("gumbo", "gambol").calculateDistance();
        assertEquals(2, distance);

        distance = new LevenshteinDistance("cache", "staheli").calculateDistance();
        assertEquals(5, distance);
    }

}