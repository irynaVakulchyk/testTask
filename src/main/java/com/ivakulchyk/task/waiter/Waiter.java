package com.ivakulchyk.task.waiter;

import com.codeborne.selenide.SelenideElement;
import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.function.Function;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class Waiter {

    private static WebDriverWait webDriverWait;

    private Waiter() {
    }

    public static WebDriverWait getWebDriverWait() {
        if (webDriverWait == null) {
            webDriverWait = new WebDriverWait(getWebDriver(), Duration.ofSeconds(10), Duration.ofSeconds(15));
        }

        return webDriverWait;
    }

    public static void waitForPageLoad() {
        Function<WebDriver, Boolean> loadCondition =
                x -> ((JavascriptExecutor) getWebDriver()).executeScript("return document.readyState")
                        .equals("complete");
        getWebDriverWait().until(loadCondition);
    }

    public static void waitUntilElementHaveValue(SelenideElement element, String text) {
        getWebDriverWait().until(ExpectedConditions.textToBePresentInElementValue(element, text));
    }

    public static void waitUntilElementAttributeStyleNone(SelenideElement element) {
        waitUntilElementAttributeToBe(element, "style", "display: none;");
    }

    public static void waitUntilElementAttributeToBe(SelenideElement element, String attribute, String value) {
        getWebDriverWait().until(ExpectedConditions.attributeToBe(element, attribute, value));
    }

    @SneakyThrows
    public static void waitUntilElementVisible(By by) {
        $(by).shouldBe(visible, Duration.ofSeconds(30));
    }
}
