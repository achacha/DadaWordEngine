package io.github.achacha.dada.engine.builder;

import io.github.achacha.dada.engine.data.Adjective;
import io.github.achacha.dada.engine.data.Noun;
import io.github.achacha.dada.engine.data.Pronoun;
import io.github.achacha.dada.engine.data.Verb;
import io.github.achacha.dada.engine.render.AdjectiveRenderer;
import io.github.achacha.dada.engine.render.AdverbRenderer;
import io.github.achacha.dada.engine.render.ArticleMode;
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
     * @return List of word renderers
     */
    public List<BaseWordRenderer> getWords() {
        return words;
    }

    /**
     * Add text
     * @param text String text
     * @return SentenceRandomBuilder this
     */
    public SentenceRandomBuilder text(String text) {
        words.add(new TextRenderer(text));
        return this;
    }

    /**
     * Add text with given article and capitalization
     * @param text String text
     * @param articleMode {@link ArticleMode}
     * @param capsMode {@link CapsMode}
     * @return SentenceRandomBuilder this
     */
    public SentenceRandomBuilder text(String text, ArticleMode articleMode, CapsMode capsMode) {
        words.add(new TextRenderer(text, articleMode, capsMode));
        return this;
    }

    /**
     * Add adjective with positive (base) form and no article and no capitalization
     * @return SentenceRandomBuilder this
     */
    public SentenceRandomBuilder adjective() {
        words.add(new AdjectiveRenderer());
        return this;
    }

    /**
     * Add adjective with given form and no article and no capitalization
     * @param form {@link Adjective.Form}
     * @return SentenceRandomBuilder this
     */
    public SentenceRandomBuilder adjective(Adjective.Form form) {
        words.add(new AdjectiveRenderer(form, ArticleMode.none, CapsMode.none));
        return this;
    }

    /**
     * Add adjective with given article and capitalization
     * @param form {@link Adjective.Form}
     * @param articleMode {@link ArticleMode}
     * @param capsMode {@link CapsMode}
     * @return SentenceRandomBuilder this
     */
    public SentenceRandomBuilder adjective(Adjective.Form form, ArticleMode articleMode, CapsMode capsMode) {
        words.add(new AdjectiveRenderer(form, articleMode, capsMode));
        return this;
    }

    /**
     * Add adverb with no article and no capitalization
     * @return SentenceRandomBuilder this
     */
    public SentenceRandomBuilder adverb() {
        words.add(new AdverbRenderer());
        return this;
    }

    /**
     * Add adverb with given article and capitalization
     * @param articleMode {@link ArticleMode}
     * @param capsMode {@link CapsMode}
     * @return SentenceRandomBuilder this
     */
    public SentenceRandomBuilder adverb(ArticleMode articleMode, CapsMode capsMode) {
        words.add(new AdverbRenderer(articleMode, capsMode));
        return this;
    }

    /**
     * Add conjunction with no article and no capitalization
     * @return SentenceRandomBuilder this
     */
    public SentenceRandomBuilder conjunction() {
        words.add(new ConjunctionRenderer());
        return this;
    }

    /**
     * Add conjunction with given article and capitalization
     * @param articleMode {@link ArticleMode}
     * @param capsMode {@link CapsMode}
     * @return SentenceRandomBuilder this
     */
    public SentenceRandomBuilder conjunction(ArticleMode articleMode, CapsMode capsMode) {
        words.add(new ConjunctionRenderer(articleMode, capsMode));
        return this;
    }

    /**
     * Add noun with no article and no capitalization
     * @return SentenceRandomBuilder this
     */
    public SentenceRandomBuilder noun() {
        words.add(new NounRenderer());
        return this;
    }

    /**
     * Add noun of given form with no article and no capitalization
     * @param form {@link Noun.Form}
     * @return SentenceRandomBuilder this
     */
    public SentenceRandomBuilder noun(Noun.Form form) {
        words.add(new NounRenderer(form, ArticleMode.none, CapsMode.none));
        return this;
    }

    /**
     * Add noun with given form, article and capitalization
     * @param form {@link Noun.Form}
     * @param articleMode {@link ArticleMode}
     * @param capsMode {@link CapsMode}
     * @return SentenceRandomBuilder this
     */
    public SentenceRandomBuilder noun(Noun.Form form, ArticleMode articleMode, CapsMode capsMode) {
        words.add(new NounRenderer(form, articleMode, capsMode));
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
     * Add pronoun of given form, article and capitalization
     * @param form {@link Pronoun.Form}
     * @param articleMode {@link ArticleMode}
     * @param capsMode {@link CapsMode}
     * @return SentenceRandomBuilder this
     *
     * see pronouns.csv
     */
    public SentenceRandomBuilder pronoun(Pronoun.Form form, ArticleMode articleMode, CapsMode capsMode) {
        words.add(new PronounRenderer(form, articleMode, capsMode));
        return this;
    }

    /**
     * Add verb with base form, no article and no capitalization
     * @return SentenceRandomBuilder this
     */
    public SentenceRandomBuilder verb() {
        words.add(new VerbRenderer());
        return this;
    }

    /**
     * Add verb with given form and no article and no capitalization
     * @param form {@link Verb.Form}
     * @return SentenceRandomBuilder this
     */
    public SentenceRandomBuilder verb(Verb.Form form) {
        words.add(new VerbRenderer(form, ArticleMode.none, CapsMode.none));
        return this;
    }

    /**
     * Add verb with given form, article and capitalization
     * @param form {@link Verb.Form}
     * @param articleMode {@link ArticleMode}
     * @param capsMode {@link CapsMode}
     * @return SentenceRandomBuilder this
     */
    public SentenceRandomBuilder verb(Verb.Form form, ArticleMode articleMode, CapsMode capsMode) {
        words.add(new VerbRenderer(form, articleMode, capsMode));
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
