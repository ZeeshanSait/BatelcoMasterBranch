package Libraries;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

public class Keyword_OSM extends Driver {
	Common CO = new Common();

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: OSM_Login
	 * Arguments			: None
	 * Use 					: Opens a New Browser and logins to the OSM application
	 * Designed By			: Sravani Reddy C
	 * Last Modified Date 	: 17-Oct-2017
	--------------------------------------------------------------------------------------------------------*/
	public String OSM_Login() {

		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------OSM Login Event Details------");
		try {

			if (!(getdata("Browser").equals(""))) {
				browser.set(getdata("Browser"));
			} else {
				browser.set("ie");
			}

			if (!(getdata("URL/HOST").equals(""))) {
				URL.set(getdata("URL/HOST"));
			} else {
				URL.set("ie");
			}

			Browser.OpenBrowser(browser.get(), URL.get());

			// Result.fUpdateLog("Browser Opened Successfully");
			Result.takescreenshot("Opening Browser and navigating to the URL");
			// Browser.WebEdit.click("VQ_Login_User");
			Browser.WebEdit.Set("OSM_Login_User", getdata("VQ_Login_User"));
			// Browser.WebEdit.click("OSM_Login_Pswd");
			Browser.WebEdit.Set("OSM_Login_Pswd", getdata("VQ_Login_Pswd"));
			Browser.WebLink.click("OSM_Submit");

			CO.ToWait();
			if (Browser.WebButton.exist("OSM_Query")) {
				Continue.set(true);
				// Browser.WebButton.click("OSM_Query");
			} else {
				Test_OutPut += "Login page not loaded properly" + ",";
				Continue.set(false);
			}

			if (Continue.get()) {
				Test_OutPut += "Successfully Login with : " + getdata("OSM_Login_User") + ",";
				// Result.takescreenshot("Login Successfully with user " +
				// getdata("OSM_Login_User"));
				Result.fUpdateLog("Login Successfully with user " + getdata("OSM_Login_User"));
				Status = "PASS";
			} else {
				Test_OutPut += "Login Failed" + ",";
				Result.takescreenshot("Login Failed");
				Result.fUpdateLog("Login Failed");
				Status = "FAIL";
			}
		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------OSM Login Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public String OSM_SearchFL() {

		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------OSM_SearchFL Event Details------");
		int Col, Col_S, Row_Count, Wait = 0;

		String Order_No = null, New_Order = null;
		String LData;
		try {
			String Sales_Od = SalesOrder_No.get();
			// Sales_Od = "1-12426667804";
			CO.waitforload();
			if (Browser.WebButton.exist("OSM_Query")) {
				Browser.WebButton.click("OSM_Query");

			}

			if (Browser.WebLink.exist("OSM_EditQuery")) {
				Browser.WebLink.click("OSM_EditQuery");

			}

			CO.waitforload();
			Browser.WebEdit.Set("OSM_OrderNo_entry", Sales_Od);
			Result.fUpdateLog("Searching with Sales OrderNo " + Sales_Od);
			CO.scroll("OSM_OrderNo_entry", "WebEdit");
			Browser.WebButton.click("OSM_Query_search");
			CO.waitforload();
			CO.waitforload();
			Result.takescreenshot("On clicking Search Button");
			CO.waitforload();
			Col = CO.Actual_OSM_tabval("OSM_QueryRes", "State");
			Col_S = CO.Actual_OSM_tabval("OSM_QueryRes", "Order ID");
			int Col_T = CO.Actual_OSM_tabval("OSM_QueryRes", "Task");

			CO.waitforload();
			Row_Count = Browser.WebTable.getRowCount("OSM_QueryRes");
			if (Row_Count >= 2) {
				LData = Browser.WebTable.getCellData("OSM_QueryRes", 2, Col);
				String Task = Browser.WebTable.getCellData("OSM_QueryRes", 2, Col_T);
				if (LData.equalsIgnoreCase("Received")) {
					Order_No = Browser.WebTable.getCellData("OSM_QueryRes", 2, Col_S);
				} else if (Task.equalsIgnoreCase("InitiateWFMManualEntryTask")) {
					Order_No = Browser.WebTable.getCellData("OSM_QueryRes", 2, Col_S);
					// Continue.set(false);
				} else {
					Order_No = Browser.WebTable.getCellData("OSM_QueryRes", 2, Col_S);
					Test_OutPut += "Order Task : " + Task + ",";
					Continue.set(false);
				}
			} else {
				Test_OutPut += "Order not received to OSM" + ",";
				Continue.set(false);
			}

			if (Continue.get()) {
				CO.waitforload();
				Browser.WebButton.click("OSM_Worklist");
				CO.waitforload();
				Browser.WebEdit.Set("OSM_OrderId", Order_No);
				Result.fUpdateLog("Searching with Order_No " + Order_No);
				CO.waitforload();
				Browser.WebButton.click("OSM_Refresh");
				Row_Count = Browser.WebTable.getRowCount("OSM_QueryRes");
				CO.waitforload();
				if (Row_Count >= 2) {
					CO.waitforload();
					Browser.WebButton.click("OSM_Search");
					CO.waitforload();
					CO.waitforload();
					if (!(getdata("STB_SerialNumber").equals(""))) {
						CO.scroll("OSM_STB_SerialNumber", "WebEdit");
						Browser.WebEdit.Set("OSM_STB_SerialNumber", getdata("STB_SerialNumber"));
						Result.fUpdateLog("Entering STB Serial Number " + getdata("STB_SerialNumber"));
						Result.takescreenshot("Entering STB Serial Number " + getdata("STB_SerialNumber"));
					}

					if (!(getdata("OSM_CPE").equals("")) || (!(pulldata("OSM_CPE").equals("")))) {
						CO.scroll("OSM_CPE", "WebEdit");
						Browser.WebEdit.Set("OSM_CPE", getdata("OSM_CPE"));
						Result.fUpdateLog("Entering CPE Number " + getdata("OSM_CPE"));
					}

					if (!(getdata("OSM_OUI").equals("")) || (!(pulldata("OSM_OUI").equals("")))) {
						CO.scroll("OSM_OUI", "WebEdit");
						Browser.WebEdit.Set("OSM_OUI", getdata("OSM_OUI"));
						Result.fUpdateLog("Entering OUI Number " + getdata("OSM_OUI"));
					}
					if (!(getdata("OSM_CPEP").equals("")) || (!(pulldata("OSM_CPEP").equals("")))) {
						CO.scroll("OSM_CPEP", "WebEdit");
						Browser.WebEdit.Set("OSM_CPEP", getdata("OSM_CPEP"));
						Result.fUpdateLog("Entering OSM_CPEP Number " + getdata("OSM_CPEP"));
					}

					if (!(getdata("ONT").equals("")) || (!(pulldata("ONT").equals("")))) {
						CO.scroll("OSM_SerialN", "WebEdit");
						Browser.WebEdit.Set("OSM_SerialN", getdata("ONT"));
						Result.fUpdateLog("Entering ONT Number " + getdata("ONT"));
					}

					if (!(getdata("OSM_CardID").equals("")) || (!(pulldata("OSM_CardID").equals("")))) {
						CO.scroll("OSM_CardID", "WebEdit");
						Browser.WebEdit.Set("OSM_CardID", getdata("OSM_CardID"));
						Result.fUpdateLog("Entering CardID Number " + getdata("OSM_CardID"));
					}
					if (!(getdata("OSM_PortID").equals("")) || (!(pulldata("OSM_PortID").equals("")))) {
						CO.scroll("OSM_PortID", "WebEdit");
						Browser.WebEdit.Set("OSM_PortID", getdata("OSM_PortID"));
						Result.fUpdateLog("Entering PortID Number " + getdata("OSM_PortID"));
						Result.takescreenshot("Entering PortID Number " + getdata("OSM_PortID"));
					}

					Select dropdown = new Select(cDriver.get().findElement(
							By.xpath("//form[@name='orderEditorMenu']//select[@id='completionStatusList']")));
					dropdown.selectByVisibleText("Finish");
					Browser.WebButton.click("OSM_Update");
					Result.fUpdateLog("Updating the Order " + Order_No);
					Result.takescreenshot("Updating the Order " + Order_No);
					CO.waitmoreforload();

					Browser.WebButton.click("OSM_Query");
					// CO.waitforload();

					if (Browser.WebLink.exist("OSM_EditQuery")) {
						Browser.WebLink.click("OSM_EditQuery");

					}

					CO.scroll("OSM_OrderNo_entry", "WebEdit");
					CO.waitforload();
					Browser.WebEdit.Set("OSM_OrderNo_entry", Sales_Od);
					Browser.WebButton.click("OSM_Query_search");
					CO.waitforload();
					// CO.waitmoreforload();
					int ColOD1 = CO.Actual_OSM_tabval("OSM_QueryRes", "Order ID");
					String New_ODID = Browser.WebTable.getCellData("OSM_QueryRes", 3, ColOD1);
					Col = CO.Actual_OSM_tabval("OSM_QueryRes", "Order State");
					int ColOD = CO.Actual_OSM_tabval("OSM_QueryRes", "Order ID");
					Row_Count = Browser.WebTable.getRowCount("OSM_QueryRes");
					for (int i = 2; i < Row_Count; i++) {
						String ODid = Browser.WebTable.getCellData("OSM_QueryRes", i, ColOD);
						if (ODid.equals(New_ODID)) {
							Result.fUpdateLog("Searching with Order_No " + New_ODID);

							do {
								LData = Browser.WebTable.getCellData("OSM_QueryRes", i, Col);
								Result.fUpdateLog("OSM Status" + " " + LData);
								if (LData.equalsIgnoreCase("Completed")) {
									Result.takescreenshot("OSM New_Order Status has Sucesfully Updated ");
									Result.fUpdateLog("OSM New_Order Status has Sucesfully Updated ");
									Wait = 101;
									Continue.set(true);
									break;

								} else if (Wait == 105) {
									// Result.fUpdateLog("OSM Status updation has failed ");
									Result.takescreenshot("OSM Order_No Status updation has failed");
									Continue.set(false);
									break;

								}
								Browser.WebButton.click("OSM_Refresh1");
								Wait = Wait + 5;
								CO.waitmoreforload();

							} while (Wait < 100);

						}

					}
					if (Continue.get()) {
						Row_Count = Browser.WebTable.getRowCount("OSM_QueryRes");
						for (int i = 2; i < Row_Count; i++) {
							String ODid = Browser.WebTable.getCellData("OSM_QueryRes", i, ColOD);

							if (ODid.equals(Order_No)) {
								Browser.WebButton.click("OSM_Worklist");
								CO.waitforload();
								Browser.WebEdit.Set("OSM_OrderId", Order_No);
								Result.fUpdateLog("Searching with Order_No " + Order_No);
								CO.waitforload();
								Browser.WebButton.click("OSM_Refresh");
								Browser.WebButton.click("OSM_Search");
								// Browser.WebTable.click("OSM_QueryRes", i, 1);
								// Browser.WebButton.click("OSM_Search");
								CO.waitmoreforload();
								cDriver.get()
										.findElement(By.xpath("//form[@name='orderEditorMenu']//span[text()='Update']"))
										.click();
								// Browser.WebButton.click("OSM_Update");

								CO.waitforload();

								Browser.WebButton.click("OSM_Query");
								CO.waitforload();
								if (Browser.WebLink.exist("OSM_EditQuery")) {
									Browser.WebLink.click("OSM_EditQuery");

								}
								CO.scroll("OSM_OrderNo_entry", "WebEdit");
								CO.waitforload();
								Browser.WebEdit.Set("OSM_OrderNo_entry", Sales_Od);
								Browser.WebButton.click("OSM_Query_search");
								CO.waitmoreforload();

								do {
									LData = Browser.WebTable.getCellData("OSM_QueryRes", i, Col);
									Result.fUpdateLog("OSM Status" + " " + LData);
									if (LData.equalsIgnoreCase("Completed")) {
										Result.fUpdateLog("OSM Order_No Status has Sucesfully Updated ");
										Result.takescreenshot("OSM Order_No Status has Sucesfully Updated  ");
										Wait = 101;
										break;

									} else if (Wait == 105) {
										Result.fUpdateLog("OSM Order_No Status updation has failed ");
										Result.takescreenshot("OSM Order_No Status updation has failed   ");
										break;
									}
									Browser.WebButton.click("OSM_Refresh1");
									Wait = Wait + 5;
									CO.waitmoreforload();

								} while (Wait < 100);

							}
						}

					}

				}

			}
			CO.scroll("OSM_Logout", "WebButton");
			CO.waitforload();
			CO.waitforload();
			Browser.WebButton.click("OSM_Logout");
			cDriver.get().close();
			cDriver.get().quit();
			CO.ToWait();

			if (Continue.get()) {
				Utlities.StoreValue("Seibel Order No :", Order_No);
				Test_OutPut += "Seibel Order No : " + Order_No + ",";
				Utlities.StoreValue("New_Order:", New_Order);
				Test_OutPut += "New_Order: " + New_Order + ",";
				Result.takescreenshot("OSM Updation Is Sucessfull ");
				Result.fUpdateLog("OSM Updation Is Sucessfull ");
				Status = "PASS";
			} else {
				Test_OutPut += "OSM Updation Failed" + ",";
				Result.takescreenshot("OSM Updation Failed");
				Result.fUpdateLog("OSM Updation Failed");
				Status = "FAIL";
			}
		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------OSM_SearchFL Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public String OSM_Pearl_data() {

		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------OSM_Pearl_DATA Event Details------");
		int Col, Col_S, Row_Count;

		String Order_No = null;
		String LData;
		try {
			String Sales_Od = SalesOrder_No.get();

			Browser.WebButton.click("OSM_Query");
			if (Browser.WebLink.exist("OSM_EditQuery")) {
				Browser.WebLink.click("OSM_EditQuery");

			} else {
				Browser.WebButton.click("OSM_Query");
			}
			CO.waitforload();
			Browser.WebEdit.Set("OSM_OrderNo_entry", Sales_Od);
			Result.fUpdateLog("Searching with Sales OrderNo " + Sales_Od);

			CO.scroll("OSM_OrderNo_entry", "WebEdit");
			Browser.WebButton.click("OSM_Query_search");
			Result.takescreenshot("On clicking Search Button");
			CO.waitforload();

			// Browser.WebButton.click("OSM_Query");

			CO.waitforload();
			if (Browser.WebLink.exist("OSM_EditQuery")) {
				Browser.WebLink.click("OSM_EditQuery");

			} else {
				Browser.WebButton.click("OSM_Query");
			}
			Browser.WebEdit.clear("OSM_OrderNo_entry");
			Browser.WebEdit.Set("OSM_Date_Order", Sales_Od);
			Result.fUpdateLog("Searching with Sales Date OrderNo " + Sales_Od);
			Result.takescreenshot("Searching with Sales Date OrderNo");
			Browser.WebButton.click("OSM_Query_search");
			Result.takescreenshot("On clicking Search Button");

			Col = CO.Actual_OSM_tabval("OSM_QueryRes", "State");
			Col_S = CO.Actual_OSM_tabval("OSM_QueryRes", "Order ID");

			CO.waitforload();
			Row_Count = Browser.WebTable.getRowCount("OSM_QueryRes");

			LData = Browser.WebTable.getCellData("OSM_QueryRes", 2, Col);
			if (LData.equalsIgnoreCase("Received") || LData.equalsIgnoreCase("Accepted")) {
				Order_No = Browser.WebTable.getCellData("OSM_QueryRes", 2, Col_S);
			} else {
				Continue.set(false);
			}

			if (Continue.get()) {

				CO.waitforload();
				Browser.WebButton.click("OSM_Worklist");
				CO.waitforload();
				Browser.WebEdit.Set("OSM_OrderId", Order_No);
				Result.fUpdateLog("Searching with Order_No " + Order_No);
				CO.waitforload();
				Browser.WebButton.click("OSM_Refresh");
				Row_Count = Browser.WebTable.getRowCount("OSM_QueryRes");
				Browser.WebButton.click("OSM_Changestate");
				Browser.WebTable.click("OSM_QueryRes", 2, 1);
				if (Row_Count >= 2) {
					if (Browser.WebButton.exist("OSM_Accepted")) {
						Browser.WebButton.click("OSM_Accepted");
						CO.waitforload();
						Result.takescreenshot("");
						Browser.WebButton.click("OSM_Pearl_Update");
						CO.waitforload();
						Browser.WebButton.click("OSM_Refresh");
						CO.waitforload();
						CO.waitforload();
						Browser.WebEdit.Set("OSM_OrderId", Order_No);
						CO.waitforload();
						Browser.WebButton.click("OSM_Refresh");

						Browser.WebTable.click("OSM_QueryRes", 2, 1);
					}
					if (Browser.WebButton.exist("OSM_Completed")) {
						Browser.WebButton.click("OSM_Completed");
						Result.takescreenshot("");
						Browser.WebButton.click("OSM_Pearl_Update");
					}

					Browser.WebEdit.Set("OSM_SuccesMsg", "Manually Activity At OSM is Completed");
					Result.takescreenshot("");
					Browser.WebButton.click("OSM_Update");

					CO.waitforload();
					Browser.WebButton.click("OSM_Query");
					if (Browser.WebLink.exist("OSM_EditQuery")) {
						Browser.WebLink.click("OSM_EditQuery");

					} else {
						Browser.WebButton.click("OSM_Query");
					}
					Browser.WebEdit.clear("OSM_OrderNo_entry");
					Browser.WebEdit.clear("OSM_Date_Order");
					Browser.WebEdit.Set("OSM_OrderNo_entry", Sales_Od);
					Result.fUpdateLog("Searching with Sales Date OrderNo " + Sales_Od);
					Browser.WebButton.click("OSM_Query_search");
					Result.takescreenshot("On clicking Search Button");
					Browser.WebButton.click("OSM_Refresh");
					int i = 10;
					do {
						Col = CO.Actual_OSM_tabval("OSM_QueryRes", "State");
						LData = Browser.WebTable.getCellData("OSM_QueryRes", 2, Col);
						String LData1 = Browser.WebTable.getCellData("OSM_QueryRes", 3, Col);

						if (LData.equalsIgnoreCase("Completed") && LData1.equalsIgnoreCase("Completed")) {
							i = 101;
						} else {
							Browser.WebButton.click("OSM_Refresh");
						}

						i = i + 10;
					} while (i < 100);
					if (i == 100) {
						Continue.set(false);
					} else {
						Result.fUpdateLog("OSM Updation Is Sucessfull ");
					}

				}

			}
			CO.scroll("OSM_Logout", "WebButton");
			CO.waitforload();
			CO.waitforload();
			Browser.WebButton.click("OSM_Logout");
			CO.ToWait();

			if (Continue.get()) {
				Utlities.StoreValue("Seibel Order No :", Sales_Od);
				Test_OutPut += "Seibel Order No : " + Order_No + ",";
				Utlities.StoreValue("New_Order:", Order_No);
				Test_OutPut += "New_Order: " + Order_No + ",";
				Result.takescreenshot("OSM Updation Is Sucessfull ");
				Result.fUpdateLog("OSM Updation Is Sucessfull ");
				Status = "PASS";
			} else {
				Test_OutPut += "OSM Updation Failed" + ",";
				Result.takescreenshot("OSM Updation Failed");
				Result.fUpdateLog("OSM Updation Failed");
				Status = "FAIL";
			}
		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------OSM_Pearl_DATA Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public String RePush_SOM() {

		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------OSM_SearchFL Event Details------");
		String Sales_Od = getdata("OrderId");
		try {

			if (Browser.WebButton.exist("OSM_Query")) {
				Browser.WebButton.click("OSM_Query");

			}
			Result.takescreenshot("Navigation to Query");
			if (Browser.WebLink.exist("OSM_EditQuery")) {
				Browser.WebLink.click("OSM_EditQuery");

			}
			CO.waitforload();
			Browser.WebEdit.Set("OSM_OrderNo_entry", Sales_Od);
			Result.fUpdateLog("Searching with Sales OrderNo " + Sales_Od);
			CO.scroll("OSM_OrderNo_entry", "WebEdit");
			Browser.WebButton.click("OSM_Query_search");
			Result.takescreenshot("Edit Query");

			Result.takescreenshot("On clicking Search Button");
			CO.waitforload();
			int ColOD = CO.Actual_OSM_tabval("OSM_QueryRes", "Order ID");
			int Colsrc = CO.Actual_OSM_tabval("OSM_QueryRes", "Source");
			int totrow = Browser.WebTable.getRowCount("OSM_QueryRes");
			String New_ODID = "";
			for (int currow = 1; currow < totrow; currow++) {
				String srcval = Browser.WebTable.getCellData("OSM_QueryRes", currow, Colsrc);
				// System.out.println(srcval);
				if (srcval.toLowerCase().contains("som")) {
					New_ODID = Browser.WebTable.getCellData("OSM_QueryRes", currow, ColOD);
					System.out.println(New_ODID);
					break;
				}

			}

			CO.waitforload();
			Browser.WebButton.click("OSM_Worklist");
			Result.takescreenshot("Navigation to Worklist");
			CO.waitforload();
			Browser.WebEdit.Set("OSM_OrderId", New_ODID);
			Result.fUpdateLog("Searching with Order_No " + New_ODID);
			CO.waitforload();
			Browser.WebButton.click("OSM_Refresh");
			Result.takescreenshot("");
			CO.waitforload();
			// cDriver.get().findElement(By.xpath(".//p[@class =
			// 'radioInput']/input[7]")).click();
			Browser.WebButton.click("changestatus_radio");

			// cDriver.get().findElement(By.xpath(".//input[@name = 'move']")).click();
			Browser.WebButton.click("Move_Button");
			Result.takescreenshot("");
			CO.waitforload();
			try {
				cDriver.get().findElement(By.xpath(".//input[@id = '" + getdata("Status").toLowerCase() + "']"))
						.click();
				Result.takescreenshot("");
			} catch (Exception e) {
				Continue.set(false);
				Test_OutPut += "Exception occurred" + ",";
				Result.takescreenshot("Exception occurred");
				Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
				Status = "FAIL";
				// e.printStackTrace();
			}
			CO.waitforload();
			// cDriver.get().findElement(By.xpath(".//input[@value = 'Update']")).click();
			Browser.WebButton.click("Update_Button");
			Result.takescreenshot("");
			CO.waitforload();

			if (Continue.get()) {
				Utlities.StoreValue("OSM Order No :", New_ODID);
				Test_OutPut += "OSM Order No : " + New_ODID + ",";

				Result.takescreenshot("OSM Updation Is Sucessfull ");
				Result.fUpdateLog("OSM Updation Is Sucessfull ");
				Status = "PASS";
			} else {
				Test_OutPut += "OSM Updation Failed" + ",";
				Result.takescreenshot("OSM Updation Failed");
				Result.fUpdateLog("OSM Updation Failed");
				Status = "FAIL";
			}

		}

		catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();
		}
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public String RePush_COM() {

		String Test_OutPut = "", Status = "";
		String Sales_Od = getdata("OrderId");
		try {

			if (Browser.WebButton.exist("OSM_Query")) {
				Browser.WebButton.click("OSM_Query");

			}

			if (Browser.WebLink.exist("OSM_EditQuery")) {
				Browser.WebLink.click("OSM_EditQuery");

			}
			CO.waitforload();
			Browser.WebEdit.Set("OSM_OrderNo_entry", Sales_Od);
			Result.fUpdateLog("Searching with Sales OrderNo " + Sales_Od);
			CO.scroll("OSM_OrderNo_entry", "WebEdit");
			Browser.WebButton.click("OSM_Query_search");
			Result.takescreenshot("");
			Result.takescreenshot("On clicking Search Button");
			CO.waitforload();
			int ColOD = CO.Actual_OSM_tabval("OSM_QueryRes", "Order ID");
			int Colsrc = CO.Actual_OSM_tabval("OSM_QueryRes", "Source");
			int totrow = Browser.WebTable.getRowCount("OSM_QueryRes");
			String New_ODID = "";
			for (int currow = 1; currow < totrow; currow++) {
				String srcval = Browser.WebTable.getCellData("OSM_QueryRes", currow, Colsrc);
				// System.out.println(srcval);
				if (srcval.toLowerCase().contains("com")) {
					New_ODID = Browser.WebTable.getCellData("OSM_QueryRes", currow, ColOD);
					System.out.println(New_ODID);
					break;
				}

			}
			Result.takescreenshot("");
			CO.waitforload();
			Browser.WebButton.click("OSM_Worklist");
			Result.takescreenshot("");
			CO.waitforload();
			Browser.WebEdit.Set("OSM_OrderId", New_ODID);
			Result.fUpdateLog("Searching with Order_No " + New_ODID);
			CO.waitforload();
			Browser.WebButton.click("OSM_Refresh");
			Result.takescreenshot("");
			CO.waitforload();
			// cDriver.get().findElement(By.xpath(".//p[@class =
			// 'radioInput']/input[7]")).click();
			Browser.WebButton.click("changestatus_radio");

			// cDriver.get().findElement(By.xpath(".//input[@name = 'move']")).click();
			Browser.WebButton.click("Move_Button");
			Result.takescreenshot("");
			CO.waitforload();
			try {
				cDriver.get().findElement(By.xpath(".//input[@id = '" + getdata("Status").toLowerCase() + "']"))
						.click();
			} catch (Exception e) {
				Continue.set(false);
				Test_OutPut += "Exception occurred" + ",";
				Result.takescreenshot("Exception occurred");
				Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
				Status = "FAIL";
				e.printStackTrace();
			}
			CO.waitforload();
			// cDriver.get().findElement(By.xpath(".//input[@value = 'Update']")).click();
			Browser.WebButton.click("Update_Button");

			CO.waitforload();

			if (Continue.get()) {
				Utlities.StoreValue("OSM Order No :", New_ODID);
				Test_OutPut += "OSM Order No : " + New_ODID + ",";

				Result.takescreenshot("OSM Updation Is Sucessfull ");
				Result.fUpdateLog("OSM Updation Is Sucessfull ");
				Status = "PASS";
			} else {
				Test_OutPut += "OSM Updation Failed" + ",";
				Result.takescreenshot("OSM Updation Failed");
				Result.fUpdateLog("OSM Updation Failed");
				Status = "FAIL";
			}

		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();
		}
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public String RePush_TOM() {

		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------OSM_SearchFL Event Details------");
		// int Col, Col_S, Row_Count, Wait = 0;

		// String Order_No = null, New_Order = null;
		// String LData;
		String Sales_Od = getdata("OrderId");
		try {

			if (Browser.WebButton.exist("OSM_Query")) {
				Browser.WebButton.click("OSM_Query");

			}

			if (Browser.WebLink.exist("OSM_EditQuery")) {
				Browser.WebLink.click("OSM_EditQuery");

			}
			Result.takescreenshot("");
			CO.waitforload();
			// cDriver.get().findElement(By.xpath("//input[@name =
			// '/MobileServiceData/OrderIdentification/OrderNumber_2']")).sendKeys(Sales_Od);
			Browser.WebEdit.Set("OSM_MobileOrderNo_entry", Sales_Od);
			Result.fUpdateLog("Searching with Sales OrderNo " + Sales_Od);
			CO.scroll("OSM_MobileOrderNo_entry", "WebEdit");
			Browser.WebButton.click("OSM_Query_search");
			Result.takescreenshot("");
			Result.takescreenshot("On clicking Search Button");
			CO.waitforload();
			int ColOD = CO.Actual_OSM_tabval("OSM_QueryRes", "Order ID");
			int Colsrc = CO.Actual_OSM_tabval("OSM_QueryRes", "Source");
			int totrow = Browser.WebTable.getRowCount("OSM_QueryRes");
			String New_ODID = "";
			for (int currow = 1; currow < totrow; currow++) {
				String srcval = Browser.WebTable.getCellData("OSM_QueryRes", currow, Colsrc);
				// System.out.println(srcval);
				if (!srcval.toLowerCase().contains("source")) {
					New_ODID = Browser.WebTable.getCellData("OSM_QueryRes", currow, ColOD);
					System.out.println(New_ODID);
					break;
				}

			}
			Result.takescreenshot("");
			CO.waitforload();
			Browser.WebButton.click("OSM_Worklist");
			Result.takescreenshot("");
			CO.waitforload();
			Browser.WebEdit.Set("OSM_OrderId", New_ODID);
			Result.fUpdateLog("Searching with Order_No " + New_ODID);
			CO.waitforload();
			Result.takescreenshot("");
			Browser.WebButton.click("OSM_Refresh");
			Result.takescreenshot("");
			CO.waitforload();
			// cDriver.get().findElement(By.xpath(".//p[@class =
			// 'radioInput']/input[7]")).click();
			Browser.WebButton.click("changestatus_radio");
			Result.takescreenshot("");
			// cDriver.get().findElement(By.xpath(".//input[@name = 'move']")).click();
			Browser.WebButton.click("Move_Button");
			Result.takescreenshot("");
			CO.waitforload();
			try {
				cDriver.get().findElement(By.xpath(".//input[@id = '" + getdata("Status").toLowerCase() + "']"))
						.click();
			} catch (Exception e) {
				Continue.set(false);
				Test_OutPut += "Exception occurred" + ",";
				Result.takescreenshot("Exception occurred");
				Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
				Status = "FAIL";
				e.printStackTrace();
			}
			CO.waitforload();
			// cDriver.get().findElement(By.xpath(".//input[@value = 'Update']")).click();
			Browser.WebButton.click("Update_Button");

			CO.waitforload();
			if (Continue.get()) {
				Utlities.StoreValue("OSM Order No :", New_ODID);
				Test_OutPut += "OSM Order No : " + New_ODID + ",";

				Result.takescreenshot("OSM Updation Is Sucessfull ");
				Result.fUpdateLog("OSM Updation Is Sucessfull ");
				Status = "PASS";
			} else {
				Test_OutPut += "OSM Updation Failed" + ",";
				Result.takescreenshot("OSM Updation Failed");
				Result.fUpdateLog("OSM Updation Failed");
				Status = "FAIL";
			}

		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();
		}
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public String OSM_Logout() {
		String Test_OutPut = "", Status = "";
		try {

			CO.waitforload();
			// CO.scroll("OSM_Logout", "WebButton");
			Browser.WebButton.click("OSM_Logout");

			cDriver.get().close();
			cDriver.get().quit();

			if (Continue.get()) {
				Test_OutPut += "OSM Logout Successful";
				Result.fUpdateLog("OSM Logout Successful");
				Status = "PASS";
			} else {
				Test_OutPut += "Logout Failed";
				Result.fUpdateLog("Logout Failed");
				Status = "FAIL";
			}
		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Exception occurred";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();

		}

		return Status + "@@" + Test_OutPut + "<br/>";
	}

}
