package org.schoellerfamily.gedbrowser.api.transformers;

import org.schoellerfamily.gedbrowser.api.datamodel.ApiSubmission;
import org.schoellerfamily.gedbrowser.persistence.domain.SubmissionDocument;

/**
 * The visitor SubmissionDocument.
 */
/* default */ interface SubmissionDocumentVisitor extends GedDocumentBaseVisitor {
    @Override
    default void visit(final SubmissionDocument document) {
        setBaseObject(ApiSubmission.builder()
            .type(document.getType())
            .string(document.getString())
            .attributes(processAttributes(document))
            .build());
    }
}
