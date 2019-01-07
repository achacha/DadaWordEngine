package org.achacha.dada.integration.tags;

import org.achacha.dada.engine.data.Adverb;

public class AdverbTag extends BaseWordTag<Adverb> {
    public AdverbTag() {
        super(TagSingleton.getWordData().getAdverbs());
    }

    /**
     * Extended constructor
     * @param article article prefix
     * @param capMode capitalization mode: first, words, all
     * @param form specific to the word
     */
    public AdverbTag(String article, String capMode, String form) {
        super(TagSingleton.getWordData().getAdverbs());
        this.article = article;
        this.capMode = capMode;
        this.form = form;
    }
}
