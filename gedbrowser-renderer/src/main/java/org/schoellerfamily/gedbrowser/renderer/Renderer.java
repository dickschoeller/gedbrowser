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
        return "Content-type: text/html\n\n"
                + "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\"\n"
                + "  \"http://www.w3.org/TR/html4/strict.dtd\">\n"
                + "<html>\n  <head>\n"
                + "    <meta http-equiv=\"Content-Type\" "
                + "content=\"text/html; charset=utf-8\">\n"
                + "    <meta name=\"Author\" content=\"gedbrowser\">\n"
                + "    <meta name=\"Description\" content=\"genealogy\">\n"
                + "    <meta name=\"Keywords\" content=\"genealogy gedbrowser "
                + keywords + "\">\n"
                + "    <meta http-equiv=\"Content-Style-Type\" "
                + "content=\"text/css\">\n"
                + "    <link href=\"/gedbrowser/gedbrowser.css\" "
                + "rel=\"stylesheet\" type=\"text/css\">\n"
                + "    <title>" + title + "</title>\n"
                + "  </head>\n  <body>\n";
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
        final java.util.Date javaDate = new java.util.Date();
        final String timeString = DateFormat.getDateInstance(DateFormat.LONG,
                Locale.getDefault()).format(javaDate);
        final StringBuilder builder = new StringBuilder();
        menuInsertions(omit, builder);
        final String retVal =
        "\n    <hr class=\"final\"/>"
        + builder.toString()
        + "\n    <hr class=\"final\"/>"
        + "\n    <table class=\"buttonrow\">"
        + "\n    <tr class=\"buttonrow\">\n    <td class=\"brleft\">"
        + "\n    <p class=\"maintainer\">\n    Maintained by <a href=\"mailto:"
        + getMaintainerEmail() + "\">" + getMaintainerName()
        + "</a>.<br>\n    Created with <a href=\"" + getHomeUrl()
        + "software/gedbrowser.html"
        + "\">GEDbrowser</a>, version " + getVersion() + " on " + timeString
        + "\n    </p>\n    </td>\n    <td class=\"brright\">"
        + "\n    <p class=\"maintainer\">"
        + "\n<a href=\"http://validator.w3.org/check/referer\">"
        + "<img src=\"/gedbrowser/valid-html401.gif\" "
        + "class=\"button\" alt=\"[ Valid HTML 4.01! ]\" "
        + "height=\"31\" width=\"88\"></a>"
        + "\n    </p>\n    </td>\n    </tr>\n    </table>\n    <p>\n  </body>"
        + "\n</html>\n";
        return retVal;
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
