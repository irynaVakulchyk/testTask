package com.ivakulchyk.task;

import com.codeborne.selenide.Configuration;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.openqa.selenium.chrome.ChromeOptions;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WebDriverConfig {

    public static void defaultDriverConfiguration() {
        Configuration.browserSize = "1920x1080";
        Configuration.screenshots = true;
        Configuration.timeout = 15000;
        Configuration.browserCapabilities = new ChromeOptions().addArguments("--remote-allow-origins=*");
    }
}
