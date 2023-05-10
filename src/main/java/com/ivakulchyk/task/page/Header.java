package com.ivakulchyk.task.page;

import com.ivakulchyk.task.annotation.Required;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;

public class Header extends CorePage {

    @Required
    @FindBy(css = "[alt='PrestaShop']")
    private SelenideElement prestaShopLink;

    public Header() {
        super();
    }

    @Step("Open catalog page")
    public CatalogPage clickPrestaShopLink() {
        prestaShopLink.click();
        return new CatalogPage();
    }
}
