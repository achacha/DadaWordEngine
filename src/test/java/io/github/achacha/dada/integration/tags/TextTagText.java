package io.github.achacha.dada.integration.tags;

import io.github.achacha.dada.engine.data.WordData;
import io.github.achacha.dada.engine.hyphen.HyphenData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextTagText {
    @BeforeAll
    public static void beforeClass() {
        TagSingleton.setWordData(new WordData("resource:/data/test"));
        TagSingleton.setHypenData(new HyphenData());
    }

    @Test
    public void testFromStringTag() {
        TextTag tag = new TextTag("constant");

        // All should return same thing
        assertEquals("constant", tag.generateWord().getWord());
        assertEquals("constant", tag.generateWord().getWord());
        assertEquals("constant", tag.generateWord().getWord());
        assertEquals("constant", tag.execute());

        tag.setArticle("the");
        tag.setCapMode("words");
        assertEquals("The Constant", tag.execute());
    }

    @Test
    public void testExtendedConstructor() {
        TextTag tag = new TextTag("constant", "a", "all", "");
        assertEquals("A CONSTANT", tag.execute());
    }
}
