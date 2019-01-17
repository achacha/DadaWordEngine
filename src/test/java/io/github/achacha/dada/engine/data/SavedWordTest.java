package io.github.achacha.dada.engine.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SavedWordTest {
    @Test
    public void testInternals() {
        SavedWord sw = new SavedWord(TestWords.makeNoun("noun"), Noun.Form.singular.name());
        assertNotNull(sw.toString());
        assertEquals(TestWords.makeNoun("noun"), sw.getWord());
        assertEquals(Noun.Form.singular.name(), sw.getForm());
    }
}
