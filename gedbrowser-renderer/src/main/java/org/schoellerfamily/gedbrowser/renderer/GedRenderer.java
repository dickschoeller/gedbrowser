package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;

/**
 * Base class for rendering GedObjects.
 *
 * @author Dick Schoeller
 * @param <G> the GedObject type to render.
 */
@SuppressWarnings({ "PMD.AbstractClassWithoutAbstractMethod" })
public abstract class GedRenderer<G extends GedObject>
        extends SectionedRenderer {
    /** */
    private final transient G gedObject;

    /** */
    private final transient GedRendererFactory rendererFactory;

    /**
     * @param gedObject the GedObject that we are going to render
     * @param rendererFactory the factory that creates the renderers for the
     *        attributes
     * @param renderingContext the context that we are rendering in
     */
    public GedRenderer(final G gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext) {
        super(renderingContext);
        this.gedObject = gedObject;
        this.rendererFactory = rendererFactory;
    }

    /**
     * @return a name string cleaned for HTML usage.
     */
    public final String getNameHtml() {
        return getNameHtmlRenderer().getNameHtml();
    }

    /**
     * @return a name string in index format.
     */
    public final String getIndexName() {
        return getNameIndexRenderer().getIndexName();
    }

    /**
     * @return the GedObject
     */
    protected final G getGedObject() {
        return gedObject;
    }
//
//    /**
//     * Render the GedObject as an item in a list.
//     *
//     * @param builder Buffer for holding the rendition
//     * @param newLine put in a new line for each line.
//     * @param pad Minimum number spaces for padding each line of the output
//     * @return the builder
//     */
//    public final StringBuilder renderAsListItem(final StringBuilder builder,
//            final boolean newLine, final int pad) {
//        return listItemRenderer.renderAsListItem(builder, newLine, pad);
//    }

    /**
     * @param builder Buffer for holding the rendition
     * @param newLine put in a new line for each line.
     * @return the builder
     */
    protected static final StringBuilder renderNewLine(
            final StringBuilder builder, final boolean newLine) {
        if (newLine) {
            builder.append('\n');
        }
        return builder;
    }

    /**
     * Render some leading spaces onto a line of html.
     *
     * @param builder Buffer for holding the rendition
     * @param pad Minimum number spaces for padding each line of the output
     * @param newLine put in a new line for each line.
     * @return the builder
     */
    protected static final StringBuilder renderPad(final StringBuilder builder,
            final int pad, final boolean newLine) {
        renderNewLine(builder, newLine);
        for (int i = 0; i < pad; i++) {
            builder.append(' ');
        }
        return builder;
    }

    /**
     * @param attribute
     *            The sub-object to render.
     * @return The renderer.
     */
    protected final GedRenderer<? extends GedObject> createGedRenderer(
            final GedObject attribute) {
        return getRendererFactory().create(attribute, getRenderingContext());
    }

    /**
     * @return this object as a phrase to be inserted in a sentence
     */
    public final String renderAsPhrase() {
        return getPhraseRenderer().renderAsPhrase();
    }

    /**
     * @return the factory
     */
    protected final GedRendererFactory getRendererFactory() {
        return rendererFactory;
    }

    /**
     * @return the string field from the underlying GED object.
     */
    public final String getString() {
        return gedObject.getString();
    }

    /**
     * @return the inner part of the item as it would be in a list.
     */
    public final String getListItemContents() {
        return getListItemRenderer().getListItemContents();
    }

    /**
     * @return the href string to the living estimator.
     */
    public final String getLivingHref() {
        return "living?db=" + getDbName(getGedObject());
    }

    /**
     * Get the database name from the passed DB object.
     *
     * @param gedObj the GED object
     * @return the database name
     */
    private String getDbName(final G gedObj) {
        return gedObj.getDbName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String menuInsertions(final String omit) {
        final StringBuilder builder = new StringBuilder();
        builder.append("\n    <p>");
        if ("Header".equals(omit)) {
            builder.append("\n    <a href=\"?" + gedObject.getDbName()
                    + "+Header\">Header</a><br>");
        }
        if ("Surnames".equals(omit)) {
            builder.append("\n    <a href=\"?" + gedObject.getDbName()
                    + "+Surnames\">Surnames</a><br>");
        }
        if ("Index".equals(omit)) {
            builder.append("\n    <a href=\"?" + gedObject.getDbName()
                    + "+Index\">Index</a><br>");
        }
        builder.append("\n    </p>");
        return builder.toString();
    }
}
