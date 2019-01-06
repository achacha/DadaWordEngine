package org.achacha.dada.engine.phonemix;

import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Transforms sentences to phonetic representation
 */
public interface PhoneticTransformer {
    /**
     * Transform text to phonetic form
     * Words are split and then transformed
     *
     * @param text String
     * @return Phonetic version of the text or empty string if not enough to process
     */
    @Nonnull
    String transform(String text);

    /**
     * Transform and index with offset into the original text
     * Pair[transformed text, position into original text]
     * @param text String
     * @return List of Pairs or empty collection if start position is beyond available text
     */
    @Nonnull
    List<Pair<String,Integer>> transformAndIndex(String text);
}
