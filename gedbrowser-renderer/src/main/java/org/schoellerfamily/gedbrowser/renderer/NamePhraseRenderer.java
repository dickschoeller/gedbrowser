package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Name;

/**
 * @author Dick Schoeller
 */
public class NamePhraseRenderer implements PhraseRenderer, ComplexRenderer {
    /**
     * The renderer that we are associated with.
     */
    private final transient NameRenderer nameRenderer;

    /**
     * Constructor.
     *
     * @param nameRenderer the renderer that we are associated with.
     */
    public NamePhraseRenderer(final NameRenderer nameRenderer) {
        this.nameRenderer = nameRenderer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String renderAsPhrase() {
        final Name name = nameRenderer.getGedObject();
        final String prefix = escapeString(name.getPrefix());
        final String surname = escapeString(name.getSurname());
        final String suffix = escapeString(name.getSuffix());
        return separate(prefix, surname, suffix);
    }
}
