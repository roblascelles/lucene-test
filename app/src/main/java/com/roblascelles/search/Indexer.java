package com.roblascelles.search;

import java.io.*;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Indexer {

    private Directory directory;
    private StandardAnalyzer analyzer = new StandardAnalyzer();
    private IndexWriterConfig config;
    private IndexWriter writer;

    public Indexer(String directory) throws IOException {
        this.directory =  FSDirectory.open(new File(directory).toPath());
        this.config = new IndexWriterConfig(this.analyzer);
        this.writer = new IndexWriter(this.directory, this.config);
    }

    public void index(String title, String isbn) throws IOException {
        Document doc = new Document();

        doc.add(new TextField("title", title, Field.Store.YES));
        doc.add(new StringField("isbn", isbn, Field.Store.YES));

        writer.addDocument(doc);
        writer.commit();
    }

}