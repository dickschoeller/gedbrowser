package org.schoellerfamily.gedbrowser.persistence.mongo.domain.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Child;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.FamC;
import org.schoellerfamily.gedbrowser.datamodel.FamS;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.Husband;
import org.schoellerfamily.gedbrowser.datamodel.Multimedia;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.Note;
import org.schoellerfamily.gedbrowser.datamodel.NoteLink;
import org.schoellerfamily.gedbrowser.datamodel.Person;
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
import org.schoellerfamily.gedbrowser.persistence.GedDocumentLoader;
import org.schoellerfamily.gedbrowser.persistence.PersistenceException;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.AttributeDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.ChildDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.DateDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.FamCDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.FamSDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.FamilyDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.GedDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.HeadDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.HusbandDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.MultimediaDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.NameDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.NoteDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.NoteLinkDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.PersonDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.PlaceDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.SourceDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.SourceLinkDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.SubmissionDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.SubmissionLinkDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.SubmitterDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.SubmitterLinkDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.TrailerDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.WifeDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.visitor.GedDocumentMongoVisitor;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.visitor.TopLevelGedDocumentMongoVisitor;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedDocumentMongoToGedObjectConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.test.MongoTestConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings({ "PMD.ExcessiveImports", "PMD.CouplingBetweenObjects" })
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MongoTestConfiguration.class })
public final class GedDocumentMongoToGedObjectConverterTest {
    /** */
    @Autowired
    private transient GedDocumentMongoToGedObjectConverter toObjConverter;

