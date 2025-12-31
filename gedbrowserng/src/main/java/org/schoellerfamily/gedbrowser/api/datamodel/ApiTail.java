package org.schoellerfamily.gedbrowser.api.datamodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import tools.jackson.databind.annotation.JsonDeserialize;

/**
 * An object that has a tail string.
 *
 * @author Dick Schoeller
 */
@SuperBuilder(toBuilder = true)
@Getter
@EqualsAndHashCode(callSuper = true)
@Jacksonized
@JsonDeserialize(builder = ApiTail.ApiTailBuilderImpl.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiTail extends ApiObject {
    /**
     * An additional string containing the secondary value of this object.
     */
    @Builder.Default
    @NonNull
    private final String tail = "";

    @Override
    public boolean canEqual(final Object other) {
        return other.getClass() == ApiTail.class;
    }
}
