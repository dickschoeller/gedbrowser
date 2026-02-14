package org.schoellerfamily.gedbrowser.api.datamodel;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import tools.jackson.databind.annotation.JsonDeserialize;

/**
 * Represents a source for the person, family or other attribute that it's attached to.
 *
 * @author Dick Schoeller
 */
@SuperBuilder(toBuilder = true)
@Getter
@EqualsAndHashCode(callSuper = true)
@Jacksonized
@JsonDeserialize(builder = ApiSource.ApiSourceBuilderImpl.class)
public final class ApiSource extends ApiHasImages {
    /**
     * The title string.
     */
    private final String title;

    @Override
    public void accept(final ApiObjectVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean canEqual(final Object other) {
        return other.getClass() == ApiSource.class;
    }
}
