package org.schoellerfamily.gedbrowser.api.datamodel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonPOJOBuilder;

/**
 * An object that has a tail string.
 *
 * @author Dick Schoeller
 */
@SuperBuilder(toBuilder = true)
@Getter
@EqualsAndHashCode(callSuper = true)
@JsonDeserialize(builder = ApiTail.ApiTailBuilder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiTail extends ApiObject {
    /**
     * An additional string containing the secondary value of this object.
     */
    @Builder.Default
    @NonNull
    private final String tail = "";

    /**
     * The Builder for ApiTail.
     *
     * @param <C> the class to be built
     * @param <B> the type of the builder
     */
    @JsonPOJOBuilder(withPrefix = "")
    public abstract static class ApiTailBuilder<
            C extends ApiTail,
            B extends ApiTailBuilder<C, B>>
        extends ApiObjectBuilder<C, B> {
        /**
         * Jackson creator to obtain a concrete Lombok builder implementation.
         *
         * @return new tail builder instance
         */
        @JsonCreator
        @SuppressWarnings("java:S1452")
        public static ApiTailBuilder<?, ?> create() {
            return ApiTail.builder();
        }
    }

    /**
     * Is the other object of exactly the same type as this one? All overrides
     * should use the same approach.
     */
    @Override
    public boolean canEqual(final Object other) {
        return other.getClass() == ApiTail.class;
    }
}
