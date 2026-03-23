package org.schoellerfamily.gedbrowser.api.transformers;

import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;

/**
 * The visitor for RootDocument.
 *
 * @author Richard Schoeller
 */
/* default */ interface RootDocumentVisitor extends GedDocumentBaseVisitor {
    @Override
    default void visit(final RootDocument document) {
        // Intentionally empty.
    }
}
