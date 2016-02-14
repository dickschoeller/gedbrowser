package org.schoellerfamily.gedbrowser.renderer;

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

    @Override
    public String renderAsPhrase() {
        final StringBuilder builder = new StringBuilder("<a href=\"");
        builder.append(multimediaRenderer.getGedObject().getFilePath());
        builder.append("\">");
        builder.append(multimediaRenderer.getGedObject().getFileTitle());
        builder.append("</a>");
        return builder.toString();
    }
}
