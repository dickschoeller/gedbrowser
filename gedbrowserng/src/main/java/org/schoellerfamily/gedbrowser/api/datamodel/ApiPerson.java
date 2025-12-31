package org.schoellerfamily.gedbrowser.api.datamodel;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
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
@Jacksonized
@JsonDeserialize(builder = ApiPerson.ApiPersonBuilderImpl.class)
@JsonPropertyOrder({ "indexName", "surname", "lifeSpan" })
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
     * The Builder for ApiPerson.
     *
     * @param <C> the class to be built
     * @param <B> the type of the builder
     */
    @JsonPOJOBuilder(withPrefix="")
    public static abstract class ApiPersonBuilder<C extends ApiPerson, B extends ApiPersonBuilder<C, B>> extends ApiExtraListsBuilder<C, B> {
        @Override
        public B string(final String string) {
            if (StringUtils.isNotBlank(string) && getAttributes() != null) {
                getAttributes().removeIf(attr -> attr.isType("attribute")
                    && attr.getString().equals("Reference Number"));
                attribute(refn(string));
            }
            return super.string(string);
        }

        /**
         * Returns the reference number attribute extracted from the person string.
         *
         * @param string the person string
         * @return the reference number extracted from that
         */
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
    public String getSurname() {
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
    public String getIndexName() {
        if (indexName.isBlank()) {
            return "?, ?";
        }
        return indexName;
    }

    /**
     * @param visitor the visitor
     */
    public void accept(final ApiObjectVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean canEqual(final Object other) {
        return other.getClass() == ApiPerson.class;
    }
}
