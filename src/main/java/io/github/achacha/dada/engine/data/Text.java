package io.github.achacha.dada.engine.data;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Collection;

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

    @Override
    public Collection<Pair<String,String>> getAllForms() {
        return Lists.newArrayList(
                Pair.of(Text.Form.none.name(), this.word)
        );
    }
}
