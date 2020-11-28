package io.github.achacha.dada.examples;

import io.github.achacha.dada.engine.base.RendererPredicates;
import io.github.achacha.dada.engine.builder.SentenceRendererBuilder;
import io.github.achacha.dada.engine.data.Verb;

/**
 * Build a simple sentence and render it 5 times randomly
 */
public class NotQuiteTheRavenExample {
    public static void main(String[] args) {
        SentenceRendererBuilder renderer = new SentenceRendererBuilder()
                .text("Once upon a midnight dreary, while I pondered, weak and weary,\n")
                .text("Over many a quaint and curious volume of forgotten lore—\n")
                .text("    While I nodded, nearly")
                .verbBuilder()
                    .withFallback("napping", RendererPredicates.trueIfPercent(50))
                    .withRhymeWith("nap")
                    .withForm(Verb.Form.present)
                    .build()
                .text(", suddenly there came a")
                .verbBuilder()
                    .withRhymeWith("tap")
                    .withSaveKey("tapping")
                    .withForm(Verb.Form.present)
                    .build()
                .text(",\n")
                .text("As of some one gently")
                .verbBuilder()
                    .withRhymeWith("rap")
                    .withSaveKey("rapping")
                    .withForm(Verb.Form.present)
                    .build()
                .text(", ")
                .verbBuilder()
                    .withLoadKey("rapping")
                    .withForm(Verb.Form.present)
                    .build()
                .text("at my chamber door.\n")
                .text("“’Tis some visitor,” I muttered, “")
                .verbBuilder()
                    .withLoadKey("tapping")
                    .build()
                .text("at my chamber door—\n")
                .text("            Only this and nothing more.");

        System.out.println(renderer.execute());
        System.out.println("---");
        System.out.println(renderer.toStringStructure());
    }
}
