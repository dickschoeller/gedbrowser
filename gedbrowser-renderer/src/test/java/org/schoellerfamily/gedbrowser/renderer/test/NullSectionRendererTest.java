package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.analytics.CalendarProvider;
import org.schoellerfamily.gedbrowser.analytics.CalendarProviderStub;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;
import org.schoellerfamily.gedbrowser.renderer.ApplicationInfo;
import org.schoellerfamily.gedbrowser.renderer.DefaultRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.NullSectionRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;

 /**
  * @author Dick Schoeller
  */
public final class NullSectionRendererTest {
    /** */
    private transient NullSectionRenderer nsr;

    /** */
    private RenderingContext anonymousContext;

    /** */
    private CalendarProvider provider;

    /** */
    @Before
    public void init() {
        provider = new CalendarProviderStub();
        final ApplicationInfo appInfo = new ApplicationInfoStub();
        anonymousContext = RenderingContext.anonymous(appInfo);
        final DefaultRenderer renderer = new DefaultRenderer(createGedObject(),
                new GedRendererFactory(), anonymousContext, provider);
        nsr = (NullSectionRenderer) renderer.getSectionRenderer();
    }

    /**
     * @return an anonymous subclass of GedObject for testing
     */
    private GedObject createGedObject() {
        return new GedObject() {
            /**
             * {@inheritDoc}
             */
            @Override
            public void accept(final GedObjectVisitor visitor) {
                visitor.visit(this);
            }
        };
    }

    /** */
    @Test
    public void testNullSectionRendererNullFalse01() {
        StringBuilder builder = new StringBuilder();
        builder = nsr.renderAsSection(builder, null, false, 0, 1);
        final String string = builder.toString();
        assertEquals("Expected empty string", "", string);
    }

    /** */
    @Test
    public void testNullSectionRendererNullTrue01() {
        StringBuilder builder = new StringBuilder();
        builder = nsr.renderAsSection(builder, null, true, 0, 1);
        final String string = builder.toString();
        assertEquals("Expected empty string", "", string);
    }

    /** */
    @Test
    public void testNullSectionRendererOuterFalse21() {
        StringBuilder builder = new StringBuilder();
        builder = nsr.renderAsSection(builder, new GedRenderer<GedObject>(null,
                new GedRendererFactory(), anonymousContext, provider) {
        }, false, 2, 1);
        final String string = builder.toString();
        assertEquals("Expected empty string", "", string);
    }

    /** */
    @Test
    public void testNullSectionRendererOuterTrue02() {
        StringBuilder builder = new StringBuilder();
        builder = nsr.renderAsSection(builder, new GedRenderer<GedObject>(null,
                new GedRendererFactory(), anonymousContext, provider) {
        }, true, 0, 2);
        final String string = builder.toString();
        assertEquals("Expected empty string", "", string);
    }
}
