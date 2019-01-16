package io.github.achacha.dada.engine.data;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Adjective
 */
public class Adjective extends Word {

    public enum Form {
        positive,       // Base form
        comparative,    // -er
        superlative     // -est
    }

    protected final String comparative;
    protected final String superlative;

    protected Adjective(ArrayList<String> attrs) {
        super(attrs.get(0));
        Preconditions.checkArgument(attrs.size() == 3, "Adjective expects word with attributes to be in 3 parts, attrs=%s", attrs);

        comparative = attrs.get(1);
        superlative = attrs.get(2);

        LOGGER.trace("Adding positive=`{}` comparative=`{}` superlative=`{}`", this.word, comparative, superlative);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Adjective adjective = (Adjective) o;

        return new EqualsBuilder()
                .append(word, adjective.word)
                .append(comparative, adjective.comparative)
                .append(superlative, adjective.superlative)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(word)
                .append(comparative)
                .append(superlative)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "Adjective{" +
                Form.positive.name() + "='" + word + '\'' +
                ", " + Form.comparative.name() + "='" + comparative + '\'' +
                ", " + Form.superlative.name() + "='" + superlative + '\'' +
                '}';
    }

    @Override
    public String toCsv() {
        return word.replace(' ','_') + ","
                + comparative.replace(' ','_') + ","
                + superlative.replace(' ','_');
    }

    public String getComparative() {
        return comparative;
    }

    public String getSuperlative() {
        return superlative;
    }

    @Override
    public Collection<Pair<String,String>> getAllForms() {
        return Lists.newArrayList(
                Pair.of(Form.positive.name(), this.word),
                Pair.of(Form.comparative.name(), this.comparative),
                Pair.of(Form.superlative.name(), this.superlative)
        );
    }

    @Override
    public Type getType() {
        return Type.Adjective;
    }
}
