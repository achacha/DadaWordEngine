package io.github.achacha.dada.integration.tags;

import io.github.achacha.dada.engine.data.Noun;
import io.github.achacha.dada.engine.render.NounRenderer;

public class NounTag extends BaseWordTag<Noun, NounRenderer> {
    public NounTag() {
        super(new NounRenderer(new RenderContextToJspTag<>(TagSingleton.getWordData().getNouns())));
    }
}
