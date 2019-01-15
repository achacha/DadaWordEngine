package io.github.achacha.dada.integration.tags;

import io.github.achacha.dada.engine.data.SavedWord;
import io.github.achacha.dada.engine.data.WordData;
import io.github.achacha.dada.engine.hyphen.HyphenData;
import io.github.achacha.dada.engine.render.ArticleMode;
import io.github.achacha.dada.engine.render.CapsMode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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
    public void testCallsByContainer() {
        NounTag tag = new NounTag();

        JspTag jspTag = Mockito.mock(JspTag.class);
        tag.setParent(jspTag);
        assertEquals(jspTag, tag.getParent());

        // Nothing to compare to, we ignore the setter since we have TLD configured to empty body
        JspFragment jspFragment = Mockito.mock(JspFragment.class);
        tag.setJspBody(jspFragment);
    }

    @Test
    public void testSave() throws IOException {
        TestJspContext jspContext = new TestJspContext();
        NounTag tag = new NounTag();

        // Save the "noun" to the JspContext provided
        tag.setJspContext(jspContext);
        tag.getWordRenderer().setSaveKey("saved");
        tag.getWordRenderer().execute();
        SavedWord savedWord = (SavedWord)jspContext.getAttribute("saved");
        assertNotNull(savedWord);
        assertEquals("noun", savedWord.getWord().getWord());
        assertNotNull(savedWord.getForm());
        assertEquals(1, jspContext.getBackingMap().size());

        // Test loading
        tag.getWordRenderer().setLoadKey("saved");
        assertEquals("noun", tag.getWordRenderer().execute());

        // Test output
        tag.doTag();
        assertEquals("noun", jspContext.getBackingJspWriter().getBackingSw().getBuffer().toString());
    }

    @Test
    public void testSaveWithForm() throws IOException {
        TestJspContext jspContext = new TestJspContext();
        NounTag tag = new NounTag();

        // Save the "noun" to the JspContext provided
        tag.setJspContext(jspContext);
        tag.getWordRenderer().setSaveKey("saved");
        tag.getWordRenderer().setForm("plural");
        tag.getWordRenderer().execute();
        SavedWord savedWord = (SavedWord)jspContext.getAttribute("saved");
        assertNotNull(savedWord);
        assertEquals("noun", savedWord.getWord().getWord());
        assertEquals("plural", savedWord.getForm());
        assertEquals(1, jspContext.getBackingMap().size());

        // Output will only take into account the current form of the tag not the saved one
        // Test loading
        tag.getWordRenderer().setLoadKey("saved");
        tag.getWordRenderer().setForm("");
        assertEquals("noun", tag.getWordRenderer().execute());

        // Test output
        tag.doTag();
        assertEquals("noun", jspContext.getBackingJspWriter().getBackingSw().getBuffer().toString());
    }

    @Test
    public void testRhyme() {
        TestJspContext jspContext = new TestJspContext();
        NounTag tag = new NounTag();

        // Save the "noun" to the JspContext provided
        tag.setJspContext(jspContext);
        tag.getWordRenderer().setSaveKey("saved");
        tag.getWordRenderer().execute();

        tag.getWordRenderer().setRhymeKey("saved");
        assertEquals("noun", tag.getWordRenderer().execute());
    }

    @Test
    public void checkAttributesSetThrough() {
        TestJspContext jspContext = new TestJspContext();
        NounTag tag = new NounTag();

        tag.setJspContext(jspContext);
        tag.setRhyme("rhyme");
        assertEquals("rhyme", tag.wordRenderer.getRhymeKey());
        tag.setSave("mySaveKey");
        assertEquals("mySaveKey", tag.wordRenderer.getSaveKey());
        tag.setLoad("myLoadKey");
        assertEquals("myLoadKey", tag.wordRenderer.getLoadKey());
        tag.setForm("Plural");
        assertEquals("plural", tag.wordRenderer.getForm());
        tag.setCapsMode("ALL");
        assertEquals(CapsMode.all, tag.wordRenderer.getCapsMode());
        tag.setArticle("tHE");
        assertEquals(ArticleMode.the, tag.wordRenderer.getArticle());
        tag.setSyllables("6");
        assertEquals(6, tag.wordRenderer.getSyllablesDesired());
    }

}
