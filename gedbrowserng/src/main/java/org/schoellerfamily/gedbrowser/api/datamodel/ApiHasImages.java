package org.schoellerfamily.gedbrowser.api.datamodel;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;
import lombok.experimental.SuperBuilder;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonPOJOBuilder;



/**
 * Represents api has images in the domain model.
 *
 * @author Richard Schoeller
 */
@SuperBuilder(toBuilder = true)
@Getter
@EqualsAndHashCode(callSuper = true)
@JsonDeserialize(builder = ApiHasImages.ApiHasImagesBuilder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiHasImages extends ApiObject {
    /**
     * The list of image attributes of this object.
     */
    @Singular
    private final List<ApiAttribute> images;

    /**
     * The Builder for ApiHasImages.
     *
     * @param <C> the class to be built
     * @param <B> the type of the builder
     */
    @JsonPOJOBuilder(withPrefix = "")
    public abstract static class ApiHasImagesBuilder<
            C extends ApiHasImages,
            B extends ApiHasImagesBuilder<C, B>>
        extends ApiObjectBuilder<C, B> {
        /**
         * Jackson creator to obtain a concrete Lombok builder implementation.
         *
         * @return new has-images builder instance
         */
        @JsonCreator
        @SuppressWarnings("java:S1452")
        public static ApiHasImagesBuilder<?, ?> create() {
            return ApiHasImages.builder();
        }
    }

    /**
     * Returns the boolean.
     *
     * @param other the other
     * @return the resulting boolean
     */
    @Override
    public boolean canEqual(final Object other) {
        return other.getClass() == ApiHasImages.class;
    }
}
