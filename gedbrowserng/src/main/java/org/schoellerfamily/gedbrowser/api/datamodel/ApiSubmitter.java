package org.schoellerfamily.gedbrowser.api.datamodel;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import tools.jackson.databind.annotation.JsonDeserialize;

/**
 * Represents the person who created this data set.
 *
 * @author Dick Schoeller
 */
@SuperBuilder(toBuilder = true)
@Getter
@EqualsAndHashCode(callSuper = true)
@Jacksonized
@JsonDeserialize(builder = ApiSubmitter.ApiSubmitterBuilderImpl.class)
public final class ApiSubmitter extends ApiObject {
    /**
     * The name of the submitter.
     */
    private final String name;

    @Override
    public void accept(final ApiObjectVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean canEqual(final Object other) {
        return other.getClass() == ApiSubmitter.class;
    }
}
