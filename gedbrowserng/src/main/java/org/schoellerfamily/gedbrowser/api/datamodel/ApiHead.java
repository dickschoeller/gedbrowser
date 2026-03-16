package org.schoellerfamily.gedbrowser.api.datamodel;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonPOJOBuilder;

/**
 * Represents the HEAD object of the loaded GEDCOM. Generally not needed for UI rendering, but
 * is useful in data export.
 *
 * @author Dick Schoeller
 */
@SuperBuilder(toBuilder = true)
@Getter
@EqualsAndHashCode(callSuper = true)
@JsonDeserialize(builder = ApiHead.ApiHeadBuilder.class)
public final class ApiHead extends ApiObject {
    /**
     * The Builder for ApiHead.
     *
     * @param <C> the class to be built
     * @param <B> the type of the builder
     */
    @JsonPOJOBuilder(withPrefix = "")
    public abstract static class ApiHeadBuilder<
            C extends ApiHead,
            B extends ApiHeadBuilder<C, B>>
        extends ApiObjectBuilder<C, B> {
        /**
         * Jackson creator to obtain a concrete Lombok builder implementation.
         *
         * @return new head builder instance
         */
        @JsonCreator
        @SuppressWarnings("java:S1452")
        public static ApiHeadBuilder<?, ?> create() {
            return ApiHead.builder();
        }
    }

    /**
     * Executes accept.
     *
     * @param visitor the visitor
     */
    @Override
    public void accept(final ApiObjectVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Returns the boolean.
     *
     * @param other the other
     * @return the resulting boolean
     */
    @Override
    protected boolean canEqual(final Object other) {
        return other.getClass() == ApiHead.class;
    }
}
