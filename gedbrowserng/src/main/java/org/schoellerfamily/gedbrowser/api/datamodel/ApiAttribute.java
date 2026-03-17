package org.schoellerfamily.gedbrowser.api.datamodel;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonPOJOBuilder;



/**
 * Represents api attribute in the domain model.
 *
 * @author Richard Schoeller
 */
@SuperBuilder(toBuilder = true)
@Getter
@EqualsAndHashCode(callSuper = true)
@JsonDeserialize(builder = ApiAttribute.ApiAttributeBuilder.class)
public final class ApiAttribute extends ApiTail {
    /**
     * The Builder for ApiAttribute.
     *
     * @param <C> the class to be built
     * @param <B> the type of the builder
     */
    @JsonPOJOBuilder(withPrefix = "")
    public abstract static class ApiAttributeBuilder<
            C extends ApiAttribute,
            B extends ApiAttributeBuilder<C, B>>
        extends ApiTailBuilder<C, B> {
        /**
         * Jackson creator to obtain a concrete Lombok builder implementation.
         *
         * @return new attribute builder instance
         */
        @JsonCreator
        @SuppressWarnings("java:S1452")
        public static ApiAttributeBuilder<?, ?> create() {
            return ApiAttribute.builder();
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
    public boolean canEqual(final Object other) {
        return other.getClass() == ApiAttribute.class;
    }
}
