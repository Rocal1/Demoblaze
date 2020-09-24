package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class HomePage extends BasePage{
    @FindBy(id= "signin2")
    private WebElement signupLink;

    @FindBy(id="sign-username")
    private WebElement usernameSignUp;

    @FindBy(id= "sign-password")
    private WebElement passwordSignUp;

    @FindBy(id= "loginusername")
    private WebElement signupButton;

    @FindBy(id= "login2")
    private WebElement loginLink;

    @FindBy(id= "loginusername")
    private WebElement usernameLogin;

    @FindBy(id= "loginpassword")
    private WebElement passwordLogin;

    @FindBy(css = "[onclick=\"logIn()\"]")
    private WebElement loginButton;

    @FindBy(id="nameofuser")
    private WebElement nameofuser;

    @FindBy(css="[onclick=\"byCat('phone')\"]")
    private WebElement phoneCategory;

    @FindBy(css="[onclick=\"byCat('notebook')\"]")
    private WebElement laptopsCategory;

    @FindBy(css="[onclick=\"byCat('monitor')\"]")
    private WebElement monitorCategory;

    @FindAll(@FindBy( css= "[id=\"tbodyid\"] > div [class=\"card h-100\"] > div h4 a"))
    private List<WebElement> product;

    @FindAll(@FindBy( css= "[id=\"tbodyid\"] > div [class=\"card h-100\"] > div h5"))
    private List<WebElement> price;

    @FindAll(@FindBy( css= "p[id=\"article\"]"))
    private List<WebElement> description;

    @FindBy(css = "[class=\"navbar-nav ml-auto\"] > li:nth-child(6)")
    private WebElement logOutLink;

    @FindAll(@FindBy(css = "[class=\"card-img-top img-fluid\"]"))
    private List<WebElement> card;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage signupUser (String username, String password){
        click(signupLink);
        usernameSignUp.sendKeys(username);
        passwordSignUp.sendKeys(password);
        click(signupButton);
        return this;
    }

    public HomePage loginUser (String username, String password){
        clickWithActionsBuilder(loginLink);
        usernameLogin.sendKeys(username);
        passwordLogin.sendKeys(password);
        clickWithActionsBuilder(loginButton);
        waitElementClickable(nameofuser);
        return this;
    }

    public boolean nameDisplayed(){
        boolean Displayed=false;
        if(nameofuser.getAttribute("style").contains("block")) {
            Displayed = true;
        }
        return Displayed;
    }

    public HomePage logout(){
        waitAllElementVisible(card);
        clickWithActionsBuilder(logOutLink);
        return this;
    }

    public HomePage selectLaptops()throws InterruptedException{
        clickWithActionsBuilder(laptopsCategory);
        waitElementVisible(nameofuser);
        waitUntilCategoryLoad();
        return this;
    }

    public HomePage selectMonitors() throws InterruptedException {
        scrollDownToElement(monitorCategory);
        waitElementVisible(nameofuser);
        clickWithActionsBuilder(monitorCategory);
        waitUntilCategoryLoad();
        return this;
    }


    public int findMostExpensiveProduct(){
        int Price=0;
        int index=0;
        for (int i = 0; i < product.size(); i++) {
            if(Price < Integer.parseInt(price.get(i).getText().replace("$",""))){
                index = i;
                Price=Integer.parseInt(price.get(i).getText().replace("$",""));
            }
        }
        return index;
    }

    public String getMostExpensiveProductName (){
        waitAllElementVisible(card);
        String NameMostExpensiveProduct = product.get(findMostExpensiveProduct()).getText();
        return NameMostExpensiveProduct;
    }
    public int getMostExpensiveProductPrice (){
        waitAllElementVisible(card);
        int PriceMostExpensiveProduct = Integer.parseInt(price.get(findMostExpensiveProduct()).getText().replace("$",""));;
        return PriceMostExpensiveProduct;
    }
    public String getMostExpensiveProductDescription (){
        waitAllElementVisible(card);
        String DescriptionMostExpensiveProduct = description.get(findMostExpensiveProduct()).getText();
        return DescriptionMostExpensiveProduct;
    }

    public ProductDetailPage selectMostExpensiveProduct(){
        int index= findMostExpensiveProduct();
        scrollDownToElement(product.get(index));
        clickWithActionsBuilder(product.get(index));
        return new ProductDetailPage(getDriver());
    }


}
