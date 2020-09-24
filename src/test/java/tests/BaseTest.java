package tests;

import driver.MyDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.asserts.SoftAssert;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class BaseTest {
	
	private Properties prop;
	protected MyDriver myDriver;
	public static Logger log = LogManager.getLogger(BaseTest.class);

	@BeforeTest
	public void SetUp() throws IOException {
		log.info("-----------------------------START TEST-----------------------------------");
		myDriver = new MyDriver(getPropertyFile("browser"));
		myDriver.getDriver().manage().window().maximize();
		myDriver.getDriver().get(getPropertyFile("url"));
	}

	@AfterTest
	public void teardown() {
		log.info("------------------------------END TEST-----------------------------------");
		myDriver.disposeDriver();
	}
	
	public String getPropertyFile(String keyProperty) throws IOException {
		prop = new Properties();
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\main\\resources\\data.properties");
		prop.load(fis);
		return prop.getProperty(keyProperty);
	}

}
