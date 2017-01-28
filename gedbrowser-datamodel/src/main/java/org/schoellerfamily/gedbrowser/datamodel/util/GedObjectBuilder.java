package org.schoellerfamily.gedbrowser.datamodel.util;

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
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.SourceLink;
import org.schoellerfamily.gedbrowser.datamodel.Submittor;
import org.schoellerfamily.gedbrowser.datamodel.SubmittorLink;
import org.schoellerfamily.gedbrowser.datamodel.Trailer;
import org.schoellerfamily.gedbrowser.datamodel.Wife;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings({ "PMD.CommentSize", "PMD.GodClass" })
public class GedObjectBuilder {
    /** */
    private final Root root;

    /**
     * Constructor.
     */
    public GedObjectBuilder() {
        this(new Root(null));
    }

    /**
     * Constructor.
     *
     * @param root root of the data set we're working with
     */
    public GedObjectBuilder(final Root root) {
        this.root = root;
    }

    /**
     * Encapsulate creating person 1.
     *
     * @return the person
     */
    public Person createPerson1() {
        return createPerson("I1", "J. Random/Schoeller/");
    }

    /**
     * Encapsulate creating person 2.
     *
     * @return the person
     */
    public Person createPerson2() {
        return createPerson("I2", "Anonymous/Schoeller/");
    }

    /**
     * Encapsulate creating person 3.
     *
     * @return the person
     */
    public Person createPerson3() {
        return createPerson("I3", "Anonymous/Jones/");
    }

    /**
     * Encapsulate creating person 4.
     *
     * @return the person
     */
    public Person createPerson4() {
        return createPerson("I4", "Too Tall/Jones/");
    }

    /**
     * Encapsulate creating person 5.
     *
     * @return the person
     */
    public Person createPerson5() {
        return createPerson("I5", "Anonyma/Schoeller/");
    }

    /**
     * Create a person with the given ID and name.
     *
     * @param idString the ID
     * @return the person
     */
    public Person createPerson(final String idString) {
        if (idString == null) {
            return new Person();
        }
        final Person person = new Person(root, new ObjectId(idString));
        root.insert(person);
        return person;
    }

    /**
     * Create a person with the given ID and name.
     *
     * @param idString the ID
     * @param name the name
     * @return the person
     */
    public Person createPerson(final String idString, final String name) {
        if (idString == null || name == null) {
            return new Person();
        }
        final Person person = new Person(root, new ObjectId(idString));
        person.insert(new Name(person, name));
        root.insert(person);
        return person;
    }

    /**
     * Create a dated event that we use as the starting point for the test
     * estimate.
     *
     * @param person the person the event is for
     * @param type the type of event
     * @param dateString the date of the event
     * @return the created event
     */
    public Attribute createPersonEvent(final Person person, final String type,
            final String dateString) {
        if (person == null || type == null) {
            return new Attribute();
        }
        final Attribute event = new Attribute(person, type);
        event.insert(new Date(event, dateString));
        person.insert(event);
        return event;
    }

    /**
     * Create an undated event that we use as the starting point for the test
     * estimate.
     *
     * @param person the person the event is for
     * @param type the type of event
     * @return the created event
     */
    public Attribute createPersonEvent(final Person person, final String type) {
        if (person == null || type == null) {
            return new Attribute();
        }
        final Attribute event = new Attribute(person, type);
        person.insert(event);
        return event;
    }

    /**
     * Encapsulate creating family 1.
     *
     * @return the family
     */
    public Family createFamily1() {
        return createFamily("F1");
    }

    /**
     * Encapsulate creating family 2.
     *
     * @return the family
     */
    public Family createFamily2() {
        return createFamily("F2");
    }

    /**
     * Encapsulate creating family 3.
     *
     * @return the family
     */
    public Family createFamily3() {
        return createFamily("F3");
    }

    /**
     * Encapsulate creating family with the given ID.
     *
     * @param idString the id string for the family
     * @return the family
     */
    public Family createFamily(final String idString) {
        if (idString == null) {
            return new Family();
        }
        final Family family3 = new Family(root, new ObjectId(idString));
        root.insert(family3);
        return family3;
    }

    /**
     * Create a dated event that we use as the starting point for the test
     * estimate.
     *
     * @param family the family the event is for
     * @param type the type of event
     * @param dateString the date of the event
     * @return the created event
     */
    public Attribute createFamilyEvent(final Family family, final String type,
            final String dateString) {
        if (family == null || type == null || dateString == null) {
            return new Attribute();
        }
        final Attribute event = new Attribute(family, type);
        event.insert(new Date(event, dateString));
        family.insert(event);
        return event;
    }

    /**
     * Create a dated event that we use as the starting point for the test
     * estimate.
     *
     * @param family the family the event is for
     * @param type the type of event
     * @return the created event
     */
    public Attribute createFamilyEvent(final Family family,
            final String type) {
        if (family == null || type == null) {
            return new Attribute();
        }
        final Attribute event = new Attribute(family, type);
        family.insert(event);
        return event;
    }

