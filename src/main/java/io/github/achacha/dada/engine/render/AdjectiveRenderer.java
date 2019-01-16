package io.github.achacha.dada.engine.render;

import io.github.achacha.dada.engine.data.Adjective;
import io.github.achacha.dada.engine.data.Word;
import io.github.achacha.dada.integration.tags.TagSingleton;
import org.apache.commons.lang3.StringUtils;

public class AdjectiveRenderer extends BaseWordRenderer<Adjective> {

    protected Adjective.Form form = Adjective.Form.positive;

    public AdjectiveRenderer() {
        super(new RenderContextToString<>(TagSingleton.getWordData().getAdjectives()));
    }

    public AdjectiveRenderer(RenderContext<Adjective> renderData) {
        super(renderData);
    }

    /**
     * Extended constructor
     * @param articleMode ArticleMode
     * @param capsMode CapsMode
     * @param form {@link Adjective.Form)
     */
    public AdjectiveRenderer(ArticleMode articleMode, CapsMode capsMode, Adjective.Form form) {
        this();
        this.articleMode = articleMode;
        this.capsMode = capsMode;
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
