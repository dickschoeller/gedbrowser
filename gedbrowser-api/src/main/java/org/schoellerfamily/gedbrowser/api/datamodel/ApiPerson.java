package org.schoellerfamily.gedbrowser.api.datamodel;

import java.util.List;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings({ "PMD.CyclomaticComplexity",
        "PMD.ModifiedCyclomaticComplexity", "PMD.StdCyclomaticComplexity" })
public final class ApiPerson extends ApiObject {
    /** */
    private static final long serialVersionUID = 2L;

    /**
     * The name in a form that is usable for indexing.
     */
    private final String indexName;

    /**
     * The surname.
     */
    private final String surname;

    /**
     * The lifespan of this person.
     */
    private final ApiLifespan lifespan;

    /**
     * Constructor.
     */
    public ApiPerson() {
        super();
        indexName = "";
        surname = "";
        lifespan = new ApiLifespan("", "");
    }

    /**
     * Constructor.
     * @param type a string describing the data type of this object
     * @param string a string containing the primary value of this object
     * @param indexName the name in a form that is usable for indexing
     * @param surname the surname
     * @param lifespan the lifespan of this person
     */
    public ApiPerson(final String type, final String string,
            final String indexName, final String surname,
            final ApiLifespan lifespan) {
        super(type, string);
        this.indexName = indexName;
        this.surname = surname;
        this.lifespan = lifespan;
    }

    /**
     * @param type a string describing the data type of this object
     * @param string a string containing the primary value of this object
     * @param attributes the list of subordinate attributes of this object
     * @param indexName the name in a form that is usable for indexing
     * @param surname the surname
     * @param lifespan the lifespan of this person
     */
    public ApiPerson(final String type, final String string,
            final List<ApiAttribute> attributes, final String indexName,
            final String surname, final ApiLifespan lifespan) {
        super(type, string, attributes);
        this.indexName = indexName;
        this.surname = surname;
        this.lifespan = lifespan;
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
     * @return the lifespan of this person
     */
    public ApiLifespan getLifespan() {
        return lifespan;
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
        result = prime * result + indexNameHash();
        result = prime * result + surnameHash();
        return result;
    }

    /**
     * @return the hash code for the index name string
     */
    private int indexNameHash() {
        if (indexName == null) {
            return 0;
        }
        return indexName.hashCode();
    }

    /**
     * @return the hash code for the surname string
     */
    private int surnameHash() {
        if (surname == null) {
            return 0;
        }
        return surname.hashCode();
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
        final ApiPerson other = (ApiPerson) obj;
        if (indexName == null) {
            if (other.indexName != null) {
                return false;
            }
        } else if (!indexName.equals(other.indexName)) {
            return false;
        }
        if (surname == null) {
            if (other.surname != null) {
                return false;
            }
        } else if (!surname.equals(other.surname)) {
            return false;
        }
        return true;
    }
}
