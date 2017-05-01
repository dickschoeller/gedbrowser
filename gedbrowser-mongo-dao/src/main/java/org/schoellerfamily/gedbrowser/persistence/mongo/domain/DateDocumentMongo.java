package org.schoellerfamily.gedbrowser.persistence.mongo.domain;

import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.persistence.PersistenceException;
import org.schoellerfamily.gedbrowser.persistence.domain.DateDocument;

/**
 * @author Dick Schoeller
 */
public class DateDocumentMongo extends GedDocumentMongo<Date>
        implements DateDocument {
    /**
     * {@inheritDoc}
     */
    @Override
    public final String getType() {
        return "date";
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(final TopLevelGedDocumentMongoVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(final GedDocumentMongoVisitor visitor) {
        visitor.visit(this);
    }
}
