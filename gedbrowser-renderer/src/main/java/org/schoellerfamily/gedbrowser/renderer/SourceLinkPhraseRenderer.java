package org.schoellerfamily.gedbrowser.renderer;

/**
 * @author Dick Schoeller
 */
public final class SourceLinkPhraseRenderer implements PhraseRenderer {
    /** */
    private final transient SourceLinkRenderer slRenderer;

    /**
     * @param renderer the associated source link renderer;
     */
    public SourceLinkPhraseRenderer(
            final SourceLinkRenderer renderer) {
        this.slRenderer = renderer;
    }

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
