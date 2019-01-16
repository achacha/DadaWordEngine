package io.github.achacha.dada.examples;

import io.github.achacha.dada.engine.builder.SentenceRandomBuilder;
import io.github.achacha.dada.engine.data.Adjective;
import io.github.achacha.dada.engine.data.Noun;
import io.github.achacha.dada.engine.render.ArticleMode;
import io.github.achacha.dada.engine.render.CapsMode;
import io.github.achacha.dada.integration.tags.TagSingleton;

/**
 * Build a simple sentence and render it 5 times randomly
 */
public class GenerateRandomSentenceExample {
    public static void main(String[] args) {
        TagSingleton.init();

        for (int i=0; i<5; ++i) {
            System.out.println(new SentenceRandomBuilder()
                    .text("One ")
                    .adjective(Adjective.Form.comparative, ArticleMode.the, CapsMode.none)
                    .text(" ")
                    .noun()
                    .text(" for ")
                    .noun()
                    .text(", one ")
                    .adjective()
                    .text(" ")
                    .noun()
                    .text(" for ")
                    .noun(Noun.Form.plural)
                    .randomize());
        }
    }
}
