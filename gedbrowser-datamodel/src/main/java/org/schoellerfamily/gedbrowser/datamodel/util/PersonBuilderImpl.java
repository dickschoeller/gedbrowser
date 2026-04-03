package org.schoellerfamily.gedbrowser.datamodel.util;

import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.Multimedia;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;

/**
 * Represents person builder impl in the domain model.
 *
 * @author Richard Schoeller
 */
public final class PersonBuilderImpl implements PersonBuilder {
    /**
     * The ged object builder value.
     */
    private final GedObjectBuilder gedObjectBuilder;

    /**
     * Creates a new PersonBuilderImpl.
     *
     * @param gedObjectBuilder the ged object builder
     */
    public PersonBuilderImpl(final GedObjectBuilder gedObjectBuilder) {
        this.gedObjectBuilder = gedObjectBuilder;
    }

    /**
     * Gets the root.
     *
     * @return the root
     */
    @Override
    public Root getRoot() {
        return gedObjectBuilder.getRoot();
    }

    /**
     * Creates the person.
     *
     * @return the resulting person
     */
    @Override
    public Person createPerson() {
        return new Person();
    }

    /**
     * Creates the person.
     *
     * @param idString the id string
     * @return the resulting person
     */
    @Override
    public Person createPerson(final String idString) {
        if (idString == null) {
            return new Person();
        }
        final Person person = new Person(getRoot(), new ObjectId(idString));
        getRoot().insert(person);
        return person;
    }

    /**
     * Creates the person.
     *
     * @param idString the id string
     * @param name the name to use
     * @return the resulting person
     */
    @Override
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
     * Executes add name to person.
     *
     * @param person the person
     * @param string the string
     * @return the resulting name
     */
    @Override
    public Name addNameToPerson(final Person person, final String string) {
        if (person == null || string == null) {
            return new Name();
        }
        final Name name = new Name(person, string);
        person.insert(name);
        return name;
    }


    /**
     * Creates the person event.
     *
     * @param person the person
     * @param type the type to use
     * @return the resulting attribute
     */
    @Override
    public Attribute createPersonEvent(final Person person, final String type,
            final String dateString) {
        final Attribute event = gedObjectBuilder.createAttribute(person, type);
        event.insert(new Date(event, dateString));
        return event;
    }

    /**
     * Creates the person event.
     *
     * @param person the person
     * @param type the type to use
     * @return the resulting attribute
     */
    @Override
    public Attribute createPersonEvent(final Person person, final String type) {
        return gedObjectBuilder.createAttribute(person, type);
    }

    /**
     * Executes add multimedia to person.
     *
     * @param person the person
     * @return the resulting multimedia
     */
    @Override
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
