package io.github.achacha.dada.integration.tags;

import io.github.achacha.dada.test.GlobalTestData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AdjectiveTagTest {
    @BeforeAll
    public static void beforeClass() {
        GlobalData.setWordData(GlobalTestData.WORD_DATA);
    }

    @Test
    public void testInternals() {
        AdjectiveTag tag = new AdjectiveTag();
        assertNotNull(tag.toString());
        assertNotNull(tag.getWordRenderer().execute());
    }
}
