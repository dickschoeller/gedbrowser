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
import org.schoellerfamily.gedbrowser.renderer.NameHtmlRenderer;
import org.schoellerfamily.gedbrowser.renderer.NameNameHtmlRenderer;
import org.schoellerfamily.gedbrowser.renderer.NameRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public final class NameNameHtmlRendererTest {
    /** */
    @Autowired
    private transient CalendarProvider provider;
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
        final NameRenderer nameRenderer = new NameRenderer(name,
                new GedRendererFactory(), anonymousContext, provider);
        final NameHtmlRenderer nameHtmlRenderer = new NameNameHtmlRenderer(
                nameRenderer);
        assertEquals("Rendered string mismatch",
                " <span class=\"surname\">?</span>",
                nameHtmlRenderer.getNameHtml());
    }

    /** */
    @Test
    public void testGetNameHtmlEmpty() {
        final Name name = new Name(person, "");
        person.addAttribute(name);
        final NameRenderer nameRenderer = new NameRenderer(name,
                new GedRendererFactory(), anonymousContext, provider);
        final NameHtmlRenderer nameHtmlRenderer = new NameNameHtmlRenderer(
                nameRenderer);
        assertEquals("Rendered string mismatch",
                " <span class=\"surname\">?</span>",
                nameHtmlRenderer.getNameHtml());
    }

    /** */
    @Test
    public void testGetNameHtmlSurnameOnly() {
        final Name name = new Name(person, "/Schoeller/");
        person.addAttribute(name);
        final NameRenderer nameRenderer = new NameRenderer(name,
                new GedRendererFactory(), anonymousContext, provider);
        final NameHtmlRenderer nameHtmlRenderer = new NameNameHtmlRenderer(
                nameRenderer);
        assertEquals("Rendered string mismatch",
                " <span class=\"surname\">Schoeller</span>",
                nameHtmlRenderer.getNameHtml());
    }

    /** */
    @Test
    public void testGetNameHtmlSurnameLast() {
        final Name name = new Name(person, "Richard/Schoeller/");
        person.addAttribute(name);
        final NameRenderer nameRenderer = new NameRenderer(name,
                new GedRendererFactory(), anonymousContext, provider);
        final NameHtmlRenderer nameHtmlRenderer = new NameNameHtmlRenderer(
                nameRenderer);
        assertEquals("Rendered string mismatch",
                "Richard <span class=\"surname\">Schoeller</span>",
                nameHtmlRenderer.getNameHtml());
    }

    /** */
    @Test
    public void testGetNameHtmlSurnameFirst() {
        final Name name = new Name(person, "/Deng/Shao Ping");
        person.addAttribute(name);
        final NameRenderer nameRenderer = new NameRenderer(name,
                new GedRendererFactory(), anonymousContext, provider);
        final NameHtmlRenderer nameHtmlRenderer = new NameNameHtmlRenderer(
                nameRenderer);
        assertEquals("Rendered string mismatch",
                " <span class=\"surname\">Deng</span> Shao Ping",
                nameHtmlRenderer.getNameHtml());
    }

    /** */
    @Test
    public void testGetNameHtmlSurnameMiddle() {
        final Name name = new Name(person, "Karl Frederick/Schoeller/Sr.");
        person.addAttribute(name);
        final NameRenderer nameRenderer = new NameRenderer(name,
                new GedRendererFactory(), anonymousContext, provider);
        final NameHtmlRenderer nameHtmlRenderer = new NameNameHtmlRenderer(
                nameRenderer);
        assertEquals("Rendered string mismatch",
                "Karl Frederick <span class=\"surname\">Schoeller</span> Sr.",
                nameHtmlRenderer.getNameHtml());
    }

    /** */
    @Test
    public void testGetNameHtmlWeirdEmpty() {
        final Name name = new Name(person, "//");
        person.addAttribute(name);
        final NameRenderer nameRenderer = new NameRenderer(name,
                new GedRendererFactory(), anonymousContext, provider);
        final NameHtmlRenderer nameHtmlRenderer = new NameNameHtmlRenderer(
                nameRenderer);
        assertEquals("Rendered string mismatch",
                " <span class=\"surname\">?</span>",
                nameHtmlRenderer.getNameHtml());
    }
}
