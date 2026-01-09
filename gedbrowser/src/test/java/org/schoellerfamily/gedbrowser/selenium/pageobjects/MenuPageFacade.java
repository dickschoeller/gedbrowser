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

    @Override
    default IndexPage clickIndex() {
        return getMenuPage().clickIndex();
    }

    @Override
    default SourcesPage clickSources() {
        return getMenuPage().clickSources();
    }

    @Override
    default SubmittersPage clickSubmitters() {
        return getMenuPage().clickSubmitters();
    }

    @Override
    default LoginPage clickLogin() {
        return getMenuPage().clickLogin();
    }

    @Override
    default PageBase clickLogout() {
        return getMenuPage().clickLogout();
    }

    @Override
    default boolean isMenuPresent(final String name) {
        return getMenuPage().isMenuPresent(name);
    }
}
