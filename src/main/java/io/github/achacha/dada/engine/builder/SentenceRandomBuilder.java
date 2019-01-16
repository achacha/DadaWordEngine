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
     * @return List of wond renderers
     */
    public List<BaseWordRenderer> getWords() {
        return words;
    }

    /**
     * Add text
     * @param text Fixed text
     * @return SentenceRandomBuilder this
     */
    public SentenceRandomBuilder text(String text) {
        words.add(new TextRenderer(text));
        return this;
    }

    /**
     * Add text
     * @param text Fixed text
     * @param articleMode {@link ArticleMode}
     * @param capsMode {@link CapsMode}
     * @return SentenceRandomBuilder this
     */
    public SentenceRandomBuilder text(String text, ArticleMode articleMode, CapsMode capsMode) {
        words.add(new TextRenderer(text, articleMode, capsMode));
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
     * @param articleMode {@link ArticleMode}
     * @param capsMode {@link CapsMode}
     * @param form {@link Adjective.Form}
     * @return SentenceRandomBuilder this
     */
    public SentenceRandomBuilder adjective(ArticleMode articleMode, CapsMode capsMode, Adjective.Form form) {
        words.add(new AdjectiveRenderer(articleMode, capsMode, form));
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
     * @param articleMode {@link ArticleMode}
     * @param capsMode {@link CapsMode}
     * @return SentenceRandomBuilder this
     */
    public SentenceRandomBuilder adverb(ArticleMode articleMode, CapsMode capsMode) {
        words.add(new AdverbRenderer(articleMode, capsMode));
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
     * @param articleMode {@link ArticleMode}
     * @param capsMode {@link CapsMode}
     * @return SentenceRandomBuilder this
     */
    public SentenceRandomBuilder conjunction(ArticleMode articleMode, CapsMode capsMode) {
        words.add(new ConjunctionRenderer(articleMode, capsMode));
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
     * @param articleMode {@link ArticleMode}
     * @param capsMode {@link CapsMode}
     * @param form {@link Noun.Form}
     * @return SentenceRandomBuilder this
     */
    public SentenceRandomBuilder noun(ArticleMode articleMode, CapsMode capsMode, Noun.Form form) {
        words.add(new NounRenderer(articleMode, capsMode, form));
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
     * @param articleMode {@link ArticleMode}
     * @param capsMode {@link CapsMode}
     * @param form {@link Pronoun.Form}
     * @return SentenceRandomBuilder this
     *
     * see pronouns.csv
     */
    public SentenceRandomBuilder pronoun(ArticleMode articleMode, CapsMode capsMode, Pronoun.Form form) {
        words.add(new PronounRenderer(articleMode, capsMode, form));
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
     * @param articleMode {@link ArticleMode}
     * @param capsMode {@link CapsMode}
     * @param form {@link Verb.Form}
     * @return SentenceRandomBuilder this
     */
    public SentenceRandomBuilder verb(ArticleMode articleMode, CapsMode capsMode, Verb.Form form) {
        words.add(new VerbRenderer(articleMode, capsMode, form));
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
