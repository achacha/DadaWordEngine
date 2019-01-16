package io.github.achacha.dada.integration.tags;

import io.github.achacha.dada.engine.data.Adverb;
import io.github.achacha.dada.engine.render.AdverbRenderer;

public class AdverbTag extends BaseWordTag<Adverb, AdverbRenderer> {
    public AdverbTag() {
        super(new AdverbRenderer(new RenderContextToJspTag<>(TagSingleton.getWordData().getAdverbs())));
    }
}
