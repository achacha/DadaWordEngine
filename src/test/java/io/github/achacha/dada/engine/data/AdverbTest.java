package io.github.achacha.dada.engine.data;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AdverbTest {
    @Test
    public void testInternals() {
        Adverb adverb = TestWords.makeAdverb("quickly");
        assertNotNull(adverb.toString());
        assertEquals(Word.Type.Adverb, adverb.getType());
    }

    @Test
    public void findAdverbInAllWords() {
        WordData wordData = new WordData("resource:/data/test");

        // Make sure we are able to find word without case issues
        Optional<? extends Word> ow = wordData.findFirstWordsByText("ADVERBLY");
        assertTrue(ow.isPresent());
        ow = wordData.findFirstWordsByText("adverbly");
        assertTrue(ow.isPresent());
    }
}
