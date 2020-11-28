package io.github.achacha.dada.integration.tags;

import io.github.achacha.dada.test.GlobalTestData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextTagText {
    @BeforeAll
    public static void beforeClass() {
        GlobalData.setWordData(GlobalTestData.WORD_DATA);
    }

    @Test
    public void testInternal() {
        TextTag tag = new TextTag();
        assertEquals("", tag.getWordRenderer().execute());

        tag = new TextTag("some text");
        assertEquals("some text", tag.getWordRenderer().execute());
    }
}
