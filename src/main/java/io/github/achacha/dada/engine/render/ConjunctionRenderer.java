package io.github.achacha.dada.engine.render;

import io.github.achacha.dada.engine.data.Conjunction;
import io.github.achacha.dada.integration.tags.GlobalData;

public class ConjunctionRenderer extends BaseWordRenderer<Conjunction> {
    public ConjunctionRenderer() {
        super(new RenderContextToString<>(GlobalData.getWordData().getConjunctions()));
    }

    public ConjunctionRenderer(RenderContext<Conjunction> renderData) {
        super(renderData);
    }

    /**
     * Extended constructor
     * @param articleMode {@link ArticleMode}
     * @param capsMode {@link CapsMode}
     * @param renderData {@link RenderContext} or null to use {@link RenderContextToString} with GlobalData
     */
    public ConjunctionRenderer(ArticleMode articleMode, CapsMode capsMode, RenderContext<Conjunction> renderData) {
        super(
                renderData == null ? new RenderContextToString<>(GlobalData.getWordData().getConjunctions()) : renderData,
                articleMode,
                capsMode
        );
    }

    @Override
    public String getFormName() {
        return Conjunction.Form.none.name();
    }

    @Override
    public void setForm(String formName) {
        LOGGER.error("Unexpected form name for this={} formName={}", this, formName);
    }
}
