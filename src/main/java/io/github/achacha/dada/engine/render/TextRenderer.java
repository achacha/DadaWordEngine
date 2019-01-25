package io.github.achacha.dada.engine.render;

import com.google.common.base.Preconditions;
import io.github.achacha.dada.engine.builder.SentenceRendererBuilder;
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
     * @param renderContext {@link RenderContext}
     */
    public TextRenderer(String text, RenderContext<Text> renderContext) {
        super(renderContext);
        this.text = Text.of(text);
    }

    /**
     * Extended constructor
     * @param text String constant
     * @param articleMode {@link ArticleMode}
     * @param capsMode {@link CapsMode}
     * @param renderContext {@link RenderContext} or null to use {@link RenderContextToString} with GlobalData
     */
    public TextRenderer(String text, ArticleMode articleMode, CapsMode capsMode, RenderContext<Text> renderContext) {
        super(
                renderContext == null ? new RenderContextToString<>(WordsByType.empty()) : renderContext,
                articleMode,
                capsMode
        );
        this.text = Text.of(text);
    }

    @Override
    public Word.Type getType() {
        return Word.Type.Unknown;
    }

    /**
     * Builder to be used with SentenceRendererBuilder
     * @param sentenceBuilder SentenceRendererBuilder
     * @return Builder
     */
    public static Builder builder(SentenceRendererBuilder sentenceBuilder) {
        return new Builder(sentenceBuilder);
    }

    public static class Builder {
        private final SentenceRendererBuilder sentenceBuilder;
        private String text;
        private ArticleMode articleMode = ArticleMode.none;
        private CapsMode capsMode = CapsMode.none;
        private RenderContext<Text> renderContext;
        private String loadKey;
        private String saveKey;
        private String rhymeKey;
        private String rhymeWith;

        public Builder(SentenceRendererBuilder sentenceBuilder) {
            this.sentenceBuilder = sentenceBuilder;
        }

        /**
         * Add to provided SentenceRendererBuilder
         * @return {@link SentenceRendererBuilder} provided in constructor
         */
        public SentenceRendererBuilder build() {
            Preconditions.checkNotNull(text);

            TextRenderer renderer = new TextRenderer(text, articleMode, capsMode, renderContext);
            renderer.loadKey = loadKey;
            renderer.saveKey = saveKey;
            renderer.rhymeKey = rhymeKey;
            renderer.rhymeWith = rhymeWith;

            // Validate and add
            validateRenderer(renderer);
            sentenceBuilder.getRenderers().add(renderer);

            return sentenceBuilder;
        }

        public Builder withText(String text) {
            this.text = text;
            return this;
        }

        public Builder withArticleMode(ArticleMode articleMode) {
            this.articleMode = articleMode;
            return this;
        }

        public Builder withCapsMode(CapsMode capsMode) {
            this.capsMode = capsMode;
            return this;
        }

        public Builder withRenderContext(RenderContext<Text> renderContext) {
            this.renderContext = renderContext;
            return this;
        }

        public Builder withLoadKey(String loadKey) {
            this.loadKey = loadKey;
            return this;
        }

        public Builder withSaveKey(String saveKey) {
            this.saveKey = saveKey;
            return this;
        }

        public Builder withRhymeKey(String rhymeKey) {
            this.rhymeKey = rhymeKey;
            return this;
        }

        public Builder withRhymeWith(String rhymeWith) {
            this.rhymeWith = rhymeWith;
            return this;
        }
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
