package org.schoellerfamily.gedbrowser.api.transformers;

import org.schoellerfamily.gedbrowser.persistence.domain.NoteLinkDocument;

/**
 * The visitor for NoteLinkDocument.
 *
 * @author Richard Schoeller
 */
/* default */ interface NoteLinkDocumentVisitor extends GedDocumentBaseVisitor {
    @Override
    default void visit(final NoteLinkDocument document) {
        setBaseObject(createAttribute(document));
    }
}
