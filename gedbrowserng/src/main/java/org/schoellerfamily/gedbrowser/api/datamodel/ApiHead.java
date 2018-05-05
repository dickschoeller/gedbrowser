package org.schoellerfamily.gedbrowser.api.datamodel;

import java.util.List;

/**
 * @author Dick Schoeller
 */
public final class ApiHead extends ApiObject {
    /** */
    private static final long serialVersionUID = 2L;

    /**
     * Constructor.
     */
    public ApiHead() {
        super();
    }

    /**
     * Constructor.
     *
     * @param type a string describing the data type of this object
     * @param string a string containing the primary value of this object
     */
    public ApiHead(final String type, final String string) {
        super(type, string);
    }

    /**
     * Constructor.
     *
     * @param type a string describing the data type of this object
     * @param string a string containing the primary value of this object
     * @param attributes the list of subordinate attributes of this object
     */
    public ApiHead(final String type, final String string,
            final List<ApiAttribute> attributes) {
        super(type, string, attributes);
    }

    /**
     * @param visitor the visitor
     */
    public void accept(final ApiObjectVisitor visitor) {
        visitor.visit(this);
    }
}
