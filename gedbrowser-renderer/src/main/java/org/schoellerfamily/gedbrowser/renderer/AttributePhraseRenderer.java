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
     * Constructor.
     *
     * @param attributeRenderer the renderer that this is associated with.
     */
    protected AttributePhraseRenderer(
            final AttributeRenderer attributeRenderer) {
        this.attributeRenderer = attributeRenderer;
    }

    @Override
    public String renderAsPhrase() {
        return GedRenderer.escapeString(attributeRenderer.getGedObject()
                .getTail());
    }
}
