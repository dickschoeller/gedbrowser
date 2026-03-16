package org.schoellerfamily.gedbrowser.persistence.mongo.domain;

import org.schoellerfamily.gedbrowser.persistence.mongo.domain.visitor.GedDocumentMongoVisitor;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.visitor.TopLevelGedDocumentMongoVisitor;

/**
 * Defines persistence operations for accepts.
 *
 * @author Richard Schoeller
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
