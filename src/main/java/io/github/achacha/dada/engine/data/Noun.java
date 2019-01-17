package io.github.achacha.dada.engine.data;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Noun
 */
public class Noun extends Word {

    public enum Form {
        /** base */
        singular,
        /** --s, -es, etc */
        plural
    }

    /** plural form of this word */
    protected final String plural;

    protected Noun(ArrayList<String> attrs) {
        super(attrs.get(0));
        Preconditions.checkArgument(attrs.size() == 2, "Noun expects word with attributes to be in 2 parts, attrs=%s", attrs);

        plural = attrs.get(1);

        LOGGER.trace("Adding noun=`{}`", this.word);
    }

    /**
     * Plural form of the word
     * @return String
     */
    @Nonnull
    public String getPlural() {
        return plural;
    }

    @Override
    public String toString() {
        return "Noun{" +
                Form.singular.name() + "='" + word + '\'' +
                ", " + Form.plural.name() + "='" + plural + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Noun noun = (Noun) o;

        return new EqualsBuilder()
                .append(word, noun.word)
                .append(plural, noun.plural)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(word)
                .append(plural)
                .toHashCode();
    }

    @Override
    public String toCsv() {
        return word.replace(' ','_') + ","+plural.replace(' ','_');
    }

    @Override
    public Collection<Pair<String,String>> getAllForms() {
        return Lists.newArrayList(
                Pair.of(Form.singular.name(), this.word),
                Pair.of(Form.plural.name(), this.plural)
        );
    }

    @Override
    public Type getType() {
        return Type.Noun;
    }
}
