package io.github.achacha.dada.engine.builder;

import io.github.achacha.dada.engine.data.Adjective;
import io.github.achacha.dada.engine.data.Noun;
import io.github.achacha.dada.engine.data.Pronoun;
import io.github.achacha.dada.engine.data.Verb;
import io.github.achacha.dada.engine.render.ArticleMode;
import io.github.achacha.dada.engine.render.CapsMode;
import io.github.achacha.dada.integration.tags.TagSingleton;
import org.apache.commons.lang3.StringUtils;
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
                .pronoun()
                .adjective()
                .noun()
                .preposition()
                .adverb()
                .verb();

        assertEquals(8, rs.getWords().size());
        assertTrue(!rs.execute().isEmpty());

    }

    @Test
    void extendedRandomSentence() {
        SentenceRandomBuilder rs = new SentenceRandomBuilder()
                .text("shoe", ArticleMode.the, CapsMode.first)
                .pronoun(Pronoun.Form.personal, ArticleMode.none, CapsMode.first)
                .adverb(ArticleMode.none, CapsMode.none)
                .verb(Verb.Form.singular, ArticleMode.none, CapsMode.none)
                .adjective(Adjective.Form.positive, ArticleMode.a, CapsMode.none)
                .noun(Noun.Form.plural, ArticleMode.a, CapsMode.none)
                .conjunction(ArticleMode.a, CapsMode.all)
                .pronoun(Pronoun.Form.possessive)
                .adjective(Adjective.Form.comparative)
                .noun(Noun.Form.plural)
                .preposition(ArticleMode.none, CapsMode.none)
                .adjective(Adjective.Form.superlative)
                .verb(Verb.Form.infinitive)
                .text(", and so it ends.");

        assertEquals(14, rs.getWords().size());
        assertTrue(StringUtils.isNotEmpty(rs.execute()));
        assertTrue(StringUtils.isNotEmpty(rs.toStringStructure()));
    }
}