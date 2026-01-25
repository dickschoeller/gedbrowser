package org.schoellerfamily.gedbrowser.persistence.mongo.domain.test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocumentVisitor;
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

/**
 * @author Dick Schoeller
 */
@SuppressWarnings({ "PMD.ExcessiveImports", "PMD.CouplingBetweenObjects" })
@ExtendWith(org.springframework.test.context.junit.jupiter.SpringExtension.class)
@ContextConfiguration(classes = { MongoTestConfiguration.class })
public final class GedDocumentMongoToGedObjectConverterTest {
    /** */
    @Autowired
    private transient GedDocumentMongoToGedObjectConverter toObjConverter;

    /** */
    @Test
    void testCreateAttribute() {
        final AttributeDocumentMongo gmd = new AttributeDocumentMongo();
        gmd.setString("Attribute");
        final GedObject ged = toObjConverter.createGedObject(new Root(), gmd);
        assertEquals(Attribute.class, ged.getClass(), "Wrong class");
    }

    /** */
    @Test
    void testCreateChild() {
        final ChildDocumentMongo gmd = new ChildDocumentMongo();
        gmd.setString("Child");
        final GedObject ged = toObjConverter.createGedObject(new Root(), gmd);
        assertEquals(Child.class, ged.getClass(), "Wrong class");
    }

    /** */
    @Test
    void testCreateDate() {
        final DateDocumentMongo gmd = new DateDocumentMongo();
        gmd.setString("Date");
        final GedObject ged = toObjConverter.createGedObject(new Root(), gmd);
        assertEquals(Date.class, ged.getClass(), "Wrong class");
    }

    /** */
    @Test
    void testCreateFamily() {
        final FamilyDocumentMongo gmd = new FamilyDocumentMongo();
        gmd.setString("Family");
        final GedObject ged = toObjConverter.createGedObject(new Root(), gmd);
        assertEquals(Family.class, ged.getClass(), "Wrong class");
    }

    /** */
    @Test
    void testCreateFamC() {
        final FamCDocumentMongo gmd = new FamCDocumentMongo();
        gmd.setString("FamC");
        final GedObject ged = toObjConverter.createGedObject(new Root(), gmd);
        assertEquals(FamC.class, ged.getClass(), "Wrong class");
    }

    /** */
    @Test
    void testCreateFamS() {
        final FamSDocumentMongo gmd = new FamSDocumentMongo();
        gmd.setString("FamS");
        final GedObject ged = toObjConverter.createGedObject(new Root(), gmd);
        assertEquals(FamS.class, ged.getClass(), "Wrong class");
    }

    /** */
    @Test
    void testCreateHead() {
        final HeadDocumentMongo gmd = new HeadDocumentMongo();
        gmd.setString("Head");
        final GedObject ged = toObjConverter.createGedObject(new Root(), gmd);
        assertEquals(Head.class, ged.getClass(), "Wrong class");
    }

    /** */
    @Test
    void testCreateHusband() {
        final HusbandDocumentMongo gmd = new HusbandDocumentMongo();
        gmd.setString("Husband");
        final GedObject ged = toObjConverter.createGedObject(new Root(), gmd);
        assertEquals(Husband.class, ged.getClass(), "Wrong class");
    }

    /** */
    @Test
    void testCreateNote() {
        final NoteDocumentMongo gmd = new NoteDocumentMongo();
        gmd.setString("Note");
        final GedObject ged = toObjConverter.createGedObject(new Root(), gmd);
        assertEquals(Note.class, ged.getClass(), "Wrong class");
    }

    /** */
    @Test
    void testCreateNoteLink() {
        final NoteLinkDocumentMongo gmd = new NoteLinkDocumentMongo();
        gmd.setString("NoteLink");
        final GedObject ged = toObjConverter.createGedObject(new Root(), gmd);
        assertEquals(NoteLink.class, ged.getClass(), "Wrong class");
    }

    /** */
    @Test
    void testCreatePerson() {
        final PersonDocumentMongo gmd = new PersonDocumentMongo();
        gmd.setString("Person");
        final GedObject ged = toObjConverter.createGedObject(new Root(), gmd);
        assertEquals(Person.class, ged.getClass(), "Wrong class");
    }

