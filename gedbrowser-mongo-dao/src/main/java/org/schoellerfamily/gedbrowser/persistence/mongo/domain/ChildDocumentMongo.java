package org.schoellerfamily.gedbrowser.persistence.mongo.domain;

import org.schoellerfamily.gedbrowser.datamodel.Child;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.persistence.PersistenceException;
import org.schoellerfamily.gedbrowser.persistence.domain.ChildDocument;

/**
 * @author Dick Schoeller
 */
public class ChildDocumentMongo extends GedDocumentMongo<Child>
        implements ChildDocument {
    /**
     * {@inheritDoc}
     */
    @Override
    public final String getType() {
        return "child";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void loadGedObject(final GedObject ged) {
        if (!(ged instanceof Child)) {
            throw new PersistenceException("Wrong type");
        }
        final Child gedObject = (Child) ged;
        this.setGedObject(gedObject);
        this.setString(gedObject.getToString());
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
