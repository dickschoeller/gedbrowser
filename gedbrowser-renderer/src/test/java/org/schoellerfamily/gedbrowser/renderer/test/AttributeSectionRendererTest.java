package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.analytics.CalendarProvider;
import org.schoellerfamily.gedbrowser.analytics.CalendarProviderStub;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.renderer.ApplicationInfo;
import org.schoellerfamily.gedbrowser.renderer.AttributeRenderer;
import org.schoellerfamily.gedbrowser.renderer.AttributeSectionRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.PersonRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;

/**
 * @author Dick Schoeller
 */
public final class AttributeSectionRendererTest {
    /** */
    private transient Person person;

    /** */
    private transient Attribute attribute1;

    /** */
    private transient Attribute attribute2;

    /** */
    private transient Attribute attribute3;

    /** */
    private transient CalendarProvider provider;

    /** */
    private transient RenderingContext anonymousContext;

    /** */
    @Before
    public void init() {
        person = new Person();
        attribute1 = new Attribute(person, "String", "");
        attribute2 = new Attribute(person, "String", "Strung");
        attribute3 = new Attribute(person, "Sproing", "Spring");
        final Attribute attribute4 =
                new Attribute(attribute3, "Stinky", "Stanky");
        person.insert(attribute1);
        person.insert(attribute2);
        person.insert(attribute3);
        attribute3.insert(attribute4);

        provider = new CalendarProviderStub();
        final ApplicationInfo appInfo = new ApplicationInfoStub();
        anonymousContext = RenderingContext.anonymous(appInfo);
    }

    /** */
    @Test
    public void testRenderAsSectionEmpty() {
        final AttributeRenderer aRenderer = new AttributeRenderer(attribute1,
                new GedRendererFactory(), anonymousContext, provider);
        final AttributeSectionRenderer asRenderer =
                (AttributeSectionRenderer) aRenderer.getSectionRenderer();
        final StringBuilder builder = new StringBuilder();
        asRenderer.renderAsSection(builder, new PersonRenderer(person,
                new GedRendererFactory(), anonymousContext,
                provider), false, 0, 1);
        assertEquals("Rendered html doesn't match expectation",
                "<p><span class=\"label\">String:</span> </p>\n"
                + "<ul>\n</ul>\n", builder.toString());
    }

    /** */
    @Test
    public void testRenderAsSectionString() {
        final AttributeRenderer aRenderer = new AttributeRenderer(attribute2,
                new GedRendererFactory(), anonymousContext, provider);
        final AttributeSectionRenderer asRenderer =
                (AttributeSectionRenderer) aRenderer.getSectionRenderer();
        final StringBuilder builder = new StringBuilder();
        asRenderer.renderAsSection(builder, new PersonRenderer(person,
                new GedRendererFactory(), anonymousContext,
                provider), false, 0, 1);
        assertEquals("Rendered html doesn't match expectation",
                "<p><span class=\"label\">String:</span> Strung</p>\n"
                + "<ul>\n</ul>\n", builder.toString());
    }

    /** */
    @Test
    public void testRenderAsSectionHierarchy() {
        final AttributeRenderer aRenderer = new AttributeRenderer(attribute3,
                new GedRendererFactory(), anonymousContext, provider);
        final AttributeSectionRenderer asRenderer =
                (AttributeSectionRenderer) aRenderer.getSectionRenderer();
        final StringBuilder builder = new StringBuilder();
        asRenderer.renderAsSection(builder, new PersonRenderer(person,
                new GedRendererFactory(), anonymousContext,
                provider), false, 0, 1);
        assertEquals("Rendered html doesn't match expectation",
                "<p><span class=\"label\">Sproing:</span> Spring</p>\n"
                + "<ul>\n"
                + "<li><span class=\"label\">Stinky:</span> Stanky</li>\n"
                + "</ul>\n", builder.toString());
    }
}
