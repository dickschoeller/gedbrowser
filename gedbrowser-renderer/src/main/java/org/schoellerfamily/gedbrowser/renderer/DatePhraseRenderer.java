package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Date;

/**
 * @author Dick Schoeller
 */
public class DatePhraseRenderer implements PhraseRenderer {
    /**
     * Holder for the DateRenderer that is using this helper.
     */
    private final transient DateRenderer dateRenderer;

    /**
     * Constructor.
     *
     * @param dateRenderer the renderer that this is associated with.
     */
    protected DatePhraseRenderer(final DateRenderer dateRenderer) {
        this.dateRenderer = dateRenderer;
    }

    @Override
    public final String renderAsPhrase() {
        final Date date = dateRenderer.getGedObject();
        return GedRenderer.escapeString(date.getString());
    }
}
