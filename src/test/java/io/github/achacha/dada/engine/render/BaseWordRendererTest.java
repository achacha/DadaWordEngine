package io.github.achacha.dada.engine.render;

import io.github.achacha.dada.engine.builder.SentenceRendererBuilder;
import io.github.achacha.dada.engine.data.Adjective;
import io.github.achacha.dada.engine.hyphen.HyphenData;
import io.github.achacha.dada.integration.tags.GlobalData;
import io.github.achacha.dada.test.GlobalTestData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BaseWordRendererTest {
    @BeforeAll
    public static void beforeClass() {
        GlobalData.setWordData(GlobalTestData.WORD_DATA);
        GlobalData.setHypenData(new HyphenData());
    }

    @Test
    public void testRenderingList() {
        SentenceRendererBuilder tagsToRender = new SentenceRendererBuilder();
        tagsToRender.getRenderers().add(new TextRenderer("with "));
        tagsToRender.getRenderers().add(new AdjectiveRenderer(Adjective.Form.positive, ArticleMode.a, CapsMode.none, null));
        tagsToRender.getRenderers().add(new TextRenderer(" "));
        tagsToRender.getRenderers().add(new NounRenderer());

        assertEquals("with a subtle noun", tagsToRender.execute());
    }

    @Test
    public void testSyllables() {
        NounRenderer noun = new NounRenderer();
        noun.setSyllablesDesired(1);

        assertEquals("noun", noun.execute());
    }
}
