package mobileUtilities;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import Libraries.Driver;
import Libraries.Result;

@SuppressWarnings("unused")
public class Notification extends Driver {
	public String MSG;

	public String BalanceCheck() {
		String Test_OutPut = "", Status = "";
		try {
			SetCapabilities.dr.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			SetCapabilities.dr.findElement(By.xpath("//*[@class='android.widget.TextView' and @text='Vodafone']"))
					.click();
			WebElement message = SetCapabilities.dr
					.findElementById("com.google.android.apps.messaging:id/message_text");
			MSG = SetCapabilities.dr.findElementById("com.google.android.apps.messaging:id/message_text").getText();
			utils.takeScreenShot();
			SetCapabilities.dr
					.findElement(By.xpath("//*[@class='android.widget.ImageView' and @content-desc='More options']"))
					.click();
			SetCapabilities.dr.findElement(By.xpath("//*[@class='android.widget.TextView' and @text='Delete']"))
					.click();
			SetCapabilities.dr.findElementById("android:id/button1").click();
			if (MSG != null) {
				Test_OutPut += MSG;
				Result.fUpdateLog("Below SMS Notification Recieved in SMS: </br>" + MSG);
				Status = "PASS";
			} else
				Status = "FAIL";
			SetCapabilities.dr.quit();
		} catch (Exception e) {
			SetCapabilities.dr.quit();
			e.printStackTrace();
		}
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public String FlexBalanceCheck() {
		String Test_OutPut = "", Status = "";
		try {
			SetCapabilities.dr.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			SetCapabilities.dr
					.findElement(By.xpath("//*[@class='android.widget.TextView' and @text='Vodafone Customer Care']"))
					.click();
			WebElement message = SetCapabilities.dr
					.findElementById("com.google.android.apps.messaging:id/message_text");
			MSG = SetCapabilities.dr.findElementById("com.google.android.apps.messaging:id/message_text").getText();
			utils.takeScreenShot();
			SetCapabilities.dr
					.findElement(By.xpath("//*[@class='android.widget.ImageView' and @content-desc='More options']"))
					.click();
			SetCapabilities.dr.findElement(By.xpath("//*[@class='android.widget.TextView' and @text='Delete']"))
					.click();
			SetCapabilities.dr.findElementById("android:id/button1").click();
			if (MSG != null) {
				Test_OutPut += MSG;
				Result.fUpdateLog("Below SMS Notification Recieved in SMS: </br>" + MSG);
				Status = "PASS";
			} else {
				SetCapabilities.dr.quit();
				Status = "FAIL";
			}

		} catch (Exception e) {
			SetCapabilities.dr.quit();
			e.printStackTrace();
		}
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public String ProductInfoNotification() {
		String Test_OutPut = "", Status = "";
		try {
			SetCapabilities.dr.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			SetCapabilities.dr
					.findElement(By.xpath("//*[@class='android.widget.TextView' and @text='Vodafone Customer Care']"))
					.click();
			WebElement message = SetCapabilities.dr
					.findElementById("com.google.android.apps.messaging:id/message_text");
			MSG = SetCapabilities.dr.findElementById("com.google.android.apps.messaging:id/message_text").getText();
			utils.takeScreenShot();
			SetCapabilities.dr
					.findElement(By.xpath("//*[@class='android.widget.ImageView' and @content-desc='More options']"))
					.click();
			SetCapabilities.dr.findElement(By.xpath("//*[@class='android.widget.TextView' and @text='Delete']"))
					.click();
			SetCapabilities.dr.findElementById("android:id/button1").click();
			if (MSG != null) {
				Test_OutPut += MSG;
				Result.fUpdateLog("Below SMS Notification Recieved in SMS: </br>" + MSG);
				Status = "PASS";
			} else {
				SetCapabilities.dr.quit();
				Status = "FAIL";
			}
		} catch (Exception e) {
			SetCapabilities.dr.quit();
			Result.fUpdateLog("Product Info Message is not recieved or failed due to Exception " + e);
			Status = "FAIL";
		}
		return Status + "@@" + Test_OutPut + "<br/>";
	}
}
