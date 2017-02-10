package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.analytics.CalendarProvider;
import org.schoellerfamily.gedbrowser.analytics.CalendarProviderStub;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Multimedia;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.renderer.ApplicationInfo;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.MultimediaListItemRenderer;
import org.schoellerfamily.gedbrowser.renderer.MultimediaRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;

/**
 * @author Dick Schoeller
 */
public final class MultimediaListItemRendererTest {
    /** */
    private transient Multimedia multimedia1;

    /** */
    private transient Multimedia multimedia2;

    /** */
    private transient Multimedia multimedia3;

    /** */
    private CalendarProvider provider;

    /** */
    private RenderingContext anonymousContext;

    /** */
    @Before
    public void init() {
        final Person person = new Person();

        multimedia1 = new Multimedia(person, "Multimedia", "");
        multimedia1.addAttribute(
                new Attribute(multimedia1, "Title", "Title 1"));
        final Attribute file1 =
                new Attribute(multimedia1, "File", "file1.jpg");
        multimedia1.addAttribute(file1);
        file1.addAttribute(new Attribute(file1, "Format", "jpg"));

        multimedia2 = new Multimedia(person, "Multimedia", "");
        multimedia2.addAttribute(
                new Attribute(multimedia2, "Title", "Title 2"));
        final Attribute file2 =
                new Attribute(multimedia2, "File", "file2.html");
        multimedia2.addAttribute(file2);
        file2.addAttribute(new Attribute(file2, "Format", "html"));

        multimedia3 = new Multimedia(person, "Multimedia", "");
        multimedia3.addAttribute(
                new Attribute(multimedia3, "Title", "Title 3"));
        final Attribute file3 =
                new Attribute(multimedia3, "File", "file3.html");
        multimedia3.addAttribute(file3);

        person.insert(multimedia1);
        person.insert(multimedia2);
        person.insert(multimedia3);

        provider = new CalendarProviderStub();
        final ApplicationInfo appInfo = new ApplicationInfoStub();
        anonymousContext = RenderingContext.anonymous(appInfo);
    }

    /** */
    @Test
    public void testRenderAsListItemEmpty() {
        final MultimediaRenderer aRenderer = new MultimediaRenderer(multimedia1,
                new GedRendererFactory(), anonymousContext, provider);
        final MultimediaListItemRenderer apr =
                (MultimediaListItemRenderer) aRenderer.getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        apr.renderAsListItem(builder, false, 0);
        assertEquals("Rendered string mismatch",
                "<li><span class=\"label\">Multimedia:</span> Title 1<br/>\n"
                + "<a href=\"file1.jpg\">"
                + "<img height=\"300px\" src=\"file1.jpg\" title=\"Title 1\"/>"
                + "</a></li>\n",
                builder.toString());
    }

    /** */
    @Test
    public void testRenderAsListItemString() {
        final MultimediaRenderer aRenderer = new MultimediaRenderer(multimedia2,
                new GedRendererFactory(), anonymousContext, provider);
        final MultimediaListItemRenderer apr =
                (MultimediaListItemRenderer) aRenderer.getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        apr.renderAsListItem(builder, false, 2);
        assertEquals("Rendered string mismatch",
                "<li><span class=\"label\">Multimedia:</span> "
                + "<a href=\"file2.html\">Title 2</a></li>\n",
                builder.toString());
    }

    /** */
    @Test
    public void testRenderAsListItem() {
        final MultimediaRenderer aRenderer = new MultimediaRenderer(multimedia3,
                new GedRendererFactory(), anonymousContext, provider);
        final MultimediaListItemRenderer apr =
                (MultimediaListItemRenderer) aRenderer .getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        apr.renderAsListItem(builder, true, 0);
        assertEquals("Rendered string mismatch",
                "<li><span class=\"label\">Multimedia:</span>"
                + " <a href=\"file3.html\">Title 3</a></li>\n",
                builder.toString());
    }
}
