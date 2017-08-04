package org.schoellerfamily.gedbrowser.controller.test;

/**
 * @author Dick Schoeller
 *
 */
public interface MenuTestHelper {
    /**
     * The text of the menu.
     */
    String MENU =
            "        <span class=\"left\">\n"
            + "            <span><a"
            + " id=\"home-menu\""
            + " href=\"http://www.schoellerfamily.org/\""
            + ">Home</a></span>\n"
            + "            <span><a"
            + " id=\"header-menu\""
            + " href=\"head?db=%s\""
            + ">Header</a></span>\n"
            + "            <span><a"
            + " id=\"index-menu\""
            + " href=\"surnames?db=%s&amp;letter=%s\""
            + ">Index</a></span>\n"
            + "            <span><a"
            + " id=\"sources-menu\""
            + " href=\"sources?db=%s\""
            + ">Sources</a></span>\n"
            + "            <span><a"
            + " id=\"submitters-menu\""
            + " href=\"submitters?db=%s\""
            + ">Submitters</a></span>\n"
            + "            <span></span>\n"
            + "            <span></span>\n"
            + "        </span>";

    /**
     * @param indexParameter expected index URL parameter
     * @return the menu string contents
     */
    default String getMenu(final String indexParameter) {
        return getMenu("gl120368", indexParameter);
    }

    /**
     * @param dataset dataset name
     * @param indexParameter expected index URL parameter
     * @return the menu string contents
     */
    default String getMenu(final String dataset, final String indexParameter) {
        return String.format(MENU, dataset, dataset, indexParameter, dataset,
                dataset);
    }
}
