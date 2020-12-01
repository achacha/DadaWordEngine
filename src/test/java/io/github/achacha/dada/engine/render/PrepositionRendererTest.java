package io.github.achacha.dada.engine.render;

import io.github.achacha.dada.engine.base.RendererPredicates;
import io.github.achacha.dada.engine.builder.SentenceRendererBuilder;
import io.github.achacha.dada.engine.data.Preposition;
import io.github.achacha.dada.integration.tags.GlobalData;
import io.github.achacha.dada.test.GlobalTestData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PrepositionRendererTest {
    @BeforeAll
    public static void beforeClass() {
        GlobalData.setWordData(GlobalTestData.WORD_DATA);
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
        PrepositionRenderer tag = new PrepositionRenderer(ArticleMode.none, CapsMode.first, null);
        assertEquals("On", tag.execute());
    }

    @Test
    public void testBuilder() {
        SentenceRendererBuilder renderers = new SentenceRendererBuilder();

        String sentence = renderers
                .prepositionBuilder()
                    .withArticleMode(ArticleMode.a)
                    .withCapsMode(CapsMode.first)
                    .withRhymeWith("with")
                    .withSaveKey("saved")
                    .withSyllablesDesired(2)
                    .withFallback("NEVER", RendererPredicates.falseAlways())
                    .build()
                .text(" ")
                .prepositionBuilder()
                    .withCapsMode(CapsMode.all)
                    .withLoadKey("saved")
                    .withRenderContext(new RenderContextToString<>(GlobalData.getWordData().getPrepositions()))
                    .withRhymeKey("saved")
                    .withFallback("NEVER")
                    .build()
                .execute();

        assertEquals("An on ON", sentence);
    }
}
