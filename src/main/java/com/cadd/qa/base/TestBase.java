package com.cadd.qa.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

public class TestBase {

	public static WebDriver driver;
	public static Properties prop;
	static Actions act;
	Logger log = Logger.getLogger(TestBase.class);
	
	public TestBase()
	{
		try
		{
			prop = new Properties();
			FileInputStream ip = new FileInputStream(new File("").getAbsolutePath() + "/src/main/java/com/cadd/qa/config/config.properties");
			prop.load(ip);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void initialization()
	{
		String browserName = prop.getProperty("browser");
		
		if(browserName.equals("chrome"))
		{
			System.setProperty("webdriver.chrome.driver", new File("").getAbsolutePath() + "/BrowserDrivers/chromedriver");
			ChromeOptions options = new ChromeOptions();
			HashMap<String, Object> chromePref = new HashMap<>();
			chromePref.put("download.default_directory", new File("").getAbsolutePath() + "/Downloads");
			options.setExperimentalOption("prefs", chromePref);
			driver = new ChromeDriver(options);
			act = new Actions(driver);
		}
		else if(browserName.equals("firefox"))
		{
			System.setProperty("webdriver.gecko.driver", "/Users/tejshad/eclipse-workspace/Browsers/geckodriver");
			driver = new FirefoxDriver();
		}
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		
		driver.get(prop.getProperty("url"));
	}
	
	public void hoverElement(WebElement element)
	{
		act.moveToElement(element).build().perform();
		log.info("Hovering element" + element.toString());
	}
	
	public void sendKeys(WebElement element, String value)
	{
		element.clear();
		element.sendKeys(value);
		log.info("Entering " + value + "' to element '" + element.toString() + "'");
	}
	
	public void click(WebElement element, String elementName)
	{
		element.click();
		log.info("Clicking '" + elementName + "'");
	}
	
	public String getPageTitle()
	{
		return driver.getTitle();
	}
	
	public void moveToElement(WebElement element)
	{
		act.moveToElement(element).build().perform();
		log.info("Moving to element" + element.toString());
	}
}
