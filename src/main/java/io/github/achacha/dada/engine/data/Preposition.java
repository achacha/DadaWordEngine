package io.github.achacha.dada.engine.data;

import com.google.common.base.Preconditions;

import java.util.ArrayList;

/**
 * Preposition
 */
public class Preposition extends Word {
    public enum Form {
        none   // preposition has one form
    }

    protected Preposition(ArrayList<String> attrs) {
        super(attrs.get(0));
        Preconditions.checkArgument(attrs.size() == 1, "Preposition expects word with attributes to be in 1 parts, attrs=%s", attrs);

        LOGGER.trace("Adding preposition=`{}`", this.word);
    }

    @Override
    public String toString() {
        return "Preposition{" +
                "word='" + word + '\'' +
                '}';
    }

    @Override
    public Type getType() {
        return Type.Preposition;
    }
}
