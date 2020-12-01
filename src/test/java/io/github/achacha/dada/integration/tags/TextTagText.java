package io.github.achacha.dada.integration.tags;

import io.github.achacha.dada.test.GlobalTestData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TextTagText {
    @BeforeAll
    public static void beforeClass() {
        GlobalData.setWordData(GlobalTestData.WORD_DATA);
    }

    @Test
    public void testInternal() {
        TextTag tag = new TextTag();
        assertNotNull(tag.toString());
        assertEquals("", tag.getWordRenderer().execute());

        tag = new TextTag("some text");
        assertEquals("some text", tag.getWordRenderer().execute());
    }

    @Test
    public void testTagRender() throws IOException {
        TestJspContext jspContext = new TestJspContext();
        TextTag tag = new TextTag("ConstantText");

        // Save the "noun" to the JspContext provided
        tag.setJspContext(jspContext);

        // Test output
        tag.doTag();
        assertEquals("ConstantText", jspContext.getBackingJspWriter().getBackingSw().getBuffer().toString());
    }

    @Test
    public void testProbablityRange() {
        TextTag tag = new TextTag("something");
        tag.setFallbackProbability("1.0");
        assertTrue(tag.getWordRenderer().getFallbackPredicate().test(tag.getWordRenderer()));

        assertThrows(IllegalArgumentException.class, () -> tag.setFallbackProbability("-0.1"));
        assertThrows(IllegalArgumentException.class, () -> tag.setFallbackProbability("1.1"));
        assertThrows(IllegalArgumentException.class, () -> tag.setFallbackProbability("NAN"));
    }
}
