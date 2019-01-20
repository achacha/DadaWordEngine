package io.github.achacha.dada.engine.builder;

import io.github.achacha.dada.engine.base.WordRendererHelper;
import io.github.achacha.dada.engine.data.Adjective;
import io.github.achacha.dada.engine.data.Noun;
import io.github.achacha.dada.engine.data.Pronoun;
import io.github.achacha.dada.engine.data.Verb;
import io.github.achacha.dada.engine.data.Word;
import io.github.achacha.dada.engine.data.WordData;
import io.github.achacha.dada.engine.data.WordsByType;
import io.github.achacha.dada.engine.render.AdjectiveRenderer;
import io.github.achacha.dada.engine.render.AdverbRenderer;
import io.github.achacha.dada.engine.render.ArticleMode;
import io.github.achacha.dada.engine.render.BaseWordRenderer;
import io.github.achacha.dada.engine.render.CapsMode;
import io.github.achacha.dada.engine.render.ConjunctionRenderer;
import io.github.achacha.dada.engine.render.NounRenderer;
import io.github.achacha.dada.engine.render.PrepositionRenderer;
import io.github.achacha.dada.engine.render.PronounRenderer;
import io.github.achacha.dada.engine.render.RenderContextToString;
import io.github.achacha.dada.engine.render.TextRenderer;
import io.github.achacha.dada.engine.render.VerbRenderer;
import io.github.achacha.dada.integration.tags.GlobalData;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Allows building a Sentence using words by type
 */
public class SentenceRendererBuilder {
    protected static final Logger LOGGER = LogManager.getLogger(Sentence.class);

    protected List<BaseWordRenderer> renderers = new ArrayList<>();

    protected WordData wordData;

    /**
     * Uses GlobalData loaded data which provides default if not otherwise configured
     * @see GlobalData
     */
    public SentenceRendererBuilder() {
        this.wordData = GlobalData.getWordData();
    }

    /**
     * Uses provided WordData
     * @param wordData WordData
     */
    public SentenceRendererBuilder(WordData wordData) {
        this.wordData = wordData;
    }

    /**
     * Given a parsed Sentence of words, build one with randomized words and use WordData provided in Sentence
     * Override via {@link #setWordData}
     * @param senetenceTemplate Sentence
     */
    public SentenceRendererBuilder(Sentence senetenceTemplate) {
        this.wordData = senetenceTemplate.wordData;
        renderers = senetenceTemplate.getWords().stream()
                .filter(word-> word.getType() != Word.Type.Unknown || StringUtils.isNotBlank(word.getWord()))
                .map(WordRendererHelper::mapWordToRenderer)
                .collect(Collectors.toList());
    }

    /**
     * @return List of word renderers
     */
    public List<BaseWordRenderer> getRenderers() {
        return renderers;
    }

    /**
     * Change WordData used
     * @param wordData WordData
     */
    public void setWordData(WordData wordData) {
        this.wordData = wordData;
    }

    /**
     * Add text
     * @param text String text
     * @return SentenceRendererBuilder this
     */
    public SentenceRendererBuilder text(String text) {
        renderers.add(new TextRenderer(text, new RenderContextToString<>(WordsByType.empty())));
        return this;
    }

    /**
     * Add text with given article and capitalization
     * @param text String text
     * @param articleMode {@link ArticleMode}
     * @param capsMode {@link CapsMode}
     * @return SentenceRendererBuilder this
     */
    public SentenceRendererBuilder text(String text, ArticleMode articleMode, CapsMode capsMode) {
        renderers.add(new TextRenderer(text, articleMode, capsMode, new RenderContextToString<>(WordsByType.empty())));
        return this;
    }

    /**
     * Add adjective with positive (base) form and no article and no capitalization
     * @return SentenceRendererBuilder this
     */
    public SentenceRendererBuilder adjective() {
        renderers.add(new AdjectiveRenderer());
        return this;
    }

    /**
     * Add adjective with given form and no article and no capitalization
     * @param form {@link Adjective.Form}
     * @return SentenceRendererBuilder this
     */
    public SentenceRendererBuilder adjective(Adjective.Form form) {
        renderers.add(new AdjectiveRenderer(form, ArticleMode.none, CapsMode.none, new RenderContextToString<>(wordData.getAdjectives())));
        return this;
    }

    /**
     * Add adjective with given article and capitalization
     * @param form {@link Adjective.Form}
     * @param articleMode {@link ArticleMode}
     * @param capsMode {@link CapsMode}
     * @return SentenceRendererBuilder this
     */
    public SentenceRendererBuilder adjective(Adjective.Form form, ArticleMode articleMode, CapsMode capsMode) {
        renderers.add(new AdjectiveRenderer(form, articleMode, capsMode, new RenderContextToString<>(wordData.getAdjectives())));
        return this;
    }

    /**
     * Add adverb with no article and no capitalization
     * @return SentenceRendererBuilder this
     */
    public SentenceRendererBuilder adverb() {
        renderers.add(new AdverbRenderer());
        return this;
    }

    /**
     * Add adverb with given article and capitalization
     * @param articleMode {@link ArticleMode}
     * @param capsMode {@link CapsMode}
     * @return SentenceRendererBuilder this
     */
    public SentenceRendererBuilder adverb(ArticleMode articleMode, CapsMode capsMode) {
        renderers.add(new AdverbRenderer(articleMode, capsMode, new RenderContextToString<>(wordData.getAdverbs())));
        return this;
    }

