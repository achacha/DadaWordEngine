package org.achacha.dada.engine.data;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WordDataTest {
    @Test
    public void testLoadingFallback() {
        WordData wordData = new WordData("does-not-exist");

        assertEquals("resource:/data/default/noun.csv", wordData.getNouns().getResourcePath());
        assertEquals("resource:/data/default/pronoun.csv", wordData.getPronouns().getResourcePath());
        assertEquals("resource:/data/default/verb.csv", wordData.getVerbs().getResourcePath());
        assertEquals("resource:/data/default/adverb.csv", wordData.getAdverbs().getResourcePath());
        assertEquals("resource:/data/default/adjective.csv", wordData.getAdjectives().getResourcePath());
        assertEquals("resource:/data/default/conjunction.csv", wordData.getConjunctions().getResourcePath());
        assertEquals("resource:/data/default/pronoun.csv", wordData.getPronouns().getResourcePath());
    }

    @Test
    public void testLoadingDefault() {
        WordData wordData = new WordData();
        assertEquals("resource:/data/default/noun.csv", wordData.getNouns().getResourcePath());
        assertEquals("resource:/data/default/pronoun.csv", wordData.getPronouns().getResourcePath());
        assertEquals("resource:/data/default/verb.csv", wordData.getVerbs().getResourcePath());
        assertEquals("resource:/data/default/adverb.csv", wordData.getAdverbs().getResourcePath());
        assertEquals("resource:/data/default/adjective.csv", wordData.getAdjectives().getResourcePath());
        assertEquals("resource:/data/default/conjunction.csv", wordData.getConjunctions().getResourcePath());
        assertEquals("resource:/data/default/pronoun.csv", wordData.getPronouns().getResourcePath());
        assertEquals(0, wordData.getIgnore().size());

    }

    @Test
    public void testLoadingTest() {
        WordData wordData = new WordData("resource:/data/test");
        assertEquals("resource:/data/test/noun.csv", wordData.getNouns().getResourcePath());
        assertEquals("resource:/data/test/pronoun.csv", wordData.getPronouns().getResourcePath());
        assertEquals("resource:/data/test/verb.csv", wordData.getVerbs().getResourcePath());
        assertEquals("resource:/data/test/adverb.csv", wordData.getAdverbs().getResourcePath());
        assertEquals("resource:/data/test/adjective.csv", wordData.getAdjectives().getResourcePath());
        assertEquals("resource:/data/test/conjunction.csv", wordData.getConjunctions().getResourcePath());
        assertEquals("resource:/data/test/pronoun.csv", wordData.getPronouns().getResourcePath());
        assertEquals(1, wordData.getIgnore().size());

    }

    @Test
    public void testFindText() {
        WordData wordData = new WordData("resource/data/test_parser");
        Optional<? extends Word> pronoun = wordData.findFirstWordsByText("I");

        assertTrue(pronoun.isPresent());
    }

    /**
     * Load word sets and check for data issues
     */
    @Test
    public void testLoadingWordDataSets() {
        verifyWordData("resource:/data/default");
        verifyWordData("resource:/data/dada2002");
        verifyWordData("resource:/data/dada2018");
        verifyWordData("resource:/data/extended2018");
    }

    private void verifyWordData(String resourcePath) {
        WordData wordData = new WordData(resourcePath);
        assertFalse(wordData.getAdjectives().isDuplicateFound());
        assertFalse(wordData.getAdverbs().isDuplicateFound());
        assertFalse(wordData.getConjunctions().isDuplicateFound());
        assertFalse(wordData.getNouns().isDuplicateFound());
        assertFalse(wordData.getPrepositions().isDuplicateFound());
        assertFalse(wordData.getVerbs().isDuplicateFound());
    }
}
