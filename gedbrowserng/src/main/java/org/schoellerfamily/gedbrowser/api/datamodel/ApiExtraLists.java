package org.schoellerfamily.gedbrowser.api.datamodel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dick Schoeller
 */
public class ApiExtraLists extends ApiHasImages {
    /** */
    private static final long serialVersionUID = 1L;

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
    public ApiExtraLists() {
        super();
    }

    /**
     * Constructor.
     *
     * @param in an object to copy
     * @param string the id string
     */
    public ApiExtraLists(final ApiExtraLists in, final String string) {
        super(in, string);
        this.refn.addAll(in.refn);
        this.famc.addAll(in.famc);
        this.fams.addAll(in.fams);
        this.change();
    }

    /**
     * @param builder the builder
     */
    public ApiExtraLists(final ApiExtraLists.Builder<?> builder) {
        super(builder);
    }

    /**
     * @return the list of fams attributes
     */
    public final List<ApiAttribute> getFams() {
        return fams;
    }

    /**
     * @return the list of famc attributes
     */
    public final List<ApiAttribute> getFamc() {
        return famc;
    }

    /**
     * @return the list of refn attributes
     */
    public final List<ApiAttribute> getRefn() {
        return refn;
    }

    /**
     * @return the list of changed attributes
     */
    public final List<ApiAttribute> getChanged() {
        return changed;
    }

    /**
     * Mark the person as changed today.
     */
    public final void change() {
        final ApiAttribute chanAttr = new ApiAttribute("attribute", "Changed");
        chanAttr.getAttributes().add(new DateUtil().todayDateAttribute());
        final ArrayList<ApiAttribute> chanList = new ArrayList<>();
        chanList.add(chanAttr);
        this.changed.clear();
        this.changed.addAll(chanList);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public final void addAttribute(final ApiAttribute attribute) {
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
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + attributesHash(changed);
        result = prime * result + attributesHash(famc);
        result = prime * result + attributesHash(fams);
        result = prime * result + attributesHash(refn);
        return result;
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
        final ApiExtraLists other = (ApiExtraLists) obj;
        if (!changed.equals(other.changed)) {
            return false;
        }
        if (!famc.equals(other.famc)) {
            return false;
        }
        if (!fams.equals(other.fams)) {
            return false;
        }
        return refn.equals(other.refn);
    }

    /**
     * @author Dick Schoeller
     *
     * @param <T> the actual builder type
     */
    public static class Builder<T extends ApiHasImages.Builder<T>>
            extends ApiHasImages.Builder<T> {
        /**
         * Build.
         *
         * @return this
         */
        @SuppressWarnings("unchecked")
        public T build() {
            super.build();
            return (T) this;
        }
    }
}
