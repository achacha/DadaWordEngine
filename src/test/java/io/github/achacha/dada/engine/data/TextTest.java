package io.github.achacha.dada.engine.data;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TextTest {
    @Test
    public void testInternals() {
        Text text = new Text("text 1");
        assertNotNull(text.toString());
        assertEquals("text 1", text.getWordString());
        assertEquals(Word.Type.Unknown, text.getType());
    }

    @Test
    public void testParseNewConstructor() {
        ArrayList<String> parts = new ArrayList<>();
        parts.add("text 1");
        parts.add("text 2");  // This should be ignoted

        Text text = new Text(parts);
        assertEquals("text 1", text.getWordString());
        // none is always first in the parts for the text, text2 is ignored since it's one too many
        assertEquals("[(none,text 1)]", text.getAllForms().toString());
    }
}
