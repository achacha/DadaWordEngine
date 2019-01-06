package org.achacha.dada.engine.builder;

import org.achacha.dada.engine.data.Text;
import org.achacha.dada.engine.data.Word;
import org.achacha.dada.engine.data.WordData;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Allows building a Sentence using random words and parsing
 */
public class SentenceRandomBuilder extends Sentence {
    public SentenceRandomBuilder(String baseResourceDir) {
        super(baseResourceDir);
    }

    public SentenceRandomBuilder(WordData wordData) {
        super(wordData);
    }

    public SentenceRandomBuilder(WordData wordData, boolean unknownAddedAsText) {
        super(wordData, unknownAddedAsText);
    }

    /**
     * Add text
     * @param str Fixed text
     */
    public SentenceRandomBuilder text(String str) {
        words.add(new Text(str));
        return this;
    }

    /**
     * Add random adjective
     */
    public SentenceRandomBuilder adjective() {
        words.add(wordData.getAdjectives().getRandomWord());
        return this;
    }

    /**
     * Add random adverb
     */
    public SentenceRandomBuilder adverb() {
        words.add(wordData.getAdverbs().getRandomWord());
        return this;
    }

    /**
     * Add random conjunction
     */
    public SentenceRandomBuilder conjunction() {
        words.add(wordData.getConjunctions().getRandomWord());
        return this;
    }

    /**
     * Add random noun
     */
    public SentenceRandomBuilder noun() {
        words.add(wordData.getNouns().getRandomWord());
        return this;
    }

    /**
     * Add random pronoun
     */
    public SentenceRandomBuilder pronoun() {
        words.add(wordData.getPronouns().getRandomWord());
        return this;
    }

    /**
     * Add random verb
     */
    public SentenceRandomBuilder verb() {
        words.add(wordData.getVerbs().getRandomWord());
        return this;
    }

    /**
     * Build new sentence with using random words based on types
     * Unlike {@link #toString()} which will preserve the words parsed
     * NOTE: This does not change the internal structure, only randomized during rendering
     * @return Sentence string randomized by their types
     */
    public String randomize() {
        List<Word> sentence = new ArrayList<>(words.size());
        for (Word word : words) {
            if (word.getType() == Word.Type.Unknown)
                sentence.add(word);
            else
                sentence.add(wordData.getRandomWordByType(word.getType()));
        }
        return sentence.stream().map(Word::getWord).collect(Collectors.joining());
    }

}
