package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Name;

/**
 * Renders name name index output for display.
 *
 * @author Richard Schoeller
 */
public final class NameNameIndexRenderer
    implements NameIndexRenderer, ComplexRenderer {
    /** */
    private final transient NameRenderer nameRenderer;

    /**
     * Creates a new NameNameIndexRenderer.
     *
     * @param nameRenderer the name renderer to use
     */
    public NameNameIndexRenderer(final NameRenderer nameRenderer) {
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

        final StringBuilder builder = new StringBuilder(40);

        final String prefix = escapeString(name.getPrefix());
        final String surname = escapeString(name.getSurname());
        final String suffix = escapeString(name.getSuffix());

        builder.append(" <span class=\"surname\">");
        builder.append(surname);
        builder.append("</span>");
        if (!prefix.isEmpty()) {
            builder.append(", ");
            builder.append(prefix);
        }
        if (!suffix.isEmpty()) {
            builder.append(", ");
            builder.append(suffix);
        }
        return builder.toString();
    }

}
