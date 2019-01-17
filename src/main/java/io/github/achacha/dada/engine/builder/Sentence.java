package io.github.achacha.dada.engine.builder;

import io.github.achacha.dada.engine.data.Text;
import io.github.achacha.dada.engine.data.Word;
import io.github.achacha.dada.engine.data.WordData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Parse text sentence into a collection of known Word objects
 */
public class Sentence {

    protected static final Logger LOGGER = LogManager.getLogger(Sentence.class);

    protected List<Word> words = new ArrayList<>();

    protected final WordData wordData;

    /** Unknown words added as text */
    private boolean addUnknownAsText = true;

    /**
     * Sentence builder loading words by path
     * @param baseResourceDir to words
     *
     * @see WordData#WordData(String)
     */
    public Sentence(String baseResourceDir) {
        this.wordData = new WordData(baseResourceDir);
    }

    /**
     * Set up sentence parser and add unknown words as {@link Text} by default
     * @param wordData WordData
     */
    public Sentence(WordData wordData) {
        this.wordData = wordData;
    }

    /**
     * Set up sentence parser and add unknown words are Text objects
     * NOTE: default is add as Text
     *
     * @param wordData WordData
     * @param unknownAddedAsText if unknown words should be added as {@link Text}
     */
    public Sentence(WordData wordData, boolean unknownAddedAsText) {
        this.wordData = wordData;
        this.addUnknownAsText = unknownAddedAsText;
    }

    /**
     * @return List of {@link Word} parsed
     */
    public List<Word> getWords() {
        return words;
    }

    /**
     * Set subsequent parsing mode to ignore unknown words during parse
     * @return Sentence this
     * @see #parse(String)
     */
    public Sentence ignoreUnknown() {
        this.addUnknownAsText = false;
        return this;
    }

    /**
     * Set subsequent parsing mode to add unknown words during parse
     * @return Sentence this
     * @see #parse(String)
     */
    public Sentence addUnknownAsText() {
        this.addUnknownAsText = true;
        return this;
    }

    /**
     * Parse sentence adding to existing words
     * All known words will become lower case to match them in WordData
     * Text blocks are unchanged
     * @param text String
     * @return Sentence this
     */
    public Sentence parse(String text) {
        BreakIterator bi = BreakIterator.getWordInstance();
        bi.setText(text);
        int lastIndex = bi.first();
        Word lastAddedWord = null;
        while (BreakIterator.DONE != lastIndex) {
            int firstIndex = lastIndex;
            lastIndex = bi.next();
            if (lastIndex != BreakIterator.DONE) {
                String subtext = text.substring(firstIndex, lastIndex);
                if (!wordData.getIgnore().contains(subtext)) {
                    Optional<? extends Word> ow = wordData.findFirstWordsByText(subtext.toLowerCase());
                    if (ow.isPresent()) {
                        LOGGER.trace("Found word for `{}`, word={}", subtext, ow.get());
                        lastAddedWord = ow.get();
                        words.add(lastAddedWord);
                    } else {
                        LOGGER.debug("Failed to find a Word for `{}`, adding as Text", subtext);
                        if (addUnknownAsText) {
                            if (lastAddedWord instanceof Text) {
                                // Replace last Text added with new text to avoid creating sequential Text blocks
                                lastAddedWord = new Text(lastAddedWord.getWord()+subtext);
                                words.set(words.size()-1, lastAddedWord);
                            }
                            else {
                                // Add Text
                                lastAddedWord = new Text(subtext);
                                words.add(lastAddedWord);
                            }
                        }
                    }
                }
                else {
                    LOGGER.debug("Skipping ignored word: `{}`", subtext);
                }
            }
        }
        return this;
    }

    /**
     * Build new sentence with using random words based on types that were parsed/added
     * Unlike {@link #toString()} which will preserve the words parsed
     * NOTE: This does not change the internal structure, only randomized during rendering
     * @return Sentence string randomized by their types
     */
    public String execute() {
        List<Word> sentence = new ArrayList<>(words.size());
        for (Word word : words) {
            if (word.getType() == Word.Type.Unknown)
                sentence.add(word);
            else
                sentence.add(wordData.getRandomWordByType(word.getType()));
        }
        return sentence.stream().map(Word::getWord).collect(Collectors.joining());
    }

    /**
     * Display structure
     * @return String
     */
    @Override
    public String toString() {
        return words.stream().map(Word::getWord).collect(Collectors.joining());
    }

    /**
     * Display structure
     * @return String
     */
    public String toStringStructure() {
        return words.stream().map(Word::toString).collect(Collectors.joining("\n  ", "  ", ""));
    }

    /**
     * @return true if parser is adding unknown words as {@link Text}
     */
    public boolean isAddUnknownAsText() {
        return addUnknownAsText;
    }
}
