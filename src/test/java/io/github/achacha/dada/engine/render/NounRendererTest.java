package io.github.achacha.dada.engine.render;

import io.github.achacha.dada.engine.data.TestWords;
import io.github.achacha.dada.engine.data.WordData;
import io.github.achacha.dada.engine.hyphen.HyphenData;
import io.github.achacha.dada.integration.tags.TagSingleton;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class NounRendererTest {
    @BeforeAll
    public static void beforeClass() {
        TagSingleton.setWordData(new WordData("resource:/data/test"));
        TagSingleton.setHypenData(new HyphenData());
    }

    @Test
    public void testInternals() {
        NounRenderer tag = new NounRenderer();
        assertNotNull(tag.execute());
        assertNotNull(tag.getRendererContext().getWriter());
        assertNotNull(tag.getRendererContext().getWriter().toString());
    }

    @Test
    public void testNounAttributes() {
        NounRenderer tag = new NounRenderer();
        assertEquals("noun", tag.execute());

        tag.setArticle(ArticleMode.a);
        assertEquals("a noun", tag.execute());

        tag.setForm("plural");
        assertEquals("a nouns", tag.execute());

        tag.setCapsMode(CapsMode.first);
        assertEquals("A nouns", tag.execute());

        tag.setCapsMode(CapsMode.words);
        assertEquals("A Nouns", tag.execute());

        tag.setCapsMode(CapsMode.all);
        assertEquals("A NOUNS", tag.execute());

        tag.setForm("normal");
        tag.setArticle(ArticleMode.the);
        tag.setCapsMode(CapsMode.words);
        assertEquals("The Noun", tag.execute());
    }

    @Test
    public void testExtendedConstructor() {
        NounRenderer tag = new NounRenderer(ArticleMode.the, CapsMode.words, "normal");
        assertEquals("The Noun", tag.execute());
    }

    @Test
    public void testInvalidInput() {
        NounRenderer tag = new NounRenderer();
        assertEquals("noun", tag.execute());

        tag.setForm("invalid");
        assertEquals("noun", tag.execute());
    }

    @Test
    public void testPluralForms() {
        NounRenderer tag = new NounRenderer();
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
        NounRenderer tag = new NounRenderer();

        tag.setArticle(ArticleMode.a);
        assertEquals("an anvil", tag.execute(TestWords.makeNoun("anvil")));

        tag.setArticle(ArticleMode.a);
        assertEquals("an hour", tag.execute(TestWords.makeNoun("hour")));
    }
}
