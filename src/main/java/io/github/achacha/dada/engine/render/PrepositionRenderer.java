package io.github.achacha.dada.engine.render;

import io.github.achacha.dada.engine.data.Preposition;
import io.github.achacha.dada.integration.tags.TagSingleton;

public class PrepositionRenderer extends BaseWordRenderer<Preposition> {
    public PrepositionRenderer() {
        super(new RenderContextToString<>(TagSingleton.getWordData().getPrepositions()));
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
     */
    public PrepositionRenderer(ArticleMode articleMode, CapsMode capsMode) {
        super(new RenderContextToString<>(TagSingleton.getWordData().getPrepositions()), articleMode, capsMode);
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
