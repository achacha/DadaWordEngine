package io.github.achacha.dada.engine.render;

import io.github.achacha.dada.engine.data.Verb;
import io.github.achacha.dada.engine.data.Word;
import io.github.achacha.dada.integration.tags.GlobalData;
import org.apache.commons.lang3.StringUtils;

public class VerbRenderer extends BaseWordRenderer<Verb> {
    protected Verb.Form form = Verb.Form.base;

    public VerbRenderer() {
        super(new RenderContextToString<>(GlobalData.getWordData().getVerbs()));
    }

    public VerbRenderer(RenderContext<Verb> renderData) {
        super(renderData);
    }

    /**
     * Extended constructor
     * @param form {@link Verb.Form}
     * @param articleMode {@link ArticleMode}
     * @param capsMode {@link CapsMode}
     * @param renderData {@link RenderContext} or null to use {@link RenderContextToString} with GlobalData
     */
    public VerbRenderer(Verb.Form form, ArticleMode articleMode, CapsMode capsMode, RenderContext<Verb> renderData) {
        super(
                renderData == null ? new RenderContextToString<>(GlobalData.getWordData().getVerbs()) : renderData,
                articleMode,
                capsMode
        );
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
