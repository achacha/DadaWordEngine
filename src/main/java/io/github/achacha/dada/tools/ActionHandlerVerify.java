package io.github.achacha.dada.tools;

import io.github.achacha.dada.engine.data.WordData;
import io.github.achacha.dada.engine.data.WordsByType;
import org.apache.commons.cli.CommandLine;

import java.io.PrintStream;
import java.util.Optional;

public class ActionHandlerVerify implements BaseActionHandler {
    @Override
    public void handle(CommandLine cmd, PrintStream out) {
        String dataset = cmd.getOptionValue("dataset");
        if (dataset == null) {
            out.println("-dataset is required");
            return;
        }

        System.out.println("Processing: "+"resource:/data/"+dataset);
        WordData wordData = new WordData("resource:/data/"+dataset);
        Optional<Boolean> hasErrors = wordData.getWordsByTypeStream()
                .map(WordsByType::isDuplicateFound)
                .filter(b->b).findFirst();

        if (hasErrors.isPresent()) {
            System.out.println("\nData set contains errors");
        }
        else {
            System.out.println("\nNo errors detected");
        }
    }
}
