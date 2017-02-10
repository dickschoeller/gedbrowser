package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.analytics.CalendarProviderStub;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.renderer.ApplicationInfo;
import org.schoellerfamily.gedbrowser.renderer.DefaultRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.NullPhraseRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;

/**
 * @author Dick Schoeller
 */
public final class NullPhraseRendererTest {
    /** */
    private transient NullPhraseRenderer npr;

    /** */
    @Before
    public void init() {
        final ApplicationInfo appInfo = new ApplicationInfoStub();
        final DefaultRenderer renderer = new DefaultRenderer(new GedObject(null,
                "THIS IS A STRING") {
        }, new GedRendererFactory(),
        RenderingContext.anonymous(appInfo),
        new CalendarProviderStub());
        npr = (NullPhraseRenderer) renderer.getPhraseRenderer();
    }

    /** */
    @Test
    public void testRenderAsPhrase() {
        assertEquals("Expected empty string", "", npr.renderAsPhrase());
    }
}
