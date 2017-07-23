package org.schoellerfamily.gedbrowser.selenium.pageobjects;

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
        sendText("input[name='username']", username);
        sendText("input[name='password']", password);
        getWebElement("input[value='Login']").click();
        return getPrevious();
    }
}
