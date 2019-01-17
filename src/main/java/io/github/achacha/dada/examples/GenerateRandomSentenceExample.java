package io.github.achacha.dada.examples;

import io.github.achacha.dada.engine.builder.SentenceRandomBuilder;
import io.github.achacha.dada.engine.data.Adjective;
import io.github.achacha.dada.engine.data.Noun;
import io.github.achacha.dada.engine.data.Verb;
import io.github.achacha.dada.engine.render.ArticleMode;
import io.github.achacha.dada.engine.render.CapsMode;
import io.github.achacha.dada.integration.tags.TagSingleton;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.function.Supplier;

/**
 * Build a simple sentence and render it 5 times randomly
 */
public class GenerateRandomSentenceExample {
    private static final ArrayList<Supplier<SentenceRandomBuilder>> BUILDERS = new ArrayList<>();

    // Create a few sentence builders we can randomly use
    static {
        BUILDERS.add(()-> new SentenceRandomBuilder()
                .adjective(Adjective.Form.superlative, ArticleMode.the, CapsMode.first)
                .noun()
                .text("for")
                .noun()
                .text("is")
                .adjective(Adjective.Form.comparative)
                .noun()
                .text("for")
                .noun(Noun.Form.plural)
        );

        BUILDERS.add(()-> new SentenceRandomBuilder()
                .text("nothing", ArticleMode.none, CapsMode.first)
                .verb(Verb.Form.infinitive)
                .text("here")
        );

        BUILDERS.add(()-> new SentenceRandomBuilder()
                .verb(Verb.Form.present, ArticleMode.none, CapsMode.first)
                .text("and")
                .verb(Verb.Form.present)
                .text("is not allowed here")
        );

        BUILDERS.add(()-> new SentenceRandomBuilder()
                .verb(Verb.Form.present, ArticleMode.none, CapsMode.first)
                .text("is allowed there")
        );

        BUILDERS.add(()-> new SentenceRandomBuilder()
                .noun(Noun.Form.singular, ArticleMode.a, CapsMode.first)
                .text("cannot")
                .verb(Verb.Form.base)
                .text("any")
                .noun(Noun.Form.plural)
        );
    }

    public static void main(String[] args) {
        TagSingleton.init();

        for (int i=0; i<5; ++i) {
            Supplier<SentenceRandomBuilder> supplier = BUILDERS.get(RandomUtils.nextInt(0, BUILDERS.size()));
            System.out.println(supplier.get().execute());
        }
    }
}
