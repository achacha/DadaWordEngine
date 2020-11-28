package io.github.achacha.dada.integration.tags;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PronounTagTest {
    @Test
    public void testInternals() {
        PronounTag tag = new PronounTag();
        assertNotNull(tag.toString());
        assertNotNull(tag.getWordRenderer().execute());
    }
}
