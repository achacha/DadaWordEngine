package io.github.achacha.dada.engine.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        Pronoun us = pronouns.getWordByBase("us");
        assertNotNull(us);
        assertEquals("us", us.getWord());
        assertTrue(us.isA(Pronoun.Form.personal));
        assertTrue(us.isA(Pronoun.Form.objective));
        assertEquals(2, us.attributes.size());
        assertTrue(pronouns.getPronounsByForm(Pronoun.Form.personal).indexOf(us) >= 0);
        assertTrue(pronouns.getPronounsByForm(Pronoun.Form.objective).indexOf(us) >= 0);

        Pronoun whom = pronouns.getWordByBase("whom");
        assertNotNull(whom);
        assertEquals("whom", whom.getWord());
        assertTrue(whom.isA(Pronoun.Form.objective));
        assertTrue(whom.isA(Pronoun.Form.interrogative));
        assertTrue(whom.isA(Pronoun.Form.relative));
        assertTrue(pronouns.getPronounsByForm(Pronoun.Form.objective).indexOf(whom) >= 0);
        assertTrue(pronouns.getPronounsByForm(Pronoun.Form.interrogative).indexOf(whom) >= 0);
        assertTrue(pronouns.getPronounsByForm(Pronoun.Form.relative).indexOf(whom) >= 0);

        Pronoun those = pronouns.getWordByBase("those");
        assertNotNull(those);
        assertEquals("those", those.getWord());
        assertTrue(those.isA(Pronoun.Form.possessive));
        assertTrue(those.isA(Pronoun.Form.demonstrative));
        assertTrue(pronouns.getPronounsByForm(Pronoun.Form.possessive).indexOf(those) >= 0);
        assertTrue(pronouns.getPronounsByForm(Pronoun.Form.demonstrative).indexOf(those) >= 0);

        Pronoun such = pronouns.getWordByBase("such");
        assertNotNull(such);
        assertEquals("such", such.getWord());
        assertTrue(such.isA(Pronoun.Form.indefinite));
        assertTrue(pronouns.getPronounsByForm(Pronoun.Form.indefinite).indexOf(such) >= 0);

        // Make sure we can find a random pronoun
        Pronoun randomOne = pronouns.getRandomPronounByForm(Pronoun.Form.possessive);
        assertNotNull(randomOne);
        assertTrue(randomOne.isA(Pronoun.Form.possessive));
    }
}
