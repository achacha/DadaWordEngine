package org.achacha.dada.engine.data;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Nonnull;

/**
 * Saved word data
 */
public class SavedWord {
    /**
     * Word and all of its forms
     */
    private final Word word;

    /**
     * Form used to render the saved word
     * This is only useful in knowing which form was used at save time so that
     * rhyme based matching knows which form to rhyme against
     */
    private final String form;

    public SavedWord(@Nonnull Word word, @Nonnull String form) {
        this.word = word;
        this.form = form;
        Preconditions.checkNotNull(form, "Form must not be null, base is specified by empty string");
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("word", word)
                .append("form", form)
                .toString();
    }

    /**
     * @return Word
     */
    @Nonnull
    public Word getWord() {
        return word;
    }

    /**
     * @return form String used when this word was saved
     */
    @Nonnull
    public String getForm() {
        return form;
    }
}
