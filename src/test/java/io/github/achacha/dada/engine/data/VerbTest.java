package io.github.achacha.dada.engine.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class VerbTest {
    @Test
    public void testInternals() {
        Verb verb = TestWords.makeVerb("know", "knew", "known", "knows", "knowing");
        assertNotNull(verb.toString());
        assertEquals(Word.Type.Verb, verb.getType());

        Verb verb2 = TestWords.makeVerb("know", "knew", "known", "knows", "knowing");
        assertEquals(verb2, verb);
        assertEquals(0, verb.compareTo(verb2));
        assertEquals(0, verb.compareToForSave(verb2));
    }

    @Test
    public void testToCsv() {
        Verb verb = TestWords.makeVerb("silent run","silent ran","silently ran","silently runs","silently running");
        assertEquals("silent_run,silent_ran,silently_ran,silently_runs,silently_running", verb.toCsv());
    }

}
