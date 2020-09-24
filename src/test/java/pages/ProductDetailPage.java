package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ProductDetailPage extends BasePage{

    @FindBy(css= "[class=\"name\"]")
    private WebElement productName;

    @FindBy(css="[class=\"price-container\"] ")
    private WebElement productPrice;

    @FindBy(css= "[id=\"more-information\"] > p")
    private WebElement productDescription;

    @FindBy(css= "[onclick*=\"addToCart\"] ")
    private WebElement addToCart;

    @FindBy(css = "[class=\"navbar-nav ml-auto\"] > li:nth-child(1)")
    private WebElement homeLink;

    @FindBy(css = "[class=\"navbar-nav ml-auto\"] > li:nth-child(4)")
    private WebElement cartLink;

    public ProductDetailPage(WebDriver driver) {
        super(driver);
    }

    public String getProductName(){
        String Name= productName.getText();
        return Name;
    }
    public int getProductPrice(){
        int Price= Integer.parseInt(productPrice.getText().replace("$","").replace("*includes tax","").trim());
        return Price;
    }
    public String getProductDescription(){
        String Desc= productDescription.getText();
        return Desc;
    }

    public HomePage clickAddToCart(){
        click(addToCart);
        acceptAlert();
        click(homeLink);
        return new HomePage(getDriver());
    }

    public CartPage clickCartLink(){
        click(cartLink);
        return new CartPage(getDriver());
    }

}
