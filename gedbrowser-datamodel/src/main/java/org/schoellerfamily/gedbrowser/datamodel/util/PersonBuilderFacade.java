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

    @Override
    default Person createPerson() {
        return getPersonBuilder().createPerson();
    }

    @Override
    default Person createPerson(final String idString) {
        return getPersonBuilder().createPerson(idString);
    }

    @Override
    default Person createPerson(final String idString, final String name) {
        return getPersonBuilder().createPerson(idString, name);
    }

    @Override
    default Name addNameToPerson(final Person person, final String string) {
        return getPersonBuilder().addNameToPerson(person, string);
    }

    @Override
    default Attribute createPersonEvent(final Person person, final String type,
            final String dateString) {
        return getPersonBuilder().createPersonEvent(person, type, dateString);
    }

    @Override
    default Attribute createPersonEvent(final Person person, final String type) {
        return getPersonBuilder().createPersonEvent(person, type);
    }

    @Override
    default Multimedia addMultimediaToPerson(final Person person, final String string) {
        return getPersonBuilder().addMultimediaToPerson(person, string);
    }
}
