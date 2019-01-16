package io.github.achacha.dada.engine.render;

import io.github.achacha.dada.engine.data.Adjective;
import io.github.achacha.dada.engine.data.WordData;
import io.github.achacha.dada.engine.hyphen.HyphenData;
import io.github.achacha.dada.integration.tags.TagSingleton;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BaseWordRendererTest {
    @BeforeAll
    public static void beforeClass() {
        TagSingleton.setWordData(new WordData("resource:/data/test"));
        TagSingleton.setHypenData(new HyphenData());
    }

    @Test
    public void testRenderingList() {
        SentenceRenderer tagsToRender = new SentenceRenderer();
        tagsToRender.add(new TextRenderer("with"));
        tagsToRender.add(new AdjectiveRenderer(Adjective.Form.positive, ArticleMode.a, CapsMode.none));
        tagsToRender.add(new NounRenderer());

        assertEquals("with a subtle noun", tagsToRender.execute());
    }

    @Test
    public void testSyllables() {
        NounRenderer noun = new NounRenderer();
        noun.setSyllablesDesired(1);

        assertEquals("noun", noun.execute());
    }
}
