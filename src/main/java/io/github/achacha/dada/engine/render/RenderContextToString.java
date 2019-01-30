package io.github.achacha.dada.engine.render;

import io.github.achacha.dada.engine.data.SavedWord;
import io.github.achacha.dada.engine.data.Word;
import io.github.achacha.dada.engine.data.WordsByType;

import javax.annotation.Nullable;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * Context that uses String based writer to render
 * If attributes map is not provided,
 *   local one is created, Senetence* classes usually provide one that is shared
 *   it is better to provide one so that saved words can be used across different types
 *   during rendering and for rhyming
 *
 * @param <T> extends Word
 */
public class RenderContextToString<T extends Word> extends RenderContext<T> {
    /** Attribute storage */
    private Map<String, SavedWord> attributes;

    /** Writer */
    private Writer writer = new StringWriter();

    /**
     * Construct a context with local attributes and StringWriter
     * @param words {@link WordsByType} of type T
     */
    public RenderContextToString(WordsByType<T> words) {
        super(words);
        attributes = new HashMap<>();
    }

    /**
     * Construct a context with provided attributes and StringWriter
     * @param words {@link WordsByType} of type T
     * @param attributes Map of key to Object (usually {@link SavedWord}
     */
    public RenderContextToString(WordsByType<T> words, Map<String, SavedWord> attributes) {
        super(words);
        this.attributes = attributes;
    }

    /**
     * Construct a context with provided attributes and provided Writer
     * @param words {@link WordsByType} of type T
     * @param attributes Map of key to Object (usually {@link SavedWord}
     * @param writer {@link Writer} to use when rendering
     */
    public RenderContextToString(WordsByType<T> words, Map<String, SavedWord> attributes, Writer writer) {
        super(words);
        this.attributes = attributes;
        this.writer = writer;
    }

    @Override
    @Nullable
    public SavedWord getAttribute(String name) {
        return attributes.get(name);
    }

    @Override
    public void setAttribute(String name, SavedWord value) {
        attributes.put(name, value);
    }

    @Override
    public Writer getWriter() {
        return writer;
    }
}
