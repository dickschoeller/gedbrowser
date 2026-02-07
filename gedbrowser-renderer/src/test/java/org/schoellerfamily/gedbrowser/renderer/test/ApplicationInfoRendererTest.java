package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.DateFormat;
import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.renderer.ApplicationInfoRenderer;
import org.schoellerfamily.gedbrowser.renderer.Renderer;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public final class ApplicationInfoRendererTest {
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    private String homeUrl;

    /**
     * Object under test.
     */
    private Renderer renderer;

    /** */
    @BeforeEach
    void setUp() {
        homeUrl = "http://www.schoellerfamily.org/";
        renderer = new ApplicationInfoRenderer(appInfo) {
        };
    }

    /** */
    @Test
    void testGetTrailerHtmlEmpty() {
        assertEquals(
            "\n" + "    <hr class=\"final\"/>\n" + "    <hr class=\"final\"/>\n"
                + "    <table class=\"buttonrow\">\n" + "    <tr class=\"buttonrow\">\n"
                + "    <td class=\"brleft\">\n" + "    <p class=\"maintainer\">\n"
                + "    Maintained by <a href=\"mailto:schoeller@comcast.net\">"
                + "Richard Schoeller</a>.<br>\n" + "    Created with <a href=\"" + homeUrl
                + "software/gedbrowser.html\">GEDbrowser</a>, version " + GedObject.VERSION + " on "
                + getDateString() + "\n" + "    </p>\n" + "    </td>\n"
                + "    <td class=\"brright\">\n" + "    <p class=\"maintainer\">\n"
                + "<a href=\"http://validator.w3.org/check/referer\">"
                + "<img src=\"/gedbrowser/valid-html401.gif\" "
                + "class=\"button\" alt=\"[ Valid HTML 4.01! ]\" "
                + "height=\"31\" width=\"88\"></a>\n" + "    </p>\n" + "    </td>\n" + "    </tr>\n"
                + "    </table>\n" + "    <p>\n" + "  </body>\n" + "</html>\n",
            renderer.getTrailerHtml(""), "Rendered string does not match expectation");
    }

    /** */
    @Test
    void testGetHeaderHtml() {
        final String keywords = "one two three";
        final String title = "title";
        final String testString = "Content-type: text/html\n\n"
            + "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\"\n"
            + "  \"http://www.w3.org/TR/html4/strict.dtd\">\n" + "<html>\n" + "  <head>\n"
            + "    <meta http-equiv=\"Content-Type\" " + "content=\"text/html; charset=utf-8\">\n"
            + "    <meta name=\"Author\" " + "content=\"gedbrowser\">\n"
            + "    <meta name=\"Description\" " + "content=\"genealogy\">\n"
            + "    <meta name=\"Keywords\" " + "content=\"genealogy gedbrowser " + keywords
            + "\">\n" + "    <meta http-equiv=\"Content-Style-Type\" " + "content=\"text/css\">\n"
            + "    <link href=\"/gedbrowser/gedbrowser.css\" "
            + "rel=\"stylesheet\" type=\"text/css\">\n" + "    <title>" + title + "</title>\n"
            + "  </head>\n" + "  <body>\n";
        assertEquals(testString, renderer.getHeaderHtml(title, keywords),
            "Rendered string does not match expectation");
    }

    /** */
    @Test
    void testGetTrailerHtml() {
        assertEquals(
            "\n" + "    <hr class=\"final\"/>\n" + "    <hr class=\"final\"/>\n"
                + "    <table class=\"buttonrow\">\n" + "    <tr class=\"buttonrow\">\n"
                + "    <td class=\"brleft\">\n" + "    <p class=\"maintainer\">\n"
                + "    Maintained by <a href=\"mailto:schoeller@comcast.net\">"
                + "Richard Schoeller</a>.<br>\n" + "    Created with <a href=\"" + homeUrl
                + "software/gedbrowser.html\">GEDbrowser</a>, version " + GedObject.VERSION + " on "
                + getDateString() + "\n" + "    </p>\n" + "    </td>\n"
                + "    <td class=\"brright\">\n" + "    <p class=\"maintainer\">\n"
                + "<a href=\"http://validator.w3.org/check/referer\">"
                + "<img src=\"/gedbrowser/valid-html401.gif\" "
                + "class=\"button\" alt=\"[ Valid HTML 4.01! ]\" "
                + "height=\"31\" width=\"88\"></a>\n" + "    </p>\n" + "    </td>\n" + "    </tr>\n"
                + "    </table>\n" + "    <p>\n" + "  </body>\n" + "</html>\n",
            renderer.getTrailerHtml(), "Rendered string does not match expectation");
    }

    /** */
    @Test
    void testGetTrailerHtmlIndex() {
        assertEquals(
            "\n" + "    <hr class=\"final\"/>\n" + "    <hr class=\"final\"/>\n"
                + "    <table class=\"buttonrow\">\n" + "    <tr class=\"buttonrow\">\n"
                + "    <td class=\"brleft\">\n" + "    <p class=\"maintainer\">\n"
                + "    Maintained by <a href=\"mailto:schoeller@comcast.net\">"
                + "Richard Schoeller</a>.<br>\n" + "    Created with <a href=\"" + homeUrl
                + "software/gedbrowser.html\">GEDbrowser</a>, version " + GedObject.VERSION + " on "
                + getDateString() + "\n" + "    </p>\n" + "    </td>\n"
                + "    <td class=\"brright\">\n" + "    <p class=\"maintainer\">\n"
                + "<a href=\"http://validator.w3.org/check/referer\">"
                + "<img src=\"/gedbrowser/valid-html401.gif\" "
                + "class=\"button\" alt=\"[ Valid HTML 4.01! ]\" "
                + "height=\"31\" width=\"88\"></a>\n" + "    </p>\n" + "    </td>\n" + "    </tr>\n"
                + "    </table>\n" + "    <p>\n" + "  </body>\n" + "</html>\n",
            renderer.getTrailerHtml("Index"), "Rendered string does not match expectation");
    }

    /** */
    @Test
    void testGetHomeUrl() {
        assertEquals(homeUrl, renderer.getHomeUrl(), "Home URL does not match expectation");
    }

    /** */
    @Test
    void testGetName() {
        assertEquals("gedbrowser", renderer.getApplicationName(),
            "Application name does not match expectation");
    }

    /** */
    @Test
    void testGetApplicationURL() {
        assertEquals("https://github.com/dickschoeller/gedbrowser", renderer.getApplicationURL(),
            "Application URL does not match expectation");
    }

    /** */
    @Test
    void testGetMaintainerEmail() {
        assertEquals("schoeller@comcast.net", renderer.getMaintainerEmail(),
            "Maintainer email does not match expectation");
    }

    /** */
    @Test
    void testGetMaintainerName() {
        assertEquals("Richard Schoeller", renderer.getMaintainerName(),
            "Maintainer email does not match expectation");
    }

    /** */
    @Test
    void testGetVersion() {
        assertEquals(GedObject.VERSION, renderer.getVersion(),
            "Version does not match expectation");
    }

    /**
     * Get today as a date string. This emulates what happens in the renderers.
     *
     * @return the date string.
     */
    private static String getDateString() {
        final java.util.Date javaDate = new java.util.Date();
        return DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault()).format(javaDate);
    }
}
