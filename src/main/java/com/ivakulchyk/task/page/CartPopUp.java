package com.ivakulchyk.task.page;

import com.ivakulchyk.task.annotation.Required;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;

public class CartPopUp extends CorePage {

    @Required
    @FindBy(css = "h6.product-name")
    private SelenideElement productName;

    @FindBy(css = "span.product-quantity strong")
    private SelenideElement productQuantity;

    @FindBy(css = "p.cart-products-count")
    private SelenideElement productsCount;

    @Required
    @FindBy(css = "div.cart-content-btn > button.btn-secondary")
    private SelenideElement continueShopping;

    @Required
    @FindBy(css = "div.cart-content-btn > a.btn-primary")
    private SelenideElement proceedToCheckout;

    public CartPopUp() {
        super();
    }

    @Step("Get product quantity")
    public String getProductQuantity() {
        return productQuantity.getText();
    }

    @Step("Get products count")
    public String getProductsCount() {
        return productsCount.getText();
    }

    @Step("Click on proceed to checkout")
    public ShoppingCartPage clickProceedToCheckout() {
        proceedToCheckout.click();
        return new ShoppingCartPage();
    }

    @Step("Click on continue shopping")
    public ProductPage clickContinueShopping() {
        continueShopping.click();
        return new ProductPage();
    }
}
