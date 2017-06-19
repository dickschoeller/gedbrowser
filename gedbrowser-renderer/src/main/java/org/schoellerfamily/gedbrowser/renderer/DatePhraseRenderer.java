package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.visitor.GetDateVisitor;

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

    /**
     * {@inheritDoc}
     */
    @Override
    public final String renderAsPhrase() {
        final GetDateVisitor visitor = new GetDateVisitor();
        final Date date1 = dateRenderer.getGedObject();
        date1.accept(visitor);
        return GedRenderer.escapeString(visitor.getDate());
    }
}
