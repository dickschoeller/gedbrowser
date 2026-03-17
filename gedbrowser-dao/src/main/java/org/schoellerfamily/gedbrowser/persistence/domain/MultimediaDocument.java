package org.schoellerfamily.gedbrowser.persistence.domain;

import org.schoellerfamily.gedbrowser.datamodel.Multimedia;

/**
 * Represents the persisted form of multimedia.
 *
 * @author Richard Schoeller
 */
public interface MultimediaDocument extends GedDocument<Multimedia> {
    /**
     * @return the tail string
     */
    String getTail();

    /**
     * @param tail the new tail string
     */
    void setTail(String tail);

    @Override
    default void accept(final GedDocumentVisitor visitor) {
        visitor.visit(this);
    }
}
