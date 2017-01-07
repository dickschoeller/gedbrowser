package org.schoellerfamily.gedbrowser.persistence.mongo.domain.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
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
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Place;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.SourceLink;
import org.schoellerfamily.gedbrowser.datamodel.Submittor;
import org.schoellerfamily.gedbrowser.datamodel.SubmittorLink;
import org.schoellerfamily.gedbrowser.datamodel.Trailer;
import org.schoellerfamily.gedbrowser.datamodel.Wife;
import org.schoellerfamily.gedbrowser.persistence.PersistenceException;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.AttributeDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.ChildDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.DateDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.FamCDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.FamSDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.FamilyDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.GedDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.GedDocumentMongoFactory;
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

/**
 * @author Dick Schoeller
 */
@SuppressWarnings({ "PMD.CouplingBetweenObjects", "PMD.ExcessiveImports" })
public final class GedDocumentFactoryTest {
    /** */
    @Test
    public void testCreateAttributeDocument() {
        final GedObject ged = new Attribute(null);
        final String typeString = "attribute";
        final GedDocument<?> gmd = GedDocumentMongoFactory.getInstance()
                .createGedDocument(ged);
        final Class<AttributeDocumentMongo> expectedClass =
                AttributeDocumentMongo.class;
        assertTrue("Failed document check",
                checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public void testCreateChildDocument() {
        final GedObject ged = new Child(null);
        final String typeString = "child";
        final GedDocument<?> gmd = GedDocumentMongoFactory.getInstance()
                .createGedDocument(ged);
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
        final GedDocument<?> gmd = GedDocumentMongoFactory.getInstance()
                .createGedDocument(ged);
        final Class<DateDocumentMongo> expectedClass =
                DateDocumentMongo.class;
        assertTrue("Failed document check",
                checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public void testCreateMultimediaDocument() {
        final GedObject ged = new Multimedia(null);
        final String typeString = "multimedia";
        final GedDocument<?> gmd = GedDocumentMongoFactory.getInstance()
                .createGedDocument(ged);
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
        final GedDocument<?> gmd = GedDocumentMongoFactory.getInstance()
                .createGedDocument(ged);
        final Class<NameDocumentMongo> expectedClass =
                NameDocumentMongo.class;
        assertTrue("Failed document check",
                checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public void testCreateFamilyDocument() {
        final GedObject ged = new Family(null);
        final String typeString = "family";
        final GedDocument<?> gmd = GedDocumentMongoFactory.getInstance()
                .createGedDocument(ged);
        final Class<FamilyDocumentMongo> expectedClass =
                FamilyDocumentMongo.class;
        assertTrue("Failed document check",
                checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public void testCreateFamCDocument() {
        final GedObject ged = new FamC(null);
        final String typeString = "famc";
        final GedDocument<?> gmd = GedDocumentMongoFactory.getInstance()
                .createGedDocument(ged);
        final Class<FamCDocumentMongo> expectedClass =
                FamCDocumentMongo.class;
        assertTrue("Failed document check",
                checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public void testCreateFamSDocument() {
        final GedObject ged = new FamS(null);
        final String typeString = "fams";
        final GedDocument<?> gmd = GedDocumentMongoFactory.getInstance()
                .createGedDocument(ged);
        final Class<FamSDocumentMongo> expectedClass =
                FamSDocumentMongo.class;
        assertTrue("Failed document check",
                checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public void testCreateHeadDocument() {
        final GedObject ged = new Head(null);
        final String typeString = "head";
        final GedDocument<?> gmd = GedDocumentMongoFactory.getInstance()
                .createGedDocument(ged);
        final Class<HeadDocumentMongo> expectedClass =
                HeadDocumentMongo.class;
        assertTrue("Failed document check",
                checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public void testCreateHusbandDocument() {
        final GedObject ged = new Husband(null);
        final String typeString = "husband";
        final GedDocument<?> gmd = GedDocumentMongoFactory.getInstance()
                .createGedDocument(ged);
        final Class<HusbandDocumentMongo> expectedClass =
                HusbandDocumentMongo.class;
        assertTrue("Failed document check",
                checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public void testCreatePersonDocument() {
        final GedObject ged = new Person(null);
        final String typeString = "person";
        final GedDocument<?> gmd = GedDocumentMongoFactory.getInstance()
                .createGedDocument(ged);
        final Class<PersonDocumentMongo> expectedClass =
                PersonDocumentMongo.class;
        assertTrue("Failed document check",
                checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public void testCreatePlaceDocument() {
        final GedObject ged = new Place(null);
        final String typeString = "place";
        final GedDocument<?> gmd = GedDocumentMongoFactory.getInstance()
                .createGedDocument(ged);
        final Class<PlaceDocumentMongo> expectedClass =
                PlaceDocumentMongo.class;
        assertTrue("Failed document check",
                checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public void testCreateSourceDocument() {
        final GedObject ged = new Source(null);
        final String typeString = "source";
        final GedDocument<?> gmd = GedDocumentMongoFactory.getInstance()
                .createGedDocument(ged);
        final Class<SourceDocumentMongo> expectedClass =
                SourceDocumentMongo.class;
        assertTrue("Failed document check",
                checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public void testCreateSourceLinkDocument() {
        final GedObject ged = new SourceLink(null);
        final String typeString = "sourcelink";
        final GedDocument<?> gmd = GedDocumentMongoFactory.getInstance()
                .createGedDocument(ged);
        final Class<SourceLinkDocumentMongo> expectedClass =
                SourceLinkDocumentMongo.class;
        assertTrue("Failed document check",
                checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public void testCreateSubmittorDocument() {
        final GedObject ged = new Submittor(null);
        final String typeString = "submittor";
        final GedDocument<?> gmd = GedDocumentMongoFactory.getInstance()
                .createGedDocument(ged);
        final Class<SubmittorDocumentMongo> expectedClass =
                SubmittorDocumentMongo.class;
        assertTrue("Failed document check",
                checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public void testCreateSubmittorLinkDocument() {
        final GedObject ged = new SubmittorLink(null);
        final String typeString = "submittorlink";
        final GedDocument<?> gmd = GedDocumentMongoFactory.getInstance()
                .createGedDocument(ged);
        final Class<SubmittorLinkDocumentMongo> expectedClass =
                SubmittorLinkDocumentMongo.class;
        assertTrue("Failed document check",
                checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public void testCreateTrailerDocument() {
        final GedObject ged = new Trailer(null);
        final String typeString = "trailer";
        final GedDocument<?> gmd = GedDocumentMongoFactory.getInstance()
                .createGedDocument(ged);
        final Class<TrailerDocumentMongo> expectedClass =
                TrailerDocumentMongo.class;
        assertTrue("Failed document check",
                checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public void testCreateWifeDocument() {
        final GedObject ged = new Wife(null);
        final String typeString = "wife";
        final GedDocument<?> gmd = GedDocumentMongoFactory.getInstance()
                .createGedDocument(ged);
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
            gmd = GedDocumentMongoFactory.getInstance().
                    createGedDocument(null);
            fail("Should not get here");
        } catch (PersistenceException e) {
            assertNull("Should be null", gmd); // Expected
        }
    }

    /** */
    @Test
    public void testUnexpectedCreateDocument() {
        final GedObject ged = new GedObject(null) {
        };
        GedDocument<?> gmd = null;
        try {
            gmd = GedDocumentMongoFactory.getInstance().
                    createGedDocument(ged);
            fail("Should not get here");
        } catch (PersistenceException e) {
            assertNull("Should be null", gmd); // Expected
        }
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

    /** */
    @Test
    public void testCreateAttribute() {
        final AttributeDocumentMongo gmd = new AttributeDocumentMongo();
        gmd.setString("Attribute");
        final GedObject ged = GedDocumentMongoFactory.getInstance().
                createGedObject(new Root(null), gmd);
        assertEquals("Wrong class", Attribute.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreateChild() {
        final ChildDocumentMongo gmd = new ChildDocumentMongo();
        gmd.setString("Child");
        final GedObject ged = GedDocumentMongoFactory.getInstance().
                createGedObject(new Root(null), gmd);
        assertEquals("Wrong class", Child.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreateDate() {
        final DateDocumentMongo gmd = new DateDocumentMongo();
        gmd.setString("Date");
        final GedObject ged = GedDocumentMongoFactory.getInstance().
                createGedObject(new Root(null), gmd);
        assertEquals("Wrong class", Date.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreateFamily() {
        final FamilyDocumentMongo gmd = new FamilyDocumentMongo();
        gmd.setString("Family");
        final GedObject ged = GedDocumentMongoFactory.getInstance().
                createGedObject(new Root(null), gmd);
        assertEquals("Wrong class", Family.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreateFamC() {
        final FamCDocumentMongo gmd = new FamCDocumentMongo();
        gmd.setString("FamC");
        final GedObject ged = GedDocumentMongoFactory.getInstance().
                createGedObject(new Root(null), gmd);
        assertEquals("Wrong class", FamC.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreateFamS() {
        final FamSDocumentMongo gmd = new FamSDocumentMongo();
        gmd.setString("FamS");
        final GedObject ged = GedDocumentMongoFactory.getInstance().
                createGedObject(new Root(null), gmd);
        assertEquals("Wrong class", FamS.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreateHead() {
        final HeadDocumentMongo gmd = new HeadDocumentMongo();
        gmd.setString("Head");
        final GedObject ged = GedDocumentMongoFactory.getInstance().
                createGedObject(new Root(null), gmd);
        assertEquals("Wrong class", Head.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreateHusband() {
        final HusbandDocumentMongo gmd = new HusbandDocumentMongo();
        gmd.setString("Husband");
        final GedObject ged = GedDocumentMongoFactory.getInstance().
                createGedObject(new Root(null), gmd);
        assertEquals("Wrong class", Husband.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreatePerson() {
        final PersonDocumentMongo gmd = new PersonDocumentMongo();
        gmd.setString("Person");
        final GedObject ged = GedDocumentMongoFactory.getInstance().
                createGedObject(new Root(null), gmd);
        assertEquals("Wrong class", Person.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreatePlace() {
        final PlaceDocumentMongo gmd = new PlaceDocumentMongo();
        gmd.setString("Place");
        final GedObject ged = GedDocumentMongoFactory.getInstance().
                createGedObject(new Root(null), gmd);
        assertEquals("Wrong class", Place.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreateSource() {
        final SourceDocumentMongo gmd = new SourceDocumentMongo();
        gmd.setString("Source");
        final GedObject ged = GedDocumentMongoFactory.getInstance().
                createGedObject(new Root(null), gmd);
        assertEquals("Wrong class", Source.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreateSourceLink() {
        final SourceLinkDocumentMongo gmd = new SourceLinkDocumentMongo();
        gmd.setString("SourceLink");
        final GedObject ged = GedDocumentMongoFactory.getInstance().
                createGedObject(new Root(null), gmd);
        assertEquals("Wrong class", SourceLink.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreateSubmittor() {
        final SubmittorDocumentMongo gmd = new SubmittorDocumentMongo();
        gmd.setString("Submittor");
        final GedObject ged = GedDocumentMongoFactory.getInstance().
                createGedObject(new Root(null), gmd);
        assertEquals("Wrong class", Submittor.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreateSubmittorLink() {
        final SubmittorLinkDocumentMongo gmd = new SubmittorLinkDocumentMongo();
        gmd.setString("SubmittorLink");
        final GedObject ged = GedDocumentMongoFactory.getInstance().
                createGedObject(new Root(null), gmd);
        assertEquals("Wrong class", SubmittorLink.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreateTrailer() {
        final TrailerDocumentMongo gmd = new TrailerDocumentMongo();
        gmd.setString("Trailer");
        final GedObject ged = GedDocumentMongoFactory.getInstance().
                createGedObject(new Root(null), gmd);
        assertEquals("Wrong class", Trailer.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreateWife() {
        final WifeDocumentMongo gmd = new WifeDocumentMongo();
        gmd.setString("Wife");
        final GedObject ged = GedDocumentMongoFactory.getInstance().
                createGedObject(new Root(null), gmd);
        assertEquals("Wrong class", Wife.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreateMultimedia() {
        final MultimediaDocumentMongo gmd = new MultimediaDocumentMongo();
        gmd.setString("Multimedia");
        final GedObject ged = GedDocumentMongoFactory.getInstance().
                createGedObject(new Root(null), gmd);
        assertEquals("Wrong class", Multimedia.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreateName() {
        final NameDocumentMongo gmd = new NameDocumentMongo();
        gmd.setString("Name");
        final GedObject ged = GedDocumentMongoFactory.getInstance().
                createGedObject(new Root(null), gmd);
        assertEquals("Wrong class", Name.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreateGed() {
        @SuppressWarnings("PMD.NonStaticInitializer")
        final GedDocument<?> gmd = new GedDocumentMongo<GedObject>() {
            {
                setType("foo");
            }
            /**
             * {@inheritDoc}
             */
            @Override
            public void loadGedObject(final GedObject ged) {
                // Intentionally empty
            }
        };
        gmd.setString("Foo");
        GedObject ged = null;
        try {
            ged = GedDocumentMongoFactory.getInstance().
                    createGedObject(new Root(null), gmd);
            fail("Should not get here");
        } catch (PersistenceException e) {
            assertNull("Result should be null", ged);
        }
    }
}
