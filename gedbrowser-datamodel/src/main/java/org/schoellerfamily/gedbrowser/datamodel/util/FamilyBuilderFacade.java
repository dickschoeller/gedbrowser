package org.schoellerfamily.gedbrowser.datamodel.util;

import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Child;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.Husband;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Wife;

/**
 * @author Dick Schoeller
 */
public interface FamilyBuilderFacade extends FamilyBuilder {
    /**
     * @return the family builder
     */
    FamilyBuilder getFamilyBuilder();

    /**
     * {@inheritDoc}
     */
    @Override
    default Family createFamily() {
        return getFamilyBuilder().createFamily();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default Family createFamily(final String idString) {
        return getFamilyBuilder().createFamily(idString);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default Attribute createFamilyEvent(final Family family, final String type,
            final String dateString) {
        return getFamilyBuilder().createFamilyEvent(family, type, dateString);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default Attribute createFamilyEvent(final Family family, final String type) {
        return getFamilyBuilder().createFamilyEvent(family, type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default Husband addHusbandToFamily(final Family family, final Person person) {
        return getFamilyBuilder().addHusbandToFamily(family, person);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default Wife addWifeToFamily(final Family family, final Person person) {
        return getFamilyBuilder().addWifeToFamily(family, person);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default Child addChildToFamily(final Family family, final Person person) {
        return getFamilyBuilder().addChildToFamily(family, person);
    }
}
