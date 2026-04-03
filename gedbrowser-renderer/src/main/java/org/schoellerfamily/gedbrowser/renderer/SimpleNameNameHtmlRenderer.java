package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Name;

import lombok.RequiredArgsConstructor;

/**
 * Renders simple name name html output for display.
 *
 * @author Richard Schoeller
 */
@RequiredArgsConstructor
public class SimpleNameNameHtmlRenderer implements NameHtmlRenderer, SimpleRenderer {
    /**
     * Holder for the SimpleNameRenderer that is using this helper.
     */
    private final SimpleNameRenderer simpleNameRenderer;

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
