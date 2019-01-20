package io.github.achacha.dada.integration.tags;

import io.github.achacha.dada.engine.data.WordData;
import io.github.achacha.dada.engine.hyphen.HyphenData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AdjectiveTagTest {
    @BeforeAll
    public static void beforeClass() {
        GlobalData.setWordData(new WordData("resource:/data/test"));
        GlobalData.setHypenData(new HyphenData());
    }

    @Test
    public void testInternals() {
        AdjectiveTag tag = new AdjectiveTag();
        assertNotNull(tag.toString());
    }
}
