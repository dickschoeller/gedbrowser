package org.schoellerfamily.gedbrowser.renderer;

import java.text.DateFormat;
import java.util.Locale;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;

/**
 * Base class for rendering GedObjects.
 *
 * @author Dick Schoeller
 * @param <G> the GedObject type to render.
 */
@SuppressWarnings({ "PMD.AbstractClassWithoutAbstractMethod",
        "PMD.CommentSize",
        "PMD.GodClass",
        "PMD.TooManyMethods" })
public abstract class GedRenderer<G extends GedObject> {
    /** */
    private static final String DICK_EMAIL = "schoeller@comcast.net";

    /** */
    private static final String GEDBROWSER_URL =
            "http://www.schoellerfamily.org/softwarwe/gedbrowser.html";

    /** */
    private final transient G gedObject;

    /** */
    private final RenderingContext renderingContext;

    /** */
    private final transient GedRendererFactory rendererFactory;

    /** */
    private NameHtmlRenderer nameHtmlRenderer;

    /** */
    private NameIndexRenderer nameIndexRenderer;

    /** */
    private ListItemRenderer listItemRenderer;

    /** */
    private SectionRenderer sectionRenderer;

    /** */
    private PhraseRenderer phraseRenderer;

    /** */
    private AttributeListOpenRenderer attributeListOpenRenderer;

    /**
     * @param gedObject the GedObject that we are going to render
     * @param rendererFactory the factory that creates the renderers for the
     *        attributes
     * @param renderingContext the context that we are rendering in
     */
    public GedRenderer(final G gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext) {
        this.gedObject = gedObject;
        this.rendererFactory = rendererFactory;
        this.nameHtmlRenderer = new NullNameHtmlRenderer();
        this.nameIndexRenderer = new NullNameIndexRenderer();
        this.listItemRenderer = new NullListItemRenderer();
        this.sectionRenderer = new NullSectionRenderer();
        this.phraseRenderer = new NullPhraseRenderer();
        this.attributeListOpenRenderer = new SimpleAttributeListOpenRenderer();
        this.renderingContext = renderingContext;
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
     * Render the GedObject as a section of a page.
     *
     * @param builder Buffer for holding the rendition
     * @param outerRenderer the renderer being used for the current page
     * @param newLine put in a new line for each line.
     * @param pad Minimum number spaces for padding each line of the output
     * @param sectionNumber numbers repeated sections of the same type.
     * @return the builder
     */
    public final StringBuilder renderAsSection(final StringBuilder builder,
            final GedRenderer<?> outerRenderer, final boolean newLine,
            final int pad, final int sectionNumber) {
        return sectionRenderer.renderAsSection(builder, outerRenderer, newLine,
                pad, sectionNumber);
    }

    /**
     * Render the GedObject as a section of a page.
     *
     * @param outerRenderer the renderer being used for the current page
     * @param sectionNumber numbers repeated sections of the same type
     * @return the section rendition as a string
     */
    public final String getAsSection(final GedRenderer<?> outerRenderer,
            final int sectionNumber) {
        final StringBuilder builder =
                sectionRenderer.renderAsSection(new StringBuilder(),
                        outerRenderer, true, 0, sectionNumber);
        return builder.toString();
    }

    /**
     * Get the HTML header for a page.
     *
     * This method is public for testing purposes only. Do
     * not try to call it outside of the context of the rendering
     * engine.
     *
     * @param title the title string.
     * @param keywords any keywords.
     * @return a string containing the HTML header.
     */
    public final String getHeaderHtml(final String title,
            final String keywords) {
        final StringBuilder builder = new StringBuilder(585);
        builder.append("Content-type: text/html\n\n"); // NOPMD
        builder.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\"\n");
        builder.append("  \"http://www.w3.org/TR/html4/strict.dtd\">\n");
        builder.append("<html>\n");
        builder.append("  <head>\n");
        builder.append("    <meta http-equiv=\"Content-Type\" ");
        builder.append("content=\"text/html; charset=utf-8\">\n");
        builder.append("    <meta name=\"Author\" ");
        builder.append("content=\"gedbrowser\">\n");
        builder.append("    <meta name=\"Description\" ");
        builder.append("content=\"genealogy\">\n");
        builder.append("    <meta name=\"Keywords\" ");
        builder.append("content=\"genealogy gedbrowser ");
        builder.append(keywords);
        builder.append("\">\n"); // NOPMD
        builder.append("    <meta http-equiv=\"Content-Style-Type\" ");
        builder.append("content=\"text/css\">\n");
        builder.append("    <link href=\"/gedbrowser/gedbrowser.css\" ");
        builder.append("rel=\"stylesheet\" type=\"text/css\">\n");
        builder.append("    <title>");
        builder.append(title);
        builder.append("</title>\n"); // NOPMD
        builder.append("  </head>\n");
        builder.append("  <body>\n");
        return builder.toString();
    }

