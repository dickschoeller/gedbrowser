package org.schoellerfamily.gedbrowser.api.datamodel;

import java.util.List;

/**
 * @author Dick Schoeller
 */
public final class ApiNote extends ApiTail {
    /** */
    private static final long serialVersionUID = 2L;

    /**
     * Constructor.
     */
    public ApiNote() {
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
    public ApiNote(final String type, final String string, final String tail) {
        super(type, string, tail);
    }

    /**
     * Constructor.
     * @param type a string describing the data type of this object
     * @param string a string containing the primary value of this object
     * @param attributes the list of subordinate attributes of this object
     * @param tail an additional string containing the secondary value of this
     *      object
     */
    public ApiNote(final String type, final String string,
            final List<ApiAttribute> attributes, final String tail) {
        super(type, string, attributes, tail);
    }

    /**
     * @param visitor the visitor
     */
    public void accept(final ApiObjectVisitor visitor) {
        visitor.visit(this);
    }
}
