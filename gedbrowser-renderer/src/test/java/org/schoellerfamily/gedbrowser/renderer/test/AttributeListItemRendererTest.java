package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedbrowser.renderer.AttributeListItemRenderer;
import org.schoellerfamily.gedbrowser.renderer.AttributeRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public final class AttributeListItemRendererTest {
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

    /** */
    @BeforeEach
    public void init() {
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

    /** */
    @Test
    public void testRenderAsListItemEmpty() {
        final AttributeRenderer aRenderer = new AttributeRenderer(attribute1,
                new GedRendererFactory(), anonymousContext);
        final AttributeListItemRenderer apr =
                (AttributeListItemRenderer) aRenderer.getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        apr.renderAsListItem(builder, false, 0);
        assertEquals("<li><span class=\"label\">String:</span> </li>\n",
                builder.toString(), "Rendered html doesn't match expectation");
    }

    /** */
    @Test
    public void testRenderAsListItemString() {
        final AttributeRenderer aRenderer = new AttributeRenderer(attribute2,
                new GedRendererFactory(), anonymousContext);
        final AttributeListItemRenderer apr =
                (AttributeListItemRenderer) aRenderer.getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        apr.renderAsListItem(builder, false, 2);
        assertEquals("<li><span class=\"label\">String:</span> Strung</li>\n",
                builder.toString(), "Rendered html doesn't match expectation");
    }

    /** */
    @Test
    public void testRenderAsListItem() {
        final AttributeRenderer aRenderer = new AttributeRenderer(attribute3,
                new GedRendererFactory(), anonymousContext);
        final AttributeListItemRenderer apr =
                (AttributeListItemRenderer) aRenderer .getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        apr.renderAsListItem(builder, true, 0);
        assertEquals("<li><span class=\"label\">"
                + "Sproing:</span> Spring, Stanky</li>\n", builder.toString(),
                "Rendered html doesn't match expectation");
    }
}