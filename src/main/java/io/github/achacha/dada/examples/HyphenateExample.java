package io.github.achacha.dada.examples;

import io.github.achacha.dada.engine.hyphen.HyphenData;

import java.util.Scanner;

public class HyphenateExample {
    public static void main(String[] args) {
        HyphenData hyphenData = new HyphenData("resource:data/hyphen");

        System.out.print("\nEnter line to hyphenate\n>");
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            System.out.println("INPUT : "+input);
            System.out.println("OUTPUT: "+hyphenData.hyphenateWord(input));
        }
    }
}
