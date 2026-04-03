package org.schoellerfamily.gedbrowser.api.transformers;

import org.schoellerfamily.gedbrowser.persistence.domain.NameDocument;

/**
 * The visitor for NameDocument.
 *
 * @author Richard Schoeller
 */
/* default */ interface NameDocumentVisitor extends GedDocumentBaseVisitor {
    @Override
    default void visit(final NameDocument document) {
        setBaseObject(createAttribute(document));
    }
}
