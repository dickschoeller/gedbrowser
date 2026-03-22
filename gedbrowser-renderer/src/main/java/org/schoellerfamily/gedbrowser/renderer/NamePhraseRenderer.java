package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Name;

import lombok.RequiredArgsConstructor;

/**
 * Renders name phrase output for display.
 *
 * @author Richard Schoeller
 */
@RequiredArgsConstructor
public class NamePhraseRenderer implements PhraseRenderer, ComplexRenderer {
    /**
     * The renderer that we are associated with.
     */
    private final NameRenderer nameRenderer;

    /**
     * Executes render as phrase.
     *
     * @return the resulting string
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
