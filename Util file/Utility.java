package com.serviceNow.util;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.DateUtils;

import org.apache.commons.io.FileUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Utility {

	String requestId = "";
	String screenShotPath = System.getProperty("user.dir") + "\\Screenshots\\";
	public String screenShotFileName;
	public DateFormat dateFormat;
	public Date date;
	String emailmsg;

	public Utility() {

		screenShotPath = System.getProperty("user.dir") + "\\Screenshots\\";

		dateFormat = new SimpleDateFormat("ddMMyyyy-HH_mm_ss");
//
		date = new Date();
	}

	/**
	 * This method for wait or pause upto specified time
	 * 
	 * @param waitTime
	 * @param logger
	 */
	public void pause(ExtentTest logger, String waitTime) {
		try {
			long time = (long) Double.parseDouble(waitTime);
			Thread.sleep(time * 1000L);
			logger.log(LogStatus.PASS, "Wait <b>" + waitTime + "</b> Seconds");
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL, "Failed to wait <b>" + waitTime + " Seconds</b> due to <b style='color:red'>"
					+ e.getClass() + "<br>" + e.getMessage() + "</b>");
		}
	}

//method changes format of date according to India exit letters PDF
	public String changeDateFormat(ExtentTest logger, String date, String format) throws Exception {
		String convertedDate = "";
		try {

			DateFormat inputDate = new SimpleDateFormat("yyyy-MM-dd");
			DateFormat outputDate = new SimpleDateFormat(format);
			Date changedate = inputDate.parse(date);
			convertedDate = outputDate.format(changedate);
			// System.out.println("Final date:"+convertedDate);

			return convertedDate;
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL,
					"Failed to convert format of date" + e.getClass() + "<br>" + e.getMessage() + "</b>");
			return convertedDate;
		}
	}

	public File getLatestFilefromDir(String dirPath) {
		File dir = new File(dirPath);
		File[] files = dir.listFiles();
		if (files == null || files.length == 0) {
			return null;
		}

		File lastModifiedFile = files[0];
		for (int i = 1; i < files.length; i++) {
			if (lastModifiedFile.lastModified() < files[i].lastModified()) {
				lastModifiedFile = files[i];

			}
		}
		File f = new File(lastModifiedFile + "\\");
		// System.out.println("Inside getLatestFilefromDir \n"+f);
		return lastModifiedFile;
	}

	/**
	 * The method waits till element is Clickable
	 * 
	 * @param logger
	 * @param driver
	 * @param object
	 */
	public void waitTillElementIsClickable(ExtentTest logger, WebDriver driver, WebElement object) {
		try {

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

			logger.log(LogStatus.INFO, "Wait upto <b>60 Seconds </b>");
			wait.until(ExpectedConditions.elementToBeClickable(object));
			logger.log(LogStatus.PASS, "Wait till <b>" + object + " </b>[Object] clikable is Success");

		} catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL, "Failed: Wait till element is clickable <b>" + object
					+ "</b> due to <b style='color:red'>" + e.getClass() + "<br>" + e.getMessage() + "</b>");
		}
	}

	// Wait Till Element becomes visible on refreshing page
	public void waitTillElementIsVisible(ExtentTest logger, WebDriver driver, WebElement object) {
		try {
			// WebDriverWait wait=new WebDriverWait(driver,60);
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
			logger.log(LogStatus.INFO, "Wait upto <b>60 Seconds </b>");
			wait.until(ExpectedConditions.visibilityOf(object));
			logger.log(LogStatus.PASS, "Wait till <b>" + object + " </b>[Object] Visible is Success");
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL, "Failed: Wait till element is visible <b>" + object
					+ "</b> due to <b style='color:red'>" + e.getClass() + "<br>" + e.getMessage() + "</b>");
		}

	}

	/**
	 * This method will verify the whether the element is displayed or not
	 * 
	 * @param logger
	 * @param object
	 * @return
	 */
	public boolean verifyElementByXpath(ExtentTest logger, WebElement object) {
		try {
			if (object.isDisplayed()) {
				// System.out.println("The Object is present ");
				logger.log(LogStatus.PASS, "WebElement is displayed");
				return true;

			}
		} catch (Exception e) {
			e.printStackTrace();

			logger.log(LogStatus.FAIL,
					"Failed: Element  <b>" + object + "</b> not displayed due to <b style='color:red'>" + e.getClass()
							+ "<br>" + e.getMessage() + "</b>");
			return false;
		}
		return false;

	}

	/**
	 * This method will verify the whether the element is displayed or not
	 * 
	 * @param logger
	 * @param object
	 * @return
	 */
	public boolean verifyElementNotDisplayed(ExtentTest logger, WebElement object) {
		try {
			if (!(object.isDisplayed())) {
				// System.out.println("The Object is present ");
				logger.log(LogStatus.PASS, "WebElement is not displayed");
				return true;

			}
		} catch (Exception e) {
			e.printStackTrace();

			logger.log(LogStatus.FAIL, "Failed: Element  <b>" + object + "</b> displayed due to <b style='color:red'>"
					+ e.getClass() + "<br>" + e.getMessage() + "</b>");
			return false;
		}
		return false;

	}

	/**
	 * This method will take the screen shot
	 * 
	 * @param logger
	 * @param driver
	 * @param fileName
	 */
	public void captureScreenShot(ExtentTest logger, WebDriver driver, String fileName) {
		try {
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			logger.log(LogStatus.PASS, "Screen shot taken");

			// String path = System.getProperty("user.dir") + "\\Screenshots\\" + fileName +
			// ".jpg";
			FileUtils.copyFile(scrFile, new File(fileName));

			logger.log(LogStatus.INFO, "Screen shot Path : <b>" + fileName + "</b>");

		} catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL, "Failed To take Screen shot due to <b style='color:red'>" + e.getClass() + "<br>"
					+ e.getMessage() + "</b>");
		}
	}

	// Method will verify whether alert is present or not
	public boolean isAlertPresent(ExtentTest logger, WebDriver driver) {
		boolean foundAlert = false;
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
			// WebDriverWait wait = new WebDriverWait(driver, 5);

			wait.until(ExpectedConditions.alertIsPresent());
			foundAlert = true;
			logger.log(LogStatus.INFO, "Alert is Present");

		} catch (Exception e) {

			logger.log(LogStatus.INFO, "Alert is not Present");
		}
		return foundAlert;
	}

	/**
	 * This method will return specified element value
	 * 
	 * @param logger
	 * @param element
	 * @return
	 */
	public String getElementValue(ExtentTest logger, WebElement element) {
		try {
			requestId = element.getAttribute("value");
			logger.log(LogStatus.PASS, "Element value taken :" + requestId + " <b>" + requestId + "</b");
			System.out.println(requestId);

		} catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL, "Failed to take Element <b>" + element + "</b>Value due to <b style='color:red'>"
					+ e.getClass() + "<br>" + e.getMessage() + "</b>");
		}

		return requestId;
	}

	/**
	 * This method will return specified drop down[Select Box] Value
	 * 
	 * @param logger
	 * @param element
	 * @return
	 */
	public String getSelectedValueFromDropdown(ExtentTest logger, WebElement element) {
		try {

			Select dropList = new Select(element);
			// System.out.println(dropList.getFirstSelectedOption());
			requestId = dropList.getFirstSelectedOption().getText();
			logger.log(LogStatus.INFO, "Dropdown Value: <b>" + requestId + "</b");
			logger.log(LogStatus.PASS, "Getting dropdown values is Success");

		} catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL, "Failed to get Dropdown <b>" + element + "</b>value due to <b style='color:red'>"
					+ e.getClass() + "<br>" + e.getMessage() + "</b>");
		}
		return requestId;
	}

	/**
	 * This method for Mouse hover on Specified element
	 * 
	 * @param logger
	 * @param driver
	 * @param element
	 */
	public void mouseHover(ExtentTest logger, WebDriver driver, WebElement element) {
		try {
			Actions builder = new Actions(driver);
			// Move cursor to the Main Menu Element
			builder.moveToElement(element).perform();
			logger.log(LogStatus.PASS, "Mouse Hover to " + element + " is success");

		} catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL, "Failed:  Mouse hover on <b>" + element
					+ "</b> element due to <b style='color:red'>" + e.getClass() + "<br>" + e.getMessage() + "</b>");
		}
	}

	/**
	 * Verifying Drop down[Select box] options
	 * 
	 * @param object
	 * @param optionsList
	 * @return
	 */
	public void verifyDropdownValues(ExtentTest logger, WebElement object, String optionsList) {
		try {
			List<WebElement> droplistContents = object.findElements(By.tagName("option"));

			String valuesList[] = optionsList.split("\\|");
			logger.log(LogStatus.INFO,
					"Application list size" + droplistContents.size() + "Expected List Size" + valuesList.length);

			if (valuesList.length != droplistContents.size()) {

				logger.log(LogStatus.FAIL, "Dropdown list size and Options size are different");

			}
			for (int i = 0; i < droplistContents.size(); i++) {
				if (!droplistContents.get(i).getText().contains(valuesList[i])) {
					logger.log(LogStatus.FAIL, "Failed to verify dropdown list values due to <b>" + valuesList[i]
							+ " option not avaliable in Dropdown list</b>");

				} else {
					logger.log(LogStatus.INFO, "Option" + valuesList[i] + "is available in dropdown");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL, "Failed:  to verify dropdown list values due to <b style='color:red'>"
					+ e.getClass() + "<br>" + e.getMessage() + "</b>");

		}

	}

	// Verify List values
	public void verifyListValues(WebDriver driver, ExtentTest logger, String object, String optionsList) {
		try {

			List<WebElement> droplistContents = driver.findElements(By.xpath(object));

			String valuesList[] = optionsList.split("\\|");
			logger.log(LogStatus.INFO,
					"Application list size" + droplistContents.size() + "Expected List Size" + valuesList.length);
			System.out.println();

			for (int i = 0; i < valuesList.length; i++) {
				int flag = 0;
				for (int j = 0; j < droplistContents.size(); j++) {

					if (droplistContents.get(j).getText().contains(valuesList[i])) {
						logger.log(LogStatus.INFO,
								"Expected value is present in application list" + droplistContents.get(j).getText());
						System.out.println(
								"Expected value is present in application list" + droplistContents.get(j).getText());
						break;
					} else {
						flag = flag + 1;
					}
					if (flag == droplistContents.size()) {
						logger.log(LogStatus.FAIL, "Expected value is not present in application list"
								+ droplistContents.get(j).getText());
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL, "Failed:  to verify dropdown list values due to <b style='color:red'>"
					+ e.getClass() + "<br>" + e.getMessage() + "</b>");

		}

	}

	/**
	 * This method for selecting a option in Drop down by Visible Text
	 * 
	 * @param logger
	 * @param we
	 * @param value
	 */
	public void selectBoxByValue(ExtentTest logger, WebElement element, String value) {
		try {

			Thread.sleep(3000);
			;
			Select dropList = new Select(element);

			dropList.selectByVisibleText(value);

			logger.log(LogStatus.PASS, "<b>" + value + "</b> value is selected from Dropdown");
			System.out.println(value + " is getting selected from dropdown");
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL, "Failed to select value from drop down due to <b style='color:red'>"
					+ e.getClass() + "<br>" + e.getMessage() + "</b>");
		}
	}

	/**
	 * This method will verify whether the webelement is disabled or not
	 * 
	 * @param webelement
	 * @return
	 */

	public void isDisabled(ExtentTest logger, WebElement webelement) {
		try {
			if (!webelement.isEnabled())
				logger.log(LogStatus.PASS, "Element <b>" + webelement + "</b> is disabled");
			else
				logger.log(LogStatus.FAIL, "Element <b>" + webelement + "</b> is enabled");

		} catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL,
					"Failed to check whether the element is Disabled or not due to <b style='color:red'>" + e.getClass()
							+ "<br>" + e.getMessage() + "</b>");
		}

	}

	// Method will verify element is enabled
	public void isEnabled(ExtentTest logger, WebElement webelement) {
		try {
			if (webelement.isEnabled())
				logger.log(LogStatus.PASS, "Element <b>" + webelement + "</b> is enabled");
			else
				logger.log(LogStatus.FAIL, "Element <b>" + webelement + "</b> is disbaled");

		} catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL,
					"Failed to check whether the element is Disabled or not due to <b style='color:red'>" + e.getClass()
							+ "<br>" + e.getMessage() + "</b>");
		}

	}

	// Method verifies element is disabled
	public void verifyElementDisabled(ExtentTest logger, WebElement we) {

		try {
			String b = we.getAttribute("readonly");

			if (b.equals("true")) {
				logger.log(LogStatus.PASS, "Element <b>" + we + "</b> is disabled");

			}

			else {
				logger.log(LogStatus.FAIL, "Element <b>" + we + "</b> is enabled");

			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL,
					"Failed to check whether the element is Disabled or not due to <b style='color:red'>" + e.getClass()
							+ "<br>" + e.getMessage() + "</b>");
		}

	}

	// Method verifies element is disabled
	public void verifyElementReadonly(ExtentTest logger, WebElement we) {

		try {
			String b = we.getAttribute("readonly");
			System.out.println("attribute value" + b);

			if (b.equals("readonly")) {
				logger.log(LogStatus.PASS, "Element <b>" + we + "</b> is disabled");

			}

			else {
				logger.log(LogStatus.FAIL, "Element <b>" + we + "</b> is enabled");

			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL,
					"Failed to check whether the element is Disabled or not due to <b style='color:red'>" + e.getClass()
							+ "<br>" + e.getMessage() + "</b>");
		}

	}

	// Method verifies element is disabled based on some attribute
	public void verifyElementReadonlyBasedonAttribute(ExtentTest logger, WebElement we, String attribute) {

		try {
			String b = we.getAttribute(attribute);

			if (b.equals("readonly")) {
				logger.log(LogStatus.PASS, "Element <b>" + we + "</b> is disabled");

			}

			else {
				logger.log(LogStatus.FAIL, "Element <b>" + we + "</b> is enabled");

			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL,
					"Failed to check whether the element is Disabled or not due to <b style='color:red'>" + e.getClass()
							+ "<br>" + e.getMessage() + "</b>");
		}

	}

	/**
	 * This method will compare two strings and return true only if both strings are
	 * same
	 * 
	 * @param webelement
	 * @return
	 */

	public void compareTwoStrings(ExtentTest logger, String actual, String expected) {
		try {
			if (actual.equalsIgnoreCase(expected)) {
				logger.log(LogStatus.PASS, "Comparing " + actual + "and " + expected + " success");

			} else {
				logger.log(LogStatus.FAIL, "Comparing " + actual + "and " + expected + " failure");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL, "Failed to compare strings due to <b style='color:red'>" + e.getClass() + "<br>"
					+ e.getMessage() + "</b>");
		}

	}

	/**
	 * This method is useful for clicking Java Script enabled elements
	 * 
	 * @param driver
	 * @param element
	 * @return status[ boolean type]
	 */
	public void clickByJavascriptExecutor(ExtentTest logger, WebDriver driver, WebElement element) {
		try {

			JavascriptExecutor executr = (JavascriptExecutor) driver;
			executr.executeScript("arguments[0].click();", element);
			logger.log(LogStatus.PASS, "Clicking WebElement by using JavaScriptExecutor success");

		} catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL, "Failed to click on web element due to <b style='color:red'>" + e.getClass()
					+ "<br>" + e.getMessage() + "</b>");
		}

	}

	/**
	 * This method will take print error Message and Screen Shot and also screenShot
	 * will attached to report
	 * 
	 * @param logger
	 * @param exception
	 * @param driver
	 * @param fileName
	 */

