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
        assertEquals("constant", tag.generateWord().getWord());
        assertEquals("constant", tag.generateWord().getWord());
        assertEquals("constant", tag.generateWord().getWord());
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
                .textBuilder().withText("Option").withArticleMode(ArticleMode.the).withCapsMode(CapsMode.first).withRhymeWith("with").withSaveKey("saved").build()
                .text(" ")
                .textBuilder().withText("option").withCapsMode(CapsMode.first).withLoadKey("saved").withRenderContext(new RenderContextToString<>(WordsByType.empty())).withRhymeKey("saved").build()
                .execute();
        assertEquals("The Option Option", sentence);
    }
}
