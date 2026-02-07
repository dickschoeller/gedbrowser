package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.NameHtmlRenderer;
import org.schoellerfamily.gedbrowser.renderer.NameNameHtmlRenderer;
import org.schoellerfamily.gedbrowser.renderer.NameRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Dick Schoeller
 */
@ContextConfiguration(classes = { TestConfiguration.class })
@ExtendWith(SpringExtension.class)
final class NameNameHtmlRendererTest {
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    private transient Person person;

    /** */
    private RenderingContext anonymousContext;

    /** */
    @BeforeEach
    void setUp() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        person = builder.createPerson("I1");
        anonymousContext = RenderingContext.anonymous(appInfo);
    }

    /** */
    @Test
    void testGetNameHtmlNull() {
        final Name name = new Name(person);
        person.addAttribute(name);
        final NameRenderer nameRenderer = new NameRenderer(name, new GedRendererFactory(),
            anonymousContext);
        final NameHtmlRenderer nameHtmlRenderer = new NameNameHtmlRenderer(nameRenderer);
        assertEquals(" <span class=\"surname\">?</span>", nameHtmlRenderer.getNameHtml(),
            "Rendered string mismatch");
    }

    /** */
    @Test
    void testGetNameHtmlEmpty() {
        final Name name = new Name(person, "");
        person.addAttribute(name);
        final NameRenderer nameRenderer = new NameRenderer(name, new GedRendererFactory(),
            anonymousContext);
        final NameHtmlRenderer nameHtmlRenderer = new NameNameHtmlRenderer(nameRenderer);
        assertEquals(" <span class=\"surname\">?</span>", nameHtmlRenderer.getNameHtml(),
            "Rendered string mismatch");
    }

    /** */
    @Test
    void testGetNameHtmlSurnameOnly() {
        final Name name = new Name(person, "/Schoeller/");
        person.addAttribute(name);
        final NameRenderer nameRenderer = new NameRenderer(name, new GedRendererFactory(),
            anonymousContext);
        final NameHtmlRenderer nameHtmlRenderer = new NameNameHtmlRenderer(nameRenderer);
        assertEquals(" <span class=\"surname\">Schoeller</span>", nameHtmlRenderer.getNameHtml(),
            "Rendered string mismatch");
    }

    /** */
    @Test
    void testGetNameHtmlSurnameLast() {
        final Name name = new Name(person, "Richard/Schoeller/");
        person.addAttribute(name);
        final NameRenderer nameRenderer = new NameRenderer(name, new GedRendererFactory(),
            anonymousContext);
        final NameHtmlRenderer nameHtmlRenderer = new NameNameHtmlRenderer(nameRenderer);
        assertEquals("Richard <span class=\"surname\">Schoeller</span>",
            nameHtmlRenderer.getNameHtml(), "Rendered string mismatch");
    }

    /** */
    @Test
    void testGetNameHtmlSurnameFirst() {
        final Name name = new Name(person, "/Deng/Shao Ping");
        person.addAttribute(name);
        final NameRenderer nameRenderer = new NameRenderer(name, new GedRendererFactory(),
            anonymousContext);
        final NameHtmlRenderer nameHtmlRenderer = new NameNameHtmlRenderer(nameRenderer);
        assertEquals(" <span class=\"surname\">Deng</span> Shao Ping",
            nameHtmlRenderer.getNameHtml(), "Rendered string mismatch");
    }

    /** */
    @Test
    void testGetNameHtmlSurnameMiddle() {
        final Name name = new Name(person, "Karl Frederick/Schoeller/Sr.");
        person.addAttribute(name);
        final NameRenderer nameRenderer = new NameRenderer(name, new GedRendererFactory(),
            anonymousContext);
        final NameHtmlRenderer nameHtmlRenderer = new NameNameHtmlRenderer(nameRenderer);
        assertEquals("Karl Frederick <span class=\"surname\">Schoeller</span> Sr.",
            nameHtmlRenderer.getNameHtml(), "Rendered string mismatch");
    }

    /** */
    @Test
    void testGetNameHtmlWeirdEmpty() {
        final Name name = new Name(person, "//");
        person.addAttribute(name);
        final NameRenderer nameRenderer = new NameRenderer(name, new GedRendererFactory(),
            anonymousContext);
        final NameHtmlRenderer nameHtmlRenderer = new NameNameHtmlRenderer(nameRenderer);
        assertEquals(" <span class=\"surname\">?</span>", nameHtmlRenderer.getNameHtml(),
            "Rendered string mismatch");
    }
}
