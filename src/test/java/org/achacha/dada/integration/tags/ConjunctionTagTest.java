package org.achacha.dada.integration.tags;

import org.achacha.dada.engine.data.WordData;
import org.achacha.dada.engine.hyphen.HyphenData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ConjunctionTagTest {
    @BeforeAll
    public static void beforeClass() {
        TagSingleton.setWordData(new WordData("resource:/data/test"));
        TagSingleton.setHypenData(new HyphenData());
    }

    @Test
    public void testInternals() {
        ConjunctionTag tag = new ConjunctionTag();
        assertNotNull(tag.toString());
    }

    @Test
    public void testConjunctionForm() {
        ConjunctionTag tag = new ConjunctionTag();
        assertEquals("&", tag.execute());
    }

    @Test
    public void testExtendedConstructor() {
        ConjunctionTag tag = new ConjunctionTag("the", "first", "");
        assertEquals("The &", tag.execute());
    }
}
