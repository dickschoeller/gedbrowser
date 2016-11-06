package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.NameHtmlRenderer;
import org.schoellerfamily.gedbrowser.renderer.NameNameHtmlRenderer;
import org.schoellerfamily.gedbrowser.renderer.NameRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;

/**
 * @author Dick Schoeller
 */
public class NameNameHtmlRendererTest {
    /** */
    private transient Person person;

    /** */
    @Before
    public final void init() {
        final Root root = new Root(null, "root");
        person = new Person(root, new ObjectId("I1"));
        root.insert(person);
    }

    /** */
    @Test
    public final void testGetNameHtmlNull() {
        final Name name = new Name(person);
        person.addAttribute(name);
        final NameRenderer nameRenderer = new NameRenderer(name,
                new GedRendererFactory(),
                RenderingContext.anonymous());
        final NameHtmlRenderer nameHtmlRenderer = new NameNameHtmlRenderer(
                nameRenderer);
        assertEquals(" <span class=\"surname\">?</span>",
                nameHtmlRenderer.getNameHtml());
    }

    /** */
    @Test
    public final void testGetNameHtmlEmpty() {
        final Name name = new Name(person, "");
        person.addAttribute(name);
        final NameRenderer nameRenderer = new NameRenderer(name,
                new GedRendererFactory(),
                RenderingContext.anonymous());
        final NameHtmlRenderer nameHtmlRenderer = new NameNameHtmlRenderer(
                nameRenderer);
        assertEquals(" <span class=\"surname\">?</span>",
                nameHtmlRenderer.getNameHtml());
    }

    /** */
    @Test
    public final void testGetNameHtmlSurnameOnly() {
        final Name name = new Name(person, "/Schoeller/");
        person.addAttribute(name);
        final NameRenderer nameRenderer = new NameRenderer(name,
                new GedRendererFactory(),
                RenderingContext.anonymous());
        final NameHtmlRenderer nameHtmlRenderer = new NameNameHtmlRenderer(
                nameRenderer);
        assertEquals(" <span class=\"surname\">Schoeller</span>",
                nameHtmlRenderer.getNameHtml());
    }

    /** */
    @Test
    public final void testGetNameHtmlSurnameLast() {
        final Name name = new Name(person, "Richard/Schoeller/");
        person.addAttribute(name);
        final NameRenderer nameRenderer = new NameRenderer(name,
                new GedRendererFactory(),
                RenderingContext.anonymous());
        final NameHtmlRenderer nameHtmlRenderer = new NameNameHtmlRenderer(
                nameRenderer);
        assertEquals("Richard <span class=\"surname\">Schoeller</span>",
                nameHtmlRenderer.getNameHtml());
    }

    /** */
    @Test
    public final void testGetNameHtmlSurnameFirst() {
        final Name name = new Name(person, "/Deng/Shao Ping");
        person.addAttribute(name);
        final NameRenderer nameRenderer = new NameRenderer(name,
                new GedRendererFactory(),
                RenderingContext.anonymous());
        final NameHtmlRenderer nameHtmlRenderer = new NameNameHtmlRenderer(
                nameRenderer);
        assertEquals(" <span class=\"surname\">Deng</span> Shao Ping",
                nameHtmlRenderer.getNameHtml());
    }

    /** */
    @Test
    public final void testGetNameHtmlSurnameMiddle() {
        final Name name = new Name(person, "Karl Frederick/Schoeller/Sr.");
        person.addAttribute(name);
        final NameRenderer nameRenderer = new NameRenderer(name,
                new GedRendererFactory(),
                RenderingContext.anonymous());
        final NameHtmlRenderer nameHtmlRenderer = new NameNameHtmlRenderer(
                nameRenderer);
        assertEquals(
                "Karl Frederick <span class=\"surname\">Schoeller</span> Sr.",
                nameHtmlRenderer.getNameHtml());
    }

    /** */
    @Test
    public final void testGetNameHtmlWeirdEmpty() {
        final Name name = new Name(person, "//");
        person.addAttribute(name);
        final NameRenderer nameRenderer = new NameRenderer(name,
                new GedRendererFactory(),
                RenderingContext.anonymous());
        final NameHtmlRenderer nameHtmlRenderer = new NameNameHtmlRenderer(
                nameRenderer);
        assertEquals(" <span class=\"surname\">?</span>",
                nameHtmlRenderer.getNameHtml());
    }
}
