package org.schoellerfamily.gedbrowser.api.datamodel;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonPOJOBuilder;

/**
 * The family data model object for the API.
 *
 * @author Dick Schoeller
 */
@SuperBuilder(toBuilder = true)
@Getter
@EqualsAndHashCode(callSuper = true)
@Jacksonized
@JsonDeserialize(builder = ApiFamily.ApiFamilyBuilderImpl.class)
@JsonPropertyOrder({ "children", "spouses" })
public class ApiFamily extends ApiHasImages {
    /**
     * The list of child attributes of this object.
     */
    @Singular
    private final List<ApiAttribute> children;

    /**
     * The list of husband, wife, spouse attributes of this object.
     */
    @Singular
    private final List<ApiAttribute> spouses;

    /**
     * @param visitor the visitor
     */
    public void accept(final ApiObjectVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * The Builder for ApiFamily.
     *
     * @param <C> the class to be built
     * @param <B> the type of the builder
     */
    @JsonPOJOBuilder(withPrefix="")
    public static abstract class ApiFamilyBuilder<C extends ApiFamily, B extends ApiFamilyBuilder<C, B>> extends ApiHasImagesBuilder<C, B> {
        public B attribute(final ApiAttribute attribute) {
            if (attribute.isType("husband") || attribute.isType("wife")
                    || attribute.isType("spouse")) {
                return spouse(attribute);
            }
            if (attribute.isType("child")) {
                return child(attribute);
            }
            if (new ImageUtils().isImageWrapper(attribute)) {
                return image(attribute);
            }
            return super.attribute(attribute);
        }

        public B attributes(final List<ApiAttribute> attributes) {
            for (final ApiAttribute attribute : attributes) {
                attribute(attribute);
            }
            return self();
        }

        public B addSpouse(final int index, final ApiAttribute spouse) {
            this.spouses.add(index, spouse);
            return self();
        }

        public List<ApiAttribute> getSpouses() {
            return this.spouses;
        }

        public List<ApiAttribute> getChildren() {
            return this.children;
        }
    }

    @Override
    public boolean canEqual(final Object other) {
        return other.getClass() == ApiFamily.class;
    }
}
