package com.cadd.qa.pages;

import java.time.Duration;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cadd.qa.base.TestBase;
import com.cadd.qa.util.TestConstants;

public class CADDrawingsPage extends TestBase{
	
	Logger log = Logger.getLogger(CADDrawingsPage.class);

	@FindBy(xpath = "//div[text()='AMICO Speed Bead™']//following-sibling::a")
	WebElement linkDownloadAmicoCADFiles;
	
	@FindBy(xpath = "//img[@alt='AMICO Speed Bead™']")
	WebElement imgAmico;
	
	@FindBy(xpath = "//div[@id='visualizerContent']//button[contains(@class, 'download-all')]")
	WebElement btnDownloadAll;
	
	@FindBy(id = "cad-tab")
	WebElement linkCadTab;
	
	@FindBy(xpath = "//img[@id='cad-preview-0']")
	WebElement imgCadPreview;
	
	@FindBy(xpath = "//h2[@id='product-title-large']")
	WebElement productTitle;
	
	@FindBy(xpath = "//div[@id='3d']//div[@class='visualizer-login-notice']//h3")
	WebElement txtVisualizerNotice;
	
	public CADDrawingsPage()
	{
		PageFactory.initElements(driver, this);
	}
	
	public void clickDownloadCADFilesForAmico()
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until((ExpectedCondition<Boolean>) driver -> imgAmico.isDisplayed());
		moveToElement(driver.findElement(By.xpath("//section[@id='cadSearch']//h2")));
		hoverElement(linkDownloadAmicoCADFiles);
		click(linkDownloadAmicoCADFiles, "Download CAD Files");
	}
	
	public void clickDownloadAllButtonInVisualizer()
	{
		click(btnDownloadAll, "Download All");
	}
	
	public void clickCADdetailsTab()
	{
		click(linkCadTab, "CADdetails");
	}
	
	public boolean validateCADPreviewImage()
	{
		return imgCadPreview.isDisplayed();
	}
	
	public void validateCADDrawingsPageTitle()
	{
		String pageTitle = getPageTitle();
		Assert.assertEquals(pageTitle, TestConstants.CADDRAWINGS_PAGE_TITLE);
		log.info("[ASSERT PASSED] - '" + pageTitle + "' does match expected title '" + TestConstants.CADDRAWINGS_PAGE_TITLE + "'");
	}
	
	public void validateCADDrawingsModalIsOpen()
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until((ExpectedCondition<Boolean>) driver -> productTitle.isDisplayed());
		Assert.assertTrue(productTitle.isDisplayed());
		log.info("[ASSERT PASSED] - CADDDrawings modal is open");
	}
	
	public void validateBlockedUserMessage(String expectedErrorMessage)
	{
		Assert.assertEquals(txtVisualizerNotice.getText(), expectedErrorMessage);
		log.info("[ASSERT PASSED] - Blocked user text '" + txtVisualizerNotice.getText() + "' does match expected error text '" + expectedErrorMessage + "'");
	}
}
