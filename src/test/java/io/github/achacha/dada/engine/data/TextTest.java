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
        ArrayList<String> parts = new ArrayList();
        parts.add("text 1");
        parts.add("text 2");

        Text text = new Text(parts);
        assertEquals("text 1", text.getWordString());
    }
}
