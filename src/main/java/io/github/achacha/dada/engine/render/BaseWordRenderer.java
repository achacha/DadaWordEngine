package io.github.achacha.dada.engine.render;

import com.google.common.base.Preconditions;
import io.github.achacha.dada.engine.base.WordHelper;
import io.github.achacha.dada.engine.data.SavedWord;
import io.github.achacha.dada.engine.data.Text;
import io.github.achacha.dada.engine.data.Word;
import io.github.achacha.dada.integration.tags.GlobalData;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.text.WordUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Predicate;

public abstract class BaseWordRenderer<T extends Word> {
    protected static final Logger LOGGER = LogManager.getLogger(BaseWordRenderer.class);

    /**
     * RenderContext and data for this word
     */
    protected RenderContext<T> rendererContext;

    /** Article prefix to use */
    protected ArticleMode articleMode = ArticleMode.none;

    /**
     * Capitalization mode, applied as the last thing
     */
    protected CapsMode capsMode = CapsMode.none;

    /**
     * load="[key]" to load the current Word from saved pool, if not set random used
     */
    protected String loadKey;

    /**
     * save="[key]" to save the current word to a pool
     */
    protected String saveKey;

    /**
     * rhyme="[key]" try to rhyme this word with a saved word
     * If loadKey is also used, this does nothing
     */
    protected String rhymeKey;

    /**
     * If provided try to rhyme word with this one
     * Cannot be used in combination with rhymeKey
     */
    protected String rhymeWith;

    /**
     * Syllables desired count
     * When 0 we don't care about syllables
     */
    protected int syllablesDesired;

    /**
     * Fallback word, if randomization is not used, this is the word that will be used
     * This is also the body of the text based sub-classes i.e. {@link TextRenderer}
     */
    protected String fallback;

    /**
     * Function that determines if the fallback should be used
     */
    protected Predicate<BaseWordRenderer<T>> fallbackPredicate;

    /** How many words to try to get syllables desired before using the closest one */
    protected final static int TRIES_TO_GET_SYLLABLES = 10;

    /**
     * Construct renderer for the word with rendering context with no capitalization and no article
     * NOTE: Form of the word is handled by each specific word class
     *
     * @param renderContext {@link RenderContext}
     * @see RenderContextToString
     */
    public BaseWordRenderer(RenderContext<T> renderContext) {
        this.rendererContext = renderContext;
    }

    /**
     * Construct renderer for the word with rendering context
     * NOTE: Form of the word is handled by each specific word class
     *
     * @param renderContext {@link RenderContext}
     * @param articleMode {@link ArticleMode}
     * @param capsMode {@link CapsMode}
     * @see RenderContextToString
     */
    public BaseWordRenderer(RenderContext<T> renderContext, ArticleMode articleMode, CapsMode capsMode) {
        this.rendererContext = renderContext;
        this.articleMode = articleMode;
        this.capsMode = capsMode;
    }

    /**
     * Set new rendering context for the specific word
     * @param rendererContext RenderContext
     */
    public void setRendererContext(RenderContext<T> rendererContext) {
        this.rendererContext = rendererContext;
    }

    /**
     * @return RenderContext associated with this rendererContext
     */
    public RenderContext<T> getRendererContext() {
        return rendererContext;
    }

    /**
     * @return {@link Word.Type of the renderer}
     */
    public abstract Word.Type getType();

    /**
     * @return Fallback word when unable to generate/select a word
     */
    public String getFallback() {
        return fallback;
    }

    /**
     * Article mode
     * @return {@link ArticleMode}
     */
    public ArticleMode getArticleMode() {
        return articleMode;
    }

    /**
     * Word constant to rhyme with
     * @return String
     */
    public String getRhymeWith() {
        return rhymeWith;
    }

    /**
     * Predicate used to determine if fallback should be used instead of random word
     * @return {@link Predicate}
     */
    public Predicate<BaseWordRenderer<T>> getFallbackPredicate() {
        return fallbackPredicate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("class", getClass().getSimpleName())
                .append("fallback", fallback)
                .append("fallbackPredicate", fallbackPredicate)
                .append("articleMode", articleMode)
                .append("capsMode", capsMode)
                .append("loadKey", loadKey)
                .append("saveKey", saveKey)
                .append("rhymeKey", rhymeKey)
                .append("rhymeWith", rhymeWith)
                .append("syllablesDesired", syllablesDesired)
                .toString();
    }

    /**
     * Set type of article to prepend to the word
     * @param articleMode ArticleMode
     */
    public void setArticle(@Nonnull ArticleMode articleMode) {
        this.articleMode = articleMode;
    }

    /**
     * Capitalization mode
     * @param capsMode CapsMode
     */
    public void setCapsMode(@Nonnull CapsMode capsMode) {
        this.capsMode = capsMode;
    }

    /**
     * Word is loaded from the provided key (must have been saved previously)
     * The form of the saved word will be used when loaded
     * @param loadKey keyword to load from
     */
    public void setLoadKey(String loadKey) {
        this.loadKey = loadKey;
    }

