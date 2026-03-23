package org.schoellerfamily.gedbrowser.api.transformers;

import org.schoellerfamily.gedbrowser.persistence.domain.SubmissionLinkDocument;

/**
 * The visitor for SubmissionLinkDocument.
 *
 * @author Richard Schoeller
 */
/* default */ interface SubmissionLinkDocumentVisitor extends GedDocumentBaseVisitor {
    @Override
    default void visit(final SubmissionLinkDocument document) {
        setBaseObject(createAttribute(document));
    }
}
