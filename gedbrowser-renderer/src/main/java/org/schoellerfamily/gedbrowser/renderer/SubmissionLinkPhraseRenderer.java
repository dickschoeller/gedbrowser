package org.schoellerfamily.gedbrowser.renderer;

/**
 * @author Dick Schoeller
 */
public class SubmissionLinkPhraseRenderer implements PhraseRenderer {
    /**
     * Holder for the SubmissionLinkRenderer that is using this helper.
     */
    private final transient SubmissionLinkRenderer slRenderer;

    /**
     * Constructor.
     *
     * @param renderer the renderer that this is associated with.
     */
    protected SubmissionLinkPhraseRenderer(
            final SubmissionLinkRenderer renderer) {
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
