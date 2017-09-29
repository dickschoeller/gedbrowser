package org.schoellerfamily.gedbrowser.api.datamodel;

import java.util.List;

/**
 * @author Dick Schoeller
 */
public final class ApiAttribute extends ApiObject {
    /** */
    private static final long serialVersionUID = 1L;

    /**
     * An additional string containing the secondary value of this object.
     */
    private String tail;

    /**
     * Constructor.
     */
    public ApiAttribute() {
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
    public ApiAttribute(final String string, final String type,
            final String tail) {
        super(type, string);
        this.tail = tail;
    }

    /**
     * Constructor.
     *
     * @param type a string describing the data type of this object
     * @param string a string containing the primary value of this object
     * @param attributes the list of subordinate attributes of this object
     * @param tail an additional string
     */
    public ApiAttribute(final String string, final String type,
            final List<ApiObject> attributes, final String tail) {
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
     * @param visitor the visitor
     */
    public void accept(final ApiObjectVisitor visitor) {
        visitor.visit(this);
    }
}