    /**
     * This method is public for testing purposes only. Do
     * not try to call it outside of the context of the rendering
     * engine.
     *
     * @return trailer HTML with no omissions.
     */
    public final String getTrailerHtml() {
        return getTrailerHtml("");
    }

    /**
     * This method is public for testing purposes only. Do
     * not try to call it outside of the context of the rendering
     * engine.
     *
     * @param omit title of section to leave out.
     * @return trailer HTML
     */
    public final String getTrailerHtml(final String omit) {
        final java.util.Date javaDate = new java.util.Date();
        @SuppressWarnings("PMD.LawOfDemeter")
        final String timeString = DateFormat.getDateInstance(DateFormat.LONG,
                Locale.getDefault()).format(javaDate);

        final StringBuilder builder = new StringBuilder(769);
        builder.append("\n    <hr class=\"final\"/>"); // NOPMD
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
        builder.append("\n    </p>"); // NOPMD
        builder.append("\n    <hr class=\"final\"/>");
        builder.append("\n    <table class=\"buttonrow\">");
        builder.append("\n    <tr class=\"buttonrow\">");
        builder.append("\n    <td class=\"brleft\">");
        builder.append("\n    <p class=\"maintainer\">");
        builder.append("\n    Maintained by <a href=\"mailto:");
        builder.append(DICK_EMAIL);
        builder.append("\">Dick Schoeller</a>.<br>"); // NOPMD
        builder.append("\n    Created with <a href=\"");
        builder.append(GEDBROWSER_URL);
        builder.append("\">GEDbrowser</a>, version ");
        builder.append(GedObject.VERSION);
        builder.append(" on ");
        builder.append(timeString);
        builder.append("\n    </p>"); // NOPMD
        builder.append("\n    </td>");
        builder.append("\n    <td class=\"brright\">");
        builder.append("\n    <p class=\"maintainer\">");
        builder.append("\n<a href=\"http://validator.w3.org/check/referer\">");
        builder.append("<img src=\"/gedbrowser/valid-html401.gif\" ");
        builder.append("class=\"button\" alt=\"[ Valid HTML 4.01! ]\" ");
        builder.append("height=\"31\" width=\"88\"></a>");
        builder.append("\n    </p>");
        builder.append("\n    </td>");
        builder.append("\n    </tr>");
        builder.append("\n    </table>");
        builder.append("\n    <p>");
        builder.append("\n  </body>");
        builder.append("\n</html>\n");
        return builder.toString();
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
    @SuppressWarnings("PMD.LawOfDemeter")
    protected final GedRenderer<? extends GedObject> createGedRenderer(
            final GedObject attribute) {
        return getRendererFactory().create(attribute,
                getRenderingContext());
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
     * @param separate
     *            whether to return the separator.
     * @return the separator
     */
    public final String getSeparator(final boolean separate) {
        if (separate) {
            return ", ";
        } else {
            return "";
        }
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
     * This method is public for testing purposes only. Do
     * not try to call it outside of the context of the rendering
     * engine.
     *
     * @return the renderer.
     */
    public final ListItemRenderer getListItemRenderer() {
        return this.listItemRenderer;
    }

    /**
     * Set the renderer to something new.
     *
     * @param sectionRenderer
     *            the new renderer.
     */
    protected final void setSectionRenderer(
            final SectionRenderer sectionRenderer) {
        this.sectionRenderer = sectionRenderer;
    }

    /**
     * This method is public for testing purposes only. Do
     * not try to call it outside of the context of the rendering
     * engine.
     *
     * @return the renderer.
     */
    public final SectionRenderer getSectionRenderer() {
        return this.sectionRenderer;
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
     * This method is public for testing purposes only. Do
     * not try to call it outside of the context of the rendering
     * engine.
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
     * Convert the string for use in HTML or URLs.
     *
     * @param input unescaped string.
     * @return the escaped string.
     */
    @SuppressWarnings("PMD.LawOfDemeter")
    protected static final String escapeString(final String input) {
        return input.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;").replaceAll("\n", "<br/>\n");
    }

    /**
     * Convert the string for use in HTML or URLs.
     *
     * @param gedObject the object whose string we are going to escape.
     * @return the escaped string.
     */
    protected static final String escapeString(final GedObject gedObject) {
        return escapeString(gedObject.getString());
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
     * @return the standard URL for home.
     */
    public final String getHomeUrl() {
        // TODO externalize this.
        return "http://www.schoellerfamily.org/";
    }

    /**
     * @return the user context we are rendering in
     */
    protected final RenderingContext getRenderingContext() {
        return renderingContext;
    }

    /**
     * @return user's first name
     */
    public final String getUserFirstname() {
        return renderingContext.getFirstname();
    }

    /**
     * Check if the user in the rendering context has a particular role.
     *
     * @param role role that we are looking for
     * @return true if the user has the role
     */
    public final boolean hasRole(final String role) {
        return renderingContext.hasRole(role);
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
     * @param gedObject the GED object
     * @return the database name
     */
    private String getDbName(final G gedObject) {
        return gedObject.getDbName();
    }
}
