package com.roblascelles.search;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;

import org.junit.jupiter.api.Test;

class IndexingTest {
    @Test void canIndexAndSearch() throws IOException, org.apache.lucene.queryparser.classic.ParseException {
        String directory = Files.createTempDirectory("lucene-test").toFile().getAbsolutePath();

        Indexer indexer = new Indexer(directory);
        indexer.index("a tale of five shapes", "AB001");
        indexer.index("doctor fishcake", "AB002");
        indexer.index("sunshine on my heron", "BC001");
        indexer.index("the shapes I regret", "BC002");
        
        Searcher searcher = new Searcher(directory);
        int hits = searcher.query("shapes");

         assertEquals(2, hits);
    }

}