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
     * @param articleMode ArticleMode
     * @param capsMode CapsMode
     */
    public AdverbRenderer(ArticleMode articleMode, CapsMode capsMode) {
        this();
        this.articleMode = articleMode;
        this.capsMode = capsMode;
    }

    @Override
    public String getFormName() {
        return Adverb.Form.none.name();
    }

    @Override
    public void setForm(String formName) {
        LOGGER.error("Unexpected form name for this={} formName={}", this, formName);
    }
}
