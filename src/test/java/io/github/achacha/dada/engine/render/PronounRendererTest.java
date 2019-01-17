package io.github.achacha.dada.engine.render;

import io.github.achacha.dada.engine.data.Pronoun;
import io.github.achacha.dada.engine.data.Pronouns;
import io.github.achacha.dada.engine.data.WordData;
import io.github.achacha.dada.engine.hyphen.HyphenData;
import io.github.achacha.dada.engine.phonemix.PhoneticTransformerBuilder;
import io.github.achacha.dada.integration.tags.TagSingleton;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PronounRendererTest {
    @BeforeAll
    public static void beforeClass() {
        TagSingleton.setWordData(new WordData("resource:/data/test"));
        TagSingleton.setHypenData(new HyphenData());
    }

    @Test
    public void testInternals() {
        PronounRenderer tag = new PronounRenderer();
        assertNotNull(tag.toString());
    }

    @Test
    public void testPronounDefaultTag() {
        PronounRenderer tag = new PronounRenderer();
        assertNotNull(tag.execute());
    }

    @Test
    public void testExtendedConstructor() {
        PronounRenderer tag = new PronounRenderer(Pronoun.Form.subjective, ArticleMode.none, CapsMode.first);
        assertEquals("Who", tag.execute());
    }

    @Test
    public void testPossessiveRenderer() {
        PronounRenderer tag = new PronounRenderer(Pronoun.Form.possessive, ArticleMode.none, CapsMode.none);
        assertEquals("these", tag.execute());
    }

    @Test
    public void testPersonalRenderer() {
        PronounRenderer tag = new PronounRenderer(Pronoun.Form.personal, ArticleMode.none, CapsMode.all);
        assertEquals("ME", tag.execute());
    }

    @Test
    public void testPronounRenderer() {
        Pronouns pronouns = new Pronouns(
                "resource:/data/test/pronoun.csv",
                PhoneticTransformerBuilder.builder().build(),
                PhoneticTransformerBuilder.builder().withReverse().build());

        PronounRenderer tag = new PronounRenderer(pronouns);
        String pronoun = tag.execute();
        assertNotNull(pronoun);
        assertFalse(pronoun.isEmpty());

        tag.setForm(Pronoun.Form.personal.name());
        assertEquals(Pronoun.Form.personal, tag.getForm());
        assertEquals("me", tag.execute());

        tag.setForm(Pronoun.Form.subjective.name());
        assertEquals(Pronoun.Form.subjective, tag.getForm());
        assertEquals("who", tag.execute());

        tag.setForm(Pronoun.Form.interrogative);
        assertEquals("interrogative", tag.getFormName());
        assertEquals("who", tag.execute());

        tag.setForm(Pronoun.Form.relative.name());
        assertEquals("who", tag.execute());

        tag.setForm(Pronoun.Form.possessive.name());
        assertEquals("these", tag.execute());

        tag.setForm(Pronoun.Form.demonstrative.name());
        assertEquals("these", tag.execute());

        tag.setForm(Pronoun.Form.interrogative.name());
        assertEquals("who", tag.execute());

        tag.setForm(Pronoun.Form.relative.name());
        assertEquals("who", tag.execute());

        tag.setForm(Pronoun.Form.reflexive.name());
        assertEquals("myself", tag.execute());

        tag.setForm(Pronoun.Form.reciprocal.name());
        assertEquals("each other", tag.execute());

        tag.setForm(Pronoun.Form.indefinite.name());
        assertEquals("anybody", tag.execute());
    }
}
