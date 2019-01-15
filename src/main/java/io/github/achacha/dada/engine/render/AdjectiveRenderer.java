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
     * @param capsMode CapsMode
     * @param form "", "er", "est"
     */
    public AdjectiveRenderer(String article, CapsMode capsMode, String form) {
        this();
        this.article = article;
        this.capsMode = capsMode;
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
