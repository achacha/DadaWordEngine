package io.github.achacha.dada.engine.render;

import io.github.achacha.dada.engine.data.Noun;
import io.github.achacha.dada.engine.data.Word;
import io.github.achacha.dada.integration.tags.TagSingleton;

public class NounRenderer extends BaseWordRenderer<Noun>{
    public NounRenderer() {
        super(new RenderContextToString<>(TagSingleton.getWordData().getNouns()));
    }

    public NounRenderer(RenderContext<Noun> renderData) {
        super(renderData);
    }

    /**
     * Extended constructor
     * @param articleMode ArticleMode
     * @param capsMode CapsMode
     * @param form "singular" or "plural"
     */
    public NounRenderer(ArticleMode articleMode, CapsMode capsMode, String form) {
        this();
        this.articleMode = articleMode;
        this.capsMode = capsMode;
        this.form = form;
    }

    @Override
    protected String selectWord(Word word) {
        Noun noun = (Noun)word;
        if ("plural".equals(form)) {
            return noun.getPlural();
        }
        else {
            if (LOGGER.isDebugEnabled()) {
                if (!form.isEmpty())
                    LOGGER.debug("Skipping unknown form `{}` in {}", form, this);
            }
            return super.selectWord(noun);
        }
    }
}
