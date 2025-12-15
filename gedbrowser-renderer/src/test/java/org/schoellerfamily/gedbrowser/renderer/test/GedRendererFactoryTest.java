package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.analytics.calendar.CalendarProvider;
import org.schoellerfamily.gedbrowser.datamodel.Child;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.FamC;
import org.schoellerfamily.gedbrowser.datamodel.FamS;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.Husband;
import org.schoellerfamily.gedbrowser.datamodel.Link;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.Note;
import org.schoellerfamily.gedbrowser.datamodel.NoteLink;
import org.schoellerfamily.gedbrowser.datamodel.Place;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.SourceLink;
import org.schoellerfamily.gedbrowser.datamodel.Submission;
import org.schoellerfamily.gedbrowser.datamodel.SubmissionLink;
import org.schoellerfamily.gedbrowser.datamodel.Submitter;
import org.schoellerfamily.gedbrowser.datamodel.SubmitterLink;
import org.schoellerfamily.gedbrowser.datamodel.Trailer;
import org.schoellerfamily.gedbrowser.datamodel.Wife;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;
import org.schoellerfamily.gedbrowser.renderer.AttributeRenderer;
import org.schoellerfamily.gedbrowser.renderer.ChildRenderer;
import org.schoellerfamily.gedbrowser.renderer.DateRenderer;
import org.schoellerfamily.gedbrowser.renderer.DefaultRenderer;
import org.schoellerfamily.gedbrowser.renderer.FamCRenderer;
import org.schoellerfamily.gedbrowser.renderer.FamSRenderer;
import org.schoellerfamily.gedbrowser.renderer.FamilyRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.HeadRenderer;
import org.schoellerfamily.gedbrowser.renderer.HusbandRenderer;
import org.schoellerfamily.gedbrowser.renderer.LinkRenderer;
import org.schoellerfamily.gedbrowser.renderer.NameRenderer;
import org.schoellerfamily.gedbrowser.renderer.NoteLinkRenderer;
import org.schoellerfamily.gedbrowser.renderer.NoteRenderer;
import org.schoellerfamily.gedbrowser.renderer.PersonRenderer;
import org.schoellerfamily.gedbrowser.renderer.PlaceRenderer;
import org.schoellerfamily.gedbrowser.renderer.RootRenderer;
import org.schoellerfamily.gedbrowser.renderer.SimpleNameRenderer;
import org.schoellerfamily.gedbrowser.renderer.SourceLinkRenderer;
import org.schoellerfamily.gedbrowser.renderer.SourceRenderer;
import org.schoellerfamily.gedbrowser.renderer.SubmissionLinkRenderer;
import org.schoellerfamily.gedbrowser.renderer.SubmissionRenderer;
import org.schoellerfamily.gedbrowser.renderer.SubmitterLinkRenderer;
import org.schoellerfamily.gedbrowser.renderer.SubmitterRenderer;
import org.schoellerfamily.gedbrowser.renderer.TrailerRenderer;
import org.schoellerfamily.gedbrowser.renderer.WifeRenderer;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings({ "PMD.ExcessiveImports" })
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public final class GedRendererFactoryTest {
    /** */
    @Autowired
    private transient CalendarProvider provider;
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    private transient GedRendererFactory grf;

    /** */
    private final GedObjectBuilder builder = new GedObjectBuilder();

    /** */
    @BeforeEach
    public void init() {
        grf = new GedRendererFactory();
    }

    /** */
    @Test
    public void testGetHusbandRenderer() {
        final GedRenderer<?> gedRenderer =
                grf.create(new Husband(), provider, appInfo);
        assertTrue(gedRenderer instanceof HusbandRenderer, "Expected HusbandRenderer");
    }

    /** */
    @Test
    public void testGetWifeRenderer() {
        final GedRenderer<?> gedRenderer =
                grf.create(new Wife(), provider, appInfo);
        assertTrue(gedRenderer instanceof WifeRenderer, "Expected WifeRenderer");
    }

    /** */
    @Test
    public void testGetPersonRenderer() {
        final GedRenderer<?> gedRenderer = grf.create(
                builder.createPerson(), provider, appInfo);
        assertTrue(gedRenderer instanceof PersonRenderer, "Expected PersonRenderer");
    }

    /** */
    @Test
    public void testGetPlaceRenderer() {
        final GedRenderer<?> gedRenderer =
                grf.create(new Place(), provider, appInfo);
        assertTrue(gedRenderer instanceof PlaceRenderer, "Expected PlaceRenderer");
    }

    /** */
    @Test
    public void testGetNameRenderer() {
        final GedRenderer<?> gedRenderer =
                grf.create(new Name(null), provider, appInfo);
        assertTrue(gedRenderer instanceof NameRenderer, "Expected NameRenderer");
    }

    /** */
    @Test
    public void testGetSubmitterNameRenderer() {
        final GedRenderer<?> gedRenderer =
                grf.create(new Name(new Submitter()), provider, appInfo);
        assertTrue(gedRenderer instanceof SimpleNameRenderer,
                "Expected SimpleNameRenderer");
    }

    /** */
    @Test
    public void testGetAttributeRenderer() {
        final GedRenderer<?> gedRenderer =
                grf.create(builder.createAttribute(), provider, appInfo);
        assertTrue(gedRenderer instanceof AttributeRenderer,
                "Expected AttributeRenderer");
    }

    /** */
    @Test
    public void testGetChildRenderer() {
        final GedRenderer<?> gedRenderer =
                grf.create(new Child(), provider, appInfo);
        assertTrue(gedRenderer instanceof ChildRenderer, "Expected ChildRenderer");
    }

    /** */
    @Test
    public void testGetDateRenderer() {
        final GedRenderer<?> gedRenderer =
                grf.create(new Date(null), provider, appInfo);
        assertTrue(gedRenderer instanceof DateRenderer, "Expected DateRenderer");
    }

    /** */
    @Test
    public void testGetFamCRenderer() {
        final GedRenderer<?> gedRenderer =
                grf.create(new FamC(), provider, appInfo);
        assertTrue(gedRenderer instanceof FamCRenderer, "Expected FamCRenderer");
    }

    /** */
    @Test
    public void testGetFamilyRenderer() {
        final GedRenderer<?> gedRenderer = grf.create(
                builder.createFamily(), provider, appInfo);
        assertTrue(gedRenderer instanceof FamilyRenderer,
                "Expected FamilyRenderer");
    }

    /** */
    @Test
    public void testGetFamSRenderer() {
        final GedRenderer<?> gedRenderer =
                grf.create(new FamS(), provider, appInfo);
        assertTrue(gedRenderer instanceof FamSRenderer, "Expected FamSRenderer");
    }

    /** */
    @Test
    public void testGetHeadRenderer() {
        final GedRenderer<?> gedRenderer =
                grf.create(new Head(null, "Header"), provider, appInfo);
        assertTrue(gedRenderer instanceof HeadRenderer, "Expected HeadRenderer");
    }

    /** */
    @Test
    public void testGetRootRenderer() {
        final GedRenderer<?> gedRenderer =
                grf.create(new Root(), provider, appInfo);
        assertTrue(gedRenderer instanceof RootRenderer, "Expected RootRenderer");
    }

    /** */
    @Test
    public void testGetSourceRenderer() {
        final GedRenderer<?> gedRenderer =
                grf.create(new Source(), provider, appInfo);
        assertTrue(gedRenderer instanceof SourceRenderer, "Expected SourceRenderer");
    }

    /** */
    @Test
    public void testGetSourceLinkRenderer() {
        final GedRenderer<?> gedRenderer =
                grf.create(new SourceLink(), provider, appInfo);
        assertTrue(gedRenderer instanceof SourceLinkRenderer,
                "Expected SourceLinkRenderer");
    }

    /** */
    @Test
    public void testGetSubmissionRenderer() {
        final GedRenderer<?> gedRenderer =
                grf.create(new Submission(), provider, appInfo);
        assertTrue(gedRenderer instanceof SubmissionRenderer,
                "Expected SubmissionRenderer");
    }

    /** */
    @Test
    public void testGetSubmissionLinkRenderer() {
        final GedRenderer<?> gedRenderer =
                grf.create(new SubmissionLink(), provider, appInfo);
        assertTrue(gedRenderer instanceof SubmissionLinkRenderer,
                "Expected SubmissionLinkRenderer");
    }

    /** */
    @Test
    public void testGetSubmitterRenderer() {
        final GedRenderer<?> gedRenderer =
                grf.create(new Submitter(), provider, appInfo);
        assertTrue(gedRenderer instanceof SubmitterRenderer,
                "Expected SubmitterRenderer");
    }

    /** */
    @Test
    public void testGetSubmitterLinkRenderer() {
        final GedRenderer<?> gedRenderer =
                grf.create(new SubmitterLink(), provider, appInfo);
        assertTrue(gedRenderer instanceof SubmitterLinkRenderer,
                "Expected SubmitterLinkRenderer");
    }

    /** */
    @Test
    public void testGetTrailerRenderer() {
        final GedRenderer<?> gedRenderer =
                grf.create(new Trailer(null, "Trailer"), provider, appInfo);
        assertTrue(gedRenderer instanceof TrailerRenderer, "Expected TrailerRenderer");
    }

    /** */
    @Test
    public void testGetLinkRenderer() {
        final GedRenderer<?> gedRenderer =
                grf.create(new Link(null), provider, appInfo);
        assertTrue(gedRenderer instanceof LinkRenderer, "Expected LinkRenderer");
    }

    /** */
    @Test
    public void testGetDefaultRenderer() {
        final GedRenderer<?> gedRenderer =
                grf.create(createGedObject(), provider, appInfo);
        assertTrue(gedRenderer instanceof DefaultRenderer, "Expected DefaultRenderer");
    }

    /** */
    @Test
    public void testGetNoteRenderer() {
        final GedRenderer<?> gedRenderer =
                grf.create(new Note(), provider, appInfo);
        assertTrue(gedRenderer instanceof NoteRenderer, "Expected NoteRenderer");
    }

    /** */
    @Test
    public void testGetNoteLinkRenderer() {
        final GedRenderer<?> gedRenderer =
                grf.create(new NoteLink(), provider, appInfo);
        assertTrue(gedRenderer instanceof NoteLinkRenderer,
                "Expected NoteLinkRenderer");
    }

    /**
     * @return an anonymous subclass of GedObject for testing
     */
    private GedObject createGedObject() {
        return new GedObject() {
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
