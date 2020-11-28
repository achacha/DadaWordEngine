package io.github.achacha.dada.engine.render;

import io.github.achacha.dada.engine.builder.SentenceRendererBuilder;
import io.github.achacha.dada.engine.data.Text;
import io.github.achacha.dada.engine.data.WordsByType;
import io.github.achacha.dada.integration.tags.GlobalData;
import io.github.achacha.dada.test.GlobalTestData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextRendererTest {
    @BeforeAll
    public static void beforeClass() {
        GlobalData.setWordData(GlobalTestData.WORD_DATA);
    }

    @Test
    public void testFromStringTag() {
        TextRenderer tag = new TextRenderer("constant");

        // All should return same thing
        assertEquals("constant", tag.generateWord().getWordString());
        assertEquals("constant", tag.generateWord().getWordString());
        assertEquals("constant", tag.generateWord().getWordString());
        assertEquals("constant", tag.execute());

        tag.setArticle(ArticleMode.the);
        tag.setCapsMode(CapsMode.words);
        assertEquals("The Constant", tag.execute());
    }

    @Test
    public void testExtendedConstructor() {
        TextRenderer tag = new TextRenderer("constant", ArticleMode.a, CapsMode.all, null);
        assertEquals("A CONSTANT", tag.execute());
        assertEquals(Text.Form.none.name(), tag.getFormName());
    }

    @Test
    public void testBuilder() {
        SentenceRendererBuilder renderers = new SentenceRendererBuilder();

        String sentence = renderers
                .textBuilder()
                    .withFallback("Option", textBaseWordRenderer -> true)
                    .withArticleMode(ArticleMode.the)
                    .withCapsMode(CapsMode.first)
                    .withRhymeWith("with")
                    .withSaveKey("saved")
                    .build()
                .text(" ")
                .textBuilder()
                    .withFallback("option")
                    .withCapsMode(CapsMode.first)
                    .withLoadKey("saved")
                    .withRenderContext(new RenderContextToString<>(WordsByType.empty()))
                    .withRhymeKey("saved").build()
                .execute();
        assertEquals("The Option Option", sentence);
    }

    @Test
    public void testPredicateFalse() {
        SentenceRendererBuilder renderers = new SentenceRendererBuilder();

        // Fallback predicate with text doesn't make sense since it's a constant, so always on
        String sentence = renderers
                .textBuilder()
                    .withFallback("ALWAYS1", textBaseWordRenderer -> false)
                    .build()
                .text(".")
                .textBuilder()
                    .withFallback("ALWAYS2", textBaseWordRenderer -> true)
                    .build()
                .text(".")
                .textBuilder()
                    .withFallback("ALWAYS3")
                    .build()
                .execute();

        assertEquals("ALWAYS1.ALWAYS2.ALWAYS3", sentence);
    }
}
