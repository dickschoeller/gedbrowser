package org.schoellerfamily.gedbrowser.selenium.pageobjects;

/**
 * @author Dick Schoeller
 */
public class SourcesPage extends PageBase {
    /**
     * PageObject pattern for the sources page.
     *
     * @param factory the factory for creating new page objects
     * @param previous where we came from. Can be null
     * @param baseUrl the base URL from which all others derive
     */
    public SourcesPage(final PageFactory factory,
            final PageBase previous, final String baseUrl) {
        super(factory, previous, baseUrl, "sources?db=schoeller");
    }
}
