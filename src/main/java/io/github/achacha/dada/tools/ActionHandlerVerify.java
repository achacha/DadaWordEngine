package io.github.achacha.dada.tools;

import io.github.achacha.dada.engine.data.WordData;
import io.github.achacha.dada.engine.data.WordsByType;
import org.apache.commons.cli.CommandLine;

import java.io.PrintStream;

public class ActionHandlerVerify implements BaseActionHandler {
    @Override
    public void handle(CommandLine cmd, PrintStream out) {
        String dataset = cmd.getOptionValue("dataset");
        if (dataset == null) {
            out.println("-dataset is required");
            return;
        }

        WordData wordData = new WordData("resource:/data/"+dataset);
        wordData.getWordsByTypeStream()
                .map(WordsByType::isDuplicateFound)
                .filter(b->b).findFirst()
                .ifPresent(b-> out.println("Data set contains errors"));
    }
}
