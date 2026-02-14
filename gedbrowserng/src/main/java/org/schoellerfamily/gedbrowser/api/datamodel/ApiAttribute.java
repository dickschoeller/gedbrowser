package org.schoellerfamily.gedbrowser.api.datamodel;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import tools.jackson.databind.annotation.JsonDeserialize;

/**
 * The attribute data model object for the API.
 *
 * @author Dick Schoeller
 */
@SuperBuilder(toBuilder = true)
@Getter
@EqualsAndHashCode(callSuper = true)
@Jacksonized
@JsonDeserialize(builder = ApiAttribute.ApiAttributeBuilderImpl.class)
public final class ApiAttribute extends ApiTail {
    @Override
    public void accept(final ApiObjectVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean canEqual(final Object other) {
        return other.getClass() == ApiAttribute.class;
    }
}