//this method is to select option by giving wild card value instead of complete text
	public void selectoptionfromlist(ExtentTest logger, WebElement element, String value) {
		try {
			Select sel = new Select(element);
			List<WebElement> list = sel.getOptions();
			for (WebElement option : list) {

				if (option.getText().contains(value)) {
					sel.selectByVisibleText(option.getAttribute("value"));
					break;

				}
			}
		}

		catch (Exception e) {

			e.printStackTrace();
			logger.log(LogStatus.FAIL, "Failed to select value from drop down due to <b style='color:red'>"
					+ e.getClass() + "<br>" + e.getMessage() + "</b>");
		}

	}

	public void screenShotAndErrorMsg(ExtentTest logger, Exception exception, WebDriver driver, String fileName) {

		exception.printStackTrace();
		screenShotFileName = screenShotPath + fileName + "_" + dateFormat.format(date) + ".jpg";
		captureScreenShot(logger, driver, screenShotFileName);

		logger.log(LogStatus.FAIL, "HTML", "Failed due to <b style='color:red'> " + exception.getClass() + "<br>"
				+ exception.getMessage() + " </b>");
		logger.log(LogStatus.INFO, "For reference Snapshot below: " + logger.addScreenCapture(screenShotFileName));
	}

	// Pooja Methods

	/**
	 * This method will verify Text based on value attribute
	 * 
	 * @param logger
	 * @param webElement
	 * @param expected   string
	 * 
	 */
	public void verifyExactTextByValue(ExtentTest logger, WebElement we, String expected) {

		try {
			String actual = we.getAttribute("value");

			if (actual.trim().equalsIgnoreCase(expected.trim())) {

				logger.log(LogStatus.PASS, "Strings Matched" + expected + "--" + actual);

			} else {
				System.out.println("strings are not matching" + expected + "--" + actual);
				logger.log(LogStatus.FAIL, "Strings not Matched");

			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL, "Failed to match string due to <b style='color:red'>" + e.getClass() + "<br>"
					+ e.getMessage() + "</b>");

		}
	}

	// Method will verify element's empty value
	public void verifyNullTextByValue(ExtentTest logger, WebElement we) {

		try {
			String actual = we.getAttribute("value");

			if (actual.isEmpty()) {
				System.out.println("strings are matching");
				logger.log(LogStatus.PASS, "Strings Matched");

			} else {
				System.out.println("strings are not matching");
				logger.log(LogStatus.FAIL, "Strings not Matched");

			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL, "Failed to match string due to <b style='color:red'>" + e.getClass() + "<br>"
					+ e.getMessage() + "</b>");

		}
	}

	/**
	 * This method will click on a particular webelement
	 * 
	 * @param logger
	 * @param webElement
	 * 
	 */
	public void clickOn(ExtentTest logger, WebElement we) {

		try {
			we.click();
			logger.log(LogStatus.PASS, "Element Clicked");
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL, "Failed to click on element due to <b style='color:red'>" + e.getClass() + "<br>"
					+ e.getMessage() + "</b>");

		}
	}

	/**
	 * This method will send keys to a particular webElement
	 * 
	 * @param logger
	 * @param webElement
	 * @param string     needs to be enter
	 */

	public void setText(ExtentTest logger, WebElement we, String data) {
		try {
			we.sendKeys(data);
			logger.log(LogStatus.PASS, "Keys sent");
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL, "Failed to send keys due to <b style='color:red'>" + e.getClass() + "<br>"
					+ e.getMessage() + "</b>");
		}
	}

	// Method provides enter key
	public void provideEnterKey(ExtentTest logger, WebElement we) {
		try {
			we.sendKeys(Keys.ENTER);
			logger.log(LogStatus.PASS, "Keys sent");
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL, "Failed to send keys due to <b style='color:red'>" + e.getClass() + "<br>"
					+ e.getMessage() + "</b>");
		}
	}
	// Method provides delete key

	public void provideDeleteKey(ExtentTest logger, WebElement we, String data) {
		try {
			logger.log(LogStatus.INFO, data);
			we.sendKeys(Keys.DELETE);
			logger.log(LogStatus.PASS, "Keys sent and enter pressed");
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL, "Failed to send keys and enter due to <b style='color:red'>" + e.getClass()
					+ "<br>" + e.getMessage() + "</b>");
		}
	}

	// Method provides control key
	public void provideCtrlAKey(ExtentTest logger, WebElement we, String data) {
		try {
			logger.log(LogStatus.INFO, data);
			we.sendKeys(Keys.chord(Keys.CONTROL, "a"));
			logger.log(LogStatus.PASS, "Keys sent and enter pressed");
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL, "Failed to send keys and enter due to <b style='color:red'>" + e.getClass()
					+ "<br>" + e.getMessage() + "</b>");
		}
	}

	/**
	 * This method will send keys to a particular WebElement and press Enter
	 * 
	 * @param logger
	 * @param webElement
	 * @param string     needs to be enter
	 */

	public void setTextWithEnter(ExtentTest logger, WebElement we, String data) {
		try {
			we.sendKeys(data + Keys.ENTER);
			logger.log(LogStatus.PASS, "Keys sent and enter pressed");
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL, "Failed to send keys and enter due to <b style='color:red'>" + e.getClass()
					+ "<br>" + e.getMessage() + "</b>");
		}
	}

	// Method press right arrow key
	public void pressRightKey(ExtentTest logger, WebElement we) {
		try {
			we.sendKeys(Keys.ARROW_RIGHT);
			logger.log(LogStatus.PASS, "Right Arrow Key is pressed");
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL, "Failed to send keys due to <b style='color:red'>" + e.getClass() + "<br>"
					+ e.getMessage() + "</b>");
		}
	}

	// Method press left arrow key
	public void pressLeftKey(ExtentTest logger, WebElement we) {
		try {
			we.sendKeys(Keys.ARROW_LEFT);
			logger.log(LogStatus.PASS, "Left Arrow key ispressed");
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL,
					"Failed to send keys <b style='color:red'>" + e.getClass() + "<br>" + e.getMessage() + "</b>");
		}
	}

	/**
	 * This method will select multiple options from dropdown by pressing ctrl key
	 * 
	 * @param logger
	 * @param driver
	 * @param webElement
	 * @param option     value needs to be selected
	 */

	public void selectMultipleValuesFromDropdown(ExtentTest logger, WebDriver driver, WebElement we, String data) {
		try {
			String multipleSel[] = data.split(",");
			// System.out.println(multipleSel.length);

			Select dropdown = new Select(we);
			List<WebElement> options = dropdown.getOptions();
			Actions builder = new Actions(driver);
			boolean isMultiple = dropdown.isMultiple();
			if (isMultiple) {
				dropdown.deselectAll();
			}
			builder.keyDown(Keys.CONTROL);
			for (WebElement webElement : options) {
				// System.out.println(webElement.getText());
			}
			// System.out.println("key pressed"+options.size());

			for (String textOption : multipleSel) {
				for (WebElement option : options) {
					String optionText = option.getText().trim();
					if (optionText.equalsIgnoreCase(textOption)) {

						if (isMultiple) {

							if (!option.isSelected()) {

								builder.click(option);
							}
						} else {

							option.click();
						}
						break;
					}
				}
			}
			builder.keyUp(Keys.CONTROL).build().perform();
			logger.log(LogStatus.PASS, "Multiple values are selected from dropdown by pressing control key");

		}

		catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL, "Failed to select multiple values from dropdown due to <b style='color:red'>"
					+ e.getClass() + "<br>" + e.getMessage() + "</b>");
		}

	}

	/**
	 * This method will deselect all options of a dropdown
	 * 
	 * @param logger
	 * @param webElement
	 */
	public void deselectDropdown(ExtentTest logger, WebElement we) {
		try {
			Select dropdown = new Select(we);
			List<WebElement> options = dropdown.getOptions();
			boolean isMultiple = dropdown.isMultiple();
			if (isMultiple) {
				dropdown.deselectAll();
			}

			logger.log(LogStatus.PASS, "Dropdown is deselected completely");

		}

		catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL, "Dropdown is not deselected due to <b style='color:red'>" + e.getClass() + "<br>"
					+ e.getMessage() + "</b>");
		}

	}

	/**
	 * This method will select option from a Model box one by one
	 * 
	 * @param logger
	 * @param webElement
	 * @param Left       or Right arrow button
	 * @param option     needs to be selected
	 */

	public void selectOptionsFromBox(ExtentTest logger, WebElement drpdwn, WebElement button, String data) {
		try {
			String value[] = data.split(",");
			Select dropList = new Select(drpdwn);
			for (int i = 0; i < value.length; i++) {
				dropList.selectByVisibleText(value[i]);
				button.click();

			}
			logger.log(LogStatus.PASS, "Values are selected from dropdown one by one");

		} catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL, "Failed to select values from dropdown one by one due to <b style='color:red'>"
					+ e.getClass() + "<br>" + e.getMessage() + "</b>");
		}

	}

	/**
	 * This method will verify first selected option from a dropdown
	 * 
	 * @param logger
	 * @param webElement
	 * @param Expected   Value needs to be verified
	 * 
	 */

	public void verifySelectedValueInDropdown(ExtentTest logger, WebElement element, String expectedValue) {
		try {

			Select dropList = new Select(element);
			// System.out.println(dropList.getFirstSelectedOption());
			String selectedValue = dropList.getFirstSelectedOption().getText().trim();
			if (selectedValue.trim().equalsIgnoreCase(expectedValue)) {

				logger.log(LogStatus.PASS, "Expected and Application Selected Values is matched");
				logger.log(LogStatus.PASS, "Expected Value: " + expectedValue);
				logger.log(LogStatus.PASS, "Application Value: " + selectedValue);
			} else {
				logger.log(LogStatus.FAIL, "Expected and Application Selected Values are not matched");
				logger.log(LogStatus.FAIL, "Expected Value: " + expectedValue);
				logger.log(LogStatus.FAIL, "Application Value: " + selectedValue);
			}

			logger.log(LogStatus.PASS, "Fecthing selected value from dropdown is success");

		} catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL, "Failed to retrieve value from dropdown <b>" + element
					+ "</b>value due to <b style='color:red'>" + e.getClass() + "<br>" + e.getMessage() + "</b>");
		}

	}

	/**
	 * This method will clear a particular webelement
	 * 
	 * @param logger
	 * @param webElement
	 * 
	 */
	public void clearText(ExtentTest logger, WebElement we) {

		try {
			we.clear();
			logger.log(LogStatus.PASS, "Element Text cleared");
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL, "Failed to clear element text due to <b style='color:red'>" + e.getClass()
					+ "<br>" + e.getMessage() + "</b>");

		}
	}

	// Method select first option from dropdown
	public void selectfirstoptionfromlist(ExtentTest logger, WebElement element, int i) {
		try {
			Select sel = new Select(element);
			List<WebElement> list = sel.getOptions();
			for (WebElement option : list) {

				sel.selectByIndex(i);
			}
		}

		catch (Exception e) {

			e.printStackTrace();
			logger.log(LogStatus.FAIL, "Failed to select value from drop down due to <b style='color:red'>"
					+ e.getClass() + "<br>" + e.getMessage() + "</b>");
		}

	}

	/**
	 * This method will verify application value same as expected value
	 * 
	 * @param logger
	 * @param webElement
	 * @param expected   string
	 * 
	 */
	public void verifyExactText(ExtentTest logger, WebElement we, String expected) {

		try {
			String actual = we.getText();

			if (actual.trim().equalsIgnoreCase(expected.trim())) {
				System.out.println("strings are matching" + expected + "--" + actual);
				logger.log(LogStatus.PASS, "Actual Contains Expected");
				logger.log(LogStatus.PASS, "strings are matching" + expected + "--" + actual);
			} else {
				System.out.println("strings are not matching" + expected + "--" + actual);
				logger.log(LogStatus.FAIL, "Actual does not contain expected");
				logger.log(LogStatus.FAIL, "strings are not matching" + expected + "--" + actual);

			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL, "Failed to match string due to <b style='color:red'>" + e.getClass() + "<br>"
					+ e.getMessage() + "</b>");

		}
	}

	/**
	 * This method will accept/dismiss alert
	 * 
	 * @param logger
	 * @param webElement
	 * @param expected   string
	 * 
	 */
	public void manageAlert(ExtentTest logger, WebDriver driver, String operation) {
		try {
			// WebDriverWait wait = new WebDriverWait(driver, 30);
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.alertIsPresent());
			if (operation.equalsIgnoreCase("accept")) {
				Thread.sleep(3000);
				driver.switchTo().alert().accept();
				logger.log(LogStatus.PASS, "alert accepted successfully");
				System.out.println("alert accepted successfully");
			} else if (operation.equalsIgnoreCase("dismiss")) {
				Thread.sleep(3000);
				driver.switchTo().alert().dismiss();
				logger.log(LogStatus.PASS, "alert dismissed successfully");
				System.out.println("alert dismissed successfully");
			} else {
				logger.log(LogStatus.FAIL, "invalid string input");
				System.out.println("invalid string input");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL, "Failed to get <b>Alert<b> due to <b style='color:red'>" + e.getClass() + "<br>"
					+ e.getMessage() + "</b>");
		}
	}

	/**
	 * This method will verify alert's application value same as expected value
	 * 
	 * @param logger
	 * @param webElement
	 * @param expected   string
	 * 
	 */

	public String verifyAlertText(ExtentTest logger, WebDriver driver, String expectedMessage) {
		String actualMessage = "";
		try {
			// WebDriverWait wait = new WebDriverWait(driver, 30);
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.alertIsPresent());

			actualMessage = driver.switchTo().alert().getText();

			if (actualMessage.trim().contains(expectedMessage.trim())) {
				logger.log(LogStatus.PASS,
						"Actual Message is verified: " + actualMessage + "Expected Message:" + expectedMessage);

			} else {

				logger.log(LogStatus.FAIL,
						"Actual Message is not verified: " + actualMessage + "Expected Message: " + expectedMessage);
				System.out.println("alert accepted successfully but message is not matched");
			}
			return actualMessage;

		} catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL, "Failed to get <b>Alert Message<b> due to <b style='color:red'>" + e.getClass()
					+ "<br>" + e.getMessage() + "</b>");
			return actualMessage;
		}
	}

	public String getAlertText(ExtentTest logger, WebDriver driver) {
		String actualMessage = "";
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			// WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.alertIsPresent());

			actualMessage = driver.switchTo().alert().getText();

			logger.log(LogStatus.PASS, "Actual Message is " + actualMessage);
			System.out.println(actualMessage);

			return actualMessage;

		} catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL, "Failed to get <b>Alert Message<b> due to <b style='color:red'>" + e.getClass()
					+ "<br>" + e.getMessage() + "</b>");
			return actualMessage;
		}
	}

	/**
	 * This method will accept alert if there is any alert
	 * 
	 * @param logger
	 * @param webElement
	 * @param expected   string
	 * 
	 */
	public void acceptAlertIfAny(ExtentTest logger, WebDriver driver) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.alertIsPresent());

			driver.switchTo().alert().accept();
			logger.log(LogStatus.PASS, "alert accepted successfully");
			System.out.println("alert is present");

		} catch (Exception e) {
			System.out.println("No Alert present");
		}
	}

	/**
	 * This method will return specified element text
	 * 
	 * @param logger
	 * @param element
	 * @return
	 */
	public String getElementText(ExtentTest logger, WebElement element) {
		String text = "";
		try {
			text = element.getText();
			logger.log(LogStatus.PASS, "Element text taken :" + text + "</b");
			System.out.println(text);

		} catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL, "Failed to take Element <b>" + element + "</b>text due to <b style='color:red'>"
					+ e.getClass() + "<br>" + e.getMessage() + "</b>");
		}

		return text;
	}

	/**
	 * This method will verify expected text contains actual text
	 * 
	 * @param logger
	 * @param element
	 * @return
	 */
	public void verifyTextContains(ExtentTest logger, WebElement we, String expected) {

		try {
			String actual = we.getText();

			if (actual.trim().contains(expected.trim())) {
				System.out.println("strings are matching" + expected + "--" + actual);
				logger.log(LogStatus.PASS, "Actual Contains Expected" + expected + "--" + actual);

			} else {
				System.out.println("strings are not matching" + expected + "--" + actual);
				logger.log(LogStatus.FAIL, "Actual does not contain expected" + expected + "--" + actual);

			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL, "Failed to match string due to <b style='color:red'>" + e.getClass() + "<br>"
					+ e.getMessage() + "</b>");

		}
	}

	// This method will verify expected text contains actual text by value attribute

	public void verifyTextContainsByValue(ExtentTest logger, WebElement we, String expected) {

		try {
			String actual = we.getAttribute("value");

			if (actual.trim().contains(expected.trim())) {
				System.out.println("strings are matching" + expected + "--" + actual);
				logger.log(LogStatus.PASS, "Actual Contains Expected");

			} else {
				System.out.println("strings are not matching" + expected + "--" + actual);
				logger.log(LogStatus.FAIL,
						"Actual does not contain expected, strings are not matching " + expected + "--" + actual);

			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL, "Failed to match string due to <b style='color:red'>" + e.getClass() + "<br>"
					+ e.getMessage() + "</b>");

		}
	}

	/**
	 * This method will verify the whether the element is displayed or not
	 * 
	 * @param logger
	 * @param object
	 * @return
	 */
	public int verifyElementNotPresentByXpath(ExtentTest logger, String xpath, WebDriver driver) {
		int flag = 0;
		try {
			if (driver.findElements(By.xpath(xpath)).size() == 0)

			{
				// System.out.println("The Object is present ");
				logger.log(LogStatus.PASS, "WebElement" + xpath + " is not displayed");

				flag = 0;
			} else {

				logger.log(LogStatus.FAIL, "WebElement" + xpath + " is displayed");
				flag = 1;

			}
			return flag;
		} catch (Exception e) {
			flag = 1;
			e.printStackTrace();

			logger.log(LogStatus.FAIL, "Failed: Element  <b>" + xpath + "</b> displayed due to <b style='color:red'>"
					+ e.getClass() + "<br>" + e.getMessage() + "</b>");
			return flag;
		}

	}

