package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Name;

/**
 * @author Dick Schoeller
 */
public class SimpleNamePhraseRenderer
    implements PhraseRenderer, SimpleRenderer {
    /**
     * The renderer that we are associated with.
     */
    private final transient SimpleNameRenderer simpleNameRenderer;

    /**
     * Constructor.
     *
     * @param simpleNameRenderer the renderer that we are associated with.
     */
    public SimpleNamePhraseRenderer(
            final SimpleNameRenderer simpleNameRenderer) {
        this.simpleNameRenderer = simpleNameRenderer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String renderAsPhrase() {
        final Name name = simpleNameRenderer.getGedObject();
        final String prefix = escapeString(name.getPrefix());
        final String surname = escapeString(name.getSurname());
        final String suffix = escapeString(name.getSuffix());
        return separate(prefix, surname, suffix);
    }
}
