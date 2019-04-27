package io.github.achacha.dada.engine.builder;

import io.github.achacha.dada.engine.base.WordRendererHelper;
import io.github.achacha.dada.engine.data.Adjective;
import io.github.achacha.dada.engine.data.Adverb;
import io.github.achacha.dada.engine.data.Conjunction;
import io.github.achacha.dada.engine.data.Noun;
import io.github.achacha.dada.engine.data.Preposition;
import io.github.achacha.dada.engine.data.Pronoun;
import io.github.achacha.dada.engine.data.SavedWord;
import io.github.achacha.dada.engine.data.Text;
import io.github.achacha.dada.engine.data.Verb;
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
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Allows building a Sentence using words by type
 * Renderers do not add any additional spacing
 * Text is used to generate custom spacing, line breaks, etc.
 */
public class SentenceRendererBuilder {
    protected static final Logger LOGGER = LogManager.getLogger(Sentence.class);

    protected List<BaseWordRenderer> renderers = new ArrayList<>();

    protected WordData wordData;

    /**
     * Single map used by all renderers to save words
     */
    private Map<String, SavedWord> attributes = new HashMap<>();

    private RenderContextToString<Adjective> adjectiveRenderContext;
    private RenderContextToString<Adverb> adverbRenderContext;
    private RenderContextToString<Conjunction> conjunctionRenderContext;
    private RenderContextToString<Noun> nounRenderContext;
    private RenderContextToString<Preposition> prepositionRenderContext;
    private RenderContextToString<Pronoun> pronounRenderContext;
    private RenderContextToString<Text> textRenderContext;
    private RenderContextToString<Verb> verbRenderContext;

    /**
     * Uses GlobalData loaded data which provides default if not otherwise configured
     * @see GlobalData
     */
    public SentenceRendererBuilder() {
        this.wordData = GlobalData.getWordData();
        initializeContexts();
    }

    /**
     * Uses provided WordData
     * @param wordData WordData
     */
    public SentenceRendererBuilder(WordData wordData) {
        this.wordData = wordData;
        initializeContexts();
    }

    /**
     * Given a parsed Sentence of words, build one with randomized words and use WordData provided in Sentence
     * Override via {@link #setWordData}
     * @param senetenceTemplate Sentence
     */
    public SentenceRendererBuilder(Sentence senetenceTemplate) {
        this.wordData = senetenceTemplate.wordData;
        initializeContexts();
        senetenceTemplate.getWords().forEach(
                savedWord->{
                    BaseWordRenderer renderer = WordRendererHelper.mapWordToRenderer(savedWord.getWord());
                    renderer.setForm(savedWord.getFormName());
                    renderers.add(renderer);
                }
        );
    }

    private void initializeContexts() {
        adjectiveRenderContext = new RenderContextToString<>(wordData.getAdjectives(), attributes);
        adverbRenderContext = new RenderContextToString<>(wordData.getAdverbs(), attributes);
        conjunctionRenderContext = new RenderContextToString<>(wordData.getConjunctions(), attributes);
        nounRenderContext = new RenderContextToString<>(wordData.getNouns(), attributes);
        prepositionRenderContext = new RenderContextToString<>(wordData.getPrepositions(), attributes);
        pronounRenderContext = new RenderContextToString<>(wordData.getPronouns(), attributes);
        textRenderContext = new RenderContextToString<>(WordsByType.empty(), attributes);
        verbRenderContext = new RenderContextToString<>(wordData.getVerbs(), attributes);
    }

    /**
     * @return List of word renderers
     */
    public List<BaseWordRenderer> getRenderers() {
        return renderers;
    }

