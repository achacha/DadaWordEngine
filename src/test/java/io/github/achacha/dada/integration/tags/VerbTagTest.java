package io.github.achacha.dada.integration.tags;

import io.github.achacha.dada.test.GlobalTestData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class VerbTagTest {
    @BeforeAll
    public static void beforeClass() {
        GlobalData.setWordData(GlobalTestData.WORD_DATA);
    }

    @Test
    public void testInternals() {
        VerbTag tag = new VerbTag();
        assertNotNull(tag.toString());
    }

    @Test
    public void testFallback() {
        VerbTag tag = new VerbTag();
        tag.setFallback("FALLBACK");
        tag.setFallbackProbability("0.0");  // never
        assertNotEquals("FALLBACK", tag.getWordRenderer().execute());

        tag.setFallbackProbability("1.0");  // always
        assertEquals("FALLBACK", tag.getWordRenderer().execute());
    }
}
