package org.schoellerfamily.gedbrowser.persistence.mongo.domain;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Trailer;
import org.schoellerfamily.gedbrowser.persistence.GedDocumentLoader;
import org.schoellerfamily.gedbrowser.persistence.PersistenceException;
import org.schoellerfamily.gedbrowser.persistence.domain.TrailerDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.visitor.GedDocumentMongoVisitor;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.visitor.TopLevelGedDocumentMongoVisitor;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Dick Schoeller
 */
@Document(collection = "trailers")
@CompoundIndexes({
    @CompoundIndex(name = "trailer_unique_idx",
            def = "{'string': 1, 'filename': 1}",
            unique = true)
})
public class TrailerDocumentMongo extends GedDocumentMongo<Trailer>
        implements TrailerDocument {
    /**
     * {@inheritDoc}
     */
    @Override
    public final String getType() {
        return "trailer";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void loadGedObject(final GedDocumentLoader loader,
            final GedObject ged) {
        if (!(ged instanceof Trailer)) {
            throw new PersistenceException("Wrong type");
        }
        final Trailer gedObject = (Trailer) ged;
        this.setGedObject(gedObject);
        this.setString(gedObject.getString());
        this.setFilename(gedObject.getFilename());
        loader.loadAttributes(this, gedObject.getAttributes());
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
