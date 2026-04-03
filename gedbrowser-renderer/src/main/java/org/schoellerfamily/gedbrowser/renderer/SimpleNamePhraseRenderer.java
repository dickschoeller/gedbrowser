package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Name;

import lombok.RequiredArgsConstructor;

/**
 * Renders simple name phrase output for display.
 *
 * @author Richard Schoeller
 */
@RequiredArgsConstructor
public class SimpleNamePhraseRenderer implements PhraseRenderer, SimpleRenderer {
    /**
     * The renderer that we are associated with.
     */
    private final SimpleNameRenderer simpleNameRenderer;

    /**
     * Executes render as phrase.
     *
     * @return the resulting string
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
