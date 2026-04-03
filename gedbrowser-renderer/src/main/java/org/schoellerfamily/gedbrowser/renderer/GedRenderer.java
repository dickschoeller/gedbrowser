package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;



/**
 * Renders ged output for display.
 *
 * @author Richard Schoeller
 *
 * @param <G> the GedObject type to render.
 */
@SuppressWarnings({ "PMD.AbstractClassWithoutAbstractMethod" })
public abstract class GedRenderer<G extends GedObject> extends SectionedRenderer {
    /**
     * Beginning of common anchor.
     */
    private static final String A_HREF = "\n    <a href=\"?";

    /** */
    private final G gedObject;

    /** */
    private final GedRendererFactory rendererFactory;

    /**
     * Executes ged renderer.
     *
     * @param gedObject the ged object
     * @param rendererFactory the renderer factory
     * @param renderingContext the rendering context
     */
    protected GedRenderer(final G gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext) {
        super(renderingContext);
        this.gedObject = gedObject;
        this.rendererFactory = rendererFactory;
    }

    /**
     * Gets the name html.
     *
     * @return the name html
     */
    public final String getNameHtml() {
        return getNameHtmlRenderer().getNameHtml();
    }

    /**
     * Gets the index name.
     *
     * @return the index name
     */
    public final String getIndexName() {
        return getNameIndexRenderer().getIndexName();
    }

    /**
     * Gets the ged object.
     *
     * @return the ged object
     */
    public final G getGedObject() {
        return gedObject;
    }

    /**
     * Executes render new line.
     *
     * @param builder the builder
     * @param newLine the new line
     * @return the resulting string builder
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
     * Creates the ged renderer.
     *
     * @param attribute the attribute
     * @return the resulting ged object>
     */
    @SuppressWarnings("java:S1452")
    public final GedRenderer<? extends GedObject> createGedRenderer(
            final GedObject attribute) {
        return getRendererFactory().create(attribute, getRenderingContext());
    }

    /**
     * Returns the string.
     *
     * @return the resulting string
     */
    public final String renderAsPhrase() {
        return getPhraseRenderer().renderAsPhrase();
    }

    /**
     * Gets the renderer factory.
     *
     * @return the renderer factory
     */
    protected final GedRendererFactory getRendererFactory() {
        return rendererFactory;
    }

    /**
     * Gets the string.
     *
     * @return the string
     */
    public final String getString() {
        return gedObject.getString();
    }

    /**
     * Gets the list item contents.
     *
     * @return the list item contents
     */
    public final String getListItemContents() {
        return getListItemRenderer().getListItemContents();
    }

    /**
     * Gets the living href.
     *
     * @return the living href
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
     * Executes menu insertions.
     *
     * @param omit the omit
     * @return the resulting string
     */
    @Override
    public final String menuInsertions(final String omit) {
        final StringBuilder builder = new StringBuilder();
        builder.append("\n    <p>");
        if ("Header".equals(omit)) {
            builder.append(A_HREF + gedObject.getDbName() + "+Header\">Header</a><br>");
        }
        if ("Surnames".equals(omit)) {
            builder.append(A_HREF + gedObject.getDbName() + "+Surnames\">Surnames</a><br>");
        }
        if ("Index".equals(omit)) {
            builder.append(A_HREF + gedObject.getDbName() + "+Index\">Index</a><br>");
        }
        builder.append("\n    </p>");
        return builder.toString();
    }
}
