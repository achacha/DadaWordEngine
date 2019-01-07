package org.achacha.dada.integration.tags;

import org.achacha.dada.engine.data.Text;
import org.achacha.dada.engine.data.Word;
import org.achacha.dada.engine.data.WordsByType;

/**
 * Tag that represents constant text string
 */
public class TextTag extends BaseWordTag<Text> {
    private final Text text;

    /**
     * @param text constant string
     */
    public TextTag(String text) {
        super(WordsByType.empty());
        this.text = Text.of(text);
    }

    /**
     * Extended constructor
     * @param text constant string
     * @param article article prefix
     * @param capMode capitalization mode: first, words, all
     * @param form specific to the word
     */
    public TextTag(String text, String article, String capMode, String form) {
        super(WordsByType.empty());
        this.text = Text.of(text);
        this.article = article;
        this.capMode = capMode;
        this.form = form;
    }

    @Override
    protected Word generateWord() {
        return text;
    }
}
