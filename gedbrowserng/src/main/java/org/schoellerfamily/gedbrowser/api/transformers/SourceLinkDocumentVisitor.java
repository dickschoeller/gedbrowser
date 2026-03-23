package org.schoellerfamily.gedbrowser.api.transformers;

import org.schoellerfamily.gedbrowser.persistence.domain.SourceLinkDocument;

/**
 * The visitor for SourceLinkDocument.
 *
 * @author Richard Schoeller
 */
/* default */ interface SourceLinkDocumentVisitor extends GedDocumentBaseVisitor {
    @Override
    default void visit(final SourceLinkDocument document) {
        setBaseObject(createAttribute(document));
    }
}
