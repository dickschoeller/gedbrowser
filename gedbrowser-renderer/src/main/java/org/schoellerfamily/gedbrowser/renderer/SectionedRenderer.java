package org.schoellerfamily.gedbrowser.renderer;

import lombok.Getter;
import lombok.Setter;

/**
 * Renders sectioned output for display.
 *
 * @author Richard Schoeller
 */
@Getter
public class SectionedRenderer extends RenderingContextRenderer {
    /** */
    @Setter(lombok.AccessLevel.PROTECTED)
    private NameHtmlRenderer nameHtmlRenderer;

    /** */
    @Setter(lombok.AccessLevel.PROTECTED)
    private NameIndexRenderer nameIndexRenderer;

    /** */
    @Setter(lombok.AccessLevel.PROTECTED)
    private ListItemRenderer listItemRenderer;

    /** */
    @Setter(lombok.AccessLevel.PROTECTED)
    private PhraseRenderer phraseRenderer;

    /** */
    @Setter(lombok.AccessLevel.PROTECTED)
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
}
