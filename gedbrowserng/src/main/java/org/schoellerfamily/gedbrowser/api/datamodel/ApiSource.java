package org.schoellerfamily.gedbrowser.api.datamodel;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonPOJOBuilder;

/**
 * Represents a source for the person, family or other attribute that it's attached to.
 *
 * @author Dick Schoeller
 */
@SuperBuilder(toBuilder = true)
@Getter
@EqualsAndHashCode(callSuper = true)
@JsonDeserialize(builder = ApiSource.ApiSourceBuilder.class)
public final class ApiSource extends ApiHasImages {
    /**
     * The title string.
     */
    private final String title;

    /**
     * The Builder for ApiSource.
     *
     * @param <C> the class to be built
     * @param <B> the type of the builder
     */
    @JsonPOJOBuilder(withPrefix = "")
    public abstract static class ApiSourceBuilder<
            C extends ApiSource,
            B extends ApiSourceBuilder<C, B>>
        extends ApiHasImagesBuilder<C, B> {
        /**
         * Jackson creator to obtain a concrete Lombok builder implementation.
         *
         * @return new source builder instance
         */
        @JsonCreator
        public static ApiSourceBuilder<?, ?> create() {
            return ApiSource.builder();
        }
    }

    @Override
    public void accept(final ApiObjectVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean canEqual(final Object other) {
        return other.getClass() == ApiSource.class;
    }
}
