package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Multimedia;
import org.schoellerfamily.gedbrowser.datamodel.visitor.MultimediaVisitor;

/**
 * @author Dick Schoeller
 */
public final class MultimediaPhraseRenderer implements PhraseRenderer {
    /**
     * Holder for the MultimediaRenderer that is using this helper.
     */
    private final transient MultimediaRenderer multimediaRenderer;

    /**
     * Constructor.
     *
     * @param multimediaRenderer the renderer that this is associated with.
     */
    protected MultimediaPhraseRenderer(
            final MultimediaRenderer multimediaRenderer) {
        this.multimediaRenderer = multimediaRenderer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String renderAsPhrase() {
        final StringBuilder builder = new StringBuilder("<a href=\"");
        final Multimedia multimedia = multimediaRenderer.getGedObject();
        final MultimediaVisitor visitor = new MultimediaVisitor();
        multimedia.accept(visitor);
        builder.append(visitor.getFilePath());
        builder.append("\">");
        builder.append(visitor.getTitle());
        builder.append("</a>");
        return builder.toString();
    }
}
