package io.github.achacha.dada.engine.base;

import io.github.achacha.dada.engine.data.Word;
import io.github.achacha.dada.engine.render.AdjectiveRenderer;
import io.github.achacha.dada.engine.render.AdverbRenderer;
import io.github.achacha.dada.engine.render.BaseWordRenderer;
import io.github.achacha.dada.engine.render.ConjunctionRenderer;
import io.github.achacha.dada.engine.render.NounRenderer;
import io.github.achacha.dada.engine.render.PrepositionRenderer;
import io.github.achacha.dada.engine.render.PronounRenderer;
import io.github.achacha.dada.engine.render.TextRenderer;
import io.github.achacha.dada.engine.render.VerbRenderer;

public class WordRendererHelper {
    /**
     * Given a Word return appropriate renderer
     * Text renderer trims white space by default
     * @param word Word
     * @return BaseWordRenderer for type of Word
     */
    public static BaseWordRenderer mapWordToRenderer(Word word) {
        return mapWordToRenderer(word,false);
    }

    /**
     * Given a Word return appropriate renderer
     * @param word Word
     * @param trimText if Text content is trimmed
     * @return BaseWordRenderer for type of Word
     */
    public static BaseWordRenderer mapWordToRenderer(Word word, boolean trimText) {
        switch(word.getType()) {
            case Adjective: return new AdjectiveRenderer();
            case Adverb: return new AdverbRenderer();
            case Conjunction: return new ConjunctionRenderer();
            case Noun: return new NounRenderer();
            case Preposition: return new PrepositionRenderer();
            case Pronoun: return new PronounRenderer();
            case Verb: return new VerbRenderer();
            case Unknown: return new TextRenderer(trimText ? word.getWordString().trim() : word.getWordString());
        }
        throw new IllegalArgumentException("Undeclared word type, cannot map to renderer");
    }
}
