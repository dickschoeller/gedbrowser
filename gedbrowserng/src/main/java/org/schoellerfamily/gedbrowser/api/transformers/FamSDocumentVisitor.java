package org.schoellerfamily.gedbrowser.api.transformers;

import org.schoellerfamily.gedbrowser.persistence.domain.FamSDocument;

/**
 * The visitor for FamSDocument.
 *
 * @author Richard Schoeller
 */
/* default */ interface FamSDocumentVisitor extends GedDocumentBaseVisitor {
    @Override
    default void visit(final FamSDocument document) {
        setBaseObject(createAttribute(document));
    }
}
