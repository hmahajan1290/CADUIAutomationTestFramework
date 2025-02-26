package com.cadd.qa.testcases;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cadd.qa.base.TestBase;
import com.cadd.qa.pages.CADDrawingsPage;
import com.cadd.qa.pages.HomePage;
import com.cadd.qa.pages.LoginPage;
import com.cadd.qa.util.TestConstants;
import com.cadd.qa.util.TestUtil;

public class HomePageTests extends TestBase{
	
	HomePage homePage;
	CADDrawingsPage cadDrawingsPage;
	Logger log = Logger.getLogger(HomePageTests.class);
	
	public HomePageTests()
	{
		super();
	}
	
	@BeforeMethod
	public void setUp()
	{
		initialization();
		log.info("Navigating to url: " + prop.getProperty("url"));
		homePage = new HomePage();
	}
	
	@Test
	public void verifyClickingOnSignInButtonOpensLoginPage()
	{
		log.info("------------- Test execution verifyClickingOnSignInButtonOpensLoginPage START -------------");
		LoginPage loginPage = homePage.clickSignIn();
		loginPage.validateLoginPageTitle();
		log.info("------------- Test execution verifyClickingOnSignInButtonOpensLoginPage END -------------");
	}
	
	@Test
	public void verifyUserIsAuthBlockedWhenUserClicksCADLinkOnHomePage()
	{
		log.info("------------- Test execution verifyUserIsAuthBlockedWhenUserClicksCADLinkOnHomePage START -------------");
		LoginPage loginPage = homePage.clickCADLink();
		loginPage.validateLoginPageTitle();
		log.info("------------- Test execution verifyUserIsAuthBlockedWhenUserClicksCADLinkOnHomePage END -------------");
	}
	
	@Test
	public void verifyUnloggedUserCannotPreviewAndDownloadContentsFromVisualizer()
	{
		log.info("------------- Test execution verifyUnloggedUserCannotPreviewAndDownloadContentsFromVisualizer START -------------");
		cadDrawingsPage = homePage.clickCADDrawings();
		cadDrawingsPage.validateCADDrawingsPageTitle();
		cadDrawingsPage.clickDownloadCADFilesForAmico();
		cadDrawingsPage.validateCADDrawingsModalIsOpen();
		cadDrawingsPage.validateBlockedUserMessage(TestConstants.BLOCKED_USER_TEXT);
		cadDrawingsPage.clickDownloadAllButtonInVisualizer();
		try {
			Assert.assertEquals(cadDrawingsPage.getPageTitle(), TestConstants.LOGIN_PAGE_TITLE);
			log.info("[ASSERT PASSED] - User is on CADdetails Login page");
		} catch (Exception e) {
			testFailed = true;
			log.error("ERROR in verifyUnloggedUserCannotPreviewAndDownloadContentsFromVisualizer method", e);
			e.printStackTrace();
		}
		catch (AssertionError e)
		{
			testFailed = true;
			log.error("ERROR in verifyUnloggedUserCannotPreviewAndDownloadContentsFromVisualizer method", e);
			e.printStackTrace();
		}
		log.info("------------- Test execution verifyUnloggedUserCannotPreviewAndDownloadContentsFromVisualizer END -------------");
	}
	
	@AfterMethod
	public void tearDown()
	{
		if(testFailed)
		{
			try 
			{
				TestUtil.takeScreenshotAtEndOfTest();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		driver.quit();
	}
}
