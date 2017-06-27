package org.schoellerfamily.gedbrowser.renderer;

/**
 * @author Dick Schoeller
 */
public class SubmittorLinkPhraseRenderer implements PhraseRenderer {
    /**
     * Holder for the SubmittorLinkRenderer that is using this helper.
     */
    private final transient SubmittorLinkRenderer slRenderer;

    /**
     * Constructor.
     *
     * @param renderer the renderer that this is associated with.
     */
    protected SubmittorLinkPhraseRenderer(
            final SubmittorLinkRenderer renderer) {
        this.slRenderer = renderer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String renderAsPhrase() {
        return slRenderer.getNameString();
    }
}
