package org.schoellerfamily.gedbrowser.renderer;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains a few common methods used in complex renderers.
 *
 * @author Dick Schoeller
 */
public interface ComplexRenderer {
    /**
     * Properly escape the string for display in HTML.
     *
     * @param input the input string
     * @return the string prepared for display in HTML
     */
    default String escapeString(final String input) {
        return GedRenderer.escapeString(input).trim();
    }

    /**
     * Separate the provided strings with spaces, drop empty tokens.
     *
     * @param args the strings to separate
     * @return either a space or an empty string
     */
    default String separate(final String... args) {
        final List<String> argList = new ArrayList<>();
        for (String arg : args) {
            if (!arg.isEmpty()) {
                argList.add(arg);
            }
        }
        return String.join(" ", argList);
    }
}
