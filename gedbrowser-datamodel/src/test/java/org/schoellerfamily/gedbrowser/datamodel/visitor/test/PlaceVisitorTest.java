package org.schoellerfamily.gedbrowser.datamodel.visitor.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Before;
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
import org.schoellerfamily.gedbrowser.datamodel.Link;
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
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;
import org.schoellerfamily.gedbrowser.datamodel.visitor.PlaceVisitor;

/**
 * @author Dick Schoeller
 */
public class PlaceVisitorTest {
    /** */
    private transient Root root;
    /** */
    private transient Person person1;
    /** */
    private transient Person person3;
    /** */
    private transient Person person4;
    /** */
    private transient Family family6;
    /** */
    private transient Person person6;
    /** */
    private transient Person person7;

    /** */
    @Before
    public void init() {
        root = new Root();
        final GedObjectBuilder builder = new GedObjectBuilder(root);
        person1 = builder.createPerson("I1", "Richard John/Schoeller/");
        final Attribute attr =
                new Attribute(person1, "Restriction", "confidential");
        person1.insert(attr);

        builder.createPerson("I2", "Lisa Hope/Robinson/");
        person3 = builder.createPerson("I3", "Karl Frederick/Schoeller/Jr.");
        person4 = builder.createPerson("I4");
        final Attribute birth = builder.createPersonEvent(person4, "Birth");
        final Place birthPlace = new Place(birth, "Here");
        birth.insert(birthPlace);
        final Attribute death = builder.createPersonEvent(person4, "Death");
        final Place deathPlace = new Place(death, "There");
        death.insert(deathPlace);

        final Person person5 = builder.createPerson("I5",
                "Whosis/Schoeller/Jr./Huh?");
        builder.createPersonEvent(person5, "Birth", "1 JAN 1900");
        builder.createPersonEvent(person5, "Death", "1 JAN 1950");

        family6 = builder.createFamily("F6");
        builder.addChildToFamily(family6, person3);
        final Attribute marriage =
                builder.createFamilyEvent(family6, "Marriage", "1 JAN 1900");
        final Place marriagePlace = new Place(marriage, "Everywhere");
        marriage.insert(marriagePlace);

        person6 = builder.createPerson("I6");
        person7 = builder.createPerson("I7");

        builder.addHusbandToFamily(family6, person6);
        builder.addWifeToFamily(family6, person7);

        final Person person8 = builder.createPerson("I8", "Same/Name/");
        builder.createPersonEvent(person8, "Birth", "1 JAN 1950");
        final Person person9 = builder.createPerson("I9", "Same/Name/");
        builder.createPersonEvent(person9, "Birth", "1 JAN 1940");
        final Person person10 = builder.createPerson("I10", "Same/Name/");
        builder.createPersonEvent(person10, "Birth", "1 JAN 1950");
        final Person person11 = builder.createPerson("I11", "Different/Name/");
        builder.createPersonEvent(person11, "Birth", "1 JAN 1930");
    }

    /** */
    @Test
    public void testRootPlaces() {
        final PlaceVisitor visitor = new PlaceVisitor();
        root.accept(visitor);
        final Collection<Place> placeStrings = visitor.getPlaces();
        final int expected = 3;
        assertEquals("Wrong number of places", expected, placeStrings.size());
    }

    /** */
    @Test
    public void testPersonPlaces() {
        final PlaceVisitor visitor = new PlaceVisitor();
        person4.accept(visitor);
        final Collection<String> placeStrings = visitor.getPlaceStrings();
        assertEquals("Wrong number of places", 2, placeStrings.size());
    }

    /** */
    @Test
    public void testPersonPlaces6() {
        final PlaceVisitor visitor = new PlaceVisitor();
        person6.accept(visitor);
        final Collection<String> placeStrings = visitor.getPlaceStrings();
        assertEquals("Wrong number of places", 1, placeStrings.size());
    }

    /** */
    @Test
    public void testFamilyPlaces6() {
        final PlaceVisitor visitor = new PlaceVisitor();
        family6.accept(visitor);
        final Collection<String> placeStrings = visitor.getPlaceStrings();
        assertEquals("Wrong number of places", 1, placeStrings.size());
    }

    /** */
    @Test
    public void testNoImpactFromUnrelated() {
        final PlaceVisitor visitor = new PlaceVisitor();
        new Child().accept(visitor);
        new Date(null).accept(visitor);
        new FamC().accept(visitor);
        new FamS().accept(visitor);
        new Head().accept(visitor);
        new Husband().accept(visitor);
        new Link(null).accept(visitor);
        new Multimedia().accept(visitor);
        new Name().accept(visitor);
        new Source().accept(visitor);
        new SourceLink().accept(visitor);
        new Submittor().accept(visitor);
        new SubmittorLink().accept(visitor);
        new Trailer().accept(visitor);
        new Wife().accept(visitor);
        final GedObject gob = new GedObject() {
            /**
             * {@inheritDoc}
             */
            @Override
            public void accept(final GedObjectVisitor visitor) {
                visitor.visit(this);
            } };
        gob.accept(visitor);
        assertTrue("Found unexpected content", visitor.getPlaces().isEmpty());
    }

}
