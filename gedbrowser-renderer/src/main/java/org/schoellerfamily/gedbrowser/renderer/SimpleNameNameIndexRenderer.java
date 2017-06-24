package org.schoellerfamily.gedbrowser.renderer;

import java.util.ArrayList;
import java.util.List;

import org.schoellerfamily.gedbrowser.datamodel.Name;

/**
 * @author Dick Schoeller
 */
public final class SimpleNameNameIndexRenderer
    implements NameIndexRenderer, SimpleRenderer {
    /** */
    private final transient SimpleNameRenderer nameRenderer;

    /**
     * Constructor.
     *
     * @param nameRenderer the NameRenderer that is using this helper.
     */
    public SimpleNameNameIndexRenderer(final SimpleNameRenderer nameRenderer) {
        this.nameRenderer = nameRenderer;
    }

    /**
     * {@inheritDoc}
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
     * {@inheritDoc}
     */
    @Override
    public String separate(final String... args) {
        final List<String> argList = new ArrayList<>();
        for (final String arg : args) {
            if (!arg.isEmpty()) {
                argList.add(arg);
            }
        }
        return String.join(", ", argList);
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
