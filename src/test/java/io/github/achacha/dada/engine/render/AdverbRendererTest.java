package io.github.achacha.dada.engine.render;

import io.github.achacha.dada.engine.data.Adverb;
import io.github.achacha.dada.engine.data.WordData;
import io.github.achacha.dada.engine.hyphen.HyphenData;
import io.github.achacha.dada.integration.tags.TagSingleton;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AdverbRendererTest {
    @BeforeAll
    public static void beforeClass() {
        TagSingleton.setWordData(new WordData("resource:/data/test"));
        TagSingleton.setHypenData(new HyphenData());
    }

    @Test
    public void testInternals() {
        AdverbRenderer tag = new AdverbRenderer();
        assertNotNull(tag.toString());
        assertEquals(Adverb.Form.none.name(), tag.getFormName());

        tag.setForm("none");
        assertEquals(Adverb.Form.none.name(), tag.getFormName());

        tag.setForm("invalid");
        assertEquals(Adverb.Form.none.name(), tag.getFormName());
    }

    @Test
    public void testAdverbForm() {
        AdverbRenderer tag = new AdverbRenderer();
        assertEquals("ADVERBLY", tag.execute());
    }

    @Test
    public void testExtendedConstructor() {
        AdverbRenderer tag = new AdverbRenderer(ArticleMode.the, CapsMode.first);
        assertEquals("The ADVERBLY", tag.execute());
    }
}
