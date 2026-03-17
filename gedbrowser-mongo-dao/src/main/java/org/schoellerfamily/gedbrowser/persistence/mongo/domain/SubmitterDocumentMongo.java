package org.schoellerfamily.gedbrowser.persistence.mongo.domain;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Submitter;
import org.schoellerfamily.gedbrowser.persistence.GedDocumentLoader;
import org.schoellerfamily.gedbrowser.persistence.PersistenceException;
import org.schoellerfamily.gedbrowser.persistence.domain.SubmitterDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.visitor.GedDocumentMongoVisitor;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.visitor.TopLevelGedDocumentMongoVisitor;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.NoArgsConstructor;

/**
 * Represents submitter document mongo for persistence operations.
 *
 * @author Richard Schoeller
 */
@Document(collection = "submitters")
@CompoundIndexes({
    @CompoundIndex(name = "submitter_unique_idx",
            def = "{'string': 1, 'filename': 1}",
            unique = true)
})
@NoArgsConstructor
public final class SubmitterDocumentMongo extends GedDocumentMongo<Submitter>
        implements SubmitterDocument {
    /**
     * Gets the type.
     *
     * @return the type
     */
    @Override
    public String getType() {
        return "submitter";
    }

    /**
     * Loads the ged object.
     *
     * @param loader the loader
     */
    @Override
    public void loadGedObject(final GedDocumentLoader loader,
            final GedObject ged) {
        if (!(ged instanceof Submitter)) {
            throw new PersistenceException("Wrong type");
        }
        final Submitter gedObject = (Submitter) ged;
        this.setGedObject(gedObject);
        this.setString(gedObject.getString());
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
