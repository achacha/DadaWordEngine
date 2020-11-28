package io.github.achacha.dada.engine.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PronounsTest {
    @Test
    public void testLoadingPronoun() {
        // Check that pronouns collection loads all the words
        Pronouns pronouns = new Pronouns();
        assertEquals("resource:/data/default/"+ Word.Type.Pronoun.getTypeName()+".csv", pronouns.getResourcePath());
        assertEquals(Word.Type.Pronoun, pronouns.getType());
        assertEquals(73, pronouns.getWordsData().size());

        // Check individual pronoun forms for correctly indexing
        Pronoun me = pronouns.getWordByBase("me");
        assertNotNull(me);
        assertNotNull(me.toString());
        assertEquals(Pronoun.Type.Pronoun, me.getType());
        assertEquals("me", me.getWordString());
        assertEquals(3, me.attributes.size());
        assertTrue(me.isA(Pronoun.Form.objective));
        assertTrue(me.isA(Pronoun.Form.personal));
        assertTrue(me.isA(Pronoun.Form.singular));
        assertFalse(me.isA(Pronoun.Form.plural));
        assertTrue(pronouns.getPronounsByForm(Pronoun.Form.singular).contains(me));
        assertFalse(pronouns.getPronounsByForm(Pronoun.Form.plural).contains(me));

        Pronoun us = pronouns.getWordByBase("us");
        assertNotNull(us);
        assertNotNull(us.toString());
        assertEquals(Pronoun.Type.Pronoun, us.getType());
        assertEquals("us", us.getWordString());
        assertEquals(3, us.attributes.size());
        assertTrue(us.isA(Pronoun.Form.personal));
        assertTrue(us.isA(Pronoun.Form.objective));
        assertFalse(us.isA(Pronoun.Form.singular));
        assertTrue(us.isA(Pronoun.Form.plural));
        assertTrue(pronouns.getPronounsByForm(Pronoun.Form.personal).contains(us));
        assertTrue(pronouns.getPronounsByForm(Pronoun.Form.objective).contains(us));
        assertFalse(pronouns.getPronounsByForm(Pronoun.Form.singular).contains(us));
        assertTrue(pronouns.getPronounsByForm(Pronoun.Form.plural).contains(us));

        Pronoun whom = pronouns.getWordByBase("whom");
        assertNotNull(whom);
        assertEquals("whom", whom.getWordString());
        assertEquals(4, whom.attributes.size());
        assertTrue(whom.isA(Pronoun.Form.objective));
        assertTrue(whom.isA(Pronoun.Form.interrogative));
        assertTrue(whom.isA(Pronoun.Form.relative));
        assertTrue(whom.isA(Pronoun.Form.singular));
        assertFalse(whom.isA(Pronoun.Form.plural));
        assertTrue(pronouns.getPronounsByForm(Pronoun.Form.objective).contains(whom));
        assertTrue(pronouns.getPronounsByForm(Pronoun.Form.interrogative).contains(whom));
        assertTrue(pronouns.getPronounsByForm(Pronoun.Form.relative).contains(whom));
        assertTrue(pronouns.getPronounsByForm(Pronoun.Form.singular).contains(whom));
        assertFalse(pronouns.getPronounsByForm(Pronoun.Form.plural).contains(whom));

        Pronoun those = pronouns.getWordByBase("those");
        assertNotNull(those);
        assertEquals("those", those.getWordString());
        assertTrue(those.isA(Pronoun.Form.possessive));
        assertTrue(those.isA(Pronoun.Form.demonstrative));
        assertTrue(those.isA(Pronoun.Form.plural));
        assertTrue(pronouns.getPronounsByForm(Pronoun.Form.possessive).contains(those));
        assertTrue(pronouns.getPronounsByForm(Pronoun.Form.demonstrative).contains(those));

        Pronoun such = pronouns.getWordByBase("such");
        assertNotNull(such);
        assertEquals("such", such.getWordString());
        assertTrue(such.isA(Pronoun.Form.indefinite));
        assertTrue(pronouns.getPronounsByForm(Pronoun.Form.indefinite).contains(such));
        assertFalse(pronouns.getPronounsByForm(Pronoun.Form.singular).contains(such));
        assertFalse(pronouns.getPronounsByForm(Pronoun.Form.plural).contains(such));

        // Make sure we can find a random pronoun
        Pronoun randomOne = pronouns.getRandomPronounByForm(Pronoun.Form.possessive);
        assertNotNull(randomOne);
        assertTrue(randomOne.isA(Pronoun.Form.possessive));
    }
}
