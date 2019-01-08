package io.github.achacha.dada.integration.tags;

import io.github.achacha.dada.engine.data.SavedWord;
import io.github.achacha.dada.engine.data.TestWords;
import io.github.achacha.dada.engine.data.WordData;
import io.github.achacha.dada.engine.hyphen.HyphenData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.JspTag;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Noun class also tests some BaseWordTag members
 */
public class NounTagTest {
    @BeforeAll
    public static void beforeClass() {
        TagSingleton.setWordData(new WordData("resource:/data/test"));
        TagSingleton.setHypenData(new HyphenData());
    }

    @Test
    public void testInternals() {
        NounTag tag = new NounTag();
        assertNotNull(tag.toString());
    }

    @Test
    public void testNounAttributes() {
        NounTag tag = new NounTag();
        assertEquals("noun", tag.execute());

        tag.setArticle("a");
        assertEquals("a noun", tag.execute());

        tag.setForm("plural");
        assertEquals("a nouns", tag.execute());

        tag.setCapMode("first");
        assertEquals("A nouns", tag.execute());

        tag.setCapMode("words");
        assertEquals("A Nouns", tag.execute());

        tag.setCapMode("all");
        assertEquals("A NOUNS", tag.execute());

        tag.setForm("normal");
        tag.setArticle("the");
        tag.setCapMode("words");
        assertEquals("The Noun", tag.execute());
    }

    @Test
    public void testExtendedConstructor() {
        NounTag tag = new NounTag("the", "words", "normal");
        assertEquals("The Noun", tag.execute());
    }

    @Test
    public void testInvalidInput() {
        NounTag tag = new NounTag();
        assertEquals("noun", tag.execute());

        tag.setArticle("invalid");
        assertEquals("noun", tag.execute());

        tag.setForm("invalid");
        assertEquals("noun", tag.execute());

        tag.setCapMode("invalid");
        assertEquals("noun", tag.execute());
    }

    @Test
    public void testPluralForms() {
        NounTag tag = new NounTag();
        tag.setForm("plural");
        assertEquals("taxes", tag.execute(TestWords.makeNoun("tax")));

        assertEquals("wolves", tag.execute(TestWords.makeNoun("wolf")));

        assertEquals("scarves", tag.execute(TestWords.makeNoun("scarf")));
        assertEquals("knives", tag.execute(TestWords.makeNoun("knife")));
        assertEquals("bikes", tag.execute(TestWords.makeNoun("bike")));

        assertEquals("witches", tag.execute(TestWords.makeNoun("witch")));
        assertEquals("wishes", tag.execute(TestWords.makeNoun("wish")));
        assertEquals("baths", tag.execute(TestWords.makeNoun("bath")));

        assertEquals("plays", tag.execute(TestWords.makeNoun("play")));
        assertEquals("plies", tag.execute(TestWords.makeNoun("ply")));

        assertEquals("zoos", tag.execute(TestWords.makeNoun("zoo")));
        assertEquals("bos", tag.execute(TestWords.makeNoun("bo")));
    }

    @Test
    public void testArticleAn() {
        NounTag tag = new NounTag();

        tag.setArticle("a");
        assertEquals("an anvil", tag.execute(TestWords.makeNoun("anvil")));

        tag.setArticle("a");
        assertEquals("an hour", tag.execute(TestWords.makeNoun("hour")));
    }

    @Test
    public void testSave() throws IOException, JspException {
        TestJspContext jspContext = new TestJspContext();
        NounTag tag = new NounTag();

        // Save the "noun" to the JspContext provided
        tag.setJspContext(jspContext);
        tag.setSave("saved");
        tag.execute();
        SavedWord savedWord = (SavedWord)jspContext.getAttribute("saved");
        assertNotNull(savedWord);
        assertEquals("noun", savedWord.getWord().getWord());
        assertNotNull(savedWord.getForm());
        assertEquals(1, jspContext.getBackingMap().size());

        // Test loading
        tag.setLoad("saved");
        assertEquals("noun", tag.execute());

        // Test output
        tag.doTag();
        assertEquals("noun", jspContext.getTestJspWriter().getBackingSw().getBuffer().toString());
    }

    @Test
    public void testSaveWithForm() throws IOException, JspException {
        TestJspContext jspContext = new TestJspContext();
        NounTag tag = new NounTag();

        // Save the "noun" to the JspContext provided
        tag.setJspContext(jspContext);
        tag.setSave("saved");
        tag.setForm("plural");
        tag.execute();
        SavedWord savedWord = (SavedWord)jspContext.getAttribute("saved");
        assertNotNull(savedWord);
        assertEquals("noun", savedWord.getWord().getWord());
        assertEquals("plural", savedWord.getForm());
        assertEquals(1, jspContext.getBackingMap().size());

        // Output will only take into account the current form of the tag not the saved one
        // Test loading
        tag.setLoad("saved");
        tag.setForm("");
        assertEquals("noun", tag.execute());

        // Test output
        tag.doTag();
        assertEquals("noun", jspContext.getTestJspWriter().getBackingSw().getBuffer().toString());
    }

    @Test
    public void testRhyme() {
        TestJspContext jspContext = new TestJspContext();
        NounTag tag = new NounTag();

        // Save the "noun" to the JspContext provided
        tag.setJspContext(jspContext);
        tag.setSave("saved");
        tag.execute();

        tag.setRhyme("saved");
        assertEquals("noun", tag.execute());
    }

    @Test
    public void testCallsByContainer() {
        NounTag tag = new NounTag();

        JspTag jspTag = Mockito.mock(JspTag.class);
        tag.setParent(jspTag);
        assertEquals(jspTag, tag.getParent());

        // Nothing to compare to, we ignore the setter since we have TLD configured to empty body
        JspFragment jspFragment = Mockito.mock(JspFragment.class);
        tag.setJspBody(jspFragment);
    }
}
