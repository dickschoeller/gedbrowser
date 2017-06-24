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
     * Constructor.
     *
     * This constructor is public for testing purposes only. Do not try to call
     * it outside of the context of the rendering engine.
     *
     * @param simpleNameRenderer the renderer that this is associated with.
     */
    public SimpleNameNameHtmlRenderer(
            final SimpleNameRenderer simpleNameRenderer) {
        this.simpleNameRenderer = simpleNameRenderer;
    }


    /**
     * {@inheritDoc}
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