    /**
     * Save word based on this key (to be loaded later)
     * @param saveKey keyword to save to
     */
    public void setSaveKey(String saveKey) {
        this.saveKey = saveKey;
    }

    /**
     * Rhyme this word with one saved at the provided keyword
     * @param rhymeKey keyword where saved word is to be used to rhyme to this one
     * @see #setSaveKey(String)
     */
    public void setRhymeKey(String rhymeKey) {
        this.rhymeKey = rhymeKey;
    }

    /**
     * Word to rhyme with
     * @param rhymeWith String
     */
    public void setRhymeWith(String rhymeWith) {
        this.rhymeWith = rhymeWith;
    }

    /**
     * Set how many syllables this word should have
     * @param syllablesDesired int syllable count
     */
    public void setSyllablesDesired(int syllablesDesired) {
        this.syllablesDesired = syllablesDesired;
    }

    @Nonnull
    public ArticleMode getArticle() {
        return articleMode;
    }

    @Nonnull
    public CapsMode getCapsMode() {
        return capsMode;
    }

    public String getLoadKey() {
        return loadKey;
    }

    public String getSaveKey() {
        return saveKey;
    }

    public String getRhymeKey() {
        return rhymeKey;
    }

    public int getSyllablesDesired() {
        return syllablesDesired;
    }

    /**
     * This is word type specific
     * @return String version of the form name
     */
    public abstract String getFormName();

    /**
     * Use form name to set the form
     * Invalid form name for a given word is ignored
     * Loaded words will use the form of the time they were saved
     * @param formName Name of a form
     */
    public abstract void setForm(String formName);

    /**
     * Article mode
     * @param articleMode {@link ArticleMode}
     */
    public void setArticleMode(ArticleMode articleMode) {
        this.articleMode = articleMode;
    }

    /**
     * Fallback/default text
     * @param fallback String
     */
    public void setFallback(String fallback) {
        this.fallback = fallback;
    }

    /**
     * Probability that the fallback will be used
     * @param fallbackPredicate {@link Predicate}
     */
    public void setFallbackPredicate(Predicate<BaseWordRenderer<T>> fallbackPredicate) {
        this.fallbackPredicate = fallbackPredicate;
    }

    /**
     * Execute tag
     * @return Content for this tag
     */
    public String execute() {
        // Load word if key present
        Word word = null;
        if (loadKey != null) {
            SavedWord savedWord = rendererContext.getAttribute(loadKey);

            if (savedWord == null) {
                word = generateWord();
                LOGGER.debug("There is no saved key={}, using random word={}", loadKey, word);
            }
            else {
                // Saved word, use it
                word = savedWord.getWord();
                if (word.getType() != getType()) {
                    if (word instanceof Text) {
                        LOGGER.debug("SavedWord found, but type mismatched, using Text as-is. key={} word={} word.type={} this.type={}", loadKey, word, word.getType(), getType());
                        setForm(Text.Form.none.name());
                    }
                    else {
                        LOGGER.debug("SavedWord found, but type mismatched and non-Text, ignoring saved key. key={} word={} word.type={} this.type={}", loadKey, word, word.getType(), getType());
                        word = null;
                    }
                }
                else {
                    LOGGER.debug("SavedWord found, key={} word={}", loadKey, word);
                    setForm(savedWord.getFormName());
                }
            }
        }

        // If rhymeKey is selected and we did not load a word
        if (word == null && rhymeKey != null) {
            SavedWord savedWord = rendererContext.getAttribute(rhymeKey);
            if (savedWord == null) {
                LOGGER.debug("There is no saved rhyme key={}, using random word", rhymeKey);
                word = generateWord();  // Use first word since we don't care about syllables
            }
            else {
                word = savedWord.getWord();
                LOGGER.debug("Rhyming to key={} for word={}", rhymeKey, word);
            }

            // Try to rhyme, if not it just gets the word that is being used as a rhyme template
            List<SavedWord> rhymedWords = rendererContext.getWords().findRhymes(word.getWordString());
            if (rhymedWords.size() > 0) {
                word = rhymedWords.get(RandomUtils.nextInt(0, rhymedWords.size())).getWord();
                LOGGER.debug("rhymeKey: Rhyme word selected, word={}", word);
            } else {
                LOGGER.debug("rhymeKey: No rhyming word found for word={}, will use it", word);
            }
        }
        else if (word == null && rhymeWith != null) {
            // Rhyme this word with another
            List<SavedWord> rhymedWords = rendererContext.getWords().findRhymes(rhymeWith);
            if (rhymedWords.size() > 0) {
                SavedWord savedWord = rhymedWords.get(RandomUtils.nextInt(0, rhymedWords.size()));

                // Set the form name for when it was saved
                setForm(savedWord.getFormName());
                word = savedWord.getWord();
                LOGGER.debug("rhymeWith: Rhyme word selected, word={}", word);
            } else {
                LOGGER.debug("rhymeWith: No rhyming word found for word={}, will use it", word);
            }
        }

        // Execute on word picked based on load/rhyme
        // Rest of selection will happen there if word is not picked
        // If execute returns nothing we use fallback
        return execute(word);
    }

