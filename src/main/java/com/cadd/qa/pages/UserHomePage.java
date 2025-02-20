package com.cadd.qa.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.cadd.qa.base.TestBase;

public class UserHomePage extends TestBase{
	
	Logger log = Logger.getLogger(UserHomePage.class);

	//Page Factory - OR
	
	@FindBy(linkText = "CAD")
	WebElement linkCAD;
	
	@FindBy(xpath = "//span[@id='navProjectName']")
	WebElement navProjectName;
	
	@FindBy(xpath = "//div[@class='nav-tab']//a[contains(@href, 'editprofile')]")
	WebElement loggedInUserName;
	
	@FindBy(xpath = "//section[@id='fileTypeBar']//a[@href='/cad-drawings']")
	WebElement linkCADDrawingsFileType;
	
	public UserHomePage()
	{
		PageFactory.initElements(driver, this);
	}
	
	public void clickCADLink()
	{
		click(linkCAD, "CAD");
	}
	
	public CADDrawingsPage clickCADDrawings()
	{
		click(linkCADDrawingsFileType, "CAD Drawings");
		return new CADDrawingsPage();
	}
	
	public void hoverProjectName()
	{
		hoverElement(navProjectName);
	}
	
	public void validateLoggedInUserName()
	{
		hoverProjectName();
		String userName = loggedInUserName.getText();
		Assert.assertEquals(userName, prop.getProperty("username"));
		log.info("[ASSERT PASSED] - User '" + userName + "'is logged in into the application as expected");
	}
}
