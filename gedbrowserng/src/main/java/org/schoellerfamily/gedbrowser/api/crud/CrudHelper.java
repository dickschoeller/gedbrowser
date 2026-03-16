package org.schoellerfamily.gedbrowser.api.crud;

import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiFamily.ApiFamilyBuilder;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson.ApiPersonBuilder;

/**
 * Represents crud helper.
 *
 * @author Richard Schoeller
 */
public class CrudHelper {

    /**
     * Executes spouse attribute.
     *
     * @param person the person
     * @return the resulting api attribute
     */
    public ApiAttribute spouseAttribute(final ApiPersonBuilder<?, ?> person) {
        if (sex(person, "M")) {
            return ApiAttribute.builder().type("husband").string(person.getString()).build();
        }
        if (sex(person, "F")) {
            return ApiAttribute.builder().type("wife").string(person.getString()).build();
        }
        // TODO This should really be "spouse" but not supported everywhere
        return ApiAttribute.builder().type("husband").string(person.getString()).build();
    }

    private boolean sex(final ApiPersonBuilder<?, ?> person, final String sex) {
        for (final ApiAttribute attr : person.getAttributes()) {
            if ("attribute".equals(attr.getType())
                    && "Sex".equals(attr.getString())
                    && sex.equals(attr.getTail())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the api attribute.
     *
     * @param person the person
     * @return the resulting api attribute
     */
    public ApiAttribute childAttribute(final ApiPersonBuilder<?, ?> person) {
        return ApiAttribute.builder().type("child").string(person.getString()).build();
    }

    /**
     * Returns the api attribute.
     *
     * @param family the family
     * @return the resulting api attribute
     */
    public ApiAttribute famcAttribute(final ApiFamilyBuilder<?, ?> family) {
        return ApiAttribute.builder().type("famc").string(family.getString()).build();
    }

    /**
     * Returns the api attribute.
     *
     * @param family the family
     * @return the resulting api attribute
     */
    public ApiAttribute famsAttribute(final ApiFamilyBuilder<?, ?> family) {
        return ApiAttribute.builder().type("fams").string(family.getString()).build();
    }
}
