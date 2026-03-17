package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Submitter;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedbrowser.reader.testreader.TestDataReader;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.NullListItemRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullPhraseRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.SimpleAttributeListOpenRenderer;
import org.schoellerfamily.gedbrowser.renderer.SubmitterNameHtmlRenderer;
import org.schoellerfamily.gedbrowser.renderer.SubmitterNameIndexRenderer;
import org.schoellerfamily.gedbrowser.renderer.SubmitterRenderer;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;



/**
 * Contains tests for submitter renderer.
 *
 * @author Richard Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
final class SubmitterRendererTest {
    /** */
    @Autowired
    private transient TestDataReader reader;

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
    void testAttributeListOpenRenderer() {
        final SubmitterRenderer renderer = createRenderer();
        assertTrue(
            renderer.getAttributeListOpenRenderer() instanceof SimpleAttributeListOpenRenderer,
            "Wrong renderer type");
    }

    @Test
    void testListItemRenderer() {
        final SubmitterRenderer renderer = createRenderer();
        assertTrue(renderer.getListItemRenderer() instanceof NullListItemRenderer,
            "Wrong renderer type");
    }

    @Test
    void testNameHtmlRenderer() {
        final SubmitterRenderer renderer = createRenderer();
        assertTrue(renderer.getNameHtmlRenderer() instanceof SubmitterNameHtmlRenderer,
            "Wrong renderer type");
    }

    @Test
    void testNameIndexRenderer() {
        final SubmitterRenderer renderer = createRenderer();
        assertTrue(renderer.getNameIndexRenderer() instanceof SubmitterNameIndexRenderer,
            "Wrong renderer type");
    }

    @Test
    void testPhraseRenderer() {
        final SubmitterRenderer renderer = createRenderer();
        assertTrue(renderer.getPhraseRenderer() instanceof NullPhraseRenderer,
            "Wrong renderer type");
    }

    @Test
    void testIdString() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submitter submitter = builder.createSubmitter("S1", "Richard John/Schoeller/");
        final SubmitterRenderer renderer = createRenderer(submitter);
        assertEquals("S1", renderer.getIdString(), "Submitter ID mismatch");
    }

    @Test
    void testTitleString() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submitter submitter = builder.createSubmitter("S1", "Richard John/Schoeller/");
        final SubmitterRenderer renderer = createRenderer(submitter);
        assertEquals("Richard John Schoeller", renderer.getTitleString(), "Submitter ID mismatch");
    }

    @Test
    void testNullIdString() {
        final SubmitterRenderer renderer = createRenderer();
        assertEquals("", renderer.getIdString(), "Expected empty submitter ID");
    }

    private SubmitterRenderer createRenderer() {
        return new SubmitterRenderer(new Submitter(), new GedRendererFactory(), anonymousContext);
    }

    @Test
    void testHeadMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final Collection<Submitter> submitters = root.find(Submitter.class);
        for (final Submitter submitter : submitters) {
            final SubmitterRenderer renderer = createRenderer(submitter);
            assertEquals("head?db=gl120368", renderer.getHeaderHref(), "head href mismatch");
        }
    }

    @Test
    void testSaveMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final Collection<Submitter> submitters = root.find(Submitter.class);
        for (final Submitter submitter : submitters) {
            final SubmitterRenderer renderer = createRenderer(submitter);
            assertEquals("save?db=gl120368", renderer.getSaveHref(), "save href mismatch");
        }
    }

    @Test
    void testSaveFilename() throws IOException {
        final Root root = reader.readFileTestSource();
        final Collection<Submitter> submitters = root.find(Submitter.class);
        for (final Submitter submitter : submitters) {
            final SubmitterRenderer renderer = createRenderer(submitter);
            assertEquals("gl120368.ged", renderer.getSaveFilename(), "save href mismatch");
        }
    }

    @Test
    void testIndexMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final Collection<Submitter> submitters = root.find(Submitter.class);
        for (final Submitter submitter : submitters) {
            final SubmitterRenderer renderer = createRenderer(submitter);
            assertEquals("surnames?db=gl120368&letter=A", renderer.getIndexHref(),
                "index href mismatch");
        }
    }

    @Test
    void testLivingMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final Collection<Submitter> submitters = root.find(Submitter.class);
        for (final Submitter submitter : submitters) {
            final SubmitterRenderer renderer = createRenderer(submitter);
            assertEquals("living?db=gl120368", renderer.getLivingHref(), "living href mismatch");
        }
    }

    @Test
    void testSourcesMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final Collection<Submitter> submitters = root.find(Submitter.class);
        for (final Submitter submitter : submitters) {
            final SubmitterRenderer renderer = createRenderer(submitter);
            assertEquals("sources?db=gl120368", renderer.getSourcesHref(), "sources href mismatch");
        }
    }

    @Test
    void testSubmittersMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final Collection<Submitter> submitters = root.find(Submitter.class);
        for (final Submitter submitter : submitters) {
            final SubmitterRenderer renderer = createRenderer(submitter);
            assertEquals("submitters?db=gl120368", renderer.getSubmittersHref(),
                "submitters href mismatch");
        }
    }

    private SubmitterRenderer createRenderer(final Submitter submitter) {
        return new SubmitterRenderer(submitter, new GedRendererFactory(), anonymousContext);
    }
}
