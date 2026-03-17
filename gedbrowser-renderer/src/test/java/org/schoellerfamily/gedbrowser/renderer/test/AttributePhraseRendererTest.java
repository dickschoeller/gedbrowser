package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedbrowser.renderer.AttributePhraseRenderer;
import org.schoellerfamily.gedbrowser.renderer.AttributeRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;



/**
 * Contains tests for attribute phrase renderer.
 *
 * @author Richard Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
final class AttributePhraseRendererTest {
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    private transient Attribute attribute1;

    /** */
    private transient Attribute attribute2;

    /** */
    private transient Attribute attribute3;

    /** */
    private RenderingContext anonymousContext;

    @BeforeEach
    void setUp() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson();
        attribute1 = new Attribute(person, "String", "");
        attribute2 = new Attribute(person, "String", "Strung");
        attribute3 = new Attribute(person, "Sproing", "Spring");
        final Attribute attribute4 =
                new Attribute(attribute3, "Stinky", "Stanky");
        person.insert(attribute1);
        person.insert(attribute2);
        person.insert(attribute3);
        attribute3.insert(attribute4);
        anonymousContext = RenderingContext.anonymous(appInfo);
    }

    @Test
    void testRenderAsPhraseEmpty() {
        final AttributeRenderer aRenderer = new AttributeRenderer(attribute1,
                new GedRendererFactory(), anonymousContext);
        final AttributePhraseRenderer apRenderer =
                (AttributePhraseRenderer) aRenderer.getPhraseRenderer();
        final String string = apRenderer.renderAsPhrase();
        assertEquals("", string, "Expected empty string");
    }

    @Test
    void testRenderAsPhraseString() {
        final AttributeRenderer aRenderer = new AttributeRenderer(attribute2,
                new GedRendererFactory(), anonymousContext);
        final AttributePhraseRenderer apRenderer =
                (AttributePhraseRenderer) aRenderer.getPhraseRenderer();
        final String string = apRenderer.renderAsPhrase();
        assertEquals("Strung", string, "Rendered string doesn't match expectation");
    }

    @Test
    void testRenderAsPhrase() {
        final AttributeRenderer aRenderer = new AttributeRenderer(attribute3,
                new GedRendererFactory(), anonymousContext);
        final AttributePhraseRenderer apRenderer =
                (AttributePhraseRenderer) aRenderer.getPhraseRenderer();
        final String string = apRenderer.renderAsPhrase();
        assertEquals("Spring", string, "Rendered string doesn't match expectation");
    }
}
