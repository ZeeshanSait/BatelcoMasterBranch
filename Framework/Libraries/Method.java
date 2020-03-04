package Libraries;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.w3c.dom.Document;
//import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/*---------------------------------------------------------------------------------------------------------
 * Class Name			: Method
 * Use 					: Has the functions to do operation on the objects on the web page 
 * Designed By			: AG
 * Last Modified Date 	: 25-Apr-2016
 --------------------------------------------------------------------------------------------------------*/
public class Method extends Driver {
	/*----------------------------------------------------------------------------------------------------------
	 * Method Name			: setTD
	 * Arguments			: identifier and value
	 * Use 					: Enters value in the Web Edit
	 * Designed By			: Vinodhini
	 * Last Modified Date 	: 25-Apr-2016
	--------------------------------------------------------------------------------------------------------*/
	public static void setETD(String[] identify, String val) {
		int i = 0;
		Exception Error = null;
		for (i = 0; i < identify.length;) {
			try {
				if (Continue.get() == true) {
					switch (i) {
					case 0:
						if (identify[0] != "") {
							Scroll(cDriver.get().findElement(By.xpath(identify[0])));
							cDriver.get().findElement(By.xpath(identify[0])).sendKeys(val);
							cDriver.get().findElement(By.xpath(identify[0])).click();
							cDriver.get().findElement(By.xpath(identify[0])).sendKeys(Keys.ENTER);
							break;
						} else {
							throw new Exception(Error);
						}
					case 1:
						if (identify[1] != "") {
							Scroll(cDriver.get().findElement(By.xpath(identify[1])));
							cDriver.get().findElement(By.name(identify[1])).sendKeys(val);
							cDriver.get().findElement(By.name(identify[1])).click();
							cDriver.get().findElement(By.name(identify[1])).sendKeys(Keys.ENTER);
							break;
						} else {
							throw new Exception(Error);
						}
					case 2:
						if (identify[2] != "") {
							Scroll(cDriver.get().findElement(By.xpath(identify[2])));
							cDriver.get().findElement(By.id(identify[2])).sendKeys(val);
							cDriver.get().findElement(By.id(identify[2])).click();
							cDriver.get().findElement(By.id(identify[2])).sendKeys(Keys.ENTER);
							break;
						} else {
							throw new Exception(Error);
						}
					case 3:
						if (identify[3] != "") {
							Scroll(cDriver.get().findElement(By.xpath(identify[3])));
							cDriver.get().findElement(By.className(identify[3])).sendKeys(val);
							cDriver.get().findElement(By.className(identify[3])).click();
							cDriver.get().findElement(By.className(identify[3])).sendKeys(Keys.ENTER);
							break;
						} else {
							throw new Exception(Error);
						}
					case 4:
						if (identify[4] != "") {
							Scroll(cDriver.get().findElement(By.xpath(identify[4])));
							cDriver.get().findElement(By.linkText(identify[4])).sendKeys(val);
							cDriver.get().findElement(By.linkText(identify[4])).click();
							cDriver.get().findElement(By.linkText(identify[4])).sendKeys(Keys.ENTER);
							break;
						} else {
							throw new Exception(Error);
						}
					}
				}
				break;
			} catch (Exception e) {
				i++;
				Error = e;
				Continue.set(true);
			}
		}
		if (i == identify.length) {
			Continue.set(false);
			Result.fUpdateLog("Object does't Exists to set");
			Error.printStackTrace();
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: waitForPageToLoad
	 * Arguments			: webcDriver.get() and timeout in seconds
	 * Use 					: Checks for the browser ready state and waits for the page to load 
	 * Designed By			: AG
	 * Last Modified Date 	: 25-Apr-2016
	--------------------------------------------------------------------------------------------------------*/
	public static void waitForPageToLoad(WebDriver driver, int timeOutInSeconds) {
		try {
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			Thread.sleep(2000);
			JavascriptExecutor js = (JavascriptExecutor) cDriver.get();
			String command = "return document.readyState";
			// Check the readyState before doing any waits
			if (js.executeScript(command).toString().equals("complete")) {
				return;
			}
			for (int i = 0; i < timeOutInSeconds; i++) {
				if (js.executeScript(command).toString().equals("complete")) {
					break;
				}
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			Result.fUpdateLog("Object does't Exists waitForPageToLoad : " + ExceptionUtils.getStackTrace(e));
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: setdropvalue
	 * Arguments			: identifier and value
	 * Use 					: Enters value in the drop down 
	 * Designed By			: AG
	 * Last Modified Date 	: 25-Apr-2016
	--------------------------------------------------------------------------------------------------------*/
	public static void setdropvalue(String[] identify, String val) {
		int i = 0;
		Exception Error = null;
		for (i = 0; i < identify.length; i++) {
			try {
				if (Continue.get() == true) {
					switch (i) {
					case 0:
						if (identify[0] != "") {
							cDriver.get().findElement(By.xpath(identify[0])).click();
							cDriver.get().findElement(By.xpath(identify[0])).sendKeys(String.valueOf(val.charAt(0)));
							String brfound = "no";
							while (brfound != "yes") {
								String brcode = cDriver.get().findElement(By.xpath(identify[0])).getAttribute("value");
								if (brcode.contains(val)) {
									cDriver.get().findElement(By.xpath(identify[0])).click();
									brfound = "yes";
									break;
								} else {
									cDriver.get().findElement(By.xpath(identify[0])).sendKeys(Keys.ARROW_DOWN);
								}
							}
							break;
						} else {
							throw new Exception(Error);
						}
					case 1:
						if (identify[1] != "") {
							cDriver.get().findElement(By.name(identify[1])).click();
							cDriver.get().findElement(By.name(identify[1])).sendKeys(String.valueOf(val.charAt(0)));
							String brfound = "no";
							while (brfound != "yes") {
								String brcode = cDriver.get().findElement(By.name(identify[1])).getAttribute("value");
								if (brcode.contains(val)) {
									cDriver.get().findElement(By.name(identify[1])).click();
									brfound = "yes";
									break;
								} else {
									cDriver.get().findElement(By.name(identify[1])).sendKeys(Keys.ARROW_DOWN);
								}
							}
							break;
						} else {
							throw new Exception(Error);
						}
					case 2:
						if (identify[2] != "") {
							cDriver.get().findElement(By.id(identify[2])).click();
							cDriver.get().findElement(By.id(identify[2])).sendKeys(String.valueOf(val.charAt(0)));
							String brfound = "no";
							while (brfound != "yes") {
								String brcode = cDriver.get().findElement(By.id(identify[2])).getAttribute("value");
								if (brcode.contains(val)) {
									cDriver.get().findElement(By.id(identify[2])).click();
									brfound = "yes";
									break;
								} else {
									cDriver.get().findElement(By.id(identify[2])).sendKeys(Keys.ARROW_DOWN);
								}
							}
							break;
						} else {
							throw new Exception(Error);
						}
					case 3:
						if (identify[3] != "") {
							cDriver.get().findElement(By.className(identify[3])).click();
							cDriver.get().findElement(By.className(identify[3]))
									.sendKeys(String.valueOf(val.charAt(0)));
							String brfound = "no";
							while (brfound != "yes") {
								String brcode = cDriver.get().findElement(By.className(identify[3]))
										.getAttribute("value");
								if (brcode.contains(val)) {
									cDriver.get().findElement(By.className(identify[3])).click();
									brfound = "yes";
									break;
								} else {
									cDriver.get().findElement(By.className(identify[3])).sendKeys(Keys.ARROW_DOWN);
								}
							}
							break;
						} else {
							throw new Exception(Error);
						}
					case 4:
						if (identify[4] != "") {
							cDriver.get().findElement(By.linkText(identify[4])).click();
							cDriver.get().findElement(By.linkText(identify[4])).sendKeys(String.valueOf(val.charAt(0)));
							String brfound = "no";
							while (brfound != "yes") {
								String brcode = cDriver.get().findElement(By.linkText(identify[4]))
										.getAttribute("value");
								if (brcode.contains(val)) {
									cDriver.get().findElement(By.linkText(identify[4])).click();
									brfound = "yes";
									break;
								} else {
									cDriver.get().findElement(By.linkText(identify[4])).sendKeys(Keys.ARROW_DOWN);
								}
							}
							break;
						} else {
							throw new Exception(Error);
						}
					}
				}
				break;
			} catch (Exception e) {
				i++;
				Error = e;
				Continue.set(true);
			}
		}
		if (i == identify.length) {
			Continue.set(false);
			Result.fUpdateLog("Object does't Exists to set drop value");
			Error.printStackTrace();
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: setTD
	 * Arguments			: identifier and value
	 * Use 					: Enters value in the Web Edit
	 * Designed By			: AG
	 * Last Modified Date 	: 25-Apr-2016
	--------------------------------------------------------------------------------------------------------*/
	public static void setTD(String[] identify, String val) {
		int i = 0;
		Exception Error = null;
		for (i = 0; i < identify.length;) {
			try {
				if (Continue.get() == true) {
					switch (i) {
					case 0:
						if (identify[0] != "") {
							Scroll(cDriver.get().findElement(By.xpath(identify[0])));
							cDriver.get().findElement(By.xpath(identify[0])).sendKeys(val);
							break;
						} else {
							throw new Exception(Error);
						}
					case 1:
						if (identify[1] != "") {
							Scroll(cDriver.get().findElement(By.xpath(identify[1])));
							cDriver.get().findElement(By.name(identify[1])).sendKeys(val);
							break;
						} else {
							throw new Exception(Error);
						}
					case 2:
						if (identify[2] != "") {
							Scroll(cDriver.get().findElement(By.xpath(identify[2])));
							cDriver.get().findElement(By.id(identify[2])).sendKeys(val);
							break;
						} else {
							throw new Exception(Error);
						}
					case 3:
						if (identify[3] != "") {
							Scroll(cDriver.get().findElement(By.xpath(identify[3])));
							cDriver.get().findElement(By.className(identify[3])).sendKeys(val);
							break;
						} else {
							throw new Exception(Error);
						}
					case 4:
						if (identify[4] != "") {
							Scroll(cDriver.get().findElement(By.xpath(identify[4])));
							cDriver.get().findElement(By.linkText(identify[4])).sendKeys(val);
							break;
						} else {
							throw new Exception(Error);
						}
					}
				}
				break;
			} catch (Exception e) {
				i++;
				Error = e;
				Continue.set(true);
			}
		}
		if (i == identify.length) {
			Continue.set(false);
			Result.fUpdateLog("Object does't Exists to set");
			Error.printStackTrace();
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: selectTD
	 * Arguments			: identifier and value
	 * Use 					: Selects value in the drop down
	 * Designed By			: AG
	 * Last Modified Date 	: 25-Apr-2016
	--------------------------------------------------------------------------------------------------------*/
	public static void selectTD(String[] identify, String val) {
		int i = 0;
		Exception Error = null;
		for (i = 0; i < identify.length;) {
			try {
				if (Continue.get() == true) {
					switch (i) {
					case 0:
						if (identify[0] != "") {
							cDriver.get().findElement(By.xpath(identify[0])).sendKeys(val);
							break;
						} else {
							throw new Exception(Error);
						}
					case 1:
						if (identify[1] != "") {
							cDriver.get().findElement(By.name(identify[1])).sendKeys(val);
							break;
						} else {
							throw new Exception(Error);
						}
					case 2:
						if (identify[2] != "") {
							cDriver.get().findElement(By.id(identify[2])).sendKeys(val);
							break;
						} else {
							throw new Exception(Error);
						}
					case 3:
						if (identify[3] != "") {
							cDriver.get().findElement(By.className(identify[3])).sendKeys(val);
							break;
						} else {
							throw new Exception(Error);
						}
					case 4:
						if (identify[4] != "") {
							cDriver.get().findElement(By.linkText(identify[4])).sendKeys(val);
							break;
						} else {
							throw new Exception(Error);
						}
					}
				}
				break;
			} catch (Exception e) {
				i++;
				Error = e;
				Continue.set(true);
			}
		}
		if (i == identify.length) {
			Continue.set(false);
			Result.fUpdateLog("Object does't Exists to select");
			Error.printStackTrace();
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: clickTD
	 * Arguments			: identifier
	 * Use 					: Click the Web element, Button or Link
	 * Designed By			: AG
	 * Last Modified Date 	: 25-Apr-2016
	--------------------------------------------------------------------------------------------------------*/
	public static void clickTD(String[] identify) {
		int i = 0;
		Exception Error = null;
		for (i = 0; i < identify.length;) {

			try {
				if (Continue.get() == true) {
					switch (i) {
					case 0:
						if (identify[0] != "") {
							Scroll(cDriver.get().findElement(By.xpath(identify[0])));
							cDriver.get().findElement(By.xpath(identify[0])).click();
							break;
						} else {
							throw new Exception(Error);
						}
					case 1:
						if (identify[1] != "") {
							Scroll(cDriver.get().findElement(By.xpath(identify[1])));
							cDriver.get().findElement(By.name(identify[1])).click();
							break;
						} else {
							throw new Exception(Error);
						}
					case 2:
						if (identify[2] != "") {
							Scroll(cDriver.get().findElement(By.xpath(identify[2])));
							cDriver.get().findElement(By.id(identify[2])).click();
							break;
						} else {
							throw new Exception(Error);
						}
					case 3:
						if (identify[3] != "") {
							Scroll(cDriver.get().findElement(By.xpath(identify[3])));
							cDriver.get().findElement(By.className(identify[3])).click();
							break;
						} else {
							throw new Exception(Error);
						}
					case 4:
						if (identify[4] != "") {
							Scroll(cDriver.get().findElement(By.xpath(identify[4])));
							cDriver.get().findElement(By.linkText(identify[4])).click();
							break;
						} else {
							throw new Exception(Error);
						}
					}
				}
				break;
			} catch (Exception e) {
				i++;
				Error = e;
				Continue.set(true);
			}

		}

		if (i == identify.length) {

			if (Error.toString().contains("is not clickable at point")) {
				clickTD1(identify[0]);
			} else {
				Continue.set(false);
				Result.fUpdateLog("Object does't Exists to click");
				Error.printStackTrace();
			}
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: clickTD
	 * Arguments			: identifier
	 * Use 					: Click the Web element, Button or Link
	 * Designed By			: AG
	 * Last Modified Date 	: 25-Apr-2016
	--------------------------------------------------------------------------------------------------------*/
	public static void clickTD1(String identify) {
		int count = 0;
		Exception Error = null;
		do {
			try {
				if (Continue.get() == true) {
					if (identify != "") {
						Thread.sleep(1000);
						Scroll(cDriver.get().findElement(By.xpath(identify)));
						cDriver.get().findElement(By.xpath(identify)).click();
						break;
					} else {
						throw new Exception(Error);
					}

				}

			} catch (Exception e) {
				Error = e;
				Continue.set(true);
			}

			if (!Error.toString().contains("is not clickable at point")) {
				count = 17;
			} else if (count == 15) {
				Continue.set(false);
				Result.fUpdateLog("Object does't Exists to click");
				Error.printStackTrace();
			}
			System.out.println(count);

			count++;
		} while (count < 16);
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: clearTD
	 * Arguments			: identifier
	 * Use 					: Clears the value in the web edit or drop down
	 * Designed By			: AG
	 * Last Modified Date 	: 25-Apr-2016
	--------------------------------------------------------------------------------------------------------*/
	public static void clearTD(String[] identify) {
		int i = 0;
		Exception Error = null;
		for (i = 0; i < identify.length;) {
			try {

				if (Continue.get() == true) {
					switch (i) {
					case 0:
						if (identify[0] != "") {
							cDriver.get().findElement(By.xpath(identify[0])).clear();
							break;
						} else {
							throw new Exception(Error);
						}
					case 1:
						if (identify[1] != "") {
							cDriver.get().findElement(By.name(identify[1])).clear();
							break;
						} else {
							throw new Exception(Error);
						}
					case 2:
						if (identify[2] != "") {
							cDriver.get().findElement(By.id(identify[2])).clear();
							break;
						} else {
							throw new Exception(Error);
						}
					case 3:
						if (identify[3] != "") {
							cDriver.get().findElement(By.className(identify[3])).clear();
							break;
						} else {
							throw new Exception(Error);
						}
					case 4:
						if (identify[4] != "") {
							cDriver.get().findElement(By.linkText(identify[4])).click();
							break;
						} else {
							throw new Exception(Error);
						}
					}
				}
				break;
			} catch (Exception e) {
				i++;
				Error = e;
				Continue.set(true);
			}
		}
		if (i == identify.length) {
			Continue.set(false);
			Result.fUpdateLog("Object does't Exists to clear");
			Error.printStackTrace();
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: getval
	 * Arguments			: identifier and value
	 * Use 					: reads value
	 * Designed By			: AG
	 * Last Modified Date 	: 25-Apr-2016
	--------------------------------------------------------------------------------------------------------*/
	public static String getval(String[] identify) {
		String TxtVal = null;
		int i = 0;
		Exception Error = null;
		for (i = 0; i < identify.length;) {
			try {
				if (Continue.get() == true) {
					switch (i) {
					case 0:
						if (identify[0] != "") {
							Scroll(cDriver.get().findElement(By.xpath(identify[0])));
							TxtVal = cDriver.get().findElement(By.xpath(identify[0])).getAttribute("value");
							break;
						} else {
							throw new Exception(Error);
						}
					case 1:
						if (identify[1] != "") {
							Scroll(cDriver.get().findElement(By.xpath(identify[1])));
							TxtVal = cDriver.get().findElement(By.name(identify[1])).getAttribute("value");
							break;
						} else {
							throw new Exception(Error);
						}
					case 2:
						if (identify[2] != "") {
							Scroll(cDriver.get().findElement(By.xpath(identify[2])));
							TxtVal = cDriver.get().findElement(By.id(identify[2])).getAttribute("value");
							break;
						} else {
							throw new Exception(Error);
						}
					case 3:
						if (identify[3] != "") {
							Scroll(cDriver.get().findElement(By.xpath(identify[3])));
							TxtVal = cDriver.get().findElement(By.className(identify[3])).getAttribute("value");
							break;
						} else {
							throw new Exception(Error);
						}
					case 4:
						if (identify[4] != "") {
							Scroll(cDriver.get().findElement(By.xpath(identify[4])));
							TxtVal = cDriver.get().findElement(By.linkText(identify[4])).getAttribute("value");
							break;
						} else {
							throw new Exception(Error);
						}
					}
				}
				break;
			} catch (Exception e) {
				i++;
				Error = e;
				Continue.set(true);
			}
		}
		if (i == identify.length) {
			Continue.set(false);
			Result.fUpdateLog("Object does't Exists to get val");
			Error.printStackTrace();
			return null;
		} else {
			return TxtVal;
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: waittillobjvisible
	 * Arguments			: identifier and value
	 * Use 					: waits till the object exists
	 * Designed By			: AG
	 * Last Modified Date 	: 25-Apr-2016
	--------------------------------------------------------------------------------------------------------*/
	public static void waittillobjvisible(String[] identify) {
		String vis = "false";
		int countval = 1;
		while (countval < 5) {
			int i = 0;
			for (i = 0; i < identify.length;) {
				try {
					switch (i) {
					case 0:
						if (identify[0] != "") {
							cDriver.get().findElement(By.xpath(identify[0])).isDisplayed();
							break;
						} else {
							throw new Exception();
						}
					case 1:
						if (identify[1] != "") {
							cDriver.get().findElement(By.name(identify[1])).isDisplayed();
							break;
						} else {
							throw new Exception();
						}
					case 2:
						if (identify[2] != "") {
							cDriver.get().findElement(By.id(identify[2])).isDisplayed();
							break;
						} else {
							throw new Exception();
						}
					case 3:
						if (identify[3] != "") {
							cDriver.get().findElement(By.className(identify[3])).isDisplayed();
							break;
						} else {
							throw new Exception();
						}
					case 4:
						if (identify[4] != "") {
							cDriver.get().findElement(By.linkText(identify[4])).isDisplayed();
							break;
						} else {
							throw new Exception();
						}
					}
					break;
				} catch (Exception e) {
					i++;
				}
			}
			if (i == identify.length) {
				vis = "false";
				countval++;
			} else {
				vis = "true";
				break;
			}
		}
		if (vis == "false") {
			Continue.set(false);
			Result.fUpdateLog("Object does't Visible");
		}
	}

	public static boolean existobj(String[] identify) {
		String vis = "false";
		int countval = 1;
		Exception Error = null;
		while (countval < 2) {
			int i = 0;
			for (i = 0; i < identify.length;) {
				try {
					switch (i) {
					case 0:
						if (identify[0] != "") {
							cDriver.get().findElement(By.xpath(identify[0])).isDisplayed();
							break;
						} else {
							throw new Exception(Error);
						}
					case 1:
						if (identify[1] != "") {
							cDriver.get().findElement(By.name(identify[1])).isDisplayed();
							break;
						} else {
							throw new Exception(Error);
						}
					case 2:
						if (identify[2] != "") {
							cDriver.get().findElement(By.id(identify[2])).isDisplayed();
							break;
						} else {
							throw new Exception(Error);
						}
					case 3:
						if (identify[3] != "") {
							cDriver.get().findElement(By.className(identify[3])).isDisplayed();
							break;
						} else {
							throw new Exception(Error);
						}
					case 4:
						if (identify[4] != "") {
							cDriver.get().findElement(By.linkText(identify[4])).isDisplayed();
							break;
						} else {
							throw new Exception(Error);
						}
					}
					break;
				} catch (Exception e) {
					i++;
					Error = e;
				}
			}
			if (i == identify.length) {
				vis = "false";
				countval++;
			} else {
				vis = "true";
				break;
			}
		}
		if (vis == "false") {
			return false;
		} else {
			return true;
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Methodwaittillenabled
	 * Arguments			: identifier and value
	 * Use 					: waits till the object exists
	 * Designed By			: AG
	 * Last Modified Date 	: 25-Apr-2016
	--------------------------------------------------------------------------------------------------------*/
	public static boolean Methodwaittillenabled(String[] identify) {
		String vis = "false";
		int countval = 1;
		while (countval < 2) {
			int i = 0;
			for (i = 0; i < identify.length;) {
				try {
					switch (i) {
					case 0:
						if (identify[0] != "") {
							cDriver.get().findElement(By.xpath(identify[0])).isEnabled();
							break;
						} else {
							throw new Exception();
						}
					case 1:
						if (identify[1] != "") {
							cDriver.get().findElement(By.name(identify[1])).isEnabled();
							break;
						} else {
							throw new Exception();
						}
					case 2:
						if (identify[2] != "") {
							cDriver.get().findElement(By.id(identify[2])).isEnabled();
							break;
						} else {
							throw new Exception();
						}
					case 3:
						if (identify[3] != "") {
							cDriver.get().findElement(By.className(identify[3])).isEnabled();
							break;
						} else {
							throw new Exception();
						}
					case 4:
						if (identify[4] != "") {
							cDriver.get().findElement(By.linkText(identify[4])).isEnabled();
							break;
						} else {
							throw new Exception();
						}
					}
					break;
				} catch (Exception e) {
					i++;
				}
			}
			if (i == identify.length) {
				vis = "false";
				countval++;
			} else {
				vis = "true";
				break;
			}
		}
		if (vis == "false") {
			return false;
		} else {
			return true;
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Setvalue
	 * Arguments			: Update the tag value with the value in the testdata sheet
	 * Use 					: waits till the object exists
	 * Designed By			: AG
	 * Last Modified Date 	: 25-Apr-2016
	--------------------------------------------------------------------------------------------------------*/
	public static Document Setvalue(Document doc, String key, String val) {
		NodeList nList = doc.getElementsByTagName(key);
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				nNode.setTextContent(val);
			}
		}
		return doc;
	}

	public static void Scroll(WebElement element) {
		JavascriptExecutor jse = (JavascriptExecutor) cDriver.get();
		jse.executeScript("arguments[0].scrollIntoView();", element);
		cDriver.get().manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

	}
		
	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: allItems
	 * Arguments			: identifier and value
	 * Use 					: reads All Items in list box
	 * Designed By			: Sisira
	 * Last Modified Date 	: 07-Mar-2019
	--------------------------------------------------------------------------------------------------------*/
	public static ArrayList<String> allItems(String[] identify) {
		ArrayList<String> values = new ArrayList<String>();
		int i = 0;
		Exception Error = null;
		for (i = 0; i < identify.length;) {
			try {
				if (Continue.get() == true) {
					switch (i) {
					case 0:
						if (identify[0] != "") {
							Scroll(cDriver.get().findElement(By.xpath(identify[0])));
							// TxtVal =
							// cDriver.get().findElement(By.xpath(identify[0])).getAttribute("value");
							WebElement selectElement = cDriver.get().findElement(By.xpath(identify[0]));
							Select select = new Select(selectElement);
							List<WebElement> allOptions = select.getOptions();
							for (int j = 0; j < allOptions.size(); j++) {
								values.add(allOptions.get(j).getText());
							}

							break;
						} else {
							throw new Exception(Error);
						}
					case 1:
						if (identify[1] != "") {
							Scroll(cDriver.get().findElement(By.xpath(identify[1])));
							// TxtVal =
							// cDriver.get().findElement(By.name(identify[1])).getAttribute("value");
							WebElement selectElement = cDriver.get().findElement(By.xpath(identify[1]));
							Select select = new Select(selectElement);
							List<WebElement> allOptions = select.getOptions();
							for (int j = 0; j < allOptions.size(); j++) {
								values.add(allOptions.get(j).getText());
							}
							break;
						} else {
							throw new Exception(Error);
						}
					case 2:
						if (identify[2] != "") {
							Scroll(cDriver.get().findElement(By.xpath(identify[2])));
							// TxtVal = cDriver.get().findElement(By.id(identify[2])).getAttribute("value");
							WebElement selectElement = cDriver.get().findElement(By.xpath(identify[2]));
							Select select = new Select(selectElement);
							List<WebElement> allOptions = select.getOptions();
							for (int j = 0; j < allOptions.size(); j++) {
								values.add(allOptions.get(j).getText());
							}
							break;
						} else {
							throw new Exception(Error);
						}
					case 3:
						if (identify[3] != "") {
							Scroll(cDriver.get().findElement(By.xpath(identify[3])));
							WebElement selectElement = cDriver.get().findElement(By.xpath(identify[3]));
							Select select = new Select(selectElement);
							List<WebElement> allOptions = select.getOptions();
							for (int j = 0; j < allOptions.size(); j++) {
								values.add(allOptions.get(j).getText());
							}
							break;
						} else {
							throw new Exception(Error);
						}
					case 4:
						if (identify[4] != "") {
							Scroll(cDriver.get().findElement(By.xpath(identify[4])));
							WebElement selectElement = cDriver.get().findElement(By.xpath(identify[4]));
							Select select = new Select(selectElement);
							List<WebElement> allOptions = select.getOptions();
							for (int j = 0; j < allOptions.size(); j++) {
								values.add(allOptions.get(j).getText());
							}
							break;
						} else {
							throw new Exception(Error);
						}
					}
				}
				break;
			} catch (Exception e) {
				i++;
				Error = e;
				Continue.set(true);
			}
		}
		if (i == identify.length) {
			Continue.set(false);
			Result.fUpdateLog("Object does't Exists to get val");
			Error.printStackTrace();
			return null;
		} else {
			// return TxtVal;
			return values;
		}
	}
   

	//new Method 
	public static void getAction(String[] identify){
	//	Actions builder= new Actions(KeyWord.cDriver.get());
		Actions builder= new Actions(Driver.cDriver.get());
		WebElement element=null;
		try
		{
			if (identify[0] != "")
			{
				System.out.println(identify[0]);
   			//  element =KeyWord.cDriver.get().findElement(By.xpath(identify[0]));
				element =Driver.cDriver.get().findElement(By.xpath(identify[0]));
			}	
			else if(identify[1] != "")
			{
			//	 element=KeyWord.cDriver.get().findElement(By.name(identify[1]));
				element=Driver.cDriver.get().findElement(By.name(identify[1]));
			}	
			else if(identify[2] != "")
			{
		//		 element=KeyWord.cDriver.get().findElement(By.id(identify[2]));
				 element=Driver.cDriver.get().findElement(By.id(identify[2]));
					
			}
			else if(identify[3] != "")
			{
        //    	element=KeyWord.cDriver.get().findElement(By.className(identify[3]));
				element=Driver.cDriver.get().findElement(By.className(identify[3]));
			}
			else if(identify[4] != "")
			{
		//		 element=KeyWord.cDriver.get().findElement(By.linkText(identify[4]));
				 element=Driver.cDriver.get().findElement(By.linkText(identify[4]));
			}
			
			System.out.println(element);
			Action drawAction = builder.moveToElement(element,135,15) //start points x axis and y axis. 
		              .click()
		              .moveByOffset(200, 60) // 2nd points (x1,y1)
		              .clickAndHold()
		              .moveByOffset(100, 70)// 3rd points (x2,y2)
		              .release()
		              .build();
		    drawAction.perform();
		    System.out.println("Action Performed");
			
		}
		catch(Exception e)
		{
			
			e.printStackTrace();
			Driver.Continue.set(false);
			
		}
		
	}
	
	/*---------------------------------------------------------------------------------------------------------
     * Method Name             : JavaScriptClick
     * Arguments               : Object to where the script has to scroll
     * Use                     : To Click specific object usiong javascript
     * Designed By             : Sisira
     * Last Modified Date      : 08-Aug-2019
     --------------------------------------------------------------------------------------------------------*/
     public static void Highlight(String objname, String ObjTyp) {
           try {      
        	   JavascriptExecutor js = (JavascriptExecutor) cDriver.get();
                String[] objprop = Utlities.FindObject(objname, ObjTyp);
                WebElement Ele = cDriver.get().findElement(By.xpath(objprop[0]));
                js.executeScript("arguments[0].setAttribute('style', 'background: #F8EFEBA; border: 2px solid red;');", Ele);   
                
                Thread.sleep(1500);
           } catch (Exception e) {
                Continue.set(false);
                Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
           }
     }
	
     public static void Press(String Event, int NumberOfTimes) throws InterruptedException, AWTException
 	{
 		Robot robot = new Robot();
 		for(int i=0;i<NumberOfTimes;i++) {
 		switch (Event) { 		 	
 		
		case "TAB":			
			robot.keyPress(KeyEvent.VK_TAB);	
			break;
		case "SAVE":
			robot.keyPress(KeyEvent.VK_CONTROL+KeyEvent.VK_S);
			break;
		case "Enter":
			robot.keyPress(KeyEvent.VK_ALT);
			robot.keyPress(KeyEvent.VK_ENTER);
			System.out.println("********  Alt + Enter  Clicked****************");
			break;
		case "PAGEUP":
			break;
			
	
		
 		}
 		}
 		System.out.println(Event);
 		Thread.sleep(5000);
 		
 	}

     
     
     public static void SetDropdown(String[] identify, String val) {
         int i = 0;
         Exception Error = null;
         for (i = 0; i < identify.length; i++) {
             try {
                 if (Continue.get() == true) {
                     switch (i) {
                     case 0:
                         if (identify[0] != "") {
                             cDriver.get().findElement(By.xpath(identify[0])).click();
                             //cDriver.get().findElement(By.xpath(identify[0])).sendKeys(String.valueOf(val.charAt(0)));
                             Select pre_post1 = new Select(cDriver.get().findElement(By.xpath(identify[0])));
                                     pre_post1.selectByVisibleText(val);
                            
                             break;
                         } else {
                             throw new Exception(Error);
                         }
                     case 1:
                         if (identify[1] != "") {
                             cDriver.get().findElement(By.xpath(identify[1])).click();
                             //cDriver.get().findElement(By.xpath(identify[0])).sendKeys(String.valueOf(val.charAt(0)));
                             Select pre_post1 = new Select(cDriver.get().findElement(By.xpath(identify[1])));
                                     pre_post1.selectByVisibleText(val);
                            
                             break;
                         } else {
                             throw new Exception(Error);
                         }
                     case 2:
                         if (identify[2] != "") {
                             cDriver.get().findElement(By.xpath(identify[2])).click();
                             //cDriver.get().findElement(By.xpath(identify[0])).sendKeys(String.valueOf(val.charAt(0)));
                             Select pre_post1 = new Select(cDriver.get().findElement(By.xpath(identify[2])));
                                     pre_post1.selectByVisibleText(val);
                            
                             break;
                         } else {
                             throw new Exception(Error);
                         }
                     case 3:
                         if (identify[3] != "") {
                             cDriver.get().findElement(By.xpath(identify[3])).click();
                             //cDriver.get().findElement(By.xpath(identify[0])).sendKeys(String.valueOf(val.charAt(0)));
                             Select pre_post1 = new Select(cDriver.get().findElement(By.xpath(identify[3])));
                                     pre_post1.selectByVisibleText(val);
                            
                             break;
                         } else {
                             throw new Exception(Error);
                         }
                     case 4:
                         if (identify[4] != "") {
                             cDriver.get().findElement(By.xpath(identify[4])).click();
                             //cDriver.get().findElement(By.xpath(identify[0])).sendKeys(String.valueOf(val.charAt(0)));
                             Select pre_post1 = new Select(cDriver.get().findElement(By.xpath(identify[4])));
                                     pre_post1.selectByVisibleText(val);
                            
                             break;
                         } else {
                             throw new Exception(Error);
                         }
                     }
                 }
                 break;
             } catch (Exception e) {
                 i++;
                 Error = e;
                 Continue.set(true);
             }
         }
         if (i == identify.length) {
             Continue.set(false);
             Result.fUpdateLog("Object does't Exists to set drop value");
             Error.printStackTrace();
         }
     }

}