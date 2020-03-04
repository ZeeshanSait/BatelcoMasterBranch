package mobileUtilities;

import java.text.SimpleDateFormat;

import org.openqa.selenium.By;
import Libraries.Browser;
import Libraries.Common;
import Libraries.Driver;
import Libraries.Result;
import Libraries.Utlities;

public class Siebel extends Driver {
	Common CO = new Common();

	public String SearchAsset() {
		String Test_OutPut = "", Status = "";
		String MSISDN = utils.fetchData("MSISDN");

		try {
			CO.waitforload();
			int Row = 2, Col;
			CO.Title_Select("a", "Home");
			CO.waitforload();
			Browser.WebLink.waittillvisible("VQ_Assert");
			Browser.WebLink.click("VQ_Assert");
			CO.scroll("Assert_Search", "WebLink");
			Browser.WebLink.click("Assert_Search");
			CO.waitforload();
			Col = CO.Select_Cell("Assert", "Service ID");
			Browser.WebTable.SetDataE("Assert", Row, Col, "Serial_Number", MSISDN);
			Col = CO.Select_Cell("Assert", "Status");
			Browser.WebTable.SetDataE("Assert", Row, Col, "Status", "Active");
			Col = CO.Select_Cell("Assert", "Product");
			Browser.WebButton.waitTillEnabled("Assert_Go");
			Browser.WebButton.click("Assert_Go");
			CO.waitforload();
			// Result.takescreenshot("Account Status : " + Status);
			Col = CO.Select_Cell("Assert", "Account");
			int Assert_Row_Count = Browser.WebTable.getRowCount("Assert");
			if (Assert_Row_Count > 1)
				Browser.WebTable.clickL("Assert", Row, Col);
			else
				Continue.set(false);
			// Browser.WebLink.waittillvisible("Acc_Portal");
			// CO.waitforload();
			// Browser.WebLink.click("Acc_Portal");
			Browser.WebLink.waittillvisible("Inst_Assert_ShowMore");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public String Cookies() {
		String Test_OutPut = "", Status = "";
		String MSISDN = utils.fetchData("MSISDN");

		try {
			CO.waitforload();
			int Row = 2, Col;
			CO.Title_Select("a", "Home");
			CO.waitforload();
			Browser.WebLink.waittillvisible("VQ_Assert");
			Browser.WebLink.click("VQ_Assert");
			CO.scroll("Assert_Search", "WebLink");
			Browser.WebLink.click("Assert_Search");
			CO.waitforload();
			Col = CO.Select_Cell("Assert", "Service ID");
			Browser.WebTable.SetDataE("Assert", Row, Col, "Serial_Number", MSISDN);
			Col = CO.Select_Cell("Assert", "Status");
			Browser.WebTable.SetDataE("Assert", Row, Col, "Status", "Active");
			Col = CO.Select_Cell("Assert", "Product");
			Browser.WebButton.waitTillEnabled("Assert_Go");
			Browser.WebButton.click("Assert_Go");
			CO.waitforload();
			// Result.takescreenshot("Account Status : " + Status);
			Col = CO.Select_Cell("Assert", "Account");
			int Assert_Row_Count = Browser.WebTable.getRowCount("Assert");
			if (Assert_Row_Count > 1)
				Browser.WebTable.clickL("Assert", Row, Col);
			else
				Continue.set(false);

			// Browser.WebLink.waittillvisible("Acc_Portal");
			// CO.waitforload();
			// Browser.WebLink.click("Acc_Portal");
			Browser.WebLink.waittillvisible("Inst_Assert_ShowMore");

			Result.takescreenshot("");

			CO.waitforload();
			CO.InstalledAssertChange("New Query                   [Alt+Q]", "Installed_Assert_Menu");
			CO.waitforload();
			Col = CO.Select_Cell("Installed_Assert", "Service ID");
			Browser.WebTable.SetDataE("Installed_Assert", 2, Col, "Serial_Number", MSISDN);
			Browser.WebButton.click("InstalledAssert_Go");

			Result.takescreenshot("");
			Col = CO.Actual_Cell("Installed_Assert", "Available Cookies");
			String Cookie = Browser.WebTable.getCellData("Installed_Assert", 2, Col);

			Utlities.StoreValue("Cookie", Cookie);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public String CheckOrder() {
		String Test_OutPut = "", Status = "", OrderNo, OS_Status = "", EStatus = "Complete", FStatus = "Failed",
				Bill_Cycle, Action;
		int Col, Wait = 0, Row_Count, Complete_Status = 0, Bill_Col, Row = 2;

		try {
			do {
				Browser.WebButton.click("Orders_Tab");
				CO.waitforload();
				if (CO.isAlertExist())
					Browser.WebButton.click("Orders_Tab");
			} while (!Browser.WebTable.waitTillEnabled("Order_Table"));

			CO.waitforload();
			CO.Text_Select("div", "Order Date");
			CO.waitforload();
			cDriver.get().findElement(By.xpath("//span[@class='siebui-icon-arrowsm-sort']")).click();
			CO.Text_Select("div", "Order Date");
			CO.waitforload();
			cDriver.get().findElement(By.xpath("//span[@class='siebui-icon-arrowsm-sort']")).click();
			CO.waitforload();
			Col = CO.Select_Cell("Order_Table", "Order #");
			Col = CO.Select_Cell("Order_Table", "Order Date");
			String LastOrderTime = Browser.WebTable.getCellData("Order_Table", 2, Col);
			SimpleDateFormat sdf = new SimpleDateFormat("M/dd/YYYY hh:mm:ss aa");
			// utils.timestamp = "2/12/2018 02:25:58 PM";
			Result.fUpdateLog(LastOrderTime + " and timestamp is " + utils.timestamp);
			do {
				if (sdf.parse(LastOrderTime).after(sdf.parse(utils.timestamp))) {
					Col = CO.Select_Cell("Order_Table", "Order #");
					OrderNo = Browser.WebTable.getCellData("Order_Table", 2, Col);
					Test_OutPut += "OrderNo : " + OrderNo + ",";
					Utlities.StoreValue("OrderNo", OrderNo);
					Browser.WebTable.clickL("Order_Table", 2, Col);
					CO.waitforload();
					Row_Count = Browser.WebTable.getRowCount("Line_Items");
					if (Row_Count <= 3) {
						Browser.WebButton.waittillvisible("Expand");
						Browser.WebButton.click("Expand");
					}
					LineItemData.clear();
					Action = utils.pulldata("Action");
					Result.fUpdateLog("Value of Action is  -->" + Action);
					CO.Status(Action);
					do {
						Complete_Status = 0;
						// To refresh Page
						cDriver.get().navigate().refresh();
						Browser.WebButton.waittillvisible("Submit");
						CO.waitforload();
						CO.scroll("Ful_Status", "WebButton");
						Col = CO.Select_Cell("Line_Items", "Fulfillment Status");
						for (int i = 2; i <= Row_Count; i++) {
							CO.scroll("Submit", "WebButton");
							CO.scroll("Ful_Status", "WebButton");

							OS_Status = Browser.WebTable.getCellData("Line_Items", i, Col);
							Result.fUpdateLog("Round" + (i - 1) + " " + OS_Status);
							// To Find the Complete Status
							if (EStatus.equalsIgnoreCase(OS_Status)) {
								Complete_Status = Complete_Status + 1;
								if (Complete_Status == (Row_Count - 1)) {
									Wait = 101;
								}
							} else if (FStatus.equalsIgnoreCase(OS_Status)) {
								Continue.set(false);
								Wait = 101;
							}
						}
						Wait = Wait + 5;
						CO.waitmoreforload();
					} while (Wait < 100);
					Browser.WebButton.waittillvisible("Submit");
					Result.takescreenshot("");
					CO.waitforload();
					Row_Count = Browser.WebTable.getRowCount("Line_Items");
					CO.scroll("Submit", "WebButton");
					OS_Status = Browser.WebTable.getCellData("Line_Items", Row, Col);
					if (Row_Count <= 3) {
						Browser.WebButton.waittillvisible("Expand");
						Browser.WebButton.click("Expand");
					}
					Bill_Col = CO.Actual_Cell("Line_Items", "Bill Cycle");
					Bill_Cycle = Browser.WebTable.getCellData("Line_Items", Row, Bill_Col);
					billDate.set(Bill_Cycle);

					if (OS_Status.equalsIgnoreCase(EStatus) || Complete_Status == (Row_Count - 1)) {
						Test_OutPut += "Order Status : " + OS_Status + ",";
						Result.fUpdateLog("Postpaid_Guided_Journey Order Status : " + OS_Status);
						Result.takescreenshot("Postpaid_Guided_Journey Order Status : " + OS_Status);
						Continue.set(true);
					} else {

						Result.fUpdateLog("Postpaid_Guided_Journey Order Status : " + FStatus);
						Result.takescreenshot("Postpaid_Guided_Journey Order Status : " + FStatus);
						Test_OutPut += "Order Status : " + FStatus + ",";
						Continue.set(false);
					}
					CO.ToWait();
					if (Continue.get()) {

						Status = "PASS";
						Wait += 101;
					} else {
						Test_OutPut += "Action Status Failed" + ",";
						Status = "FAIL";
						Wait += 101;
					}
				} else {
					Result.fUpdateLog("Order has not reached to Siebel yet, Waiting...");
					Thread.sleep(30000);
					Wait += 5;
					Status = "FAIL";
				}
			} while (Wait < 100);
			Result.fUpdateLog("test ----->");

		} catch (Exception e) {
			Status = "FAIL";
			Result.fUpdateLog("Faile due to Exception " + e);

		}
		return Status + "@@" + Test_OutPut + "<br/>";
	}

}
