package mobileUtilities;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Libraries.Driver;
import Libraries.Result;
import Libraries.Utlities;

public class Dialers extends Driver {
	public String MobileNumber;

	public String Dialer() {
		String Test_OutPut = "", Status = "";
		try {
			System.out.println("*** Dialing Number on Mobiles ***");
			MobileNumber = utils.fetchData("CallingNumber");
			SetCapabilities.dr.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			SetCapabilities.dr.findElement(By.id("com.android.dialer:id/floating_action_button")).click();
			SetCapabilities.dr.findElement(By.id("com.android.dialer:id/digits")).click();
			utils.takeScreenShot();
			SetCapabilities.dr.findElement(By.id("com.android.dialer:id/digits")).sendKeys(MobileNumber);
			Result.fUpdateLog("Dialing on Mobile Number: " + MobileNumber);
			Test_OutPut += "Dailed Number is: " + MobileNumber;
			utils.takeScreenShot();
			SetCapabilities.dr.findElement(By.id("com.android.dialer:id/dialpad_floating_action_button")).click();
			utils.takeScreenShot();
			try {
				WebDriverWait wait = new WebDriverWait(SetCapabilities.dr, 30);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.android.incallui:id/elapsedTime")));
				Result.fUpdateLog("*** Call Was Successful ***");
				Thread.sleep(2000);
				SetCapabilities.dr.findElement(By.id("com.android.incallui:id/floating_end_call_action_button"))
						.click();
				utils.takeScreenShot();
				SetCapabilities.dr.quit();
				Status = "PASS";
			} catch (Exception e) {
				SetCapabilities.dr.quit();
				Status = "FAIL";
				Test_OutPut += "Call was failed due to Other Party Has not Picked Up the Call or No Network/SIM Found on the mobile";
				Result.fUpdateLog(
						"Call was failed due to Other Party Has not Picked Up the Call or No Network/SIM Found on the mobile or \n");
			}
		} catch (Exception e) {
			SetCapabilities.dr.quit();
			System.out.println(e);
			Status = "FAIL";
		}
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public String smsSender() {
		String Test_OutPut = "", Status = "";
		try {
			MobileNumber = utils.fetchData("CallingNumber");
			Result.fUpdateLog("*** Sending SMS on Mobiles ***");
			SetCapabilities.dr.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			SetCapabilities.dr.findElement(By.id("com.google.android.apps.messaging:id/start_new_conversation_button"))
					.click();
			utils.takeScreenShot();
			SetCapabilities.dr.findElement(By.id("com.google.android.apps.messaging:id/recipient_text_view"))
					.sendKeys(MobileNumber);
			// SetCapabilities.dr.findElement(By.xpath("//*[@class='android.widget.TextView'
			// and @text='FREQUENTS']")).click();
			utils.takeScreenShot();
			SetCapabilities.dr.findElement(By.id("com.google.android.apps.messaging:id/contact_picker_create_group"))
					.click();
			SetCapabilities.dr.findElement(By.id("com.google.android.apps.messaging:id/compose_message_text"))
					.sendKeys("This is a test message send by automation script.");
			utils.takeScreenShot();
			SetCapabilities.dr.findElement(By.id("com.google.android.apps.messaging:id/send_message_button_icon"))
					.click();
			utils.takeScreenShot();
			Thread.sleep(10000);
			try {
				SetCapabilities.dr.findElement(By.xpath("//*[@class='android.widget.TextView' and @text='Now ? SMS']"));
				utils.takeScreenShot();
				Result.fUpdateLog("*** Message Successfully Sent ***");
				Status = "PASS";
			} catch (Exception e) {
				Result.fUpdateLog("SMS was not sent, Please check SMS Center/Network in Mobile");
			}
		} catch (Exception e) {
			System.out.println(e);
			Status = "FAIL";
		}
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public String BalanceCheckDialer() {
		String Test_OutPut = "", Status = "";
		try {
			System.out.println("*** Dialing Balance Check Code on Mobiles ***");
			MobileNumber = utils.fetchData("BalanceCheckCode");
			SetCapabilities.dr.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			SetCapabilities.dr.findElement(By.id("com.android.dialer:id/floating_action_button")).click();
			SetCapabilities.dr.findElement(By.id("com.android.dialer:id/digits")).click();
			utils.takeScreenShot();
			SetCapabilities.dr.findElement(By.id("com.android.dialer:id/digits")).sendKeys(MobileNumber);
			Result.fUpdateLog("Dialing on Mobile Number: " + MobileNumber);
			Test_OutPut += "Balance Check Code is: " + MobileNumber;
			utils.takeScreenShot();
			SetCapabilities.dr.findElement(By.id("com.android.dialer:id/dialpad_floating_action_button")).click();
			try {
				WebDriverWait wait = new WebDriverWait(SetCapabilities.dr, 30);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/button1")));
				utils.takeScreenShot();
				SetCapabilities.dr.findElement(By.id("android:id/button1")).click();
				utils.takeScreenShot();
				SetCapabilities.dr.quit();
				Status = "PASS";
			} catch (Exception e) {
				SetCapabilities.dr.quit();
				Status = "FAIL";
				Test_OutPut += "Balance Check was failed due to USSD Code is not working or No Network/SIM Found on the mobile";
				Result.fUpdateLog(
						"Balance Check was failed due to USSD Code is not working or No Network/SIM Found on the mobile");
			}
		} catch (Exception e) {
			SetCapabilities.dr.quit();
			System.out.println(e);
			Status = "FAIL";
		}
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public String RechargeDialer() {
		String Test_OutPut = "", Status = "", RechargePIN;
		try {
			System.out.println("*** Dialing Recharge Code on Mobiles ***");
			RechargePIN = utils.fetchData("RechargePIN");
			SetCapabilities.dr.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			SetCapabilities.dr.findElement(By.id("com.android.dialer:id/floating_action_button")).click();
			SetCapabilities.dr.findElement(By.id("com.android.dialer:id/digits")).click();
			utils.takeScreenShot();
			SetCapabilities.dr.findElement(By.id("com.android.dialer:id/digits")).sendKeys("*127*" + RechargePIN + "#");
			Result.fUpdateLog("Recharge PIN used is: " + RechargePIN);
			Test_OutPut += "Recharge PIN used is: " + RechargePIN + "</br>";
			SetCapabilities.dr.findElement(By.id("com.android.dialer:id/dialpad_floating_action_button")).click();
			utils.takeScreenShot();
			try {
				WebDriverWait wait = new WebDriverWait(SetCapabilities.dr, 30);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/button1")));
				utils.takeScreenShot();
				SetCapabilities.dr.findElement(By.id("android:id/button1")).click();
				utils.takeScreenShot();
				SetCapabilities.dr.quit();
				Status = "PASS";
			} catch (Exception e) {
				SetCapabilities.dr.quit();
				Status = "FAIL";
				Test_OutPut += "Recharge didn't worked to USSD Code is not working or No Network/SIM Found on the mobile";
				Result.fUpdateLog(
						"Recharge didn't worked to USSD Code is not working or No Network/SIM Found on the mobile");
			}
		} catch (Exception e) {
			SetCapabilities.dr.quit();
			System.out.println(e);
			Status = "FAIL";
		}
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public String CheckBarringCall() {
		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------OG Bar Call Event Details------");
		try {
			String RTB = Validatedata("OGBar_Call");
			if (RTB.equalsIgnoreCase("yes")) {
				String DeviceName = getdata("DeviceName");
				Result.fUpdateLog("Device Name is set to " + DeviceName);
				SetCapabilities.setDialerCapabilities1(DeviceName);
				System.out.println("** Dialing Number on Mobiles **");
				MobileNumber = utils.fetchData("CallingNumber");
				SetCapabilities.dr.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				SetCapabilities.dr.findElement(By.id("com.android.dialer:id/floating_action_button")).click();
				SetCapabilities.dr.findElement(By.id("com.android.dialer:id/digits")).click();
				utils.takeScreenShot();
				SetCapabilities.dr.findElement(By.id("com.android.dialer:id/digits")).sendKeys(MobileNumber);
				Result.fUpdateLog("Dialing on Mobile Number: " + MobileNumber);
				Test_OutPut += "Dailed Number is: " + MobileNumber;
				utils.takeScreenShot();
				SetCapabilities.dr.findElement(By.id("com.android.dialer:id/dialpad_floating_action_button")).click();
				utils.takeScreenShot();
				try {
					WebDriverWait wait = new WebDriverWait(SetCapabilities.dr, 30);
					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.id("com.android.incallui:id/elapsedTime")));
					Result.fUpdateLog("** Call Was Successful **");
					utils.takeScreenShot();
					Thread.sleep(2000);
					SetCapabilities.dr.findElement(By.id("com.android.incallui:id/floating_end_call_action_button"))
							.click();
					utils.takeScreenShot();
					SetCapabilities.dr.quit();
					Test_OutPut += "Call was successful";
					Result.fUpdateLog("Call was successful");
					Status = "FAIL";
				} catch (Exception e) {
					Status = "PASS";
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			Status = "FAIL";
		}
		Result.fUpdateLog("------OG Bar Call Event Details------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public String CheckUnBarringCall() {
		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------OG Bar Call Event Details------");
		try {
			String RTB = Validatedata("OGUnBar_Call");
			if (RTB.equalsIgnoreCase("yes")) {
				String DeviceName = getdata("DeviceName");
				Result.fUpdateLog("Device Name is set to " + DeviceName);
				SetCapabilities.setDialerCapabilities1(DeviceName);
				System.out.println("** Dialing Number on Mobiles **");
				MobileNumber = utils.fetchData("CallingNumber");
				SetCapabilities.dr.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				SetCapabilities.dr.findElement(By.id("com.android.dialer:id/floating_action_button")).click();
				SetCapabilities.dr.findElement(By.id("com.android.dialer:id/digits")).click();
				utils.takeScreenShot();
				SetCapabilities.dr.findElement(By.id("com.android.dialer:id/digits")).sendKeys(MobileNumber);
				Result.fUpdateLog("Dialing on Mobile Number: " + MobileNumber);
				Test_OutPut += "Dailed Number is: " + MobileNumber;
				utils.takeScreenShot();
				SetCapabilities.dr.findElement(By.id("com.android.dialer:id/dialpad_floating_action_button")).click();
				utils.takeScreenShot();
				try {
					WebDriverWait wait = new WebDriverWait(SetCapabilities.dr, 30);
					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.id("com.android.incallui:id/elapsedTime")));
					Result.fUpdateLog("** Call Was Successful **");
					utils.takeScreenShot();
					Thread.sleep(2000);
					SetCapabilities.dr.findElement(By.id("com.android.incallui:id/floating_end_call_action_button"))
							.click();
					SetCapabilities.dr.quit();
					Test_OutPut += "Call was successful";
					Result.fUpdateLog("Call was successful");
					Status = "PASS";
				} catch (Exception e) {
					Status = "PASS";
					Status = "FAIL";
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			Status = "FAIL";
		}
		Result.fUpdateLog("------OG Bar Call Event Details------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public String BillEnquiryDialler() {
		String Text = "", Test_OutPut = "", Status = "";
		;
		try {
			Result.fUpdateLog("*** Dialing Bill Enquiry Code on Mobiles ***");
			MobileNumber = utils.fetchData("BillEnquiryCode");
			SetCapabilities.dr.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			SetCapabilities.dr.findElement(By.id("com.android.dialer:id/floating_action_button")).click();
			SetCapabilities.dr.findElement(By.id("com.android.dialer:id/digits")).click();
			utils.takeScreenShot();
			SetCapabilities.dr.findElement(By.id("com.android.dialer:id/digits")).sendKeys(MobileNumber);
			Result.fUpdateLog("Dialing on Mobile Number: " + MobileNumber);
			utils.takeScreenShot();
			SetCapabilities.dr.findElement(By.id("com.android.dialer:id/dialpad_floating_action_button")).click();
			// Thread.sleep(5000);
			try {
				WebDriverWait wait = new WebDriverWait(SetCapabilities.dr, 30);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/button1")));
				utils.takeScreenShot();
				Text = SetCapabilities.dr.findElement(By.id("android:id/message")).getText();
				Result.fUpdateLog(Text);
				SetCapabilities.dr.findElement(By.id("android:id/button1")).click();
				utils.takeScreenShot();
				double DueNow = utils.getDueNowUSSDBillEnquiry(Text);
				double Unbilled = utils.getUnbilledUSSDBillEnquiry(Text);
				Test_OutPut += "Previous Month Outstanding Bill for MSISDN " + utils.fetchData("MSISDN") + " is QAR "
						+ DueNow + "</br>";
				Test_OutPut += "Current Month Unbilled Usage for MSISDN " + utils.fetchData("MSISDN") + " is QAR "
						+ Unbilled + "</br>";
				Test_OutPut += "Total Outstanding for MSISDN" + utils.fetchData("MSISDN") + " is QAR "
						+ (DueNow + Unbilled) + "</br>";
				double SiebelDueNow = Double
						.parseDouble(Utlities.FetchStoredValue("BillEnquiry_USSD", "BillEnquiry_USSD", "RTB_DueNow"));
				double SiebelUnbilled = Double.parseDouble(
						Utlities.FetchStoredValue("BillEnquiry_USSD", "BillEnquiry_USSD", "RTB_UnbilledUsage"));
				double SiebelTotal = Double
						.parseDouble(Utlities.FetchStoredValue("BillEnquiry_USSD", "BillEnquiry_USSD", "RTB_Total"));
				SetCapabilities.dr.quit();
				if ((DueNow == SiebelDueNow) && (Unbilled == SiebelUnbilled) && ((DueNow + Unbilled) == SiebelTotal)) {
					Status = "PASS";
				} else {
					SetCapabilities.dr.quit();
					Result.fUpdateLog("There is some mismatch in RTB and USSD values");
					Test_OutPut += "There is some mismatch in RTB and USSD values";
					Status = "FAIL";
				}

			} catch (Exception e) {
				SetCapabilities.dr.quit();
				Test_OutPut += "Bill Enquiry was failed due to USSD Code is not working or No Network/SIM Found on the mobile";
				Result.fUpdateLog(
						"Bill Enquiry was failed due to USSD Code is not working or No Network/SIM Found on the mobile");
				Status = "FAIL";
			}
		} catch (Exception e) {
			System.out.println(e);
			Status = "FAIL";
		}
		return Status + "@@" + Test_OutPut + "<br/>";
	}

}
