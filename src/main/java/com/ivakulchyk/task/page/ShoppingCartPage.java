package com.ivakulchyk.task.page;

import com.ivakulchyk.task.annotation.Required;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Selenide.$x;

public class ShoppingCartPage extends CorePage {

    @FindBy(css = "div#cart-subtotal-products span.label")
    private SelenideElement totalProduct;

    @Required
    @FindBy(css = "div.card-block a.btn-primary")
    private SelenideElement proceedToCheckout;

    private static String productQuantityByName = "//div[@class='product-line-info']//a[text()=\"%s\"]/ancestor::div[@class='product-line-grid']//input";

    public ShoppingCartPage() {
        super();
    }

    @Step("Get total product count")
    public String getTotalProduct() {
        return totalProduct.getText();
    }

    @Step("Click on proceed to checkout")
    public OrderShopping clickProceedToCheckout() {
        proceedToCheckout.click();
        return new OrderShopping();
    }

    @Step("Get product quantity by name")
    public int getProductQuantityByName(String name) {
        SelenideElement element = $x(productQuantityByName.formatted(name));
        return Integer.parseInt(element.getValue());
    }
}
