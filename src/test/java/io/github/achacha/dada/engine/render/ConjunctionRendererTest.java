package io.github.achacha.dada.engine.render;

import io.github.achacha.dada.engine.data.WordData;
import io.github.achacha.dada.engine.hyphen.HyphenData;
import io.github.achacha.dada.integration.tags.ConjunctionTag;
import io.github.achacha.dada.integration.tags.TagSingleton;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ConjunctionRendererTest {
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
        ConjunctionRenderer tag = new ConjunctionRenderer();
        assertEquals("&", tag.execute());
    }

    @Test
    public void testExtendedConstructor() {
        ConjunctionRenderer tag = new ConjunctionRenderer("the", "first", "");
        assertEquals("The &", tag.execute());
    }
}