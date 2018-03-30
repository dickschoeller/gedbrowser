package org.schoellerfamily.gedbrowser.api.datamodel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dick Schoeller
 */
public final class ApiPerson extends ApiHasImages {
    /** */
    private static final long serialVersionUID = 3L;

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
     * The list of fams attributes of this object.
     */
    private final List<ApiAttribute> fams = new ArrayList<>();

    /**
     * The list of famc attributes of this object.
     */
    private final List<ApiAttribute> famc = new ArrayList<>();

    /**
     * The list of refn attributes of this object.
     */
    private final List<ApiAttribute> refn = new ArrayList<>();

    /**
     * The list of changed attributes of this object.
     */
    private final List<ApiAttribute> changed = new ArrayList<>();

    /**
     * Constructor.
     */
    public ApiPerson() {
        super();
        indexName = "";
        surname = "";
        lifespan = new ApiLifespan();
    }

    /**
     * Constructor.
     *
     * @param in a person to copy (except for the ID)
     * @param string the ID of this object
     */
    public ApiPerson(final ApiPerson in, final String string) {
        super(in.getType(), string, in.getAttributes());
        this.indexName = in.indexName;
        this.surname = in.surname;
        this.lifespan = in.lifespan;
        this.refn.addAll(refn);
        this.famc.addAll(in.famc);
        this.fams.addAll(in.fams);
        this.getImages().addAll(in.getImages());
        this.change();
    }

    /**
     * Constructor.
     *
     * @param builder a builder
     */
    public ApiPerson(final Builder builder) {
        super(builder);
        this.indexName = builder.getIndexName();
        this.surname = builder.getSurname();
        this.lifespan = builder.getLifespan();
        addAttribute(refn(getString()));
    }

    /**
     * @param string the person string
     * @return the refn number extracted from that
     */
    private ApiAttribute refn(final String string) {
        return new ApiAttribute("attribute", "Reference Number",
                string.replaceAll("[A-Za-z]", ""));
    }

    /**
     * Mark the person as changed today.
     */
    public void change() {
        final ApiAttribute chanAttr = new ApiAttribute("attribute", "Changed");
        chanAttr.getAttributes().add(new DateUtil().todayDateAttribute());
        final ArrayList<ApiAttribute> chanList = new ArrayList<>();
        chanList.add(chanAttr);
        this.changed.clear();
        this.changed.addAll(chanList);
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
     * @return the list of fams attributes
     */
    public List<ApiAttribute> getFams() {
        return fams;
    }

    /**
     * @return the list of famc attributes
     */
    public List<ApiAttribute> getFamc() {
        return famc;
    }

    /**
     * @return the list of refn attributes
     */
    public List<ApiAttribute> getRefn() {
        return refn;
    }

    /**
     * @return the list of changed attributes
     */
    public List<ApiAttribute> getChanged() {
        return changed;
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
        if (!stringCompare(indexName, other.indexName)) {
            return false;
        }
        return stringCompare(surname, other.surname);
    }

    /**
     * Special handling of adding attributes to an ApiPerson because the list
     * gets broken up into different sections.
     *
     * @param attribute the attribute to add
     */
    public void addAttribute(final ApiAttribute attribute) {
        if (attribute.isType("fams")) {
            fams.add(attribute);
            return;
        }
        if (attribute.isType("famc")) {
            famc.add(attribute);
            return;
        }
        if (attribute.isType("Changed")) {
            changed.clear();
            changed.add(attribute);
            return;
        }
        if (attribute.isType("Reference Number")) {
            refn.clear();
            refn.add(attribute);
            return;
        }
        if (new ImageUtils().isImageWrapper(attribute)) {
            getImages().add(attribute);
            return;
        }
        getAttributes().add(attribute);
    }

    /**
     * @author Dick Schoeller
     */
    public static final class Builder extends ApiHasImages.Builder {
        /** */
        private String s;
        /** */
        private String i;
        /** */
        private ApiLifespan l;

        /**
         * @param surname the surname
         * @return this
         */
        public Builder surname(final String surname) {
            this.s = surname;
            return this;
        }

        /**
         * @param indexName the index name
         * @return this
         */
        public Builder indexName(final String indexName) {
            this.i = indexName;
            return this;
        }

        /**
         * @param lifespan the lifespan
         * @return this
         */
        public Builder lifespan(final ApiLifespan lifespan) {
            this.l = lifespan;
            return this;
        }

        /**
         * @return the surname
         */
        /* default */ String getSurname() {
            return s;
        }

        /**
         * @return the index name
         */
        /* default */ String getIndexName() {
            return i;
        }

        /**
         * @return the lifespan
         */
        /* default */ ApiLifespan getLifespan() {
            return l;
        }

        /**
         * @return this
         */
        public Builder build() {
            type("person");
            super.build();
            if (s == null) {
                s = "?";
            }
            if (i == null) {
                i = "?, ?";
            }
            if (l == null) {
                l = new ApiLifespan();
            }
            return this;
        }
    }
}
