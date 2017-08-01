package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Note;
import org.schoellerfamily.gedbrowser.datamodel.NoteLink;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.NoteLinkListItemRenderer;
import org.schoellerfamily.gedbrowser.renderer.NoteLinkRenderer;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public final class NoteLinkListItemRendererTest {
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    private transient Person person;

    /** */
    private RenderingContext anonymousContext;

    /** */
    @Before
    public void init() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        person = builder.createPerson("I1");
        final Root root = builder.getRoot();
        final Note note1 = new Note(root, new ObjectId("N1"));
        root.insert(note1);
        final Note note2 =
                new Note(root, new ObjectId("N2"), "The title of N2");

        root.insert(note2);
        anonymousContext = RenderingContext.anonymous(appInfo);
    }

    /** */
    @Test
    public void testRenderAsListItem() {
        final NoteLink noteLink =
                new NoteLink(person, "NOTE", new ObjectId("N1"));
        person.addAttribute(noteLink);
        final NoteLinkRenderer slRenderer = new NoteLinkRenderer(noteLink,
                new GedRendererFactory(), anonymousContext);
        final NoteLinkListItemRenderer lir =
                (NoteLinkListItemRenderer) slRenderer
                .getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        lir.renderAsListItem(builder, false, 0);
        assertEquals("Rendered html doesn't match expectation",
                "<span class=\"label\">Note:</span> <a href=\"note?db=null"
                + "&amp;id=N1\" class=\"name\" id=\"note-N1\"> (N1)</a>",
                builder.toString());
    }

    /** */
    @Test
    public void testRenderAsListItemWithTitle() {
        final NoteLink sourceLink =
                new NoteLink(person, "NOTE", new ObjectId("N2"));
        person.addAttribute(sourceLink);
        final NoteLinkRenderer slRenderer = new NoteLinkRenderer(sourceLink,
                new GedRendererFactory(), anonymousContext);
        final NoteLinkListItemRenderer lir =
                (NoteLinkListItemRenderer) slRenderer
                .getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        lir.renderAsListItem(builder, false, 0);
        assertEquals("Rendered html doesn't match expectation",
                "<span class=\"label\">Note:</span>"
                + " <a href=\"note?db=null&amp;id=N2\""
                + " class=\"name\" id=\"note-N2\">The title of N2 (N2)</a>",
                builder.toString());
    }
}
