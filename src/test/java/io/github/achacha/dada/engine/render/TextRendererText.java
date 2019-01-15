package io.github.achacha.dada.engine.render;

import io.github.achacha.dada.engine.data.WordData;
import io.github.achacha.dada.engine.hyphen.HyphenData;
import io.github.achacha.dada.integration.tags.TagSingleton;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextRendererText {
    @BeforeAll
    public static void beforeClass() {
        TagSingleton.setWordData(new WordData("resource:/data/test"));
        TagSingleton.setHypenData(new HyphenData());
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
        TextRenderer tag = new TextRenderer("constant", ArticleMode.a, CapsMode.all, "");
        assertEquals("A CONSTANT", tag.execute());
    }
}