//Method returns integer value(more than zero) if element is present
	public int verifyElementPresentByXpath(ExtentTest logger, String xpath, WebDriver driver) {
		int flag = 0;
		try {
			if (driver.findElements(By.xpath(xpath)).size() == 0)

			{
				// System.out.println("The Object is present ");
				logger.log(LogStatus.FAIL, "WebElement" + xpath + " is not displayed");

			} else {

				logger.log(LogStatus.PASS, "WebElement" + xpath + " is displayed");
				flag = 1;

			}
			return flag;
		} catch (Exception e) {

			e.printStackTrace();

			logger.log(LogStatus.FAIL, "Failed: Element  <b>" + xpath + "</b> displayed due to <b style='color:red'>"
					+ e.getClass() + "<br>" + e.getMessage() + "</b>");
			return flag;
		}

	}

	/**
	 * This method will verify alert's application value should not same as expected
	 * value
	 * 
	 * @param logger
	 * @param webElement
	 * @param expected   string
	 * 
	 */

	public String verifyAlertDoesnotContainsText(ExtentTest logger, WebDriver driver, String expectedMessage) {
		String actualMessage = "";
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			// WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.alertIsPresent());

			actualMessage = driver.switchTo().alert().getText();

			if (!(actualMessage.trim().contains(expectedMessage.trim()))) {
				logger.log(LogStatus.PASS,
						"alert Message Verified" + actualMessage + "does not contain ---" + expectedMessage);

			} else {

				logger.log(LogStatus.FAIL,
						"alert Message not verified" + actualMessage + " should not contain ---" + expectedMessage);
				System.out.println("alert accepted successfully but message is not matched");
			}
			return actualMessage;

		} catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL, "Failed to get <b>Alert Message<b> due to <b style='color:red'>" + e.getClass()
					+ "<br>" + e.getMessage() + "</b>");
			return actualMessage;
		}
	}

	/**
	 * This method will generate any random number between 1-100
	 * 
	 * @param logger
	 * @param webElement
	 * @param expected   string
	 * 
	 */

	public int generateRandomNumber(ExtentTest logger, WebDriver driver) {
		int randomInt = 0;
		try {

			// generate unique number using Random class
			Random randomGenerator = new Random();
			randomInt = randomGenerator.nextInt(100);
			logger.log(LogStatus.PASS, "Random number is generated" + randomInt);

			return randomInt;

		} catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL, "Failed to generate <b>random number<b> due to <b style='color:red'>"
					+ e.getClass() + "<br>" + e.getMessage() + "</b>");
			return randomInt;
		}
	}

	/**
	 * This method is useful for scrolling via Java script
	 * 
	 * @param driver
	 * @param element
	 * @return status[ boolean type]
	 */
	public void scrollDownByJavascriptExecutor(ExtentTest logger, WebDriver driver) {
		try {
			JavascriptExecutor js = ((JavascriptExecutor) driver);

			js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
			logger.log(LogStatus.PASS, "Scrolling Down by using JavaScriptExecutor success");

		} catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL, "Failed to scroll down due to <b style='color:red'>" + e.getClass() + "<br>"
					+ e.getMessage() + "</b>");
		}

	}

	// Method opens a new tab in browser
	public void openNewTab(ExtentTest logger, WebDriver driver) {

		try {
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "t");

			Thread.sleep(5000L);
		} catch (Exception e) {

			e.printStackTrace();
			logger.log(LogStatus.FAIL, "Failed to switch");
		}

	}

	// Method press right key through robot class

	public void keyPressRight(ExtentTest logger) {
		try {
			Robot r = new Robot();
			r.keyPress(KeyEvent.KEY_LOCATION_RIGHT);
			System.out.println("Enter Key pressed");
			logger.log(LogStatus.PASS, "Key Press Right is success");
			r.keyRelease(KeyEvent.KEY_LOCATION_RIGHT);
		} catch (Exception e) {
			logger.log(LogStatus.FAIL, "Failed to press Right key " + e.getMessage());
		}
	}

	// Method provies input in textfield
	public void setTextValue(ExtentTest logger, WebDriver driver, WebElement we, String value) {

		try {

			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].value='" + value + "';", we);
			System.out.println("Value is set in textfield:" + value);

		} catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL, "Failed to match string due to <b style='color:red'>" + e.getClass() + "<br>"
					+ e.getMessage() + "</b>");

		}
	}

	// Method verifies text based on any attribute
	public void verifyTextByAttribute(ExtentTest logger, WebElement we, String expected, String attribute) {

		try {
			String actual = we.getAttribute(attribute);

			if (actual.trim().contains(expected.trim())) {
				System.out.println("strings are matching" + expected + "--" + actual);
				logger.log(LogStatus.PASS, "Strings Matched");

			} else {
				System.out.println("strings are not matching" + expected + "--" + actual);
				logger.log(LogStatus.FAIL, "Strings not Matched");

			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL, "Failed to match string due to <b style='color:red'>" + e.getClass() + "<br>"
					+ e.getMessage() + "</b>");

		}
	}

	// Mehtod verifies application text contains expected text
	public void checkForContainsText(ExtentTest logger, WebElement we, String data) {
		try {
			String textFromResults = we.getText().toLowerCase().trim();

			if (textFromResults.contains(data.toLowerCase().trim()))
				logger.log(LogStatus.PASS,
						"<b>Actual: </b>" + we.getText().toString() + "<br>" + "<b>Expected: </b>" + data);
			else {
				logger.log(LogStatus.FAIL,
						"<b>Actual: </b>" + we.getText().toString() + "<br>" + "<b>Expected: </b>" + data);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL,
					"Failed </b> due to <b style='color:red'>" + e.getClass() + "<br>" + e.getMessage() + "</b>");
		}

	}

	// Mehtod verifies application text does not contain expected text
	public void checkForNotContainsText(ExtentTest logger, WebElement we, String data) {
		try {
			String textFromResults = we.getText().toLowerCase().trim();

			if (!(textFromResults.contains(data.toLowerCase().trim())))
				logger.log(LogStatus.PASS,
						"<b>Actual: </b>" + we.getText().toString() + "<br>" + "<b>Expected: </b>" + data);
			else {
				logger.log(LogStatus.FAIL,
						"<b>Actual: </b>" + we.getText().toString() + "<br>" + "<b>Expected: </b>" + data);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL,
					"Failed </b> due to <b style='color:red'>" + e.getClass() + "<br>" + e.getMessage() + "</b>");
		}

	}

	// Method returns number of matching records present
	public int verifyNumberOfRecords(ExtentTest logger, WebElement we) {

		int size = 0;
		try {
			List<WebElement> tableTr = we.findElements(By.tagName("tr"));
			size = tableTr.size();
			System.out.println("Size" + size);
			return size;

		} catch (Exception e) {

			logger.log(LogStatus.FAIL, "Failed to match string due to <b style='color:red'>" + e.getClass() + "<br>"
					+ e.getMessage() + "</b>");
			return size;
		}
	}

	// Method returns index of matching column
	public int returnColumnIndex(ExtentTest logger, String xp, String columnName, WebDriver driver) {

		int index = 0;
		try {
			List<WebElement> tableTr = driver.findElements(By.xpath(xp));
			int size = tableTr.size();
			// System.out.println("Size"+size);
			for (int i = 0; i < size; i++) {

				if (tableTr.get(i).getText().contains(columnName)) {
					index = i + 1;
					// System.out.println("Column Index"+index);
					break;
				}
			}
			if (index == 0) {
				logger.log(LogStatus.FAIL, "Column is not found and index is " + index);
			}
			logger.log(LogStatus.PASS, "COlumn is found and index is " + index);
			return index;
		} catch (Exception e) {

			logger.log(LogStatus.FAIL,
					"Failed to get column Index'>" + e.getClass() + "<br>" + e.getMessage() + "</b>");
			return index;
		}
	}

	// Mehtod upload file
	public void uploadFile(ExtentTest logger, WebElement we, String data) {

		try {
			System.out.print("FilePath:" + System.getProperty("user.dir") + "\\" + data);
			we.sendKeys(System.getProperty("user.dir") + "\\" + data);
			logger.log(LogStatus.PASS, "File is uploaded");

		} catch (Exception e) {

			logger.log(LogStatus.FAIL, "Unable to upload file");
		}

	}

	// Method returns current date& time in given format
	public String getCurrentDateTime(ExtentTest logger, String format) {
		String currentDate = "";
		try {
			DateFormat sdf = new SimpleDateFormat(format);
			Date date = new Date();
			System.out.println(sdf.format(date));
			currentDate = sdf.format(date);
			System.out.println("currentDateTime: " + currentDate);
			logger.log(LogStatus.PASS, "Current Date and Time is formatted");

			return currentDate;

		} catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL, "Unable to get current date time ");
			return currentDate;
		}

	}

	// format: yyyy-MM-dd
	// Method returns next date & time in given format
	public String getNextDateTime(ExtentTest logger, String format, int numberOfDays) {
		String nextDate = "";
		try {

			Date currentDate = new Date();

			SimpleDateFormat sdf = new SimpleDateFormat(format);

			int i;
			i = 24 * 3600000 * numberOfDays;
			Date myDate = DateUtils.addMilliseconds(currentDate, i);
			nextDate = sdf.format(myDate);
			System.out.print("Next Date and Time" + nextDate);
			return nextDate;
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL,
					"Unable to get next date <b style='color:red'>" + e.getClass() + "<br>" + e.getMessage() + "</b>");
			return nextDate;
		}

	}

	// Method returns previous date and time in given format
	public String getPreviousDateTime(ExtentTest logger, String format) {
		String previousDate = "";
		try {

			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -1);

			SimpleDateFormat sdf = new SimpleDateFormat(format);

			previousDate = sdf.format(cal.getTime());
			System.out.println("Previous Date and Time" + previousDate);
			return previousDate;
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL,
					"Unable to get next date <b style='color:red'>" + e.getClass() + "<br>" + e.getMessage() + "</b>");
			return previousDate;
		}

	}

	// Method returns next month of your current month in given format
	public String getNextMonth(ExtentTest logger, String format) {
		String nextDate = "";
		try {

			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, 1);

			while (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
					|| cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
				cal.add(Calendar.MONTH, 1);
			}

			SimpleDateFormat sdf = new SimpleDateFormat(format);

			nextDate = sdf.format(cal.getTime());
			System.out.println("Next Date and Time" + nextDate);
			return nextDate;
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL,
					"Unable to get next date <b style='color:red'>" + e.getClass() + "<br>" + e.getMessage() + "</b>");
			return nextDate;
		}

	}

	public void takeScreenShot(ExtentTest logger, WebDriver driver, String fileName) {

		screenShotFileName = screenShotPath + fileName + "_" + dateFormat.format(date) + ".jpg";
		captureScreenShot(logger, driver, screenShotFileName);

		logger.log(LogStatus.PASS, "HTML", "Pass due to <b style='color:green'>");
		logger.log(LogStatus.INFO, "For reference Snapshot below: " + logger.addScreenCapture(screenShotFileName));

	}

	// Method scrolls till element becomes visible
	public void scrollByVisibleElement(WebDriver driver, ExtentTest logger, WebElement we) {

		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;

			// This will scroll the page till the element is found
			js.executeScript("arguments[0].scrollIntoView();", we);
			pause(logger, "3");

			logger.log(LogStatus.PASS, "Scroll bar is scrolled till element becomes visible");
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL, "Unable to scroll");

		}
	}

	// Method focus on a particular element

	public void focusToElement(ExtentTest logger, WebDriver driver, WebElement ele) {
		try {
			new Actions(driver).moveToElement(ele).perform();
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL, "Focus to element failed");

		}
	}

	// Wait till page is loaded

	public void waitTillPageLoad(ExtentTest logger, WebDriver driver) {
		try {
			driver.manage().timeouts().pageLoadTimeout(1000, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL, "Page not loaded in 1000 seconds");

		}

	}

	// goto end of page
	public void endofpage(ExtentTest logger) {
		try {
			Robot r = new Robot();
			r.keyPress(KeyEvent.VK_CONTROL);
			r.keyPress(KeyEvent.VK_END);

			r.keyRelease(KeyEvent.VK_END);
			r.keyRelease(KeyEvent.VK_CONTROL);
		} catch (Exception e) {
			logger.log(LogStatus.FAIL, "Failed to press Enter key " + e.getMessage());
		}
	}

	// go to top of page
	public void topofpage(ExtentTest logger) {
		try {
			Robot r = new Robot();
			r.keyPress(KeyEvent.VK_CONTROL);
			r.keyPress(KeyEvent.VK_HOME);

			r.keyRelease(KeyEvent.VK_HOME);
			r.keyRelease(KeyEvent.VK_CONTROL);
		} catch (Exception e) {
			logger.log(LogStatus.FAIL, "Failed to press Enter key " + e.getMessage());
		}
	}

	// copy paste
	public void copypaste(ExtentTest logger) {
		try {
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Clipboard clipboard = toolkit.getSystemClipboard();
			StringSelection strSel = new StringSelection(System.getProperty("user.dir") + "\\Input.xlsx");
			clipboard.setContents(strSel, null);
			Robot r = new Robot();
			r.keyPress(KeyEvent.VK_CONTROL);
			r.keyPress(KeyEvent.VK_V);
			r.keyPress(KeyEvent.VK_ENTER);
			r.keyRelease(KeyEvent.VK_V);
			r.keyRelease(KeyEvent.VK_CONTROL);
			r.keyRelease(KeyEvent.VK_ENTER);

		} catch (Exception e) {
			logger.log(LogStatus.FAIL, "Failed to press  " + e.getMessage());
		}
	}

	// Wait till page is loaded completely
	public void waitForPageToLoadCompletely(ExtentTest logger, final WebDriver driver) {
		try {
			logger.log(LogStatus.INFO, "Waiting for page to load completely");
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			// WebDriverWait wait = new WebDriverWait(driver, 30);

			wait.until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver wdriver) {
					return ((JavascriptExecutor) driver).executeScript(
							"var frames = window.frames; var frameNo=(frames.length)-1;" + "if(frames.length<=1){ "

									+ "return document.readyState;}"

									+ "return frames[frameNo].document.readyState")
							.equals("complete");
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL, "Unable to waitForPageToLoadCompletely  due to <b style='color:red'>"
					+ e.getClass() + "<br>" + e.getMessage() + "</b>");
		}

	}

	/**
	 * This method will accept alert
	 * 
	 * @param logger
	 * @param webElement
	 * @param expected   string
	 * 
	 */
	public void acceptAlert(ExtentTest logger, WebDriver driver) {
		try {
			// WebDriverWait wait = new WebDriverWait(driver, 10);
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

			driver.switchTo().alert().accept();
			logger.log(LogStatus.PASS, "alert accepted successfully");
			System.out.println("alert is present");

		} catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL, e.getMessage());
		}
	}

	// Provide input in Textfield by using Actions class. Specially used to type in
	// Table cell
	public void setTextUsingActions(ExtentTest logger, WebDriver driver, WebElement we, String data) {
		try {
			Actions actions = new Actions(driver);
			actions.moveToElement(we);
			actions.click();
			actions.sendKeys(data);
			actions.build().perform();
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL, "Failed to send keys due to <b style='color:red'>" + e.getClass() + "<br>"
					+ e.getMessage() + "</b>");
		}
	}

	// Method double clicks on element
	public void doubleClick(ExtentTest logger, WebDriver driver, WebElement we) {
		try {
			Actions actions = new Actions(driver);

			actions.doubleClick(we).perform();

		} catch (Exception e) {
			e.printStackTrace();
			logger.log(LogStatus.FAIL, "Failed to double click" + e.getClass() + "<br>" + e.getMessage() + "</b>");
		}
	}

	public void scrollToElement(ExtentTest logger, WebElement element, WebDriver driver) {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
			pause(logger, "2");
			element.getLocation();

			/*
			 * driver.manage().window().setPosition(element.getLocation());
			 * logger.log(LogStatus.INFO, "Scrolled to the given element");
			 */
		} catch (Exception e) {

			e.printStackTrace();
			logger.log(LogStatus.FAIL, "Failed To scrollToElement due to <b style='color:red'>" + e.getClass() + "<br>"
					+ e.getMessage() + "</b>");
		}
	}

	

}
