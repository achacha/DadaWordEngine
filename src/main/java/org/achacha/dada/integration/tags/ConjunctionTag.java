package org.achacha.dada.integration.tags;

import org.achacha.dada.engine.data.Conjunction;

public class ConjunctionTag extends BaseWordTag<Conjunction> {
    public ConjunctionTag() {
        super(TagSingleton.getWordData().getConjunctions());
    }

    /**
     * Extended constructor
     * @param article article prefix
     * @param capMode capitalization mode: first, words, all
     * @param form specific to the word
     */
    public ConjunctionTag(String article, String capMode, String form) {
        super(TagSingleton.getWordData().getConjunctions());
        this.article = article;
        this.capMode = capMode;
        this.form = form;
    }
}
