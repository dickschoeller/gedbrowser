package org.schoellerfamily.gedbrowser.api.datamodel;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonPOJOBuilder;



/**
 * Represents api submitter in the domain model.
 *
 * @author Richard Schoeller
 */
@SuperBuilder(toBuilder = true)
@Getter
@EqualsAndHashCode(callSuper = true)
@JsonDeserialize(builder = ApiSubmitter.ApiSubmitterBuilder.class)
public final class ApiSubmitter extends ApiObject {
    /**
     * The name of the submitter.
     */
    private final String name;

    /**
     * The Builder for ApiSubmitter.
     *
     * @param <C> the class to be built
     * @param <B> the type of the builder
     */
    @JsonPOJOBuilder(withPrefix = "")
    public abstract static class ApiSubmitterBuilder<
            C extends ApiSubmitter,
            B extends ApiSubmitterBuilder<C, B>>
        extends ApiObjectBuilder<C, B> {
        /**
         * Jackson creator to obtain a concrete Lombok builder implementation.
         *
         * @return new submitter builder instance
         */
        @JsonCreator
        @SuppressWarnings("java:S1452")
        public static ApiSubmitterBuilder<?, ?> create() {
            return ApiSubmitter.builder();
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
        return other.getClass() == ApiSubmitter.class;
    }
}
