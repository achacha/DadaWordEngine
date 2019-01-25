package io.github.achacha.dada.engine.render;

import io.github.achacha.dada.engine.data.Word;
import io.github.achacha.dada.engine.data.WordData;
import io.github.achacha.dada.engine.data.WordsByType;

import javax.annotation.Nullable;
import java.io.Writer;

/**
 * Render data for each word type
 * @param <T> extends Word
 */
public abstract class RenderContext<T extends Word> {
    private WordsByType<T> words;

    /**
     * Construct context with words for this word type
     * @param words WordsByType of T
     */
    public RenderContext(WordsByType<T> words) {
        this.words = words;
    }

    /**
     * Word specific attribute used in rendering
     * Attributes are usually page/sentence global across all words
     *
     * @param name String
     * @return Value object or null
     */
    @Nullable
    public abstract Object getAttribute(String name);

    /**
     * Set attribute used in rendering
     * Attributes are usually page/sentence global across all words
     *
     * @param name String
     * @param value String
     */
    public abstract void setAttribute(String name, Object value);

    /**
     * @return Writer to be used for rendering the word
     */
    public abstract Writer getWriter();

    /**
     * Words for this specific type
     * @return WordsByType of T
     */
    public WordsByType<T> getWords() {
        return words;
    }

    /**
     * Set new WordsByType on this context
     * @param words WordsByType
     */
    public void setWords(WordsByType<T> words) {
        this.words = words;
    }

    /**
     * Sets appropriate WordsByType from WordData for this instance
     * @param wordData WordData
     */
    public void applyWords(WordData wordData) {
        words = (WordsByType<T>)wordData.getWordsByType(words.getType());
    }
}
