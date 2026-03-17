package org.schoellerfamily.gedbrowser.selenium.pageobjects;

import org.openqa.selenium.WebElement;

/**
 * Represents login page.
 *
 * @author Richard Schoeller
 */
public class LoginPage extends PageBase {
    /**
     * Creates a new LoginPage.
     *
     * @param factory the factory
     * @param previous the previous
     * @param baseUrl the base url to use
     */
    public LoginPage(final PageFactory factory, final PageBase previous,
            final String baseUrl) {
        super(factory, previous, baseUrl, "login");
    }

    /**
     * Executes login.
     *
     * @param username the username to use
     * @param password the password
     * @return the resulting page base
     */
    public PageBase login(final String username, final String password) {
        getWebElement("input[name='username'").click();
        getWebElement("input[name='username'").clear();
        sendText("input[name='username']", username);
        getWebElement("input[name='password'").click();
        getWebElement("input[name='password'").clear();
        sendText("input[name='password']", password);
        final WebElement webElement = getWebElement("input[value='Login']");
        webElement.click();
        final PageBase previous = getPrevious();
        final String expectedURL =
                previous.getBaseUrl() + previous.getLocation();
        waitForPageLoaded(expectedURL);
        return previous;
    }
}
