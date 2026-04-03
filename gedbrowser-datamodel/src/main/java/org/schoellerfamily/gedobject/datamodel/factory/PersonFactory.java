package org.schoellerfamily.gedobject.datamodel.factory;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;

/**
 * Factory for creating persons.
 *
 * @author Richard Schoeller
 */
/* default */ class PersonFactory extends AbstractGedObjectFactory {
    static {
        put("INDI", "Person", new PersonFactory());
    }

    @Override
    public GedObject create(final GedObject parent, final ObjectId xref, final String tag,
            final String tail) {
        return new Person(parent, xref);
    }
}
