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
     * @param articleMode {@link ArticleMode}
     * @param capsMode {@link CapsMode}
     */
    public ConjunctionRenderer(ArticleMode articleMode, CapsMode capsMode) {
        super(new RenderContextToString<>(TagSingleton.getWordData().getConjunctions()), articleMode, capsMode);
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
