package edu.byu.cstaheli.cs453.document_ranking.index;

import com.google.common.collect.TreeMultimap;
import javafx.util.Pair;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * An Index of Documents to the entries in a document.
 */
public interface Index
{
    /**
     * Adds an entry to the index. It will be added and sorted according to its document id, as well as the differences
     * in the entry.
     *
     * @param entry the entry to add.
     */
    void addEntry(IndexEntry entry);

    /**
     * Gets a <code>SortedSet<Integer></code> of all of the document ids present in the Index.
     *
     * @return all of the document Ids.
     */
    SortedSet<Integer> getAllDocumentIds();

    /**
     * Gets all of the entries that contain the given word.
     *
     * @param word the desired word.
     * @return the entries that contain the given word.
     */
    SortedSet<IndexEntry> getEntriesOfWord(String word);

    /**
     * Gets the entries of the given word in the given document id.
     *
     * @param word       the desired word.
     * @param documentId the document Id to search in.
     * @return the entries of the given word in the given document id.
     */
    default SortedSet<IndexEntry> getEntriesOfWordInDocument(String word, int documentId)
    {
        return getEntriesFromDocument(documentId)
                .stream()
                .filter(i -> word.equals(i.getWord()))
                .collect(Collectors.toCollection(TreeSet<IndexEntry>::new));
    }

    /**
     * Gets the frequency of the given word int the document given by its id.
     *
     * @param word       the desired word.
     * @param documentId the document Id to search in.
     * @return the frequency of the word in the document.
     */
    default int getFrequencyOfWordInDocument(String word, int documentId)
    {
        return getEntriesOfWordInDocument(word, documentId)
                .stream()
                .mapToInt(IndexEntry::getFrequency)
                .sum();
    }

    /**
     * Gets the number of documents that the word is present in.
     *
     * @param word the word to check.
     * @return the number of documents that the word is present in.
     */
    default int getNumberOfDocumentsWordIsPresentIn(String word)
    {
        return getDocumentIdsWhereWordPresent(word)
                .size();
    }

    /**
     * Gets all of the entries from a document.
     *
     * @param documentId the document to get the entries from.
     * @return all of the entries from the document.
     */
    SortedSet<IndexEntry> getEntriesFromDocument(int documentId);

    /**
     * Gets the size of the index.
     *
     * @return the size of the index.
     */
    int size();

    /**
     * Gets the total number of entries in the Index. This will be the total number of all entries in all documents.
     *
     * @return the total number of entries in the Index
     */
    int totalSize();

    /**
     * Gets all the document ids where the given word is present.
     *
     * @param word the word to check.
     * @return all the document ids where the given word is present.
     */
    default SortedSet<Integer> getDocumentIdsWhereWordPresent(String word)
    {
        return getEntriesOfWord(word)
                .stream()
                .map(IndexEntry::getDocumentId)
                .collect(Collectors.toCollection(TreeSet<Integer>::new));
    }

    /**
     * Gets the total frequency of a word across all documents.
     *
     * @param word the word to check.
     * @return the total frequency of a word across all documents.
     */
    default int totalFrequencyOfWord(String word)
    {
        return getEntriesOfWord(word)
                .stream()
                .mapToInt(IndexEntry::getFrequency)
                .sum();
    }

    /**
     * Determines the maximum frequency in a document. This can be used to normalize the frequency of another word.
     *
     * @param documentId the document id to check in.
     * @return the maximum frequency in a document.
     */
    default int mostFrequentWordFrequency(int documentId)
    {
        return getEntriesOfWordInDocument(mostFrequentWord(documentId), documentId)
                .stream()
                .mapToInt(IndexEntry::getFrequency)
                .sum();
    }

    /**
     * Gets the most frequent word in a document.
     *
     * @param documentId the document id to check in.
     * @return the most frequent word in a document.
     */
    String mostFrequentWord(int documentId);

    /**
     * This method iterates through the values of the {@link TreeMultimap},
     * searching for {@link IndexEntry} objects which have their {@code word}
     * field equal to the parameter, word.
     *
     * @param word The word to search for in every document.
     * @return A {@link List<Pair<Integer, Integer>>} where each {@link Pair}
     * will hold the document's ID as its first element and the frequency
     * of the word in the document as its second element.
     * <p>
     * Note that the {@link Pair} object is defined in javafx.util.Pair
     */
    List<Pair<Integer, Integer>> totalWordUses(String word);
}
