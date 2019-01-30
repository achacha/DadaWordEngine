package io.github.achacha.dada.engine.data;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class NounTest {
    @Test
    public void testInternals() {
        Noun noun = TestWords.makeNoun("noun");
        assertNotNull(noun.toString());
        assertEquals(Word.Type.Noun, noun.getType());
    }

    @Test
    public void testToCsv() {
        Noun noun = TestWords.makeNoun("small pebble");
        assertEquals("small_pebble,small_pebbles", noun.toCsv());
    }

    @Test
    public void testSortingForSave() {
        List<Word> words = new ArrayList<>();
        words.add(TestWords.makeNoun("small_pebble"));
        words.add(TestWords.makeNoun("small pebble"));
        words.add(TestWords.makeNoun("small-pebble"));
        words.add(TestWords.makeNoun("small"));

        words.sort(Word::compareToForSave);

        assertEquals("small", words.get(0).getWordString());
        assertEquals("small pebble", words.get(1).getWordString());
        assertEquals("small-pebble", words.get(2).getWordString());
        assertEquals("small_pebble", words.get(3).getWordString());
    }
}
