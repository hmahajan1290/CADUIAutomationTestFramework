package com.cadd.qa.testcases;

import java.io.File;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cadd.qa.base.TestBase;
import com.cadd.qa.pages.CADDrawingsPage;
import com.cadd.qa.pages.HomePage;
import com.cadd.qa.pages.LoginPage;
import com.cadd.qa.pages.UserHomePage;
import com.cadd.qa.util.TestConstants;
import com.cadd.qa.util.TestUtil;

public class CADDrawingsPageTests extends TestBase{
	
	HomePage homePage;
	LoginPage loginPage;
	UserHomePage userHomePage;
	CADDrawingsPage cadDrawingsPage;
	Logger log = Logger.getLogger(CADDrawingsPageTests.class);
	
	public CADDrawingsPageTests()
	{
		super();
	}
	
	@BeforeMethod
	public void setUp()
	{
		initialization();
		log.info("Navigating to url: " + prop.getProperty("url"));
		homePage = new HomePage();
		loginPage = homePage.clickSignIn();
		Assert.assertEquals(loginPage.getLoginPageTitle(), "CADdetails Login");
		log.info("[ASSERT PASSED] - User is on CADdetails Login page");
		userHomePage = loginPage.login(prop.getProperty("email"), prop.getProperty("password"));
		userHomePage.validateLoggedInUserName();
		cadDrawingsPage = userHomePage.clickCADDrawings();
		cadDrawingsPage.validateCADDrawingsPageTitle();
	}

	@Test
	public void verifyUserIsAbleToPreviewAndDownloadContentFromVisualizer()
	{
		log.info("------------- Test execution verifyUserIsAbleToPreviewAndDownloadContentFromVisualizer START -------------");
		cadDrawingsPage.clickDownloadCADFilesForAmico();
		cadDrawingsPage.validateCADDrawingsModalIsOpen();
		cadDrawingsPage.clickCADdetailsTab();
		cadDrawingsPage.validateCADPreviewImage();
		TestUtil.deleteFileIfExists(TestConstants.AMICO_CAD_DRAWINGS_DOWNLOAD_FILE_NAME);
		cadDrawingsPage.clickDownloadAllButtonInVisualizer();
		TestUtil.validateFileIsDownloaded(TestConstants.AMICO_CAD_DRAWINGS_DOWNLOAD_FILE_NAME);
		log.info("File '" + TestConstants.AMICO_CAD_DRAWINGS_DOWNLOAD_FILE_NAME + "' is downloaded at - " + new File("").getAbsolutePath() + "/Downloads/");
		log.info("------------- Test execution verifyUserIsAbleToPreviewAndDownloadContentFromVisualizer END -------------");
	}
	
	@AfterMethod
	public void tearDown()
	{
		driver.quit();
	}
}
