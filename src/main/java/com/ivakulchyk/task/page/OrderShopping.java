package com.ivakulchyk.task.page;

import com.ivakulchyk.task.annotation.Required;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;

public class OrderShopping extends CorePage {

    @Required
    @FindBy(css = "div.cart-summary-products p")
    private SelenideElement totalProduct;

    public OrderShopping() {
        super();
    }

    @Step("Get total product count")
    public String getTotalProduct() {
        return totalProduct.getText();
    }

}
