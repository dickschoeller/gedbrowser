package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Note;
import org.schoellerfamily.gedbrowser.datamodel.NoteLink;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.NoteLinkPhraseRenderer;
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
public final class NoteLinkPhraseRendererTest {
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    private transient NoteLink noteLink;

    /** */
    private RenderingContext anonymousContext;

    /** */
    @Before
    public void init() {
        /** */
        final Root root = new Root("Root");
        /** */
        final Head head = new Head(root, "Head");
        root.insert(head);

        final Note note =
                new Note(root, new ObjectId("N1"), "The text of the note");
        final Name name = new Name(note, "Richard/Schoeller/");
        root.insert(note);
        note.insert(name);

        noteLink = new NoteLink(head, "NOTE", new ObjectId("N1"));
        anonymousContext = RenderingContext.anonymous(appInfo);
    }

    /** */
    @Test
    public void testRenderAsPhrase() {
        final NoteLinkRenderer slr = new NoteLinkRenderer(
                noteLink, new GedRendererFactory(), anonymousContext);
        final NoteLinkPhraseRenderer slpr =
                (NoteLinkPhraseRenderer) slr.getPhraseRenderer();
        assertEquals("Rendered html doesn't match expectation",
                " [<a href=\"note?db=null&amp;id=N1\">N1</a>]",
                slpr.renderAsPhrase());
    }
}
