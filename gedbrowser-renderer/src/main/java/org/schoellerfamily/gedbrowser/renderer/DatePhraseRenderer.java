package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.visitor.GetDateVisitor;

/**
 * Renders date phrase output for display.
 *
 * @author Richard Schoeller
 */
public class DatePhraseRenderer implements PhraseRenderer {
    /**
     * Holder for the DateRenderer that is using this helper.
     */
    private final transient DateRenderer dateRenderer;

    /**
     * Creates a new DatePhraseRenderer.
     *
     * @param dateRenderer the date renderer
     */
    protected DatePhraseRenderer(final DateRenderer dateRenderer) {
        this.dateRenderer = dateRenderer;
    }

    /**
     * Executes render as phrase.
     *
     * @return the resulting string
     */
    @Override
    public final String renderAsPhrase() {
        final GetDateVisitor visitor = new GetDateVisitor();
        final Date date1 = dateRenderer.getGedObject();
        date1.accept(visitor);
        return RenderingContextRenderer.escapeString(visitor.getDate());
    }
}
