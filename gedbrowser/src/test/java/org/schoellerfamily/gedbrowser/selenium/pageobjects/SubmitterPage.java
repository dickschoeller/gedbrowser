package org.schoellerfamily.gedbrowser.selenium.pageobjects;

/**
 * Represents submitter page.
 *
 * @author Richard Schoeller
 */
public final class SubmitterPage extends PageBase implements MenuPageFacade {
    /** */
    private final String id;

    /** */
    private final MenuPage menuPage = new MenuPageImpl(this);

    /**
     * Creates a new SubmitterPage.
     *
     * @param factory the factory
     * @param previous the previous
     * @param baseUrl the base url to use
     * @param id the unique identifier for the target
     */
    public SubmitterPage(final PageFactory factory, final PageBase previous,
            final String baseUrl, final String id) {
        super(factory, previous, baseUrl, "submitter?db=gl120368&id=" + id);
        this.id = id;
    }

    /**
     * Gets the menu page.
     *
     * @return the menu page
     */
    @Override
    public MenuPage getMenuPage() {
        return menuPage;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }
}
