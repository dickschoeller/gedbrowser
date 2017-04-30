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
import org.schoellerfamily.gedbrowser.renderer.PersonNameHtmlRenderer;
import org.schoellerfamily.gedbrowser.renderer.PersonRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * All of these tests should return Living. The reason is that the user created
 * is one who shows up living in the test. Therefore, the renderer will punt, no
 * matter what the input string.
 *
 * There is a separate set of tests with a logged in user. Those should get real
 * rendering.
 *
 * @author Dick Schoeller
 */
@SuppressWarnings("PMD.CommentSize")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public final class AnonymousModePersonNameHtmlRendererTest {
    /** */
    @Autowired
    private transient CalendarProvider provider;
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    private transient Person person;

    /** */
    private transient RenderingContext renderingContext;

    /** */
    @Before
    public void init() {
        final Root root = new Root("Root");
        person = new Person(root, new ObjectId("I1"));
        root.insert(person);
        renderingContext = RenderingContext.anonymous(appInfo);
    }

    /** */
    @Test
    public void testGetNameHtmlNull() {
        final Name name = new Name(person);
        person.addAttribute(name);
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(), renderingContext, provider);
        final PersonNameHtmlRenderer pnhr =
                (PersonNameHtmlRenderer) personRenderer.getNameHtmlRenderer();
        assertEquals("Expected Living", "Living", pnhr.getNameHtml());
    }

    /** */
    @Test
    public void testGetNameHtmlEmpty() {
        final Name name = new Name(person, "");
        person.addAttribute(name);
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(), renderingContext, provider);
        final PersonNameHtmlRenderer pnhr =
                (PersonNameHtmlRenderer) personRenderer.getNameHtmlRenderer();
        assertEquals("Expected Living", "Living", pnhr.getNameHtml());
    }

    /** */
    @Test
    public void testGetNameHtmlSurnameOnly() {
        final Name name = new Name(person, "/Schoeller/");
        person.addAttribute(name);
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(), renderingContext, provider);
        final PersonNameHtmlRenderer pnhr =
                (PersonNameHtmlRenderer) personRenderer.getNameHtmlRenderer();
        assertEquals("Expected Living", "Living", pnhr.getNameHtml());
    }

    /** */
    @Test
    public void testGetNameHtmlSurnameLast() {
        final Name name = new Name(person, "Richard/Schoeller/");
        person.addAttribute(name);
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(), renderingContext, provider);
        final PersonNameHtmlRenderer pnhr =
                (PersonNameHtmlRenderer) personRenderer.getNameHtmlRenderer();
        assertEquals("Expected Living", "Living", pnhr.getNameHtml());
    }

    /** */
    @Test
    public void testGetNameHtmlSurnameFirst() {
        final Name name = new Name(person, "/Deng/Shao Ping");
        person.addAttribute(name);
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(), renderingContext, provider);
        final PersonNameHtmlRenderer pnhr =
                (PersonNameHtmlRenderer) personRenderer.getNameHtmlRenderer();
        assertEquals("Expected Living", "Living", pnhr.getNameHtml());
    }

    /** */
    @Test
    public void testGetNameHtmlSurnameMiddle() {
        final Name name = new Name(person, "Karl Frederick/Schoeller/Sr.");
        person.addAttribute(name);
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(), renderingContext, provider);
        final PersonNameHtmlRenderer pnhr =
                (PersonNameHtmlRenderer) personRenderer.getNameHtmlRenderer();
        assertEquals("Expected Living", "Living", pnhr.getNameHtml());
    }

    /** */
    @Test
    public void testGetNameHtmlPersonUnset() {
        final PersonRenderer personRenderer = new PersonRenderer(new Person(),
                new GedRendererFactory(), renderingContext, provider);
        final PersonNameHtmlRenderer pnhr =
                (PersonNameHtmlRenderer) personRenderer.getNameHtmlRenderer();
        assertEquals("Expected empty name", "", pnhr.getNameHtml());
    }
}
