package io.github.achacha.dada.engine.render;

import io.github.achacha.dada.engine.data.Verb;
import io.github.achacha.dada.engine.data.Word;
import io.github.achacha.dada.integration.tags.TagSingleton;

public class VerbRenderer extends BaseWordRenderer<Verb> {
    public VerbRenderer() {
        super(new RenderContextToString<>(TagSingleton.getWordData().getVerbs()));
    }

    public VerbRenderer(RenderContext<Verb> renderData) {
        super(renderData);
    }

    /**
     * Extended constructor
     * @param article article prefix
     * @param capMode capitalization mode: first, words, all
     * @param form "infinitive", "past", "singular", "present", "pastparticiple"
     */
    public VerbRenderer(String article, String capMode, String form) {
        this();
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
