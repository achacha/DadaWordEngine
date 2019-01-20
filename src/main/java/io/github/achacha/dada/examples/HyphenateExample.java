package io.github.achacha.dada.examples;

import io.github.achacha.dada.engine.hyphen.HyphenData;

import java.util.Scanner;

/**
 * Hyphenate a given word/sentence
 */
public class HyphenateExample {
    private static final String PROMPT = "\n\nEnter line to hyphenate (!q to quit)\n>";

    public static void main(String[] args) {
        HyphenData hyphenData = new HyphenData("resource:data/hyphen");

        System.out.print(PROMPT);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine().trim();
            if ("!q".equals(input))
                return;

            System.out.println("INPUT : "+input);
            System.out.println("OUTPUT: "+hyphenData.hyphenateWord(input));

            System.out.print(PROMPT);
        }
    }
}
