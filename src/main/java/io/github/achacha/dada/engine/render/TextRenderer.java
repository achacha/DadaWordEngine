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
     * @param text constant string
     */
    public TextRenderer(String text) {
        super(new RenderContextToString<>(WordsByType.empty()));
        this.text = Text.of(text);
    }

    public TextRenderer(String text, RenderContext<Text> renderData) {
        super(renderData);
        this.text = Text.of(text);
    }

    /**
     * Extended constructor
     * @param text constant string
     * @param article article prefix
     * @param capMode capitalization mode: first, words, all
     * @param form specific to the word
     */
    public TextRenderer(String text, String article, String capMode, String form) {
        this(text);
        this.article = article;
        this.capMode = capMode;
        this.form = form;
    }

    @Override
    protected Word generateWord() {
        return text;
    }
}