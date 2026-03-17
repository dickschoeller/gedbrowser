package org.schoellerfamily.gedbrowser.api.datamodel;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.schoellerfamily.gedbrowser.datamodel.GetString;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;
import lombok.experimental.SuperBuilder;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonPOJOBuilder;



/**
 * Represents api object in the domain model.
 *
 * @author Richard Schoeller
 */
@SuperBuilder(toBuilder = true)
@Getter
@EqualsAndHashCode
@JsonDeserialize(builder = ApiObject.ApiObjectBuilder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "type", "string", "attributes" })
public class ApiObject implements GetString {
    /**
     * A string describing the data type of this object.
     */
    @Builder.Default
    private final String type = "";

    /**
     * A string containing the primary value of this object. In Attributes this will
     * be the sub-type and tail will contain the data value.
     */
    @Builder.Default
    private final String string = "";

    /**
     * The list of subordinate attributes of this object.
     */
    @Singular
    private final List<ApiAttribute> attributes;

    /**
     * Executes accept.
     *
     * @param visitor the visitor
     */
    public void accept(final ApiObjectVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Check this object against a sought after type string.
     *
     * @param t type we want to match
     * @return true if this object matches
     */
    public boolean isType(final String t) {
        final String dt = decode(t);
        if (dt.equals(getType())) {
            return true;
        }
        return "attribute".equals(getType()) && dt.equalsIgnoreCase(getString());
    }

    private String decode(final String t) {
        return URLDecoder.decode(t, StandardCharsets.UTF_8);
    }

    /**
     * We only allow equality with objects of the exactly the same class.
     *
     * @param other the other object
     * @return true if other can equal this
     */
    protected boolean canEqual(final Object other) {
        final boolean can = other.getClass() == ApiObject.class;
        return can;
    }

    /**
     * The Builder for ApiObject.
     *
     * @param <C> the class to be built
     * @param <B> the type of the builder
     */
    @JsonPOJOBuilder(withPrefix = "")
    public abstract static class ApiObjectBuilder<
        C extends ApiObject,
        B extends ApiObjectBuilder<C, B>> {

        /**
         * Jackson creator to obtain a concrete Lombok builder implementation.
         *
         * @return new object builder instance
         */
        @JsonCreator
        @SuppressWarnings("java:S1452")
        public static ApiObjectBuilder<?, ?> create() {
            return ApiObject.builder();
        }

        /**
         * Get the type value.
         *
         * @return the type value
         */
        public String getType() {
            return this.type$value;
        }

        /**
         * Get the string value.
         *
         * @return the string value
         */
        public String getString() {
            return this.string$value;
        }

        /**
         * Get the attributes.
         *
         * @return the attributes
         */
        public List<ApiAttribute> getAttributes() {
            return this.attributes;
        }
    }
}
