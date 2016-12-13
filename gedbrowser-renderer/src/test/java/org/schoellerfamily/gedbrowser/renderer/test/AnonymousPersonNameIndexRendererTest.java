package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.PersonNameIndexRenderer;
import org.schoellerfamily.gedbrowser.renderer.PersonRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;

/**
 * @author Dick Schoeller
 */
public class AnonymousPersonNameIndexRendererTest {
    /** */
    private transient Person person;

    /** */
    private transient RenderingContext anonymousContext;

    /** */
    @Before
    public final void init() {
        final Root root = new Root(null, "root");
        person = new Person(root, new ObjectId("I1"));
        root.insert(person);
        anonymousContext = RenderingContext.anonymous();
    }

    /** */
    @Test
    public final void testGetNameHtmlNull() {
        final Name name = new Name(person);
        person.addAttribute(name);
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(), anonymousContext);
        final PersonNameIndexRenderer pnhr =
                (PersonNameIndexRenderer) personRenderer.getNameIndexRenderer();
        assertEquals("Expected Living", "Living", pnhr.getIndexName());
    }

    /** */
    @Test
    public final void testGetNameHtmlEmpty() {
        final Name name = new Name(person, "");
        person.addAttribute(name);
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(), anonymousContext);
        final PersonNameIndexRenderer pnhr =
                (PersonNameIndexRenderer) personRenderer.getNameIndexRenderer();
        assertEquals("Expected Living", "Living", pnhr.getIndexName());
    }

    /** */
    @Test
    public final void testGetNameHtmlSurnameOnly() {
        final Name name = new Name(person, "/Schoeller/");
        person.addAttribute(name);
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(), anonymousContext);
        final PersonNameIndexRenderer pnhr =
                (PersonNameIndexRenderer) personRenderer.getNameIndexRenderer();
        assertEquals("Expected Living", "Living", pnhr.getIndexName());
    }

    /** */
    @Test
    public final void testGetNameHtmlSurnameLast() {
        final Name name = new Name(person, "Richard/Schoeller/");
        person.addAttribute(name);
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(), anonymousContext);
        final PersonNameIndexRenderer pnhr =
                (PersonNameIndexRenderer) personRenderer.getNameIndexRenderer();
        assertEquals("Expected Living", "Living", pnhr.getIndexName());
    }

    /** */
    @Test
    public final void testGetNameHtmlSurnameFirst() {
        final Name name = new Name(person, "/Deng/Shao Ping");
        person.addAttribute(name);
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(), anonymousContext);
        final PersonNameIndexRenderer pnhr =
                (PersonNameIndexRenderer) personRenderer.getNameIndexRenderer();
        assertEquals("Expected Living", "Living", pnhr.getIndexName());
    }

    /** */
    @Test
    public final void testGetNameHtmlSurnameMiddle() {
        final Name name = new Name(person, "Karl Frederick/Schoeller/Sr.");
        person.addAttribute(name);
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(), anonymousContext);
        final PersonNameIndexRenderer pnhr =
                (PersonNameIndexRenderer) personRenderer.getNameIndexRenderer();
        assertEquals("Expected Living", "Living", pnhr.getIndexName());
    }

    /** */
    @Test
    public final void testGetNameHtmlPersonUnset() {
        final PersonRenderer personRenderer = new PersonRenderer(new Person(),
                new GedRendererFactory(), anonymousContext);
        final PersonNameIndexRenderer pnhr =
                (PersonNameIndexRenderer) personRenderer.getNameIndexRenderer();
        assertEquals("Expected empty", "", pnhr.getIndexName());
    }


}
