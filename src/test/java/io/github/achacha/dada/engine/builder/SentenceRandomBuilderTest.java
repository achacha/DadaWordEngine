package io.github.achacha.dada.engine.builder;

import io.github.achacha.dada.engine.render.CapsMode;
import io.github.achacha.dada.integration.tags.TagSingleton;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SentenceRandomBuilderTest {
    @BeforeAll
    public static void beforeClass() {
        TagSingleton.loadWordData("resource:/data/test_parser");
        TagSingleton.loadHyphenData(TagSingleton.DEFAULT_HYPHENDATA_BASE_RESOURCE_PATH);
    }

    @Test
    void randomize() {
        SentenceRandomBuilder rs = new SentenceRandomBuilder();
        rs
                .noun()
                .conjunction()
                .noun()
                .verb("", CapsMode.none, "infinitive");

        // TODO: Better test needed
        assertTrue(!rs.randomize().isEmpty());

    }
}