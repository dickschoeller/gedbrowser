package org.schoellerfamily.gedbrowser.datamodel.util;

import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.Multimedia;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;

/**
 * @author Dick Schoeller
 */
public final class PersonBuilder {
    /** */
    private final GedObjectBuilder gedObjectBuilder;

    /**
     * @param gedObjectBuilder the containing builder
     */
    public PersonBuilder(final GedObjectBuilder gedObjectBuilder) {
        this.gedObjectBuilder = gedObjectBuilder;
    }

    /**
     * Get the root object from the parent builder.
     *
     * @return the root object
     */
    private Root getRoot() {
        return gedObjectBuilder.getRoot();
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
        final Person person = new Person(getRoot(), new ObjectId(idString));
        getRoot().insert(person);
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
        final Person person = new Person(getRoot(), new ObjectId(idString));
        addNameToPerson(person, name);
        getRoot().insert(person);
        return person;
    }

    /**
     * @param person the person to add the name to
     * @param string the name string
     * @return the new name object
     */
    public Name addNameToPerson(final Person person, final String string) {
        if (person == null || string == null) {
            return new Name();
        }
        final Name name = new Name(person, string);
        person.insert(name);
        return name;
    }


    /**
     * Create a dated event.
     *
     * @param person the person the event is for
     * @param type the type of event
     * @param dateString the date of the event
     * @return the created event
     */
    public Attribute createPersonEvent(final Person person, final String type,
            final String dateString) {
        final Attribute event = gedObjectBuilder.createAttribute(person, type);
        event.insert(new Date(event, dateString));
        return event;
    }

    /**
     * Create an undated event.
     *
     * @param person the person the event is for
     * @param type the type of event
     * @return the created event
     */
    public Attribute createPersonEvent(final Person person, final String type) {
        return gedObjectBuilder.createAttribute(person, type);
    }

    /**
     * @param person the person to add the name to
     * @param string the reference string
     * @return the new multimedia object
     */
    public Multimedia addMultimediaToPerson(final Person person,
            final String string) {
        if (person == null || string == null) {
            return new Multimedia();
        }
        final Multimedia mm = new Multimedia(person, "Multimedia", string);
        person.insert(mm);
        return mm;
    }
}
