package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Submittor;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedbrowser.reader.testreader.TestDataReader;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.NullListItemRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullPhraseRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.SimpleAttributeListOpenRenderer;
import org.schoellerfamily.gedbrowser.renderer.SubmittorNameHtmlRenderer;
import org.schoellerfamily.gedbrowser.renderer.SubmittorNameIndexRenderer;
import org.schoellerfamily.gedbrowser.renderer.SubmittorRenderer;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public final class SubmittorRendererTest {
    /** */
    @Autowired
    private transient TestDataReader reader;

    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    private RenderingContext anonymousContext;

    /** */
    @Before
    public void init() {
        anonymousContext = RenderingContext.anonymous(appInfo);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testAttributeListOpenRenderer() {
        final SubmittorRenderer renderer = createRenderer();
        assertTrue("Wrong renderer type",
                renderer.getAttributeListOpenRenderer()
                instanceof SimpleAttributeListOpenRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testListItemRenderer() {
        final SubmittorRenderer renderer = createRenderer();
        assertTrue("Wrong renderer type",
                renderer.getListItemRenderer()
                instanceof NullListItemRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testNameHtmlRenderer() {
        final SubmittorRenderer renderer = createRenderer();
        assertTrue("Wrong renderer type",
                renderer.getNameHtmlRenderer()
                instanceof SubmittorNameHtmlRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testNameIndexRenderer() {
        final SubmittorRenderer renderer = createRenderer();
        assertTrue("Wrong renderer type",
                renderer.getNameIndexRenderer()
                instanceof SubmittorNameIndexRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testPhraseRenderer() {
        final SubmittorRenderer renderer = createRenderer();
        assertTrue("Wrong renderer type",
                renderer.getPhraseRenderer()
                instanceof NullPhraseRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testIdString() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submittor submittor = builder.createSubmittor("S1",
                "Richard John/Schoeller/");
        final SubmittorRenderer renderer = createRenderer(submittor);
        assertEquals("Submittor ID mismatch", "S1", renderer.getIdString());
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testNullIdString() {
        final SubmittorRenderer renderer = createRenderer();
        assertEquals("Expected empty submittor ID", "", renderer.getIdString());
    }

    /**
     * @return the renderer
     */
    private SubmittorRenderer createRenderer() {
        return new SubmittorRenderer(new Submittor(), new GedRendererFactory(),
                anonymousContext);
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    public void testHeaderMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final Collection<Submittor> submittors = root.find(Submittor.class);
        for (final Submittor submittor : submittors) {
            final SubmittorRenderer renderer = createRenderer(submittor);
            assertEquals("head href mismatch",
                    "head?db=gl120368", renderer.getHeaderHref());
        }
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    public void testIndexMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final Collection<Submittor> submittors = root.find(Submittor.class);
        for (final Submittor submittor : submittors) {
            final SubmittorRenderer renderer = createRenderer(submittor);
            assertEquals("index href mismatch",
                    "surnames?db=gl120368&letter=A", renderer.getIndexHref());
        }
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    public void testLivingMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final Collection<Submittor> submittors = root.find(Submittor.class);
        for (final Submittor submittor : submittors) {
            final SubmittorRenderer renderer = createRenderer(submittor);
            assertEquals("living href mismatch",
                    "living?db=gl120368", renderer.getLivingHref());
        }
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    public void testSourcesMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final Collection<Submittor> submittors = root.find(Submittor.class);
        for (final Submittor submittor : submittors) {
            final SubmittorRenderer renderer = createRenderer(submittor);
            assertEquals("sources href mismatch",
                    "sources?db=gl120368", renderer.getSourcesHref());
        }
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    public void testSubmittorsMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final Collection<Submittor> submittors = root.find(Submittor.class);
        for (final Submittor submittor : submittors) {
            final SubmittorRenderer renderer = createRenderer(submittor);
            assertEquals("submittors href mismatch",
                    "submittors?db=gl120368", renderer.getSubmittorsHref());
        }
    }

    /**
     * @param submittor the submittor
     * @return the renderer
     */
    private SubmittorRenderer createRenderer(final Submittor submittor) {
        return new SubmittorRenderer(submittor,
                new GedRendererFactory(), anonymousContext);
    }
}
