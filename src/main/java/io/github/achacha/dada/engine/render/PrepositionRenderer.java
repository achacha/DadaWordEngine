package io.github.achacha.dada.engine.render;

import io.github.achacha.dada.engine.data.Preposition;
import io.github.achacha.dada.integration.tags.TagSingleton;

public class PrepositionRenderer extends BaseWordRenderer<Preposition> {
    public PrepositionRenderer() {
        super(new RenderContextToString<>(TagSingleton.getWordData().getPrepositions()));
    }

    public PrepositionRenderer(RenderContext<Preposition> renderData) {
        super(renderData);
    }

    /**
     * Extended constructor
     * @param article article prefix
     * @param capsMode CapsMode
     * @param form specific to the word
     */
    public PrepositionRenderer(String article, CapsMode capsMode, String form) {
        this();
        this.article = article;
        this.capsMode = capsMode;
        this.form = form;
    }
}
