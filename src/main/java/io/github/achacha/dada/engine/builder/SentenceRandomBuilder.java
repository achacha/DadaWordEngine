package io.github.achacha.dada.engine.builder;

import io.github.achacha.dada.engine.render.AdjectiveRenderer;
import io.github.achacha.dada.engine.render.AdverbRenderer;
import io.github.achacha.dada.engine.render.BaseWordRenderer;
import io.github.achacha.dada.engine.render.CapsMode;
import io.github.achacha.dada.engine.render.ConjunctionRenderer;
import io.github.achacha.dada.engine.render.NounRenderer;
import io.github.achacha.dada.engine.render.PronounRenderer;
import io.github.achacha.dada.engine.render.TextRenderer;
import io.github.achacha.dada.engine.render.VerbRenderer;
import io.github.achacha.dada.integration.tags.TagSingleton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Allows building a Sentence using words by type
 */
public class SentenceRandomBuilder {
    protected static final Logger LOGGER = LogManager.getLogger(Sentence.class);

    protected List<BaseWordRenderer> words = new ArrayList<>();

    /**
     * Use TagSingleton data
     * @see TagSingleton#init()
     */
    public SentenceRandomBuilder() {
    }

    /**
     * Add text
     * @param str Fixed text
     * @return SentenceRandomBuilder this
     */
    public SentenceRandomBuilder text(String str) {
        words.add(new TextRenderer(str));
        return this;
    }

    /**
     * Add adjective
     * @return SentenceRandomBuilder this
     */
    public SentenceRandomBuilder adjective() {
        words.add(new AdjectiveRenderer());
        return this;
    }

    /**
     * Add adjective
     * @param article ""=(none), "a", "the"
     * @param capsMode CapsMode
     * @param form ""=(default), "er", "est"
     * @return SentenceRandomBuilder this
     */
    public SentenceRandomBuilder adjective(String article, CapsMode capsMode, String form) {
        words.add(new AdjectiveRenderer(article, capsMode, form));
        return this;
    }

    /**
     * Add adverb
     * @return SentenceRandomBuilder this
     */
    public SentenceRandomBuilder adverb() {
        words.add(new AdverbRenderer());
        return this;
    }

    /**
     * Add adverb
     * @param article ""=(none), "a", "the"
     * @param capsMode CapsMode
     * @return SentenceRandomBuilder this
     */
    public SentenceRandomBuilder adverb(String article, CapsMode capsMode) {
        words.add(new AdverbRenderer(article, capsMode));
        return this;
    }

    /**
     * Add conjunction
     * @return SentenceRandomBuilder this
     */
    public SentenceRandomBuilder conjunction() {
        words.add(new ConjunctionRenderer());
        return this;
    }

    /**
     * Add conjunction
     * @param article ""=(none), "a", "the"
     * @param capsMode CapsMode
     * @return SentenceRandomBuilder this
     */
    public SentenceRandomBuilder conjunction(String article, CapsMode capsMode) {
        words.add(new ConjunctionRenderer(article, capsMode));
        return this;
    }

    /**
     * Add noun
     * @return SentenceRandomBuilder this
     */
    public SentenceRandomBuilder noun() {
        words.add(new NounRenderer());
        return this;
    }

    /**
     * Add noun
     * @param article ""=(none), "a", "the"
     * @param capsMode CapsMode
     * @param form ""=(singular), "singular", "plural"
     * @return SentenceRandomBuilder this
     */
    public SentenceRandomBuilder noun(String article, CapsMode capsMode, String form) {
        words.add(new NounRenderer(article, capsMode, form));
        return this;
    }

    /**
     * Add pronoun
     * @return SentenceRandomBuilder this
     */
    public SentenceRandomBuilder pronoun() {
        words.add(new PronounRenderer());
        return this;
    }

    /**
     * Add pronoun
     * @param article ""=(none), "a", "the"
     * @param capsMode CapsMode
     * @param form "personal", "subjective", "objective", "possessive", "demonstrative", "interrogative", "relative", "reflexive", "reciprocal", "indefinite"
     * @return SentenceRandomBuilder this
     *
     * see pronouns.csv
     */
    public SentenceRandomBuilder pronoun(String article, CapsMode capsMode, String form) {
        words.add(new PronounRenderer(article, capsMode, form));
        return this;
    }

    /**
     * Add verb
     * @return SentenceRandomBuilder this
     */
    public SentenceRandomBuilder verb() {
        words.add(new VerbRenderer());
        return this;
    }

    /**
     * Add verb
     * @param article ""=(none), "a", "the"
     * @param capsMode CapsMode
     * @param form ""=(default), "infinitive", "past", "singular", "present", "pastparticiple"
     * @return SentenceRandomBuilder this
     */
    public SentenceRandomBuilder verb(String article, CapsMode capsMode, String form) {
        words.add(new VerbRenderer(article, capsMode, form));
        return this;
    }

    /**
     * Build new sentence with using random words based on types
     * @return Sentence string randomized by their types
     */
    public String randomize() {
        return words.stream().map(BaseWordRenderer::execute).collect(Collectors.joining());
    }

    /**
     * Display structure
     * @return String
     */
    public String toStringStructure() {
        return words.stream().map(BaseWordRenderer::toString).collect(Collectors.joining("\n  ", "  ", ""));
    }

}