    /**
     * Change WordData used on all renderers
     * @param wordData WordData
     */
    public void setWordData(WordData wordData) {
        this.wordData = wordData;
        adjectiveRenderContext.setWords(wordData.getAdjectives());
        adverbRenderContext.setWords(wordData.getAdverbs());
        conjunctionRenderContext.setWords(wordData.getConjunctions());
        nounRenderContext.setWords(wordData.getNouns());
        prepositionRenderContext.setWords(wordData.getPrepositions());
        pronounRenderContext.setWords(wordData.getPronouns());
        verbRenderContext.setWords(wordData.getVerbs());
    }

    /**
     * Create builder where the build will return this instance to allow continued chain building
     * @return {@link TextRenderer.Builder}
     */
    public TextRenderer.Builder textBuilder() {
        return new TextRenderer.Builder(this).withRenderContext(textRenderContext);
    }

    /**
     * Add text
     * @param text String text
     * @return SentenceRendererBuilder this
     */
    public SentenceRendererBuilder text(String text) {
        renderers.add(new TextRenderer(text, textRenderContext));
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
        renderers.add(new TextRenderer(text, articleMode, capsMode, textRenderContext));
        return this;
    }

    /**
     * Create builder where the build will return this instance to allow continued chain building
     * @return {@link AdjectiveRenderer.Builder}
     */
    public AdjectiveRenderer.Builder adjectiveBuilder() {
        return new AdjectiveRenderer.Builder(this).withRenderContext(adjectiveRenderContext);
    }

    /**
     * Add adjective with positive (base) form and no article and no capitalization
     * @return SentenceRendererBuilder this
     */
    public SentenceRendererBuilder adjective() {
        renderers.add(new AdjectiveRenderer(adjectiveRenderContext));
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
        renderers.add(new AdjectiveRenderer(form, articleMode, capsMode, adjectiveRenderContext));
        return this;
    }

    /**
     * Create builder where the build will return this instance to allow continued chain building
     * @return {@link AdverbRenderer.Builder}
     */
    public AdverbRenderer.Builder adverbBuilder() {
        return new AdverbRenderer.Builder(this).withRenderContext(adverbRenderContext);
    }

    /**
     * Add adverb with no article and no capitalization
     * @return SentenceRendererBuilder this
     */
    public SentenceRendererBuilder adverb() {
        renderers.add(new AdverbRenderer(adverbRenderContext));
        return this;
    }

    /**
     * Add adverb with given article and capitalization
     * @param articleMode {@link ArticleMode}
     * @param capsMode {@link CapsMode}
     * @return SentenceRendererBuilder this
     */
    public SentenceRendererBuilder adverb(ArticleMode articleMode, CapsMode capsMode) {
        renderers.add(new AdverbRenderer(articleMode, capsMode, adverbRenderContext));
        return this;
    }

    /**
     * Create builder where the build will return this instance to allow continued chain building
     * @return {@link ConjunctionRenderer.Builder}
     */
    public ConjunctionRenderer.Builder conjunctionBuilder() {
        return new ConjunctionRenderer.Builder(this).withRenderContext(conjunctionRenderContext);
    }

    /**
     * Add conjunction with no article and no capitalization
     * @return SentenceRendererBuilder this
     */
    public SentenceRendererBuilder conjunction() {
        renderers.add(new ConjunctionRenderer(conjunctionRenderContext));
        return this;
    }

    /**
     * Add conjunction with given article and capitalization
     * @param articleMode {@link ArticleMode}
     * @param capsMode {@link CapsMode}
     * @return SentenceRendererBuilder this
     */
    public SentenceRendererBuilder conjunction(ArticleMode articleMode, CapsMode capsMode) {
        renderers.add(new ConjunctionRenderer(articleMode, capsMode, conjunctionRenderContext));
        return this;
    }

    /**
     * Create builder where the build will return this instance to allow continued chain building
     * @return {@link NounRenderer.Builder}
     */
    public NounRenderer.Builder nounBuilder() {
        return new NounRenderer.Builder(this).withRenderContext(nounRenderContext);
    }

