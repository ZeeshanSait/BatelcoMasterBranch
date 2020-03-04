package mobileUtilities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Libraries.Result;
import mobileUtilities.SetCapabilities;
import mobileUtilities.utils;
import Libraries.Driver;

public class USSD extends Driver {

	public String VerifyRecharge() {
		Result.fUpdateLog("*** Starting Verify Recharge ***");
		String Status = "", Test_OutPut = "", BucketID, RechargeType;
		double Value, pre, post;
		try {
			BucketID = utils.fetchData("BucketID");
			Value = Double.parseDouble(utils.fetchData("RechargeValue"));
			RechargeType = utils.fetchData("RechargeType");
			pre = utils.balance(BucketID, "pre");
			post = utils.balance(BucketID, "post");
			if (Value == (post - pre)) {
				Status = "PASS";
				Test_OutPut += "Recharge Benefits added " + Value + RechargeType;
				Result.fUpdateLog("Recharge benifits are added successfuly in RTB.");
			} else {
				Status = "FAIL";
				Test_OutPut += "Recharge Benefits added " + (post - pre) + RechargeType + ". But expected " + Value
						+ RechargeType + "</br>";
				Result.fUpdateLog("There is mismatch in expected and actual recharge benifits.");
			}
		} catch (Exception e) {
			Status = "FAIL";
			Test_OutPut += "Failed due to Exception " + e;
			Result.fUpdateLog("Failed due to Exception " + e);
		}
		Result.fUpdateLog("*** Exiting Verify Recharge ***");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public String InvokeUSSDMenu() {
		String Test_OutPut = "", Status = "", USSDMenu;
		Result.fUpdateLog("*** Starting USSD Menu ***");
		try {
			USSDMenu = utils.fetchData("USSDMenu");
			SetCapabilities.dr.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			SetCapabilities.dr.findElement(By.id("com.android.dialer:id/floating_action_button")).click();
			SetCapabilities.dr.findElement(By.id("com.android.dialer:id/digits")).click();
			SetCapabilities.dr.findElement(By.id("com.android.dialer:id/digits")).sendKeys(USSDMenu);
			SetCapabilities.dr.findElement(By.id("com.android.dialer:id/dialpad_floating_action_button")).click();
			utils.takeScreenShot();
			try {
				WebDriverWait wait = new WebDriverWait(SetCapabilities.dr, 30);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/button1")));
				Result.fUpdateLog("USSD Menu Invoked Successfully");
				// SetCapabilities.dr.quit();
				Status = "PASS";
			} catch (Exception e) {
				Status = "FAIL";
				Test_OutPut += "USSD Mene Invoke Failed";
				Result.fUpdateLog("USSD Mene Invoke Failed");
			}
		} catch (Exception e) {
			System.out.println(e);
			Status = "FAIL";
		}
		Result.fUpdateLog("*** Exiting Invoke USSD Menu ***");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public String USSDJourney() {
		utils.timestamp = new SimpleDateFormat("M/dd/YYYY hh:mm:ss aa").format(Calendar.getInstance().getTime());
		String Status = "", Test_OutPut = "", USSDJourney;
		Result.fUpdateLog("*** Starting USSD Journey ***");
		USSDJourney = utils.fetchData("USSDJourney");
		String[] journey = USSDJourney.split(",");
		int len = journey.length;
		try {
			for (int i = 0; i <= len - 1; i++) {
				if (i != len - 1) {
					SetCapabilities.dr.findElement(By.id("com.android.phone:id/input_field")).sendKeys(journey[i]);
					utils.takeScreenShot();
					SetCapabilities.dr.findElement(By.id("android:id/button1")).click();
					Status = "PASS";
				} else {
					SetCapabilities.dr.findElement(By.id("com.android.phone:id/input_field")).sendKeys(journey[i]);
					utils.takeScreenShot();
					SetCapabilities.dr.findElement(By.id("android:id/button1")).click();
					utils.takeScreenShot();
					Status = "PASS";
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			Status = "FAIL";
		}
		Result.fUpdateLog("*** Exiting USSD Journey ***");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public String USSDCleaner() {
		String Status = "", Test_OutPut = "";
		Result.fUpdateLog("*** Starting USSD Cleaner ***");
		try {
			SetCapabilities.dr.findElement(By.id("android:id/button1")).click();
			Status = "PASS";
		} catch (Exception e) {
			System.out.println(e);
			Status = "FAIL";
		}
		SetCapabilities.dr.quit();
		Result.fUpdateLog("*** Exiting USSD Cleaner ***");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public String PunchRechargePIN() {
		String Status = "", Test_OutPut = "", RechargePIN;
		Result.fUpdateLog("*** Starting Punch Recharge PIN ***");
		RechargePIN = utils.fetchData("RechargePIN");
		try {
			SetCapabilities.dr.findElement(By.id("com.android.phone:id/input_field")).sendKeys(RechargePIN);
			Test_OutPut += "Recharge PIN used: " + RechargePIN + "<br/>";
			Result.fUpdateLog("Recharge PIN used: " + RechargePIN);
			utils.takeScreenShot();
			SetCapabilities.dr.findElement(By.id("android:id/button1")).click();
			WebDriverWait wait = new WebDriverWait(SetCapabilities.dr, 30);
			String Text = SetCapabilities.dr.findElement(By.id("android:id/message")).getText();
			Result.fUpdateLog(Text);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/button1")));
			SetCapabilities.dr.findElement(By.id("android:id/button1")).click();
			SetCapabilities.dr.quit();
			if (Text.contains("already been used")) {
				Status = "FAIL";
				Test_OutPut += "Recharge PIN has already been used.>";
			} else {
				Status = "PASS";
			}
		} catch (Exception e) {
			System.out.println(e);
			Status = "FAIL";
		}
		Result.fUpdateLog("*** Exiting Punch Recharge PIN ***");
		return Status + "@@" + Test_OutPut + "<br/>";

	}

	public String VerifyProductActivationPrepaid() {
		Result.fUpdateLog("*** Starting Verify Product Activation ***");
		String Status = "", Test_OutPut = "", BucketID, BucketType;
		double Value, pre, post;
		try {
			BucketID = utils.fetchData("BucketID");
			Value = Double.parseDouble(utils.fetchData("BucketValue"));
			BucketType = utils.fetchData("BucketType");
			pre = utils.balance(BucketID, "pre");
			post = utils.balance(BucketID, "post");
			if (Value == (post - pre)) {
				Status = "PASS";
				Test_OutPut += "Benefits are added " + Value + BucketType;
				Result.fUpdateLog("Benifits are added successfuly in RTB.");
			} else {
				Status = "FAIL";
				Test_OutPut += "Benefits are added " + (post - pre) + BucketType + ". But expected " + Value
						+ BucketType + "</br>";
				Result.fUpdateLog("There is mismatch in expected and actual benifits.");
			}
		} catch (Exception e) {
			Status = "FAIL";
			Test_OutPut += "Failed due to Exception " + e;
			Result.fUpdateLog("Failed due to Exception " + e);
		}
		Result.fUpdateLog("*** Exiting Verify Product Activation ***");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public String VerifyProductDeActivationPrepaid() {
		String Status = "", Test_OutPut = "";
		Test_OutPut += "Manual Verification is required to Pass this Case. ";
		Status = "PartiallyPass";
		return Status + "@@" + Test_OutPut + "<br/>";
	}

}
