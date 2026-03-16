package org.schoellerfamily.gedbrowser.renderer;

/**
 * @author Dick Schoeller
 */
public final class AttributePhraseRenderer implements PhraseRenderer {
    /**
     * Holder for the AttributeRenderer that is using this helper.
     */
    private final transient AttributeRenderer attributeRenderer;

    /**
     * Creates a new AttributePhraseRenderer.
     *
     * @param attributeRenderer the attribute renderer
     */
    protected AttributePhraseRenderer(
            final AttributeRenderer attributeRenderer) {
        this.attributeRenderer = attributeRenderer;
    }

    /**
     * Returns the string.
     *
     * @return the resulting string
     */
    @Override
    public String renderAsPhrase() {
        return RenderingContextRenderer.escapeString(attributeRenderer.getGedObject()
                .getTail());
    }
}
