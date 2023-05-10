package com.ivakulchyk.task;

import com.codeborne.selenide.junit5.TextReportExtension;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.ivakulchyk.task.WebDriverConfig.defaultDriverConfiguration;

@ExtendWith({TextReportExtension.class})
public class TestBase {

    protected static SoftAssertions softAssertions;

    @BeforeAll
    static void defaultSetUp() {
        defaultDriverConfiguration();
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(true)
        );
        softAssertions = new SoftAssertions();
    }
}
