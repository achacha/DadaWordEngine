package io.github.achacha.dada.engine.render;

import io.github.achacha.dada.engine.data.Adjective;
import io.github.achacha.dada.engine.data.Word;
import io.github.achacha.dada.integration.tags.GlobalData;
import org.apache.commons.lang3.StringUtils;

public class AdjectiveRenderer extends BaseWordRenderer<Adjective> {

    protected Adjective.Form form = Adjective.Form.positive;

    /**
     * Build renderer with string based context
     * @see RenderContextToString
     */
    public AdjectiveRenderer() {
        super(new RenderContextToString<>(GlobalData.getWordData().getAdjectives()));
    }

    /**
     * Build renderer with custom context
     * @param renderData {@link RenderContext}
     */
    public AdjectiveRenderer(RenderContext<Adjective> renderData) {
        super(renderData);
    }

    /**
     * Extended constructor
     * @param form {@link Adjective.Form}
     * @param articleMode {@link ArticleMode}
     * @param capsMode {@link CapsMode}
     * @param renderData {@link RenderContext} or null to use {@link RenderContextToString} with GlobalData
     */
    public AdjectiveRenderer(Adjective.Form form, ArticleMode articleMode, CapsMode capsMode, RenderContext<Adjective> renderData) {
        super(
                renderData == null ? new RenderContextToString<>(GlobalData.getWordData().getAdjectives()) : renderData,
                articleMode,
                capsMode
        );
        this.form = form;
    }

    @Override
    public String getFormName() {
        return form.name();
    }

    @Override
    public void setForm(String formName) {
        try {
            this.form = Adjective.Form.valueOf(StringUtils.trim(formName).toLowerCase());
        }
        catch(IllegalArgumentException e) {
            LOGGER.error("Invalid form name for this={} formName={}", this, formName);
        }
    }

    public Adjective.Form getForm() {
        return form;
    }

    public void setForm(Adjective.Form form) {
        this.form = form;
    }

    @Override
    protected String selectWord(Word word) {
        Adjective adjective = (Adjective)word;
        switch (form) {
            // Comparative
            case comparative:
                return adjective.getComparative();

            // Superlative
            case superlative:
                return adjective.getSuperlative();

            default:
                return super.selectWord(adjective);
        }
    }
}
