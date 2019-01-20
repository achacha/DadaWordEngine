package io.github.achacha.dada.engine.render;

import io.github.achacha.dada.engine.data.Text;
import io.github.achacha.dada.engine.data.Word;
import io.github.achacha.dada.engine.data.WordsByType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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
     * @param renderData {@link RenderContext} or null to use {@link RenderContextToString} with GlobalData
     */
    public TextRenderer(String text, ArticleMode articleMode, CapsMode capsMode, RenderContext<Text> renderData) {
        super(
                renderData == null ? new RenderContextToString<>(WordsByType.empty()) : renderData,
                articleMode,
                capsMode
        );
        this.text = Text.of(text);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("class", getClass().getSimpleName())
                .append("text", text)
                .append("articleMode", articleMode)
                .append("capsMode", capsMode)
                .append("loadKey", loadKey)
                .append("saveKey", saveKey)
                .append("rhymeKey", rhymeKey)
                .append("rhymeWith", rhymeWith)
                .append("syllablesDesired", syllablesDesired)
                .toString();
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
