package io.github.achacha.dada.engine.render;

import com.google.common.base.Preconditions;
import io.github.achacha.dada.engine.base.WordHelper;
import io.github.achacha.dada.engine.data.SavedWord;
import io.github.achacha.dada.engine.data.Word;
import io.github.achacha.dada.integration.tags.TagSingleton;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.text.WordUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.List;

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
     * Syllables desired count
     * When 0 we don't care about syllables
     */
    protected int syllablesDesired;

    /** How many words to try to get syllables desired before using the closest one */
    protected final static int TRIES_TO_GET_SYLLABLES = 10;

    /**
     * Construct renderer with rendering context
     * @param rendererContext RenderingContext
     * @see RenderContextToString
     */
    public BaseWordRenderer(RenderContext<T> rendererContext) {
        this.rendererContext = rendererContext;
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

    @Override
    public String toString() {
        return "BaseWordTag{" +
                "articleMode='" + articleMode + '\'' +
                ", capsMode='" + capsMode + '\'' +
                ", loadKey='" + loadKey + '\'' +
                ", saveKey='" + saveKey + '\'' +
                ", rhymeKey=" + rhymeKey + '\'' +
                '}';
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
     * @param formName Name of a form
     */
    public abstract void setForm(String formName);

    /**
     * Execute tag
     * @return Content for this tag
     */
    public String execute() {
        // Load word if key present
        Word word = null;
        if (loadKey != null) {
            SavedWord savedWord = (SavedWord) rendererContext.getAttribute(loadKey);
            if (savedWord == null) {
                LOGGER.error("There is no saved key={}, using random word", loadKey);
                word = generateWord();
            }
            else {
                word = savedWord.getWord();
            }
        }

        // If rhymeKey is selected and we did not load a word
        if (word == null && rhymeKey != null) {
            SavedWord savedWord = (SavedWord) rendererContext.getAttribute(rhymeKey);
            if (savedWord == null) {
                LOGGER.error("There is no saved key={} to rhyme={}, using random word", loadKey, rhymeKey);
                word = generateWord();  // Use first word since we don't care about syllables
            }
            else {
                word = savedWord.getWord();
                LOGGER.debug("Rhyming to key={} for word={}", rhymeKey, word);
            }

            // Try to rhyme, if not it just gets the word that is being used as a rhyme template
            List<SavedWord> rhymedWords = rendererContext.getWords().findRhymes(word.getWord());
            if (rhymedWords.size() > 0) {
                word = rhymedWords.get(RandomUtils.nextInt(0, rhymedWords.size())).getWord();
                LOGGER.debug("Rhyme word selected, word={}", word);
            } else {
                LOGGER.debug("No rhyming word found for word={}, will use it", word);
            }
        }

        // Execute on word picked based on load/rhyme
        // Rest of selection will happen there if word is not picked
        return execute(word);
    }

    /**
     * Execute tag with provided Word
     * This method is meant to be called for testing and by main execute method once it selects the word
     *
     * @param word Word
     * @return String after execution
     * @see #execute()
     */
    protected String execute(Word word) {
        // No load or rhyme
        String selectedWord;
        if (word == null) {
            // Generate word and select form of the word
            Word bestWord = generateWord();
            String bestSelectedWord = selectWord(bestWord);

            // Syllable check
            if (syllablesDesired > 0) {
                int syllables = TagSingleton.getHypenData().countSyllables(bestSelectedWord);
                int bestDiff = Math.abs(syllablesDesired - syllables);

                int tries = TRIES_TO_GET_SYLLABLES;
                while (bestDiff != 0 && tries-- > 0) {
                    Word possibleWord = generateWord();
                    String possibleSelectedWord = selectWord(possibleWord);
                    int possibleSyllables = TagSingleton.getHypenData().countSyllables(possibleSelectedWord);
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
        }
        else {
            // Pick form of the word
            selectedWord = selectWord(word);
        }
        Preconditions.checkNotNull(word);
        Preconditions.checkNotNull(selectedWord);

        // Do pre processing
        LOGGER.debug("Pre-processing and selecting word={}", word);
        saveWord(word);

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
     * Save word
     * @param word Word
     */
    protected void saveWord(Word word) {
        if (saveKey != null) {
            SavedWord sw = new SavedWord(word, getFormName());
            rendererContext.setAttribute(saveKey, sw);
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
        return word.getWord();
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
}
