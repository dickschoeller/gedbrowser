package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Name;

/**
 * A NameHtmlRenderer that is designed to work with a NameRenderer.
 *
 * @author Dick Schoeller
 */
public class SimpleNameNameHtmlRenderer
    implements NameHtmlRenderer, SimpleRenderer {
    /**
     * Holder for the SimpleNameRenderer that is using this helper.
     */
    private final transient SimpleNameRenderer simpleNameRenderer;

    /**
     * Creates a new SimpleNameNameHtmlRenderer.
     *
     * @param simpleNameRenderer the simple name renderer to use
     */
    public SimpleNameNameHtmlRenderer(
            final SimpleNameRenderer simpleNameRenderer) {
        this.simpleNameRenderer = simpleNameRenderer;
    }


    /**
     * Returns the name html.
     *
     * @return the name html
     */
    @Override
    public final String getNameHtml() {
        final Name name = simpleNameRenderer.getGedObject();

        final String prefix = escapeString(name.getPrefix());
        final String surname = escapeString(name.getSurname());
        final String suffix = escapeString(name.getSuffix());

        return separate(prefix, surname, suffix);
    }
}
