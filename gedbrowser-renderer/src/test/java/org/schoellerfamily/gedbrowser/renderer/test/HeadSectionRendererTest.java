package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.analytics.CalendarProvider;
import org.schoellerfamily.gedbrowser.analytics.CalendarProviderStub;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.HeadRenderer;
import org.schoellerfamily.gedbrowser.renderer.HeadSectionRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.RootRenderer;

/**
 * @author Dick Schoeller
 */
public final class HeadSectionRendererTest {
    /** */
    private transient Root root;

    /** */
    private transient Head head;

    /** */
    private CalendarProvider provider;

    /** */
    @Before
    public void init() {
        root = new Root(null, "root");
        head = new Head(root);
        root.insert(head);
        root.setFilename("thefile.ged");
        root.setDbName("thefile");
        provider = new CalendarProviderStub();
    }

    // TODO the above really represents head as page, not head as section.
    // Both should be implemented.  Right now this is implemented wrong.
    // We can use TDD to get there, after getting the rest of the tests
    // right.

    /** */
    @Test
    public void testRenderAsSection() {
        final HeadRenderer hRenderer =
                new HeadRenderer(head, new GedRendererFactory(),
                        RenderingContext.anonymous(), provider);
        final StringBuilder builder = new StringBuilder();
        final HeadSectionRenderer hsr =
                (HeadSectionRenderer) hRenderer.getSectionRenderer();
        hsr.renderAsSection(builder,
                new RootRenderer(root, new GedRendererFactory(),
                        RenderingContext.anonymous(), provider), false, 0, 0);
        assertEquals("Rendered html doesn't match expectation",
                "Content-type: text/html\n"
                        + "\n"
                        + "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD"
                        + " HTML 4.01//EN\"\n"
                        + "  \"http://www.w3.org/TR/html4/strict.dtd\">\n"
                        + "<html>\n"
                        + "  <head>\n"
                        + "    <meta http-equiv=\"Content-Type\" "
                        + "content=\"text/html; "
                        + "charset=utf-8\">\n"
                        + "    <meta name=\"Author\" content=\"gedbrowser\">\n"
                        + "    <meta name=\"Description\" "
                        + "content=\"genealogy\">\n"
                        + "    <meta name=\"Keywords\" "
                        + "content=\"genealogy gedbrowser \">\n"
                        + "    <meta http-equiv=\"Content-Style-Type\" "
                        + "content=\"text/css\">\n"
                        + "    <link href=\"/gedbrowser/gedbrowser.css\" "
                        + "rel=\"stylesheet\" "
                        + "type=\"text/css\">\n"
                        + "    <title>thefile.ged</title>\n"
                        + "  </head>\n"
                        + "  <body>\n"
                        + "\n"
                        + "<h2 class=\"name\">thefile.ged</h2>\n"
                        + "    <hr class=\"final\"/>\n"
                        + "    <p>\n"
                        + "    <a href=\"?thefile+Header\">Header</a><br>\n"
                        + "    </p>\n"
                        + "    <hr class=\"final\"/>\n"
                        + "    <table class=\"buttonrow\">\n"
                        + "    <tr class=\"buttonrow\">\n"
                        + "    <td class=\"brleft\">\n"
                        + "    <p class=\"maintainer\">\n"
                        + "    Maintained by "
                        + "<a href=\"mailto:schoeller@comcast.net\">Dick "
                        + "Schoeller</a>.<br>\n"
                        + "    Created with "
                        + "<a href=\"http://www.schoellerfamily.org/"
                        + "softwarwe/gedbrowser.html\">GEDbrowser</a>, version "
                        + GedObject.VERSION + " on "
                        + getDateString() + "\n" + "    </p>\n"
                        + "    </td>\n" + "    <td class=\"brright\">\n"
                        + "    <p class=\"maintainer\">\n"
                        + "<a href=\"http://validator.w3.org/check/referer\">"
                        + "<img src=\"/gedbrowser/valid-html401.gif\" "
                        + "class=\"button\" alt=\"[ Valid HTML 4.01! ]\" "
                        + "height=\"31\" width=\"88\"></a>\n" + "    </p>\n"
                        + "    </td>\n" + "    </tr>\n" + "    </table>\n"
                        + "    <p>\n" + "  </body>\n" + "</html>\n",
                builder.toString());
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
