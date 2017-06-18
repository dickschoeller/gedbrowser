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
            + "            <span><a href=\"http://www.schoellerfamily.org/"
            + "\">Home</a></span>\n"
            + "            <span><a href=\"head?db=%s\">Header</a></"
            + "span>\n"
            + "            <span><a href=\"surnames?db=%s&amp;letter"
            + "=%s\">Index</a></span>\n"
            + "            <span><a href=\"sources?db=%s\">Sources</"
            + "a></span>\n"
            + "            <span><a href=\"submittors?db=%s\">Submit"
            + "tors</a></span>\n"
            + "            <span></span>\n" + "        </span>";

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