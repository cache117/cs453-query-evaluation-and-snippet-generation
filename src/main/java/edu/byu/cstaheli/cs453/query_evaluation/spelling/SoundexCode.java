package edu.byu.cstaheli.cs453.query_evaluation.spelling;

/**
 * Created by cstaheli on 6/8/2017.
 */
public class SoundexCode
{
    public static String getSoundexEncoding(String word)
    {
        char[] wordArray = word.toUpperCase().toCharArray();
        char firstChar = wordArray[0];
        for (int i = 1; i < wordArray.length; ++i)
        {
            wordArray[i] = getSoundexCode(wordArray[i]);
        }

        StringBuilder soundexEncoding = new StringBuilder();
        soundexEncoding.append(firstChar);
        for (int i = 1; i < wordArray.length; i++)
        {
            if (wordArray[i] != wordArray[i-1] && wordArray[i] != '-')
            {
                soundexEncoding.append(wordArray[i]);
            }
        }
        // Make sure it has at least the first character and is padded with 0s.
        // These will get stripped off anyways.
        soundexEncoding.append("000");
        return soundexEncoding.substring(0, 4);
    }

    private static char getSoundexCode(char character)
    {
        switch (character)
        {
            case 'A':
            case 'E':
            case 'I':
            case 'O':
            case 'U':
            case 'Y':
            case 'H':
            case 'W':
                return '-';
            case 'B':
            case 'F':
            case 'P':
            case 'V':
                return '1';
            case 'C':
            case 'G':
            case 'J':
            case 'K':
            case 'Q':
            case 'S':
            case 'X':
            case 'Z':
                return '2';
            case 'D':
            case 'T':
                return '3';
            case 'L':
                return '4';
            case 'M':
            case 'N':
                return '5';
            case 'R':
                return '6';
            default:
                throw new IllegalArgumentException(String.format("Character \"%s\" is not a valid character for spelling", character));
        }
    }

    public static boolean wordsHaveSameSoundexCode(String first, String second)
    {
        return getSoundexEncoding(first).equals(getSoundexEncoding(second));
    }
}
