package org.achacha.dada.integration.tags;

import org.achacha.dada.engine.data.Pronoun;
import org.achacha.dada.engine.data.Pronouns;
import org.achacha.dada.engine.data.Word;

public class PronounTag extends BaseWordTag<Pronoun> {
    public PronounTag() {
        super(TagSingleton.getWordData().getPronouns());
    }

    PronounTag(Pronouns pronouns) {
        super(pronouns);
    }

    /**
     * Extended constructor
     * @param article article prefix
     * @param capMode capitalization mode: first, words, all
     * @param form specific to the word
     */
    public PronounTag(String article, String capMode, String form) {
        super(TagSingleton.getWordData().getPronouns());
        this.article = article;
        this.capMode = capMode;
        this.form = form;
    }

    @Override
    protected Word generateWord() {
        if (!form.isEmpty()) {
            Pronouns pronouns = (Pronouns)words;
            try {
                Pronoun.Form pronounForm = Pronoun.Form.valueOf(form.toLowerCase());
                Word word = pronouns.getRandomPronounByForm(pronounForm);
                LOGGER.debug("Generated pronoun={} form={}", word, pronounForm);
                return word;
            }
            catch(IllegalArgumentException e) {
                LOGGER.debug("Skipping unknown form `{}` in {}", form, this);
            }
        }
        return super.generateWord();
    }
}
