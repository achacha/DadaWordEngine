package io.github.achacha.dada.engine.render;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Wrapper to an ArrayList of renderers
 */
public class SentenceRenderer extends ArrayList<BaseWordRenderer> {
    /**
     * Execute all tags and join with space separator
     * @return String
     */
    public String execute() {
        return stream().map(BaseWordRenderer::execute).collect(Collectors.joining(" "));
    }
}
