package org.schoellerfamily.gedbrowser.api.datamodel;

import java.util.List;

/**
 * @author Dick Schoeller
 */
public class ApiPerson extends ApiObject {
    /** */
    private static final long serialVersionUID = 1L;

    /**
     * The name in a form that is usable for indexing.
     */
    private String indexName;

    /**
     * The surname.
     */
    private String surname;

    /**
     * Constructor.
     */
    public ApiPerson() {
        super();
    }

    /**
     * Constructor.
     *
     * @param type a string describing the data type of this object
     * @param string a string containing the primary value of this object
     * @param indexName the name in a form that is usable for indexing
     * @param surname the surname
     */
    public ApiPerson(final String type, final String string,
            final String indexName, final String surname) {
        super(type, string);
        this.indexName = indexName;
        this.surname = surname;
    }

    /**
     * Constructor.
     * @param type a string describing the data type of this object
     * @param string a string containing the primary value of this object
     * @param attributes the list of subordinate attributes of this object
     * @param indexName the name in a form that is usable for indexing
     * @param surname the surname
     */
    public ApiPerson(final String type, final String string,
            final List<ApiObject> attributes,
            final String indexName, final String surname) {
        super(type, string, attributes);
        this.indexName = indexName;
        this.surname = surname;
    }

    /**
     * @return the name in a form that is usable for indexing
     */
    public String getIndexName() {
        return indexName;
    }

    /**
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * @param visitor the visitor
     */
    public void accept(final ApiObjectVisitor visitor) {
        visitor.visit(this);
    }
}
