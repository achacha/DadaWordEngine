package io.github.achacha.dada.integration.tags;

import io.github.achacha.dada.engine.data.WordData;
import io.github.achacha.dada.engine.hyphen.HyphenData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BaseWordTagTest {
    @BeforeAll
    public static void beforeClass() {
        TagSingleton.setWordData(new WordData("resource:/data/test"));
        TagSingleton.setHypenData(new HyphenData());
    }

    @Test
    public void testRenderingList() {
        TagSentence tagsToRender = new TagSentence();
        tagsToRender.add(new TextTag("with"));
        tagsToRender.add(new AdjectiveTag("a", "", ""));
        tagsToRender.add(new NounTag());

        assertEquals("with a subtle noun", tagsToRender.execute());
    }

    @Test
    public void testSyllables() {
        NounTag noun = new NounTag();
        noun.setSyllables("1");

        assertEquals("noun", noun.execute());
    }
}
