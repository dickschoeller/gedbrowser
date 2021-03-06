package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Multimedia;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.MultimediaPhraseRenderer;
import org.schoellerfamily.gedbrowser.renderer.MultimediaRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public final class MultimediaPhraseRendererTest {
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

    /** */
    @Before
    public void init() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson();

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

        anonymousContext = RenderingContext.anonymous(appInfo);
    }

    /** */
    @Test
    public void testRenderAsPhraseEmpty() {
        final MultimediaRenderer aRenderer = new MultimediaRenderer(multimedia1,
                new GedRendererFactory(), anonymousContext);
        final MultimediaPhraseRenderer apRenderer =
                (MultimediaPhraseRenderer) aRenderer.getPhraseRenderer();
        final String string = apRenderer.renderAsPhrase();
        assertEquals("Rendered string mismatch",
                "<a href=\"file1.jpg\">Title 1</a>", string);
    }

    /** */
    @Test
    public void testRenderAsPhraseString() {
        final MultimediaRenderer aRenderer = new MultimediaRenderer(multimedia2,
                new GedRendererFactory(), anonymousContext);
        final MultimediaPhraseRenderer apRenderer =
                (MultimediaPhraseRenderer) aRenderer.getPhraseRenderer();
        final String string = apRenderer.renderAsPhrase();
        assertEquals("Rendered string mismatch",
                "<a href=\"file2.html\">Title 2</a>", string);
    }

    /** */
    @Test
    public void testRenderAsPhrase() {
        final MultimediaRenderer aRenderer = new MultimediaRenderer(multimedia3,
                new GedRendererFactory(), anonymousContext);
        final MultimediaPhraseRenderer apRenderer =
                (MultimediaPhraseRenderer) aRenderer.getPhraseRenderer();
        final String string = apRenderer.renderAsPhrase();
        assertEquals("Rendered string mismatch",
                "<a href=\"file3.html\">Title 3</a>", string);
    }
}
