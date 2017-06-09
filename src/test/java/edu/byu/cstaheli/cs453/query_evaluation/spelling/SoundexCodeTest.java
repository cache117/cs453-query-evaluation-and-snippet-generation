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
        String soundexEncoding = SoundexCode.getSoundexEncoding(word);
        assertEquals("E235", soundexEncoding);

        word = "extensions";
        soundexEncoding = SoundexCode.getSoundexEncoding(word);
        assertEquals("E235", soundexEncoding);

        word = "marshmellow";
        soundexEncoding = SoundexCode.getSoundexEncoding(word);
        assertEquals("M625", soundexEncoding);

        word = "marshmallow";
        soundexEncoding = SoundexCode.getSoundexEncoding(word);
        assertEquals("M625", soundexEncoding);

        word = "brimingham";
        soundexEncoding = SoundexCode.getSoundexEncoding(word);
        assertEquals("B655", soundexEncoding);

        word = "birmingham";
        soundexEncoding = SoundexCode.getSoundexEncoding(word);
        assertEquals("B655", soundexEncoding);

        word = "poiner";
        soundexEncoding = SoundexCode.getSoundexEncoding(word);
        assertEquals("P560", soundexEncoding);

        word = "pointer";
        soundexEncoding = SoundexCode.getSoundexEncoding(word);
        assertEquals("P536", soundexEncoding);

        word = "StAHeLi";
        soundexEncoding = SoundexCode.getSoundexEncoding(word);
        assertEquals("S340", soundexEncoding);

        word = "happenstance";
        soundexEncoding = SoundexCode.getSoundexEncoding(word);
        assertEquals("H152", soundexEncoding);

        word = "CACHE";
        soundexEncoding = SoundexCode.getSoundexEncoding(word);
        assertEquals("C200", soundexEncoding);

        try
        {
            word = "4#61-032";
            soundexEncoding = SoundexCode.getSoundexEncoding(word);
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
        assertTrue(SoundexCode.wordsHaveSameSoundexCode(first, second));

        first = "marshmellow";
        second = "marshmallow";
        assertTrue(SoundexCode.wordsHaveSameSoundexCode(first, second));

        first = "brimingham";
        second = "birmingham";
        assertTrue(SoundexCode.wordsHaveSameSoundexCode(first, second));

        first = "poiner";
        second = "pointer";
        assertFalse(SoundexCode.wordsHaveSameSoundexCode(first, second));
    }

}