package org.schoellerfamily.gedbrowser.persistence.mongo.domain;

import org.schoellerfamily.gedbrowser.persistence.mongo.domain.visitor.GedDocumentMongoVisitor;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.visitor.TopLevelGedDocumentMongoVisitor;

/**
 * Define a few methods for GedDocumentMongo derivatives to implement.
 *
 * @author Dick Schoeller
 */
public interface Accepts {
    /**
     * Accept a visitor.
     *
     * @param visitor the visitor
     */
    void accept(TopLevelGedDocumentMongoVisitor visitor);

    /**
     * Accept a visitor.
     *
     * @param visitor the visitor
     */
    void accept(GedDocumentMongoVisitor visitor);
}
