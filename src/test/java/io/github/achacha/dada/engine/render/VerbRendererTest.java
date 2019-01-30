package io.github.achacha.dada.engine.render;

import io.github.achacha.dada.engine.builder.SentenceRendererBuilder;
import io.github.achacha.dada.engine.data.TestWords;
import io.github.achacha.dada.engine.data.Verb;
import io.github.achacha.dada.integration.tags.GlobalData;
import io.github.achacha.dada.test.GlobalTestData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class VerbRendererTest {
    @BeforeAll
    public static void beforeClass() {
        GlobalData.setWordData(GlobalTestData.WORD_DATA);
    }

    @Test
    public void testInternals() {
        VerbRenderer tag = new VerbRenderer();
        assertNotNull(tag.toString());
        assertEquals(Verb.Form.base.name(), tag.getFormName());
    }

    @Test
    public void testVerbForms() {
        VerbRenderer tag = new VerbRenderer();

        assertEquals(Verb.Form.base, tag.getForm());
        assertEquals("know", tag.selectWord(TestWords.makeVerb("know", "knew", "known", "knows", "knowing")));

        tag.setForm("infinitive");
        assertEquals(Verb.Form.infinitive, tag.getForm());
        assertEquals("to know", tag.selectWord(TestWords.makeVerb("know", "knew", "known", "knows", "knowing")));
        assertEquals("to nag", tag.selectWord(TestWords.makeVerb("nag", "nagged", "nagged", "nags", "nagging")));

        tag.setForm(Verb.Form.present);
        assertEquals("present", tag.getFormName());
        assertEquals("knowing", tag.selectWord(TestWords.makeVerb("know", "knew", "known", "knows", "knowing")));
        assertEquals("nagging", tag.selectWord(TestWords.makeVerb("nag", "nagged", "nagged", "nags", "nagging")));

        tag.setForm(Verb.Form.past);
        assertEquals("knew", tag.selectWord(TestWords.makeVerb("know", "knew", "known", "knows", "knowing")));
        assertEquals("nagged", tag.selectWord(TestWords.makeVerb("nag", "nagged", "nagged", "nags", "nagging")));

        tag.setForm("pastParticiple");
        assertEquals(Verb.Form.pastparticiple, tag.getForm());
        assertEquals("pastparticiple", tag.getFormName());
        assertEquals("known", tag.selectWord(TestWords.makeVerb("know", "knew", "known", "knows", "knowing")));
        assertEquals("nagged", tag.selectWord(TestWords.makeVerb("nag", "nagged", "nagged", "nags", "nagging")));

        tag.setForm("singular");
        assertEquals("knows", tag.selectWord(TestWords.makeVerb("know", "knew", "known", "knows", "knowing")));
        assertEquals("nags", tag.selectWord(TestWords.makeVerb("nag", "nagged", "nagged", "nags", "nagging")));
    }

    @Test
    public void testExtendedConstructor() {
        VerbRenderer tag = new VerbRenderer(Verb.Form.present, ArticleMode.the, CapsMode.first, null);
        assertEquals("The swimming", tag.execute());
    }

    @Test
    public void testBuilder() {
        SentenceRendererBuilder renderers = new SentenceRendererBuilder();

        String sentence = renderers
                .verbBuilder()
                    .withForm(Verb.Form.past)
                    .withArticleMode(ArticleMode.the)
                    .withCapsMode(CapsMode.first)
                    .withRhymeWith("fly")
                    .withSyllablesDesired(3)
                    .withSaveKey("rhymes_with_fly")
                    .build()
                .text(" ")
                .verbBuilder()
                    .withForm(Verb.Form.infinitive)
                    .withLoadKey("rhymes_with_fly")
                    .withCapsMode(CapsMode.first)
                    .withArticleMode(ArticleMode.none)
                    .build()
                .text(" ")
                .verbBuilder()
                    .withForm(Verb.Form.pastparticiple)
                    .withRhymeKey("rhymes_with_fly")
                    .withRenderContext(new RenderContextToString<>(GlobalData.getWordData().getVerbs()))
                    .withCapsMode(CapsMode.all)
                    .build()
                .execute();

        assertEquals("The swam Swam SWUM", sentence);
    }

}
