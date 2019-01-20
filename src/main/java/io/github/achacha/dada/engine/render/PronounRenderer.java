package io.github.achacha.dada.engine.render;

import io.github.achacha.dada.engine.data.Pronoun;
import io.github.achacha.dada.engine.data.Pronouns;
import io.github.achacha.dada.engine.data.Word;
import io.github.achacha.dada.integration.tags.GlobalData;
import org.apache.commons.lang3.StringUtils;

public class PronounRenderer extends BaseWordRenderer<Pronoun> {
    protected Pronoun.Form form = Pronoun.Form.personal;

    public PronounRenderer() {
        super(new RenderContextToString<>(GlobalData.getWordData().getPronouns()));
    }

    /**
     * Custom render context
     * @param renderData {@link RenderContext}
     */
    public PronounRenderer(RenderContext<Pronoun> renderData) {
        super(renderData);
    }

    /** Used in testing */
    PronounRenderer(Pronouns pronouns) {
        super(new RenderContextToString<>(pronouns));
    }

    /**
     * Extended constructor
     * @param form {@link Pronoun.Form}
     * @param articleMode {@link ArticleMode}
     * @param capsMode {@link CapsMode}
     * @param renderData {@link RenderContext} or null to use {@link RenderContextToString} with GlobalData
     */
    public PronounRenderer(Pronoun.Form form, ArticleMode articleMode, CapsMode capsMode, RenderContext<Pronoun> renderData) {
        super(
                renderData == null ? new RenderContextToString<>(GlobalData.getWordData().getPronouns()) : renderData,
                articleMode,
                capsMode
        );
        this.form = form;
    }

    @Override
    protected Word generateWord() {
        Pronouns pronouns = (Pronouns) rendererContext.getWords();
        try {
            Word word = pronouns.getRandomPronounByForm(form);
            LOGGER.debug("Generated pronoun={} form={}", word, form.name());
            return word;
        }
        catch(IllegalArgumentException e) {
            LOGGER.debug("Skipping unknown form `{}` in {}", form, this);
        }
        return super.generateWord();
    }

    public Pronoun.Form getForm() {
        return form;
    }

    public void setForm(Pronoun.Form form) {
        this.form = form;
    }

    @Override
    public String getFormName() {
        return form.name();
    }

    @Override
    public void setForm(String formName) {
        try {
            this.form = Pronoun.Form.valueOf(StringUtils.trim(formName).toLowerCase());
        }
        catch(IllegalArgumentException e) {
            LOGGER.error("Invalid form name for this={} formName={}", this, formName);
        }
    }
}
