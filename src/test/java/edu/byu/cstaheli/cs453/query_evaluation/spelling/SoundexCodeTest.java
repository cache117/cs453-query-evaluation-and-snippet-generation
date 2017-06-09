package edu.byu.cstaheli.cs453.query_evaluation.spelling;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by cstaheli on 6/8/2017.
 */
class SoundexCodeTest
{
    @Test
    void getSoundexEncoding()
    {
        String word = "extenssions";
        String soundexEncoding = SoundexCode.encode(word);
        assertEquals("E235", soundexEncoding);

        word = "extensions";
        soundexEncoding = SoundexCode.encode(word);
        assertEquals("E235", soundexEncoding);

        word = "marshmellow";
        soundexEncoding = SoundexCode.encode(word);
        assertEquals("M625", soundexEncoding);

        word = "marshmallow";
        soundexEncoding = SoundexCode.encode(word);
        assertEquals("M625", soundexEncoding);

        word = "brimingham";
        soundexEncoding = SoundexCode.encode(word);
        assertEquals("B655", soundexEncoding);

        word = "birmingham";
        soundexEncoding = SoundexCode.encode(word);
        assertEquals("B655", soundexEncoding);

        word = "poiner";
        soundexEncoding = SoundexCode.encode(word);
        assertEquals("P560", soundexEncoding);

        word = "pointer";
        soundexEncoding = SoundexCode.encode(word);
        assertEquals("P536", soundexEncoding);

        word = "StAHeLi";
        soundexEncoding = SoundexCode.encode(word);
        assertEquals("S340", soundexEncoding);

        word = "happenstance";
        soundexEncoding = SoundexCode.encode(word);
        assertEquals("H152", soundexEncoding);

        word = "CACHE";
        soundexEncoding = SoundexCode.encode(word);
        assertEquals("C200", soundexEncoding);

        try
        {
            word = "4#61-032";
            soundexEncoding = SoundexCode.encode(word);
            fail("Should have thrown Exception");
        }
        catch (IllegalArgumentException ignored)
        {

        }
    }

    @Test
    void doWordsHaveSameSoundexCode()
    {
        String first = "extenssions";
        String second = "extensions";
        assertTrue(SoundexCode.wordsHaveSameEncoding(first, second));

        first = "marshmellow";
        second = "marshmallow";
        assertTrue(SoundexCode.wordsHaveSameEncoding(first, second));

        first = "brimingham";
        second = "birmingham";
        assertTrue(SoundexCode.wordsHaveSameEncoding(first, second));

        first = "poiner";
        second = "pointer";
        assertFalse(SoundexCode.wordsHaveSameEncoding(first, second));
    }

}