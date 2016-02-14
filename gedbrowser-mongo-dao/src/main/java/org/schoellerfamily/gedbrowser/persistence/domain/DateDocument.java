package org.schoellerfamily.gedbrowser.persistence.domain;

import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.persistence.PersistenceException;

/**
 * @author Dick Schoeller
 */
public class DateDocument extends GedDocument<Date> {
    /**
     * Constructor.
     */
    public DateDocument() {
        setType("date");
    }

    @Override
    public final void loadGedObject(final GedObject ged) {
        if (!(ged instanceof Date)) {
            throw new PersistenceException("Wrong type");
        }
        final Date gedObject = (Date) ged;
        this.setGedObject(gedObject);
        this.setString(gedObject.getString());
        this.setFilename(gedObject.getFilename());
        this.loadAttributes(gedObject.getAttributes());
    }
}
