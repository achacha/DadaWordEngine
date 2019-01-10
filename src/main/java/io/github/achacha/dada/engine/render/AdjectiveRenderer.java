package io.github.achacha.dada.engine.render;

import io.github.achacha.dada.engine.data.Adjective;
import io.github.achacha.dada.engine.data.Word;
import io.github.achacha.dada.integration.tags.TagSingleton;

public class AdjectiveRenderer extends BaseWordRenderer<Adjective> {
    public AdjectiveRenderer() {
        super(new RenderContextToString<>(TagSingleton.getWordData().getAdjectives()));
    }

    public AdjectiveRenderer(RenderContext<Adjective> renderData) {
        super(renderData);
    }

    /**
     * Extended constructor
     * @param article article prefix
     * @param capMode capitalization mode: first, words, all
     * @param form specific to the word
     */
    AdjectiveRenderer(String article, String capMode, String form) {
        this();
        this.article = article;
        this.capMode = capMode;
        this.form = form;
    }

    @Override
    protected String selectWord(Word word) {
        Adjective adjective = (Adjective)word;
        if (!form.isEmpty()) {
            switch (form) {
                // Comparative
                case "er":
                    return adjective.getComparative();

                // Superlative
                case "est":
                    return adjective.getSuperlative();

                default:
                    LOGGER.debug("Skipping unknown form `{}` in {}", form, this);
                    return super.selectWord(adjective);
            }
        }
        return word.getWord();
    }
}
