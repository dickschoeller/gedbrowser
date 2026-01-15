package org.schoellerfamily.gedbrowser.persistence.mongo.domain;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Wife;
import org.schoellerfamily.gedbrowser.persistence.GedDocumentLoader;
import org.schoellerfamily.gedbrowser.persistence.PersistenceException;
import org.schoellerfamily.gedbrowser.persistence.domain.WifeDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.visitor.GedDocumentMongoVisitor;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.visitor.TopLevelGedDocumentMongoVisitor;

/**
 * @author Dick Schoeller
 */
public final class WifeDocumentMongo extends GedDocumentMongo<Wife>
        implements WifeDocument {
    @Override
    public String getType() {
        return "wife";
    }

    @Override
    public void loadGedObject(final GedDocumentLoader loader,
            final GedObject ged) {
        if (!(ged instanceof Wife)) {
            throw new PersistenceException("Wrong type");
        }
        final Wife gedObject = (Wife) ged;
        this.setGedObject(gedObject);
        this.setString(gedObject.getToString());
        this.setFilename(gedObject.getFilename());
        loader.loadAttributes(this, gedObject.getAttributes());
    }

    @Override
    public void accept(final TopLevelGedDocumentMongoVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void accept(final GedDocumentMongoVisitor visitor) {
        visitor.visit(this);
    }
}
