package org.schoellerfamily.gedbrowser.api.transformers;

import org.schoellerfamily.gedbrowser.persistence.domain.SubmitterLinkDocument;

/**
 * The visitor for SubmitterLinkDocument.
 *
 * @author Richard Schoeller
 */
/* default */ interface SubmitterLinkDocumentVisitor extends GedDocumentBaseVisitor {
    @Override
    default void visit(final SubmitterLinkDocument document) {
        setBaseObject(createAttribute(document));
    }
}
