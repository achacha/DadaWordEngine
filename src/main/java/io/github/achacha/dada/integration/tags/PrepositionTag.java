package io.github.achacha.dada.integration.tags;

import io.github.achacha.dada.engine.data.Preposition;

public class PrepositionTag extends BaseWordTag<Preposition> {
    public PrepositionTag() {
        super(TagSingleton.getWordData().getPrepositions());
    }

    /**
     * Extended constructor
     * @param article article prefix
     * @param capMode capitalization mode: first, words, all
     * @param form specific to the word
     */
    public PrepositionTag(String article, String capMode, String form) {
        super(TagSingleton.getWordData().getPrepositions());
        this.article = article;
        this.capMode = capMode;
        this.form = form;
    }
}
