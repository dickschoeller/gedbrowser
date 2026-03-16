package org.schoellerfamily.gedbrowser.renderer;

import java.util.Arrays;

import org.schoellerfamily.gedbrowser.datamodel.Name;

/**
 * Renders simple name name index output for display.
 *
 * @author Richard Schoeller
 */
public final class SimpleNameNameIndexRenderer
    implements NameIndexRenderer, SimpleRenderer {
    /** */
    private final transient SimpleNameRenderer nameRenderer;

    /**
     * Creates a new SimpleNameNameIndexRenderer.
     *
     * @param nameRenderer the name renderer to use
     */
    public SimpleNameNameIndexRenderer(final SimpleNameRenderer nameRenderer) {
        this.nameRenderer = nameRenderer;
    }

    /**
     * Returns the index name.
     *
     * @return the index name
     */
    @Override
    public String getIndexName() {
        final Name name = nameRenderer.getGedObject();
        if (!name.isSet()) {
            return " <span class=\"surname\">?</span>";
        }

        final String prefix = escapeString(name.getPrefix());
        final String surname = escapeString(name.getSurname());
        final String suffix = escapeString(name.getSuffix());

        return " " + separate(
                wrap("<span class=\"surname\">", surname, "</span>"),
                prefix, suffix);
    }

    /**
     * Returns the string.
     *
     * @param args the command-line arguments
     * @return the resulting string
     */
    @Override
    public String separate(final String... args) {
        return String.join(", ",
            Arrays.stream(args)
                .filter(arg -> !arg.isEmpty())
                .toList());
    }

    /**
     * If the middle string has contents, append the strings.
     *
     * @param before the before string
     * @param string the middle string
     * @param after the after string
     * @return the combined strings
     */
    private String wrap(final String before, final String string,
            final String after) {
        if (string.isEmpty()) {
            return "";
        }
        return before + string + after;
    }

}
