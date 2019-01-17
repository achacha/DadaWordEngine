package io.github.achacha.dada.engine.data;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Verb
 *    past - sat, ate, drank, ran
 *    pastParticiple (has/had) - sat, ate, drunk, ran
 *    singular - sits, eats, drinks, runs
 *    present - sitting, eating, drinking, running
 *    infinitive - to sit, to eat, to drink, to run
 */
public class Verb extends Word {
    public enum Form {
        base,
        /** -ed */
        past,
        /** (has) - */
        pastparticiple,
        /** -s */
        singular,
        /** -ing */
        present,
        /** to - */
        infinitive
    }

    protected final String past;
    protected final String pastParticiple;
    protected final String singular;
    protected final String present;

    protected Verb(ArrayList<String> attrs) {
        super(attrs.get(0));
        Preconditions.checkArgument(attrs.size() == 5, "Verb expects word with attributes to be in 5 parts, attrs=%s", attrs);

        past = attrs.get(1);
        pastParticiple = attrs.get(2);
        singular = attrs.get(3);
        present = attrs.get(4);

        LOGGER.trace("Adding verb=`{}` past=`{}` pastParticiple={} singular={} present={}", this.word, this.past, this.pastParticiple, this.singular, this.present);
    }

    @Override
    public String toString() {
        return "Verb{" +
                "past='" + past + '\'' +
                ", pastParticiple='" + pastParticiple + '\'' +
                ", singular='" + singular + '\'' +
                ", present='" + present + '\'' +
                ", base='" + word + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Verb verb = (Verb) o;

        return new EqualsBuilder()
                .append(word, verb.word)
                .append(past, verb.past)
                .append(pastParticiple, verb.pastParticiple)
                .append(singular, verb.singular)
                .append(present, verb.present)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(word)
                .append(past)
                .append(pastParticiple)
                .append(singular)
                .append(present)
                .toHashCode();
    }

    /**
     * Convert a verb to infinitive form
     * @param word String
     * @return Infinitive form
     */
    public static String toInfinitive(String word) {
        return "to " + word;
    }

    public String getInfinitive() {
        return toInfinitive(word);
    }

    public String getPast() {
        return past;
    }

    public String getPastParticiple() {
        return pastParticiple;
    }

    public String getSingular() {
        return singular;
    }

    public String getPresent() {
        return present;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<Pair<String,String>> getAllForms() {
        return Sets.newHashSet(
                Pair.of("base", this.word),
                Pair.of("past", this.past),
                Pair.of("pastParticiple", this.pastParticiple),
                Pair.of("singular", this.singular),
                Pair.of("present", this.present)
        );
    }

    @Override
    public String toCsv() {
        return word.replace(' ','_') + ","
                + past.replace(' ','_') + ","
                + pastParticiple.replace(' ','_') + ","
                + singular.replace(' ','_') + ","
                + present.replace(' ','_');
    }

    @Override
    public Type getType() {
        return Type.Verb;
    }
}
