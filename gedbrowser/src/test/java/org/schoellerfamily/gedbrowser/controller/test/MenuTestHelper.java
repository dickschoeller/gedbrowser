package org.schoellerfamily.gedbrowser.controller.test;

/**
 * Defines the contract for menu test helper.
 *
 * @author Richard Schoeller
 */
public interface MenuTestHelper {
    /**
     * The text of the menu.
     */
    @SuppressWarnings("java:S6126")
    String MENU =
            "        <span class=\"left\">\n"
            + "            <span><a"
            + " href=\"http://www.schoellerfamily.org/\""
            + " id=\"home-menu\""
            + ">Home</a></span>\n"
            + "            <span><a"
            + " href=\"head?db=%s\""
            + " id=\"header-menu\""
            + ">Header</a></span>\n"
            + "            <span><a"
            + " href=\"surnames?db=%s&amp;letter=%s\""
            + " id=\"index-menu\""
            + ">Index</a></span>\n"
            + "            <span><a"
            + " href=\"sources?db=%s\""
            + " id=\"sources-menu\""
            + ">Sources</a></span>\n"
            + "            <span><a"
            + " href=\"submitters?db=%s\""
            + " id=\"submitters-menu\""
            + ">Submitters</a></span>\n"
            + "            <span></span>\n"
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
        return MENU.formatted(dataset, dataset, indexParameter, dataset, dataset);
    }
}
