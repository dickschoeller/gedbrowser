package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.analytics.CalendarProvider;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.renderer.ApplicationInfo;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.PersonNameIndexRenderer;
import org.schoellerfamily.gedbrowser.renderer.PersonRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public final class AnonymousPersonNameIndexRendererTest {
    /** */
    @Autowired
    private transient CalendarProvider provider;
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    private transient Person person;

    /** */
    private transient RenderingContext anonymousContext;

    /** */
    @Before
    public void init() {
        final Root root = new Root("Root");
        person = new Person(root, new ObjectId("I1"));
        root.insert(person);
        anonymousContext = RenderingContext.anonymous(appInfo);
    }

    /** */
    @Test
    public void testGetNameHtmlNull() {
        final Name name = new Name(person);
        person.addAttribute(name);
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(), anonymousContext, provider);
        final PersonNameIndexRenderer pnhr =
                (PersonNameIndexRenderer) personRenderer.getNameIndexRenderer();
        assertEquals("Rendered string doesn't match expectation",
                "Living", pnhr.getIndexName());
    }

    /** */
    @Test
    public void testGetNameHtmlEmpty() {
        final Name name = new Name(person, "");
        person.addAttribute(name);
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(), anonymousContext, provider);
        final PersonNameIndexRenderer pnhr =
                (PersonNameIndexRenderer) personRenderer.getNameIndexRenderer();
        assertEquals("Rendered string doesn't match expectation",
                "Living", pnhr.getIndexName());
    }

    /** */
    @Test
    public void testGetNameHtmlSurnameOnly() {
        final Name name = new Name(person, "/Schoeller/");
        person.addAttribute(name);
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(), anonymousContext, provider);
        final PersonNameIndexRenderer pnhr =
                (PersonNameIndexRenderer) personRenderer.getNameIndexRenderer();
        assertEquals("Rendered string doesn't match expectation",
                "Living", pnhr.getIndexName());
    }

    /** */
    @Test
    public void testGetNameHtmlSurnameLast() {
        final Name name = new Name(person, "Richard/Schoeller/");
        person.addAttribute(name);
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(), anonymousContext, provider);
        final PersonNameIndexRenderer pnhr =
                (PersonNameIndexRenderer) personRenderer.getNameIndexRenderer();
        assertEquals("Rendered string doesn't match expectation",
                "Living", pnhr.getIndexName());
    }

    /** */
    @Test
    public void testGetNameHtmlSurnameFirst() {
        final Name name = new Name(person, "/Deng/Shao Ping");
        person.addAttribute(name);
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(), anonymousContext, provider);
        final PersonNameIndexRenderer pnhr =
                (PersonNameIndexRenderer) personRenderer.getNameIndexRenderer();
        assertEquals("Rendered string doesn't match expectation",
                "Living", pnhr.getIndexName());
    }

    /** */
    @Test
    public void testGetNameHtmlSurnameMiddle() {
        final Name name = new Name(person, "Karl Frederick/Schoeller/Sr.");
        person.addAttribute(name);
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(), anonymousContext, provider);
        final PersonNameIndexRenderer pnhr =
                (PersonNameIndexRenderer) personRenderer.getNameIndexRenderer();
        assertEquals("Rendered string doesn't match expectation",
                "Living", pnhr.getIndexName());
    }

    /** */
    @Test
    public void testGetNameHtmlPersonUnset() {
        final PersonRenderer personRenderer = new PersonRenderer(new Person(),
                new GedRendererFactory(), anonymousContext, provider);
        final PersonNameIndexRenderer pnhr =
                (PersonNameIndexRenderer) personRenderer.getNameIndexRenderer();
        assertEquals("Expected empty string", "", pnhr.getIndexName());
    }


}
