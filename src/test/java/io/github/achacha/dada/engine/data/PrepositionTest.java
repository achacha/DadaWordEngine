package io.github.achacha.dada.engine.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PrepositionTest {
    @Test
    public void testInternals() {
        Preposition preposition = TestWords.makePreposition("on");
        assertNotNull(preposition.toString());
        assertEquals(Word.Type.Preposition, preposition.getType());
    }
}
