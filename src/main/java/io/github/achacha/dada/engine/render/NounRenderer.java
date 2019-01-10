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
     * @param article article prefix
     * @param capMode capitalization mode: first, words, all
     * @param form specific to the word
     */
    NounRenderer(String article, String capMode, String form) {
        this();
        this.article = article;
        this.capMode = capMode;
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
