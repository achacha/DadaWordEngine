package io.github.achacha.dada.examples;

import io.github.achacha.dada.engine.data.SavedWord;
import io.github.achacha.dada.engine.data.WordData;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Given a noun, find other nouns that have a phonetic rhyme to it
 */
public class FindWordsThatRhymeExample {
    public static void main(String[] args) {
        WordData wordData = new WordData("resource:/data/extended2018");

        System.out.print("\nEnter noun to try and rhyme\n>");
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            System.out.println("INPUT : " + input);

            List<SavedWord> wordsMatched = wordData.getNouns().findRhymes(input);
            if (wordsMatched.size() > 0) {
                    List<String> allMatched = wordsMatched.stream()
                            .map(SavedWord::getForm)   // Get the word form that was used
                            .collect(Collectors.toList());
                    Collections.shuffle(allMatched);

                    System.out.println("Rhymed words\n---\n"+allMatched
                            .stream()
                            .limit(10)
                            .collect(Collectors.joining("\n")));
            } else {
                System.out.println("No rhyming word found for word=`" + input);
            }
        }
    }
}

