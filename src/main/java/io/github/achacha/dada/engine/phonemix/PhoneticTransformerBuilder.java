package io.github.achacha.dada.engine.phonemix;

/**
 * Builder for transformer
 */
public class PhoneticTransformerBuilder {

    private enum TYPE {
        Phonemix,
        PhonemixAggressive
    }

    private TYPE type = TYPE.Phonemix;

    /**
     * Start position
     */
    int startPos = 0;

    /**
     * Reverse before transform
     */
    boolean reverse = false;

    /**
     * Be default leading vowel is kept
     */
    boolean keepLeadingVowel = true;

    /**
     * Default simple transformer
     */
    private static final PhoneticTransformer DEFAULT_FORWARD = PhoneticTransformerBuilder.builder().build();

    /**
     * Default reverse transformer
     */
    private static final PhoneticTransformer DEFAULT_REVERSE = PhoneticTransformerBuilder.builder().withReverse().build();

    /**
     * @return Default XFormer
     */
    public static PhoneticTransformer getDefaultForward() {
        return DEFAULT_FORWARD;
    }

    /**
     * @return Default reverse XFormer
     */
    public static PhoneticTransformer getDefaultReverse() {
        return DEFAULT_REVERSE;
    }

    /**
     * @return Build a transformer
     */
    public static PhoneticTransformerBuilder builder() {
        return new PhoneticTransformerBuilder();
    }

    private PhoneticTransformerBuilder() {

    }

    /**
     * Allow to start processing at a position
     *
     * @param startPos int
     * @return PhoneticTransformerBuilder this
     */
    public PhoneticTransformerBuilder withStartPosition(int startPos) {
        this.startPos = startPos;
        return this;
    }

    /**
     * Use aggressive transformer that ignores more non-essential sounds
     * @return PhoneticTransformerBuilder this
     */
    public PhoneticTransformerBuilder withAggressiveTransformer() {
        this.type = TYPE.PhonemixAggressive;
        return this;
    }

    /**
     * Ignore leading vowel
     * By default all leading phonemixs are kept
     * @return PhoneticTransformerBuilder this
     */
    public PhoneticTransformerBuilder withIgnoreLeadingVowel() {
        this.keepLeadingVowel = false;
        return this;
    }

    /**
     * If enabled all resulting data is reversed
     * If multiple words are detected each will be reversed while sentence structure preserved
     * Reversing phonemix is mainly for rhyming words
     * @return PhoneticTransformerBuilder this
     */
    public PhoneticTransformerBuilder withReverse() {
        this.reverse = true;
        return this;
    }

    /**
     * Build the transformer
     * @return PhoneticTransformer object to match builder config
     */
    public PhoneticTransformer build() {
        switch (type) {
            case PhonemixAggressive:
                return new PhonemixAggressiveTransformer(this);

            default:
                return new PhonemixCompactingTransformer(this);
        }
    }
}
