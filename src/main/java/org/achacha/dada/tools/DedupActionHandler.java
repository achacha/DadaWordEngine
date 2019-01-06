package org.achacha.dada.tools;

import org.achacha.dada.engine.data.WordData;
import org.achacha.dada.engine.data.WordsByType;
import org.apache.commons.cli.CommandLine;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DedupActionHandler implements BaseActionHandler {
    /**
     * Remove duplicates and save to output location
     * @param cmd CommandLine with parameters
     * @param out PrintStream for output
     */
    @Override
    public void handle(CommandLine cmd, PrintStream out) {
        String dataset = cmd.getOptionValue("dataset");
        if (dataset == null) {
            out.println("-dataset is required");
            return;
        }
        String basePathString = cmd.getOptionValue("outpath");
        if (basePathString == null) {
            out.println("-outpath is required");
            return;
        }
        Path basePath = Paths.get(basePathString);

        WordData wordData = new WordData("resource:/data/"+dataset);
        wordData.getWordsByTypeStream().filter(WordsByType::isDuplicateFound).forEach(byType->{
            out.println("Processing: "+byType.getType());
            try {
                byType.saveWords(basePath);
            }
            catch(IOException e) {
                e.printStackTrace(out);
            }
        });
    }
}
