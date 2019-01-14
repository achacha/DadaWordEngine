package io.github.achacha.dada.engine.render;

import io.github.achacha.dada.engine.data.Adverb;
import io.github.achacha.dada.integration.tags.TagSingleton;

public class AdverbRenderer extends BaseWordRenderer<Adverb> {
    public AdverbRenderer() {
        super(new RenderContextToString<>(TagSingleton.getWordData().getAdverbs()));
    }

    public AdverbRenderer(RenderContext<Adverb> rendererContext) {
        super(rendererContext);
    }

    /**
     * Extended constructor
     * @param article article prefix
     * @param capMode capitalization mode: first, words, all
     * @param form specific to the word
     */
    public AdverbRenderer(String article, String capMode, String form) {
        this();
        this.article = article;
        this.capMode = capMode;
        this.form = form;
    }
}
