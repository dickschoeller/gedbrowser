package org.schoellerfamily.gedbrowser.api.crud;

import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiFamily.ApiFamilyBuilder;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson.ApiPersonBuilder;

/**
 * @author Dick Schoeller
 *
 */
public class CrudHelper {

    /**
     * @param person the person to check
     * @return the spouse attribute
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

    /**
     * @param person a person to check
     * @param sex the sex we are checking for
     * @return true if the sex female attribute is found
     */
    private boolean sex(final ApiPersonBuilder<?, ?> person, final String sex) {
        for (final ApiAttribute attr : person.getAttributes()) {
            if (attr.getType().equals("attribute")
                    && attr.getString().equals("Sex")
                    && attr.getTail().equals(sex)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param person to link to
     * @return the child link attribute
     */
    public ApiAttribute childAttribute(final ApiPersonBuilder<?, ?> person) {
        return ApiAttribute.builder().type("child").string(person.getString()).build();
    }

    /**
     * @param family the family to link to
     * @return the famc link attribute
     */
    public ApiAttribute famcAttribute(final ApiFamilyBuilder<?, ?> family) {
        return ApiAttribute.builder().type("famc").string(family.getString()).build();
    }

    /**
     * @param family the family to link to
     * @return the fams link attribute
     */
    public ApiAttribute famsAttribute(final ApiFamilyBuilder<?, ?> family) {
        return ApiAttribute.builder().type("fams").string(family.getString()).build();
    }
}
