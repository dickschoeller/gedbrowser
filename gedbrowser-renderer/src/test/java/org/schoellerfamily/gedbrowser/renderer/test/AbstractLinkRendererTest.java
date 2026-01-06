package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.datamodel.AbstractLink;
import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;
import org.schoellerfamily.gedbrowser.renderer.AbstractLinkRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.NullListItemRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullNameHtmlRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullNameIndexRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullPhraseRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.SimpleAttributeListOpenRenderer;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public final class AbstractLinkRendererTest {
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    private RenderingContext anonymousContext;

    /** */
    @BeforeEach
    public void setUp() {
        anonymousContext = RenderingContext.anonymous(appInfo);
    }

    /**
     * Test that we are using the appropriate sub-renderers. We will test the
     * sub-renderers directly.
     */
    @Test
    public void testAttributeListOpenRenderer() {
        final AbstractLinkRenderer<?> renderer = createRenderer();
        assertTrue(
            renderer.getAttributeListOpenRenderer() instanceof SimpleAttributeListOpenRenderer,
            "renderer is not of the right type");
    }

    /**
     * Test that we are using the appropriate sub-renderers. We will test the
     * sub-renderers directly.
     */
    @Test
    public void testListItemRenderer() {
        final AbstractLinkRenderer<?> renderer = createRenderer();
        assertTrue(renderer.getListItemRenderer() instanceof NullListItemRenderer,
            "renderer is not of the right type");
    }

    /**
     * Test that we are using the appropriate sub-renderers. We will test the
     * sub-renderers directly.
     */
    @Test
    public void testNameHtmlRenderer() {
        final AbstractLinkRenderer<?> renderer = createRenderer();
        assertTrue(renderer.getNameHtmlRenderer() instanceof NullNameHtmlRenderer,
            "renderer is not of the right type");
    }

    /**
     * Test that we are using the appropriate sub-renderers. We will test the
     * sub-renderers directly.
     */
    @Test
    public void testNameIndexRenderer() {
        final AbstractLinkRenderer<?> renderer = createRenderer();
        assertTrue(renderer.getNameIndexRenderer() instanceof NullNameIndexRenderer,
            "renderer is not of the right type");
    }

    /**
     * Test that we are using the appropriate sub-renderers. We will test the
     * sub-renderers directly.
     */
    @Test
    public void testPhraseRenderer() {
        final AbstractLinkRenderer<?> renderer = createRenderer();
        assertTrue(renderer.getPhraseRenderer() instanceof NullPhraseRenderer,
            "renderer is not of the right type");
    }

    /**
     * @return the renderer
     */
    private AbstractLinkRenderer<?> createRenderer() {
        return new AbstractLinkRenderer<AbstractLink>(createAbstractLink(),
            new GedRendererFactory(), anonymousContext) {
        };
    }

    /**
     * Create an anonymous subclass of AbstractLink for testing.
     *
     * @return the link
     */
    private AbstractLink createAbstractLink() {
        return new AbstractLink(null) {
            /**
             * {@inheritDoc}
             */
            @Override
            public void accept(final GedObjectVisitor visitor) {
                visitor.visit(this);
            }
        };
    }
}
