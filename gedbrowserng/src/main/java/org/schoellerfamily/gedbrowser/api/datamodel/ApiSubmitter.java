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

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + nameHashCode();
        return result;
    }

    /**
     * @return hash code of name string or 0 if null
     */
    private int nameHashCode() {
        if (name == null) {
            return 0;
        }
        return name.hashCode();
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
        final ApiSubmitter other = (ApiSubmitter) obj;
        return this.stringCompare(name, other.name);
    }
}
