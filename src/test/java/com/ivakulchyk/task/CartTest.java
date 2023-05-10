package com.ivakulchyk.task;

import com.codeborne.selenide.Selenide;
import com.ivakulchyk.task.page.CartPopUp;
import com.ivakulchyk.task.page.CatalogPage;
import com.ivakulchyk.task.page.OrderShopping;
import com.ivakulchyk.task.page.ProductPage;
import com.ivakulchyk.task.page.ShoppingCartPage;
import com.ivakulchyk.task.util.RandomUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.ivakulchyk.task.util.PropertyUtil.getProperty;
import static com.ivakulchyk.task.util.PropertyUtil.label;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@Slf4j
class CartTest extends TestBase {

    private static CatalogPage catalogPage;

    @BeforeEach
    void setUp() {
        Selenide.open(getProperty("env.url"));
        catalogPage = new CatalogPage();
    }

    @Order(1)
    @Test
    void testCountProductAtCartPopUp() {
        int productNumber1 = RandomUtils.randomize(0, catalogPage.getCountProducts());
        int countOfProduct1 = RandomUtils.randomize(2, 10);

        log.info("Click on product with number {}", productNumber1);
        ProductPage productPage = catalogPage
                .clickOnProduct(productNumber1);

        log.info("Add product with quantity {}", countOfProduct1);
        productPage
                .setQuantity(String.valueOf(countOfProduct1));

        CartPopUp cartPopUp = productPage.clickAddToCart();
        int actualCountOfProduct = Integer.parseInt(cartPopUp.getProductQuantity());

        softAssertions.assertThat(actualCountOfProduct)
                .as("Product quantity on cart popUp is not correct")
                .isEqualTo(countOfProduct1);
        softAssertions.assertThat(cartPopUp.getProductsCount())
                .as("Product count on cart popUp is not correct")
                .isEqualTo(label("cartPopUp.quantity", countOfProduct1));

        softAssertions.assertAll();
    }

    @Order(2)
    @ParameterizedTest
    @MethodSource("randomCountOfProducts")
    void testCountProductsAtCartPopUp(int countOfProduct1, int countOfProduct2) {
        int productNumber1 = RandomUtils.randomize(0, catalogPage.getCountProducts());
        int productNumber2 = RandomUtils.randomize(0, catalogPage.getCountProducts(), productNumber1);

        log.info("Click on product with number {}", productNumber1);
        ProductPage productPage = catalogPage
                .clickOnProduct(productNumber1);

        log.info("Add product with quantity {}", countOfProduct1);
        productPage
                .setQuantity(String.valueOf(countOfProduct1));

        CartPopUp cartPopUp = productPage.clickAddToCart();
        int actualCountOfProduct = Integer.parseInt(cartPopUp.getProductQuantity());

        softAssertions.assertThat(actualCountOfProduct)
                .as("Product quantity on cart popUp is not correct")
                .isEqualTo(countOfProduct1);
        softAssertions.assertThat(cartPopUp.getProductsCount())
                .as("Product count on cart popUp is not correct")
                .isEqualTo(label("cartPopUp.quantity", countOfProduct1));

        log.info("Click on product with number {}", productNumber2);
        cartPopUp
                .clickContinueShopping()
                .getHeader()
                .clickPrestaShopLink()
                .clickOnProduct(productNumber2);

        log.info("Add product with quantity {}", countOfProduct2);
        String productTitle2 = productPage
                .setQuantity(String.valueOf(countOfProduct2))
                .getProductName();
        productPage.clickAddToCart();
        actualCountOfProduct = Integer.parseInt(cartPopUp.getProductQuantity());

        softAssertions.assertThat(actualCountOfProduct)
                .as("Product quantity on cart popUp is not correct")
                .isEqualTo(countOfProduct2);
        softAssertions.assertThat(cartPopUp.getProductsCount())
                .as("Product count on cart popUp is not correct")
                .isEqualTo(label("cartPopUp.quantity", countOfProduct1 + countOfProduct2));

        softAssertions.assertAll();
    }

    @Order(3)
    @ParameterizedTest
    @MethodSource("randomCountOfProducts")
    void testCountOfProductsShoppingCartPageToCart(int countOfProduct1, int countOfProduct2) {
        int productNumber1 = RandomUtils.randomize(0, catalogPage.getCountProducts());
        int productNumber2 = RandomUtils.randomize(0, catalogPage.getCountProducts(), productNumber1);

        log.info("Click on product with number {}", productNumber1);
        ProductPage productPage = catalogPage
                .clickOnProduct(productNumber1);

        log.info("Add product with quantity {}", countOfProduct1);
        productPage
                .setQuantity(String.valueOf(countOfProduct1));

        String productTitle1 = productPage.getProductName();

        log.info("Click on product with number {}", productNumber2);
        productPage.clickAddToCart()
                .clickContinueShopping()
                .getHeader()
                .clickPrestaShopLink()
                .clickOnProduct(productNumber2);

        log.info("Add product with quantity {}", countOfProduct2);
        String productTitle2 = productPage
                .setQuantity(String.valueOf(countOfProduct2))
                .getProductName();

        log.info("Verify quantity of products at cart");
        ShoppingCartPage shoppingCartPage = productPage
                .clickAddToCart()
                .clickProceedToCheckout();

        String totalProduct = shoppingCartPage.getTotalProduct();

        softAssertions.assertThat(totalProduct)
                .as("Product count on shopping cart page is not correct")
                .isEqualTo(label("cartPage.totalProduct", countOfProduct1 + countOfProduct2));

        softAssertions.assertThat(shoppingCartPage.getProductQuantityByName(productTitle1))
                .as("Count of product %s on order page is not correct".formatted(productTitle1))
                .isEqualTo(countOfProduct1);
        softAssertions.assertThat(shoppingCartPage.getProductQuantityByName(productTitle2))
                .as("Count of product %s on order page is not correct".formatted(productTitle2))
                .isEqualTo(countOfProduct2);

        softAssertions.assertAll();
    }

    @Order(4)
    @ParameterizedTest
    @MethodSource("randomCountOfProducts")
    void testAbilityToBuyThroughTheCart(int countOfProduct1, int countOfProduct2) {
        int productNumber1 = RandomUtils.randomize(0, catalogPage.getCountProducts());
        int productNumber2 = RandomUtils.randomize(0, catalogPage.getCountProducts(), productNumber1);

        log.info("Add two random product with different quantity");
        CartPopUp cartPopUp = catalogPage
                .clickOnProduct(productNumber1)
                .setQuantity(String.valueOf(countOfProduct1))
                .clickAddToCart()
                .clickContinueShopping()
                .getHeader()
                .clickPrestaShopLink()
                .clickOnProduct(productNumber2)
                .setQuantity(String.valueOf(countOfProduct2))
                .clickAddToCart();

        log.info("Verify quantity of products at cart");
        OrderShopping orderShopping = cartPopUp
                .clickProceedToCheckout()
                .clickProceedToCheckout();
        String totalProduct = orderShopping.getTotalProduct();

        softAssertions.assertThat(totalProduct)
                .as("Product count on order page is not correct")
                .isEqualTo(label("cartPage.totalProduct", countOfProduct1 + countOfProduct2));

        softAssertions.assertAll();
    }

    @AfterEach
    void cleanup() {
        catalogPage.closeWindow();
    }

    static Stream<Arguments> randomCountOfProducts() {
        return Stream.of(
                arguments(RandomUtils.randomize(2, 10), RandomUtils.randomize(2, 10))
        );
    }
}
