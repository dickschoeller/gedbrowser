package org.schoellerfamily.gedbrowser.api.datamodel;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;
import lombok.experimental.SuperBuilder;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonPOJOBuilder;



/**
 * Represents api extra lists in the domain model.
 *
 * @author Richard Schoeller
 */
@SuperBuilder(toBuilder = true)
@Getter
@EqualsAndHashCode(callSuper = true)
@JsonDeserialize(builder = ApiExtraLists.ApiExtraListsBuilder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "famss", "famcs", "refns", "changes" })
public class ApiExtraLists extends ApiHasImages {
    /**
     * The list of fams attributes of this object.
     */
    @Singular(value = "fams")
    private final List<ApiAttribute> famss;

    /**
     * The list of famc attributes of this object.
     */
    @Singular
    private final List<ApiAttribute> famcs;

    /**
     * The list of refn attributes of this object.
     */
    @Singular
    private final List<ApiAttribute> refns;

    /**
     * The list of changed attributes of this object.
     */
    @Singular
    private final List<ApiAttribute> changes;

    /**
     * The Builder for ApiExtraLists.
     *
     * @param <C> the class to be built
     * @param <B> the type of the builder
     */
    @JsonPOJOBuilder(withPrefix = "")
    public abstract static class
        ApiExtraListsBuilder<
            C extends ApiExtraLists,
            B extends ApiExtraLists.ApiExtraListsBuilder<C, B>>
        extends ApiExtraLists.ApiHasImagesBuilder<C, B> {

        /**
         * Jackson creator to obtain a concrete Lombok builder implementation.
         *
         * @return new extra-lists builder instance
         */
        @JsonCreator
        @SuppressWarnings({ "java:S1452", "java:S3252" })
        public static ApiExtraListsBuilder<?, ?> create() {
            return ApiExtraLists.builder();
        }

        /**
         * Add a list of attributes.
         *
         * @param attributes the attributes to add
         * @return this
         */
        public B attributes(final List<ApiAttribute> attributes) {
            attributes.forEach(attribute -> attribute(attribute));
            return self();
        }

        /**
         * Add an attribute to the correct list.
         *
         * @param attribute the attribute to add
         * @return this
         */
        @Override
        public B attribute(final ApiAttribute attribute) {
            if (attribute.isType("fams")) {
                return fams(attribute);
            }
            if (attribute.isType("famc")) {
                return famc(attribute);
            }
            if (attribute.isType("Changed")) {
                return change(attribute);
            }
            if (attribute.isType("Reference Number")) {
                return refn(attribute);
            }
            if (new ImageUtils().isImageWrapper(attribute)) {
                return image(attribute);
            }
            return super.attribute(attribute);
        }

        /**
         * Get the list of family spouses.
         *
         * @return the family spouses
         */
        public List<ApiAttribute> getFamss() {
            return this.famss;
        }

        /**
         * Get the list of family children.
         *
         * @return the family children
         */
        public List<ApiAttribute> getFamcs() {
            return this.famcs;
        }

        /**
         * Mark the person as changed today.
         *
         * @return this
         */
        public final B changed() {
            final ApiAttribute chanAttr = ApiAttribute.builder()
                .type("attribute")
                .string("Changed")
                .attribute(new DateUtil().todayDateAttribute())
                .build();
            clearChanges();
            change(chanAttr);
            return self();
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
        return other.getClass() == ApiExtraLists.class;
    }
}
