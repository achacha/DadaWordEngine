package io.github.achacha.dada.engine.render;

import io.github.achacha.dada.engine.builder.SentenceRendererBuilder;
import io.github.achacha.dada.engine.data.Adverb;
import io.github.achacha.dada.engine.data.WordData;
import io.github.achacha.dada.integration.tags.GlobalData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AdverbRendererTest {
    @BeforeAll
    public static void beforeClass() {
        GlobalData.setWordData(new WordData("resource:/data/test"));
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
        assertEquals("adverbly", tag.execute());
    }

    @Test
    public void testExtendedConstructor() {
        AdverbRenderer tag = new AdverbRenderer(ArticleMode.the, CapsMode.first, null);
        assertEquals("The adverbly", tag.execute());
    }

    @Test
    public void testBuilder() {
        SentenceRendererBuilder renderers = new SentenceRendererBuilder();

        String sentence = renderers
                .adverbBuilder().withArticleMode(ArticleMode.a).withCapsMode(CapsMode.all).withRhymeWith("shoe").withSaveKey("saved_shoe").withSyllablesDesired(2).build()
                .adverbBuilder().withCapsMode(CapsMode.none).withLoadKey("saved_shoe").withRenderContext(new RenderContextToString<>(GlobalData.getWordData().getAdverbs())).withRhymeKey("saved_shoe").build()
                .execute();
        assertEquals("AN ADVERBLY adverbly", sentence);
    }
}
