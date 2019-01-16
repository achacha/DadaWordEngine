package io.github.achacha.dada.engine.render;

import io.github.achacha.dada.engine.data.Verb;
import io.github.achacha.dada.engine.data.Word;
import io.github.achacha.dada.integration.tags.TagSingleton;
import org.apache.commons.lang3.StringUtils;

public class VerbRenderer extends BaseWordRenderer<Verb> {
    protected Verb.Form form = Verb.Form.base;

    public VerbRenderer() {
        super(new RenderContextToString<>(TagSingleton.getWordData().getVerbs()));
    }

    public VerbRenderer(RenderContext<Verb> renderData) {
        super(renderData);
    }

    /**
     * Extended constructor
     * @param articleMode ArticleMode
     * @param capsMode CapsMode
     * @param form {@link Verb.Form}
     */
    public VerbRenderer(ArticleMode articleMode, CapsMode capsMode, Verb.Form form) {
        this();
        this.articleMode = articleMode;
        this.capsMode = capsMode;
        this.form = form;
    }

    @Override
    protected String selectWord(Word word) {
        Verb verb = (Verb)word;
        switch (form) {
            case infinitive:
                return verb.getInfinitive();
            case past:
                return verb.getPast();
            case singular:
                return verb.getSingular();
            case present:
                return verb.getPresent();
            case pastparticiple:
                return verb.getPastParticiple();
            default:
                return super.selectWord(verb);
        }
    }

    public Verb.Form getForm() {
        return form;
    }

    public void setForm(Verb.Form form) {
        this.form = form;
    }

    @Override
    public String getFormName() {
        return form.name();
    }

    @Override
    public void setForm(String formName) {
        try {
            this.form = Verb.Form.valueOf(StringUtils.trim(formName).toLowerCase());
        }
        catch(IllegalArgumentException e) {
            LOGGER.error("Invalid form name for this={} formName={}", this, formName);
        }
    }
}
