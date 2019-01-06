package org.achacha.dada.engine.data;

import com.google.common.collect.Lists;
import org.achacha.dada.engine.base.WordHelper;

/**
 * Helper methods for testing
 */
public class TestWords {
    /**
     * Manually build Noun
     * @param noun String
     * @return Noun
     */
    static Noun makeNoun(String noun) {
        return new Noun(Lists.newArrayList(noun, WordHelper.makePlural(noun)));
    }

    /**
     * Manually build Verb
     * @param verb Verb
     * @param past Past tense
     * @param pastParticiple Past participle
     * @param singular Singular
     * @param present Present
     * @return Verb
     */
    static Verb makeVerb(String verb, String past, String pastParticiple, String singular, String present) {
        return new Verb(Lists.newArrayList(verb, past, pastParticiple, singular, present));
    }

    /**
     * Manually build Adjective
     * @param adjective Adjective
     * @param comparative Comparative
     * @param superlative Superlative
     * @return Adjective
     */
    static Adjective makeAdjective(String adjective, String comparative, String superlative) {
        return new Adjective(Lists.newArrayList(adjective, comparative, superlative));
    }

    /**
     * Manually build Adverb
     * @param adverb String
     * @return Adverb
     */
    static Adverb makeAdverb(String adverb) {
        return new Adverb(Lists.newArrayList(adverb));
    }

    /**
     * Manually build Conjunction
     * @param conjunction String
     * @return Conjunction
     */
    static Conjunction makeConjunction(String conjunction) {
        return new Conjunction(Lists.newArrayList(conjunction));
    }

    /**
     * Manually build Preposition
     * @param preposition String
     * @return Preposition
     */
    static Preposition makePreposition(String preposition) {
        return new Preposition(Lists.newArrayList(preposition));
    }
}
