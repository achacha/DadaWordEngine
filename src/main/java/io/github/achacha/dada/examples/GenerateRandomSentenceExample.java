package io.github.achacha.dada.examples;

import io.github.achacha.dada.engine.builder.SentenceRandomBuilder;
import io.github.achacha.dada.engine.data.WordData;

/**
 * Build a simple sentence and render it 5 times randomly
 */
public class GenerateRandomSentenceExample {
    public static void main(String[] args) {
        WordData wordData = new WordData("resource:/data/dada2018");

        for (int i=0; i<5; ++i) {
            System.out.println(new SentenceRandomBuilder(wordData)
                    .text("One ")
                    .adjective()
                    .text(" ")
                    .noun()
                    .text(" for man, one ")
                    .adjective()
                    .text(" ")
                    .noun()
                    .text(" for mankind.")
                    .randomize()
            );
        }
    }
}
