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
public final class PersonBuilderImpl implements PersonBuilder {
    /** */
    private final GedObjectBuilder gedObjectBuilder;

    /**
     * Constructor.
     *
     * @param gedObjectBuilder the containing builder
     */
    public PersonBuilderImpl(final GedObjectBuilder gedObjectBuilder) {
        this.gedObjectBuilder = gedObjectBuilder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Root getRoot() {
        return gedObjectBuilder.getRoot();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Person createPerson() {
        return new Person();
    }

    /**
     * {@inheritDoc}
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
     * {@inheritDoc}
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
     * {@inheritDoc}
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
     * {@inheritDoc}
     */
    @Override
    public Attribute createPersonEvent(final Person person, final String type,
            final String dateString) {
        final Attribute event = gedObjectBuilder.createAttribute(person, type);
        event.insert(new Date(event, dateString));
        return event;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Attribute createPersonEvent(final Person person, final String type) {
        return gedObjectBuilder.createAttribute(person, type);
    }

    /**
     * {@inheritDoc}
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
