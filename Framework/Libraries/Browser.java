package Libraries;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Browser extends Driver {

	/*----------------------------------------------------------------------------------------------------------
	 * Class Name			: WebEdit 
	 * Use 					: Subclass of browser class represents the WebEdit in the application and 
	 * 						  contains functions for all the operations performed on web edit   
	 * Designed By			: AG
	 * Last Modified Date 	: 16-April-2017
	--------------------------------------------------------------------------------------------------------*/
	public static class WebEdit {
		public static void SetE(String objname, String objvalue) throws Exception {
			String objtype = "WebEdit";
			Common.ConditionalWait(objname, "WebEdit");
			String[] objprop = Utlities.FindObject(objname, objtype);
			Method.clearTD(objprop);
			Thread.sleep(200);
			Method.setETD(objprop, objvalue);

			if (Continue.get() == false) {
				Result.fUpdateLog(Batchs.get() + " :: Failed at Obj: " + objname + " - to set Value: " + objvalue);
				throw new Exception();
			} else {
				Result.fUpdateLog(Batchs.get() + " :: Action SetText on Obj: " + objname + " - Value: " + objvalue);
			}
		}

		
		
		public static void Set(String objname, String objvalue) throws Exception {
			String objtype = "WebEdit";
			Common.ConditionalWait(objname, "WebEdit");
			String[] objprop = Utlities.FindObject(objname, objtype);
			Method.clearTD(objprop);
			Thread.sleep(200);
			Method.setTD(objprop, objvalue);
			if (Continue.get() == false) {
				Result.fUpdateLog(Batchs.get() + " :: Failed at Obj: " + objname + " - to set Value: " + objvalue);
				throw new Exception();
			} else {
				Result.fUpdateLog(Batchs.get() + " :: Action SetText on Obj: " + objname + " - Value: " + objvalue);
			}
		}

		public static String gettext(String objname) throws Exception {
			String objtype = "WebEdit";
			Common.ConditionalWait(objname, objtype);
			String[] objprop = Utlities.FindObject(objname, objtype);
			return Method.getval(objprop);
		}

		public static void click(String objname) throws Exception {
			String objtype = "WebEdit";
			Common.ConditionalWait(objname, objtype);
			String[] objprop = Utlities.FindObject(objname, objtype);
			Method.clearTD(objprop);
			if (Continue.get() == false) {
				Result.fUpdateLog(Batchs.get() + " :: Failed at Obj: " + objname + " to Click");
				throw new Exception();
			} else {
				Result.fUpdateLog(Batchs.get() + " :: Action Click on Obj: " + objname);
			}
		}

		public static void clear(String objname) throws Exception {
			String objtype = "WebEdit";
			Common.ConditionalWait(objname, objtype);
			String[] objprop = Utlities.FindObject(objname, objtype);
			Method.clearTD(objprop);
			if (Continue.get() == false) {
				Result.fUpdateLog(Batchs.get() + " :: Failed at Obj: " + objname + " to Clear");
				throw new Exception();
			} else {
				Result.fUpdateLog(Batchs.get() + " :: Action Clear on Obj: " + objname);
			}
		}
		
		

		public static void Doubleclick(String objname) throws Exception {
			Common.ConditionalWait(objname, "WebEdit");
			String[] objprop = Utlities.FindObject(objname, "WebEdit");
			String Xpath = objprop[0];
			org.openqa.selenium.WebElement element = cDriver.get().findElement(By.xpath(Xpath));
			((JavascriptExecutor) cDriver.get()).executeScript("arguments[0].scrollIntoView();", element);
			Actions action = new Actions(cDriver.get()).doubleClick(element);
			action.build().perform();
		}

		public static boolean exist(String objname) throws Exception {
			String objtype = "WebEdit";
			String[] objprop = Utlities.FindObject(objname, objtype);
			return Method.existobj(objprop);
		}

		public static void waittillvisible(String objname) throws Exception {
			String objtype = "WebEdit";
			String[] objprop = Utlities.FindObject(objname, objtype);
			Method.waittillobjvisible(objprop);
			if (Continue.get() == false) {
				Result.fUpdateLog(Batchs.get() + " :: Failed at Obj: " + objname + " - is not Visible");
				throw new Exception();
			} else {
				Result.fUpdateLog(Batchs.get() + " :: Action at Obj: " + objname + " - is Visible");
			}
		}

		public static boolean waitTillEnabled(String objname) throws Exception {
			String objtype = "WebEdit";
			String[] objprop = Utlities.FindObject(objname, objtype);
			return Method.Methodwaittillenabled(objprop);

		}

		public static Boolean CheckDisabled(String objname) throws Exception {
			String objtype = "WebEdit";
			String[] objprop = Utlities.FindObject(objname, objtype);
			String cellXpath = objprop[0];
			org.openqa.selenium.WebElement scr1 = cDriver.get().findElement(By.xpath(cellXpath));
			((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)", scr1);
			if (cDriver.get().findElement(By.xpath(cellXpath)).isDisplayed()
					& ((cDriver.get().findElement(By.xpath(cellXpath)).getAttribute("readonly")).equals("readonly"))) {
				Result.fUpdateLog(Batchs.get() + " :: Action at Obj: " + objname + " - is Disabled");
				return true;
			} else {
				Result.fUpdateLog(Batchs.get() + " :: Failed at Obj: " + objname + " - is not Disabled");
				return false;
			}
		}

		public static void doSignature(String objname){
			String objtype = "WebEdit";
			String[] objprop = Utlities.FindObject(objname,objtype);
			Method.getAction(objprop);	
		}
		 
		public static void Highlight(String objname) throws Exception
		{
			String objtype = "WebEdit";
			Common.ConditionalWait(objname, "WebEdit");
			String[] objprop = Utlities.FindObject(objname, objtype);
			System.out.println(objprop[0]);
			System.out.println(objprop[1]);
			Method.clearTD(objprop);
			Thread.sleep(200);
		//	Method.setTD(objprop, objvalue);
			//org.openqa.selenium.WebElement element = cDriver.get().findElement(By.xpath(cellXpath));
			JavascriptExecutor js = (JavascriptExecutor) cDriver;
			 js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", objprop[1]);
			
			if (Continue.get() == false) {
				Result.fUpdateLog(Batchs.get() + " :: Failed at Obj: " + objname + " - is not Visible");
				throw new Exception();
			} else {
				Result.fUpdateLog(Batchs.get() + " :: Action at Obj: " + objname + " - is Visible");
			}
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Class Name			: WebButton 
	 * Use 					: Subclass of browser class represents the WebButton in the application and 
	 * 						  contains functions for all the operations performed on web edit   
	 * Designed By			: AG
	 * Last Modified Date 	: 25-Apr-2016
	--------------------------------------------------------------------------------------------------------*/
	public static class WebButton {
		public static void click(String objname) throws Exception {
			String objtype = "WebButton";
			Common.ConditionalWait(objname, objtype);

			String[] objprop = Utlities.FindObject(objname, objtype);
			Method.clickTD(objprop);

			if (Continue.get() == false) {
				Result.fUpdateLog(Batchs.get() + " :: Failed at Obj: " + objname + " to click");
				throw new Exception();
			} else {
				Result.fUpdateLog(Batchs.get() + " :: Action Click on Obj: " + objname);
			}
		}

		public static void waittillvisible(String objname) throws Exception {
			String objtype = "WebButton";
			String[] objprop = Utlities.FindObject(objname, objtype);
			Method.waittillobjvisible(objprop);
			if (Continue.get() == false) {
				Result.fUpdateLog(Batchs.get() + " :: Failed at Obj: " + objname + " - is not Visible");
				throw new Exception();
			} else {
				Result.fUpdateLog(Batchs.get() + " :: Action at Obj: " + objname + " - is Visible");
			}
		}

		public static boolean exist(String objname) throws Exception {
			String objtype = "WebButton";
			String[] objprop = Utlities.FindObject(objname, objtype);
			return Method.existobj(objprop);
		}

		public static boolean waitTillEnabled(String objname) throws Exception {
			String objtype = "WebButton";
			String[] objprop = Utlities.FindObject(objname, objtype);
			return Method.Methodwaittillenabled(objprop);
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Class Name			: WebLink 
	 * Use 					: Subclass of browser class represents the WebLink in the application and 
	 * 						  contains functions for all the operations performed on web edit   
	 * Designed By			: AG
	 * Last Modified Date 	: 25-Apr-2016
	--------------------------------------------------------------------------------------------------------*/
	public static class WebLink {
		public static void click(String objname) throws Exception {
			String objtype = "WebLink";
			Common.ConditionalWait(objname, objtype);
			String[] objprop = Utlities.FindObject(objname, objtype);
			System.out.println(objprop[0]);
			System.out.println(objprop[1]);
			System.out.println(objprop);
			Method.clickTD(objprop);
			if (Continue.get() == false) {
				Result.fUpdateLog(Batchs.get() + " :: Failed at Obj: " + objname + " to click");
				throw new Exception();
			} else {
				Result.fUpdateLog(Batchs.get() + " :: Action Click on Obj: " + objname);
			}
		}

		public static void clickL(String objname, int R) throws Exception {
			Common.ConditionalWait(objname, "WebLink");
			String[] objprop = Utlities.FindObject(objname, "WebLink");
			String cellXpath = objprop[0] + "//div[" + R + "]//div[1]/a";
			cDriver.get().findElement(By.xpath(cellXpath)).click();
			if (Continue.get() == false) {
				Result.fUpdateLog(Batchs.get() + " :: Failed at Obj: " + objname + " to clickL");
				throw new Exception();
			} else {
				Result.fUpdateLog(Batchs.get() + " :: Action ClickL on Obj: " + objname);
			}
		}

		public static boolean exist(String objname) throws Exception {
			String objtype = "WebLink";
			String[] objprop = Utlities.FindObject(objname, objtype);
			return Method.existobj(objprop);
		}

		public static void waittillvisible(String objname) throws Exception {
			String objtype = "WebLink";
			String[] objprop = Utlities.FindObject(objname, objtype);
			Method.waittillobjvisible(objprop);
			if (Continue.get() == false) {
				Result.fUpdateLog(Batchs.get() + " :: Failed at Obj: " + objname + " - is not Visible");
				throw new Exception();
			} else {
				Result.fUpdateLog(Batchs.get() + " :: Action at Obj: " + objname + " - is Visible");
			}
		}

		public static boolean waitTillEnabled(String objname) throws Exception {
			String objtype = "WebLink";
			String[] objprop = Utlities.FindObject(objname, objtype);
			return Method.Methodwaittillenabled(objprop);
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Class Name			: ListBox 
	 * Use 					: Subclass of browser class represents the ListBox in the application and 
	 * 						  contains functions for all the operations performed on web edit   
	 * Designed By			: AG
	 * Last Modified Date 	: 25-Apr-2016
	--------------------------------------------------------------------------------------------------------*/
	public static class ListBox {
		public static void setdropvalue(String objname, String objvalue) throws Exception {
			String objtype = "ListBox";
			Common.ConditionalWait(objname, objtype);
			String[] objprop = Utlities.FindObject(objname, objtype);
			Method.setdropvalue(objprop, objvalue);
			if (Continue.get() == false) {
				Result.fUpdateLog(Batchs.get() + " :: Failed at Obj: " + objname + " - to set drop Value: " + objvalue);
				throw new Exception();
			} else {
				Result.fUpdateLog(
						Batchs.get() + " :: Action SetDropValue on Obj: " + objname + " - Value: " + objvalue);
			}
		}

		public static void select(String objname, String objvalue) throws Exception {
			String objtype = "ListBox";
			Common.ConditionalWait(objname, objtype);
			String[] objprop = Utlities.FindObject(objname, objtype);
			Thread.sleep(100);
			//Method.clearTD(objprop);
			Thread.sleep(100);
			Method.selectTD(objprop, objvalue);
			if (Continue.get() == false) {
				Result.fUpdateLog(Batchs.get() + " :: Failed at Obj: " + objname + " - to select Value: " + objvalue);
				throw new Exception();
			} else {
				Result.fUpdateLog(Batchs.get() + " :: Action select on Obj: " + objname + " - Value: " + objvalue);
			}
		}

		public static void click(String objname) throws Exception {
			String objtype = "ListBox";
			Common.ConditionalWait(objname, objtype);
			String[] objprop = Utlities.FindObject(objname, objtype);
			Method.clickTD(objprop);
			if (Continue.get() == false) {
				Result.fUpdateLog(Batchs.get() + " :: Failed at Obj: " + objname + " to click");
				throw new Exception();
			} else {
				Result.fUpdateLog(Batchs.get() + " :: Action Click on Obj: " + objname);
			}
		}

		public static void clear(String objname) throws Exception {
			String objtype = "ListBox";
			Common.ConditionalWait(objname, objtype);
			String[] objprop = Utlities.FindObject(objname, objtype);
			Method.clearTD(objprop);
			if (Continue.get() == false) {
				Result.fUpdateLog(Batchs.get() + " :: Failed at Obj: " + objname + " to Clear");
				throw new Exception();
			} else {
				Result.fUpdateLog(Batchs.get() + " :: Action Clear on Obj: " + objname);
			}
		}

		public static String gettext(String objname) throws Exception {
			String objtype = "ListBox";
			String[] objprop = Utlities.FindObject(objname, objtype);
			return Method.getval(objprop);
		}

		public static boolean exist(String objname) throws Exception {
			String objtype = "ListBox";
			String[] objprop = Utlities.FindObject(objname, objtype);
			return Method.existobj(objprop);
		}

		public static boolean waitTillEnabled(String objname) throws Exception {
			String objtype = "ListBox";
			String[] objprop = Utlities.FindObject(objname, objtype);
			return Method.Methodwaittillenabled(objprop);
		}

		public static void waittillvisible(String objname) throws Exception {
			String objtype = "ListBox";
			String[] objprop = Utlities.FindObject(objname, objtype);
			Method.waittillobjvisible(objprop);
			if (Continue.get() == false) {
				Result.fUpdateLog(Batchs.get() + " :: Failed at Obj: " + objname + " - is not Visible");
				throw new Exception();
			} else {
				Result.fUpdateLog(Batchs.get() + " :: Action at Obj: " + objname + " - is Visible");
			}
		}

		/*---------------------------------------------------------------------------------------------------------
		 * Class Name			: ListBox 
		 * Use 					: To get all values from the list box    
		 * Designed By			: Sisir
		 * Last Modified Date 	: 07-Mar-2019
		--------------------------------------------------------------------------------------------------------*/
		// sisira
		public static ArrayList<String> listAllItems(String objname) throws Exception {
			String objtype = "ListBox";
			Common.ConditionalWait(objname, objtype);
			String[] objprop = Utlities.FindObject(objname, objtype);
			Thread.sleep(100);

			if (Continue.get() == false) {
				Result.fUpdateLog(Batchs.get() + " :: Failed at Obj: " + objname);
				throw new Exception();
			} else {
				Result.fUpdateLog(Batchs.get() + " :: Action select on Obj: " + objname);
			}
			return Method.allItems(objprop);
		}

		/*---------------------------------------------------------------------------------------------------------
						 * Class Name			: ListBox
						 * Use 					:   To Put data on the select with out clearing the data
						 * Designed By			: Sisir
						 * Last Modified Date 	: 07-Mar-2019
						--------------------------------------------------------------------------------------------------------*/

		public static void listselect(String objname, String objvalue) throws Exception {
			String objtype = "ListBox";
			//Common.ConditionalWait(objname, objtype);
			String[] objprop = Utlities.FindObject(objname, objtype);
			Thread.sleep(100);
			// Method.clearTD(objprop);
			Thread.sleep(100);
			Method.selectTD(objprop, objvalue);
			if (Continue.get() == false) {
				Result.fUpdateLog(Batchs.get() + " :: Failed at Obj: " + objname + " - to select Value: " + objvalue);
				throw new Exception();
			} else {
				Result.fUpdateLog(Batchs.get() + " :: Action select on Obj: " + objname + " - Value: " + objvalue);
			}
		}
		
		public static void SelectDropdown(String objname, String objvalue) throws Exception {
            String objtype = "ListBox";
            Common.ConditionalWait(objname, objtype);
            String[] objprop = Utlities.FindObject(objname, objtype);
            Method.SetDropdown(objprop, objvalue);
            if (Continue.get() == false) {
                Result.fUpdateLog(Batchs.get() + " :: Failed at Obj: " + objname + " - to set drop Value: " + objvalue);
                throw new Exception();
            } else {
                Result.fUpdateLog(
                        Batchs.get() + " :: Action SetDropValue on Obj: " + objname + " - Value: " + objvalue);
            }
        }

	}

	/*---------------------------------------------------------------------------------------------------------
	 * Class Name			: WebElement 
	 * Use 					: Subclass of browser class represents the WebElement in the application and 
	 * 						  contains functions for all the operations performed on web edit   
	 * Designed By			: AG
	 * Last Modified Date 	: 25-Apr-2016
	--------------------------------------------------------------------------------------------------------*/
	public static class WebElement {
		public static void click(String objname) throws Exception {
			String objtype = "WebElement";
			Common.ConditionalWait(objname, objtype);
			String[] objprop = Utlities.FindObject(objname, objtype);
			Method.clickTD(objprop);
			if (Continue.get() == false) {
				Result.fUpdateLog(Batchs.get() + " :: Failed at Obj: " + objname + " to click");
				throw new Exception();
			} else {
				Result.fUpdateLog(Batchs.get() + " :: Action Click on Obj: " + objname);
			}
		}

		public static void waittillvisible(String objname) throws Exception {
			String objtype = "WebElement";
			String[] objprop = Utlities.FindObject(objname, objtype);
			Method.waittillobjvisible(objprop);
			if (Continue.get() == false) {
				Result.fUpdateLog(Batchs.get() + " :: Failed at Obj: " + objname + " - is not Visible");
				throw new Exception();
			} else {
				Result.fUpdateLog(Batchs.get() + " :: Action at Obj: " + objname + " - is Visible");
			}
		}

		public static String gettext(String objname) throws Exception {
			String objtype = "WebElement";
			Common.ConditionalWait(objname, objtype);
			String[] objprop = Utlities.FindObject(objname, objtype);
			return Method.getval(objprop);
		}
		
		
		public static boolean exist(String objname) throws Exception {
			String objtype = "WebElement";
			String[] objprop = Utlities.FindObject(objname, objtype);
			return Method.existobj(objprop);
		}

		public static boolean waitTillEnabled(String objname) throws Exception {
			String objtype = "WebElement";
			String[] objprop = Utlities.FindObject(objname, objtype);
			return Method.Methodwaittillenabled(objprop);
		}

		public static void select(String objname, String objvalue) throws Exception {
			String objtype = "WebElement";
			Common.ConditionalWait(objname, objtype);
			String[] objprop = Utlities.FindObject(objname, objtype);
			Method.selectTD(objprop, objvalue);
			if (Continue.get() == false) {
				Result.fUpdateLog(Batchs.get() + " :: Failed at Obj: " + objname + " - to select Value: " + objvalue);
				throw new Exception();
			} else {
				Result.fUpdateLog(Batchs.get() + " :: Action select on Obj: " + objname + " - Value: " + objvalue);
			}
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Class Name			: WebRadioButton 
	 * Use 					: Subclass of browser class represents the WebRadioButton in the application and 
	 * 						  contains functions for all the operations performed on web edit   
	 * Designed By			: AG
	 * Last Modified Date 	: 25-Apr-2016
	--------------------------------------------------------------------------------------------------------*/
	public static class WebRadioButton {
		public static void click(String objname) throws Exception {
			String objtype = "RadioButton";
			Common.ConditionalWait(objname, objtype);
			String[] objprop = Utlities.FindObject(objname, objtype);
			Method.clickTD(objprop);
			if (Continue.get() == false) {
				Result.fUpdateLog(Batchs.get() + " :: Failed at Obj: " + objname + " to click");
				throw new Exception();
			} else {
				Result.fUpdateLog(Batchs.get() + " :: Action Click on Obj: " + objname);
			}
		}

		public static boolean exist(String objname) throws Exception {
			String objtype = "RadioButton";
			String[] objprop = Utlities.FindObject(objname, objtype);
			return Method.existobj(objprop);
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Class Name			: WebCheckBox 
	 * Use 					: Subclass of browser class represents the WebRadioButton in the application and 
	 * 						  contains functions for all the operations performed on web edit   
	 * Designed By			: AG
	 * Last Modified Date 	: 25-Apr-2016
	--------------------------------------------------------------------------------------------------------*/
	public static class WebCheckBox {
		public static void click(String objname) throws Exception {
			String objtype = "CheckBox";
			Common.ConditionalWait(objname, objtype);
			String[] objprop = Utlities.FindObject(objname, objtype);
			Method.clickTD(objprop);
			if (Continue.get() == false) {
				Result.fUpdateLog(Batchs.get() + " :: Failed at Obj: " + objname + " to Check box");
				throw new Exception();
			} else {
				Result.fUpdateLog(Batchs.get() + " :: Action CheckBox on Obj: " + objname);
			}
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Class Name			: WebTable 
	 * Use 					: Subclass of browser class represents the WebTabel in the application and 
	 * 						  contains functions for all the operations performed on Web Table 
	 * Designed By			: AG
	 * Last Modified Date 	: 25-Apr-2016
	--------------------------------------------------------------------------------------------------------*/
	public static class WebTable {
		/*------------------------------------------------------------------------------------------------------
		* Function Name: Expand
		* Use :	To Expand the contents in specific Row
		* Designed By: Vinodhini
		* Last Modified Date : 15-June-2016
		--------------------------------------------------------------------------------------------------------*/
		public static void Expand(String objname, int rownum) throws Exception {
			String[] objprop = Utlities.FindObject(objname, "WebTable");
			String[] Expand = Utlities.FindObject("Expand", "WebButton");
			String cellXpath = objprop[0] + "//tr[" + rownum + "]" + Expand[0];
			if (cDriver.get().findElement(By.xpath(cellXpath)).isDisplayed()) {
				cDriver.get().findElement(By.xpath(cellXpath)).click();
			}
		}

		/*------------------------------------------------------------------------------------------------------
		* Function Name: scroll
		* Use :	Scrolls the particular cell in the given row and column of the webtable
		* Designed By: Vinodhini
		* Last Modified Date : 15-June-2016
		--------------------------------------------------------------------------------------------------------*/
		public static void scroll(String objname, int rownum, int columnnum) throws Exception {
			String[] objprop = Utlities.FindObject(objname, "WebTable");
			String cellXpath = objprop[0] + "//tr[" + rownum + "]/td[" + (columnnum + 1) + "]";
			cDriver.get().findElement(By.xpath(cellXpath)).click();
			WebElement scr1 = (WebElement) cDriver.get().findElement(By.xpath(cellXpath));
			((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)", scr1);
		}

		/*------------------------------------------------------------------------------------------------------
		* Function Name: getRowCount
		* Use :	returns the total number of rows in the webtable
		* Designed By: AG
		* Last Modified Date : 15-June-2016
		--------------------------------------------------------------------------------------------------------*/
		public static int getRowCount(String objname) throws Exception {
			try {
				Common.ConditionalWait(objname, "WebTable");
				String[] objprop = Utlities.FindObject(objname, "WebTable");
				String cellXpath = objprop[0] + "//tr";
				List<org.openqa.selenium.WebElement> rows = cDriver.get().findElements(By.xpath(cellXpath));
				int rowcount = rows.size();
				Result.fUpdateLog(Batchs.get() + " :: Action getRowCount on Obj: " + objname);
				return rowcount;
			} catch (Exception e) {
				Result.fUpdateLog(Batchs.get() + " :: Failed at Obj: " + objname + " to getRowCount");
				throw new Exception();
			}

		}

		public static int getRowCount1(String objname) throws Exception {
			try {
				String[] objprop = Utlities.FindObject(objname, "WebTable");
				String cellXpath = objprop[0] + "//tr";
				List<org.openqa.selenium.WebElement> rows = cDriver.get().findElements(By.xpath(cellXpath));
				int rowcount = rows.size();
				Result.fUpdateLog(Batchs.get() + " :: Action getRowCount on Obj: " + objname);
				return rowcount;
			} catch (Exception e) {
				Result.fUpdateLog(Batchs.get() + " :: Failed at Obj: " + objname + " to getRowCount");
				throw new Exception();
			}

		}

		public static boolean exist(String objname) throws Exception {
			String objtype = "WebTable";
			String[] objprop = Utlities.FindObject(objname, objtype);
			return Method.existobj(objprop);
		}

		public static boolean waitTillEnabled(String objname) throws Exception {
			String objtype = "WebTable";
			String[] objprop = Utlities.FindObject(objname, objtype);
			return Method.Methodwaittillenabled(objprop);
		}

		/*------------------------------------------------------------------------------------------------------
		* Function Name: getCellData
		* Use :	returns the value in the given row and column of the web table
		* Designed By: AG
		* Modified By: Vinodhini
		* Last Modified Date : 13-Feb-2017
		--------------------------------------------------------------------------------------------------------*/
		public static String getCellData(String objname, int rownum, int columnnum) throws Exception {
			try {
				Common.ConditionalWait(objname, "WebTable");
				String[] objprop = Utlities.FindObject(objname, "WebTable");

				String cellXpath = objprop[0] + "//tr[" + rownum + "]" + "//td[" + columnnum + "]";
				Thread.sleep(100);
				String celldata = cDriver.get().findElement(By.xpath(cellXpath)).getText();
				Result.fUpdateLog(Batchs.get() + " :: Action getCellData on Obj: " + objname);
				return celldata;
			} catch (Exception e) {
				Result.fUpdateLog(Batchs.get() + " :: Failed at Obj: " + objname + " to getCellData");
				throw new Exception();
			}

		}

		public static String getCellData_title(String objname, int rownum, int columnnum) throws Exception {
			try {
				Common.ConditionalWait(objname, "WebTable");
				String[] objprop = Utlities.FindObject(objname, "WebTable");

				String cellXpath = objprop[0] + "//tr[" + rownum + "]" + "//td[" + columnnum + "]";
				String celldata = cDriver.get().findElement(By.xpath(cellXpath)).getAttribute("title");
				Result.fUpdateLog(Batchs.get() + " :: Action getCellData_title on Obj: " + objname);
				return celldata;
			} catch (Exception e) {
				Result.fUpdateLog(Batchs.get() + " :: Failed at Obj: " + objname + " to getCellData_title");
				throw new Exception();
			}

		}

		public static String CellData(String objname, int rownum) throws Exception {
			try {
				Common.ConditionalWait(objname, "WebTable");
				String[] objprop = Utlities.FindObject(objname, "WebTable");
				String celldata = "";
				int a = Browser.WebTable.getColCount(objname);
				System.out.println(a);
				for (int columnnum = 1; columnnum <= a; columnnum++) {
					String cellXpath = objprop[0] + "//tr[" + rownum + "]" + "//td[" + columnnum + "]";					
					celldata = cDriver.get().findElement(By.xpath(cellXpath)).getText();
					System.out.println(celldata);
				}
				Result.fUpdateLog(Batchs.get() + " :: Action CellData on Obj: " + objname);
				return celldata;
			} catch (Exception e) {
				Result.fUpdateLog(Batchs.get() + " :: Failed at Obj: " + objname + " to CellData");
				throw new Exception();
			}

		}

		public static boolean DataSearch(String objname, String Data) throws Exception {
			boolean status = true;
			Common.ConditionalWait(objname, "WebTable");
			String[] objprop = Utlities.FindObject(objname, "WebTable");
			String cellXpath = objprop[0] + "/tr/td";
			System.out.println(cellXpath);
			List<org.openqa.selenium.WebElement> celldata = cDriver.get().findElements(By.xpath(cellXpath));
			for (org.openqa.selenium.WebElement data : celldata) {
				System.out.println(data.getText());
				if (data.getText().toString().equalsIgnoreCase(Data)) {
					status = true;
					System.out.println("True");
				} else {
					status = false;
				}
			}
			return status;

		}

		/*------------------------------------------------------------------------------------------------------
		* Function Name: CommentE
		* Use : Sets the Specified value to the Comment cell
		* Designed By: Vinodhini
		* Last Modified Date : 13-January-2017
		--------------------------------------------------------------------------------------------------------*/
		public static void CommentE(String objname, int rownum, int columnnum, String obj, String Val)
				throws Exception {
			try {
				Common.ConditionalWait(objname, "WebTable");
				String[] objprop = Utlities.FindObject(objname, "WebTable");
				String cellXpath = objprop[0] + "//tr[" + rownum + "]//td[" + columnnum + "]";
				cDriver.get().findElement(By.xpath(cellXpath)).click();
				String cellXpath1 = objprop[0] + "//tr[" + rownum + "]//td[" + columnnum + "]//textarea[@name='" + obj
						+ "']";
				Thread.sleep(200);
				cDriver.get().findElement(By.xpath(cellXpath1)).clear();
				Thread.sleep(200);
				String vis = "false";
				int countval = 1;
				while (vis == "false" || countval < 10000)
					if (cDriver.get().findElement(By.xpath(cellXpath1)).isDisplayed()) {
						cDriver.get().findElement(By.xpath(cellXpath1)).sendKeys(Val);
						vis = "true";
						countval = 10000;
					} else {
						countval++;
						Thread.sleep(10);
					}
				Result.fUpdateLog(Batchs.get() + " :: Action SetDataE on Obj: " + objname);
			} catch (Exception e) {
				Result.fUpdateLog(Batchs.get() + " :: Failed at Obj: " + objname + " to SetDataE");
				throw new Exception();
			}

		}

		/*------------------------------------------------------------------------------------------------------
		* Function Name: click
		* Use :	Clicks the given row and column of the webtable
		* Designed By: AG
		* Last Modified Date : 15-June-2016
		--------------------------------------------------------------------------------------------------------*/
		public static void click(String objname, int rownum, int columnnum) throws Exception {
			try {
				Common.ConditionalWait(objname, "WebTable");
				String[] objprop = Utlities.FindObject(objname, "WebTable");
				String cellXpath = objprop[0] + "//tr[" + rownum + "]/td[" + columnnum + "]";
				cDriver.get().findElement(By.xpath(cellXpath)).click();
				Result.fUpdateLog(Batchs.get() + " :: Action Click on Obj: " + objname);
			} catch (Exception e) {
				Result.fUpdateLog(Batchs.get() + " :: Failed at Obj: " + objname + " to click");
				throw new Exception();
			}
		}
		
		
		/*------------------------------------------------------------------------------------------------------
		* Function Name: click&SendKeys(
		* Use :	Clicks the given row and column of the webtable
		* Designed By:
		* Last Modified Date : 
		--------------------------------------------------------------------------------------------------------*/
		public static void click_SendKeys(String objname, int rownum, int columnnum,String obj,String data) throws Exception {
			try {
				Common.ConditionalWait(objname, "WebTable");
				String[] objprop = Utlities.FindObject(objname, "WebTable");
				String cellXpath1 = objprop[0] + "//tr[" + rownum + "]//td[" + columnnum + "]";
				cDriver.get().findElement(By.xpath(cellXpath1)).click();
				Thread.sleep(5000);
				String cellXpath2 = objprop[0] + "//tr[" + rownum + "]//td[" + columnnum + "]//input[@name='" + obj
						+ "']";
//				cDriver.get().manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				cDriver.get().findElement(By.xpath(cellXpath2)).clear();
				Thread.sleep(5000);
			    cDriver.get().findElement(By.xpath(cellXpath2)).sendKeys(data);
				cDriver.get().findElement(By.xpath(cellXpath2)).sendKeys(Keys.ENTER);
				
				Thread.sleep(2000);
				
				Result.fUpdateLog(Batchs.get() + " :: Action Click on Obj: " + objname);
			} catch (Exception e) {
				Result.fUpdateLog(Batchs.get() + " :: Failed at Obj: " + objname + " to click");
				throw new Exception();
			}
		}

		public static void click_che(String objname, int rownum, int columnnum) throws Exception {
			try {
				Common.ConditionalWait(objname, "WebTable");
				String[] objprop = Utlities.FindObject(objname, "WebTable");
				String cellXpath = objprop[0] + "//tr[" + rownum + "]/td[" + columnnum + "]/span/input";
				cDriver.get().findElement(By.xpath(cellXpath)).click();
				Result.fUpdateLog(Batchs.get() + " :: Action Click on Obj: " + objname);
			} catch (Exception e) {
				Result.fUpdateLog(Batchs.get() + " :: Failed at Obj: " + objname + " to click");
				throw new Exception();
			}
		}

		public static void clickA(String objname, int rownum, int columnnum) throws Exception {
			try {
				Common.ConditionalWait(objname, "WebTable");
				String[] objprop = Utlities.FindObject(objname, "WebTable");
				String cellXpath = objprop[0] + "//tr[" + rownum + "]//td[" + columnnum + "]//a";
				cDriver.get().findElement(By.xpath(cellXpath)).click();
				Result.fUpdateLog(Batchs.get() + " :: Action Click on Obj: " + objname);
			} catch (Exception e) {
				Result.fUpdateLog(Batchs.get() + " :: Failed at Obj: " + objname + " to click");
				throw new Exception();
			}
		}
		public static void click_S(String objname, int rownum, int columnnum) throws Exception {
			try {
				Common.ConditionalWait(objname, "WebTable");
				String[] objprop = Utlities.FindObject(objname, "WebTable");
				String cellXpath = objprop[0] + "//tr[" + rownum + "]/td[" + columnnum + "]/span";
				cDriver.get().findElement(By.xpath(cellXpath)).click();
				Result.fUpdateLog(Batchs.get() + " :: Action Click on Obj: " + objname);
			} catch (Exception e) {
				Result.fUpdateLog(Batchs.get() + " :: Failed at Obj: " + objname + " to click");
				throw new Exception();
			}
		}
		
		
		/*------------------------------------------------------------------------------------------------------
		* Function Name: clickL
		* Use :	Clicks the given row and column of the webtable
		* Designed By: Vinodhini
		* Last Modified Date : 07-March-2017
		--------------------------------------------------------------------------------------------------------*/
		public static void clickL(String objname, int rownum, int columnnum) throws Exception {
			try {
				Common.ConditionalWait(objname, "WebTable");
				String[] objprop = Utlities.FindObject(objname, "WebTable");
				String cellXpath = objprop[0] + "//tr[" + rownum + "]//td[" + (columnnum + 1) + "]";
				cDriver.get().findElement(By.xpath(cellXpath)).click();
				String cellXpath1 = objprop[0] + "//tr[" + rownum + "]//td[" + columnnum + "]//a";
				cDriver.get().findElement(By.xpath(cellXpath1)).click();
				Result.fUpdateLog(Batchs.get() + " :: Action Click on Obj: " + objname);
			} catch (Exception e) {
				Result.fUpdateLog(Batchs.get() + " :: Failed at Obj: " + objname + " to clickL");
				throw new Exception();
			}
		}

		/*------------------------------------------------------------------------------------------------------
		* Function Name: clickMigrationExpTreeIcon  
		* Use :	Clicks the given row and column of the webtable (For Post TO Pre Migration)
		* Designed By: AG
		* Last Modified Date : 15-June-2016
		--------------------------------------------------------------------------------------------------------*/
		public static void clickMigrationExpTreeIcon(String objname, int rownum, int columnnum) throws Exception {
			try {
				Common.ConditionalWait(objname, "WebTable");
				String[] objprop = Utlities.FindObject(objname, "WebTable");
				String cellXpath = objprop[0] + "//tr[" + rownum + "]/td[" + columnnum + "]/div/div";
				cDriver.get().findElement(By.xpath(cellXpath)).click();
				Result.fUpdateLog(Batchs.get() + " :: Action Click on Obj: " + objname);
			} catch (Exception e) {
				Result.fUpdateLog(Batchs.get() + " :: Failed at Obj: " + objname + " to click");
				throw new Exception();
			}
		}
		
		
		
		
		
		/*------------------------------------------------------------------------------------------------------
		* Function Name: getColCount
		* Use : get the column count of the given web table
		* Designed By: AG
		* Last Modified Date : 15-June-2016
		--------------------------------------------------------------------------------------------------------*/
		public static int getColCount(String objname) throws Exception {
			try {
				Common.ConditionalWait(objname, "WebTable");
				String[] objprop = Utlities.FindObject(objname, "WebTable");
				String cellXpath = objprop[0] + "//tr[1]//td";
				List<org.openqa.selenium.WebElement> cols = cDriver.get().findElements(By.xpath(cellXpath));
				int colcount = cols.size();
				Result.fUpdateLog(Batchs.get() + " :: Action getColCount on Obj: " + objname);
				return colcount;
			} catch (Exception e) {
				Result.fUpdateLog(Batchs.get() + " :: Failed at Obj: " + objname + " to getColCount");
				throw new Exception();
			}
		}

		public static int getColCount1(String objname) throws Exception {
			try {
				Common.ConditionalWait(objname, "WebTable");
				String[] objprop = Utlities.FindObject(objname, "WebTable");
				String cellXpath = objprop[0] + "//tr[1]//th";
				List<org.openqa.selenium.WebElement> cols = cDriver.get().findElements(By.xpath(cellXpath));
				int colcount = cols.size();
				Result.fUpdateLog(Batchs.get() + " :: Action getColCount on Obj: " + objname);
				return colcount;
			} catch (Exception e) {
				Result.fUpdateLog(Batchs.get() + " :: Failed at Obj: " + objname + " to getColCount");
				throw new Exception();
			}
		}

		/*------------------------------------------------------------------------------------------------------
		* Function Name: getColumnname
		* Use : get the column Name for the row and column
		* Designed By: AG
		* Last Modified Date : 15-June-2016
		--------------------------------------------------------------------------------------------------------*/
		public static String getColumnname(String objname, int rownum, int columnnum) throws Exception {
			try {
				Common.ConditionalWait(objname, "WebTable");
				String[] objprop = Utlities.FindObject(objname, "WebTable");
				String cellXpath = objprop[0] + "//tr[" + rownum + "]//th[" + columnnum + "]";
				String celldata = cDriver.get().findElement(By.xpath(cellXpath)).getText();
				Result.fUpdateLog(Batchs.get() + " :: Action getColumnname on Obj: " + objname);
				return celldata;
			} catch (Exception e) {
				Result.fUpdateLog(Batchs.get() + " :: Failed at Obj: " + objname + " to getColumnname");
				throw new Exception();
			}
		}

		/*------------------------------------------------------------------------------------------------------
		* Function Name: waittillvisible
		* Use : Waits till the web table is visible
		* Designed By: AG
		* Last Modified Date : 15-June-2016
		--------------------------------------------------------------------------------------------------------*/
		public static void waittillvisible(String objname) throws Exception {
			String objtype = "WebTable";
			String[] objprop = Utlities.FindObject(objname, objtype);
			Method.waittillobjvisible(objprop);
		}

		public static void SetTableData(String objname) throws Exception {
			Common.ConditionalWait(objname, "WebTable");
			String objtype = "WebTable";
			String[] objprop = Utlities.FindObject(objname, objtype);
			Method.waittillobjvisible(objprop);

		}

		public static void clickT(String objname) throws Exception {
			try {
				Common.ConditionalWait(objname, "WebTable");
				String[] objprop = Utlities.FindObject(objname, "WebTable");
				cDriver.get().findElement(By.xpath(objprop[0])).click();
				// cDriver.get().findElement(By.xpath(objprop[0])).click();
				Result.fUpdateLog(Batchs.get() + " :: Action clickT on Obj: " + objname);
			} catch (Exception e) {
				Result.fUpdateLog(Batchs.get() + " :: Failed at Obj: " + objname + " to clickT");
				throw new Exception();
			}
		}

		/*------------------------------------------------------------------------------------------------------
		* Function Name: SetDataE
		* Use : Sets the Specified value to the cell
		* Designed By: Vinodhini
		* Last Modified Date : 13-January-2017
		--------------------------------------------------------------------------------------------------------*/
		public static void SetDataE(String objname, int rownum, int columnnum, String obj, String Val)
				throws Exception {
			try {
				Common.ConditionalWait(objname, "WebTable");
				String[] objprop = Utlities.FindObject(objname, "WebTable");
				String cellXpath = objprop[0] + "//tr[" + rownum + "]//td[" + columnnum + "]";
				cDriver.get().findElement(By.xpath(cellXpath)).click();
				String cellXpath1 = objprop[0] + "//tr[" + rownum + "]//td[" + columnnum + "]//input[@name='" + obj
						+ "']";
				Thread.sleep(200);
				cDriver.get().findElement(By.xpath(cellXpath1)).clear();
				Thread.sleep(200);
				String vis = "false";
				int countval = 1;
				while (vis == "false" || countval < 10000)
					if (cDriver.get().findElement(By.xpath(cellXpath1)).isDisplayed()) {
						cDriver.get().findElement(By.xpath(cellXpath1)).sendKeys(Val);
						vis = "true";
						countval = 10000;
					} else {
						countval++;
						Thread.sleep(10);
					}
				Result.fUpdateLog(Batchs.get() + " :: Action SetDataE on Obj: " + objname);
			} catch (Exception e) {
				Result.fUpdateLog(Batchs.get() + " :: Failed at Obj: " + objname + " to SetDataE");
				throw new Exception();
			}

		}

		/*------------------------------------------------------------------------------------------------------
		* Function Name: SetData
		* Use : Sets the Specified value to the cell
		* Designed By: Vinodhini
		* Last Modified Date : 13-January-2017
		--------------------------------------------------------------------------------------------------------*/
		public static void SetData(String objname, int rownum, int columnnum, String obj, String Val) throws Exception {
			try {
				Common.ConditionalWait(objname, "WebTable");
				String[] objprop = Utlities.FindObject(objname, "WebTable");
				String cellXpath = objprop[0] + "//tr[" + rownum + "]//td[" + columnnum + "]";
				cDriver.get().findElement(By.xpath(cellXpath)).click();
				String cellXpath1 = objprop[0] + "//tr[" + rownum + "]//td[" + columnnum + "]//input[@name='" + obj
						+ "']";
				Thread.sleep(200);
				cDriver.get().findElement(By.xpath(cellXpath1)).clear();
				Thread.sleep(200);
				String vis = "false";
				int countval = 1;
				while (vis == "false" || countval < 10000)
					if (cDriver.get().findElement(By.xpath(cellXpath1)).isDisplayed()) {
						Thread.sleep(100);
						cDriver.get().findElement(By.xpath(cellXpath1)).sendKeys(Val);
						Thread.sleep(100);
						cDriver.get().findElement(By.xpath(cellXpath1)).sendKeys(Keys.ENTER);
						vis = "true";
						countval = 10000;
					} else {
						countval++;
						Thread.sleep(10);
					}
				Result.fUpdateLog(Batchs.get() + " :: Action SetData on Obj: " + objname);
			} catch (Exception e) {
				Result.fUpdateLog(Batchs.get() + " :: Failed at Obj: " + objname + " to SetData");
				throw new Exception();
			}

		}

		/*------------------------------------------------------------------------------------------------------
		* Function Name: Check
		* Use : Sets the Specified value to the cell
		* Designed By: Vinodhini
		* Last Modified Date : 7-March-2017
		--------------------------------------------------------------------------------------------------------*/
		public static void Check(String objname, int rownum, int columnnum, String val) throws Exception {
			try {
				Common.ConditionalWait(objname, "WebTable");
				String[] objprop = Utlities.FindObject(objname, "WebTable");
				String cellXpath = objprop[0] + "//tr[" + rownum + "]//td[" + columnnum + "]";
				cDriver.get().findElement(By.xpath(cellXpath)).click();
				String cellXpath1 = objprop[0] + "//tr[" + rownum + "]//td[" + columnnum + "]//option[@value='" + val
						+ "']";
				cDriver.get().findElement(By.xpath(cellXpath1)).click();
				Result.fUpdateLog(Batchs.get() + " :: Action Check on Obj: " + objname);
			} catch (Exception e) {
				Result.fUpdateLog(Batchs.get() + " :: Failed at Obj: " + objname + " to Check");
				throw new Exception();
			}

		}

		public static void Link(String objname, int rownum, int columnnum) throws Exception {
			try {
				Common.ConditionalWait(objname, "WebTable");
				String[] objprop = Utlities.FindObject(objname, "WebTable");
				String cellXpath = objprop[0] + "//tr[" + rownum + "]//td[" + columnnum + "]/a";
				cDriver.get().findElement(By.xpath(cellXpath)).click();

				// cDriver.get().findElement(By.xpath(cellXpath)).
				Result.fUpdateLog(Batchs.get() + " :: Action Link on Obj: " + objname);
			} catch (Exception e) {
				Result.fUpdateLog(Batchs.get() + " :: Failed at Obj: " + objname + " to Link");
				throw new Exception();
			}
		}

		public static void Expand(String objname, int rownum, int columnnum) throws Exception {
			try {
				Common.ConditionalWait(objname, "WebTable");
				String[] objprop = Utlities.FindObject(objname, "WebTable");
				String cellXpathX = objprop[0] + "//tr[" + rownum + "]//td[" + columnnum + "]";
				cDriver.get().findElement(By.xpath(cellXpathX)).click();
				String cellXpath = objprop[0] + "//tr[" + rownum + "]//td[" + columnnum + "]/div/div";
				cDriver.get().findElement(By.xpath(cellXpath)).click();
				Result.fUpdateLog(Batchs.get() + " :: Action Expand on Obj: " + objname);
			} catch (Exception e) {
				Result.fUpdateLog(Batchs.get() + " :: Failed at Obj: " + objname + " to Expand");
				throw new Exception();
			}

		}

		public static void Popup(String objname, int rownum, int columnnum) throws Exception {
			try {
				Common.ConditionalWait(objname, "WebTable");
				String[] objprop = Utlities.FindObject(objname, "WebTable");
				String cellXpathX = objprop[0] + "//tr[" + rownum + "]//td[" + columnnum + "]";
				cDriver.get().findElement(By.xpath(cellXpathX)).click();
				String cellXpath = objprop[0] + "//tr[" + rownum + "]//td[" + columnnum + "]//span";
				cDriver.get().findElement(By.xpath(cellXpath)).click();
				Result.fUpdateLog(Batchs.get() + " :: Action Popup on Obj: " + objname);
			} catch (Exception e) {
				Result.fUpdateLog(Batchs.get() + " :: Failed at Obj: " + objname + " to Popup");
				throw new Exception();
			}
		}
		/*------------------------------------------------------------------------------------------------------
	     * Function Name: getRowWithCellText
	     * Use :       returns the value in the given row and column of the web table
	     * Designed By: Sisira S
	     * Modified By: 
	     * Last Modified Date : 11-Nov-2019
	     --------------------------------------------------------------------------------------------------------*/
	     public static int getRowWithCellText(String objname,int columnnum,String cellText) throws Exception {
	            try {
	                  Common.ConditionalWait(objname, "WebTable");
	                  String[] objprop = Utlities.FindObject(objname, "WebTable");
	                  int Rows=getRowCount(objname);
	                  int RowNum=1;
	                  String celldata="";
	                  for(int i=1;i<=Rows;i++)
	                  {
	                         celldata=getCellData(objname,i,columnnum);
	                         if(celldata.equals(cellText))
	                         {
	                                RowNum=i;
	                                break;
	                         }
	                  }                          
	                  return RowNum;
	            } catch (Exception e) {
	                  Result.fUpdateLog(Batchs.get() + " :: Failed at Obj: " + objname + " to getRowWithCellText");
	                  throw new Exception();
	            }

	     }
	
		
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Class Name			: alert 
	 * Use 					:  class represents the alert in the application and 
	 * 						  contains functions for all the operations performed on alert 
	 * Designed By			: AG
	 * Last Modified Date 	: 25-Apr-2016
	--------------------------------------------------------------------------------------------------------*/
	public static class alert {
		public static void accept() {
			String alertpresent = "false";
			while (alertpresent == "false") {
				try {
					Alert simpleAlert = ((WebDriver) cDriver.get()).switchTo().alert();
					simpleAlert.accept();
					alertpresent = "true";
					break;
				} catch (Exception e) {
					alertpresent = "false";
				}

			}
		}
	}

	/*------------------------------------------------------------------------------------------------------
	* Function Name: OpenBrowser
	* Use : Opens a new browser and resizes it according the number of parallel instances
	* Designed By: AG
	* Last Modified Date : 15-June-2016
	--------------------------------------------------------------------------------------------------------*/
	public static void OpenBrowser(String BrowserName, String URL) {
		try {
			switch (BrowserName.toLowerCase()) {
			case "mobile":
				cDriver.set(new AndroidMobBrow().getNewDriver());
				cDriver.get().get(URL);				
				System.out.println("Title " + cDriver.get().getTitle());
				break;
			case "firefox":
				cDriver.set(new DesktopWebBrow().getNewDriver());
				cDriver.get().get(URL);
				Thread.sleep(2000);
				// cDriver.get().findElement(By.xpath("//*[@id='advancedButton']")).click();
				// cDriver.get().findElement(By.xpath("//*[@id='exceptionDialogButton']")).click();
				// Thread.sleep(5000);
				maximize();
				System.out.println("Title " + cDriver.get().getTitle());
				break;
			case "chrome":
				cDriver.set(new DesktopWebBrow().getNewDriver());
				cDriver.get().get(URL);
				Thread.sleep(2000);
				maximize();
				System.out.println("Title " + cDriver.get().getTitle());
				break;
			case "ie":
				cDriver.set(new DesktopWebBrow().getNewDriver());
				cDriver.get().manage().deleteAllCookies();
				cDriver.get().get(URL);
				/*
				 * Thread.sleep(3000); cDriver.get().get(
				 * "javascript:document.getElementById('overridelink').click();");
				 * Thread.sleep(3000);
				 */
				maximize();
				System.out.println("Title " + cDriver.get().getTitle());
				break;

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Class Name			: WebList 
	 * Use 					: Subclass of browser class represents the WebList in the application and 
	 * 						  contains functions for all the operations performed on Web List   
	 * Designed By			: Vinodhini Raviprasad
	 * Last Modified Date 	: 13-Nov-2017
	--------------------------------------------------------------------------------------------------------*/
	public static class WebList {
		public static void clickTab(String objname, String objvalue) throws Exception {
			Common.ConditionalWait(objname, "WebList");
			String objtype = "WebList";
			String[] objprop = Utlities.FindObject(objname, objtype);
			String cellXpath = objprop[0] + "//ul[1]//li";
			List<org.openqa.selenium.WebElement> List_Count = cDriver.get().findElements(By.xpath(cellXpath));
			int List = List_Count.size();
			for (int i = 1; i < List; i++) {
				cellXpath = objprop[0] + "//ul[1]//li[" + i + "]";
				org.openqa.selenium.WebElement scr1 = cDriver.get().findElement(By.xpath(cellXpath));
				((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)", scr1);
				String celldata = cDriver.get().findElements(By.xpath(cellXpath)).get(0).getText();
				if (celldata.toLowerCase().contains(objvalue.toLowerCase())) {
					cDriver.get().findElement(By.xpath(cellXpath)).click();
					break;
				}
			}
			if (Continue.get() == false) {
				Result.fUpdateLog("Failed at Tab Selection: " + objname);
			}
		}
	}

	public static void maximize() {
		cDriver.get().manage().window().maximize();
	}
	
	public static void Highlight(WebElement Ele) {
		//WebElement ele = (WebElement)cDriver.get().findElement(By.xpath(ObjName));
	    //Create object of a JavascriptExecutor interface
		JavascriptExecutor js = (JavascriptExecutor) cDriver.get();
	    //use executeScript() method and pass the arguments 
	    //Here i pass values based on css style. Yellow background color with solid red color border. 
		js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", Ele);
		
		}
		
	public static Boolean Readystate() {
		((JavascriptExecutor) cDriver.get()).executeScript("return document.readyState");
		return true;
	}
	
	



}