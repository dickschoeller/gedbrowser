package org.schoellerfamily.gedbrowser.renderer;

import lombok.RequiredArgsConstructor;

/**
 * Renders attribute phrase output for display.
 *
 * @author Richard Schoeller
 */
@RequiredArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public final class AttributePhraseRenderer implements PhraseRenderer {
    /**
     * Holder for the AttributeRenderer that is using this helper.
     */
    private final AttributeRenderer attributeRenderer;

    /**
     * Returns the string.
     *
     * @return the resulting string
     */
    @Override
    public String renderAsPhrase() {
        return RenderingContextRenderer.escapeString(attributeRenderer.getGedObject().getTail());
    }
}
