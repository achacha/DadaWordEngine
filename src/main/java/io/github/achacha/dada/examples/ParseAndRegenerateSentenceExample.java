package io.github.achacha.dada.examples;

import io.github.achacha.dada.engine.builder.SentenceRandomBuilder;
import io.github.achacha.dada.engine.data.WordData;

import java.util.Scanner;

/**
 * Read line from input, parse, display structure and randomize known words
 */
public class ParseAndRegenerateSentenceExample {
    public static void main(String[] args) {
        WordData wordData = new WordData("resource:/data/extended2018");

        System.out.println("\nEnter sentence to parse and randomize\n>");
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextLine()) {
            SentenceRandomBuilder sentence = new SentenceRandomBuilder(wordData, true);
            sentence.parse(scanner.nextLine());

            System.out.println("Parsed: "+sentence);
            System.out.println("Structure\n---------\n"+sentence.toStringStructure());
            System.out.println("\nRandomized\n---------");
            for (int i=0; i<5; ++i)
                System.out.println(sentence.randomize());
        }
    }
}