    /** */
    @Test
    public void testCreateAttribute() {
        final AttributeDocumentMongo gmd = new AttributeDocumentMongo();
        gmd.setString("Attribute");
        final GedObject ged = toObjConverter.createGedObject(new Root(), gmd);
        assertEquals("Wrong class", Attribute.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreateChild() {
        final ChildDocumentMongo gmd = new ChildDocumentMongo();
        gmd.setString("Child");
        final GedObject ged = toObjConverter.createGedObject(new Root(), gmd);
        assertEquals("Wrong class", Child.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreateDate() {
        final DateDocumentMongo gmd = new DateDocumentMongo();
        gmd.setString("Date");
        final GedObject ged = toObjConverter.createGedObject(new Root(), gmd);
        assertEquals("Wrong class", Date.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreateFamily() {
        final FamilyDocumentMongo gmd = new FamilyDocumentMongo();
        gmd.setString("Family");
        final GedObject ged = toObjConverter.createGedObject(new Root(), gmd);
        assertEquals("Wrong class", Family.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreateFamC() {
        final FamCDocumentMongo gmd = new FamCDocumentMongo();
        gmd.setString("FamC");
        final GedObject ged = toObjConverter.createGedObject(new Root(), gmd);
        assertEquals("Wrong class", FamC.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreateFamS() {
        final FamSDocumentMongo gmd = new FamSDocumentMongo();
        gmd.setString("FamS");
        final GedObject ged = toObjConverter.createGedObject(new Root(), gmd);
        assertEquals("Wrong class", FamS.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreateHead() {
        final HeadDocumentMongo gmd = new HeadDocumentMongo();
        gmd.setString("Head");
        final GedObject ged = toObjConverter.createGedObject(new Root(), gmd);
        assertEquals("Wrong class", Head.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreateHusband() {
        final HusbandDocumentMongo gmd = new HusbandDocumentMongo();
        gmd.setString("Husband");
        final GedObject ged = toObjConverter.createGedObject(new Root(), gmd);
        assertEquals("Wrong class", Husband.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreateNote() {
        final NoteDocumentMongo gmd = new NoteDocumentMongo();
        gmd.setString("Note");
        final GedObject ged = toObjConverter.createGedObject(new Root(), gmd);
        assertEquals("Wrong class", Note.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreateNoteLink() {
        final NoteLinkDocumentMongo gmd = new NoteLinkDocumentMongo();
        gmd.setString("NoteLink");
        final GedObject ged = toObjConverter.createGedObject(new Root(), gmd);
        assertEquals("Wrong class", NoteLink.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreatePerson() {
        final PersonDocumentMongo gmd = new PersonDocumentMongo();
        gmd.setString("Person");
        final GedObject ged = toObjConverter.createGedObject(new Root(), gmd);
        assertEquals("Wrong class", Person.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreatePlace() {
        final PlaceDocumentMongo gmd = new PlaceDocumentMongo();
        gmd.setString("Place");
        final GedObject ged = toObjConverter.createGedObject(new Root(), gmd);
        assertEquals("Wrong class", Place.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreateSource() {
        final SourceDocumentMongo gmd = new SourceDocumentMongo();
        gmd.setString("Source");
        final GedObject ged = toObjConverter.createGedObject(new Root(), gmd);
        assertEquals("Wrong class", Source.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreateSourceLink() {
        final SourceLinkDocumentMongo gmd = new SourceLinkDocumentMongo();
        gmd.setString("SourceLink");
        final GedObject ged = toObjConverter.createGedObject(new Root(), gmd);
        assertEquals("Wrong class", SourceLink.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreateSubmission() {
        final SubmissionDocumentMongo gmd = new SubmissionDocumentMongo();
        gmd.setString("Submission");
        final GedObject ged = toObjConverter.createGedObject(new Root(), gmd);
        assertEquals("Wrong class", Submission.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreateSubmissionLink() {
        final SubmissionLinkDocumentMongo gmd = new SubmissionLinkDocumentMongo();
        gmd.setString("SubmissionLink");
        final GedObject ged = toObjConverter.createGedObject(new Root(), gmd);
        assertEquals("Wrong class", SubmissionLink.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreateSubmitter() {
        final SubmitterDocumentMongo gmd = new SubmitterDocumentMongo();
        gmd.setString("Submitter");
        final GedObject ged = toObjConverter.createGedObject(new Root(), gmd);
        assertEquals("Wrong class", Submitter.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreateSubmitterLink() {
        final SubmitterLinkDocumentMongo gmd = new SubmitterLinkDocumentMongo();
        gmd.setString("SubmitterLink");
        final GedObject ged = toObjConverter.createGedObject(new Root(), gmd);
        assertEquals("Wrong class", SubmitterLink.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreateTrailer() {
        final TrailerDocumentMongo gmd = new TrailerDocumentMongo();
        gmd.setString("Trailer");
        final GedObject ged = toObjConverter.createGedObject(new Root(), gmd);
        assertEquals("Wrong class", Trailer.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreateWife() {
        final WifeDocumentMongo gmd = new WifeDocumentMongo();
        gmd.setString("Wife");
        final GedObject ged = toObjConverter.createGedObject(new Root(), gmd);
        assertEquals("Wrong class", Wife.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreateMultimedia() {
        final MultimediaDocumentMongo gmd = new MultimediaDocumentMongo();
        gmd.setString("Multimedia");
        final GedObject ged = toObjConverter.createGedObject(new Root(), gmd);
        assertEquals("Wrong class", Multimedia.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreateName() {
        final NameDocumentMongo gmd = new NameDocumentMongo();
        gmd.setString("Name");
        final GedObject ged = toObjConverter.createGedObject(new Root(), gmd);
        assertEquals("Wrong class", Name.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreateGed() {
        @SuppressWarnings("PMD.NonStaticInitializer")
        final GedDocument<?> gmd = new GedDocumentMongo<GedObject>() {
            /**
             * {@inheritDoc}
             */
            @Override
            public String getType() {
                return "foo";
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void loadGedObject(final GedDocumentLoader loader,
                    final GedObject ged) {
                // Intentionally empty
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void accept(final TopLevelGedDocumentMongoVisitor visitor) {
                visitor.visit(this);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void accept(final GedDocumentMongoVisitor visitor) {
                visitor.visit(this);
            }
        };
        gmd.setString("Foo");
        GedObject ged = null;
        try {
            ged = toObjConverter.createGedObject(new Root(), gmd);
            fail("Should not get here");
        } catch (PersistenceException e) {
            assertNull("Result should be null", ged);
        }
    }
}
