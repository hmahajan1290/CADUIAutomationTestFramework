package com.cadd.qa.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cadd.qa.base.TestBase;

public class HomePage extends TestBase {
	
	//Page Factory - OR
	
	@FindBy(id = "siteLogin")
	WebElement btnSignIn;
	
	@FindBy(linkText = "CAD")
	WebElement linkCAD;
	
	@FindBy(xpath = "//section[@id='fileTypeBar']//a[@href='/cad-drawings']")
	WebElement linkCADDrawingsFileType;
	
	public HomePage()
	{
		PageFactory.initElements(driver, this);
	}

	/*
	 Action Methods
	 */
	
	public LoginPage clickSignIn()
	{
		click(btnSignIn, "Sign in");
		return new LoginPage();
	}
	
	public LoginPage clickCADLink()
	{
		click(linkCAD, "CAD");
		return new LoginPage();
	}
	
	public CADDrawingsPage clickCADDrawings()
	{
		click(linkCADDrawingsFileType, "CAD Drawings");
		return new CADDrawingsPage();
	}
}
