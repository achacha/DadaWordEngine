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
     * @param articleMode ArticleMode
     * @param capsMode CapsMode
     * @param form specific to the word
     */
    public PrepositionRenderer(ArticleMode articleMode, CapsMode capsMode, String form) {
        this();
        this.articleMode = articleMode;
        this.capsMode = capsMode;
        this.form = form;
    }
}
