package io.github.achacha.dada.engine.render;

import io.github.achacha.dada.engine.builder.SentenceRendererBuilder;
import io.github.achacha.dada.engine.data.Noun;
import io.github.achacha.dada.engine.data.TestWords;
import io.github.achacha.dada.engine.data.WordData;
import io.github.achacha.dada.engine.hyphen.HyphenData;
import io.github.achacha.dada.integration.tags.GlobalData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class NounRendererTest {
    @BeforeAll
    public static void beforeClass() {
        GlobalData.setWordData(new WordData("resource:/data/test"));
        GlobalData.setHypenData(new HyphenData());
    }

    @Test
    public void testInternals() {
        NounRenderer tag = new NounRenderer();
        assertNotNull(tag.execute());
        assertNotNull(tag.getRendererContext().getWriter());
        assertNotNull(tag.getRendererContext().getWriter().toString());
        assertEquals(Noun.Form.singular.name(), tag.getFormName());
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

        tag.setForm("singular");
        tag.setArticle(ArticleMode.the);
        tag.setCapsMode(CapsMode.words);
        assertEquals("The Noun", tag.execute());

        tag.setForm(Noun.Form.plural);
        assertEquals("The Nouns", tag.execute());
    }

    @Test
    public void testExtendedConstructor() {
        NounRenderer tag = new NounRenderer(Noun.Form.singular, ArticleMode.the, CapsMode.words, null);
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

    @Test
    public void testRhymeWith() {
        NounRenderer tag = new NounRenderer();

        tag.setRhymeWith("down");   // Since the only word we have is 'noun', this should rhyme with it
        assertEquals("noun", tag.execute());
    }

    @Test
    public void testBuilder() {
        SentenceRendererBuilder renderers = new SentenceRendererBuilder();

        String sentence = renderers
                .nounBuilder()
                    .withForm(Noun.Form.plural)
                    .withArticleMode(ArticleMode.the)
                    .withCapsMode(CapsMode.first)
                    .withRhymeWith("fly")
                    .withSyllablesDesired(3)
                    .withSaveKey("rhymes_with_fly")
                    .build()
                .nounBuilder()
                    .withLoadKey("rhymes_with_fly")
                    .withCapsMode(CapsMode.first)
                    .withArticleMode(ArticleMode.none)
                    .build()
                .nounBuilder()
                    .withRhymeKey("rhymes_with_fly")
                    .withRenderContext(new RenderContextToString<>(GlobalData.getWordData().getNouns()))
                    .withForm(Noun.Form.singular)
                    .withCapsMode(CapsMode.all)
                    .build()
                .execute();

        assertEquals("The nouns Noun NOUN", sentence);
    }
}
