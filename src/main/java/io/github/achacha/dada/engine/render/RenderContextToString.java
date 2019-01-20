package io.github.achacha.dada.engine.render;

import io.github.achacha.dada.engine.data.Word;
import io.github.achacha.dada.engine.data.WordsByType;

import javax.annotation.Nullable;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * Context that uses String based writer and simple HashMap to render
 * @param <T> extends Word
 */
public class RenderContextToString<T extends Word> extends RenderContext<T> {
    /** Attribute storage */
    private Map<String, Object> attributes = new HashMap<>();

    /** Writer */
    private Writer writer = new StringWriter();

    public RenderContextToString(WordsByType<T> words) {
        super(words);
    }

    @Override
    @Nullable
    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    @Override
    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    @Override
    public Writer getWriter() {
        return writer;
    }
}
