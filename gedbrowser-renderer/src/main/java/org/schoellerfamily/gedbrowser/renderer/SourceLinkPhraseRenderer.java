package org.schoellerfamily.gedbrowser.renderer;

/**
 * @author Dick Schoeller
 */
public final class SourceLinkPhraseRenderer implements PhraseRenderer {
    /** */
    private final transient SourceLinkRenderer slRenderer;

    /**
     * Creates a new SourceLinkPhraseRenderer.
     *
     * @param renderer the renderer
     */
    public SourceLinkPhraseRenderer(
            final SourceLinkRenderer renderer) {
        this.slRenderer = renderer;
    }

    /**
     * Executes render as phrase.
     *
     * @return the resulting string
     */
    @Override
    public String renderAsPhrase() {
        final StringBuilder builder = new StringBuilder(50);
        builder.append(" [<a href=\"source?db=");
        builder.append(slRenderer.getGedObject().getDbName());
        builder.append("&amp;id=");
        builder.append(slRenderer.getGedObject().getToString());
        builder.append("\">");
        builder.append(slRenderer.getGedObject().getToString());
        builder.append("</a>]");
        return builder.toString();
    }
}
