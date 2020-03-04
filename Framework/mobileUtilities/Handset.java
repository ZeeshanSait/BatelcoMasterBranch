package mobileUtilities;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Libraries.Driver;
import Libraries.Result;

public class Handset extends Driver {

	public static String SMSC = "\"+" + utils.fetchData("SMSC") + "\",145";

	public String RestartMobile() {
		String Test_OutPut = "", Status = "", DeviceName;
		DeviceName = utils.fetchData("DeviceName");
		try {
			utils.restartMobile(DeviceName);
			Thread.sleep(60000);
			Status = "PASS";

		} catch (Exception e) {
			Result.fUpdateLog("Mobile was not restarted because of " + e);
			Test_OutPut += "Mobile was not restarted because of " + e;
			Status = "FAIL";
		}

		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public String ConfigureSMSC() {
		Result.fUpdateLog("*** Starting SMSC Configuration ***");
		String Test_OutPut = "", Status = "";
		try {
			SetCapabilities.dr.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			SetCapabilities.dr.findElement(By.id("com.android.dialer:id/floating_action_button")).click();
			SetCapabilities.dr.findElement(By.id("com.android.dialer:id/digits")).click();
			SetCapabilities.dr.findElement(By.id("com.android.dialer:id/digits")).sendKeys("*#*#4636#*#*");
			WebDriverWait wait = new WebDriverWait(SetCapabilities.dr, 120);
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//*[@class='android.widget.TextView' and @text='Phone information']")));
			SetCapabilities.dr
					.findElement(By.xpath("//*[@class='android.widget.TextView' and @text='Phone information']"))
					.click();
			Thread.sleep(2000);
			Runtime run = Runtime.getRuntime();
			run.exec("adb shell input swipe 100 1100 100 100");
			SetCapabilities.dr.findElement(By.id("com.android.settings:id/refresh_smsc")).click();
			SetCapabilities.dr.findElement(By.id("com.android.settings:id/smsc")).clear();
			SetCapabilities.dr.findElement(By.id("com.android.settings:id/smsc")).sendKeys(SMSC);
			SetCapabilities.dr.findElement(By.id("com.android.settings:id/update_smsc")).click();
			if ((SetCapabilities.dr.findElement(By.id("com.android.settings:id/smsc")).getText()).contains(SMSC)) {
				Status = "PASS";
				Result.fUpdateLog("SMSC Center is set to: " + SMSC);
				Test_OutPut += "SMSC Center is set to: " + SMSC + "<br/>";
			} else {
				Status = "PASS";
				Result.fUpdateLog("SMSC Center was not set.");
			}
		} catch (Exception e) {
			Result.fUpdateLog("Exception Occured" + e);
			Status = "FAIL";
		}
		Result.fUpdateLog("*** Completed SMSC Configuration ***");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

}
