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
     * @param articleMode ArticleMode
     * @param capsMode CapsMode
     */
    public ConjunctionRenderer(ArticleMode articleMode, CapsMode capsMode) {
        this();
        this.articleMode = articleMode;
        this.capsMode = capsMode;
        this.form = "";
    }
}
