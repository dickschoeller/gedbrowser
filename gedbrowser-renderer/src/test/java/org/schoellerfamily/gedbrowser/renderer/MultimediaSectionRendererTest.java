package org.schoellerfamily.gedbrowser.renderer;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Multimedia;
import org.schoellerfamily.gedbrowser.datamodel.Person;

/**
 * @author Dick Schoeller
 */
public class MultimediaSectionRendererTest {
    /** */
    private transient Multimedia multimedia1;

    /** */
    private transient Multimedia multimedia2;

    /** */
    private transient Multimedia multimedia3;

    /** */
    @Before
    public final void init() {
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
    }

    /** */
    @Test
    public final void testRenderAsSectionEmpty() {
        final String expect =
                "<p><span class=\"label\">Multimedia:</span> </p>\n<ul>\n"
                + "<li><span class=\"label\">Title:</span> Title 1</li>\n"
                + "<li><span class=\"label\">File:</span> file1.jpg,"
                + " jpg</li>\n</ul>\n";

        final MultimediaRenderer aRenderer = new MultimediaRenderer(
                multimedia1, new GedRendererFactory(),
                RenderingContext.anonymous());
        final MultimediaSectionRenderer asRenderer =
                (MultimediaSectionRenderer) aRenderer.getSectionRenderer();
        StringBuilder builder = new StringBuilder();
        builder = asRenderer.renderAsSection(builder, aRenderer, false, 0, 1);
        final String string = builder.toString();
        assertEquals("Unexpected value", expect, string);
    }

    /** */
    @Test
    public final void testRenderAsSectionString() {
        final String expect =
                "<p><span class=\"label\">Multimedia:</span> </p>\n<ul>\n"
                + "<li><span class=\"label\">Title:</span> Title 2</li>\n"
                + "<li><span class=\"label\">File:</span> file2.html,"
                + " html</li>\n</ul>\n";

        final MultimediaRenderer aRenderer = new MultimediaRenderer(
                multimedia2, new GedRendererFactory(),
                RenderingContext.anonymous());
        final MultimediaSectionRenderer asRenderer =
                (MultimediaSectionRenderer) aRenderer.getSectionRenderer();
        StringBuilder builder = new StringBuilder();
        builder = asRenderer.renderAsSection(builder, aRenderer, false, 0, 1);
        final String string = builder.toString();
        assertEquals("Unexpected value", expect, string);
    }

    /** */
    @Test
    public final void testRenderAsSection() {
        final String expect =
                "<p><span class=\"label\">Multimedia:</span> </p>\n<ul>\n"
                + "<li><span class=\"label\">Title:</span> Title 3</li>\n"
                + "<li><span class=\"label\">File:</span> file3.html</li>\n"
                + "</ul>\n";

        final MultimediaRenderer aRenderer =
                new MultimediaRenderer(multimedia3,
                        new GedRendererFactory(),
                        RenderingContext.anonymous());
        final MultimediaSectionRenderer asRenderer =
                (MultimediaSectionRenderer) aRenderer.getSectionRenderer();
        StringBuilder builder = new StringBuilder();
        builder = asRenderer.renderAsSection(builder, aRenderer, false, 0, 1);
        final String string = builder.toString();
        assertEquals("Unexpected value", expect, string);
    }
}