    /**
     * Add a person as the husband in a family.
     * @param family the family
     * @param person the person
     *
     * @return the husband object
     */
    public Husband addHusbandToFamily(final Family family,
            final Person person) {
        if (family == null || person == null) {
            return new Husband();
        }
        final FamS famS = new FamS(person, "FAMS",
                new ObjectId(family.getString()));
        final Husband husband = new Husband(family, "Husband",
                new ObjectId(person.getString()));
        family.insert(husband);
        person.insert(famS);
        return husband;
    }

    /**
     * Add a person as the wife in a family.
     * @param family the family
     * @param person the person
     *
     * @return the wife object
     */
    public Wife addWifeToFamily(final Family family, final Person person) {
        if (family == null || person == null) {
            return new Wife();
        }
        final FamS famS = new FamS(person, "FAMS",
                new ObjectId(family.getString()));
        final Wife wife = new Wife(family, "Wife",
                new ObjectId(person.getString()));
        family.insert(wife);
        person.insert(famS);
        return wife;
    }

    /**
     * Add a person as a child in a family.
     *
     * @param person the person
     * @param family the family
     * @return the Child object
     */
    public Child addChildToFamily(final Family family, final Person person) {
        if (family == null || person == null) {
            return new Child();
        }
        final FamC famC = new FamC(person, "FAMC",
                new ObjectId(family.getString()));
        final Child child = new Child(family, "Child",
                new ObjectId(person.getString()));
        family.insert(child);
        person.insert(famC);
        return child;
    }

    /**
     * Create a submittor with the ID and name provided.
     *
     * @param idString the ID
     * @param name the name
     * @return the submittor
     */
    public Submittor createSubmittor(final String idString, final String name) {
        if (idString == null || name == null) {
            return new Submittor(root);
        }
        final Submittor submittor = new Submittor(root, idString);
        submittor.insert(new Name(submittor, name));
        root.insert(submittor);
        return submittor;
    }


    /**
     * Create a submittor with the ID provided.
     *
     * @param idString the ID
     * @return the submittor
     */
    public Submittor createSubmittor(final String idString) {
        if (idString == null) {
            return new Submittor(root);
        }
        final Submittor submittor = new Submittor(root, idString);
        root.insert(submittor);
        return submittor;
    }

    /**
     * Create a trailer for the data set.
     *
     * @return the created trailer
     */
    public Trailer createTrailer() {
        final Trailer trailer = new Trailer(root, "Trailer");
        root.insert("Trailer", trailer);
        return trailer;
    }

    /**
     * Create a head for the data set.
     *
     * @return the created trailer
     */
    public Head createHead() {
        final Head head = new Head(root, "Head");
        root.insert("Head", head);
        return head;
    }

    /**
     * Create a place with the given name and add it to the given event.
     *
     * @param event the event
     * @param placeName the place name
     * @return the place
     */
    public Place addPlaceToEvent(final Attribute event,
            final String placeName) {
        final Place place = new Place(event, placeName);
        event.insert(place);
        return place;
    }

    /**
     * @return the new source
     */
    public Source createSource1() {
        final Source source = new Source(root, new ObjectId("S1"));
        root.insert(source);
        return source;
    }

    /**
     * @param person the person to add the name to
     * @param string the name string
     * @return the new name object
     */
    public Name addNameToPerson(final Person person, final String string) {
        final Name name = new Name(person, string);
        person.insert(name);
        return name;
    }

    /**
     * @param person the person to add the name to
     * @param string the reference string
     * @return the new multimedia object
     */
    public Multimedia addMultimediaToPerson(final Person person,
            final String string) {
        final Multimedia mm = new Multimedia(person, "Multimedia", string);
        person.insert(mm);
        return mm;
    }

    /**
     * @param gob the GedObject to add the name to
     * @param string the date string
     * @return the new date object
     */
    public Date addDateToGedObject(final GedObject gob, final String string) {
        final Date date = new Date(gob, string);
        gob.insert(date);
        return date;
    }

    /**
     * @param string the source string
     * @return the source
     */
    public Source createSource(final String string) {
        final Source source = new Source(root, new ObjectId(string));
        root.insert(string, source);
        return source;
    }

    /**
     * @param ged link from ged object
     * @param source link to source
     * @return the new link
     */
    public SourceLink createSourceLink(final GedObject ged,
            final Source source) {
        final SourceLink sourceLink = new SourceLink(ged, "Source",
                new ObjectId(source.getString()));
        ged.insert(sourceLink);
        return sourceLink;
    }

    /**
     * @param ged link from ged object
     * @param submittor link to submittor
     * @return the new link
     */
    public SubmittorLink createSubmittorLink(final GedObject ged,
            final Submittor submittor) {
        final SubmittorLink submittorLink = new SubmittorLink(ged, "Submittor",
                new ObjectId(submittor.getString()));
        ged.insert(submittorLink);
        return submittorLink;
    }

    /**
     * @param ged parent
     * @param string attribute type string
     * @return the attribute
     */
    public Attribute createAttribute(final GedObject ged, final String string) {
        final Attribute attribute = new Attribute(ged, string);
        ged.insert(attribute);
        return attribute;
    }

    /**
     * @param ged parent
     * @param string attribute type string
     * @param tail attribute details
     * @return the attribute
     */
    public Attribute createAttribute(final GedObject ged, final String string,
            final String tail) {
        final Attribute attribute = new Attribute(ged, string, tail);
        ged.insert(attribute);
        return attribute;
    }
}
