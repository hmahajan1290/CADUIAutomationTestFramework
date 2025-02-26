UI AUTOMATION FRAMEWORK STRATEGY:

Tech Stack: Java-Maven + Selenium + TestNG
Design Pattern: Page Object Model (POM)

I used POM design pattern in my framework since its widely used everywhere with Selenium. 
It makes code look clean and maintainable.

Folder Structure: 
src/main/java:
com.cadd.qa.base Folder: This package has TestBase class which reads properties from config.properties file, initializes browser and navigates to 
the url. It also has methods which interacts with browser such as sendKeys, click and are commonly used in the framework.

com.cadd.qa.config folder: This package has config.properties file which has basic configurations such as url to test, credentials, browser name, etc.

com.cadd.qa.pages folder: This package has Page classes for various pages in the application. Each class has WebElements declared on the page, initialize those web elements and methods interacting with those elements. I have created 4 page classes - Home Page, Login page, User home page (the one after login) and CADDrawings page (the one which opens up on clicking CAD Drawings link on Home page).

com.cadd.qa.util folder: This package has TestConstants class containing constant variables used frequently in the framework. This package also has TestUtil class which has few utility methods (which does not involve any interactions with web elements) such as take screenshot method.

com.qa.ExtentReportListener: This package has a listener class which generates an extent html report upon completion of testng suite. The report is generated in test-output folder with name 'CADUIAutomationSuiteExtentReport.html'. I have generated one sample report for reference.

src/test/java
com.cadd.qa.testcases: This package has all test classes for each page which interacts with relevant page classes to run test. I have created 4 test classes same as those of page classes.

src/main/resource: This package has log4j property file containing config for logging and also has testng.xml file which has configuration related to which tests to be run from the suite.

.github/workflows folder: This has cicd.yml file which has steps to trigger CICD run every time PR is created pointing to master branch or whenever anything is merged in master branch.
Because of remote driver issue I couldn't run my tests on GitHub env but the trigger is doing its job correctly.

BrowserDrivers folder: this folder has chromedriver.exe file with same version as chrome browser.

Downloads: This folder will have cad files downloaded during the test execution.
 
Apart from this 'application.log' file is generated in the root directory of the project with all the detailed logs from the test.

------------------------------------------------------------------------------------------------------------------------------------

INSTRUCTIONS ON RUNNING THE TESTS ON LOCAL

1. Unzip the framework file on your local
2. Open the project in Eclipse. Open pom.xml and save it. This should download all dependencies mentioned in the file.
3. Download chromedriver.exe file from internet as per the version of your chrome browser on your machine. 
   Use this site to download relevant version: https://developer.chrome.com/docs/chromedriver/downloads
4. Right click testng.xml file -> Run As -> TestNG Suite. This will run all the tests from the framework.
5. Once all tests are done, do check application.log file and 'CADUIAutomationSuiteExtentReport.html' report in test-output folder.