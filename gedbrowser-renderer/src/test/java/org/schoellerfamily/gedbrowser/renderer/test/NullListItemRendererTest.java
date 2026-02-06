package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;
import org.schoellerfamily.gedbrowser.renderer.DefaultRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.NullListItemRenderer;
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
final class NullListItemRendererTest {
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    private transient NullListItemRenderer nsr;

    /** */
    @BeforeEach
    void setUp() {
        final RenderingContext anonymousContext = RenderingContext.anonymous(appInfo);
        final DefaultRenderer renderer = new DefaultRenderer(createGedObject(),
            new GedRendererFactory(), anonymousContext);
        nsr = (NullListItemRenderer) renderer.getListItemRenderer();
    }

    /**
     * @return an anonymous subclass of GedObject for testing
     */
    private GedObject createGedObject() {
        return new GedObject() {
            @Override
            public void accept(final GedObjectVisitor visitor) {
                visitor.visit(this);
            }
        };
    }

    /** */
    @Test
    void testRenderAsListItemFalse0() {
        final StringBuilder builder = new StringBuilder();
        final String string = nsr.renderAsListItem(builder, false, 0).toString();
        assertEquals("", string, "Expected empty string");
    }

    /** */
    @Test
    void testRenderAsListItemFalse2() {
        final StringBuilder builder = new StringBuilder();
        final String string = nsr.renderAsListItem(builder, false, 2).toString();
        assertEquals("", string, "Expected empty string");
    }

    /** */
    @Test
    void testRenderAsListItemTrue0() {
        final StringBuilder builder = new StringBuilder();
        final String string = nsr.renderAsListItem(builder, true, 0).toString();
        assertEquals("", string, "Expected empty string");
    }

    /** */
    @Test
    void testRenderAsListItemTrue2() {
        final StringBuilder builder = new StringBuilder();
        final String string = nsr.renderAsListItem(builder, true, 2).toString();
        assertEquals("", string, "Expected empty string");
    }
}