    /**
     * Add conjunction with no article and no capitalization
     * @return SentenceRendererBuilder this
     */
    public SentenceRendererBuilder conjunction() {
        renderers.add(new ConjunctionRenderer());
        return this;
    }

    /**
     * Add conjunction with given article and capitalization
     * @param articleMode {@link ArticleMode}
     * @param capsMode {@link CapsMode}
     * @return SentenceRendererBuilder this
     */
    public SentenceRendererBuilder conjunction(ArticleMode articleMode, CapsMode capsMode) {
        renderers.add(new ConjunctionRenderer(articleMode, capsMode, new RenderContextToString<>(wordData.getConjunctions())));
        return this;
    }

    /**
     * Add noun with no article and no capitalization
     * @return SentenceRendererBuilder this
     */
    public SentenceRendererBuilder noun() {
        renderers.add(new NounRenderer());
        return this;
    }

    /**
     * Add noun of given form with no article and no capitalization
     * @param form {@link Noun.Form}
     * @return SentenceRendererBuilder this
     */
    public SentenceRendererBuilder noun(Noun.Form form) {
        renderers.add(new NounRenderer(form, ArticleMode.none, CapsMode.none, new RenderContextToString<>(wordData.getNouns())));
        return this;
    }

    /**
     * Add noun with given form, article and capitalization
     * @param form {@link Noun.Form}
     * @param articleMode {@link ArticleMode}
     * @param capsMode {@link CapsMode}
     * @return SentenceRendererBuilder this
     */
    public SentenceRendererBuilder noun(Noun.Form form, ArticleMode articleMode, CapsMode capsMode) {
        renderers.add(new NounRenderer(form, articleMode, capsMode, new RenderContextToString<>(wordData.getNouns())));
        return this;
    }

    /**
     * Add preposition
     * @return SentenceRendererBuilder this
     */
    public SentenceRendererBuilder preposition() {
        renderers.add(new PrepositionRenderer());
        return this;
    }

    /**
     * Add preposition with given article and capitalization
     * @param articleMode {@link ArticleMode}
     * @param capsMode {@link CapsMode}
     * @return SentenceRendererBuilder this
     */
    public SentenceRendererBuilder preposition(ArticleMode articleMode, CapsMode capsMode) {
        renderers.add(new PrepositionRenderer(articleMode, capsMode, new RenderContextToString<>(wordData.getPrepositions())));
        return this;
    }

    /**
     * Add pronoun
     * @return SentenceRendererBuilder this
     */
    public SentenceRendererBuilder pronoun() {
        renderers.add(new PronounRenderer());
        return this;
    }

    /**
     * Add pronoun of given form with no article and no capitalization
     * @param form {@link Pronoun.Form}
     * @return SentenceRendererBuilder this
     *
     * see pronouns.csv
     */
    public SentenceRendererBuilder pronoun(Pronoun.Form form) {
        renderers.add(new PronounRenderer(form, ArticleMode.none, CapsMode.none, new RenderContextToString<>(wordData.getPronouns())));
        return this;
    }

    /**
     * Add pronoun of given form, article and capitalization
     * @param form {@link Pronoun.Form}
     * @param articleMode {@link ArticleMode}
     * @param capsMode {@link CapsMode}
     * @return SentenceRendererBuilder this
     *
     * see pronouns.csv
     */
    public SentenceRendererBuilder pronoun(Pronoun.Form form, ArticleMode articleMode, CapsMode capsMode) {
        renderers.add(new PronounRenderer(form, articleMode, capsMode, new RenderContextToString<>(wordData.getPronouns())));
        return this;
    }

    /**
     * Add verb with base form, no article and no capitalization
     * @return SentenceRendererBuilder this
     */
    public SentenceRendererBuilder verb() {
        renderers.add(new VerbRenderer());
        return this;
    }

    /**
     * Add verb with given form and no article and no capitalization
     * @param form {@link Verb.Form}
     * @return SentenceRendererBuilder this
     */
    public SentenceRendererBuilder verb(Verb.Form form) {
        renderers.add(new VerbRenderer(form, ArticleMode.none, CapsMode.none, new RenderContextToString<>(wordData.getVerbs())));
        return this;
    }

    /**
     * Add verb with given form, article and capitalization
     * @param form {@link Verb.Form}
     * @param articleMode {@link ArticleMode}
     * @param capsMode {@link CapsMode}
     * @return SentenceRendererBuilder this
     */
    public SentenceRendererBuilder verb(Verb.Form form, ArticleMode articleMode, CapsMode capsMode) {
        renderers.add(new VerbRenderer(form, articleMode, capsMode, new RenderContextToString<>(wordData.getVerbs())));
        return this;
    }

    /**
     * Build new sentence with using random words based on types
     * @return Sentence string randomized by their types
     */
    public String execute() {
        return renderers.stream().map(BaseWordRenderer::execute).collect(Collectors.joining(" "));
    }

    /**
     * Display structure
     * @return String
     */
    public String toStringStructure() {
        return renderers.stream().map(BaseWordRenderer::toString).collect(Collectors.joining("\n  ", "  ", ""));
    }

}
