package org.achacha.dada.integration.tags;

import org.achacha.dada.engine.data.Adjective;
import org.achacha.dada.engine.data.TestWords;
import org.achacha.dada.engine.data.WordData;
import org.achacha.dada.engine.hyphen.HyphenData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AdjectiveTagTest {
    @BeforeAll
    public static void beforeClass() {
        TagSingleton.setWordData(new WordData("resource:/data/test"));
        TagSingleton.setHypenData(new HyphenData());
    }

    @Test
    public void testInternals() {
        AdjectiveTag tag = new AdjectiveTag();
        assertNotNull(tag.toString());
    }

    @Test
    public void testAdjectiveForm() {
        AdjectiveTag tag = new AdjectiveTag();
        Adjective adjective = TestWords.makeAdjective("good", "better", "best");

        assertEquals("good", tag.selectWord(adjective));

        tag.setForm("invalid");
        assertEquals("good", tag.selectWord(adjective));

        tag.setForm("er");
        assertEquals("better", tag.selectWord(adjective));

        tag.setForm("est");
        assertEquals("best", tag.selectWord(adjective));

        tag.setForm("invalid");
        assertEquals("good", tag.selectWord(adjective));
    }

    @Test
    public void testExtendedConstructor() {
        AdjectiveTag tag = new AdjectiveTag("the", "words", "est");
        assertEquals("The Most Subtle", tag.execute());
    }
}
