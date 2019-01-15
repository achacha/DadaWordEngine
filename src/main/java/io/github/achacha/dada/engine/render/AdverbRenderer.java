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
     * @param capsMode CapsMode
     */
    public AdverbRenderer(String article, CapsMode capsMode) {
        this();
        this.article = article;
        this.capsMode = capsMode;
        this.form = "";
    }
}
