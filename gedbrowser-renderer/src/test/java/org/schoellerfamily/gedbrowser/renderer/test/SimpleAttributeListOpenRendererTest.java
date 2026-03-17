package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;
import org.schoellerfamily.gedbrowser.renderer.DefaultRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.SimpleAttributeListOpenRenderer;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;



/**
 * Contains tests for simple attribute list open renderer.
 *
 * @author Richard Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
final class SimpleAttributeListOpenRendererTest {
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    private transient SimpleAttributeListOpenRenderer npr;

    /** */
    private final transient GedObject gob = createGedObject();

    private GedObject createGedObject() {
        return new GedObject(null, "THIS IS A STRING") {
            /**
             * Executes accept.
             *
             * @param visitor the visitor
             */
            @Override
            public void accept(final GedObjectVisitor visitor) {
                visitor.visit(this);
            }
        };
    }

    @BeforeEach
    void setUp() {
        final DefaultRenderer renderer = new DefaultRenderer(gob,
                new GedRendererFactory(),
                RenderingContext.anonymous(appInfo));
        npr = (SimpleAttributeListOpenRenderer) renderer
                .getAttributeListOpenRenderer();
    }

    @Test
    void testRenderAsPhrase() {
        final StringBuilder builder = new StringBuilder();
        npr.renderAttributeListOpen(builder, 0, gob);
        final String string = builder.toString();
        assertEquals("", string, "Expdected empty string");
    }

}
