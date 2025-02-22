package com.cadd.qa.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.cadd.qa.base.TestBase;

public class LoginPage extends TestBase{
	
	Logger log = Logger.getLogger(LoginPage.class);
	
	//Page Factory - OR
	
	@FindBy(xpath = "//span[@class='modalBannerText']")
	WebElement txtLoginPageBanner;
	
	@FindBy(id = "PostModel_Email")
	WebElement inputEmail;
	
	@FindBy(id = "PostModel_Password")
	WebElement inputPassword;
	
	@FindBy(id = "loginButton")
	WebElement btnLogin;
	
	@FindBy(linkText = "forgotPasswordButton")
	WebElement linkForgotPassword;
	
	@FindBy(xpath = "//div[@class='validation-summary-errors']//li")
	WebElement validationErrorText;
	
	public LoginPage()
	{
		PageFactory.initElements(driver, this);
	}
	
	/*
	 Action Methods
	 */
	
	public void enterCredentials(String emailId, String pwd)
	{
		sendKeys(inputEmail, emailId);
		sendKeys(inputPassword, pwd);
	}
	
	public void clickLoginButton()
	{
		click(btnLogin, "Login Button");
	}
	
	public UserHomePage login(String emailId, String pwd)
	{
		enterCredentials(emailId, pwd);
		clickLoginButton();
		
		return new UserHomePage();
	}
	
	public String getLoginPageTitle()
	{
		return txtLoginPageBanner.getText();
	}
	
	public List<String> getValidationErrorTexts()
	{
		List<String> errors = new ArrayList<String>();
		try
		{
			List<WebElement> validationErrorTexts = driver.findElements(By.xpath("//div[@class='validation-summary-errors']//li"));
			
			for (WebElement validationError : validationErrorTexts)
			{
				errors.add(validationError.getText());
			}
		}
		catch (Exception e)
		{
			testFailed = true;
			log.error("ERROR in getValidationErrorTexts method", e);
			e.printStackTrace();
		}

		return errors;
	}
	
	public void validateValidationErrorText(String expectedErrorMessage)
	{
		List<String> errors = getValidationErrorTexts();
		
		try
		{
			if(errors != null)
			{
				Assert.assertTrue(errors.contains(expectedErrorMessage));
				log.info("[ASSERT PASSED] - expected error message '" + expectedErrorMessage + "' is displyed on the screen");
			}
		}
		catch (Exception e)
		{
			testFailed = true;
			log.error("ERROR in validateValidationErrorText method", e);
			e.printStackTrace();
		}
	}
	
	public void validateLoginPageTitle()
	{
		try
		{
			Assert.assertEquals(getLoginPageTitle(), "CADdetails Login");
			log.info("[ASSERT PASSED] - User is on CADdetails Login page");
		}
		catch (Exception e)
		{
			testFailed = true;
			log.error("ERROR in validateLoginPageTitle method", e);
			e.printStackTrace();
		}
	}
}
