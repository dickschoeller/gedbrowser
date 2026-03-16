package org.schoellerfamily.geoservice.persistence.fixture;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import org.schoellerfamily.geoservice.persistence.GeoCode;

/**
 * @author Dick Schoeller
 */
public class GeoCodeTestFixture {


    /**
     * High bound for not founds.final
     * Googles responses are sufficiently inconsistent that we can't
     * rely on an exact count.
     */
    private static final int HIGH_BOUND = 20;

    /**
     * Low bound for not founds.null
     * Googles responses are sufficiently inconsistent that we can't
     * rely on an exact count.
     */
    private static final int LOW_BOUND = 10;

    /**
     * Some test data.
     */
    private static final String[][] ADDRESS_TABLE = {
        {
            "1 Glendale Street, Randolph, Norfolk County, Massachusetts, USA",
            ""
        },
        {
            "1st United Methodist Church, Perkasie, Bucks County, Pennsylv"
            + "ania, USA",
            ""
        },
        {
            "4 miles northeast of Pine Grove, Pine Grove Township, Schuylk"
            + "ill County, Pennsylvania, USA",
            ""
        },
        {"Adelo, Illinois, USA", ""},
        {"Aistaig, Württemberg", ""},
        {"Albright College, Reading, Berks County, Pennsylvania, USA", ""},
        {"Albrightsville, Carbon County, Pennsylvania, USA", ""},
        {"Alsace, France", ""},
        {
            "Altalaha Lutheran Cemetery, Rehrersburg, Berks County, Pennsy"
            + "lvania, USA",
            ""
        },
        {"America", "" },
        {"Anamosa Hospital, Anamosa, Jones County, Iowa, USA", ""},
        {"Arizona, USA", ""},
        {"Ash of Normandy, Sussex, England", ""},
        {"At Sea", "Atlantic Ocean" },
        {
            "At home, 1.5 miles northwest of Sutliff, Solon, Cedar County,"
            + " Iowa, USA",
            ""
        },
        {
            "At home, Cornwall Road, Lebanon, Lebanon County, Pennsylvania"
            + ", USA",
            ""
        },
        {"At home, RD 2, Lebanon, Lebanon County, Pennsylvania, USA", ""},
        {"At home, Souderton, Montgomery County, Pennsylvania, USA", ""},
        {"At home, Tega Cay, York County, South Carolina, USA", ""},
    };


    /**
     * Array of values we expect to not find.
     */
    private static final String[] EXPECT_TO_NOT_FIND = {
        "1st United Methodist Church, Perkasie, Bucks County, Pennsylvania"
        + ", USA",
        "Adelo, Illinois, USA",
        "Albright College, Reading, Berks County, Pennsylvania, USA",
        "Altalaha Lutheran Cemetery, Rehrersburg, Berks County, Pennsylvan"
        + "ia, USA",
        "Anamosa Hospital, Anamosa, Jones County, Iowa, USA",
        "Ash of Normandy, Sussex, England",
        "At home, 1.5 miles northwest of Sutliff, Solon, Cedar County, Iow"
        + "a, USA",
        "At home, Cornwall Road, Lebanon, Lebanon County, Pennsylvania, USA",
        "At home, RD 2, Lebanon, Lebanon County, Pennsylvania, USA",
        "At home, Souderton, Montgomery County, Pennsylvania, USA",
        "At home, Tega Cay, York County, South Carolina, USA",
    };

    /**
     * Adds ress table.
     *
     * @return the resulting string[][]
     */
    public final String[][] addressTable() {
        return cloneArray(ADDRESS_TABLE);
    }

    @SuppressWarnings({ "PMD.UseVarargs" })
    private String[][] cloneArray(final String[][] src) {
        final int length = src.length;
        final String[][] target = new String[length][src[0].length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(src[i], 0, target[i], 0, src[i].length);
        }
        return target;
    }

    /**
     * Returns the string array.
     *
     * @return the resulting string array
     */
    public final String[] expectedNotFound() {
        return Arrays.copyOf(EXPECT_TO_NOT_FIND, EXPECT_TO_NOT_FIND.length);
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
     * @param gcc the geocode to load
     */
    public final void loadTestAddresses(final GeoCode gcc) {
        gcc.clear();
        load(addressTable(), gcc);
    }

    private void load(final String[][] strings, final GeoCode gcc) {
        for (final String[] line : strings) {
            if (line.length < 2 || StringUtils.isEmpty(line[1])) {
                gcc.find(line[0]);
            } else {
                gcc.find(line[0], line[1]);
            }
        }
    }
}
