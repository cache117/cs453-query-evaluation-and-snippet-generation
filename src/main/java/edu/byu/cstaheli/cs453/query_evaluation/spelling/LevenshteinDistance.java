package edu.byu.cstaheli.cs453.query_evaluation.spelling;

/**
 * Used to compute the Levenshtein distance (edit distance) between two words. This can be computed by the minimum
 * number of operations needed to transform one string into the other, where an operation is an insertion, deletion,
 * or substitution of a single character.
 */
public class LevenshteinDistance
{
    private final String left;
    private final String right;
    private int[][] distanceTable;

    /**
     * Creates a LevenshteinDistance with two words. To calculate the distance, call <code>calculateDistance()</code>.
     *
     * @param left  the first word
     * @param right the second word
     */
    public LevenshteinDistance(String left, String right)
    {
        this.left = left;
        this.right = right;
        distanceTable = constructInitialDistanceTable(left.length() + 1, right.length() + 1);
    }

    /**
     * Computes the minimum of three values.
     *
     * @param a the first value.
     * @param b the second value.
     * @param c the third value.
     * @return the minimum of three values.
     */
    private static int min(int a, int b, int c)
    {
        return Math.min(a, Math.min(b, c));
    }

    /**
     * Calculates the Levenshtein distance between the two words in this class.
     *
     * @return the edit distance between the two words.
     */
    public int calculateDistance()
    {
        for (int i = 1; i < left.length() + 1; ++i)
        {
            for (int j = 1; j < right.length() + 1; ++j)
            {
                int cost = getSubstitutionCost(left.charAt(i - 1), right.charAt(j - 1));
                distanceTable[i][j] = min(
                        distanceTable[i - 1][j] + 1,        //deletion
                        distanceTable[i][j - 1] + 1,        //insertion
                        distanceTable[i - 1][j - 1] + cost  //substitution
                );
            }
        }

        return distanceTable[left.length()][right.length()];
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        String rightString = String.join("\t", (" " + " " + right).split(""));
        String leftString = " " + left;
        builder.append(rightString)
                .append("\n");
        for (int i = 0; i < distanceTable.length; ++i)
        {
            builder.append(leftString.charAt(i))
                    .append("\t");
            for (int j = 0; j < distanceTable[i].length; ++j)
            {
                builder.append(distanceTable[i][j])
                        .append("\t");
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    /**
     * Gets the cost of changing the two characters. Cost is binary: 0 if the character are the same, 1 if they
     * are different.
     *
     * @param left  the first character.
     * @param right the second character.
     * @return 0 if the characters are equal, 1 otherwise.
     */
    private int getSubstitutionCost(char left, char right)
    {
        return (left == right) ? 0 : 1;
    }

    /**
     * Builds an initial distance table of size [rows X columns]. The first row and column will have the default
     * distances in them (their index, essentially).
     *
     * @param rows    the first dimension of the table.
     * @param columns the second dimension of the table.
     * @return a table constructed with the default distances along the first row and first column.
     */
    private int[][] constructInitialDistanceTable(int rows, int columns)
    {
        int[][] distanceTable = new int[rows][columns];
        for (int i = 0; i < rows; ++i)
        {
            distanceTable[i][0] = i;
        }

        for (int j = 0; j < columns; ++j)
        {
            distanceTable[0][j] = j;
        }

        return distanceTable;
    }
}
