package org.schoellerfamily.gedbrowser.renderer;

/**
 * @author Dick Schoeller
 */
public final class NoteLinkPhraseRenderer implements PhraseRenderer {
    /** */
    private final transient NoteLinkRenderer nlRenderer;

    /**
     * @param renderer the associated source link renderer;
     */
    public NoteLinkPhraseRenderer(
            final NoteLinkRenderer renderer) {
        this.nlRenderer = renderer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String renderAsPhrase() {
        final StringBuilder builder = new StringBuilder(50);
        builder.append(" [<a href=\"note?db=");
        builder.append(nlRenderer.getGedObject().getDbName());
        builder.append("&amp;id=");
        builder.append(nlRenderer.getGedObject().getToString());
        builder.append("\">");
        builder.append(nlRenderer.getGedObject().getToString());
        builder.append("</a>]");
        return builder.toString();
    }
}
