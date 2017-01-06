package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Name;

/**
 * A NameHtmlRenderer that is designed to work with a NameRenderer.
 *
 * @author Dick Schoeller
 */
public class NameNameHtmlRenderer implements NameHtmlRenderer {
    /**
     * Holder for the NameRenderer that is using this helper.
     */
    private final transient NameRenderer nameRenderer;

    /**
     * Constructor.
     *
     * This constructor is public for testing purposes only. Do not try to call
     * it outside of the context of the rendering engine.
     *
     * @param nameRenderer the renderer that this is associated with.
     */
    public NameNameHtmlRenderer(final NameRenderer nameRenderer) {
        this.nameRenderer = nameRenderer;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public final String getNameHtml() {
        final Name name = nameRenderer.getGedObject();
        final StringBuilder builder = new StringBuilder(40);

        final String prefix = GedRenderer.escapeString(name.getPrefix());
        final String surname = GedRenderer.escapeString(name.getSurname());
        final String suffix = GedRenderer.escapeString(name.getSuffix());

        builder.append(prefix);
        builder.append(" <span class=\"surname\">");
        builder.append(surname);
        builder.append("</span>");
        if (!suffix.isEmpty()) {
            builder.append(' ').append(suffix);
        }

        return builder.toString();
    }
}
