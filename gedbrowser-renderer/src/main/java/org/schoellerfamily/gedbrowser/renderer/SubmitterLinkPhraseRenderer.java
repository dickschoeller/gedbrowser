package org.schoellerfamily.gedbrowser.renderer;

import lombok.RequiredArgsConstructor;

/**
 * Renders submitter link phrase output for display.
 *
 * @author Richard Schoeller
 */
@RequiredArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class SubmitterLinkPhraseRenderer implements PhraseRenderer {
    /**
     * Holder for the SubmitterLinkRenderer that is using this helper.
     */
    private final SubmitterLinkRenderer slRenderer;

    /**
     * Returns the string.
     *
     * @return the resulting string
     */
    @Override
    public final String renderAsPhrase() {
        return slRenderer.getNameString();
    }
}
