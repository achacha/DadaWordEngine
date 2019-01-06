package org.achacha.dada.examples;

import org.achacha.dada.engine.phonemix.PhoneticTransformer;
import org.achacha.dada.engine.phonemix.PhoneticTransformerBuilder;

import java.util.Scanner;

public class PhonemixExample {
    public static void main(String[] args) {
        PhoneticTransformer transformer = PhoneticTransformerBuilder.builder().build();

        System.out.print("\nEnter line to convert to phonemix form\n>");
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            System.out.println("INPUT : "+input);
            System.out.println("OUTPUT: "+transformer.transform(input));

        }
    }
}
