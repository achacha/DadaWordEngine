package io.github.achacha.dada.engine.data;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Conjunction
 */
public class Conjunction extends Word {
    public enum Form {
        none   // conjunction has one form
    }

    protected Conjunction(ArrayList<String> attrs) {
        super(attrs.get(0));
        Preconditions.checkArgument(attrs.size() == 1, "Conjunction expects word with attributes to be in 1 parts, attrs=%s", attrs);

        LOGGER.trace("Adding conjunction=`{}`", this.word);
    }

    @Override
    public String toString() {
        return "Conjunction{" +
                "word='" + word + '\'' +
                '}';
    }

    @Override
    public Type getType() {
        return Type.Conjunction;
    }

    @Override
    public Collection<Pair<String,String>> getAllForms() {
        return Lists.newArrayList(
                Pair.of(Conjunction.Form.none.name(), this.word)
        );
    }
}
