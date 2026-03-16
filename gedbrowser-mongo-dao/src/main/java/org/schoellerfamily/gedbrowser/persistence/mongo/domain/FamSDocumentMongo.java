package org.schoellerfamily.gedbrowser.persistence.mongo.domain;

import org.schoellerfamily.gedbrowser.datamodel.FamS;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.persistence.GedDocumentLoader;
import org.schoellerfamily.gedbrowser.persistence.PersistenceException;
import org.schoellerfamily.gedbrowser.persistence.domain.FamSDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.visitor.GedDocumentMongoVisitor;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.visitor.TopLevelGedDocumentMongoVisitor;

/**
 * Represents fam s document mongo for persistence operations.
 *
 * @author Richard Schoeller
 */
public final class FamSDocumentMongo extends GedDocumentMongo<FamS> implements FamSDocument {
    /**
     * Gets the type.
     *
     * @return the type
     */
    @Override
    public String getType() {
        return "fams";
    }

    /**
     * Loads the ged object.
     *
     * @param loader the loader
     * @param ged the ged
     */
    @Override
    public void loadGedObject(final GedDocumentLoader loader,
            final GedObject ged) {
        if (!(ged instanceof FamS)) {
            throw new PersistenceException("Wrong type");
        }
        final FamS gedObject = (FamS) ged;
        this.setGedObject(gedObject);
        this.setString(gedObject.getToString());
        this.setFilename(gedObject.getFilename());
        loader.loadAttributes(this, gedObject.getAttributes());
    }

    /**
     * Executes accept.
     *
     * @param visitor the visitor
     */
    @Override
    public void accept(final TopLevelGedDocumentMongoVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Executes accept.
     *
     * @param visitor the visitor
     */
    @Override
    public void accept(final GedDocumentMongoVisitor visitor) {
        visitor.visit(this);
    }
}
