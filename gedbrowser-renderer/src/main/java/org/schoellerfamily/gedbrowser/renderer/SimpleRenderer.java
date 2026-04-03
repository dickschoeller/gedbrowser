package org.schoellerfamily.gedbrowser.renderer;

import java.util.Arrays;

/**
 * Renders simple output for display.
 *
 * @author Richard Schoeller
 */
public interface SimpleRenderer {
    /**
     * Properly escape the string for display in HTML.
     *
     * @param input the input string
     * @return the string prepared for display in HTML
     */
    default String escapeString(final String input) {
        return noQuestion(RenderingContextRenderer.escapeString(input)).trim();
    }

    /**
     * Separate the provided strings with spaces, drop empty tokens.
     *
     * @param args the strings to separate
     * @return either a space or an empty string
     */
    default String separate(final String... args) {
        return String.join(" ",
            Arrays.stream(args)
                .filter(arg -> !arg.isEmpty())
                .toList());
    }

    /**
     * Eliminate question marks that result from the more normal rendering
     * processes.
     *
     * @param input the input string
     * @return either and empty string or the input string
     */
    default String noQuestion(final String input) {
        if ("?".equals(input)) {
            return "";
        }
        return input;
    }
}
