package org.schoellerfamily.gedbrowser.api.transformers;

import org.schoellerfamily.gedbrowser.persistence.domain.DateDocument;

/**
 * The visitor for DateDocument.
 *
 * @author Richard Schoeller
 */
/* default */ interface DateDocumentVisitor extends GedDocumentBaseVisitor {
    @Override
    default void visit(final DateDocument document) {
        setBaseObject(createAttribute(document));
    }
}
