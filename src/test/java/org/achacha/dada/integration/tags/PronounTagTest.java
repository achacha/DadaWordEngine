package org.achacha.dada.integration.tags;

import org.achacha.dada.engine.data.Pronoun;
import org.achacha.dada.engine.data.Pronouns;
import org.achacha.dada.engine.phonemix.PhoneticTransformerBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PronounTagTest {
    @Test
    public void testInternals() {
        PronounTag tag = new PronounTag();
        assertNotNull(tag.toString());
    }

    @Test
    public void testPronounDefaultTag() {
        PronounTag tag = new PronounTag();
        assertNotNull(tag.execute());
    }

    @Test
    public void testPronounTag() {
        Pronouns pronouns = new Pronouns(
                "resource:/data/test/pronoun.csv",
                PhoneticTransformerBuilder.builder().build(),
                PhoneticTransformerBuilder.builder().withReverse().build());

        PronounTag tag = new PronounTag(pronouns);
        String pronoun = tag.execute();
        assertNotNull(pronoun);
        assertFalse(pronoun.isEmpty());

        tag.setForm(Pronoun.Form.personal.name());
        assertEquals("me", tag.execute());

        tag.setForm(Pronoun.Form.subjective.name());
        assertEquals("who", tag.execute());

        tag.setForm(Pronoun.Form.interrogative.name());
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
