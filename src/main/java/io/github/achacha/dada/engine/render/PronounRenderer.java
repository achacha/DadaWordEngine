package io.github.achacha.dada.engine.render;

import io.github.achacha.dada.engine.data.Pronoun;
import io.github.achacha.dada.engine.data.Pronouns;
import io.github.achacha.dada.engine.data.Word;
import io.github.achacha.dada.integration.tags.TagSingleton;

public class PronounRenderer extends BaseWordRenderer<Pronoun> {
    public PronounRenderer() {
        super(new RenderContextToString<>(TagSingleton.getWordData().getPronouns()));
    }

    public PronounRenderer(RenderContext<Pronoun> renderData) {
        super(renderData);
    }

    /** Used in testing */
    PronounRenderer(Pronouns pronouns) {
        super(new RenderContextToString<>(pronouns));
    }

    /**
     * Extended constructor
     * @param articleMode ArticleMode
     * @param capsMode CapsMode
     * @param form "personal", "subjective", "objective", "possessive", "demonstrative", "interrogative", "relative", "reflexive", "reciprocal", "indefinite"
     * @see Pronoun.Form
     */
    public PronounRenderer(ArticleMode articleMode, CapsMode capsMode, String form) {
        this();
        this.articleMode = articleMode;
        this.capsMode = capsMode;
        this.form = form;
    }

    @Override
    protected Word generateWord() {
        if (!form.isEmpty()) {
            Pronouns pronouns = (Pronouns) rendererContext.getWords();
            try {
                Pronoun.Form pronounForm = Pronoun.Form.valueOf(form.toLowerCase());
                Word word = pronouns.getRandomPronounByForm(pronounForm);
                LOGGER.debug("Generated pronoun={} form={}", word, pronounForm);
                return word;
            }
            catch(IllegalArgumentException e) {
                LOGGER.debug("Skipping unknown form `{}` in {}", form, this);
            }
        }
        return super.generateWord();
    }
}
