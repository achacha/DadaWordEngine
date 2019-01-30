package io.github.achacha.dada.examples;

import io.github.achacha.dada.engine.builder.Sentence;
import io.github.achacha.dada.engine.builder.SentenceRendererBuilder;
import io.github.achacha.dada.integration.tags.GlobalData;
import org.apache.commons.lang3.StringUtils;

import java.util.Scanner;

/**
 * Given a word/sentence, try to rhyme with it
 */
public class RhymeWithSentenceExample {
    private static final String PROMPT = "\n\nEnter sentence to try and rhyme with (!q to quit)\n>";

    public static void main(String[] args) {
        GlobalData.loadWordData("resource:/data/extended2018");
        GlobalData.loadHyphenData(GlobalData.DEFAULT_HYPHENDATA_BASE_RESOURCE_PATH);

        System.out.print(PROMPT);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine().trim();
            if ("!q".equals(input))
                return;

            System.out.println("INPUT : " + input);

            // Get last word to rhyme with
            String rhymeWith = StringUtils.substringAfterLast(input, " ");
            if (rhymeWith.isEmpty())
                rhymeWith = input;

            // Parse input into a sentence and find known words, then convert to a randomized version of it
            Sentence sentence = new Sentence(GlobalData.getWordData()).parse(input);
            System.out.println(sentence.toStringStructure());
            SentenceRendererBuilder randomizedSentence = new SentenceRendererBuilder(sentence);
            System.out.println(randomizedSentence.toStringStructure());

            // Get last word and rhyme and set the rhyme with itself
            randomizedSentence.getRenderers().get(randomizedSentence.getRenderers().size() - 1).setRhymeWith(rhymeWith);
            System.out.println("\nRhymes with:\n");

            for (int i=0; i<4; ++i)
                System.out.println(randomizedSentence.execute());

            System.out.print(PROMPT);
        }
    }
}

