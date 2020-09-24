package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.CartPage;
import pages.HomePage;
import pages.ProductDetailPage;

public class PurchaseTest extends BaseTest{

    @Test(description = "Purchase two of the most expensive products")
    public void purchaseTwoProducts() throws InterruptedException {
        HomePage homePage = new HomePage(myDriver.getDriver());
        homePage.loginUser("123", "123");
        Assert.assertTrue(homePage.nameDisplayed(), "Name of user is not displayed.");
        //LAPTOP
        homePage.selectLaptops();
        String LaptopName= homePage.getMostExpensiveProductName();
        int LaptopPrice=homePage.getMostExpensiveProductPrice();
        String LaptopDesc=homePage.getMostExpensiveProductDescription();
        ProductDetailPage productDetailPage = homePage.selectMostExpensiveProduct();
        String LaptopDetailName = productDetailPage.getProductName();
        int LaptopDetailPrice= productDetailPage.getProductPrice();
        String LaptopDetailDesc= productDetailPage.getProductDescription();
        Assert.assertEquals(LaptopName,LaptopDetailName);
        Assert.assertEquals(LaptopPrice,LaptopDetailPrice);
        Assert.assertEquals(LaptopDesc,LaptopDetailDesc);
        homePage = productDetailPage.clickAddToCart();
        //MONITOR
        homePage.selectMonitors();
        String MonitorName= homePage.getMostExpensiveProductName();
        int MonitorPrice=homePage.getMostExpensiveProductPrice();
        String MonitorDesc=homePage.getMostExpensiveProductDescription();
        productDetailPage = homePage.selectMostExpensiveProduct();
        String MonitorDetailName = productDetailPage.getProductName();
        int MonitorDetailPrice= productDetailPage.getProductPrice();
        String MonitorDetailDesc= productDetailPage.getProductDescription();
        Assert.assertEquals(MonitorName,MonitorDetailName);
        Assert.assertEquals(MonitorPrice,MonitorDetailPrice);
        Assert.assertEquals(MonitorDesc,MonitorDetailDesc);
        productDetailPage.clickAddToCart();
        //CartPage
        CartPage cartPage= productDetailPage.clickCartLink();
        Assert.assertTrue(cartPage.isProductNameInCart(LaptopName),"Laptop Name validation");
        Assert.assertTrue(cartPage.isProductNameInCart(MonitorName),"Monitor Name validation");
        Assert.assertTrue(cartPage.isProductPriceInCart(MonitorPrice),"Laptop Price validation");
        Assert.assertTrue(cartPage.isProductPriceInCart(LaptopPrice),"Monitor Price validation");
        Assert.assertEquals((MonitorPrice+LaptopPrice), cartPage.getTotal(),"Total Validation Cart");
        cartPage.clickPlaceOrder();
        //Modal
        cartPage.fillForm("Rodrigo", "Argentina", "Tucuman", "123123", "9","2020");
        String information = cartPage.getConfirmationModal();
        //System.out.println(information);//PRINTLN
        Assert.assertTrue(information.contains(Integer.toString(cartPage.getTotal())),"Total amount validation");
        Assert.assertTrue(information.contains("123123"),"Credit card number validation");
        Assert.assertTrue(information.contains("Rodrigo"),"Name validation");
        homePage= cartPage.clickOkModal();
        homePage.logout();
        Assert.assertFalse(homePage.nameDisplayed());

    }

}
