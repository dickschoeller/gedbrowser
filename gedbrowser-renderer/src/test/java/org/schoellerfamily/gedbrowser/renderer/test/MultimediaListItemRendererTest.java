package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Multimedia;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.MultimediaListItemRenderer;
import org.schoellerfamily.gedbrowser.renderer.MultimediaRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;



/**
 * Contains tests for multimedia list item renderer.
 *
 * @author Richard Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
final class MultimediaListItemRendererTest {
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    private transient Multimedia multimedia1;

    /** */
    private transient Multimedia multimedia2;

    /** */
    private transient Multimedia multimedia3;

    /** */
    private RenderingContext anonymousContext;

    @BeforeEach
    void setUp() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson();

        multimedia1 = new Multimedia(person, "Multimedia", "");
        multimedia1.addAttribute(new Attribute(multimedia1, "Title", "Title 1"));
        final Attribute file1 = new Attribute(multimedia1, "File", "file1.jpg");
        multimedia1.addAttribute(file1);
        file1.addAttribute(new Attribute(file1, "Format", "jpg"));

        multimedia2 = new Multimedia(person, "Multimedia", "");
        multimedia2.addAttribute(new Attribute(multimedia2, "Title", "Title 2"));
        final Attribute file2 = new Attribute(multimedia2, "File", "file2.html");
        multimedia2.addAttribute(file2);
        file2.addAttribute(new Attribute(file2, "Format", "html"));

        multimedia3 = new Multimedia(person, "Multimedia", "");
        multimedia3.addAttribute(new Attribute(multimedia3, "Title", "Title 3"));
        final Attribute file3 = new Attribute(multimedia3, "File", "file3.html");
        multimedia3.addAttribute(file3);

        person.insert(multimedia1);
        person.insert(multimedia2);
        person.insert(multimedia3);

        anonymousContext = RenderingContext.anonymous(appInfo);
    }

    @Test
    void testRenderAsListItemEmpty() {
        @SuppressWarnings("java:S6126")
        final String expected = "<li><span class=\"label\">Multimedia:</span> Title 1<br/>\n"
            + "<a href=\"file1.jpg\">"
            + "<img height=\"300px\" src=\"file1.jpg\" title=\"Title 1\"/>" + "</a></li>\n";
        final MultimediaRenderer aRenderer = new MultimediaRenderer(multimedia1,
            new GedRendererFactory(), anonymousContext);
        final MultimediaListItemRenderer apr = (MultimediaListItemRenderer) aRenderer
            .getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        apr.renderAsListItem(builder, false, 0);
        assertEquals(expected, builder.toString(), "Rendered string mismatch");
    }

    @Test
    void testRenderAsListItemString() {
        @SuppressWarnings("java:S6126")
        final String expected = "<li><span class=\"label\">Multimedia:</span> "
            + "<a href=\"file2.html\">Title 2</a></li>\n";
        final MultimediaRenderer aRenderer = new MultimediaRenderer(multimedia2,
            new GedRendererFactory(), anonymousContext);
        final MultimediaListItemRenderer apr = (MultimediaListItemRenderer) aRenderer
            .getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        apr.renderAsListItem(builder, false, 2);
        assertEquals(expected, builder.toString(), "Rendered string mismatch");
    }

    @Test
    void testRenderAsListItem() {
        @SuppressWarnings("java:S6126")
        final String expected = "<li><span class=\"label\">Multimedia:</span>"
            + " <a href=\"file3.html\">Title 3</a></li>\n";
        final MultimediaRenderer aRenderer = new MultimediaRenderer(multimedia3,
            new GedRendererFactory(), anonymousContext);
        final MultimediaListItemRenderer apr = (MultimediaListItemRenderer) aRenderer
            .getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        apr.renderAsListItem(builder, true, 0);
        assertEquals(expected, builder.toString(), "Rendered string mismatch");
    }

    @Test
    void testGetListItemContentsEmpty() {
        final String expected =
            "<span class=\"label\">Multimedia:</span> Title 1<br/>\n<a href=\"file1.jpg\">"
            + "<img height=\"300px\" src=\"file1.jpg\" title=\"Title 1\"/>" + "</a>";
        final MultimediaRenderer aRenderer = new MultimediaRenderer(multimedia1,
            new GedRendererFactory(), anonymousContext);
        final MultimediaListItemRenderer apr = (MultimediaListItemRenderer) aRenderer
            .getListItemRenderer();
        final String contents = apr.getListItemContents();
        assertEquals(expected, contents, "Rendered string mismatch");
    }

    @Test
    void testGetListItemContentsString() {
        final String expected =
            "<span class=\"label\">Multimedia:</span> <a href=\"file2.html\">Title 2</a>";
        final MultimediaRenderer aRenderer = new MultimediaRenderer(multimedia2,
            new GedRendererFactory(), anonymousContext);
        final MultimediaListItemRenderer apr = (MultimediaListItemRenderer) aRenderer
            .getListItemRenderer();
        final String contents = apr.getListItemContents();
        assertEquals(expected, contents, "Rendered string mismatch");
    }

    @Test
    void testGetListItemContents() {
        final String expected =
            "<span class=\"label\">Multimedia:</span> <a href=\"file3.html\">Title 3</a>";
        final MultimediaRenderer aRenderer = new MultimediaRenderer(multimedia3,
            new GedRendererFactory(), anonymousContext);
        final MultimediaListItemRenderer apr = (MultimediaListItemRenderer) aRenderer
            .getListItemRenderer();
        final String contents = apr.getListItemContents();
        assertEquals(expected, contents, "Rendered string mismatch");
    }
}
