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
     * @param article article prefix
     * @param capsMode CapsMode
     */
    public ConjunctionRenderer(String article, CapsMode capsMode) {
        this();
        this.article = article;
        this.capsMode = capsMode;
        this.form = "";
    }
}
