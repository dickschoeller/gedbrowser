package org.schoellerfamily.gedbrowser.api.datamodel;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonPOJOBuilder;

/**
 * Represents the information about the data set submitted.
 *
 * @author Dick Schoeller
 */
@SuperBuilder(toBuilder = true)
@Getter
@EqualsAndHashCode(callSuper = true)
@JsonDeserialize(builder = ApiSubmission.ApiSubmissionBuilder.class)
public final class ApiSubmission extends ApiObject {
    /**
     * The Builder for ApiSubmission.
     *
     * @param <C> the class to be built
     * @param <B> the type of the builder
     */
    @JsonPOJOBuilder(withPrefix = "")
    public abstract static class ApiSubmissionBuilder<
            C extends ApiSubmission,
            B extends ApiSubmissionBuilder<C, B>>
        extends ApiObjectBuilder<C, B> {
        /**
         * Jackson creator to obtain a concrete Lombok builder implementation.
         *
         * @return new submission builder instance
         */
        @JsonCreator
        public static ApiSubmissionBuilder<?, ?> create() {
            return ApiSubmission.builder();
        }
    }

    @Override
    public void accept(final ApiObjectVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean canEqual(final Object other) {
        return other.getClass() == ApiSubmission.class;
    }
}
