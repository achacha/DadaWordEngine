package io.github.achacha.dada.engine.render;

import io.github.achacha.dada.engine.builder.SentenceRendererBuilder;
import io.github.achacha.dada.engine.data.Conjunction;
import io.github.achacha.dada.integration.tags.GlobalData;
import io.github.achacha.dada.test.GlobalTestData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ConjunctionRendererTest {
    @BeforeAll
    public static void beforeClass() {
        GlobalData.setWordData(GlobalTestData.WORD_DATA);
    }

    @Test
    public void testInternals() {
        ConjunctionRenderer tag = new ConjunctionRenderer();
        assertNotNull(tag.toString());
        assertEquals(Conjunction.Form.none.name(), tag.getFormName());

        tag.setForm("none");
        assertEquals(Conjunction.Form.none.name(), tag.getFormName());

        tag.setForm("invalid");
        assertEquals(Conjunction.Form.none.name(), tag.getFormName());
    }

    @Test
    public void testConjunctionForm() {
        ConjunctionRenderer tag = new ConjunctionRenderer();
        assertEquals("&", tag.execute());
    }

    @Test
    public void testExtendedConstructor() {
        ConjunctionRenderer tag = new ConjunctionRenderer(ArticleMode.the, CapsMode.first, null);
        assertEquals("The &", tag.execute());
    }

    @Test
    public void testBuilder() {
        SentenceRendererBuilder renderers = new SentenceRendererBuilder();

        String sentence = renderers
                .conjunctionBuilder().withArticleMode(ArticleMode.a).withCapsMode(CapsMode.all).withRhymeWith("with").withSaveKey("saved").withSyllablesDesired(2).build()
                .conjunctionBuilder().withCapsMode(CapsMode.none).withLoadKey("saved").withRenderContext(new RenderContextToString<>(GlobalData.getWordData().getConjunctions())).withRhymeKey("saved").build()
                .execute();
        assertEquals("A &&", sentence);
    }
}
