package org.schoellerfamily.geoservice.persistence.fixture;

import org.schoellerfamily.geoservice.persistence.GeoCode;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Dick Schoeller
 */
public class GeoCodeTestFixture {
    /** */
    @Autowired
    private GeoCode gcc;

    /**
     * Table of old and modern addresses for some bigger tests.
     */
    private static final String[][] ADDRESS_TABLE = {
        {
            "1 Glendale Street, Randolph, Norfolk County, Massachusetts, USA",
            ""
        },
        {
            "1st United Methodist Church, Perkasie, Bucks County, Pennsylvania"
            + ", USA",
            ""
        },
        {
            "4 miles northeast of Pine Grove, Pine Grove Township, Schuylkill "
            + "County, Pennsylvania, USA",
            ""
        },
        {
            "Adelo, Illinois, USA",
            ""
        },
        {
            "Aistaig, Württemberg",
            ""
        },
        {
            "Albright College, Reading, Berks County, Pennsylvania, USA",
            ""
        },
        {
            "Albrightsville, Carbon County, Pennsylvania, USA",
            ""
        },
        {
            "Alsace, France",
            ""
        },
        {
            "Altalaha Lutheran Cemetery, Rehrersburg, Berks County, Pennsylvan"
            + "ia, USA",
            ""
        },
        {
            "America",
            ""
        },
        {
            "Anamosa Hospital, Anamosa, Jones County, Iowa, USA",
            ""
        },
        {
            "Arizona, USA",
            ""
        },
        {
            "Ash of Normandy, Sussex, England",
        },
        {
            "At Sea",
            ""
        },
        {
            "At home, 1.5 miles northwest of Sutliff, Solon, Cedar County, Iow"
            + "a, USA",
            ""
        },
        {
            "At home, Cornwall Road, Lebanon, Lebanon County, Pennsylvania, U"
            + "SA",
            ""
        },
        {
            "At home, RD 2, Lebanon, Lebanon County, Pennsylvania, USA",
            ""
        },
        {
            "At home, Souderton, Montgomery County, Pennsylvania, USA",
            ""
        },
        {
            "At home, Tega Cay, York County, South Carolina, USA",
            ""
        },
    };

    /**
     * High bound for not founds.
     * Googles responses are sufficiently inconsistent that we can't
     * rely on an exact count.
     */
    private static final int HIGH_BOUND = 20;

    /**
     * Low bound for not founds.
     * Googles responses are sufficiently inconsistent that we can't
     * rely on an exact count.
     */
    private static final int LOW_BOUND = 10;

    /**
     * Table of values we know should be not found.
     */
    private static final String[] EXPECTED_NOT_FOUND = {
        "1st United Methodist Church, Perkasie, Bucks County, Pennsylvania, US"
        + "A",
        "Adelo, Illinois, USA",
        "Albright College, Reading, Berks County, Pennsylvania, USA",
        "Altalaha Lutheran Cemetery, Rehrersburg, Berks County, Pennsylvania, "
        + "USA",
        "Anamosa Hospital, Anamosa, Jones County, Iowa, USA",
        "Ash of Normandy, Sussex, England",
        "At Sea",
        "At home, 1.5 miles northwest of Sutliff, Solon, Cedar County, Iowa, U"
        + "SA",
        "At home, Cornwall Road, Lebanon, Lebanon County, Pennsylvania, USA",
        "At home, RD 2, Lebanon, Lebanon County, Pennsylvania, USA",
        "At home, Souderton, Montgomery County, Pennsylvania, USA",
        "At home, Tega Cay, York County, South Carolina, USA",
    };

    /**
     * @return table of addresses
     */
    @SuppressWarnings("PMD.MethodReturnsInternalArray")
    public final String[][] addressTable() {
        return ADDRESS_TABLE;
    }

    /**
     * @return table of addresses
     */
    @SuppressWarnings("PMD.MethodReturnsInternalArray")
    public final String[] expectedNotFound() {
        return EXPECTED_NOT_FOUND;
    }

    /**
     * Return lower boundary of not founds. This seems to vary from run to run
     * with no rhyme or reason.
     *
     * @return low boundary
     */
    public final int expectedLowBound() {
        return LOW_BOUND;
    }

    /**
     * Return higher boundary of not founds. This seems to vary from run to run
     * with no rhyme or reason.
     *
     * @return high boundary
     */
    public final int expectedHighBound() {
        return HIGH_BOUND;
    }

    /**
     * Load the standard test addresses into the provided geocode.
     */
    public final void loadTestAddresses() {
        gcc.clear();
        load(addressTable());
    }

    /**
     * Load the cache from an array of strings. Particularly valuable for
     * testing. Each string has planeName|modernPlaceName. The second part can
     * be empty.
     *
     * @param strings the array of strings
     */
    @SuppressWarnings("PMD.UseVarargs")
    private void load(final String[][] strings) {
        for (final String[] line : strings) {
            if (line.length < 2 || line[1] == null || line[1].isEmpty()) {
                gcc.find(line[0]);
            } else {
                gcc.find(line[0], line[1]);
            }
        }
    }
}
