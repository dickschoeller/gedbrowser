package org.schoellerfamily.gedbrowser.persistence.mongo.domain.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
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
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Place;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.SourceLink;
import org.schoellerfamily.gedbrowser.datamodel.Submittor;
import org.schoellerfamily.gedbrowser.datamodel.SubmittorLink;
import org.schoellerfamily.gedbrowser.datamodel.Trailer;
import org.schoellerfamily.gedbrowser.datamodel.Wife;
import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;
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
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.PersonDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.PlaceDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.SourceDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.SourceLinkDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.SubmittorDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.SubmittorLinkDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.TrailerDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.WifeDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.test.MongoTestConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings("PMD.ExcessiveImports")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MongoTestConfiguration.class })
public class GedObjectToGedDocumentMongoConverterTest {
    /** */
    @Autowired
    private transient GedObjectToGedDocumentMongoConverter toDocConverter;

    /** */
    @Test
    public void testCreateAttributeDocument() {
        final GedObject ged = new Attribute(null);
        final String typeString = "attribute";
        final GedDocument<?> gmd = toDocConverter.createGedDocument(ged);
        final Class<AttributeDocumentMongo> expectedClass =
                AttributeDocumentMongo.class;
        assertTrue("Failed document check",
                checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public void testCreateChildDocument() {
        final GedObject ged = new Child();
        final String typeString = "child";
        final GedDocument<?> gmd = toDocConverter.createGedDocument(ged);
        final Class<ChildDocumentMongo> expectedClass =
                ChildDocumentMongo.class;
        assertTrue("Failed document check",
                checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public void testCreateDateDocument() {
        final GedObject ged = new Date(null);
        final String typeString = "date";
        final GedDocument<?> gmd = toDocConverter.createGedDocument(ged);
        final Class<DateDocumentMongo> expectedClass =
                DateDocumentMongo.class;
        assertTrue("Failed document check",
                checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public void testCreateMultimediaDocument() {
        final GedObject ged = new Multimedia();
        final String typeString = "multimedia";
        final GedDocument<?> gmd = toDocConverter.createGedDocument(ged);
        final Class<MultimediaDocumentMongo> expectedClass =
                MultimediaDocumentMongo.class;
        assertTrue("Failed document check",
                checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public void testCreateNameDocument() {
        final GedObject ged = new Name(null);
        final String typeString = "name";
        final GedDocument<?> gmd = toDocConverter.createGedDocument(ged);
        final Class<NameDocumentMongo> expectedClass =
                NameDocumentMongo.class;
        assertTrue("Failed document check",
                checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public void testCreateFamilyDocument() {
        final GedObject ged = new Family();
        final String typeString = "family";
        final GedDocument<?> gmd = toDocConverter.createGedDocument(ged);
        final Class<FamilyDocumentMongo> expectedClass =
                FamilyDocumentMongo.class;
        assertTrue("Failed document check",
                checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public void testCreateFamCDocument() {
        final GedObject ged = new FamC();
        final String typeString = "famc";
        final GedDocument<?> gmd = toDocConverter.createGedDocument(ged);
        final Class<FamCDocumentMongo> expectedClass =
                FamCDocumentMongo.class;
        assertTrue("Failed document check",
                checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public void testCreateFamSDocument() {
        final GedObject ged = new FamS();
        final String typeString = "fams";
        final GedDocument<?> gmd = toDocConverter.createGedDocument(ged);
        final Class<FamSDocumentMongo> expectedClass =
                FamSDocumentMongo.class;
        assertTrue("Failed document check",
                checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public void testCreateHeadDocument() {
        final GedObject ged = new Head();
        final String typeString = "head";
        final GedDocument<?> gmd = toDocConverter.createGedDocument(ged);
        final Class<HeadDocumentMongo> expectedClass =
                HeadDocumentMongo.class;
        assertTrue("Failed document check",
                checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public void testCreateHusbandDocument() {
        final GedObject ged = new Husband();
        final String typeString = "husband";
        final GedDocument<?> gmd = toDocConverter.createGedDocument(ged);
        final Class<HusbandDocumentMongo> expectedClass =
                HusbandDocumentMongo.class;
        assertTrue("Failed document check",
                checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public void testCreatePersonDocument() {
        final GedObject ged = new Person(null, new ObjectId("I1"));
        final String typeString = "person";
        final GedDocument<?> gmd = toDocConverter.createGedDocument(ged);
        final Class<PersonDocumentMongo> expectedClass =
                PersonDocumentMongo.class;
        assertTrue("Failed document check",
                checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public void testCreatePlaceDocument() {
        final GedObject ged = new Place();
        final String typeString = "place";
        final GedDocument<?> gmd = toDocConverter.createGedDocument(ged);
        final Class<PlaceDocumentMongo> expectedClass =
                PlaceDocumentMongo.class;
        assertTrue("Failed document check",
                checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public void testCreateSourceDocument() {
        final GedObject ged = new Source(null, new ObjectId("S1"));
        final String typeString = "source";
        final GedDocument<?> gmd = toDocConverter.createGedDocument(ged);
        final Class<SourceDocumentMongo> expectedClass =
                SourceDocumentMongo.class;
        assertTrue("Failed document check",
                checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public void testCreateSourceLinkDocument() {
        final GedObject ged = new SourceLink();
        final String typeString = "sourcelink";
        final GedDocument<?> gmd = toDocConverter.createGedDocument(ged);
        final Class<SourceLinkDocumentMongo> expectedClass =
                SourceLinkDocumentMongo.class;
        assertTrue("Failed document check",
                checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public void testCreateSubmittorDocument() {
        final GedObject ged = new Submittor();
        final String typeString = "submittor";
        final GedDocument<?> gmd = toDocConverter.createGedDocument(ged);
        final Class<SubmittorDocumentMongo> expectedClass =
                SubmittorDocumentMongo.class;
        assertTrue("Failed document check",
                checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public void testCreateSubmittorLinkDocument() {
        final GedObject ged = new SubmittorLink();
        final String typeString = "submittorlink";
        final GedDocument<?> gmd = toDocConverter.createGedDocument(ged);
        final Class<SubmittorLinkDocumentMongo> expectedClass =
                SubmittorLinkDocumentMongo.class;
        assertTrue("Failed document check",
                checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public void testCreateTrailerDocument() {
        final GedObject ged = new Trailer();
        final String typeString = "trailer";
        final GedDocument<?> gmd = toDocConverter.createGedDocument(ged);
        final Class<TrailerDocumentMongo> expectedClass =
                TrailerDocumentMongo.class;
        assertTrue("Failed document check",
                checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public void testCreateWifeDocument() {
        final GedObject ged = new Wife();
        final String typeString = "wife";
        final GedDocument<?> gmd = toDocConverter.createGedDocument(ged);
        final Class<WifeDocumentMongo> expectedClass =
                WifeDocumentMongo.class;
        assertTrue("Failed document check",
                checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public void testNullCreateDocument() {
        GedDocument<?> gmd = null;
        try {
            gmd = toDocConverter.createGedDocument(null);
            fail("Should not get here");
        } catch (PersistenceException e) {
            assertNull("Should be null", gmd); // Expected
        }
    }

    /** */
    @Test
    public void testUnexpectedCreateDocument() {
        final GedObject ged = createGedObject();
        GedDocument<?> gmd = null;
        try {
            gmd = toDocConverter.createGedDocument(ged);
            fail("Should not get here");
        } catch (PersistenceException e) {
            assertNull("Should be null", gmd); // Expected
        }
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

    /**
     * @param ged the ged object that we start with
     * @param gedDocument the mongo document that we end up with
     * @param expectedTypeString the expected type string
     * @param expectedClass the expected class
     * @return true or throws an assertion exception
     */
    private boolean checkGedDocument(final GedObject ged,
            final GedDocument<?> gedDocument, final String expectedTypeString,
            final Class<? extends GedDocumentMongo<?>> expectedClass) {
        assertEquals("Wrong class", gedDocument.getClass(), expectedClass);
        assertEquals("Content mismatch", ged.getString(),
                gedDocument.getString());
        assertEquals("Wrong type", expectedTypeString, gedDocument.getType());
        return true;
    }

}