    /** */
    @Test
    void testCreatePlace() {
        final PlaceDocumentMongo gmd = new PlaceDocumentMongo();
        gmd.setString("Place");
        final GedObject ged = toObjConverter.createGedObject(new Root(), gmd);
        assertEquals(Place.class, ged.getClass(), "Wrong class");
    }

    /** */
    @Test
    void testCreateSource() {
        final SourceDocumentMongo gmd = new SourceDocumentMongo();
        gmd.setString("Source");
        final GedObject ged = toObjConverter.createGedObject(new Root(), gmd);
        assertEquals(Source.class, ged.getClass(), "Wrong class");
    }

    /** */
    @Test
    void testCreateSourceLink() {
        final SourceLinkDocumentMongo gmd = new SourceLinkDocumentMongo();
        gmd.setString("SourceLink");
        final GedObject ged = toObjConverter.createGedObject(new Root(), gmd);
        assertEquals(SourceLink.class, ged.getClass(), "Wrong class");
    }

    /** */
    @Test
    void testCreateSubmission() {
        final SubmissionDocumentMongo gmd = new SubmissionDocumentMongo();
        gmd.setString("Submission");
        final GedObject ged = toObjConverter.createGedObject(new Root(), gmd);
        assertEquals(Submission.class, ged.getClass(), "Wrong class");
    }

    /** */
    @Test
    void testCreateSubmissionLink() {
        final SubmissionLinkDocumentMongo gmd = new SubmissionLinkDocumentMongo();
        gmd.setString("SubmissionLink");
        final GedObject ged = toObjConverter.createGedObject(new Root(), gmd);
        assertEquals(SubmissionLink.class, ged.getClass(), "Wrong class");
    }

    /** */
    @Test
    void testCreateSubmitter() {
        final SubmitterDocumentMongo gmd = new SubmitterDocumentMongo();
        gmd.setString("Submitter");
        final GedObject ged = toObjConverter.createGedObject(new Root(), gmd);
        assertEquals(Submitter.class, ged.getClass(), "Wrong class");
    }

    /** */
    @Test
    void testCreateSubmitterLink() {
        final SubmitterLinkDocumentMongo gmd = new SubmitterLinkDocumentMongo();
        gmd.setString("SubmitterLink");
        final GedObject ged = toObjConverter.createGedObject(new Root(), gmd);
        assertEquals(SubmitterLink.class, ged.getClass(), "Wrong class");
    }

    /** */
    @Test
    void testCreateTrailer() {
        final TrailerDocumentMongo gmd = new TrailerDocumentMongo();
        gmd.setString("Trailer");
        final GedObject ged = toObjConverter.createGedObject(new Root(), gmd);
        assertEquals(Trailer.class, ged.getClass(), "Wrong class");
    }

    /** */
    @Test
    void testCreateWife() {
        final WifeDocumentMongo gmd = new WifeDocumentMongo();
        gmd.setString("Wife");
        final GedObject ged = toObjConverter.createGedObject(new Root(), gmd);
        assertEquals(Wife.class, ged.getClass(), "Wrong class");
    }

    /** */
    @Test
    void testCreateMultimedia() {
        final MultimediaDocumentMongo gmd = new MultimediaDocumentMongo();
        gmd.setString("Multimedia");
        final GedObject ged = toObjConverter.createGedObject(new Root(), gmd);
        assertEquals(Multimedia.class, ged.getClass(), "Wrong class");
    }

    /** */
    @Test
    void testCreateName() {
        final NameDocumentMongo gmd = new NameDocumentMongo();
        gmd.setString("Name");
        final GedObject ged = toObjConverter.createGedObject(new Root(), gmd);
        assertEquals(Name.class, ged.getClass(), "Wrong class");
    }

    /** */
    @Test
    void testCreateGed() {
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
            public void loadGedObject(final GedDocumentLoader loader, final GedObject ged) {
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

            /**
             * {@inheritDoc}
             */
            @Override
            public void accept(final GedDocumentVisitor visitor) {
                visitor.visit(this);
            }
        };
        gmd.setString("Foo");
        assertThatExceptionOfType(PersistenceException.class)
            .isThrownBy(() -> toObjConverter.createGedObject(new Root(), gmd));
    }
}
