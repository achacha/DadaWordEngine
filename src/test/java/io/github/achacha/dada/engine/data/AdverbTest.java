package io.github.achacha.dada.engine.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AdverbTest {
    @Test
    public void testInternals() {
        Adverb adverb = TestWords.makeAdverb("quickly");
        assertNotNull(adverb.toString());
        assertEquals(Word.Type.Adverb, adverb.getType());
    }
}
