package io.github.achacha.dada.engine.data;

import io.github.achacha.dada.test.GlobalTestData;
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
        // Make sure we are able to find word without case issues
        Optional<SavedWord> ow = GlobalTestData.WORD_DATA.findFirstWordsByText("ADVERBLY");
        assertTrue(ow.isPresent());
        ow = GlobalTestData.WORD_DATA.findFirstWordsByText("adverbly");
        assertTrue(ow.isPresent());
    }
}
