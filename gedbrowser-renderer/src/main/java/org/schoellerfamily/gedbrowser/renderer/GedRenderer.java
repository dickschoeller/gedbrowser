package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.analytics.CalendarProvider;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;

/**
 * Base class for rendering GedObjects.
 *
 * @author Dick Schoeller
 * @param <G> the GedObject type to render.
 */
@SuppressWarnings({ "PMD.AbstractClassWithoutAbstractMethod" })
public abstract class GedRenderer<G extends GedObject> extends Renderer {
    /** */
    private final transient G gedObject;

    /** */
    private final transient GedRendererFactory rendererFactory;

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

    /** */
    private final CalendarProvider provider;

    /**
     * @param gedObject the GedObject that we are going to render
     * @param rendererFactory the factory that creates the renderers for the
     *        attributes
     * @param renderingContext the context that we are rendering in
     * @param provider provides information about today
     */
    public GedRenderer(final G gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext,
            final CalendarProvider provider) {
        super(renderingContext);
        this.gedObject = gedObject;
        this.rendererFactory = rendererFactory;
        this.nameHtmlRenderer = new NullNameHtmlRenderer();
        this.nameIndexRenderer = new NullNameIndexRenderer();
        this.listItemRenderer = new NullListItemRenderer();
        this.phraseRenderer = new NullPhraseRenderer();
        this.attributeListOpenRenderer = new SimpleAttributeListOpenRenderer();
        this.provider = provider;
    }

    /**
     * @return a name string cleaned for HTML usage.
     */
    public final String getNameHtml() {
        return nameHtmlRenderer.getNameHtml();
    }

    /**
     * @return a name string in index format.
     */
    public final String getIndexName() {
        return nameIndexRenderer.getIndexName();
    }

    /**
     * @return the GedObject
     */
    protected final G getGedObject() {
        return gedObject;
    }

    /**
     * Render the GedObject as an item in a list.
     *
     * @param builder Buffer for holding the rendition
     * @param newLine put in a new line for each line.
     * @param pad Minimum number spaces for padding each line of the output
     * @return the builder
     */
    public final StringBuilder renderAsListItem(final StringBuilder builder,
            final boolean newLine, final int pad) {
        return listItemRenderer.renderAsListItem(builder, newLine, pad);
    }

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
     * @param builder Buffer for holding the rendition
     * @param pad Minimum number spaces for padding each line of the output
     * @param subObject sub-object whose attributes are rendered
     * @return the builder
     */
    protected final StringBuilder renderAttributeList(
            final StringBuilder builder, final int pad,
            final GedObject subObject) {
        StringBuilder b = builder;
        renderAttributeListOpen(b, pad, subObject);

        for (final GedObject attribute : subObject.getAttributes()) {
            final GedRenderer<? extends GedObject> renderer =
                    createGedRenderer(attribute);
            b = renderAsListItem(renderer, b, pad);
        }

        renderAttributeListClose(builder, pad, subObject);

        return builder;
    }

    /**
     * Render as a list item.
     *
     * @param renderer the renderer to use
     * @param builder the string builder to fill
     * @param pad the amount of padding to make the html look nice
     * @return the string builder that was passed in
     */
    private StringBuilder renderAsListItem(
            final GedRenderer<? extends GedObject> renderer,
            final StringBuilder builder, final int pad) {
        return renderer.renderAsListItem(builder, true, pad + 2);
    }

    /**
     * @param attribute
     *            The sub-object to render.
     * @return The renderer.
     */
    protected final GedRenderer<? extends GedObject> createGedRenderer(
            final GedObject attribute) {
        return getRendererFactory().create(attribute,
                getRenderingContext(),
                provider);
    }

    /**
     * @param builder Buffer for holding the rendition
     * @param pad Minimum number spaces for padding each line of the output
     * @param subObject subobject whose attributes are rendered
     */
    protected final void renderAttributeListOpen(final StringBuilder builder,
            final int pad, final GedObject subObject) {
        attributeListOpenRenderer.renderAttributeListOpen(builder, pad,
                subObject);
    }

    /**
     * @param builder Buffer for holding the rendition
     * @param pad Minimum number spaces for padding each line of the output
     * @param subObject subobject whose attributes are rendered
     */
    protected final void renderAttributeListClose(final StringBuilder builder,
            final int pad, final GedObject subObject) {
        if (subObject.hasAttributes()) {
            renderPad(builder, pad, false);
            builder.append("</ul>");
        }
    }

    /**
     * @return this object as a phrase to be inserted in a sentence
     */
    public final String renderAsPhrase() {
        return phraseRenderer.renderAsPhrase();
    }

    /**
     * @return the factory
     */
    protected final GedRendererFactory getRendererFactory() {
        return rendererFactory;
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
        return listItemRenderer.getListItemContents();
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
     * @return the calendar provider to inform us about "today"
     */
    public final CalendarProvider getCalendarProvider() {
        return provider;
    }

    /**
     * {@inheritDoc}
     */
    protected final void menuInsertions(final String omit,
            final StringBuilder builder) {
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
    }
}
