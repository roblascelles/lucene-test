package com.roblascelles.search;

import java.io.*;
import java.nio.file.Path;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Searcher {

    private Directory index;
    private StandardAnalyzer analyzer = new StandardAnalyzer();
    private IndexReader reader;
    private IndexSearcher searcher;
    private QueryParser parser;

    public Searcher(Path indexPath) throws IOException {
        this.index = FSDirectory.open(indexPath.toAbsolutePath());
        this.reader = DirectoryReader.open(this.index);
        this.searcher = new IndexSearcher(this.reader);
        this.parser = new QueryParser("title", this.analyzer);
    }

    public int query(String query) throws IOException, ParseException {
        
        Query q = parser.parse(query);
        int hitsPerPage = 10;

        var hits = searcher.search(q, hitsPerPage).scoreDocs;

        var storedFields = searcher.storedFields();

        for (int i = 0; i < hits.length; i++) {
            Document d = storedFields.document(hits[i].doc);
            System.out.println((i + 1) + ". " + d.get("isbn") + "\t" + d.get("title"));
        }

        return hits.length;
    }

}