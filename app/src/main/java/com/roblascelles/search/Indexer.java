package com.roblascelles.search;

import java.io.*;
import java.nio.file.Path;

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

    private Directory index;
    private StandardAnalyzer analyzer = new StandardAnalyzer();
    private IndexWriterConfig config;
    private IndexWriter writer;

    public Indexer(Path indexPath) throws IOException {
        this.index = FSDirectory.open(indexPath.toAbsolutePath());
        this.config = new IndexWriterConfig(this.analyzer);
        this.writer = new IndexWriter(this.index, this.config);
    }

    public void index(String title, String isbn) throws IOException {
        Document doc = new Document();

        doc.add(new TextField("title", title, Field.Store.YES));
        doc.add(new StringField("isbn", isbn, Field.Store.YES));

        writer.addDocument(doc);
        writer.commit();
    }

}