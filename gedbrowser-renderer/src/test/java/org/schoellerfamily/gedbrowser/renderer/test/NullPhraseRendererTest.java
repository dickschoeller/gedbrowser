package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;
import org.schoellerfamily.gedbrowser.renderer.DefaultRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.NullPhraseRenderer;
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
final class NullPhraseRendererTest {
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    private transient NullPhraseRenderer npr;

    /** */
    @BeforeEach
    void setUp() {
        final DefaultRenderer renderer = new DefaultRenderer(createGedObject(),
                new GedRendererFactory(), RenderingContext.anonymous(appInfo));
        npr = (NullPhraseRenderer) renderer.getPhraseRenderer();
    }

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
    @Test
    void testRenderAsPhrase() {
        assertEquals("", npr.renderAsPhrase(), "Expected empty string");
    }
}
