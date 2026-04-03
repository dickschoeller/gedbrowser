package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Name;

import lombok.RequiredArgsConstructor;

/**
 * Renders name name html output for display.
 *
 * @author Richard Schoeller
 */
@RequiredArgsConstructor
public class NameNameHtmlRenderer implements NameHtmlRenderer {
    /**
     * Holder for the NameRenderer that is using this helper.
     */
    private final NameRenderer nameRenderer;

    /**
     * Returns the name html.
     *
     * @return the name html
     */
    @Override
    public final String getNameHtml() {
        final Name name = nameRenderer.getGedObject();
        final StringBuilder builder = new StringBuilder(40);

        final String prefix = RenderingContextRenderer.escapeString(name.getPrefix());
        final String surname = RenderingContextRenderer.escapeString(name.getSurname());
        final String suffix = RenderingContextRenderer.escapeString(name.getSuffix());

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
