package io.github.achacha.dada.engine.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ConjunctionTest {
    @Test
    public void testInternals() {
        Conjunction conjunction = TestWords.makeConjunction("and");
        assertNotNull(conjunction.toString());
    }
}
