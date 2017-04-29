package org.schoellerfamily.gedbrowser.persistence.mongo.domain.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
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
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.GedDocumentMongoVisitor;
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
    public void testCreateAttribute() {
        final AttributeDocumentMongo gmd = new AttributeDocumentMongo();
        gmd.setString("Attribute");
        final GedObject ged = GedDocumentMongoFactory.getInstance().
                createGedObject(new Root(), gmd);
        assertEquals("Wrong class", Attribute.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreateChild() {
        final ChildDocumentMongo gmd = new ChildDocumentMongo();
        gmd.setString("Child");
        final GedObject ged = GedDocumentMongoFactory.getInstance().
                createGedObject(new Root(), gmd);
        assertEquals("Wrong class", Child.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreateDate() {
        final DateDocumentMongo gmd = new DateDocumentMongo();
        gmd.setString("Date");
        final GedObject ged = GedDocumentMongoFactory.getInstance().
                createGedObject(new Root(), gmd);
        assertEquals("Wrong class", Date.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreateFamily() {
        final FamilyDocumentMongo gmd = new FamilyDocumentMongo();
        gmd.setString("Family");
        final GedObject ged = GedDocumentMongoFactory.getInstance().
                createGedObject(new Root(), gmd);
        assertEquals("Wrong class", Family.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreateFamC() {
        final FamCDocumentMongo gmd = new FamCDocumentMongo();
        gmd.setString("FamC");
        final GedObject ged = GedDocumentMongoFactory.getInstance().
                createGedObject(new Root(), gmd);
        assertEquals("Wrong class", FamC.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreateFamS() {
        final FamSDocumentMongo gmd = new FamSDocumentMongo();
        gmd.setString("FamS");
        final GedObject ged = GedDocumentMongoFactory.getInstance().
                createGedObject(new Root(), gmd);
        assertEquals("Wrong class", FamS.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreateHead() {
        final HeadDocumentMongo gmd = new HeadDocumentMongo();
        gmd.setString("Head");
        final GedObject ged = GedDocumentMongoFactory.getInstance().
                createGedObject(new Root(), gmd);
        assertEquals("Wrong class", Head.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreateHusband() {
        final HusbandDocumentMongo gmd = new HusbandDocumentMongo();
        gmd.setString("Husband");
        final GedObject ged = GedDocumentMongoFactory.getInstance().
                createGedObject(new Root(), gmd);
        assertEquals("Wrong class", Husband.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreatePerson() {
        final PersonDocumentMongo gmd = new PersonDocumentMongo();
        gmd.setString("Person");
        final GedObject ged = GedDocumentMongoFactory.getInstance().
                createGedObject(new Root(), gmd);
        assertEquals("Wrong class", Person.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreatePlace() {
        final PlaceDocumentMongo gmd = new PlaceDocumentMongo();
        gmd.setString("Place");
        final GedObject ged = GedDocumentMongoFactory.getInstance().
                createGedObject(new Root(), gmd);
        assertEquals("Wrong class", Place.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreateSource() {
        final SourceDocumentMongo gmd = new SourceDocumentMongo();
        gmd.setString("Source");
        final GedObject ged = GedDocumentMongoFactory.getInstance().
                createGedObject(new Root(), gmd);
        assertEquals("Wrong class", Source.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreateSourceLink() {
        final SourceLinkDocumentMongo gmd = new SourceLinkDocumentMongo();
        gmd.setString("SourceLink");
        final GedObject ged = GedDocumentMongoFactory.getInstance().
                createGedObject(new Root(), gmd);
        assertEquals("Wrong class", SourceLink.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreateSubmittor() {
        final SubmittorDocumentMongo gmd = new SubmittorDocumentMongo();
        gmd.setString("Submittor");
        final GedObject ged = GedDocumentMongoFactory.getInstance().
                createGedObject(new Root(), gmd);
        assertEquals("Wrong class", Submittor.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreateSubmittorLink() {
        final SubmittorLinkDocumentMongo gmd = new SubmittorLinkDocumentMongo();
        gmd.setString("SubmittorLink");
        final GedObject ged = GedDocumentMongoFactory.getInstance().
                createGedObject(new Root(), gmd);
        assertEquals("Wrong class", SubmittorLink.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreateTrailer() {
        final TrailerDocumentMongo gmd = new TrailerDocumentMongo();
        gmd.setString("Trailer");
        final GedObject ged = GedDocumentMongoFactory.getInstance().
                createGedObject(new Root(), gmd);
        assertEquals("Wrong class", Trailer.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreateWife() {
        final WifeDocumentMongo gmd = new WifeDocumentMongo();
        gmd.setString("Wife");
        final GedObject ged = GedDocumentMongoFactory.getInstance().
                createGedObject(new Root(), gmd);
        assertEquals("Wrong class", Wife.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreateMultimedia() {
        final MultimediaDocumentMongo gmd = new MultimediaDocumentMongo();
        gmd.setString("Multimedia");
        final GedObject ged = GedDocumentMongoFactory.getInstance().
                createGedObject(new Root(), gmd);
        assertEquals("Wrong class", Multimedia.class, ged.getClass());
    }

    /** */
    @Test
    public void testCreateName() {
        final NameDocumentMongo gmd = new NameDocumentMongo();
        gmd.setString("Name");
        final GedObject ged = GedDocumentMongoFactory.getInstance().
                createGedObject(new Root(), gmd);
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
            public void loadGedObject(final GedObject ged) {
                // Intentionally empty
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
            ged = GedDocumentMongoFactory.getInstance().
                    createGedObject(new Root(), gmd);
            fail("Should not get here");
        } catch (PersistenceException e) {
            assertNull("Result should be null", ged);
        }
    }
}
