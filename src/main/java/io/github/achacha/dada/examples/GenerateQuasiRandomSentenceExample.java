package io.github.achacha.dada.examples;

import io.github.achacha.dada.engine.base.RendererPredicates;
import io.github.achacha.dada.engine.builder.SentenceRendererBuilder;
import io.github.achacha.dada.engine.render.ArticleMode;
import io.github.achacha.dada.engine.render.CapsMode;

/**
 * Build a simple sentence and render it 5 times randomly
 */
public class GenerateQuasiRandomSentenceExample {
    public static void main(String[] args) {
        SentenceRendererBuilder renderer = new SentenceRendererBuilder()
                .nounBuilder()
                    .withFallback("rose", RendererPredicates.truePercent(30))
                    .withCapsMode(CapsMode.first)
                    .withArticleMode(ArticleMode.a)
                    .build()
                .text(" by any other name is still ")
                .nounBuilder().withFallback("rose").withArticleMode(ArticleMode.a).build();

        for (int i=0; i<8; ++i) {
            System.out.println(renderer.execute());
        }
    }
}
