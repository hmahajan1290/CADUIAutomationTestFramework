package com.cadd.qa.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class TestBase {

	public static WebDriver driver;
	public static Properties prop;
	static Actions act;
	Logger log = Logger.getLogger(TestBase.class);
	public static boolean testFailed = false;
	
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
	
	public static void initialization() throws MalformedURLException
	{
		String browserName = prop.getProperty("browser");
		
		if(browserName.equals("chrome"))
		{
			ChromeOptions options = new ChromeOptions();
			HashMap<String, Object> chromePref = new HashMap<>();
			chromePref.put("download.default_directory", new File("").getAbsolutePath() + "/Downloads");
			System.out.println("Downloads path: " + new File("").getAbsolutePath() + "/Downloads");
			options.setExperimentalOption("prefs", chromePref);
			
			if(prop.getProperty("remote").equals("true"))
			{
				DesiredCapabilities cap = new DesiredCapabilities();
				options.addArguments("--headless");
				options.addArguments("--disable-gpu");
				options.addArguments("--no-sandbox");
				cap.setCapability(ChromeOptions.CAPABILITY, options);
				cap.setBrowserName("chrome");
				cap.setPlatform(Platform.LINUX);
				options.merge(cap);
				System.out.println("Running tests in headless mode");
				URL gridUrl = URI.create("http://localhost:4444/wd/hub").toURL();
				driver = new RemoteWebDriver(gridUrl, options);
			}
			else
			{
				System.setProperty("webdriver.chrome.driver", new File("").getAbsolutePath() + "/BrowserDrivers/chromedriver");
				System.out.println("Browserdriver path: " + new File("").getAbsolutePath() + "/BrowserDrivers/chromedriver");
				driver = new ChromeDriver(options);
			}
			
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
		try
		{
			act.moveToElement(element).build().perform();
			log.info("Hovering element" + element.toString());
		}
		catch (Exception e) 
		{
			testFailed = true;
			log.error("ERROR in hoverElement method", e);
			e.printStackTrace();
		}
	}
	
	public void sendKeys(WebElement element, String value)
	{
		try 
		{
			element.clear();
			element.sendKeys(value);
			log.info("Entering '" + value + "' to element '" + element.toString() + "'");
		} 
		catch (Exception e) 
		{
			testFailed = true;
			log.error("ERROR in SendKeys method", e);
			e.printStackTrace();
		}
	}
	
	public void click(WebElement element, String elementName)
	{
		try
		{
			element.click();
			log.info("Clicking '" + elementName + "'");
		}
		catch (Exception e)
		{
			testFailed = true;
			log.error("ERROR in Click method", e);
			e.printStackTrace();
		}
	}
	
	public String getPageTitle()
	{
		return driver.getTitle();
	}
	
	public void moveToElement(WebElement element)
	{
		try
		{
			act.moveToElement(element).build().perform();
			log.info("Moving to element" + element.toString());
		}
		catch (Exception e)
		{
			testFailed = true;
			log.error("ERROR in moveToElement method", e);
			e.printStackTrace();
		}
	}
}
