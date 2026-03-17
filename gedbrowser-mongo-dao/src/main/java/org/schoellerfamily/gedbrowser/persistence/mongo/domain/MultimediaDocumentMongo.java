package org.schoellerfamily.gedbrowser.persistence.mongo.domain;

import lombok.NoArgsConstructor;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Multimedia;
import org.schoellerfamily.gedbrowser.persistence.GedDocumentLoader;
import org.schoellerfamily.gedbrowser.persistence.PersistenceException;
import org.schoellerfamily.gedbrowser.persistence.domain.MultimediaDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.visitor.GedDocumentMongoVisitor;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.visitor.TopLevelGedDocumentMongoVisitor;

/**
 * Represents multimedia document mongo for persistence operations.
 *
 * @author Richard Schoeller
 */
@NoArgsConstructor
public final class MultimediaDocumentMongo extends GedDocumentMongo<Multimedia>
        implements MultimediaDocument {

    /**
     * The tail value.
     */
    private String tail;

    /**
     * Gets the type.
     *
     * @return the type
     */
    @Override
    public String getType() {
        return "multimedia";
    }

    /**
     * Loads the ged object.
     *
     * @param loader the loader
     */
    @Override
    public void loadGedObject(final GedDocumentLoader loader,
            final GedObject ged) {
        if (!(ged instanceof Multimedia)) {
            throw new PersistenceException("Wrong type");
        }
        final Multimedia gedObject = (Multimedia) ged;
        this.setTail(gedObject.getTail());
        this.setGedObject(gedObject);
        this.setString(gedObject.getString());
        this.setFilename(gedObject.getFilename());
        loader.loadAttributes(this, gedObject.getAttributes());
    }

    /**
     * Gets the tail.
     *
     * @return the tail
     */
    @Override
    public String getTail() {
        return this.tail;
    }

    /**
     * Sets the tail.
     *
     * @param tail the tail
     */
    @Override
    public void setTail(final String tail) {
        this.tail = tail;
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
