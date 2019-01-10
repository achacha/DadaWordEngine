package io.github.achacha.dada.integration.tags;

import io.github.achacha.dada.engine.data.Text;
import io.github.achacha.dada.engine.data.WordsByType;
import io.github.achacha.dada.engine.render.TextRenderer;

/**
 * Tag that represents constant text string
 */
public class TextTag extends BaseWordTag<Text> {
    /**
     * @param text constant string
     */
    public TextTag(String text) {
        super(new TextRenderer(text, new RenderContextToJspTag<>(WordsByType.empty())));
    }
}
