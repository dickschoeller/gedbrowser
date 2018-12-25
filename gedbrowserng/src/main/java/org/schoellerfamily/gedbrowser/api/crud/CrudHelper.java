package org.schoellerfamily.gedbrowser.api.crud;

import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiFamily;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;

/**
 * @author Dick Schoeller
 *
 */
public class CrudHelper {

    /**
     * @param person the person to check
     * @return the spouse attribute
     */
    public ApiAttribute spouseAttribute(final ApiPerson person) {
        if (sex(person, "M")) {
            return new ApiAttribute("husband", person.getString());
        }
        if (sex(person, "F")) {
            return new ApiAttribute("wife", person.getString());
        }
        // TODO This should really be "spouse" but not supported everywhere
        return new ApiAttribute("husband", person.getString());
    }

    /**
     * @param person a person to check
     * @param sex the sex we are checking for
     * @return true if the sex female attribute is found
     */
    private boolean sex(final ApiPerson person, final String sex) {
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
    public ApiAttribute childAttribute(final ApiPerson person) {
        return new ApiAttribute("child", person.getString());
    }

    /**
     * @param family the family to link to
     * @return the famc link attribute
     */
    public ApiAttribute famcAttribute(final ApiFamily family) {
        return new ApiAttribute("famc", family.getString());
    }

    /**
     * @param family the family to link to
     * @return the fams link attribute
     */
    public ApiAttribute famsAttribute(final ApiFamily family) {
        return new ApiAttribute("fams", family.getString());
    }
}
