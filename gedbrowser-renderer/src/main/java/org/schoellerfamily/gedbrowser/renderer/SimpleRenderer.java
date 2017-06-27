package org.schoellerfamily.gedbrowser.renderer;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains a few common methods used in simple renderers.
 *
 * @author Dick Schoeller
 */
public interface SimpleRenderer {
    /**
     * Properly escape the string for display in HTML.
     *
     * @param input the input string
     * @return the string prepared for display in HTML
     */
    default String escapeString(final String input) {
        return noQuestion(GedRenderer.escapeString(input)).trim();
    }

    /**
     * Separate the provided strings with spaces, drop empty tokens.
     *
     * @param args the strings to separate
     * @return either a space or an empty string
     */
    default String separate(final String... args) {
        final List<String> argList = new ArrayList<>();
        for (final String arg : args) {
            if (!arg.isEmpty()) {
                argList.add(arg);
            }
        }
        return String.join(" ", argList);
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
