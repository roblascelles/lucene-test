package com.roblascelles.search;

import java.io.*;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Searcher {

    private Directory directory;
    private StandardAnalyzer analyzer = new StandardAnalyzer();
    private IndexReader reader;
    private IndexSearcher searcher;
    private QueryParser parser;

    public Searcher(String directory) throws IOException {
        this.directory = FSDirectory.open(new File(directory).toPath());
        this.reader = DirectoryReader.open(this.directory);
        this.searcher = new IndexSearcher(this.reader);
        this.parser = new QueryParser("title", this.analyzer);
    }

    public int query(String query) throws IOException, ParseException {
        Query q = parser.parse(query);

        // 3. search
        int hitsPerPage = 10;

        TopDocs docs = searcher.search(q, hitsPerPage);
        ScoreDoc[] hits = docs.scoreDocs;

        // 4. display results
        System.out.println("Found " + hits.length + " hits.");
        for (int i = 0; i < hits.length; ++i) {
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            System.out.println((i + 1) + ". " + d.get("isbn") + "\t" + d.get("title"));
        }

        return hits.length;
        // reader can only be closed when there
        // is no need to access the documents any more.
        //reader.close();
    }

}