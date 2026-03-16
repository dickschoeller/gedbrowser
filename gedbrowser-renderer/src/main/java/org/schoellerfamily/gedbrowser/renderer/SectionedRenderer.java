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
     * Creates a new SectionedRenderer.
     *
     * @param renderingContext the rendering context
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
     * Sets the name html renderer.
     *
     * @param nameHtmlRenderer the name html renderer to use
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
     * Sets the list item renderer.
     *
     * @param listItemRenderer the list item renderer
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
     * Sets the phrase renderer.
     *
     * @param phraseRenderer the phrase renderer
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
     * Sets the attribute list open renderer.
     *
     * @param attributeListOpenRenderer the attribute list open renderer
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
     * Sets the name index renderer.
     *
     * @param nameIndexRenderer the zero-based name index renderer
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