    /**
     * Add noun with no article and no capitalization
     * @return SentenceRendererBuilder this
     */
    public SentenceRendererBuilder noun() {
        renderers.add(new NounRenderer(nounRenderContext));
        return this;
    }

    /**
     * Add noun of given form with no article and no capitalization
     * @param form {@link Noun.Form}
     * @return SentenceRendererBuilder this
     */
    public SentenceRendererBuilder noun(Noun.Form form) {
        renderers.add(new NounRenderer(form, ArticleMode.none, CapsMode.none, nounRenderContext));
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
        renderers.add(new NounRenderer(form, articleMode, capsMode, nounRenderContext));
        return this;
    }

    /**
     * Create builder where the build will return this instance to allow continued chain building
     * @return {@link PrepositionRenderer.Builder}
     */
    public PrepositionRenderer.Builder prepositionBuilder() {
        return new PrepositionRenderer.Builder(this).withRenderContext(prepositionRenderContext);
    }

    /**
     * Add preposition
     * @return SentenceRendererBuilder this
     */
    public SentenceRendererBuilder preposition() {
        renderers.add(new PrepositionRenderer(prepositionRenderContext));
        return this;
    }

    /**
     * Add preposition with given article and capitalization
     * @param articleMode {@link ArticleMode}
     * @param capsMode {@link CapsMode}
     * @return SentenceRendererBuilder this
     */
    public SentenceRendererBuilder preposition(ArticleMode articleMode, CapsMode capsMode) {
        renderers.add(new PrepositionRenderer(articleMode, capsMode, prepositionRenderContext));
        return this;
    }

    /**
     * Create builder where the build will return this instance to allow continued chain building
     * @return {@link PronounRenderer.Builder}
     */
    public PronounRenderer.Builder pronounBuilder() {
        return new PronounRenderer.Builder(this).withRenderContext(pronounRenderContext);
    }

    /**
     * Add pronoun
     * @return SentenceRendererBuilder this
     */
    public SentenceRendererBuilder pronoun() {
        renderers.add(new PronounRenderer(pronounRenderContext));
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
        renderers.add(new PronounRenderer(form, ArticleMode.none, CapsMode.none, pronounRenderContext));
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
        renderers.add(new PronounRenderer(form, articleMode, capsMode, pronounRenderContext));
        return this;
    }

    /**
     * Create builder where the build will return this instance to allow continued chain building
     * @return {@link VerbRenderer.Builder}
     */
    public VerbRenderer.Builder verbBuilder() {
        return new VerbRenderer.Builder(this).withRenderContext(verbRenderContext);
    }

    /**
     * Add verb with base form, no article and no capitalization
     * @return SentenceRendererBuilder this
     */
    public SentenceRendererBuilder verb() {
        renderers.add(new VerbRenderer(verbRenderContext));
        return this;
    }

    /**
     * Add verb with given form and no article and no capitalization
     * @param form {@link Verb.Form}
     * @return SentenceRendererBuilder this
     */
    public SentenceRendererBuilder verb(Verb.Form form) {
        renderers.add(new VerbRenderer(form, ArticleMode.none, CapsMode.none, verbRenderContext));
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
        renderers.add(new VerbRenderer(form, articleMode, capsMode, verbRenderContext));
        return this;
    }

    /**
     * Build new sentence using random words based on types
     * Space is only added between non-Text types
     * @return Sentence string randomized by their types
     */
    public String execute() {
        return renderers.stream().map(BaseWordRenderer::execute).collect(Collectors.joining());
    }

    /**
     * Display attributes and renderers
     * @return String
     */
    public String toStringStructure() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("attributes", attributes.entrySet().stream().map(entry-> "  "+entry.getKey()+"="+entry.getValue()).collect(Collectors.joining("\n")))
                .append("renderers", renderers.stream().map(BaseWordRenderer::toString).collect(Collectors.joining("\n  ", "  ", "")))
                .build();
    }
}
