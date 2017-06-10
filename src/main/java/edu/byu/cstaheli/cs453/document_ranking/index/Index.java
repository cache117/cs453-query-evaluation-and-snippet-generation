package edu.byu.cstaheli.cs453.document_ranking.index;

import com.google.common.collect.Ordering;
import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;
import javafx.util.Pair;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * An index between all of the documents and the entries in each document.
 */
public class Index
{
    private static Index _instance;
    /**
     * A map from the Document Id of an entry to the entry itself.
     */
    private SortedSetMultimap<Integer, IndexEntry> entries;

    private Index()
    {
        entries = TreeMultimap.create(Ordering.natural(), Ordering.natural().reversed());
    }

    /**
     * Gets the one instance to the Index, since there is a bit of overhead in creating the Index.
     *
     * @return the instance of the Index.
     */
    public static Index getInstance()
    {
        if (_instance == null)
        {
            _instance = new Index();
        }
        return _instance;
    }

    /**
     * Adds an entry to the index. It will be added and sorted according to its document id, as well as the differences
     * in the entry.
     *
     * @param entry the entry to add.
     */
    public void addEntry(IndexEntry entry)
    {
        entries.put(entry.getDocumentId(), entry);
    }

    /**
     * Gets a <code>SortedSet<Integer></code> of all of the document ids present in the Index.
     *
     * @return all of the document Ids.
     */
    public SortedSet<Integer> getAllDocumentIds()
    {
        return (SortedSet<Integer>) entries.keySet();
    }

    /**
     * Gets all of the entries that contain the given word.
     *
     * @param word the desired word.
     * @return the entries that contain the given word.
     */
    public SortedSet<IndexEntry> getEntriesOfWord(String word)
    {
//        return entries.get(word);
        return entries
                .values()
                .stream()
                .filter(i -> word.equals(i.getWord()))
                .collect(Collectors.toCollection(TreeSet<IndexEntry>::new));
    }

    /**
     * Gets the entries of the given word in the given document id.
     *
     * @param word       the desired word.
     * @param documentId the document Id to search in.
     * @return the entries of the given word in the given document id.
     */
    public SortedSet<IndexEntry> getEntriesOfWordInDocument(String word, int documentId)
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
    public int getFrequencyOfWordInDocument(String word, int documentId)
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
    public int getNumberOfDocumentsWordIsPresentIn(String word)
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
    public SortedSet<IndexEntry> getEntriesFromDocument(int documentId)
    {
        return entries.get(documentId);
    }

    /**
     * Gets the size of the index, based on the number of documents there are.
     *
     * @return the number of documents in the index.
     */
    public int size()
    {
        return getAllDocumentIds().size();
    }

    /**
     * Gets the total number of entries in the Index. This will be the total number of all entries in all documents.
     *
     * @return the total number of entries in the Index
     */
    public int totalSize()
    {
        return entries.size();
    }

    /**
     * Gets all the document ids where the given word is present.
     *
     * @param word the word to check.
     * @return all the document ids where the given word is present.
     */
    public SortedSet<Integer> getDocumentIdsWhereWordPresent(String word)
    {
        return getEntriesOfWord(word)
                .stream()
                .map(IndexEntry::getDocumentId)
                .collect(Collectors.toCollection(TreeSet<Integer>::new));
    }

    /**
     * Gets the total frequency of a word across all documents.
     * @param word the word to check.
     * @return the total frequency of a word across all documents.
     */
    public int totalFrequencyOfWord(String word)
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
    public int mostFrequentWordFrequency(int documentId)
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
    public String mostFrequentWord(int documentId)
    {
        return entries
                .get(documentId)
                .first()
                .getWord();
    }

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
    public List<Pair<Integer, Integer>> totalWordUses(String word)
    {
        return entries.values()
                .stream()
                .filter(i -> word.equals(i.getWord()))
                .map(i -> new Pair<>(i.getDocumentId(), i.getFrequency()))
                .collect(Collectors.toList());
    }
}
