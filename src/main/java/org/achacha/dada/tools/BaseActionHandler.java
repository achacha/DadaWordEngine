package org.achacha.dada.tools;

import org.apache.commons.cli.CommandLine;

import java.io.PrintStream;

public interface BaseActionHandler {
    /**
     * Handle action
     * @param cmd CommandLine with parameters
     * @param out PrintStream for output
     */
    void handle(CommandLine cmd, PrintStream out);
}
