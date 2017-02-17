package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.analytics.CalendarProvider;
import org.schoellerfamily.gedbrowser.analytics.CalendarProviderStub;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.renderer.ApplicationInfo;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.PersonNameHtmlRenderer;
import org.schoellerfamily.gedbrowser.renderer.PersonRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;

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
public final class AnonymousModePersonNameHtmlRendererTest {
    /** */
    private transient Person person;

    /** */
    private transient RenderingContext renderingContext;

    /** */
    private CalendarProvider provider;

    /** */
    @Before
    public void init() {
        final Root root = new Root("Root");
        person = new Person(root, new ObjectId("I1"));
        root.insert(person);
        provider = new CalendarProviderStub();
        final ApplicationInfo appInfo = new ApplicationInfoStub();
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
