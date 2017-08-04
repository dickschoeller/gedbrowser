package org.schoellerfamily.gedbrowser.selenium.pageobjects;

/**
 * This interface defines the methods that manipulate pages that have menu
 * items displayed. Designed for implementation and inclusion using the
 * mixin facade pattern.
 *
 * @author Dick Schoeller
 */
public interface MenuPage {
    /**
     * @return the page for the newly reached page
     */
    IndexPage clickIndex();

    /**
     * @return the page for the newly reached page
     */
    SourcesPage clickSources();

    /**
     * @return the page for the newly reached page
     */
    SubmittersPage clickSubmitters();

    /**
     * @return the page for the newly reached page
     */
    LoginPage clickLogin();

    /**
     * @return the same page, but now logged out
     */
    PageBase clickLogout();

    /**
     * @param name the name of the menu item
     * @return true if the menu item is present
     */
    boolean isMenuPresent(String name);
}
