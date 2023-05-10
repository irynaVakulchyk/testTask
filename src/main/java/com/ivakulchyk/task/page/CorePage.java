package com.ivakulchyk.task.page;

import com.ivakulchyk.task.annotation.Required;
import com.ivakulchyk.task.waiter.Waiter;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static java.util.Arrays.stream;

public abstract class CorePage {

    protected CorePage() {
        page(this);
        getWebDriver().switchTo().defaultContent();
        Waiter.waitUntilElementAttributeStyleNone($("#loadingMessage"));
        switchToFrameLive();
        waitElementsOnPage();
    }

    public void switchToFrameLive() {
        getWebDriver().switchTo().frame("framelive");
    }

    public void closeWindow() {
        getWebDriver().close();
    }

    @Step("Wait for page loading")
    public void waitElementsOnPage() {
        Waiter.waitForPageLoad();
        stream(getClass().getDeclaredFields())
                .filter(f -> f.getDeclaredAnnotation(Required.class) != null)
                .map(f -> f.getDeclaredAnnotation(FindBy.class))
                .map(CorePage::getLocator)
                .forEach(Waiter::waitUntilElementVisible);
    }

    private static By getLocator(FindBy findBy) {
        if (findBy.css() != null) {
            return By.cssSelector(findBy.css());
        }
        if (findBy.xpath() != null) {
            return By.xpath(findBy.xpath());
        }
        if (findBy.id() != null) {
            return By.id(findBy.id());
        }
        return null;
    }

}
