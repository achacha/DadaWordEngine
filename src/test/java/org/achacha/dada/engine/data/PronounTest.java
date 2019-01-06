package org.achacha.dada.engine.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PronounTest {
    @Test
    public void testInternals() {
        Pronouns pronouns = new Pronouns();
        assertNotNull(pronouns.toString());
    }
}
