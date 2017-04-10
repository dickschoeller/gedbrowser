package org.schoellerfamily.gedbrowser.renderer;

/**
 * @author Dick Schoeller
 *
 */
public class SectionedRenderer extends RenderingContextRenderer {
    /** */
    private NameHtmlRenderer nameHtmlRenderer;

    /** */
    private NameIndexRenderer nameIndexRenderer;

    /** */
    private ListItemRenderer listItemRenderer;

    /** */
    private PhraseRenderer phraseRenderer;

    /** */
    private AttributeListOpenRenderer attributeListOpenRenderer;

    /**
     * Constructor.
     *
     * @param renderingContext the context that we are rendering in
     */
    public SectionedRenderer(final RenderingContext renderingContext) {
        super(renderingContext);
        this.nameHtmlRenderer = new NullNameHtmlRenderer();
        this.nameIndexRenderer = new NullNameIndexRenderer();
        this.listItemRenderer = new NullListItemRenderer();
        this.phraseRenderer = new NullPhraseRenderer();
        this.attributeListOpenRenderer = new SimpleAttributeListOpenRenderer();
    }

    /**
     * Set the renderer to something new.
     *
     * @param nameHtmlRenderer
     *            the new renderer.
     */
    protected final void setNameHtmlRenderer(
            final NameHtmlRenderer nameHtmlRenderer) {
        this.nameHtmlRenderer = nameHtmlRenderer;
    }

    /**
     * This method is public for testing purposes only. Do
     * not try to call it outside of the context of the rendering
     * engine.
     *
     * @return the renderer.
     */
    public final NameHtmlRenderer getNameHtmlRenderer() {
        return this.nameHtmlRenderer;
    }

    /**
     * Set the renderer to something new.
     *
     * @param listItemRenderer
     *            the new renderer.
     */
    protected final void setListItemRenderer(
            final ListItemRenderer listItemRenderer) {
        this.listItemRenderer = listItemRenderer;
    }

    /**
     * This method is public for testing purposes only. Do not try to call it
     * outside of the context of the rendering engine.
     *
     * @return the renderer.
     */
    public final ListItemRenderer getListItemRenderer() {
        return this.listItemRenderer;
    }

    /**
     * Set the renderer to something new.
     *
     * @param phraseRenderer
     *            the new renderer.
     */
    protected final void setPhraseRenderer(
            final PhraseRenderer phraseRenderer) {
        this.phraseRenderer = phraseRenderer;
    }

    /**
     * This method is public for testing purposes only. Do not try to call it
     * outside of the context of the rendering engine.
     *
     * @return the renderer.
     */
    public final PhraseRenderer getPhraseRenderer() {
        return this.phraseRenderer;
    }

    /**
     * Set the renderer to something new.
     *
     * @param attributeListOpenRenderer
     *            the new renderer.
     */
    protected final void setAttributeListOpenRenderer(
            final AttributeListOpenRenderer
                attributeListOpenRenderer) {
        this.attributeListOpenRenderer = attributeListOpenRenderer;
    }

    /**
     * This method is public for testing purposes only. Do
     * not try to call it outside of the context of the rendering
     * engine.
     *
     * @return the renderer.
     */
    public final AttributeListOpenRenderer getAttributeListOpenRenderer() {
        return this.attributeListOpenRenderer;
    }

    /**
     * Set the renderer to something new.
     *
     * @param nameIndexRenderer
     *            the new renderer.
     */
    protected final void setNameIndexRenderer(
            final NameIndexRenderer nameIndexRenderer) {
        this.nameIndexRenderer = nameIndexRenderer;
    }

    /**
     * This method is public for testing purposes only. Do
     * not try to call it outside of the context of the rendering
     * engine.
     *
     * @return the renderer.
     */
    public final NameIndexRenderer getNameIndexRenderer() {
        return this.nameIndexRenderer;
    }
}
