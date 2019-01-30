package io.github.achacha.dada.engine.builder;

import io.github.achacha.dada.engine.data.Adjective;
import io.github.achacha.dada.engine.data.Adverb;
import io.github.achacha.dada.engine.data.Conjunction;
import io.github.achacha.dada.engine.data.Noun;
import io.github.achacha.dada.engine.data.Preposition;
import io.github.achacha.dada.engine.data.Pronoun;
import io.github.achacha.dada.engine.data.Text;
import io.github.achacha.dada.engine.data.Verb;
import io.github.achacha.dada.engine.data.Word;
import io.github.achacha.dada.engine.data.WordData;
import io.github.achacha.dada.integration.tags.GlobalData;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SentenceTest {
    private WordData wordData = new WordData("resource:/data/test_parser");

    @Test
    public void testDefaultConstructor() {
        Sentence sentence = new Sentence();
        assertEquals(GlobalData.getWordData(), sentence.wordData);
    }

    @Test
    public void testParsingIgnoreUnknownWords() {
        Sentence s = new Sentence(wordData, false);
        s.parse("I patiently sat and drank on the table near the biggest cups");

        assertEquals(10, s.getWords().size());

        assertEquals("i", s.getWords().get(0).getWord().getWordString());
        assertTrue(s.getWords().get(0).getWord() instanceof Pronoun);

        assertEquals("patiently", s.getWords().get(1).getWord().getWordString());
        assertTrue(s.getWords().get(1).getWord() instanceof Adverb);

        assertEquals("sit", s.getWords().get(2).getWord().getWordString());
        assertTrue(s.getWords().get(2).getWord() instanceof Verb);
        assertEquals(Verb.Form.pastparticiple.name(), s.getWords().get(2).getFormName());

        assertEquals("and", s.getWords().get(3).getWord().getWordString());
        assertTrue(s.getWords().get(3).getWord() instanceof Conjunction);

        assertEquals("drink", s.getWords().get(4).getWord().getWordString());
        assertTrue(s.getWords().get(4).getWord() instanceof Verb);
        assertEquals(Verb.Form.past.name(), s.getWords().get(4).getFormName());

        assertEquals("on", s.getWords().get(5).getWord().getWordString());
        assertTrue(s.getWords().get(5).getWord() instanceof Preposition);

        assertEquals("table", s.getWords().get(6).getWord().getWordString());
        assertTrue(s.getWords().get(6).getWord() instanceof Noun);
        assertEquals(Noun.Form.singular.name(), s.getWords().get(6).getFormName());

        assertEquals("near", s.getWords().get(7).getWord().getWordString());
        assertTrue(s.getWords().get(7).getWord() instanceof Preposition);

        assertEquals("big", s.getWords().get(8).getWord().getWordString());
        assertTrue(s.getWords().get(8).getWord() instanceof Adjective);
        assertEquals(Adjective.Form.superlative.name(), s.getWords().get(8).getFormName());

        assertEquals("cup", s.getWords().get(9).getWord().getWordString());
        assertTrue(s.getWords().get(9).getWord() instanceof Noun);
        assertEquals(Noun.Form.plural.name(), s.getWords().get(9).getFormName());
    }

    @Test
    public void testParsingWithText() {
        Sentence s = new Sentence(wordData);
        assertTrue(s.isAddUnknownAsText());

        s.parse("Parsing unknown words?");

        assertEquals(1, s.getWords().size());
        assertEquals("Parsing unknown words?", s.toString());
        assertEquals("Parsing unknown words?", s.execute());
    }

    @Test
    public void testOutputsAndStringResourcePath() {
        Sentence s = new Sentence("resource:/data/test_parser");

        s.parse("shoe");

        assertEquals(1, s.getWords().size());
        assertEquals("shoe", s.toString());
        assertTrue(StringUtils.isNotBlank(s.execute()));
        assertTrue(StringUtils.isNotBlank(s.toString()));
        assertTrue(StringUtils.isNotBlank(s.toStringStructure()));
    }

    @Test
    public void testParsingWithSeparators() {
        Sentence s = new Sentence(wordData);

        s.parse("+1 cup, no table is no-table!\nBig shoe...");

        assertEquals(7, s.getWords().size());
        assertEquals("+1 cup, no table is no-table!\nbig shoe...", s.toString());
        assertTrue(StringUtils.isNotBlank(s.execute()));
        assertTrue(StringUtils.isNotBlank(s.toString()));
        assertTrue(StringUtils.isNotBlank(s.toStringStructure()));
    }

    @Test
    public void testWithSeparator() {
        Sentence s = new Sentence(wordData)
                .addUnknownAsText()
                .parse("table, table\n  table");

        assertEquals("table, table\n  table", s.toString());
    }

    @Test
    public void parsingWithUnknown() {
        Sentence s = new Sentence(wordData)
                .addUnknownAsText()
                .parse("UNKADD table");

        // Unknown text is considered constant text so it will contain trailing whitespaces
        assertEquals(2, s.getWords().size());
        assertEquals(Text.class, s.getWords().get(0).getWord().getClass());
        assertEquals("UNKADD ", s.getWords().get(0).getWord().getWordString());
        assertEquals(Word.Type.Unknown, s.getWords().get(0).getWord().getType());

        assertEquals("table", s.getWords().get(1).getWord().getWordString());
        assertEquals(Noun.class, s.getWords().get(1).getWord().getClass());
        assertEquals(Word.Type.Noun, s.getWords().get(1).getWord().getType());

        assertEquals("UNKADD table", s.toString());
    }

    @Test
    public void parsingWithoutUnknown() {
        Sentence s = new Sentence(wordData)
                .ignoreUnknown()
                .parse("UNKNOADD table UNKNOADD");

        assertEquals(1, s.getWords().size());
        assertEquals("table", s.getWords().get(0).getWord().getWordString());
        assertEquals(Noun.class, s.getWords().get(0).getWord().getClass());
        assertEquals(Word.Type.Noun, s.getWords().get(0).getWord().getType());

        assertEquals("table", s.toString());
    }
}
