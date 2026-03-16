package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.datamodel.Multimedia;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.MultimediaListItemRenderer;
import org.schoellerfamily.gedbrowser.renderer.MultimediaPhraseRenderer;
import org.schoellerfamily.gedbrowser.renderer.MultimediaRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullNameHtmlRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullNameIndexRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;



/**
 * Contains tests for multimedia renderer.
 *
 * @author Richard Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
final class MultimediaRendererTest {
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    private RenderingContext anonymousContext;

    @BeforeEach
    void setUp() {
        anonymousContext = RenderingContext.anonymous(appInfo);
    }

    @Test
    void testMultimediaListItemRenderer() {
        final MultimediaRenderer renderer = createRenderer();
        assertTrue(renderer.getListItemRenderer() instanceof MultimediaListItemRenderer,
            "Wrong renderer type");
    }

    @Test
    void testNameHtmlRenderer() {
        final MultimediaRenderer renderer = createRenderer();
        assertTrue(renderer.getNameHtmlRenderer() instanceof NullNameHtmlRenderer,
            "Wrong renderer type");
    }

    @Test
    void testNameIndexRenderer() {
        final MultimediaRenderer renderer = createRenderer();
        assertTrue(renderer.getNameIndexRenderer() instanceof NullNameIndexRenderer,
            "Wrong renderer type");
    }

    @Test
    void testPhraseRenderer() {
        final MultimediaRenderer renderer = createRenderer();
        assertTrue(renderer.getPhraseRenderer() instanceof MultimediaPhraseRenderer,
            "Wrong renderer type");
    }

    private MultimediaRenderer createRenderer() {
        return new MultimediaRenderer(new Multimedia(), new GedRendererFactory(), anonymousContext);
    }

    // TODO test render as page and renderAsListItem
}
