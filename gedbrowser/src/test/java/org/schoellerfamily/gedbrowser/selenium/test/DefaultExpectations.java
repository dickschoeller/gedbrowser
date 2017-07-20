package org.schoellerfamily.gedbrowser.selenium.test;

import java.util.HashMap;
import java.util.Map;

import org.schoellerfamily.gedbrowser.selenium.pageobjects.Expectations;
import org.schoellerfamily.gedbrowser.selenium.pageobjects.GrandParentIds;
import org.schoellerfamily.gedbrowser.selenium.pageobjects.GreatGrandParentIds;
import org.schoellerfamily.gedbrowser.selenium.pageobjects.GreatGreatGrandParentIds;
import org.schoellerfamily.gedbrowser.selenium.pageobjects.ParentIds;

/**
 * @author Dick Schoeller
 */
public final class DefaultExpectations {

    /** */
    private static final Map<String, Expectations> EXPECTATIONS_MAP =
            new HashMap<>();
    static {
        EXPECTATIONS_MAP.put("I6",
                new Expectations(
                        "Reginald Amass Williams (25 JAN 1918-2 FEB 1996)"
                        + " - I6 - gl120368",
                        "I9",
                        "Edwin Elijah A Williams (1883-1951) [I9]",
                        "I15",
                        "Ethel Ruth Moore (1893-1968) [I15]",
                        "I8",
                        new GreatGreatGrandParentIds(
                                new GreatGrandParentIds(
                                        new GrandParentIds(
                                                new ParentIds("I22", "I33"),
                                                new ParentIds("I127", "I27")
                                                ),
                                        new GrandParentIds(
                                                new ParentIds("I384", "I92"),
                                                new ParentIds("I64", "I36")
                                                )
                                        ),
                                new GreatGrandParentIds(
                                        new GrandParentIds(
                                                new ParentIds("I627", "I502"),
                                                new ParentIds("I3591", "I3592")
                                                ),
                                        new GrandParentIds(
                                                new ParentIds("I621", "I622"),
                                                new ParentIds("I617", "I616")
                                                )
                                        )
                                )
                        )
                );
        EXPECTATIONS_MAP.put("I9",
                new Expectations(
                        "Edwin Elijah A Williams (13 DEC 1883-ABT AUG 1951)"
                        + " - I9 - gl120368",
                        "I10",
                        "Thomas Harris Williams (1826-1903) [I10]",
                        "I11",
                        "Mary Amelia Shepperd Amass (1856-1929) [I11]",
                        "I6",
                        new GreatGreatGrandParentIds(
                                new GreatGrandParentIds(
                                        new GrandParentIds(
                                                new ParentIds("I372", "I373"),
                                                new ParentIds("I259", "I594")
                                                ),
                                        new GrandParentIds(
                                                new ParentIds("I154", "I641"),
                                                new ParentIds("I1479", "I1480")
                                                )
                                        ),
                                new GreatGrandParentIds(
                                        new GrandParentIds(
                                                new ParentIds("I418", "I1258"),
                                                new ParentIds("I23", "I93")
                                                ),
                                        new GrandParentIds(
                                                new ParentIds("I415", "I861"),
                                                new ParentIds("I857", "I856")
                                                )
                                        )
                                )
                        )
                );
        // "I10"
        EXPECTATIONS_MAP.put("I10",
                new Expectations(
                        "Thomas Harris Williams (1826-5 NOV 1903) - I10 -"
                        + " gl120368",
                        "I193",
                        "Thomas Williams (1802-1886) [I193]",
                        "I21",
                        "Sarah Harris (1798-1884) [I21]",
                        null,
                        null
                        ));
        // "I15"
        EXPECTATIONS_MAP.put("I15",
                new Expectations(
                        "Ethel Ruth Moore (7 DEC 1893-9 JUL 1968) - I15 -"
                        + " gl120368",
                        "I538",
                        "William Moore (1842-1915) [I538]",
                        "I539",
                        "Emily Francis Hunt (1860-) [I539]",
                        "I6",
                        null
                        ));
        // "I22"
        EXPECTATIONS_MAP.put("I22",
                new Expectations(
                        "John Williams (ABT 1778-ABT AUG 1840) - I22 -"
                        + " gl120368",
                        "I372",
                        "George Williams (1751-1810) [I372]",
                        "I373",
                        "Elizabeth Ayres (1755-) [I373]",
                        "I164",
                        null
                        ));
        // "I193"
        EXPECTATIONS_MAP.put("I193",
                new Expectations(
                        "Thomas Williams (27 MAR 1802-17 JUN 1886) - I193 -"
                        + " gl120368",
                        "I22",
                        "John Williams (1778-1840) [I22]",
                        "I33",
                        "Elizabeth Tomkins (1776-1851) [I33]",
                        "I10",
                        new GreatGreatGrandParentIds(
                                new GreatGrandParentIds(
                                        new GrandParentIds(
                                                new ParentIds("I376", "I377"),
                                                new ParentIds(null, null)
                                                ),
                                        new GrandParentIds(
                                                new ParentIds(null, null),
                                                new ParentIds(null, null)
                                                )
                                        ),
                                new GreatGrandParentIds(
                                        new GrandParentIds(
                                                new ParentIds(null, null),
                                                new ParentIds(null, null)
                                                ),
                                        new GrandParentIds(
                                                new ParentIds(null, null),
                                                new ParentIds(null, null)
                                                )
                                        )
                                )
                        )
                );
        // "I237"
        EXPECTATIONS_MAP.put("I237",
                new Expectations(
                        "Priscilla COOK (1821-ABT NOV 1886) - I237 - gl120368",
                        "I617",
                        "James Cook (1801-1877) [I617]",
                        "I616",
                        "Priscilla DARKINGS (1799-1856) [I616]",
                        "I3904",
                        new GreatGreatGrandParentIds(
                                new GreatGrandParentIds(
                                        new GrandParentIds(
                                                new ParentIds(null, null),
                                                new ParentIds(null, null)
                                                ),
                                        new GrandParentIds(
                                                new ParentIds(null, null),
                                                new ParentIds(null, null)
                                                )
                                        ),
                                new GreatGrandParentIds(
                                        new GrandParentIds(
                                                new ParentIds(null, null),
                                                new ParentIds(null, null)
                                                ),
                                        new GrandParentIds(
                                                new ParentIds(null, null),
                                                new ParentIds(null, null)
                                                )
                                        )
                                )
                        )
                );
        // "I539"
        EXPECTATIONS_MAP.put("I539",
                new Expectations(
                        "Emily Francis Hunt (ABT JUN 1860-) - I539 - gl120368",
                        "I385",
                        "Stephen HUNT (1813-1884) [I385]",
                        "I237",
                        "Priscilla COOK (1821-1886) [I237]",
                        "I480",
                        new GreatGreatGrandParentIds(
                                new GreatGrandParentIds(
                                        new GrandParentIds(
                                                new ParentIds(null, null),
                                                new ParentIds(null, null)
                                                ),
                                        new GrandParentIds(
                                                new ParentIds(null, null),
                                                new ParentIds(null, null)
                                                )
                                        ),
                                new GreatGrandParentIds(
                                        new GrandParentIds(
                                                new ParentIds(null, null),
                                                new ParentIds(null, null)
                                                ),
                                        new GrandParentIds(
                                                new ParentIds(null, null),
                                                new ParentIds(null, null)
                                                )
                                        )
                                )
                        )
                );
        // "I616"
        EXPECTATIONS_MAP.put("I616",
                new Expectations(
                        "Priscilla DARKINGS (1799-ABT 1856) - I616 - gl120368",
                        "",
                        "",
                        "",
                        "",
                        "I1409",
                        new GreatGreatGrandParentIds(
                                new GreatGrandParentIds(
                                        new GrandParentIds(
                                                new ParentIds(null, null),
                                                new ParentIds(null, null)
                                                ),
                                        new GrandParentIds(
                                                new ParentIds(null, null),
                                                new ParentIds(null, null)
                                                )
                                        ),
                                new GreatGrandParentIds(
                                        new GrandParentIds(
                                                new ParentIds(null, null),
                                                new ParentIds(null, null)
                                                ),
                                        new GrandParentIds(
                                                new ParentIds(null, null),
                                                new ParentIds(null, null)
                                                )
                                        )
                                )
                        )
                );
    }

    /**
     * @return default expectations
     */
    public Map<String, Expectations> create() {
        return EXPECTATIONS_MAP;
    }
}
