package org.achacha.dada.engine.data;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;

public abstract class Word implements Comparable<Word> {
    protected static final Logger LOGGER = LogManager.getLogger(Word.class);

    /**
     * Word type
     */
    public enum Type {
        Noun,
        Adjective,
        Verb,
        Adverb,
        Pronoun,
        Conjunction,
        Preposition,
        Unknown
        ;

        public String getTypeName() {
            return this.name().toLowerCase();
        }
    }

    /** Actual word */
    protected final String word;

    /**
     * Word compare
     * @param o Word
     * @return result of compare
     */
    @Override
    public int compareTo(@Nonnull Word o) {
        return this.word.compareTo(o.word);
    }

    public int compareToForSave(@Nonnull Word o) {
        return StringUtils.compareIgnoreCase(
                word.replaceAll("^[\\_\\-\\ ]+$", ""),
                o.word.replaceAll("^[\\_\\-\\ ]+$", "")
        );
    }

    /**
     * Parse line and create T type of word
     * @param type Word.Type
     * @param line String comma separated line to parse
     * @param <T> extends Word
     * @return T
     */
    @Nonnull
    @SuppressWarnings("unchecked")
    public static <T extends Word> T parse(Type type, String line) {

        String[] parsedParts = line.split("[ ]*,[ ]*");
        ArrayList<String> parts = new ArrayList<>(parsedParts.length);
        for (String part : parsedParts) {
            parts.add(part.replace('_',' '));
        }

        Preconditions.checkState(parts.size() > 0, "Did not find word with attributes: {}", line);
        LOGGER.trace("Parsing type={} with line={}", type, line);
        final T word;
        switch(type) {
            case Pronoun:
                word = (T)new Pronoun(parts);
                break;

            case Conjunction:
                word = (T)new Conjunction(parts);
                break;

            case Noun:
                word = (T)new Noun(parts);
                break;

            case Adjective:
                word = (T)new Adjective(parts);
                break;

            case Verb:
                word = (T)new Verb(parts);
                break;

            case Adverb:
                word = (T)new Adverb(parts);
                break;

            case Preposition:
                word = (T)new Preposition(parts);
                break;

            default:
                word = (T)new Text(parts);
        }
        return word;
    }


    Word(String word) {
        this.word = word;
    }

    /**
     * Word string
     * @return String
     */
    @Nonnull
    public String getWord() {
        return word;
    }

    /**
     * Word type
     * @return Word.Type
     */
    public Word.Type getType() {
        return Type.Unknown;
    }

    /**
     * Get all forms of this word
     * By default there is just one word associated with it, some types have multiple forms (such as Verb)
     * @return Collection of pair of form and word text
     * @see Verb
     */
    public Collection<Pair<String,String>> getAllForms() {
        return Lists.newArrayList(Pair.of("", this.word));
    }

    /**
     * Represent this word as a line in CSV file
     * Space converts to _
     * @return String
     */
    public String toCsv() {
        return word.replace(' ', '_');
    }
}
