package io.github.achacha.dada.engine.render;

import io.github.achacha.dada.engine.data.Preposition;
import io.github.achacha.dada.integration.tags.GlobalData;

public class PrepositionRenderer extends BaseWordRenderer<Preposition> {
    public PrepositionRenderer() {
        super(new RenderContextToString<>(GlobalData.getWordData().getPrepositions()));
    }

    /**
     * Custom renderer context
     * @param renderData {@link RenderContext}
     */
    public PrepositionRenderer(RenderContext<Preposition> renderData) {
        super(renderData);
    }

    /**
     * Extended constructor
     * @param articleMode {@link ArticleMode}
     * @param capsMode {@link CapsMode}
     * @param renderData {@link RenderContext} or null to use {@link RenderContextToString} with GlobalData
     */
    public PrepositionRenderer(ArticleMode articleMode, CapsMode capsMode, RenderContext<Preposition> renderData) {
        super(
                renderData == null ? new RenderContextToString<>(GlobalData.getWordData().getPrepositions()) : renderData,
                articleMode,
                capsMode
        );
    }

    @Override
    public String getFormName() {
        return Preposition.Form.none.name();
    }

    @Override
    public void setForm(String formName) {
        LOGGER.error("Unexpected form name for this={} formName={}", this, formName);
    }
}
