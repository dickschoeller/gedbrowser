package org.schoellerfamily.gedbrowser.api.transformers;

import org.schoellerfamily.gedbrowser.api.datamodel.ApiNote;
import org.schoellerfamily.gedbrowser.persistence.domain.NoteDocument;

/**
 * The visitor for NoteDocument.
 *
 * @author Richard Schoeller
 */
/* default */ interface NoteDocumentVisitor extends GedDocumentBaseVisitor {
    @Override
    default void visit(final NoteDocument document) {
        setBaseObject(ApiNote.builder()
            .type(document.getType())
            .string(document.getString())
            .tail(document.getTail())
            .attributes(processAttributes(document))
            .build());
    }
}
