package org.schoellerfamily.gedbrowser.persistence.domain;

import org.schoellerfamily.gedbrowser.datamodel.Date;

/**
 * Represents the persisted form of date.
 *
 * @author Richard Schoeller
 */
public interface DateDocument extends GedDocument<Date> {
    @Override
    default void accept(final GedDocumentVisitor visitor) {
        visitor.visit(this);
    }
}
