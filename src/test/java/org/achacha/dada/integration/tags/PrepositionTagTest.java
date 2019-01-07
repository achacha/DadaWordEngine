package org.achacha.dada.integration.tags;

import org.achacha.dada.engine.data.WordData;
import org.achacha.dada.engine.hyphen.HyphenData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PrepositionTagTest {
    @BeforeAll
    public static void beforeClass() {
        TagSingleton.setWordData(new WordData("resource:/data/test"));
        TagSingleton.setHypenData(new HyphenData());
    }

    @Test
    public void testInternals() {
        PrepositionTag tag = new PrepositionTag();
        assertNotNull(tag.toString());
    }

    @Test
    public void testConjunctionForm() {
        PrepositionTag tag = new PrepositionTag();
        assertEquals("on", tag.execute());
    }

    @Test
    public void testExtendedConstructor() {
        PrepositionTag tag = new PrepositionTag("", "first", "");
        assertEquals("On", tag.execute());
    }
}
