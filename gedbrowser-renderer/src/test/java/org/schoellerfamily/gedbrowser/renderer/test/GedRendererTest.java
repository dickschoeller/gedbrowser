package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.DateFormat;
import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.analytics.calendar.CalendarProvider;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.users.UserImpl;
import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;
import org.schoellerfamily.gedbrowser.renderer.DefaultRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.NullListItemRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullNameHtmlRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullNameIndexRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullPhraseRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.SimpleAttributeListOpenRenderer;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;



/**
 * Contains tests for ged renderer.
 *
 * @author Richard Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
final class GedRendererTest {
    /** */
    @Autowired
    private transient CalendarProvider provider;
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    private String homeUrl;

    /** */
    private RenderingContext anonymousContext;

    @BeforeEach
    void setUp() {
        homeUrl = "http://www.schoellerfamily.org/";
        anonymousContext = RenderingContext.anonymous(appInfo);
    }

    @Test
    void testAttributeListOpenRenderer() {
        final GedRenderer<GedObject> renderer = createRenderer();
        assertTrue(
            renderer.getAttributeListOpenRenderer() instanceof SimpleAttributeListOpenRenderer,
            "Wrong renderer type");
    }

    @Test
    void testListItemRenderer() {
        final GedRenderer<GedObject> renderer = createRenderer();
        assertTrue(renderer.getListItemRenderer() instanceof NullListItemRenderer,
            "Wrong renderer type");
    }

    @Test
    void testNameHtmlRenderer() {
        final GedRenderer<GedObject> renderer = createRenderer();
        assertTrue(renderer.getNameHtmlRenderer() instanceof NullNameHtmlRenderer,
            "Wrong renderer type");
    }

    @Test
    void testNameIndexRenderer() {
        final GedRenderer<GedObject> renderer = createRenderer();
        assertTrue(renderer.getNameIndexRenderer() instanceof NullNameIndexRenderer,
            "Wrong renderer type");
    }

    @Test
    void testPhraseRenderer() {
        final GedRenderer<GedObject> renderer = createRenderer();
        assertTrue(renderer.getPhraseRenderer() instanceof NullPhraseRenderer,
            "Wrong renderer type");
    }

    private GedRenderer<GedObject> createRenderer() {
        return new GedRenderer<GedObject>(createGedObject(), new GedRendererFactory(),
            anonymousContext) {
        };
    }

    @Test
    void testGetTrailerHtmlEmpty() {
        final Root root = new Root();
        final GedRenderer<GedObject> renderer = new GedRenderer<GedObject>(root,
            new GedRendererFactory(), anonymousContext) {
        };
        assertEquals(
            "\n" + "    <hr class=\"final\"/>\n" + "    <p>\n" + "    </p>\n"
                + "    <hr class=\"final\"/>\n" + "    <table class=\"buttonrow\">\n"
                + "    <tr class=\"buttonrow\">\n" + "    <td class=\"brleft\">\n"
                + "    <p class=\"maintainer\">\n"
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

    @Test
    void testGetHeaderHtml() {
        final Root root = new Root();
        final GedRenderer<GedObject> renderer = new DefaultRenderer(root, new GedRendererFactory(),
            anonymousContext);

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

    @Test
    void testGetTrailerHtml() {
        final Root root = new Root();
        final GedRenderer<GedObject> renderer = new DefaultRenderer(root, new GedRendererFactory(),
            anonymousContext);
        assertEquals(
            "\n" + "    <hr class=\"final\"/>\n" + "    <p>\n" + "    </p>\n"
                + "    <hr class=\"final\"/>\n" + "    <table class=\"buttonrow\">\n"
                + "    <tr class=\"buttonrow\">\n" + "    <td class=\"brleft\">\n"
                + "    <p class=\"maintainer\">\n"
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

    @Test
    void testHasRoleAdminFalse() {
        final Root root = new Root();
        final GedRenderer<GedObject> renderer = new DefaultRenderer(root, new GedRendererFactory(),
            anonymousContext);
        assertFalse(renderer.hasRole("ADMIN"), "Expected admin to be false");
    }

    @Test
    void testHasRoleUserTrue() {
        final Root root = new Root();
        final ApplicationInfo appInfo1 = appInfo;
        final UserImpl user2 = new UserImpl();
        user2.setUsername("User");
        user2.setFirstname("Ursula");
        user2.setLastname("User");
        user2.clearRoles();
        user2.addRole("USER");
        final RenderingContext context = new RenderingContext(user2, appInfo1, provider);
        final GedRenderer<GedObject> renderer = new DefaultRenderer(root, new GedRendererFactory(),
            context);
        assertTrue(renderer.hasRole("USER"), "Expected user to be true");
    }

    @Test
    void testHasRoleUserFalse() {
        final Root root = new Root();
        final GedRenderer<GedObject> renderer = new DefaultRenderer(root, new GedRendererFactory(),
            anonymousContext);
        assertFalse(renderer.hasRole("USER"), "Expected user to be false");
    }

    @Test
    void testUserGetFirstname() {
        final Root root = new Root();
        final GedRenderer<GedObject> renderer = new DefaultRenderer(root, new GedRendererFactory(),
            RenderingContext.user(appInfo));
        assertEquals("Ursula", renderer.getUserFirstname(), "Expected user to be Ursula");
    }

    @Test
    void testGetTrailerHtmlHeader() {
        final Root root = new Root();
        final GedRenderer<GedObject> renderer = new DefaultRenderer(root, new GedRendererFactory(),
            anonymousContext);
        assertEquals(
            "\n" + "    <hr class=\"final\"/>\n" + "    <p>\n"
                + "    <a href=\"?null+Header\">Header</a><br>\n" + "    </p>\n"
                + "    <hr class=\"final\"/>\n" + "    <table class=\"buttonrow\">\n"
                + "    <tr class=\"buttonrow\">\n" + "    <td class=\"brleft\">\n"
                + "    <p class=\"maintainer\">\n"
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
            renderer.getTrailerHtml("Header"), "Rendered string does not match expectation");
    }

    @Test
    void testGetTrailerHtmlSurnames() {
        final Root root = new Root();
        final GedRenderer<GedObject> renderer = new DefaultRenderer(root, new GedRendererFactory(),
            anonymousContext);
        assertEquals(
            "\n" + "    <hr class=\"final\"/>\n" + "    <p>\n" + "    <a href=\"?"
                + root.getFilename() + "+Surnames\">Surnames</a><br>\n" + "    </p>\n"
                + "    <hr class=\"final\"/>\n" + "    <table class=\"buttonrow\">\n"
                + "    <tr class=\"buttonrow\">\n" + "    <td class=\"brleft\">\n"
                + "    <p class=\"maintainer\">\n"
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
            renderer.getTrailerHtml("Surnames"), "Rendered string does not match expectation");
    }

    @Test
    void testGetTrailerHtmlIndex() {
        final Root root = new Root();
        final GedRenderer<GedObject> renderer = new DefaultRenderer(root, new GedRendererFactory(),
            anonymousContext);
        assertEquals(
            "\n" + "    <hr class=\"final\"/>\n" + "    <p>\n" + "    <a href=\"?"
                + root.getFilename() + "+Index\">Index</a><br>\n" + "    </p>\n"
                + "    <hr class=\"final\"/>\n" + "    <table class=\"buttonrow\">\n"
                + "    <tr class=\"buttonrow\">\n" + "    <td class=\"brleft\">\n"
                + "    <p class=\"maintainer\">\n"
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

    @Test
    void testGetHomeUrl() {
        final GedRenderer<GedObject> renderer = createRenderer();
        assertEquals(homeUrl, renderer.getHomeUrl(), "Home URL does not match expectation");
    }

    @Test
    void testGetLivingHref() {
        final GedRenderer<GedObject> renderer = createRenderer();
        assertEquals("living?db=null", renderer.getLivingHref(),
            "Living href does not match expectation");
    }

    @Test
    void testGetCalendarProvider() {
        final GedRenderer<GedObject> renderer = createRenderer();
        final CalendarProvider cp = renderer.getRenderingContext();
        final String nowString = cp.nowDate().toString("YYYYMMdd");
        assertEquals("20151214", nowString, "Should match fixed past date for testing");
    }

    private GedObject createGedObject() {
        return new GedObject() {
            /**
             * Executes accept.
             *
             * @param visitor the visitor
             */
            @Override
            public void accept(final GedObjectVisitor visitor) {
                visitor.visit(this);
            }
        };
    }

    private static String getDateString() {
        final java.util.Date javaDate = new java.util.Date();
        return DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault()).format(javaDate);
    }
}
