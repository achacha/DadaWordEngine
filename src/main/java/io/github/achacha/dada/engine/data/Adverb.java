package io.github.achacha.dada.engine.data;

import com.google.common.base.Preconditions;

import java.util.ArrayList;

/**
 * Adverb
 */
public class Adverb extends Word {
    public enum Form {
        none   // adverb has one form
    }

    protected Adverb(ArrayList<String> attrs) {
        super(attrs.get(0));
        Preconditions.checkArgument(attrs.size() == 1, "Adverb expects word with attributes to be in 1 parts, attrs=%s", attrs);

        LOGGER.trace("Adding adverb=`{}`", this.word);
    }

    @Override
    public String toString() {
        return "Adverb{" +
                "word='" + word + '\'' +
                '}';
    }

    @Override
    public Type getType() {
        return Type.Adverb;
    }
}
