package org.schoellerfamily.gedbrowser.persistence.mongo.domain;

import org.schoellerfamily.gedbrowser.datamodel.FamC;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.persistence.GedDocumentLoader;
import org.schoellerfamily.gedbrowser.persistence.PersistenceException;
import org.schoellerfamily.gedbrowser.persistence.domain.FamCDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.visitor.GedDocumentMongoVisitor;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.visitor.TopLevelGedDocumentMongoVisitor;

/**
 * Represents fam c document mongo for persistence operations.
 *
 * @author Richard Schoeller
 */
public final class FamCDocumentMongo extends GedDocumentMongo<FamC>
        implements FamCDocument {
    /**
     * Gets the type.
     *
     * @return the type
     */
    @Override
    public String getType() {
        return "famc";
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
        if (!(ged instanceof FamC)) {
            throw new PersistenceException("Wrong type");
        }
        final FamC gedObject = (FamC) ged;
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
