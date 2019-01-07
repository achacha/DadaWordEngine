package org.achacha.dada.integration.tags;

import org.achacha.dada.engine.data.Verb;
import org.achacha.dada.engine.data.Word;

public class VerbTag extends BaseWordTag<Verb> {
    public VerbTag() {
        super(TagSingleton.getWordData().getVerbs());
    }

    /**
     * Extended constructor
     * @param article article prefix
     * @param capMode capitalization mode: first, words, all
     * @param form specific to the word
     */
    public VerbTag(String article, String capMode, String form) {
        super(TagSingleton.getWordData().getVerbs());
        this.article = article;
        this.capMode = capMode;
        this.form = form;
    }

    @Override
    protected String selectWord(Word word) {
        Verb verb = (Verb)word;
        switch (form) {
            case "infinitive":
                return verb.getInfinitive();
            case "past":
                return verb.getPast();
            case "singular":
                return verb.getSingular();
            case "present":
                return verb.getPresent();
            case "pastparticiple":
                return verb.getPastParticiple();
            default:
                LOGGER.debug("Skipping unknown form `{}` in {}", form, this);
                return super.selectWord(verb);
        }
    }
}
