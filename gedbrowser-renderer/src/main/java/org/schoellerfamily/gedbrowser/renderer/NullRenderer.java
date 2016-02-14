package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;

/**
 * Renders nothing, used when we don't know what to display.
 *
 * @author Dick Schoeller
 */
public final class NullRenderer extends GedRenderer<GedObject> {
    /**
     * @param gedObject The GedObject that we are going to render.
     * @param rendererFactory
     *            The factory that creates the renderers for the attributes.
     * @param renderingContext the context that we are rendering in
     */
    public NullRenderer(final GedObject gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext) {
        super(gedObject, rendererFactory, renderingContext);
    }

//    @Override
//    public StringBuilder renderAsPage(final StringBuilder builder) {
//        return getDefaultRenderer().renderAsPage(builder);
//    }

//    @Override
//    public StringBuilder renderAsListItem(final StringBuilder builder,
//            final boolean newLine, final int pad) {
//        return getDefaultRenderer().renderAsPage(builder);
//    }

//    @Override
//    public StringBuilder renderAsSection(final StringBuilder builder,
//            final GedRenderer<?> pageRenderer, final boolean newLine,
//            final int pad, final int sectionNumber) {
//        return getDefaultRenderer().renderAsSection(builder, pageRenderer,
//                newLine, pad, sectionNumber);
//    }

//    @Override
//    public String getIndexName() {
//        return getDefaultRenderer().getIndexName();
//    }

//    @Override
//    public String getNameHtml() {
//        return getDefaultRenderer().getNameHtml();
//    }

//    @Override
//    public String renderAsPhrase() {
//        return getDefaultRenderer().renderAsPhrase();
//    }

//    @Override
//    protected void renderAttributeListOpen(final StringBuilder builder,
//            final int pad, final GedObject subObject) {
//        getDefaultRenderer().renderAttributeListOpen(builder, pad, subObject);
//    }
}
