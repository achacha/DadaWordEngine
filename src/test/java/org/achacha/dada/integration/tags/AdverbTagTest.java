package org.achacha.dada.integration.tags;

import org.achacha.dada.engine.data.WordData;
import org.achacha.dada.engine.hyphen.HyphenData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AdverbTagTest {
    @BeforeAll
    public static void beforeClass() {
        TagSingleton.setWordData(new WordData("resource:/data/test"));
        TagSingleton.setHypenData(new HyphenData());
    }

    @Test
    public void testInternals() {
        AdverbTag tag = new AdverbTag();
        assertNotNull(tag.toString());
    }

    @Test
    public void testAdverbForm() {
        AdverbTag tag = new AdverbTag();
        assertEquals("ADVERBLY", tag.execute());
    }

    @Test
    public void testExtendedConstructor() {
        AdverbTag tag = new AdverbTag("the", "first", "");
        assertEquals("The ADVERBLY", tag.execute());
    }
}
