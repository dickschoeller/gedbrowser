package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Name;

/**
 * @author Dick Schoeller
 */
public final class NameNameIndexRenderer
    implements NameIndexRenderer, ComplexRenderer {
    /** */
    private final transient NameRenderer nameRenderer;

    /**
     * Constructor.
     *
     * @param nameRenderer the NameRenderer that is using this helper.
     */
    public NameNameIndexRenderer(final NameRenderer nameRenderer) {
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
