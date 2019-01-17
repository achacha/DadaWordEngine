package io.github.achacha.dada.engine.render;

import io.github.achacha.dada.engine.data.Preposition;
import io.github.achacha.dada.engine.data.WordData;
import io.github.achacha.dada.engine.hyphen.HyphenData;
import io.github.achacha.dada.integration.tags.TagSingleton;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PrepositionRendererTest {
    @BeforeAll
    public static void beforeClass() {
        TagSingleton.setWordData(new WordData("resource:/data/test"));
        TagSingleton.setHypenData(new HyphenData());
    }

    @Test
    public void testInternals() {
        PrepositionRenderer tag = new PrepositionRenderer();
        assertNotNull(tag.toString());
        assertEquals(Preposition.Form.none.name(), tag.getFormName());

        tag.setForm("none");
        assertEquals(Preposition.Form.none.name(), tag.getFormName());

        tag.setForm("invalid");
        assertEquals(Preposition.Form.none.name(), tag.getFormName());
    }

    @Test
    public void testConjunctionForm() {
        PrepositionRenderer tag = new PrepositionRenderer();
        assertEquals("on", tag.execute());
    }

    @Test
    public void testExtendedConstructor() {
        PrepositionRenderer tag = new PrepositionRenderer(ArticleMode.none, CapsMode.first);
        assertEquals("On", tag.execute());
    }
}
