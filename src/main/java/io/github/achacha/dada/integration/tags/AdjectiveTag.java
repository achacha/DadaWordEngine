package io.github.achacha.dada.integration.tags;

import io.github.achacha.dada.engine.data.Adjective;
import io.github.achacha.dada.engine.data.Word;

public class AdjectiveTag extends BaseWordTag<Adjective> {
    public AdjectiveTag() {
        super(TagSingleton.getWordData().getAdjectives());
    }

    /**
     * Extended constructor
     * @param article article prefix
     * @param capMode capitalization mode: first, words, all
     * @param form specific to the word
     */
    public AdjectiveTag(String article, String capMode, String form) {
        super(TagSingleton.getWordData().getAdjectives());
        this.article = article;
        this.capMode = capMode;
        this.form = form;
    }

    @Override
    protected String selectWord(Word word) {
        Adjective adjective = (Adjective)word;
        if (!form.isEmpty()) {
            switch (form) {
                // Comparative
                case "er":
                    return adjective.getComparative();

                // Superlative
                case "est":
                    return adjective.getSuperlative();

                default:
                    LOGGER.debug("Skipping unknown form `{}` in {}", form, this);
                    return super.selectWord(adjective);
            }
        }
        return word.getWord();
    }
}
