package io.github.achacha.dada.engine.base;

import io.github.achacha.dada.engine.data.TestWords;
import io.github.achacha.dada.engine.data.Text;
import io.github.achacha.dada.engine.render.AdjectiveRenderer;
import io.github.achacha.dada.engine.render.AdverbRenderer;
import io.github.achacha.dada.engine.render.BaseWordRenderer;
import io.github.achacha.dada.engine.render.ConjunctionRenderer;
import io.github.achacha.dada.engine.render.NounRenderer;
import io.github.achacha.dada.engine.render.PrepositionRenderer;
import io.github.achacha.dada.engine.render.PronounRenderer;
import io.github.achacha.dada.engine.render.TextRenderer;
import io.github.achacha.dada.engine.render.VerbRenderer;
import io.github.achacha.dada.integration.tags.GlobalData;
import io.github.achacha.dada.test.GlobalTestData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WordRendererHelperTest {
    @BeforeAll
    public static void beforeClass() {
        GlobalData.setWordData(GlobalTestData.WORD_DATA);
    }

    // NOTE: words passed to map method are only used to get the Type, renderer will pick from WordData
    @Test
    public void mapWordToRenderer() {
        BaseWordRenderer renderer = WordRendererHelper.mapWordToRenderer(new Text(" tree   "), true);
        assertTrue(renderer instanceof TextRenderer);
        assertEquals("tree", renderer.execute());

        renderer = WordRendererHelper.mapWordToRenderer(TestWords.makeAdjective("big", "bigger", "biggest"));
        assertTrue(renderer instanceof AdjectiveRenderer);
        renderer.setForm("superlative");
        assertEquals("most subtle", renderer.execute());

        renderer = WordRendererHelper.mapWordToRenderer(TestWords.makeAdverb("quickly"));
        assertTrue(renderer instanceof AdverbRenderer);
        assertEquals("adverbly", renderer.execute());

        renderer = WordRendererHelper.mapWordToRenderer(TestWords.makeConjunction("or"));
        assertTrue(renderer instanceof ConjunctionRenderer);
        assertEquals("&", renderer.execute());

        renderer = WordRendererHelper.mapWordToRenderer(TestWords.makeNoun("lump"));
        assertTrue(renderer instanceof NounRenderer);
        assertEquals("noun", renderer.execute());

        renderer = WordRendererHelper.mapWordToRenderer(TestWords.makePreposition("yonder"));
        assertTrue(renderer instanceof PrepositionRenderer);
        assertEquals("on", renderer.execute());

        renderer = WordRendererHelper.mapWordToRenderer(GlobalData.getWordData().getPronouns().getWordByBase("me"));
        assertTrue(renderer instanceof PronounRenderer);
        assertEquals("me", renderer.execute());

        renderer = WordRendererHelper.mapWordToRenderer(TestWords.makeVerb("know", "knew", "known", "knows", "knowing"));
        assertTrue(renderer instanceof VerbRenderer);
        assertEquals("swim", renderer.execute());
    }
}