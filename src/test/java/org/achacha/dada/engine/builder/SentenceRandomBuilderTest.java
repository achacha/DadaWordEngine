package org.achacha.dada.engine.builder;

import org.achacha.dada.engine.data.WordData;
import org.junit.jupiter.api.Test;

class SentenceRandomBuilderTest {
    private WordData wordData = new WordData("resource:/data/test_parser");

    @Test
    void randomize() {
        SentenceRandomBuilder rs = new SentenceRandomBuilder(wordData, true);
    }
}