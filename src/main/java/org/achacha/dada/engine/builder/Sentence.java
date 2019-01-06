package org.achacha.dada.engine.builder;

import org.achacha.dada.engine.data.Text;
import org.achacha.dada.engine.data.Word;
import org.achacha.dada.engine.data.WordData;
import org.achacha.dada.engine.data.WordsByType;
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

    protected static final Logger LOGGER = LogManager.getLogger(WordsByType.class);

    protected List<Word> words = new ArrayList<>();

    protected final WordData wordData;

    /** Unknown words added as text */
    private boolean addText = true;

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
        this.addText = unknownAddedAsText;
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
        this.addText = false;
        return this;
    }

    /**
     * Set subsequent parsing mode to add unknown words during parse
     * @return Sentence this
     * @see #parse(String)
     */
    public Sentence addUnknown() {
        this.addText = true;
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
                        if (addText) {
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
    public boolean isAddText() {
        return addText;
    }
}
