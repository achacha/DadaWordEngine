package io.github.achacha.dada.engine.render;

import io.github.achacha.dada.engine.base.RendererPredicates;
import io.github.achacha.dada.engine.builder.SentenceRendererBuilder;
import io.github.achacha.dada.engine.data.Adjective;
import io.github.achacha.dada.integration.tags.GlobalData;
import io.github.achacha.dada.test.GlobalTestData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AdjectiveRendererTest {
    @BeforeAll
    public static void beforeClass() {
        GlobalData.setWordData(GlobalTestData.WORD_DATA);
    }

    @Test
    public void testInternals() {
        AdjectiveRenderer tag = new AdjectiveRenderer();
        assertNotNull(tag.toString());
        assertEquals(Adjective.Form.positive.name(), tag.getFormName());
    }

    @Test
    public void renderAdjectiveForms() {
        AdjectiveRenderer tag = new AdjectiveRenderer();

        assertEquals("subtle", tag.execute());

        tag.setForm(Adjective.Form.comparative);
        assertEquals(Adjective.Form.comparative, tag.getForm());
        assertEquals("more subtle", tag.execute());

        tag.setForm(Adjective.Form.superlative);
        assertEquals("most subtle", tag.execute());

        tag.setForm(Adjective.Form.positive);
        assertEquals("positive", tag.getFormName());
        assertEquals("subtle", tag.execute());

        tag.setForm("superlative");
        assertEquals(Adjective.Form.superlative, tag.getForm());
        assertEquals("most subtle", tag.execute());
    }

    @Test
    public void testBuilder() {
        SentenceRendererBuilder renderers = new SentenceRendererBuilder();

        String sentence = renderers
                .adjectiveBuilder()
                    .withForm(Adjective.Form.superlative)
                    .withArticleMode(ArticleMode.none)
                    .withCapsMode(CapsMode.all)
                    .withRhymeWith("fly")
                    .withSyllablesDesired(3)
                    .withSaveKey("rhymes_with_fly")
                    .build()
                .text(" ")
                .adjectiveBuilder()
                    .withLoadKey("rhymes_with_fly")
                    .withCapsMode(CapsMode.first)
                    .withArticleMode(ArticleMode.the)
                    .withFallback("NEVER", RendererPredicates.falseAlways())
                    .build()
                .text(" ")
                .adjectiveBuilder()
                    .withRhymeKey("rhymes_with_fly")
                    .withRenderContext(new RenderContextToString<>(GlobalData.getWordData().getAdjectives()))
                    .withForm(Adjective.Form.comparative)
                    .withFallback("NEVER")
                    .build()
                .execute();

        assertEquals("MOST SUBTLE The most subtle more subtle", sentence);
    }
}
