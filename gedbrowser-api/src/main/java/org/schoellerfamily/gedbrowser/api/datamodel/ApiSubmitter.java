package org.schoellerfamily.gedbrowser.api.datamodel;

import java.util.List;

/**
 * @author Dick Schoeller
 */
public final class ApiSubmitter extends ApiObject {
    /** */
    private static final long serialVersionUID = 2L;

    /**
     * The name of the submitter.
     */
    private final String name;

    /**
     * Constructor.
     */
    public ApiSubmitter() {
        super();
        this.name = "? ?";
    }

    /**
     * Constructor.
     *
     * @param type a string describing the data type of this object
     * @param string a string containing the primary value of this object
     * @param name the name of the submitter
     */
    public ApiSubmitter(final String type, final String string,
            final String name) {
        super(type, string);
        this.name = name;
    }

    /**
     * Constructor.
     *
     * @param type a string describing the data type of this object
     * @param string a string containing the primary value of this object
     * @param attributes the list of subordinate attributes of this object
     * @param name the name of the submitter
     */
    public ApiSubmitter(final String type, final String string,
            final List<ApiAttribute> attributes, final String name) {
        super(type, string, attributes);
        this.name = name;
    }

    /**
     * @return the submitter name
     */
    public String getName() {
        return name;
    }

    /**
     * @param visitor the visitor
     */
    public void accept(final ApiObjectVisitor visitor) {
        visitor.visit(this);
    }
}
