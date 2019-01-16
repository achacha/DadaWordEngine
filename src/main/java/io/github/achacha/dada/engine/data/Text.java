package io.github.achacha.dada.engine.data;

import java.util.ArrayList;

/**
 * Constant text that is not one of the other words
 */
public class Text extends Word {
    public enum Form {
        none
    }

    public Text(String word) {
        super(word);
    }

    public Text(ArrayList<String> attrs) {
        super(attrs.get(0));

        LOGGER.trace("Adding constant text=`{}`", this.word);
    }

    public static Text of(String text) {
        return new Text(text);
    }

    @Override
    public String toString() {
        return "Text{" +
                "word='" + word + '\'' +
                '}';
    }
}
