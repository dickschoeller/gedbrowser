package org.schoellerfamily.gedbrowser.selenium.pageobjects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebElement;

/**
 * @author Dick Schoeller
 */
public class LoginPage extends PageBase {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

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
        logger.info("++++++++++++++++++++++++++++++++++++++++++++++++++++");
        logger.info("++++++++++++++++++++++++++++++++++++++++++++++++++++");
        logger.info("username: " + username + " password: " + password);
        logger.info("++++++++++++++++++++++++++++++++++++++++++++++++++++");
        logger.info("++++++++++++++++++++++++++++++++++++++++++++++++++++");
        sendText("input[name='username']", username);
        sendText("input[name='password']", password);
        final WebElement webElement = getWebElement("input[value='Login']");
        logger.info("webelement: " + webElement);
        webElement.click();
        waitForPageLoaded();
        return getPrevious();
    }
}
