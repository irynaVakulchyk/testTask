package com.ivakulchyk.task.page;

import com.ivakulchyk.task.annotation.Required;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;

public class CatalogPage extends CorePage {

    @Required
    @FindBy(css = "h2.products-section-title")
    private SelenideElement productsSectionTitle;

    @Required
    @FindBy(css = "a.all-product-link")
    private SelenideElement allProductLink;

    @FindBy(css = "div.products > div.product a.product-thumbnail")
    private ElementsCollection productBlocks;

    public CatalogPage() {
        super();
    }

    @Step("Get count of products")
    public int getCountProducts() {
        return productBlocks.size();
    }

    @Step("Click on product with index [{index}]")
    public ProductPage clickOnProduct(int index) {
        productBlocks.get(index).click();
        return new ProductPage();
    }

}
