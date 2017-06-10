package edu.byu.cstaheli.cs453.query_evaluation.spelling;

/**
 * Allows turning a word into it's representative Soundex code. This is useful for Spell Checking.
 * For example, the misspelled word "extenssions" has the same Soundex encoding as the correctly
 * spelled word "extensions": "E235". It isn't foolproof though. Some words, like "poiner" and
 * "pointer" have different Soundex codes, "P560" and "P536", respectively.
 */
public class SoundexCode
{
    /**
     * Gets the Soundex encoding for a given word.
     *
     * @param word the word to encode.
     * @return the Soundex encoding for the word.
     * @throws IllegalArgumentException if any characters in the word aren't letters.
     */
    public static String encode(String word)
    {
        char[] wordArray = word.toUpperCase().toCharArray();
        char firstChar = wordArray[0];
        //Ensures the first character is a valid one
        getSoundexCode(firstChar);
        for (int i = 1; i < wordArray.length; ++i)
        {
            wordArray[i] = getSoundexCode(wordArray[i]);
        }

        StringBuilder soundexEncoding = new StringBuilder();
        soundexEncoding.append(firstChar);
        for (int i = 1; i < wordArray.length; ++i)
        {
            if (wordArray[i] != wordArray[i - 1] && wordArray[i] != '-')
            {
                soundexEncoding.append(wordArray[i]);
            }
        }
        // Make sure it has at least the first character and is padded with 0s.
        // These will get stripped off anyways.
        soundexEncoding.append("000");
        return soundexEncoding.substring(0, 4);
    }

    /**
     * Gets the soundex representation of a letter.
     *
     * @param character the letter.
     * @return the soundex representation of a letter.
     */
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
            case 216: //O with slash through it
                return Character.MIN_VALUE;
            default:
                throw new IllegalArgumentException(String.format("Character \"%s\" is not a valid character for spelling", character));
        }
    }

    /**
     * Determines if two words have the same Soundex encoding.
     *
     * @param first  the first word.
     * @param second the second word.
     * @return true if they have they same encoding, false otherwise.
     */
    public static boolean wordsHaveSameEncoding(String first, String second)
    {
        return encode(first).equals(encode(second));
    }

    public static boolean wordsHaveSimilarEncoding(String first, String second)
    {
        if (wordsHaveSameEncoding(first, second))
        {
            return true;
        }
        else
        {
            if (encode(first).substring(0, 2).equals(encode(second).substring(0, 2)))
            {
                return true;
            }
        }
        return false;
    }
}
