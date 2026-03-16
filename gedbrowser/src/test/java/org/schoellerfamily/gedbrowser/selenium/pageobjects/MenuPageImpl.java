package org.schoellerfamily.gedbrowser.selenium.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import lombok.RequiredArgsConstructor;

/**
 * This implementation of MenuPage methods. Designed for use behind a Java 8
 * mixin facade.
 *
 * @author Dick Schoeller
 */
@RequiredArgsConstructor
public final class MenuPageImpl implements MenuPage {
    /**
     * The page that facades for this class.
     */
    private final PageBase page;

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

    @Override
    public SourcesPage clickSources() {
        final WebElement element = getMenu("sources");
        element.click();
        page.waitForPageLoaded();
        return page.getFactory().createSourcesPage(page, page.getBaseUrl());
    }

    @Override
    public SubmittersPage clickSubmitters() {
        final WebElement element = getMenu("submitters");
        element.click();
        page.waitForPageLoaded();
        return page.getFactory().createSubmittersPage(page, page.getBaseUrl());
    }

    @Override
    public LoginPage clickLogin() {
        final WebElement element = page.getWebElement("a[href='login']");
        element.click();
        page.waitForPageLoaded();
        return page.getFactory().createLoginPage(page, page.getBaseUrl());
    }

    @Override
    public PageBase clickLogout() {
        final WebElement element = page.getWebElement("input[class='menubar']");
        element.click();
        return page;
    }

    @Override
    public boolean isMenuPresent(final String name) {
        try {
            getMenu(name);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private WebElement getMenu(final String name) {
        return page.getWebElement(By.id(name + "-menu"));
    }
}
