package org.schoellerfamily.gedbrowser.api.datamodel;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import tools.jackson.databind.annotation.JsonDeserialize;

/**
 * Represents the HEAD object of the loaded GEDCOM. Generally not needed for UI rendering, but
 * is useful in data export.
 *
 * @author Dick Schoeller
 */
@SuperBuilder(toBuilder = true)
@Getter
@EqualsAndHashCode(callSuper = true)
@Jacksonized
@JsonDeserialize(builder = ApiHead.ApiHeadBuilderImpl.class)
public final class ApiHead extends ApiObject {
    /**
     * @param visitor the visitor
     */
    public void accept(final ApiObjectVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    protected boolean canEqual(final Object other) {
        return other.getClass() == ApiHead.class;
    }
}
