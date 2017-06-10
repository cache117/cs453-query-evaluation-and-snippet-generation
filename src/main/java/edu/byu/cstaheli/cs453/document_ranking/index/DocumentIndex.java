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
public class DocumentIndex implements Index
{
    /**
     * A map from the Document Id of an entry to the entry itself.
     */
    private SortedSetMultimap<Integer, IndexEntry> entries;

    public DocumentIndex()
    {
        entries = TreeMultimap.create(Ordering.natural(), Ordering.natural().reversed());
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

    @Override
    public SortedSet<Integer> getAllDocumentIds()
    {
        return (SortedSet<Integer>) entries.keySet();
    }

    @Override
    public SortedSet<IndexEntry> getEntriesOfWord(String word)
    {
//        return entries.get(word);
        return entries
                .values()
                .stream()
                .filter(i -> word.equals(i.getWord()))
                .collect(Collectors.toCollection(TreeSet<IndexEntry>::new));
    }

    @Override
    public SortedSet<IndexEntry> getEntriesFromDocument(int documentId)
    {
        return entries.get(documentId);
    }

    /**
     * Gets the size of the index, based on the number of documents there are.
     *
     * @return the number of documents in the index.
     */
    @Override
    public int size()
    {
        return getAllDocumentIds().size();
    }

    @Override
    public int totalSize()
    {
        return entries.size();
    }

    @Override
    public int totalFrequencyOfWord(String word)
    {
        return getEntriesOfWord(word)
                .stream()
                .mapToInt(IndexEntry::getFrequency)
                .sum();
    }

    @Override
    public String mostFrequentWord(int documentId)
    {
        return entries
                .get(documentId)
                .first()
                .getWord();
    }

    @Override
    public List<Pair<Integer, Integer>> totalWordUses(String word)
    {
        return entries.values()
                .stream()
                .filter(i -> word.equals(i.getWord()))
                .map(i -> new Pair<>(i.getDocumentId(), i.getFrequency()))
                .collect(Collectors.toList());
    }
}
