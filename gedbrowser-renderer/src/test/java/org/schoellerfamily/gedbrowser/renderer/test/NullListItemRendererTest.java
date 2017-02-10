package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.analytics.CalendarProvider;
import org.schoellerfamily.gedbrowser.analytics.CalendarProviderStub;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.renderer.ApplicationInfo;
import org.schoellerfamily.gedbrowser.renderer.DefaultRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.NullListItemRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;

/**
 * @author Dick Schoeller
 */
public final class NullListItemRendererTest {
    /** */
    private transient NullListItemRenderer nsr;

    /** */
    @Before
    public void init() {
        final CalendarProvider provider = new CalendarProviderStub();
        final ApplicationInfo appInfo = new ApplicationInfoStub();
        final RenderingContext anonymousContext =
                RenderingContext.anonymous(appInfo);
        final DefaultRenderer renderer = new DefaultRenderer(
                new GedObject(null) {
                }, new GedRendererFactory(), anonymousContext, provider);
        nsr = (NullListItemRenderer) renderer
                .getListItemRenderer();
    }

    /** */
    @Test
    public void testRenderAsListItemFalse0() {
        final StringBuilder builder = new StringBuilder();
        final String string =
                nsr.renderAsListItem(builder, false, 0).toString();
        assertEquals("Expected empty string", "", string);
    }

    /** */
    @Test
    public void testRenderAsListItemFalse2() {
        final StringBuilder builder = new StringBuilder();
        final String string =
                nsr.renderAsListItem(builder, false, 2).toString();
        assertEquals("Expected empty string", "", string);
    }

    /** */
    @Test
    public void testRenderAsListItemTrue0() {
        final StringBuilder builder = new StringBuilder();
        final String string =
                nsr.renderAsListItem(builder, true, 0).toString();
        assertEquals("Expected empty string", "", string);
    }

    /** */
    @Test
    public void testRenderAsListItemTrue2() {
        final StringBuilder builder = new StringBuilder();
        final String string =
                nsr.renderAsListItem(builder, true, 2).toString();
        assertEquals("Expected empty string", "", string);
    }
}
