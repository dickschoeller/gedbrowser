package org.schoellerfamily.gedbrowser.api.datamodel;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import tools.jackson.databind.annotation.JsonDeserialize;

/**
 * Represents a note about some person, family or other attribute.
 *
 * @author Dick Schoeller
 */
@SuperBuilder(toBuilder = true)
@Getter
@EqualsAndHashCode(callSuper = true)
@Jacksonized
@JsonDeserialize(builder = ApiNote.ApiNoteBuilderImpl.class)
public final class ApiNote extends ApiTail {
    @Override
    public void accept(final ApiObjectVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean canEqual(final Object other) {
        return other.getClass() == ApiNote.class;
    }
}
