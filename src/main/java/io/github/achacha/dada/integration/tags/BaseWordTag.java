package io.github.achacha.dada.integration.tags;

import com.google.common.base.Preconditions;
import io.github.achacha.dada.engine.base.WordHelper;
import io.github.achacha.dada.engine.data.SavedWord;
import io.github.achacha.dada.engine.data.Word;
import io.github.achacha.dada.engine.data.WordsByType;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.text.WordUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.JspTag;
import javax.servlet.jsp.tagext.SimpleTag;
import java.io.IOException;
import java.util.List;

public abstract class BaseWordTag<T extends Word> implements SimpleTag {
    protected static final Logger LOGGER = LogManager.getLogger(BaseWordTag.class);

    /** Context of the JSP page */
    protected JspContext jspContext;

    /** Parent of the JSP tag */
    protected JspTag parentJspTag;

    /** Words of this type */
    final protected WordsByType<T> words;

    /** Article to use a (will adjust to an if needed) or the */
    protected String article;

    /**
     * Capitalization mode, applied as the last thing
     * first - only first letter
     * words - every word capitalized
     * all - all letters are capitalized
     */
    protected String capMode;

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

    /** Form of the word to use */
    protected String form = "";

    /**
     * Syllables desired count
     * When 0 we don't care about syllables
     */
    protected int syllablesDesired;

    /** How many words to try to get syllables desired before using the closest one */
    protected final static int TRIES_TO_GET_SYLLABLES = 10;

    BaseWordTag(WordsByType<T> words) {
        this.words = words;
    }

    @Override
    public String toString() {
        return "BaseWordTag{" +
                "article='" + article + '\'' +
                ", capMode='" + capMode + '\'' +
                ", loadKey='" + loadKey + '\'' +
                ", saveKey='" + saveKey + '\'' +
                ", rhymeKey=" + rhymeKey + '\'' +
                ", form='" + form + '\'' +
                '}';
    }

    @Override
    public void doTag() throws JspException, IOException {
        String word = execute();
        LOGGER.debug("doTag: word={}", word);
        jspContext.getOut().write(word);
    }

    @Override
    public void setParent(JspTag parent) {
        LOGGER.debug("setParent={}", parent);
        parentJspTag = parent;
    }

    @Override
    public JspTag getParent() {
        LOGGER.debug("getParent");
        return parentJspTag;
    }

    @Override
    public void setJspContext(JspContext context) {
        LOGGER.debug("setJspContext={}", context);
        jspContext = context;
    }

    @Override
    public void setJspBody(JspFragment jspBody) {
        LOGGER.debug("setJspBody={}", jspBody);
        // At this time the tags are defined in TLD as body-content of empty, so this would never get called
    }

    /**
     * If word is preceded by article 'a'
     * Called by Jasper
     * @param value true/false
     */
    public void setArticle(String value) {
        value = value.toLowerCase();
        if (!value.equals("a") && !value.equals("the"))
            LOGGER.warn("article can only be 'a' or 'the' in tag={}", this);
        article = value;
    }

    /**
     * Form of the word (specific to the Word type being used
     * @param value String name of the form of the word
     *
     */
    public void setForm(String value) {
        form = value.toLowerCase();
    }

    /**
     *
     * Called by Jasper
     * @param value String "first", "words", "all"
     */
    public void setCapMode(String value) {
        value = value.toLowerCase();
        if (!value.equals("first") && !value.equals("words") && !value.equals("all"))
            LOGGER.warn("capMode can only be 'first', 'words' or 'all' in tag={}");
        this.capMode = value;
    }

    /**
     * Called by Jasper
     * @param value String
     */
    @SuppressWarnings("unused")
    public void setLoad(String value) {
        loadKey = value;
    }

    /**
     * Called by Jasper
     * @param value String
     */
    @SuppressWarnings("unused")
    public void setSave(String value) {
        saveKey = value;
    }

    /**
     * Called by Jasper
     * @param value String
     */
    @SuppressWarnings("unused")
    public void setRhyme(String value) {
        rhymeKey = value;
    }


    /**
     * Called by Jasper
     * @param value String converted to int
     */
    @SuppressWarnings("unused")
    public void setSyllables(String value) {
        this.syllablesDesired = Integer.valueOf(value);
    }

    /**
     * Execute tag
     * @return Content for this tag
     */
    public String execute() {
        // Load word if key present
        Word word = null;
        if (loadKey != null) {
            SavedWord savedWord = (SavedWord)jspContext.getAttribute(loadKey);
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
            SavedWord savedWord = (SavedWord)jspContext.getAttribute(rhymeKey);
            if (savedWord == null) {
                LOGGER.error("There is no saved key={} to rhyme={}, using random word", loadKey, rhymeKey);
                word = generateWord();  // Use first word since we don't care about syllables
            }
            else {
                word = savedWord.getWord();
                LOGGER.debug("Rhyming to key={} for word={}", rhymeKey, word);
            }

            // Try to rhyme, if not it just gets the word that is being used as a rhyme template
            List<SavedWord> rhymedWords = words.findRhymes(word.getWord());
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
            SavedWord sw = new SavedWord(word, form);
            jspContext.setAttribute(saveKey, sw);
        }
    }

    /**
     * Generate a random Word
     * @return Word
     */
    protected Word generateWord() {
        return words.getRandomWord();
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
        if (article != null) {
            switch(article) {
                case "a":
                    if (WordHelper.isVowelOrH(word.charAt(0)))
                        word = "an " + word;
                    else
                        word = "a " + word;
                    break;

                case "the":
                    word = "the " + word;
                    break;

                default:
                    LOGGER.warn("Invalid article={} for this={}, ignored", article, this);
            }
        }

        // Capitalize
        if (capMode != null) {
            switch(capMode) {
                case "first":
                    word = WordHelper.capitalizeFirstLetter(word);
                    break;

                case "words":
                    word = WordUtils.capitalizeFully(word);
                    break;

                case "all":
                    word = word.toUpperCase();
                    break;

                default:
                    LOGGER.warn("Invalid capMode={} for this={}, ignored", capMode, this);
            }
        }

        return word;
    }
}
