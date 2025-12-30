package org.schoellerfamily.gedbrowser.api.datamodel;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import tools.jackson.databind.annotation.JsonDeserialize;

/**
 * Holder for multimedia image attributes.
 *
 * @author Dick Schoeller
 */
@SuperBuilder(toBuilder = true)
@Getter
@EqualsAndHashCode(callSuper = true)
@Jacksonized
@JsonDeserialize(builder = ApiHasImages.ApiHasImagesBuilderImpl.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiHasImages extends ApiObject {
    /**
     * The list of image attributes of this object.
     */
    @Singular
    protected final List<ApiAttribute> images;

    @Override
    public boolean canEqual(final Object other) {
        return other.getClass() == ApiHasImages.class;
    }
}
