package io.github.achacha.dada.integration.tags;

import io.github.achacha.dada.engine.data.Preposition;
import io.github.achacha.dada.engine.render.PrepositionRenderer;

public class PrepositionTag extends BaseWordTag<Preposition, PrepositionRenderer> {
    public PrepositionTag() {
        super(new PrepositionRenderer(new RenderContextToJspTag<>(GlobalData.getWordData().getPrepositions())));
    }
}
