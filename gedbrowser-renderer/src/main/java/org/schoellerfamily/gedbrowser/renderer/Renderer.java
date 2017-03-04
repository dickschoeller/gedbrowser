package org.schoellerfamily.gedbrowser.renderer;

import java.text.DateFormat;
import java.util.Locale;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;

/**
 * Some base rendering behaviors outside of the context of GedObject rendering.
 * This provides methods that can be used in the context of error pages, etc.
 *
 * @author Dick Schoeller
 */
@SuppressWarnings({ "PMD.CommentSize" })
public abstract class Renderer {

    /** */
    private final RenderingContext renderingContext;

    /**
     * Constructor.
     *
     * @param renderingContext the context that we are rendering in
     */
    public Renderer(final RenderingContext renderingContext) {
        this.renderingContext = renderingContext;
    }

    /**
     * @return the user context we are rendering in
     */
    protected final RenderingContext getRenderingContext() {
        return renderingContext;
    }

    /**
     * @return the standard URL for home.
     */
    public final String getHomeUrl() {
        return renderingContext.getHomeURL();
    }

    /**
     * @return the standard URL for home.
     */
    public final String getMaintainerEmail() {
        return renderingContext.getMaintainerEmail();
    }

    /**
     * @return the maintainer's name
     */
    public final String getMaintainerName() {
        return renderingContext.getMaintainerName();
    }

    /**
     * @return the version string
     */
    public final String getVersion() {
        return renderingContext.getVersion();
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
     * @param separate whether to return the separator.
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
     * Convert the string for use in HTML or URLs.
     *
     * @param input unescaped string.
     * @return the escaped string.
     */
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
     * Get the HTML header for a page.
     *
     * This method is public for testing purposes only. Do not try to call it
     * outside of the context of the rendering engine.
     *
     * @param title the title string.
     * @param keywords any keywords.
     * @return a string containing the HTML header.
     */
    public final String getHeaderHtml(final String title,
            final String keywords) {
        final StringBuilder builder = new StringBuilder(585);
        builder.append("Content-type: text/html\n\n");
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
        builder.append("\">\n");
        builder.append("    <meta http-equiv=\"Content-Style-Type\" ");
        builder.append("content=\"text/css\">\n");
        builder.append("    <link href=\"/gedbrowser/gedbrowser.css\" ");
        builder.append("rel=\"stylesheet\" type=\"text/css\">\n");
        builder.append("    <title>");
        builder.append(title);
        builder.append("</title>\n");
        builder.append("  </head>\n");
        builder.append("  <body>\n");
        return builder.toString();
    }

    /**
     * This method is public for testing purposes only. Do not try to call it
     * outside of the context of the rendering engine.
     *
     * @return trailer HTML with no omissions.
     */
    public final String getTrailerHtml() {
        return getTrailerHtml("");
    }

    /**
     * This method is public for testing purposes only. Do not try to call it
     * outside of the context of the rendering engine.
     *
     * @param omit title of section to leave out.
     * @return trailer HTML
     */
    public final String getTrailerHtml(final String omit) {
        final java.util.Date javaDate = new java.util.Date();
        final String timeString = DateFormat.getDateInstance(DateFormat.LONG,
                Locale.getDefault()).format(javaDate);

        final StringBuilder builder = new StringBuilder(769);
        builder.append("\n    <hr class=\"final\"/>");
        menuInsertions(omit, builder);
        builder.append("\n    <hr class=\"final\"/>");
        builder.append("\n    <table class=\"buttonrow\">");
        builder.append("\n    <tr class=\"buttonrow\">");
        builder.append("\n    <td class=\"brleft\">");
        builder.append("\n    <p class=\"maintainer\">");
        builder.append("\n    Maintained by <a href=\"mailto:");
        builder.append(getMaintainerEmail());
        builder.append("\">").append(getMaintainerName()).append("</a>.<br>");
        builder.append("\n    Created with <a href=\"");
        builder.append(getHomeUrl()).append("software/gedbrowser.html");
        builder.append("\">GEDbrowser</a>, version ");
        builder.append(getVersion());
        builder.append(" on ");
        builder.append(timeString);
        builder.append("\n    </p>");
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
     * Insert menu items specific to context.
     *
     * @param omit if true don't add them
     * @param builder the builder that will contain the items
     */
    protected abstract void menuInsertions(String omit, StringBuilder builder);
}
