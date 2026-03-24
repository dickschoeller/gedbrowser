package org.schoellerfamily.gedbrowser.selenium.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import lombok.RequiredArgsConstructor;



/**
 * Represents menu page impl.
 *
 * @author Richard Schoeller
 */
@RequiredArgsConstructor
public final class MenuPageImpl implements MenuPage {
    /**
     * The page that facades for this class.
     */
    private final PageBase page;

    /**
     * Executes click index.
     *
     * @return the resulting index page
     */
    @Override
    public IndexPage clickIndex() {
        final WebElement element = getMenu("index");
        element.click();
        final long multiplier = 4;
        page.sleep(multiplier);
        page.waitForPageLoaded(multiplier);
        return page.getFactory().createIndexPage(page, page.getBaseUrl(),
                page.getIndexLetter());
    }

    /**
     * Executes click sources.
     *
     * @return the resulting sources page
     */
    @Override
    public SourcesPage clickSources() {
        final WebElement element = getMenu("sources");
        element.click();
        page.waitForPageLoaded();
        return page.getFactory().createSourcesPage(page, page.getBaseUrl());
    }

    /**
     * Executes click submitters.
     *
     * @return the resulting submitters page
     */
    @Override
    public SubmittersPage clickSubmitters() {
        final WebElement element = getMenu("submitters");
        element.click();
        page.waitForPageLoaded();
        return page.getFactory().createSubmittersPage(page, page.getBaseUrl());
    }

    /**
     * Executes click login.
     *
     * @return the resulting login page
     */
    @Override
    public LoginPage clickLogin() {
        final WebElement element = page.getWebElement("a[href='login']");
        element.click();
        page.waitForPageLoaded();
        return page.getFactory().createLoginPage(page, page.getBaseUrl());
    }

    /**
     * Executes click logout.
     *
     * @return the resulting page base
     */
    @Override
    public PageBase clickLogout() {
        final WebElement element = page.getWebElement("input[class='menubar']");
        element.click();
        return page;
    }

    /**
     * Checks whether menu present.
     *
     * @param name the name to use
     * @return true if the condition is met; otherwise false
     */
    @Override
    public boolean isMenuPresent(final String name) {
        try {
            getMenu(name);
            return true;
        } catch (NoSuchElementException _) {
            return false;
        }
    }

    private WebElement getMenu(final String name) {
        return page.getWebElement(By.id(name + "-menu"));
    }
}
