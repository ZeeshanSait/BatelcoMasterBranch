package mobileUtilities;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;

import Libraries.Driver;
import Libraries.Result;

public class ECR extends Driver {

	public String verifyECRLogin() {
		String Test_OutPut = "", Status = "", pinKey = "null", keyval;
		try {
			cDriver.get().manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			cDriver.get().findElement(By.id("com.celfocus.vdf_qatar_ecr:id/field_user_selection")).click();
			// cDriver.get().findElement(By.id("com.celfocus.vdf_qatar_ecr:id/editTextSearchBox")).click();
			cDriver.get().findElement(By.id("com.celfocus.vdf_qatar_ecr:id/editTextSearchBox"))
					.sendKeys(utils.getdata("UserName"));
			cDriver.get().findElement(By.id("com.celfocus.vdf_qatar_ecr:id/row_text")).click();
			int i = -1;
			while (i < 9) {
				i++;
				keyval = cDriver.get().findElement(By.id("com.celfocus.vdf_qatar_ecr:id/pinpad_pos" + i)).getText();
				// System.out.println("i value -->"+i + " KeyValue-->" + keyval);
				if (keyval.equalsIgnoreCase("0")) {
					pinKey = "com.celfocus.vdf_qatar_ecr:id/pinpad_pos" + i;
				}
			}
			cDriver.get().findElement(By.id(pinKey)).click();
			cDriver.get().findElement(By.id(pinKey)).click();
			cDriver.get().findElement(By.id(pinKey)).click();
			cDriver.get().findElement(By.id(pinKey)).click();
			if (cDriver.get().findElement(By.id("com.celfocus.vdf_qatar_ecr:id/img_logout_option")).isDisplayed()) {
				Result.fUpdateLog("Successfully Logged in");
				Status = "PASS";
			}

		} catch (Exception e) {
			Result.fUpdateLog("Exception Occured " + e);
			Status = "FAIL";
		}
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public String prepaidProvisioning() {
		String Test_OutPut = "", Status = "";
		try {
			cDriver.get().manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			cDriver.get().findElement(By.id("com.celfocus.vdf_qatar_ecr:id/layout_section_customer")).click();
			cDriver.get().findElement(By.id("com.celfocus.vdf_qatar_ecr:id/layout_action_mobile_prepaid")).click();

		} catch (Exception e) {
			Result.fUpdateLog("Exception Occured " + e);
			Status = "FAIL";
		}
		return Status + "@@" + Test_OutPut + "<br/>";
	}
}
