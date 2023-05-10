package com.ivakulchyk.task.page;

import com.ivakulchyk.task.annotation.Required;
import com.codeborne.selenide.SelenideElement;
import com.ivakulchyk.task.waiter.Waiter;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.FindBy;

public class ProductPage extends CorePage {

    @Required
    @FindBy(css = "div.product-quantity [type='number']")
    private SelenideElement quantityInput;

    @Required
    @FindBy(css = "nav.breadcrumb li>span")
    private SelenideElement productName;

    @Required
    @FindBy(css = "[data-button-action='add-to-cart']")
    private SelenideElement addToCart;

    @Required
    @FindBy(css = "div.product-comment-list-item")
    private SelenideElement productComment;

    @Getter
    private Header header;

    public ProductPage() {
        super();
        header = new Header();
    }

    @Step("Set value [{value}] on quantity")
    public ProductPage setQuantity(String value) {
        quantityInput.sendKeys(Keys.DELETE, value);
        Waiter.waitUntilElementHaveValue(quantityInput, value);
        return this;
    }

    @Step("Click add to cart")
    public CartPopUp clickAddToCart() {
        addToCart.click();
        return new CartPopUp();
    }

    @Step("Get product name")
    public String getProductName() {
        return productName.text();
    }
}
