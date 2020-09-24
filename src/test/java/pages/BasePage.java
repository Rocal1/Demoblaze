package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;


public class BasePage {

	protected WebDriver driver;
	private WebDriverWait wait;
	private FluentWait<WebDriver> fluentWait;


	public BasePage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
	}

	public WebDriverWait getWait(int timeOut) {
		wait = new WebDriverWait(getDriver(), timeOut);
		return wait;
	}

	public FluentWait<WebDriver> getFluentWait(long timeOut, long every) {
		fluentWait = new FluentWait<WebDriver>(getDriver()).withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(every)).ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class);
		return fluentWait;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void click(WebElement element) {
		waitElementClickable(element);
		element.click();
	}

	public void acceptAlert(){
		getWait(7).until(ExpectedConditions.alertIsPresent());
		getDriver().switchTo().alert().accept();
	}

	protected void waitElementClickable(WebElement element) {
		getFluentWait(15, 2).until(ExpectedConditions.elementToBeClickable(element));
	}

	protected void waitElementNotClickable(WebElement element) {
		getWait(7).until(ExpectedConditions.not((ExpectedConditions.elementToBeClickable(element))));
	}

	protected void waitAllElementVisible(List<WebElement> elements) {
		getWait(10).until(ExpectedConditions.visibilityOfAllElements(elements));
	}

	protected void waitAllElementNotVisible(List<WebElement> elements) {
		getWait(7).until(ExpectedConditions.invisibilityOfAllElements(elements));
	}

	protected void waitElementVisible(WebElement element) {
		getFluentWait(15, 2).until(ExpectedConditions.visibilityOf(element));
	}

	protected void waitElementNotVisible(WebElement element) {
		getWait(10).until(ExpectedConditions.invisibilityOf(element));
	}


	public boolean clickWithActionsBuilder(WebElement element) {
		waitElementClickable(element);
		try {
			Actions builder = new Actions(getDriver());
			builder.moveToElement(element, 5, 5).click(element);
			builder.perform();
			return true;
		} catch (TimeoutException toe) {
			return false;
		}
	}
	protected void scrollDownToElement(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", element);
	}

	protected void waitUntilCategoryLoad() {
		getFluentWait(15, 2).until(ExpectedConditions.numberOfElementsToBeLessThan(By.cssSelector("[class=\"card-img-top img-fluid\"]"), 9));
	}

	protected void waitUntilCardsLoad(List<WebElement> elements, WebElement category){
		int TotalCards = elements.size();
		do {
			clickWithActionsBuilder(category);

		}
		while (elements.size()==TotalCards);
	}
}
