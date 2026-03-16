package org.schoellerfamily.gedbrowser.api.datamodel;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.schoellerfamily.gedbrowser.renderer.PlaceInfo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.experimental.SuperBuilder;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonPOJOBuilder;

/**
 * A person in the API data model.
 *
 * @author Dick Schoeller
 */
@SuperBuilder(toBuilder = true)
@Getter
@EqualsAndHashCode(callSuper = true)
@JsonDeserialize(builder = ApiPerson.ApiPersonBuilder.class)
@JsonPropertyOrder({ "indexName", "surname", "lifeSpan", "places" })
public class ApiPerson extends ApiExtraLists {
    /**
     * The name in a form that is usable for indexing.
     */
    @Builder.Default
    @NonNull
    private final String indexName = "?, ?";

    /**
     * The surname.
     */
    @Builder.Default
    @NonNull
    private final String surname = "?";

    /**
     * The lifespan of this person.
     */
    private final ApiLifespan lifespan;

    /**
     * The list of places associated with this person.
     */
    @Singular(value = "place")
    private final List<PlaceInfo> places;

    /**
     * The Builder for ApiPerson.
     *
     * @param <C> the class to be built
     * @param <B> the type of the builder
     */
    @JsonPOJOBuilder(withPrefix = "")
    public abstract static class ApiPersonBuilder<
            C extends ApiPerson,
            B extends ApiPersonBuilder<C, B>>
        extends ApiExtraListsBuilder<C, B> {
        /**
         * Jackson creator to obtain a concrete Lombok builder implementation.
         *
         * @return new person builder instance
         */
        @JsonCreator
        @SuppressWarnings("java:S1452")
        public static ApiPersonBuilder<?, ?> create() {
            return ApiPerson.builder();
        }

        /**
         * Sets the string field and also extracts the reference number
         * attribute from it.
         *
         * @param string the string
         * @return this
         */
        @Override
        public B string(final String string) {
            if (StringUtils.isNotBlank(string) && getAttributes() != null) {
                getAttributes().removeIf(attr -> attr.isType("attribute")
                    && attr.getString().equals("Reference Number"));
                attribute(refn(string));
            }
            return super.string(string);
        }

        private ApiAttribute refn(final String string) {
            return ApiAttribute.builder()
                .type("attribute")
                .string("Reference Number")
                    .tail(string.replaceAll("[A-Za-z]", ""))
                    .build();
        }
    }

    /**
     * Get the surname. Will return "?" if surname is blank.
     *
     * @return the surname
     */
    public final String getSurname() {
        if (surname.isBlank()) {
            return "?";
        }
        return surname;
    }

    /**
     * Get the index name. Will return "?, ?" if index name is blank.
     *
     * @return the index name
     */
    public final String getIndexName() {
        if (indexName.isBlank()) {
            return "?, ?";
        }
        return indexName;
    }

    @Override
    public final void accept(final ApiObjectVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Is the other object of exactly the same type as this one? All overrides
     * should use the same approach.
     */
    @Override
    public final boolean canEqual(final Object other) {
        return other.getClass() == ApiPerson.class;
    }
}
