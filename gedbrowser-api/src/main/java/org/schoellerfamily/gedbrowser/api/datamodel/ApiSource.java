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
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        ApiSource other = (ApiSource) obj;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        return true;
    }
}
