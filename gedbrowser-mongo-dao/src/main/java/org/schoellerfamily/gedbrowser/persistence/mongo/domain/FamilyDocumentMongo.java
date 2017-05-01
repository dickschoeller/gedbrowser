package org.schoellerfamily.gedbrowser.persistence.mongo.domain;

import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.persistence.PersistenceException;
import org.schoellerfamily.gedbrowser.persistence.domain.FamilyDocument;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Dick Schoeller
 */
@Document(collection = "families")
@CompoundIndexes({
    @CompoundIndex(name = "family_unique_idx",
            def = "{'string': 1, 'filename': 1}",
            unique = true)
})
public class FamilyDocumentMongo extends GedDocumentMongo<Family>
        implements FamilyDocument {
    /**
     * {@inheritDoc}
     */
    @Override
    public final String getType() {
        return "family";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void loadGedObject(final GedObject ged) {
        if (!(ged instanceof Family)) {
            throw new PersistenceException("Wrong type");
        }
        final Family gedObject = (Family) ged;
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
