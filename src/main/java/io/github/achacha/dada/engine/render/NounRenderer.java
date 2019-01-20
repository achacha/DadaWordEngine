package io.github.achacha.dada.engine.render;

import io.github.achacha.dada.engine.data.Noun;
import io.github.achacha.dada.engine.data.Word;
import io.github.achacha.dada.integration.tags.GlobalData;
import org.apache.commons.lang3.StringUtils;

public class NounRenderer extends BaseWordRenderer<Noun>{
    protected Noun.Form form = Noun.Form.singular;

    public NounRenderer() {
        super(new RenderContextToString<>(GlobalData.getWordData().getNouns()));
    }

    public NounRenderer(RenderContext<Noun> renderData) {
        super(renderData);
    }

    /**
     * Extended constructor
     * @param form {@link Noun.Form}
     * @param articleMode {@link ArticleMode}
     * @param capsMode {@link CapsMode}
     * @param renderData {@link RenderContext} or null to use {@link RenderContextToString} with GlobalData
     */
    public NounRenderer(Noun.Form form, ArticleMode articleMode, CapsMode capsMode, RenderContext<Noun> renderData) {
        super(
                renderData == null ? new RenderContextToString<>(GlobalData.getWordData().getNouns()) : renderData,
                articleMode,
                capsMode
        );
        this.form = form;
    }

    @Override
    protected String selectWord(Word word) {
        Noun noun = (Noun)word;
        switch(form) {
            case plural: return noun.getPlural();
            default: return super.selectWord(noun);
        }
    }

    public Noun.Form getForm() {
        return form;
    }

    public void setForm(Noun.Form form) {
        this.form = form;
    }

    @Override
    public String getFormName() {
        return form.name();
    }

    @Override
    public void setForm(String formName) {
        try {
            this.form = Noun.Form.valueOf(StringUtils.trim(formName).toLowerCase());
        }
        catch(IllegalArgumentException e) {
            LOGGER.error("Invalid form name for this={} formName={}", this, formName);
        }
    }
}
