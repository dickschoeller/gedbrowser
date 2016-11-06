package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.renderer.AttributePhraseRenderer;
import org.schoellerfamily.gedbrowser.renderer.AttributeRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;

/**
 * @author Dick Schoeller
 */
public class AttributePhraseRendererTest {
    /** */
    private transient Attribute attribute1;

    /** */
    private transient Attribute attribute2;

    /** */
    private transient Attribute attribute3;

    /** */
    @Before
    public final void init() {
        final Person person = new Person();
        attribute1 = new Attribute(person, "String", "");
        attribute2 = new Attribute(person, "String", "Strung");
        attribute3 = new Attribute(person, "Sproing", "Spring");
        final Attribute attribute4 =
                new Attribute(attribute3, "Stinky", "Stanky");
        person.insert(attribute1);
        person.insert(attribute2);
        person.insert(attribute3);
        attribute3.insert(attribute4);
    }

    /** */
    @Test
    public final void testRenderAsPhraseEmpty() {
        final AttributeRenderer aRenderer =
                new AttributeRenderer(attribute1,
                        new GedRendererFactory(),
                        RenderingContext.anonymous());
        final AttributePhraseRenderer apRenderer =
                (AttributePhraseRenderer) aRenderer.getPhraseRenderer();
        final String string = apRenderer.renderAsPhrase();
        assertEquals("", string);
    }

    /** */
    @Test
    public final void testRenderAsPhraseString() {
        final AttributeRenderer aRenderer =
                new AttributeRenderer(attribute2,
                        new GedRendererFactory(),
                        RenderingContext.anonymous());
        final AttributePhraseRenderer apRenderer =
                (AttributePhraseRenderer) aRenderer.getPhraseRenderer();
        final String string = apRenderer.renderAsPhrase();
        assertEquals("Strung", string);
    }

    /** */
    @Test
    public final void testRenderAsPhrase() {
        final AttributeRenderer aRenderer =
                new AttributeRenderer(attribute3,
                        new GedRendererFactory(),
                        RenderingContext.anonymous());
        final AttributePhraseRenderer apRenderer =
                (AttributePhraseRenderer) aRenderer.getPhraseRenderer();
        final String string = apRenderer.renderAsPhrase();
        assertEquals("Spring", string);
    }
}
