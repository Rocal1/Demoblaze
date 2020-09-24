package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Stream;

public class CartPage extends BasePage{

    @FindAll(@FindBy(css= "[id=\"tbodyid\"] > tr > td:nth-child(2)"))
    private List<WebElement> productName;

    @FindAll(@FindBy(css= "[id=\"tbodyid\"] > tr > td:nth-child(3)"))
    private List<WebElement> productPrice;

    @FindBy(id= "totalp")
    private WebElement total;

    @FindBy(id="nameofuser")
    private WebElement nameofuser;

    @FindBy(css= "[class=\"btn btn-success\"]")
    private WebElement placeOrderButton;
    //----------------------FORM
    @FindBy(id= "name")
    private WebElement modalName;
    @FindBy(id= "country")
    private WebElement modalCountry;
    @FindBy(id= "city")
    private WebElement modalCity;
    @FindBy(id= "card")
    private WebElement modalCreditCard;
    @FindBy(id= "month")
    private WebElement modalMonth;
    @FindBy(id= "year")
    private WebElement modalYear;
    @FindBy(css = "[onclick=\"purchaseOrder()\"]")
    private WebElement purchaseButton;
    @FindBy(css = "[class=\"lead text-muted \"]")
    private WebElement confirmationModal;
    @FindBy(css = "[class=\"confirm btn btn-lg btn-primary\"]")
    private WebElement confirmationModalButton;
    @FindBy(css = "[class=\"sa-line sa-tip animateSuccessTip\"]")
    private WebElement success;

    public CartPage(WebDriver driver) {
        super(driver);
    }


    public boolean isProductNameInCart(String NameOfExpectedProduct){
        waitElementVisible(nameofuser);
        List<WebElement> productNameList = productName;
        boolean exist=productNameList.stream().anyMatch(name -> name.getText().contains(NameOfExpectedProduct));
        return exist;
    }
    public boolean isProductPriceInCart(int PriceOfExpectedProduct){
        List<WebElement> productPriceList = productPrice;
        boolean exist=productPriceList.stream().anyMatch(price -> price.getText().contains(Integer.toString(PriceOfExpectedProduct)));
        return exist;
    }

    public int getTotal(){
        int Total = Integer.parseInt(total.getText());
        return Total;
    }

    public CartPage clickPlaceOrder(){
        click(placeOrderButton);
        return this;
    }

    public CartPage fillForm(String name, String Country, String City, String CreditCard, String Month, String Year){
        waitElementVisible(modalName);
        modalName.sendKeys(name);
        modalCity.sendKeys(City);
        modalCountry.sendKeys(Country);
        modalCreditCard.sendKeys(CreditCard);
        modalMonth.sendKeys(Month);
        modalYear.sendKeys(Year);
        click(purchaseButton);
        return this;
    }

    public HomePage clickOkModal() throws InterruptedException {
        waitElementVisible(success);
        Thread.sleep(2000);
        click(confirmationModalButton);
        return new HomePage(getDriver());
    }

    public String getConfirmationModal(){
        waitElementVisible(confirmationModal);
        String information = confirmationModal.getText();
        waitElementVisible(success);
        return information;
    }
}
