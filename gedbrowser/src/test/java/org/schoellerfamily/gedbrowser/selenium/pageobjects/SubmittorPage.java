package org.schoellerfamily.gedbrowser.selenium.pageobjects;

/**
 * @author Dick Schoeller
 */
public class SubmittorPage extends PageBase implements MenuPageFacade {
    /** */
    private final String id;

    /** */
    private final MenuPage menuPage = new MenuPageImpl(this);

    /**
     * @param factory the factory for creating more page objects
     * @param previous the previous page object
     * @param baseUrl the base URL for this test
     * @param id the ID of the current submittor
     */
    public SubmittorPage(final PageFactory factory, final PageBase previous,
            final String baseUrl, final String id) {
        super(factory, previous, baseUrl, "submittor?db=gl120368&id=" + id);
        this.id = id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MenuPage getMenuPage() {
        return menuPage;
    }

    /**
     * @return the submittor ID for this page.
     */
    public String getId() {
        return id;
    }
}