    /**
     * Execute renderer with provided Word
     * This method is meant to be called for testing and by main execute method once it selects the word
     *
     * @param word Word
     * @return String after execution
     * @see #execute()
     */
    protected String execute(Word word) {
        // No load or rhyme
        String selectedWord;
        if (fallbackPredicate != null && StringUtils.isNotEmpty(fallback) && fallbackPredicate.test(this)) {
            // Fallback test passed, use it as Text
            LOGGER.debug("Fallback predicate passed, selecting fallback={}", fallback);
            word = null;
            selectedWord = fallback;
        }
        else {
            if (word == null) {
                // Generate word and select form of the word
                Word bestWord = generateWord();
                String bestSelectedWord = selectWord(bestWord);

                // Syllable check
                if (syllablesDesired > 0) {
                    int syllables = GlobalData.getHyphenData().countSyllables(bestSelectedWord);
                    int bestDiff = Math.abs(syllablesDesired - syllables);

                    int tries = TRIES_TO_GET_SYLLABLES;
                    while (bestDiff != 0 && tries-- > 0) {
                        Word possibleWord = generateWord();
                        String possibleSelectedWord = selectWord(possibleWord);
                        int possibleSyllables = GlobalData.getHyphenData().countSyllables(possibleSelectedWord);
                        int possibleDiff = Math.abs(syllablesDesired - possibleSyllables);
                        if (possibleDiff < bestDiff) {
                            // Found a better word
                            bestWord = possibleWord;
                            bestSelectedWord = possibleSelectedWord;
                            bestDiff = possibleDiff;
                        }
                    }
                    LOGGER.debug("Syllables desired={} bestDiff={} bestSelectedWord={}", syllablesDesired, bestDiff, bestSelectedWord);
                }

                word = bestWord;
                selectedWord = bestSelectedWord;
            } else {
                // Pick form of the word
                selectedWord = selectWord(word);
            }
            Preconditions.checkNotNull(word);
        }
        Preconditions.checkNotNull(selectedWord);

        // Do pre processing
        LOGGER.debug("Pre-processing and selecting word={}", word);
        saveWord(word, selectedWord);

        // Process word
        LOGGER.debug("Processing selected word={}", selectedWord);
        String processedWord = process(selectedWord);

        // Do post processing
        LOGGER.debug("Post-processing word={}", processedWord);
        String resultWord = postProcess(processedWord);

        LOGGER.debug("Resulting word={}", resultWord);
        return resultWord;
    }

    /**
     * Save word unless fallback was used
     * If Fallback is used then selectedWord will be treated as {@link Text}
     * @param word Word or null if fallback was used
     * @param selectedWord Selected word
     */
    protected void saveWord(Word word, String selectedWord) {
        if (saveKey != null) {
            if (word != null) {
                String formName = getFormName();
                Preconditions.checkNotNull(formName);
                SavedWord sw = new SavedWord(word, formName);
                LOGGER.debug("Saving key={} value={}", saveKey, sw);
                rendererContext.setAttribute(saveKey, sw);
            }
            else {
                if (selectedWord != null) {
                    SavedWord sw = new SavedWord(Text.of(selectedWord), "none");
                    rendererContext.setAttribute(saveKey, sw);
                    LOGGER.debug("Not saving key={} since fallback was used, saving fallback instead, fallback={}", saveKey, selectedWord);
                }
                else {
                    LOGGER.error("Both Word and selectedWord are null when trying to save, doing nothing");
                }
            }
        }
    }

    /**
     * Generate a random Word
     * @return Word
     */
    protected Word generateWord() {
        return rendererContext.getWords().getRandomWord();
    }

    /**
     * Select a form of the word
     * @param word Word
     * @return Selected word by form
     */
    protected String selectWord(Word word) {
        return word.getWordString();
    }

    protected String process(String word) {
        return word;
    }

    protected String postProcess(String word) {
        // Articles
        switch(articleMode) {
            case a:
                if (WordHelper.isVowelOrH(word.charAt(0)))
                    word = "an " + word;
                else
                    word = "a " + word;
                break;

            case the:
                word = "the " + word;
                break;
        }

        // Capitalize
        switch(capsMode) {
            case first:
                word = WordHelper.capitalizeFirstLetter(word);
                break;

            case words:
                word = WordUtils.capitalizeFully(word);
                break;

            case all:
                word = word.toUpperCase();
                break;
        }

        return word;
    }

    static <TT extends Word> void validateRenderer(BaseWordRenderer<TT> renderer) {
        if (renderer.loadKey != null && renderer.saveKey != null)
            throw new RuntimeException("Renderer cannot have both loadKey and saveKey, renderer="+renderer);
    }
}
