package org.schoellerfamily.gedbrowser.persistence.mongo.domain;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Note;
import org.schoellerfamily.gedbrowser.persistence.GedDocumentLoader;
import org.schoellerfamily.gedbrowser.persistence.PersistenceException;
import org.schoellerfamily.gedbrowser.persistence.domain.NoteDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.visitor.GedDocumentMongoVisitor;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.visitor.TopLevelGedDocumentMongoVisitor;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents note document mongo for persistence operations.
 *
 * @author Richard Schoeller
 */
@Document(collection = "notes")
@CompoundIndexes({
    @CompoundIndex(name = "note_unique_idx",
            def = "{'string': 1, 'filename': 1}",
            unique = true)
})
public final class NoteDocumentMongo extends GedDocumentMongo<Note>
        implements NoteDocument {
    /** */
    private String tail;

    /**
     * Gets the type.
     *
     * @return the type
     */
    @Override
    public String getType() {
        return "note";
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
        if (!(ged instanceof Note)) {
            throw new PersistenceException("Wrong type");
        }
        final Note gedObject = (Note) ged;
        this.setGedObject(gedObject);
        this.setString(gedObject.getString());
        this.setTail(gedObject.getTail());
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
        return tail;
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
