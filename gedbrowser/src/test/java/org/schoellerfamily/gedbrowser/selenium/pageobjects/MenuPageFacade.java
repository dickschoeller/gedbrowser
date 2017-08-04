package org.schoellerfamily.gedbrowser.selenium.pageobjects;

/**
 * Facade implementation for MenuPage. This follows the Java 8 mixin facade
 * pattern.
 *
 * @author Dick Schoeller
 */
public interface MenuPageFacade extends MenuPage {
    /**
     * @return the menu page we are presenting a facade for
     */
    MenuPage getMenuPage();

    /**
     * {@inheritDoc}
     */
    @Override
    default IndexPage clickIndex() {
        return getMenuPage().clickIndex();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default SourcesPage clickSources() {
        return getMenuPage().clickSources();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default SubmittersPage clickSubmitters() {
        return getMenuPage().clickSubmitters();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default LoginPage clickLogin() {
        return getMenuPage().clickLogin();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default PageBase clickLogout() {
        return getMenuPage().clickLogout();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default boolean isMenuPresent(final String name) {
        return getMenuPage().isMenuPresent(name);
    }
}
