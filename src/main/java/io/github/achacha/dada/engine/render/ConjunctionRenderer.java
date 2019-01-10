package io.github.achacha.dada.engine.render;

import io.github.achacha.dada.engine.data.Conjunction;
import io.github.achacha.dada.integration.tags.TagSingleton;

public class ConjunctionRenderer extends BaseWordRenderer<Conjunction> {
    public ConjunctionRenderer() {
        super(new RenderContextToString<>(TagSingleton.getWordData().getConjunctions()));
    }

    public ConjunctionRenderer(RenderContext<Conjunction> renderData) {
        super(renderData);
    }

    /**
     * Extended constructor
     * @param article article prefix
     * @param capMode capitalization mode: first, words, all
     * @param form specific to the word
     */
    ConjunctionRenderer(String article, String capMode, String form) {
        this();
        this.article = article;
        this.capMode = capMode;
        this.form = form;
    }
}
