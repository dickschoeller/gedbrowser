package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;
import org.schoellerfamily.gedbrowser.renderer.DefaultRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.SimpleAttributeListOpenRenderer;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public final class SimpleAttributeListOpenRendererTest {
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    private transient SimpleAttributeListOpenRenderer npr;

    /** */
    private final transient GedObject gob = createGedObject();

    /**
     * @return an anonymous subclass of GedObject for testing
     */
    private GedObject createGedObject() {
        return new GedObject(null, "THIS IS A STRING") {
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
    @Before
    public void init() {
        final DefaultRenderer renderer = new DefaultRenderer(gob,
                new GedRendererFactory(),
                RenderingContext.anonymous(appInfo));
        npr = (SimpleAttributeListOpenRenderer) renderer
                .getAttributeListOpenRenderer();
    }

    /** */
    @Test
    public void testRenderAsPhrase() {
        final StringBuilder builder = new StringBuilder();
        npr.renderAttributeListOpen(builder, 0, gob);
        final String string = builder.toString();
        assertEquals("Expdected empty string", "", string);
    }

}
