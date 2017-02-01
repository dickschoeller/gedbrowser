package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.analytics.CalendarProvider;
import org.schoellerfamily.gedbrowser.analytics.CalendarProviderStub;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.renderer.DefaultRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.NullListItemRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullNameHtmlRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullNameIndexRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullPhraseRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullSectionRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.SimpleAttributeListOpenRenderer;

/**
 * @author Dick Schoeller
 */
public final class GedRendererTest {
    /** */
    private CalendarProvider provider;

    /** */
    @Before
    public void init() {
        provider = new CalendarProviderStub();
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testAttributeListOpenRenderer() {
        final GedRenderer<GedObject> renderer = new GedRenderer<GedObject>(
                new GedObject(null) {
                },
                new GedRendererFactory(),
                RenderingContext.anonymous(), provider) {
        };
        assertTrue("Wrong renderer type",
                renderer.getAttributeListOpenRenderer()
                instanceof SimpleAttributeListOpenRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testListItemRenderer() {
        final GedRenderer<GedObject> renderer = new GedRenderer<GedObject>(
                new GedObject(null) {
                },
                new GedRendererFactory(),
                RenderingContext.anonymous(), provider) {
        };
        assertTrue("Wrong renderer type",
                renderer.getListItemRenderer()
                instanceof NullListItemRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testNameHtmlRenderer() {
        final GedRenderer<GedObject> renderer = new GedRenderer<GedObject>(
                new GedObject(null) {
                },
                new GedRendererFactory(),
                RenderingContext.anonymous(), provider) {
        };
        assertTrue("Wrong renderer type",
                renderer.getNameHtmlRenderer()
                instanceof NullNameHtmlRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testNameIndexRenderer() {
        final GedRenderer<GedObject> renderer = new GedRenderer<GedObject>(
                new GedObject(null) {
                },
                new GedRendererFactory(),
                RenderingContext.anonymous(), provider) {
        };
        assertTrue("Wrong renderer type",
                renderer.getNameIndexRenderer()
                instanceof NullNameIndexRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testPhraseRenderer() {
        final GedRenderer<GedObject> renderer = new GedRenderer<GedObject>(
                new GedObject(null) {
                },
                new GedRendererFactory(),
                RenderingContext.anonymous(), provider) {
        };
        assertTrue("Wrong renderer type",
                renderer.getPhraseRenderer()
                instanceof NullPhraseRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testSectionRenderer() {
        final GedRenderer<GedObject> renderer = new GedRenderer<GedObject>(
                new GedObject(null) {
                },
                new GedRendererFactory(),
                RenderingContext.anonymous(), provider) {
        };
        assertTrue("Wrong renderer type",
                renderer.getSectionRenderer()
                instanceof NullSectionRenderer);
    }

    /** */
    @Test
    public void testGetTrailerHtmlEmpty() {
        final Root root = new Root(null);
        final GedRenderer<GedObject> renderer = new GedRenderer<GedObject>(
                root,
                new GedRendererFactory(),
                RenderingContext.anonymous(), provider) {
        };
        assertEquals("Rendered string does not match expectation",
                "\n"
                + "    <hr class=\"final\"/>\n"
                + "    <p>\n"
                + "    </p>\n"
                + "    <hr class=\"final\"/>\n"
                + "    <table class=\"buttonrow\">\n"
                + "    <tr class=\"buttonrow\">\n"
                + "    <td class=\"brleft\">\n"
                + "    <p class=\"maintainer\">\n"
                + "    Maintained by <a href=\"mailto:schoeller@comcast.net\">"
                + "Dick Schoeller</a>.<br>\n"
                + "    Created with <a href=\"http://www.schoellerfamily.org/"
                + "softwarwe/gedbrowser.html\">GEDbrowser</a>, version "
                + GedObject.VERSION
                + " on " + getDateString() + "\n"
                + "    </p>\n"
                + "    </td>\n"
                + "    <td class=\"brright\">\n"
                + "    <p class=\"maintainer\">\n"
                + "<a href=\"http://validator.w3.org/check/referer\">"
                + "<img src=\"/gedbrowser/valid-html401.gif\" "
                + "class=\"button\" alt=\"[ Valid HTML 4.01! ]\" "
                + "height=\"31\" width=\"88\"></a>\n"
                + "    </p>\n"
                + "    </td>\n"
                + "    </tr>\n"
                + "    </table>\n"
                + "    <p>\n"
                + "  </body>\n"
                + "</html>\n",
                renderer.getTrailerHtml(""));
    }

    /** */
    @Test
    public void testGetHeaderHtml() {
        final Root root = new Root(null);
        final GedRenderer<GedObject> renderer = new DefaultRenderer(root,
                new GedRendererFactory(),
                RenderingContext.anonymous(), provider);

        final String keywords = "one two three";
        final String title = "title";
        final String testString = "Content-type: text/html\n\n"
                + "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\"\n"
                + "  \"http://www.w3.org/TR/html4/strict.dtd\">\n"
                + "<html>\n"
                + "  <head>\n"
                + "    <meta http-equiv=\"Content-Type\" "
                + "content=\"text/html; charset=utf-8\">\n"
                + "    <meta name=\"Author\" "
                + "content=\"gedbrowser\">\n"
                + "    <meta name=\"Description\" "
                + "content=\"genealogy\">\n"
                + "    <meta name=\"Keywords\" "
                + "content=\"genealogy gedbrowser " + keywords + "\">\n"
                + "    <meta http-equiv=\"Content-Style-Type\" "
                + "content=\"text/css\">\n"
                + "    <link href=\"/gedbrowser/gedbrowser.css\" "
                + "rel=\"stylesheet\" type=\"text/css\">\n"
                + "    <title>" + title + "</title>\n"
                + "  </head>\n"
                + "  <body>\n";
        assertEquals("Rendered string does not match expectation",
                testString, renderer.getHeaderHtml(title, keywords));
    }

    /** */
    @Test
    public void testGetTrailerHtml() {
        final Root root = new Root(null);
        final GedRenderer<GedObject> renderer = new DefaultRenderer(root,
                new GedRendererFactory(),
                RenderingContext.anonymous(), provider);
        assertEquals("Rendered string does not match expectation",
                "\n"
                + "    <hr class=\"final\"/>\n"
                + "    <p>\n"
                + "    </p>\n"
                + "    <hr class=\"final\"/>\n"
                + "    <table class=\"buttonrow\">\n"
                + "    <tr class=\"buttonrow\">\n"
                + "    <td class=\"brleft\">\n"
                + "    <p class=\"maintainer\">\n"
                + "    Maintained by <a href=\"mailto:schoeller@comcast.net\">"
                + "Dick Schoeller</a>.<br>\n"
                + "    Created with <a href=\"http://www.schoellerfamily.org/"
                + "softwarwe/gedbrowser.html\">GEDbrowser</a>, version "
                + GedObject.VERSION
                + " on " + getDateString() + "\n"
                + "    </p>\n"
                + "    </td>\n"
                + "    <td class=\"brright\">\n"
                + "    <p class=\"maintainer\">\n"
                + "<a href=\"http://validator.w3.org/check/referer\">"
                + "<img src=\"/gedbrowser/valid-html401.gif\" "
                + "class=\"button\" alt=\"[ Valid HTML 4.01! ]\" "
                + "height=\"31\" width=\"88\"></a>\n"
                + "    </p>\n"
                + "    </td>\n"
                + "    </tr>\n"
                + "    </table>\n"
                + "    <p>\n"
                + "  </body>\n"
                + "</html>\n",
                renderer.getTrailerHtml());
    }

    /** */
    @Test
    public void testGetTrailerHtmlHeader() {
        final Root root = new Root(null);
        final GedRenderer<GedObject> renderer = new DefaultRenderer(root,
                new GedRendererFactory(),
                RenderingContext.anonymous(), provider);
        assertEquals("Rendered string does not match expectation",
                "\n"
                + "    <hr class=\"final\"/>\n"
                + "    <p>\n"
                + "    <a href=\"?null+Header\">Header</a><br>\n"
                + "    </p>\n"
                + "    <hr class=\"final\"/>\n"
                + "    <table class=\"buttonrow\">\n"
                + "    <tr class=\"buttonrow\">\n"
                + "    <td class=\"brleft\">\n"
                + "    <p class=\"maintainer\">\n"
                + "    Maintained by <a href=\"mailto:schoeller@comcast.net\">"
                + "Dick Schoeller</a>.<br>\n"
                + "    Created with <a href=\"http://www.schoellerfamily.org/"
                + "softwarwe/gedbrowser.html\">GEDbrowser</a>, version "
                + GedObject.VERSION
                + " on " + getDateString() + "\n"
                + "    </p>\n"
                + "    </td>\n"
                + "    <td class=\"brright\">\n"
                + "    <p class=\"maintainer\">\n"
                + "<a href=\"http://validator.w3.org/check/referer\">"
                + "<img src=\"/gedbrowser/valid-html401.gif\" "
                + "class=\"button\" alt=\"[ Valid HTML 4.01! ]\" "
                + "height=\"31\" width=\"88\"></a>\n"
                + "    </p>\n"
                + "    </td>\n"
                + "    </tr>\n"
                + "    </table>\n"
                + "    <p>\n"
                + "  </body>\n"
                + "</html>\n",
                renderer.getTrailerHtml("Header"));
    }

    /** */
    @Test
    public void testGetTrailerHtmlSurnames() {
        final Root root = new Root(null);
        final GedRenderer<GedObject> renderer = new DefaultRenderer(root,
                new GedRendererFactory(),
                RenderingContext.anonymous(), provider);
        assertEquals("Rendered string does not match expectation",
                "\n"
                + "    <hr class=\"final\"/>\n"
                + "    <p>\n"
                + "    <a href=\"?" + root.getFilename()
                + "+Surnames\">Surnames</a><br>\n"
                + "    </p>\n"
                + "    <hr class=\"final\"/>\n"
                + "    <table class=\"buttonrow\">\n"
                + "    <tr class=\"buttonrow\">\n"
                + "    <td class=\"brleft\">\n"
                + "    <p class=\"maintainer\">\n"
                + "    Maintained by <a href=\"mailto:schoeller@comcast.net\">"
                + "Dick Schoeller</a>.<br>\n"
                + "    Created with <a href=\"http://www.schoellerfamily.org/"
                + "softwarwe/gedbrowser.html\">GEDbrowser</a>, version "
                + GedObject.VERSION
                + " on " + getDateString() + "\n"
                + "    </p>\n"
                + "    </td>\n"
                + "    <td class=\"brright\">\n"
                + "    <p class=\"maintainer\">\n"
                + "<a href=\"http://validator.w3.org/check/referer\">"
                + "<img src=\"/gedbrowser/valid-html401.gif\" "
                + "class=\"button\" alt=\"[ Valid HTML 4.01! ]\" "
                + "height=\"31\" width=\"88\"></a>\n"
                + "    </p>\n"
                + "    </td>\n"
                + "    </tr>\n"
                + "    </table>\n"
                + "    <p>\n"
                + "  </body>\n"
                + "</html>\n",
                renderer.getTrailerHtml("Surnames"));
    }

    /** */
    @Test
    public void testGetTrailerHtmlIndex() {
        final Root root = new Root(null);
        final GedRenderer<GedObject> renderer = new DefaultRenderer(root,
                new GedRendererFactory(),
                RenderingContext.anonymous(), provider);
        assertEquals("Rendered string does not match expectation",
                "\n"
                + "    <hr class=\"final\"/>\n"
                + "    <p>\n"
                + "    <a href=\"?" + root.getFilename()
                + "+Index\">Index</a><br>\n"
                + "    </p>\n"
                + "    <hr class=\"final\"/>\n"
                + "    <table class=\"buttonrow\">\n"
                + "    <tr class=\"buttonrow\">\n"
                + "    <td class=\"brleft\">\n"
                + "    <p class=\"maintainer\">\n"
                + "    Maintained by <a href=\"mailto:schoeller@comcast.net\">"
                + "Dick Schoeller</a>.<br>\n"
                + "    Created with <a href=\"http://www.schoellerfamily.org/"
                + "softwarwe/gedbrowser.html\">GEDbrowser</a>, version "
                + GedObject.VERSION
                + " on " + getDateString() + "\n"
                + "    </p>\n"
                + "    </td>\n"
                + "    <td class=\"brright\">\n"
                + "    <p class=\"maintainer\">\n"
                + "<a href=\"http://validator.w3.org/check/referer\">"
                + "<img src=\"/gedbrowser/valid-html401.gif\" "
                + "class=\"button\" alt=\"[ Valid HTML 4.01! ]\" "
                + "height=\"31\" width=\"88\"></a>\n"
                + "    </p>\n"
                + "    </td>\n"
                + "    </tr>\n"
                + "    </table>\n"
                + "    <p>\n"
                + "  </body>\n"
                + "</html>\n",
                renderer.getTrailerHtml("Index"));
    }

    /**
     * Test the home URL. Always www.schoellerfamily.org at this point.
     */
    @Test
    public void testGetHomeUrl() {
        final GedRenderer<GedObject> renderer = new GedRenderer<GedObject>(
                new GedObject(null) {
                },
                new GedRendererFactory(),
                RenderingContext.anonymous(), provider) {
        };
        assertEquals("Home URL does not match expectation",
                "http://www.schoellerfamily.org/", renderer.getHomeUrl());
    }

    /**
     * Get today as a date string. This emulates what happens in the renderers.
     *
     * @return the date string.
     */
    private static String getDateString() {
        final java.util.Date javaDate = new java.util.Date();
        final String timeString = DateFormat.getDateInstance(DateFormat.LONG,
                Locale.getDefault()).format(javaDate);
        return timeString;
    }
}
