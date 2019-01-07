package org.achacha.dada.integration.tags;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class TagSentence extends ArrayList<BaseWordTag> {
    /**
     * Execute all tags and join with space separator
     * @return String
     */
    public String execute() {
        return stream().map(BaseWordTag::execute).collect(Collectors.joining(" "));
    }
}
