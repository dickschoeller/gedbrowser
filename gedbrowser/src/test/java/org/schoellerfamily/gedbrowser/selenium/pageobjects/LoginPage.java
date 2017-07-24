package org.schoellerfamily.gedbrowser.selenium.pageobjects;

import org.openqa.selenium.WebElement;

/**
 * @author Dick Schoeller
 */
public class LoginPage extends PageBase {
    /**
     * @param factory the factory for creating pages as we navigate
     * @param previous the previous page
     * @param baseUrl the base URL of the site
     */
    public LoginPage(final PageFactory factory, final PageBase previous,
            final String baseUrl) {
        super(factory, previous, baseUrl, "login");
    }

    /**
     * @param username the username to enter
     * @param password the password to enter
     * @return the page we got here from
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
