package io.github.achacha.dada.engine.builder;

import io.github.achacha.dada.engine.data.Noun;
import io.github.achacha.dada.engine.data.Verb;
import io.github.achacha.dada.engine.render.ArticleMode;
import io.github.achacha.dada.engine.render.CapsMode;
import io.github.achacha.dada.integration.tags.TagSingleton;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SentenceRandomBuilderTest {
    @BeforeAll
    public static void beforeClass() {
        TagSingleton.loadWordData("resource:/data/test_parser");
        TagSingleton.loadHyphenData(TagSingleton.DEFAULT_HYPHENDATA_BASE_RESOURCE_PATH);
    }

    @Test
    void simpleRandomSentence() {
        SentenceRandomBuilder rs = new SentenceRandomBuilder()
                .noun()
                .conjunction()
                .noun()
                .verb(Verb.Form.infinitive);

        assertEquals(4, rs.getWords().size());
        assertTrue(!rs.randomize().isEmpty());

    }

    @Test
    void extendedRandomSentence() {
        SentenceRandomBuilder rs = new SentenceRandomBuilder()
                .text("shoe", ArticleMode.the, CapsMode.first)
                .verb(Verb.Form.singular)
                .noun(Noun.Form.plural, ArticleMode.a, CapsMode.none);

        assertEquals(3, rs.getWords().size());
        assertTrue(rs.randomize().startsWith("The shoe"));

    }
}