package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Multimedia;

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
        builder.append(getFilePath(multimediaRenderer.getGedObject()));
        builder.append("\">");
        builder.append(getFileTitle(multimediaRenderer.getGedObject()));
        builder.append("</a>");
        return builder.toString();
    }

    /**
     * Get the file path to the multimedia object.
     *
     * @param multimedia the multimedia object
     * @return the path
     */
    private String getFilePath(final Multimedia multimedia) {
        return multimedia.getFilePath();
    }

    /**
     * Get the file title of the multimedia object.
     *
     * @param multimedia the multimedia object
     * @return the title
     */
    private String getFileTitle(final Multimedia multimedia) {
        return multimedia.getFileTitle();
    }
}
