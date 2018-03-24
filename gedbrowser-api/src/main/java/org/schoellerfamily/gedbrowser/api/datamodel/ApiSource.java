package org.schoellerfamily.gedbrowser.api.datamodel;

import java.util.List;

/**
 * @author Dick Schoeller
 */
public final class ApiSource extends ApiObject {
    /** */
    private static final long serialVersionUID = 2L;

    /**
     * The title string.
     */
    private final String title;

    /**
     * Constructor.
     */
    public ApiSource() {
        super();
        this.title = "Unknown";
    }

    /**
     * Constructor.
     *
     * @param type a string describing the data type of this object
     * @param string a string containing the primary value of this object
     * @param title the title of the source
     */
    public ApiSource(final String type, final String string,
            final String title) {
        super(type, string);
        this.title = title;
    }

    /**
     * Constructor.
     *
     * @param type a string describing the data type of this object
     * @param string a string containing the primary value of this object
     * @param attributes the list of subordinate attributes of this object
     * @param title the title of the source
     */
    public ApiSource(final String type, final String string,
            final List<ApiAttribute> attributes, final String title) {
        super(type, string, attributes);
        this.title = title;
    }

    /**
     * @return the title string
     */
    public String getTitle() {
        return title;
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
        result = prime * result + titleHash();
        return result;
    }

    /**
     * @return the title's hash code
     */
    private int titleHash() {
        if (title == null) {
            return 0;
        }
        return title.hashCode();
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
        final ApiSource other = (ApiSource) obj;
        return stringCompare(title, other.title);
    }
}
