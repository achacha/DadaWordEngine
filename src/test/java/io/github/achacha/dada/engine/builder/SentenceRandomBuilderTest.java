package io.github.achacha.dada.engine.builder;

import io.github.achacha.dada.engine.data.WordData;
import org.junit.jupiter.api.Test;

class SentenceRandomBuilderTest {
    private WordData wordData = new WordData("resource:/data/test_parser");

    @Test
    void randomize() {
        SentenceRandomBuilder rs = new SentenceRandomBuilder(wordData, true);
    }
}