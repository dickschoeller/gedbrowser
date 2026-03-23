package org.schoellerfamily.gedbrowser.api.transformers;

import org.schoellerfamily.gedbrowser.persistence.domain.ChildDocument;

/**
 * The visitor for ChildDocument.
 *
 * @author Richard Schoeller
 */
/* default */ interface ChildDocumentVisitor extends GedDocumentBaseVisitor {
    @Override
    default void visit(final ChildDocument document) {
        setBaseObject(createAttribute(document));
    }
}
