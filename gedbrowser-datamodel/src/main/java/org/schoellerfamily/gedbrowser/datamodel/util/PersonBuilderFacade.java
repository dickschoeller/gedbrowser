package org.schoellerfamily.gedbrowser.datamodel.util;

import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Multimedia;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.Person;

/**
 * @author Dick Schoeller
 */
public interface PersonBuilderFacade extends PersonBuilder {
    /**
     * @return the person builder
     */
    PersonBuilder getPersonBuilder();

    /**
     * {@inheritDoc}
     */
    @Override
    default Person createPerson() {
        return getPersonBuilder().createPerson();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default Person createPerson(String idString) {
        return getPersonBuilder().createPerson(idString);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default Person createPerson(String idString, String name) {
        return getPersonBuilder().createPerson(idString, name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default Name addNameToPerson(Person person, String string) {
        return getPersonBuilder().addNameToPerson(person, string);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default Attribute createPersonEvent(Person person, String type,
            String dateString) {
        return getPersonBuilder().createPersonEvent(person, type, dateString);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default Attribute createPersonEvent(Person person, String type) {
        return getPersonBuilder().createPersonEvent(person, type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default Multimedia addMultimediaToPerson(Person person, String string) {
        return getPersonBuilder().addMultimediaToPerson(person, string);
    }
}
