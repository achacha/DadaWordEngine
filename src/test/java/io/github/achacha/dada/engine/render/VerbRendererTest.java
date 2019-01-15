package io.github.achacha.dada.engine.render;

import io.github.achacha.dada.engine.data.TestWords;
import io.github.achacha.dada.engine.data.WordData;
import io.github.achacha.dada.engine.hyphen.HyphenData;
import io.github.achacha.dada.integration.tags.TagSingleton;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class VerbRendererTest {
    @BeforeAll
    public static void beforeClass() {
        TagSingleton.setWordData(new WordData("resource:/data/test"));
        TagSingleton.setHypenData(new HyphenData());
    }

    @Test
    public void testInternals() {
        VerbRenderer tag = new VerbRenderer();
        assertNotNull(tag.toString());
    }

    @Test
    public void testVerbForms() {
        VerbRenderer tag = new VerbRenderer();

        assertEquals("know", tag.selectWord(TestWords.makeVerb("know", "knew", "known", "knows", "knowing")));

        tag.setForm("infinitive");
        assertEquals("to know", tag.selectWord(TestWords.makeVerb("know", "knew", "known", "knows", "knowing")));
        assertEquals("to nag", tag.selectWord(TestWords.makeVerb("nag", "nagged", "nagged", "nags", "nagging")));

        tag.setForm("present");
        assertEquals("knowing", tag.selectWord(TestWords.makeVerb("know", "knew", "known", "knows", "knowing")));
        assertEquals("nagging", tag.selectWord(TestWords.makeVerb("nag", "nagged", "nagged", "nags", "nagging")));

        tag.setForm("past");
        assertEquals("knew", tag.selectWord(TestWords.makeVerb("know", "knew", "known", "knows", "knowing")));
        assertEquals("nagged", tag.selectWord(TestWords.makeVerb("nag", "nagged", "nagged", "nags", "nagging")));

        tag.setForm("pastParticiple");
        assertEquals("known", tag.selectWord(TestWords.makeVerb("know", "knew", "known", "knows", "knowing")));
        assertEquals("nagged", tag.selectWord(TestWords.makeVerb("nag", "nagged", "nagged", "nags", "nagging")));

        tag.setForm("singular");
        assertEquals("knows", tag.selectWord(TestWords.makeVerb("know", "knew", "known", "knows", "knowing")));
        assertEquals("nags", tag.selectWord(TestWords.makeVerb("nag", "nagged", "nagged", "nags", "nagging")));
    }

    @Test
    public void testExtendedConstructor() {
        VerbRenderer tag = new VerbRenderer(ArticleMode.the, CapsMode.first, "present");
        assertEquals("The swimming", tag.execute());
    }
}
