package org.schoellerfamily.gedbrowser.persistence.domain;

import org.schoellerfamily.gedbrowser.datamodel.FamS;

/**
 * Represents the persisted form of fam s.
 *
 * @author Richard Schoeller
 */
public interface FamSDocument extends GedDocument<FamS> {
    @Override
    default void accept(final GedDocumentVisitor visitor) {
        visitor.visit(this);
    }
}
