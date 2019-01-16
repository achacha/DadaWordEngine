package io.github.achacha.dada.engine.render;

import io.github.achacha.dada.engine.data.Text;
import io.github.achacha.dada.engine.data.Word;
import io.github.achacha.dada.engine.data.WordsByType;

/**
 * Tag that represents constant text string
 */
public class TextRenderer extends BaseWordRenderer<Text> {
    private final Text text;

    /**
     * Text renderer without article or capitalization
     * @param text String constant
     */
    public TextRenderer(String text) {
        super(new RenderContextToString<>(WordsByType.empty()));
        this.text = Text.of(text);
    }

    /**
     * Custom renderer for text
     * @param text String constant
     * @param renderData {@link RenderContext}
     */
    public TextRenderer(String text, RenderContext<Text> renderData) {
        super(renderData);
        this.text = Text.of(text);
    }

    /**
     * Extended constructor
     * @param text String constant
     * @param articleMode {@link ArticleMode}
     * @param capsMode {@link CapsMode}
     */
    public TextRenderer(String text, ArticleMode articleMode, CapsMode capsMode) {
        super(new RenderContextToString<>(WordsByType.empty()), articleMode, capsMode);
        this.text = Text.of(text);
    }

    @Override
    public String getFormName() {
        return Text.Form.none.name();
    }

    @Override
    public void setForm(String formName) {
        LOGGER.error("Unexpected form name for this={} formName={}", this, formName);
    }

    @Override
    protected Word generateWord() {
        return text;
    }
}
