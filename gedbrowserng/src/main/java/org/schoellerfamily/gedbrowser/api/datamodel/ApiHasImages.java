package org.schoellerfamily.gedbrowser.api.datamodel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dick Schoeller
 */
public abstract class ApiHasImages extends ApiObject
        implements ApiSplitAttributes {
    /** */
    private static final long serialVersionUID = 2L;

    /**
     * The list of image attributes of this object.
     */
    private final List<ApiAttribute> images = new ArrayList<>();

    /**
     * Constructor.
     */
    public ApiHasImages() {
        super();
    }

    /**
     * Constructor.
     *
     * @param type a string describing the data type of this object
     * @param string a string containing the primary value of this object
     */
    public ApiHasImages(final String type, final String string) {
        super(type, string);
    }

    /**
     * Constructor.
     *
     * @param type a string describing the data type of this object
     * @param string a string containing the primary value of this object
     * @param attributes the list of subordinate attributes of this object
     */
    public ApiHasImages(final String type, final String string,
            final List<ApiAttribute> attributes) {
        super(type, string, attributes);
    }

    /**
     * Constructor.
     *
     * @param in object to copy
     * @param string id string
     */
    public ApiHasImages(final ApiHasImages in, final String string) {
        super(in.getType(), string, in.getAttributes());
        this.getImages().addAll(in.getImages());
    }

    /**
     * Constructor.
     *
     * @param builder a builder for this object type
     */
    public ApiHasImages(final Builder<?> builder) {
        super(builder);
    }

    /**
     * @return the list of image attributes
     */
    public final List<ApiAttribute> getImages() {
        return images;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + attributesHash(images);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        final ApiHasImages other = (ApiHasImages) obj;
        return images.equals(other.getImages());
    }

    /**
     * @author Dick Schoeller
     *
     * @param <T> the actual type
     */
    public static class Builder<T extends ApiObject.Builder<T>>
            extends ApiObject.Builder<T> {
        /**
         * Build.
         *
         * @return this
         */
        @SuppressWarnings("unchecked")
        public T build() {
            super.build();
            return (T) this;
        }
    }
}
