package io.github.achacha.dada.engine.builder;

import io.github.achacha.dada.engine.data.Text;
import io.github.achacha.dada.engine.data.Word;
import io.github.achacha.dada.engine.data.WordData;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Allows building a Sentence using random words and parsing
 */
public class SentenceRandomBuilder extends Sentence {
    /**
     * @param baseResourceDir Base resource directory to load WordData
     * @see Sentence
     */
    public SentenceRandomBuilder(String baseResourceDir) {
        super(baseResourceDir);
    }

    /**
     * @param wordData WordData to use
     * @see Sentence
     */
    public SentenceRandomBuilder(WordData wordData) {
        super(wordData);
    }

    /**
     * @param wordData WordData to use
     * @param unknownAddedAsText if unknown words should be added as {@link Text} or discarded
     * NOTE: default is add as Text
     *
     * @see Sentence
     */
    public SentenceRandomBuilder(WordData wordData, boolean unknownAddedAsText) {
        super(wordData, unknownAddedAsText);
    }

    /**
     * Add text
     * @param str Fixed text
     * @return SentenceRandomBuilder this
     */
    public SentenceRandomBuilder text(String str) {
        words.add(new Text(str));
        return this;
    }

    /**
     * Add random adjective
     * @return SentenceRandomBuilder this
     */
    public SentenceRandomBuilder adjective() {
        words.add(wordData.getAdjectives().getRandomWord());
        return this;
    }

    /**
     * Add random adverb
     * @return SentenceRandomBuilder this
     */
    public SentenceRandomBuilder adverb() {
        words.add(wordData.getAdverbs().getRandomWord());
        return this;
    }

    /**
     * Add random conjunction
     * @return SentenceRandomBuilder this
     */
    public SentenceRandomBuilder conjunction() {
        words.add(wordData.getConjunctions().getRandomWord());
        return this;
    }

    /**
     * Add random noun
     * @return SentenceRandomBuilder this
     */
    public SentenceRandomBuilder noun() {
        words.add(wordData.getNouns().getRandomWord());
        return this;
    }

    /**
     * Add random pronoun
     * @return SentenceRandomBuilder this
     */
    public SentenceRandomBuilder pronoun() {
        words.add(wordData.getPronouns().getRandomWord());
        return this;
    }

    /**
     * Add random verb
     * @return SentenceRandomBuilder this
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
