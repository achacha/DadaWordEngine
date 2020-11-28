package io.github.achacha.dada.engine.render;

import com.google.common.base.Preconditions;
import io.github.achacha.dada.engine.builder.SentenceRendererBuilder;
import io.github.achacha.dada.engine.data.Text;
import io.github.achacha.dada.engine.data.Word;
import io.github.achacha.dada.engine.data.WordsByType;

import java.util.function.Predicate;

/**
 * Tag that represents constant text string
 */
public class TextRenderer extends BaseWordRenderer<Text> {
    /**
     * Text renderer without article or capitalization
     * @param text String constant
     */
    public TextRenderer(String text) {
        super(new RenderContextToString<>(WordsByType.empty()));
        this.fallback = text;
    }

    /**
     * Custom renderer for text
     * @param text String constant
     * @param renderContext {@link RenderContext}
     */
    public TextRenderer(String text, RenderContext<Text> renderContext) {
        super(renderContext);
        this.fallback = text;
    }

    /**
     * Extended constructor
     * @param fallback String constant
     * @param articleMode {@link ArticleMode}
     * @param capsMode {@link CapsMode}
     * @param renderContext {@link RenderContext} or null to use {@link RenderContextToString} with GlobalData
     */
    public TextRenderer(String fallback, ArticleMode articleMode, CapsMode capsMode, RenderContext<Text> renderContext) {
        super(
                renderContext == null ? new RenderContextToString<>(WordsByType.empty()) : renderContext,
                articleMode,
                capsMode
        );
        this.fallback = fallback;
    }

    /**
     * Override constrant text
     * This is needed since we may not have the text at the time of creation
     * @param text String constant
     */
    public void setText(String text) {
        this.fallback = text;
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
        private String fallback;
        private Predicate<BaseWordRenderer<Text>> fallbackPredicate = textBaseWordRenderer -> true;
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
            Preconditions.checkNotNull(fallback);

            TextRenderer renderer = new TextRenderer(fallback, articleMode, capsMode, renderContext);
            renderer.loadKey = loadKey;
            renderer.saveKey = saveKey;
            renderer.rhymeKey = rhymeKey;
            renderer.rhymeWith = rhymeWith;
            renderer.fallbackPredicate = fallbackPredicate;

            // Validate and add
            validateRenderer(renderer);
            sentenceBuilder.getRenderers().add(renderer);

            return sentenceBuilder;
        }

        public Builder withFallback(String fallback) {
            this.fallback = fallback;
            return this;
        }

        public Builder withFallback(String fallback, Predicate<BaseWordRenderer<Text>> fallbackPredicate) {
            this.fallback = fallback;
            this.fallbackPredicate = fallbackPredicate;
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
    public String getFormName() {
        return Text.Form.none.name();
    }

    @Override
    public void setForm(String formName) {
        // NO-OP, word has no forms
    }

    @Override
    protected Word generateWord() {
        return Text.of(fallback);
    }

    @Override
    protected String selectWord(Word word) {
        return word.getWordString();
    }
}
