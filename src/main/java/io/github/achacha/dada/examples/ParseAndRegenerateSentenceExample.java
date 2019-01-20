package io.github.achacha.dada.examples;

import io.github.achacha.dada.engine.builder.Sentence;
import io.github.achacha.dada.engine.builder.SentenceRendererBuilder;
import io.github.achacha.dada.integration.tags.GlobalData;

import java.util.Scanner;

/**
 * Read line from input, parse, display structure and execute known words
 */
public class ParseAndRegenerateSentenceExample {
    private static final String PROMPT = "\n\nEnter sentence to parse and randomize (!q to quit)\n>";

    public static void main(String[] args) {
        GlobalData.loadWordData("resource:/data/extended2018");

        System.out.print(PROMPT);
        System.out.flush();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine().trim();
            if ("!q".equals(input))
                return;

            // Parse input into Sentence converting known words into their respective objects
            // Any word unknown is lumped into Text constants
            Sentence sentence = new Sentence(GlobalData.getWordData(), true);
            sentence.parse(input);
            SentenceRendererBuilder randomSentence = new SentenceRendererBuilder(sentence);

            System.out.println("Parsed: "+sentence);
            System.out.println("Senetence structure\n---------\n"+sentence.toStringStructure());
            System.out.println("SenetenceRandomBuilder structure\n---------\n"+randomSentence.toStringStructure());
            System.out.println("\nRandomized\n---------");
            for (int i=0; i<5; ++i) {
                System.out.println("Random sentence: "+sentence.execute());
                System.out.println("SentenceRendererBuilder: " + randomSentence.execute());
                System.out.println("\n");
            }


            System.out.print(PROMPT);
            System.out.flush();
        }
    }
}
