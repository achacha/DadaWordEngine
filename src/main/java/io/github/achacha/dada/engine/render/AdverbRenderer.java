package io.github.achacha.dada.engine.render;

import io.github.achacha.dada.engine.data.Adverb;
import io.github.achacha.dada.integration.tags.GlobalData;

public class AdverbRenderer extends BaseWordRenderer<Adverb> {
    public AdverbRenderer() {
        super(new RenderContextToString<>(GlobalData.getWordData().getAdverbs()));
    }

    public AdverbRenderer(RenderContext<Adverb> rendererData) {
        super(rendererData);
    }

    /**
     * Extended constructor
     * @param articleMode {@link ArticleMode}
     * @param capsMode {@link CapsMode}
     * @param renderData {@link RenderContext} or null to use {@link RenderContextToString} with GlobalData
     */
    public AdverbRenderer(ArticleMode articleMode, CapsMode capsMode, RenderContext<Adverb> renderData) {
        super(
                renderData == null ? new RenderContextToString<>(GlobalData.getWordData().getAdverbs()) : renderData,
                articleMode,
                capsMode
        );
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
