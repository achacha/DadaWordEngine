package io.github.achacha.dada.integration.tags;

import io.github.achacha.dada.engine.data.Conjunction;
import io.github.achacha.dada.engine.render.ConjunctionRenderer;

public class ConjunctionTag extends BaseWordTag<Conjunction, ConjunctionRenderer> {
    public ConjunctionTag() {
        super(new ConjunctionRenderer(new RenderContextToJspTag<>(TagSingleton.getWordData().getConjunctions())));
    }
}
