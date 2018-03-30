package org.schoellerfamily.gedbrowser.api.datamodel;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.schoellerfamily.gedbrowser.datamodel.GetString;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings({ "PMD.CyclomaticComplexity",
        "PMD.ModifiedCyclomaticComplexity", "PMD.StdCyclomaticComplexity" })
public class ApiObject implements Serializable, GetString {
    /** */
    private static final long serialVersionUID = 1L;

    /**
     * A string describing the data type of this object.
     */
    private String type;

    /**
     * A string containing the primary value of this object. In Attributes
     * this will be the sub-type and tail will contain the data value.
     */
    private String string;

    /**
     * The list of subordinate attributes of this object.
     */
    private List<ApiAttribute> attributes;

    /**
     * Constructor.
     */
    public ApiObject() {
        this("", "");
    }

    /**
     * Constructor.
     *
     * @param type a string describing the data type of this object
     * @param string a string containing the primary value of this object
     */
    public ApiObject(final String type, final String string) {
        this(type, string, new ArrayList<>());
    }

    /**
     * Constructor.
     *
     * @param type the type of data in this object
     * @param string the primary data value
     * @param attributes the list of subordinate attributes of this object
     */
    public ApiObject(final String type, final String string,
            final List<ApiAttribute> attributes) {
        super();
        this.type = type;
        this.string = string;
        if (attributes == null) {
            this.attributes = new ArrayList<>();
        } else {
            this.attributes = attributes;
        }
    }

    /**
     * Constructor.
     *
     * @param builder a builder for this object type
     */
    public ApiObject(final Builder<?> builder) {
        super();
        this.type = builder.getType();
        this.string = builder.getId();
        this.attributes = builder.getAttributes();
    }

    /**
     * @return the type string
     */
    public final String getType() {
        return type;
    }

    /**
     * @return the value string
     */
    public final String getString() {
        return string;
    }

    /**
     * @return the list of additional attributes
     */
    public final List<ApiAttribute> getAttributes() {
        return attributes;
    }

    /**
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
        return "attribute".equals(getType())
                && dt.equalsIgnoreCase(getString());
    }

    /**
     * @param t type string to decode
     * @return decoded string
     */
    private String decode(final String t) {
        try {
            return URLDecoder.decode(t, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return t;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + attributesHash();
        result = prime * result + stringHash();
        result = prime * result + typeHash();
        return result;
    }

    /**
     * @return the hash for the attributes
     */
    private int attributesHash() {
        if (attributes == null) {
            return 0;
        }
        return attributes.hashCode();
    }

    /**
     * @return the hash for the string
     */
    private int stringHash() {
        if (string == null) {
            return 0;
        }
        return string.hashCode();
    }

    /**
     * @return the hash for the type
     */
    private int typeHash() {
        if (type == null) {
            return 0;
        }
        return type.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ApiObject other = (ApiObject) obj;
        if (!listCompare(attributes, other.attributes)) {
            return false;
        }
        if (!stringCompare(string, other.string)) {
            return false;
        }
        return stringCompare(type, other.type);
    }

    /**
     * @param list the list from this object
     * @param otherList the list from another object
     * @return true if the lists are equal
     */
    protected boolean listCompare(final List<ApiAttribute> list,
            final List<ApiAttribute> otherList) {
        if (list == null) {
            if (otherList != null) {
                return false;
            }
        } else if (!list.equals(otherList)) {
            return false;
        }
        return true;
    }

    /**
     * @param thisString string from this object
     * @param otherString string from object being checked
     * @return true if they match;
     */
    protected boolean stringCompare(final String thisString,
            final String otherString) {
        if (thisString == null) {
            if (otherString != null) {
                return false;
            }
        } else if (!thisString.equals(otherString)) {
            return false;
        }
        return true;
    }

    /**
     * @author Dick Schoeller
     *
     * @param <T> the actual type
     */
    public static class Builder<T extends ApiObject.Builder<T>> {
        /** */
        private String t;
        /** */
        private String d;
        /** */
        private final List<ApiAttribute> attributes = new ArrayList<>();

        /**
         * @param type the type
         * @return this
         */
        @SuppressWarnings("unchecked")
        public T type(final String type) {
            this.t = type;
            return (T) this;
        }

        /**
         * @param id the id
         * @return this
         */
        @SuppressWarnings("unchecked")
        public T id(final String id) {
            this.d = id;
            return (T) this;
        }

        /**
         * @param attribute an attribute
         * @return this
         */
        @SuppressWarnings("unchecked")
        public T add(final ApiAttribute attribute) {
            attributes.add(attribute);
            return (T) this;
        }

        /**
         * @return the type string
         */
        /* default */ final String getType() {
            return t;
        }

        /**
         * @return the id
         */
        /* default */ final String getId() {
            return d;
        }

        /**
         * @return the attributes
         */
        /* default */ final List<ApiAttribute> getAttributes() {
            return attributes;
        }

        /**
         * Build.
         *
         * @return this
         */
        @SuppressWarnings("unchecked")
        public T build() {
            if (t == null) {
                t = "object";
            }
            if (d == null) {
                d = "";
            }
            return (T) this;
        }
    }
}
