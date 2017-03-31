package org.schoellerfamily.gedbrowser.renderer;

import java.text.DateFormat;
import java.util.Locale;

/**
 * @author Dick Schoeller
 */
public interface Renderer {
    /**
     * @return the application name.
     */
    String getName();

    /**
     * @return the application URL.
     */
    String getApplicationURL();

    /**
     * @return the standard URL for home.
     */
    String getHomeUrl();

    /**
     * @return the standard URL for home.
     */
    String getMaintainerEmail();

    /**
     * @return the maintainer's name
     */
    String getMaintainerName();

    /**
     * @return the version string
     */
    String getVersion();

    /**
     * @param separate whether to return the separator.
     * @return the separator
     */
    default String getSeparator(final boolean separate) {
        if (separate) {
            return ", ";
        } else {
            return "";
        }
    }

    /**
     * Get the HTML header for a page. <p> This method is public for testing
     * purposes only. Do not try to call it outside of the context of the
     * rendering engine.
     * @param title the title string.
     * @param keywords any keywords.
     * @return a string containing the HTML header.
     */
    default String getHeaderHtml(final String title, final String keywords) {
        final StringBuilder builder = new StringBuilder(585);
        builder.append("Content-type: text/html\n\n");
        builder.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\"\n");
        builder.append("  \"http://www.w3.org/TR/html4/strict.dtd\">\n");
        builder.append("<html>\n");
        builder.append("  <head>\n");
        appendMetaTags(keywords, builder);
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
     * NOT INTENDED FOR OVERRIDE. JUST REDUCING COMPLEXITY.
     *
     * @param keywords keywords for this page
     * @param builder the builder being appended to
     */
    default void appendMetaTags(final String keywords,
            final StringBuilder builder) {
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
    }

    /**
     * This method is public for testing purposes only. Do not try to call it
     * outside of the context of the rendering engine.
     *
     * @return trailer HTML with no omissions.
     */
    default String getTrailerHtml() {
        return getTrailerHtml("");
    }

    /**
     * This method is public for testing purposes only. Do not try to call it
     * outside of the context of the rendering engine.
     *
     * @param omit title of section to leave out.
     * @return trailer HTML
     */
    default String getTrailerHtml(final String omit) {
        final StringBuilder builder = new StringBuilder(1000);
        builder.append("\n    <hr class=\"final\"/>");
        menuInsertions(omit, builder);
        builder.append("\n    <hr class=\"final\"/>");
        builder.append("\n    <table class=\"buttonrow\">");
        builder.append("\n    <tr class=\"buttonrow\">");
        builder.append("\n    <td class=\"brleft\">");
        appendApplicationInfo(builder);
        builder.append("\n    </td>");
        builder.append("\n    <td class=\"brright\">");
        appendValidator(builder);
        builder.append("\n    </td>");
        builder.append("\n    </tr>");
        builder.append("\n    </table>");
        builder.append("\n    <p>");
        builder.append("\n  </body>");
        builder.append("\n</html>\n");
        return builder.toString();
    }

    /**
     * NOT INTENDED FOR OVERRIDE. JUST REDUCING COMPLEXITY.
     *
     * @param builder the builder being appended to
     */
    default void appendApplicationInfo(final StringBuilder builder) {
        final java.util.Date javaDate = new java.util.Date();
        final String timeString = DateFormat.getDateInstance(DateFormat.LONG,
                Locale.getDefault()).format(javaDate);
        builder.append("\n    <p class=\"maintainer\">");
        builder.append("\n    Maintained by <a href=\"mailto:");
        builder.append(getMaintainerEmail());
        builder.append("\">");
        builder.append(getMaintainerName());
        builder.append("</a>.<br>");
        builder.append("\n    Created with <a href=\"");
        builder.append(getHomeUrl());
        builder.append("software/gedbrowser.html");
        builder.append("\">GEDbrowser</a>, version ");
        builder.append(getVersion());
        builder.append(" on ");
        builder.append(timeString);
        builder.append("\n    </p>");
    }

    /**
     * NOT INTENDED FOR OVERRIDE. JUST REDUCING COMPLEXITY.
     *
     * @param builder the builder being appended to
     */
    default void appendValidator(final StringBuilder builder) {
        builder.append("\n    <p class=\"maintainer\">");
        builder.append("\n<a href=\"http://validator.w3.org/check/referer\">");
        builder.append("<img src=\"/gedbrowser/valid-html401.gif\" ");
        builder.append("class=\"button\" alt=\"[ Valid HTML 4.01! ]\" ");
        builder.append("height=\"31\" width=\"88\"></a>");
        builder.append("\n    </p>");
    }

    /**
     * Insert menu items specific to context.
     *
     * @param omit if true don't add them
     * @param builder the builder that will contain the items
     */
    default void menuInsertions(String omit, StringBuilder builder) {
        // Empty default
    }
}
