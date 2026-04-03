package org.schoellerfamily.gedbrowser.persistence.domain;

import org.schoellerfamily.gedbrowser.datamodel.NoteLink;

/**
 * Represents the persisted form of note link.
 *
 * @author Richard Schoeller
 */
public interface NoteLinkDocument extends GedDocument<NoteLink> {
    @Override
    default void accept(final GedDocumentVisitor visitor) {
        visitor.visit(this);
    }
}
