package org.schoellerfamily.gedbrowser.api.datamodel;

import java.util.List;

/**
 * @author Dick Schoeller
 */
public abstract class ApiTail extends ApiObject {
    /** */
    private static final long serialVersionUID = 1L;

    /**
     * An additional string containing the secondary value of this object.
     */
    private String tail;

    /**
     * Constructor.
     */
    public ApiTail() {
        super();
    }

    /**
     * Constructor.
     *
     * @param type a string describing the data type of this object
     * @param string a string containing the primary value of this object
     * @param tail an additional string containing the secondary value of this
     *      object
     */
    public ApiTail(final String type, final String string, final String tail) {
        super(type, string);
        this.tail = tail;
    }

    /**
     * Constructor.
     *
     * @param type a string describing the data type of this object
     * @param string a string containing the primary value of this object
     */
    public ApiTail(final String type, final String string) {
        this(type, string, "");
    }

    /**
     * Constructor.
     * @param type a string describing the data type of this object
     * @param string a string containing the primary value of this object
     * @param attributes the list of subordinate attributes of this object
     * @param tail an additional string containing the secondary value of this
     *      object
     */
    public ApiTail(final String type, final String string,
            final List<ApiAttribute> attributes, final String tail) {
        super(type, string, attributes);
        this.tail = tail;
    }

    /**
     * @return an additional string containing the secondary value of this
     *      object
     */
    public String getTail() {
        return tail;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + tailHash();
        return result;
    }

    /**
     * @return the hash code for the tail
     */
    private int tailHash() {
        if (tail == null) {
            return 0;
        }
        return tail.hashCode();
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
        final ApiTail other = (ApiTail) obj;
        return stringCompare(tail, other.tail);
    }
}
