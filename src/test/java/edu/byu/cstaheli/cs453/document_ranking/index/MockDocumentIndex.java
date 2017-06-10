package edu.byu.cstaheli.cs453.document_ranking.index;

import java.util.SortedSet;

/**
 * Created by cstaheli on 6/10/2017.
 */
public class MockDocumentIndex extends DocumentIndex
{
    @Override
    public SortedSet<Integer> getAllDocumentIds()
    {
        return super.getAllDocumentIds();
    }

    @Override
    public SortedSet<IndexEntry> getEntriesOfWord(String word)
    {
        return super.getEntriesOfWord(word);
    }

    @Override
    public SortedSet<IndexEntry> getEntriesFromDocument(int documentId)
    {
        return super.getEntriesFromDocument(documentId);
    }

    @Override
    public int size()
    {
        return super.size();
    }

    @Override
    public int totalSize()
    {
        return super.totalSize();
    }

    @Override
    public int totalFrequencyOfWord(String word)
    {
        return super.totalFrequencyOfWord(word);
    }

    @Override
    public String mostFrequentWord(int documentId)
    {
        return "potato";
    }
}
