package org.achacha.dada.integration.tags;

import org.achacha.dada.engine.data.Noun;
import org.achacha.dada.engine.data.Word;

public class NounTag extends BaseWordTag<Noun> {
    public NounTag() {
        super(TagSingleton.getWordData().getNouns());
    }

    /**
     * Extended constructor
     * @param article article prefix
     * @param capMode capitalization mode: first, words, all
     * @param form specific to the word
     */
    public NounTag(String article, String capMode, String form) {
        super(TagSingleton.getWordData().getNouns());
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
