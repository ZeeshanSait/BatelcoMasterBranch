package mobileUtilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Libraries.Driver;
import Libraries.Result;
import Libraries.Utlities;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;

@SuppressWarnings("deprecation")
public class MCare extends Driver {

	public static String installMyVodafoneApp() {
		String Test_OutPut = "", Status = "", DeviceName, Path;
		DeviceName = utils.fetchData("DeviceName");
		Path = utils.fetchData("Path");
		try {
			FileReader reader = new FileReader("Framework/config/config.properties");
			Properties p = new Properties();
			p.load(reader);
			Runtime run = Runtime.getRuntime();
			Result.fUpdateLog("Uninstalling Older version of Mcare App");
			run.exec("adb uninstall  qa.vodafone.myvodafone.devel.beta");
			Thread.sleep(2000);
			run.exec("adb uninstall  qa.vodafone.myvodafone");
			Thread.sleep(2000);
			Result.fUpdateLog("Uninstallation Completed");
			Result.fUpdateLog("Installing the Given Version of MCare");
			/// Result.fUpdateLog("adb -s "+ p.getProperty(DeviceName + "_Id")+" install " +
			/// Path);
			Process proc = run.exec("adb -s " + p.getProperty(DeviceName + "_Id") + " install " + Path
					+ ">NUL && echo Action done successfully || echo Action Failed");
			Thread.sleep(20000);
			BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			String line;
			while ((line = br.readLine()) != null)
				System.out.println(line);

			Result.fUpdateLog("Instalation Completed");
			Status = "PASS";
		} catch (Exception e) {
			Status = "FAIL";
			Result.fUpdateLog(e.toString());
		}
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public static String verifyMCareLogin() {
		String Test_OutPut = "", Status = "";
		int MobNum = 0, VoV = 0, Avatar = 0, NeedHelp = 0, Gauge = 0, Menu = 0;
		try {
			// String DeviceName = utils.fetchData("DeviceName");
			FileReader reader = new FileReader("Framework/config/config.properties");
			Properties p = new Properties();
			p.load(reader);
			Thread.sleep(2000);
			utils.takeScreenShot();
			// Runtime run = Runtime.getRuntime();
			// String cmd = "adb -s " + p.getProperty(DeviceName + "_Id") + " shell input
			// swipe 100 1100 100 100";
			// run.exec(cmd);
			// Thread.sleep(1000);
			// utils.takeScreenShot();
			if (utils.fetchData("Env").equals("Test")) {
				Scroll("OK");
			} else {
				Scroll("OK! Let's Start!");
			}
			allowAppPermission();
			utils.takeScreenShot();
			Wait(Xpath.Menu);
			SetCapabilities.dr.findElement(By.xpath(Xpath.Menu)).click();
			Scroll("Switcher");
			utils.takeScreenShot();
			String MSISDN1 = utils.fetchData("MSISDN");
			MSISDN1 = MSISDN1.substring(3, 11);
			Wait(Xpath.MsisdnEntry);
			SetCapabilities.dr.findElement(By.xpath(Xpath.MsisdnEntry)).sendKeys(MSISDN1);
			Wait(Xpath.Env);
			((WebElement) SetCapabilities.dr.findElements(By.xpath(Xpath.Env)).get(1)).click();

			// SetCapabilities.dr.findElement(By.xpath(Xpath.Env)).click();
			SetCapabilities.dr.findElement(By.xpath("//*[contains(@text,'UAT')]")).click();
			Wait(Xpath.Switchbutton);
			SetCapabilities.dr.findElement(By.xpath(Xpath.Switchbutton)).click();
			SetCapabilities.dr.findElement(By.xpath(Xpath.Login)).click();
			utils.takeScreenShot();
			Wait(Xpath.Menu);
			SetCapabilities.dr.findElement(By.xpath(Xpath.Menu)).click();
			Scroll("Home");
			// swipeVertical((AppiumDriver) SetCapabilities.dr, 0.1, 0.9, 0.5, 3000);
			// SetCapabilities.dr.findElement(By.xpath(Xpath.Home)).click();
			utils.takeScreenShot();
			Wait(Xpath.SkipTutorial);
			SetCapabilities.dr.findElement(By.xpath(Xpath.SkipTutorial)).click();
			Thread.sleep(3000);
			try {
				if (SetCapabilities.dr.findElement(By.id(Xpath.AlertCancel)).isDisplayed())
					SetCapabilities.dr.findElement(By.id(Xpath.AlertCancel)).click();
			} catch (Exception e) {
			}

			Wait("//android.widget.TextView[contains(@text,'+974')]");

			if (SetCapabilities.dr.findElement(By.xpath("//*[@class='android.widget.ImageView' and @index='0']"))
					.isDisplayed()) {

				Result.fUpdateLog("Home Page Loaded Successfully, Verifying other objects.");
				if (SetCapabilities.dr.findElement(By.xpath("//*[@class='android.widget.ImageView' and @index='0']"))
						.isDisplayed()) {
					SetCapabilities.dr.findElement(By.xpath("//*[@class='android.widget.ImageView' and @index='0']"))
							.click();

					Wait(Xpath.AvatarScreen);
					// Wait(Xpath.QuickPay1);
					Result.fUpdateLog("Avatar Screen is loaded");
					utils.takeScreenShot();
					Test_OutPut += "Avatar Screen isloaded </br>";
					SetCapabilities.dr.findElement(By.xpath(Xpath.Menu)).click();
					SetCapabilities.dr.findElement(By.xpath(Xpath.Home)).click();

				}
				Result.fUpdateLog("Avatar Verified on Dashboard");
				Test_OutPut += "Avatar Verified on Dashboard </br>";
				Avatar = 1;
				Wait(Xpath.VoV);
				if (SetCapabilities.dr.findElement(By.xpath(Xpath.VoV)).isDisplayed()) {
					Result.fUpdateLog("VoV Verified on Dashboard");
					utils.takeScreenShot();
					Test_OutPut += "VoV Verified on Dashboard </br>";
					VoV = 1;
				} else {
					Result.fUpdateLog("VoV is not found on Dashboard");
					Test_OutPut += "VoV is not found on Dashboard </br>";
					VoV = 0;
				}

				if (SetCapabilities.dr.findElement(By.xpath(Xpath.BurgerMenu)).isDisplayed()) {
					Result.fUpdateLog("Burger Menu is Verified on Dashboard");
					Test_OutPut += "Burger Menu is Verified on Dashboard </br>";
					// utils.takeScreenShot();
					Menu = 1;
				} else {
					Result.fUpdateLog("Burger Menu is not found on Dashboard");
					Test_OutPut += "Burger Menu is not found on Dashboard </br>";
					Menu = 0;
				}

				String Mob = SetCapabilities.dr
						.findElement(By.xpath("//android.widget.TextView[contains(@text,'+974')]")).getText()
						.toString();
				System.out.println(Mob);
				Test_OutPut += "Avatar Icon Verified. </br>";
				Mob = Mob.substring(5, 13).trim();
				String MSISDN = utils.fetchData("MSISDN");
				MSISDN = MSISDN.substring(3, 11);
				if (Mob.equals(MSISDN)) {
					Result.fUpdateLog("Mobile No. Verified on Dashboard");
					// utils.takeScreenShot();
					Test_OutPut += "Mobile No. Verified on Dashboard </br>";
					MobNum = 1;
				} else {
					Result.fUpdateLog("Mobile No. is wrong on Dashboard");
					Test_OutPut += "Mobile No. is wrong on Dashboard </br>";
					MobNum = 0;
				}

				if (SetCapabilities.dr.findElement(By.xpath(Xpath.NeedHelp)).isDisplayed()) {
					SetCapabilities.dr.findElement(By.xpath(Xpath.NeedHelp)).click();
					if (SetCapabilities.dr.findElement(By.xpath(Xpath.AskHani)).isDisplayed()) {
						Result.fUpdateLog("Need Help Verified on Dashboard");
						utils.takeScreenShot();
						Test_OutPut += "Need Help Verified on Dashboard </br>";
						NeedHelp = 1;
						SetCapabilities.dr.findElement(By.xpath(Xpath.Cancel)).click();
					}

				} else {
					Result.fUpdateLog("Need Help is not found on Dashboard");
					Test_OutPut += "Need Help is not found on Dashboard </br>";
					NeedHelp = 0;
				}
				if (SetCapabilities.dr.findElement(By.xpath(Xpath.GaugeLayout)).isDisplayed()) {
					Result.fUpdateLog("Gauge Verified on Dashboard");
					// utils.takeScreenShot();
					Test_OutPut += "Gauge Verified on Dashboard </br>";
					Gauge = 1;
				} else {
					Result.fUpdateLog("Gauge is not found on Dashboard");
					Test_OutPut += "Gauge is not found on Dashboard </br>";
					Gauge = 0;
				}
				utils.takeScreenShot();
			} else {
				Result.fUpdateLog("For some reasons, MCare Dashboard is not loaded properly");
				Test_OutPut += "For some reasons, MCare Dashboard is not loaded properly </br>";
				Avatar = 0;
			}

			if ((Avatar + VoV + NeedHelp + Gauge + MobNum + Menu) == 6)
				Status = "PASS";
			else {
				SetCapabilities.dr.quit();
				Status = "FAIL";
			}
			// MenuVerify();
		} catch (Exception e) {
			Status = "FAIL";
			e.printStackTrace();
			Result.fUpdateLog("Exception Occured ");
			SetCapabilities.dr.quit();
		}
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public static String Billpay() {
		String Test_OutPut = "", Status = "";
		try {
			utils.takeScreenShot();
			SetCapabilities.dr.findElement(By.xpath(Xpath.Menu)).click();
			Wait(Xpath.BillingPayments);

			if (SetCapabilities.dr.findElement(By.xpath(Xpath.BillingPayments)).isDisplayed()) {
				SetCapabilities.dr.findElement(By.xpath(Xpath.BillingPayments)).click();
				Wait(Xpath.retrivebill);
				utils.takeScreenShot();
				// String home = cDriver.get().getCurrentUrl();
				if (SetCapabilities.dr.findElement(By.xpath(Xpath.retrivebill)).isDisplayed()) {
					SetCapabilities.dr.findElement(By.xpath(Xpath.retrivebill)).click();
					Wait(Xpath.retrivebill);
					utils.takeScreenShot();

					SetCapabilities.dr.findElement(By.xpath(Xpath.IDNum)).sendKeys(utils.fetchData("IDNumber"));
					SetCapabilities.dr.findElement(By.xpath(Xpath.ACCNum)).sendKeys(utils.fetchData("MSISDN"));

					SetCapabilities.dr.hideKeyboard();
					SetCapabilities.dr.findElement(By.xpath(Xpath.Retrive)).click();
					Wait(Xpath.Retrivepay);
					utils.takeScreenShot();
					SetCapabilities.dr.findElement(By.xpath(Xpath.Retrivepay)).click();
					String Card = utils.fetchData("CardType");
					String Mob = SetCapabilities.dr.findElement(By.xpath(Xpath.Oustanding)).getText().toString();

					Result.fUpdateLog("Oustanding Amount" + Mob);
					Test_OutPut += "Oustanding Amount" + Mob;

					if (Card.equalsIgnoreCase("Credit")) {
						SetCapabilities.dr.findElement(By.xpath(Xpath.Creditcard)).click();
					} else {
						SetCapabilities.dr.findElement(By.xpath(Xpath.Debitcard)).click();
					}

					SetCapabilities.dr.findElement(By.xpath(Xpath.Retrive)).click();
					utils.takeScreenShot();
					Wait(Xpath.Cancel);
					utils.takeScreenShot();

					SetCapabilities.dr.findElement(By.xpath(Xpath.Cancel)).click();
					Thread.sleep(5000);
					utils.takeScreenShot();

					Wait(Xpath.retrivebill);

					utils.takeScreenShot();
					SetCapabilities.dr.pressKeyCode(AndroidKeyCode.KEYCODE_MENU);

					Scroll("Logout");
					SetCapabilities.dr.findElement(By.xpath(Xpath.LogoutConfirmation)).click();
					SetCapabilities.dr.closeApp();

				}
			}
			Status = "Pass";

		} catch (Exception e) {
			SetCapabilities.dr.quit();
			e.printStackTrace();
			Result.fUpdateLog("Exception Occured" + e);
			Status = "FAIL";
		}
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public static String OTP_MCareLogin() {
		String Test_OutPut = "", Status = "";
		int MobNum = 0, VoV = 0, Avatar = 0, NeedHelp = 0, Gauge = 0, Menu = 0;
		try {
			// String DeviceName = utils.fetchData("DeviceName");
			FileReader reader = new FileReader("Framework/config/config.properties");
			Properties p = new Properties();
			p.load(reader);
			Thread.sleep(2000);
			String MSISDN1 = utils.fetchData("MSISDN");
			MSISDN1 = MSISDN1.substring(3, 11);
			utils.takeScreenShot();
			if (utils.fetchData("Env").equals("Test")) {
				Scroll("OK");
			} else {
				Scroll("OK! Let's Start!");
			}
			Thread.sleep(1000);
			allowAppPermission();
			utils.takeScreenShot();
			Wait(Xpath.LoginwithSMS);
			SetCapabilities.dr.findElement(By.xpath(Xpath.LoginwithSMS)).click();
			utils.takeScreenShot();
			Wait(Xpath.MSISDN_OTP);
			SetCapabilities.dr.findElement(By.xpath(Xpath.MSISDN_OTP)).sendKeys(MSISDN1);
			utils.takeScreenShot();
			Scroll("Get SMS passcode");
			Thread.sleep(15000);

			// Wait(Xpath.Login);
			Scroll("Login");
			// SetCapabilities.dr.findElement(By.xpath(Xpath.Login)).click();

			Wait(Xpath.SkipTutorial);
			utils.takeScreenShot();
			SetCapabilities.dr.findElement(By.xpath(Xpath.SkipTutorial)).click();
			Thread.sleep(3000);
			try {
				if (SetCapabilities.dr.findElement(By.id(Xpath.AlertCancel)).isDisplayed())
					SetCapabilities.dr.findElement(By.id(Xpath.AlertCancel)).click();
			} catch (Exception e) {
				System.out.println("No alert");
			}
			Thread.sleep(1000);
			Wait("//*[@class='android.widget.ImageView' and @index='0']");
			if (SetCapabilities.dr.findElement(By.xpath("//*[@class='android.widget.ImageView' and @index='0']"))
					.isDisplayed()) {
				Result.fUpdateLog("Home Page Loaded Successfully, Verifying other objects.");
				Result.fUpdateLog("Avatar Verified on Dashboard");
				Test_OutPut += "Avatar Verified on Dashboard </br>";
				Avatar = 1;
				Wait(Xpath.VoV);
				if (SetCapabilities.dr.findElement(By.xpath(Xpath.VoV)).isDisplayed()) {
					Result.fUpdateLog("VoV Verified on Dashboard");
					utils.takeScreenShot();
					Test_OutPut += "VoV Verified on Dashboard </br>";
					VoV = 1;
				} else {
					Result.fUpdateLog("VoV is not found on Dashboard");
					Test_OutPut += "VoV is not found on Dashboard </br>";
					VoV = 0;
				}

				if (SetCapabilities.dr.findElement(By.xpath(Xpath.BurgerMenu)).isDisplayed()) {
					Result.fUpdateLog("Burger Menu is Verified on Dashboard");
					Test_OutPut += "Burger Menu is Verified on Dashboard </br>";
					// utils.takeScreenShot();
					Menu = 1;
				} else {
					Result.fUpdateLog("Burger Menu is not found on Dashboard");
					Test_OutPut += "Burger Menu is not found on Dashboard </br>";
					Menu = 0;
				}

				String Mob = SetCapabilities.dr
						.findElement(By.xpath("//android.widget.TextView[contains(@text,'+974')]")).getText()
						.toString();
				System.out.println(Mob);
				Test_OutPut += "Avatar Icon Verified. </br>";
				Mob = Mob.substring(5, 13).trim();
				String MSISDN = utils.fetchData("MSISDN");
				MSISDN = MSISDN.substring(3, 11);
				if (Mob.equals(MSISDN)) {
					Result.fUpdateLog("Mobile No. Verified on Dashboard");
					// utils.takeScreenShot();
					Test_OutPut += "Mobile No. Verified on Dashboard </br>";
					MobNum = 1;
				} else {
					Result.fUpdateLog("Mobile No. is wrong on Dashboard");
					Test_OutPut += "Mobile No. is wrong on Dashboard </br>";
					MobNum = 0;
				}

				if (SetCapabilities.dr.findElement(By.xpath(Xpath.NeedHelp)).isDisplayed()) {
					SetCapabilities.dr.findElement(By.xpath(Xpath.NeedHelp)).click();
					if (SetCapabilities.dr.findElement(By.xpath(Xpath.AskHani)).isDisplayed()) {
						Result.fUpdateLog("Need Help Verified on Dashboard");
						utils.takeScreenShot();
						Test_OutPut += "Need Help Verified on Dashboard </br>";
						NeedHelp = 1;
						SetCapabilities.dr.findElement(By.xpath(Xpath.Cancel)).click();
					}

				} else {
					Result.fUpdateLog("Need Help is not found on Dashboard");
					Test_OutPut += "Need Help is not found on Dashboard </br>";
					NeedHelp = 0;
				}
				if (SetCapabilities.dr.findElement(By.xpath(Xpath.GaugeLayout)).isDisplayed()) {
					Result.fUpdateLog("Gauge Verified on Dashboard");
					// utils.takeScreenShot();
					Test_OutPut += "Gauge Verified on Dashboard </br>";
					Gauge = 1;
				} else {
					Result.fUpdateLog("Gauge is not found on Dashboard");
					Test_OutPut += "Gauge is not found on Dashboard </br>";
					Gauge = 0;
				}
				utils.takeScreenShot();
			} else {
				Result.fUpdateLog("For some reasons, MCare Dashboard is not loaded properly");
				Test_OutPut += "For some reasons, MCare Dashboard is not loaded properly </br>";
				Avatar = 0;
			}

			if ((Avatar + VoV + NeedHelp + Gauge + MobNum + Menu) == 6)
				Status = "PASS";
			else {
				SetCapabilities.dr.quit();
				Status = "FAIL";
			}

			// MenuVerify();
		} catch (Exception e) {
			Status = "FAIL";
			e.printStackTrace();
			Result.fUpdateLog("Exception Occured ");
			SetCapabilities.dr.quit();
		}
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public static String MenuVerify() {
		String Test_OutPut = "", Status = "";
		int a = 0, b = 0, c = 0, d = 0, e1 = 0, f = 0, g = 0, h = 0;
		try {
			SetCapabilities.dr.findElement(By.xpath(Xpath.Menu)).click();
			Wait(Xpath.ProductServices);
			if (SetCapabilities.dr.findElement(By.xpath(Xpath.ProductServices)).isDisplayed()) {
				SetCapabilities.dr.findElement(By.xpath(Xpath.ProductServices)).click();
				Wait(Xpath.PS_Phone);
				Result.fUpdateLog("ProductServices are loaded");
				utils.takeScreenShot();
				Test_OutPut += "ProductServices are loaded </br>";
				SetCapabilities.dr.findElement(By.xpath(Xpath.Menu)).click();
				a = 1;
			} else {
				Result.fUpdateLog("ProductServices is not found on Dashboard");
				Test_OutPut += "ProductServices is not found on Dashboard </br>";
			}
			// Wait(Xpath.BillingPayments);

			if (SetCapabilities.dr.findElement(By.xpath(Xpath.BillingPayments)).isDisplayed()) {
				SetCapabilities.dr.findElement(By.xpath(Xpath.BillingPayments)).click();
				// Wait(Xpath.MyBill);
				/*
				 * if (SetCapabilities.dr.findElement(By.xpath(Xpath.MyBill)).isDisplayed()) {
				 * SetCapabilities.dr.findElement(By.xpath(Xpath.MyBill)).click();
				 * Wait(Xpath.CurrentSP); Result.fUpdateLog("MyBill Page is loaded");
				 * utils.takeScreenShot(); Test_OutPut += "MyBill Page is loaded </br>";
				 * SetCapabilities.dr.findElement(By.xpath(Xpath.Menu)).click(); b=1; }
				 */
				Wait(Xpath.QuickPay);
				if (SetCapabilities.dr.findElement(By.xpath(Xpath.QuickPay)).isDisplayed()) {
					SetCapabilities.dr.findElement(By.xpath(Xpath.QuickPay)).click();
					Wait(Xpath.QuickPay1);
					Result.fUpdateLog("QuickPay page is loaded");
					utils.takeScreenShot();
					Test_OutPut += "QuickPay page isloaded </br>";
					SetCapabilities.dr.findElement(By.xpath(Xpath.Menu)).click();
					b = c = 1;
				}
				SetCapabilities.dr.findElement(By.xpath(Xpath.BillingPayments)).click();
			} else {
				Result.fUpdateLog("ProductServices is not found on Dashboard");
				Test_OutPut += "ProductServices is not found on Dashboard </br>";
			}
			Wait(Xpath.Recharge);
			if (SetCapabilities.dr.findElement(By.xpath(Xpath.Recharge)).isDisplayed()) {
				SetCapabilities.dr.findElement(By.xpath(Xpath.Recharge)).click();
				Wait(Xpath.Recharge1);
				Result.fUpdateLog("Recharge page is loaded");
				utils.takeScreenShot();
				Test_OutPut += "Recharge page is  loaded </br>";
				SetCapabilities.dr.findElement(By.xpath(Xpath.Menu)).click();
				d = 1;
			} else {
				Result.fUpdateLog("Recharge page not found on Dashboard");
				Test_OutPut += "Recharge page not found on Dashboard </br>";
			}
			// Wait(Xpath.TT);

			// Scroll("Trouble tickets");
			Wait(Xpath.MyInbox);
			if (SetCapabilities.dr.findElement(By.xpath(Xpath.MyInbox)).isDisplayed()) {
				SetCapabilities.dr.findElement(By.xpath(Xpath.MyInbox)).click();
				Wait(Xpath.MyInbox);
				Result.fUpdateLog("MyInbox page is loaded");
				utils.takeScreenShot();
				Test_OutPut += "MyInbox page is  loaded </br>";
				SetCapabilities.dr.findElement(By.xpath(Xpath.Menu)).click();
				e1 = 1;
			} else {
				Result.fUpdateLog("MyInbox not found on Dashboard");
				Test_OutPut += "MYInbox not found on Dashboard </br>";
			}
			Scroll("Trouble tickets");
			Wait(Xpath.TT1);
			if (SetCapabilities.dr.findElement(By.xpath(Xpath.TT1)).isDisplayed()) {
				// SetCapabilities.dr.findElement(By.xpath(Xpath.TT)).click();
				// Wait(Xpath.TT1);
				Result.fUpdateLog("Trouble Ticket page is loaded");
				utils.takeScreenShot();
				Test_OutPut += "Trouble Ticket page is  loaded </br>";
				SetCapabilities.dr.findElement(By.xpath(Xpath.Menu)).click();
				f = 1;
			} else {
				Result.fUpdateLog("TT page not found on Dashboard");
				Test_OutPut += "TT page not found on Dashboard </br>";
			}
			Scroll("Settings");
			Wait(Xpath.Settings);
			if (SetCapabilities.dr.findElement(By.xpath(Xpath.Settings)).isDisplayed()) {
				// SetCapabilities.dr.findElement(By.xpath(Xpath.Settings)).click();
				// Wait(Xpath.Settings);
				Result.fUpdateLog("Settings page is loaded");
				utils.takeScreenShot();
				Test_OutPut += "Settings page is  loaded </br>";
				SetCapabilities.dr.findElement(By.xpath(Xpath.Menu)).click();
				g = 1;
			} else {
				Result.fUpdateLog("Settings page not found on Dashboard");
				Test_OutPut += "SettingsTT page not found on Dashboard </br>";
			}

			Scroll("Need Help?");
			Wait(Xpath.Tutorial);
			if (SetCapabilities.dr.findElement(By.xpath(Xpath.Tutorial)).isDisplayed()) {
				SetCapabilities.dr.findElement(By.xpath(Xpath.Tutorial)).click();
				Wait(Xpath.Cancel);
				SetCapabilities.dr.findElement(By.xpath(Xpath.Cancel)).click();
				Result.fUpdateLog("Tutorial page is loaded");
				utils.takeScreenShot();
				Test_OutPut += "Tutorial page is  loaded </br>";
				// SetCapabilities.dr.findElement(By.xpath(Xpath.Menu)).click();
				h = 1;

			} else {
				Result.fUpdateLog("TT page not found on Dashboard");
				Test_OutPut += "TT page not found on Dashboard </br>";
			}
			if ((a + b + c + d + e1 + f + g + h) == 8)
				Status = "PASS";
			else {
				Status = "FAIL";
				SetCapabilities.dr.quit();
			}

		} catch (Exception e) {
			SetCapabilities.dr.quit();
			e.printStackTrace();
			Result.fUpdateLog("Exception Occured" + e);
			Status = "FAIL";
		}
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public static void Wait(String Path) {
		WebDriverWait wait = new WebDriverWait(SetCapabilities.dr, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Path)));
	}

	public static void Scroll(String Text) throws InterruptedException, IOException {
		MobileElement radioGroup = (MobileElement) SetCapabilities.dr.findElement(MobileBy.AndroidUIAutomator(
				"new UiScrollable(new UiSelector()).scrollIntoView(" + "new UiSelector().text(\"" + Text + "\"));"));
		Thread.sleep(1000);
		utils.takeScreenShot();
		radioGroup.click();
	}

	@SuppressWarnings("rawtypes")
	public static void swipeVertical(AppiumDriver driver, double startPercentage, double finalPercentage,
			double anchorPercentage, int duration) throws Exception {
		Dimension size = SetCapabilities.dr.manage().window().getSize();
		WebElement ele1 = SetCapabilities.dr.findElement(By.xpath(Xpath.NeedHp));
		WebElement ele2 = SetCapabilities.dr.findElement(By.xpath(Xpath.Home));
		int anchor = (int) (size.width * anchorPercentage);
		int startPoint = (int) (size.height * startPercentage);
		int endPoint = (int) (size.height * finalPercentage);
		new TouchAction(driver).press(ElementOption.element(ele1, anchor, startPoint)).waitAction()
				.moveTo(ElementOption.element(ele2, anchor, endPoint)).release().perform();
	}

	public static String verifyPlanNameMCare() {
		String Test_OutPut = "", Status = "";
		try {
			SetCapabilities.dr.findElement(By.xpath("//*[@class='android.widget.ImageView' and @index='1']")).click();
			utils.takeScreenShot();
			String PlanName = SetCapabilities.dr
					.findElement(By.xpath("//android.widget.TextView[contains(@content-desc,'ltr.menu.plan.text')]"))
					.getText().toString();
			Result.fUpdateLog("Mcare Plan Name is " + PlanName);
			Test_OutPut += "Mcare Plan Name is: " + PlanName + "</br>";
			// String SiebelName = "Red 350";
			String SiebelName = Utlities.FetchStoredValue("MCare_VerifyPlanName", "MCare_VerifyPlanName",
					"MCA_PlanName");
			Result.fUpdateLog("Siebel Plan Name is " + SiebelName);
			Test_OutPut += "Siebel Plan Name is: " + SiebelName + "</br>";
			if (SiebelName.toLowerCase().contains(PlanName.toLowerCase())) {
				Status = "PASS";
			} else {
				Result.fUpdateLog("There is some issue with the Plan name, Please check");
				Status = "FAIL";
			}
		} catch (Exception e) {
			Result.fUpdateLog("Exception Occured: " + e);
			Status = "FAIL";
		}
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public static String billEnquiryMCare() {
		String Test_OutPut = "", Status = "", MSISDN, ID, DeviceName, SiebelTotal;
		try {
			FileReader reader = new FileReader("Framework/config/config.properties");
			Properties p = new Properties();
			p.load(reader);
			DeviceName = utils.fetchData("DeviceName");
			MSISDN = utils.fetchData("MSISDN");
			MSISDN = MSISDN.substring(3, 11);
			SiebelTotal = Utlities.FetchStoredValue("MCare_BillEnquiry", "MCare_BillEnquiry", "MCA_Total");
			ID = Utlities.FetchStoredValue("MCare_BillEnquiry", "MCare_BillEnquiry", "MCA_ContactId");
			SetCapabilities.dr.findElement(By.xpath("//*[@class='android.widget.ImageView' and @index='1']")).click();
			SetCapabilities.dr.findElement(By.xpath("//android.widget.TextView[contains(@text,'Billing and Payment')]"))
					.click();
			utils.takeScreenShot();
			SetCapabilities.dr
					.findElement(By.xpath("//android.widget.TextView[contains(@text,'Retrieve a bill & pay')]"))
					.click();
			utils.takeScreenShot();
			String cmd = "adb -s " + p.getProperty(DeviceName + "_Id") + " shell input keyevent 4";
			Runtime run = Runtime.getRuntime();
			SetCapabilities.dr.findElement(By.xpath(
					"//*[@class='android.support.v7.widget.LinearLayoutCompat']//*[contains(@text,'Vodafone number or Account number')]"))
					.sendKeys(MSISDN);
			Thread.sleep(1000);
			run.exec(cmd);
			Thread.sleep(1000);
			SetCapabilities.dr.findElement(By.xpath(
					"//*[@class='android.support.v7.widget.LinearLayoutCompat']//*[contains(@text,'Qatari I.D. or Passport Number')]"))
					.click();
			SetCapabilities.dr.findElement(By.xpath(
					"//*[@class='android.support.v7.widget.LinearLayoutCompat']//*[contains(@text,'Qatari I.D. or Passport Number')]"))
					.sendKeys(ID);
			Thread.sleep(1000);
			run.exec(cmd);
			Thread.sleep(1000);
			SetCapabilities.dr
					.findElement(By
							.xpath("//android.widget.TextView[contains(@content-desc,'retrieve_billing_info.button')]"))
					.click();
			WebDriverWait wait = new WebDriverWait(SetCapabilities.dr, 120);
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//android.widget.TextView[contains(@content-desc,'retrieve_billing_info.text')]")));
			utils.takeScreenShot();
			String BillAmount = SetCapabilities.dr.findElement(By.xpath(
					"//android.support.v7.widget.LinearLayoutCompat[@content-desc='retrieve_billing_info.vertical']//android.widget.TextView[2]"))
					.getText().toString();
			System.out.println(BillAmount + "---->");
			BillAmount = BillAmount.substring(3, BillAmount.length()).trim();
			String[] obj = BillAmount.split(",");
			if (obj.length > 1)
				BillAmount = obj[0].trim() + obj[1].trim();
			Result.fUpdateLog("Total Due Amount on MCare is " + BillAmount);
			Test_OutPut += "Total Due Amount on MCare is " + BillAmount + ",";
			SiebelTotal = SiebelTotal.substring(2, SiebelTotal.length()).trim();
			String[] obj1 = SiebelTotal.split(",");
			if (obj1.length > 1)
				SiebelTotal = obj1[0].trim() + obj1[1].trim();
			Result.fUpdateLog("Total Due Amount on Siebel is " + SiebelTotal);
			Test_OutPut += "Total Due Amount on Siebel is " + SiebelTotal + ",";
			if (BillAmount.equalsIgnoreCase(SiebelTotal))
				Status = "PASS";
			else {
				Result.fUpdateLog("There is some mismatch between the Amount from MCare and Siebel");
				Test_OutPut += "There is some mismatch between the Amount from MCare and Siebel";
				Status = "FAIL";
			}
		} catch (Exception e) {
			e.printStackTrace();
			Result.fUpdateLog("Exception Occured" + e);
			Status = "FAIL";
		}
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	@SuppressWarnings("rawtypes")
	public static String verifyMCareBuckets() {
		String Test_OutPut = "", Status = "";
		int Call = -1, SMS, Data;
		try {
			Call = utils.getBucketTypeCount("Call");
			Data = utils.getBucketTypeCount("Data");
			SMS = utils.getBucketTypeCount("SMS");
			Result.fUpdateLog("Call Buckets are " + Call);
			Result.fUpdateLog("Data Buckets are " + Data);
			Result.fUpdateLog("SMS Buckets are " + SMS);
			if (Call > 0) {
				Result.fUpdateLog("Inside Call Loop");
				while (Call != 0) {
					if (SetCapabilities.dr.findElement(By.id(Xpath.BucketCall)).isDisplayed()) {
						SetCapabilities.dr.findElement(By.id(Xpath.BucketCall)).click();
						WebElement gauge = SetCapabilities.dr.findElement(By.id(Xpath.GaugeLayout));
						int startX = gauge.getLocation().getX() + 22;
						int startY = gauge.getLocation().getY() + 288;
						int endX = (startX + gauge.getSize().getWidth() - 27);
						System.out.println(startX);
						System.out.println(startY);
						System.out.println(endX);
						TouchAction action = new TouchAction(SetCapabilities.dr);
						action.press(new PointOption().withCoordinates(endX, startY)).waitAction()
								.moveTo(new PointOption().withCoordinates(startX, startY)).release().perform();
						Call--;
					}
				}
			}
			if (Data > 0) {
				Result.fUpdateLog("Inside Data Loop");
				while (Data != 0) {
					if (SetCapabilities.dr.findElement(By.id(Xpath.BucketData)).isDisplayed()) {
						SetCapabilities.dr.findElement(By.id(Xpath.BucketData)).click();
						WebElement gauge = SetCapabilities.dr.findElement(By.id(Xpath.GaugeLayout));
						int startX = gauge.getLocation().getX() + 22;
						int startY = gauge.getLocation().getY() + 288;
						int endX = (startX + gauge.getSize().getWidth() - 27);
						System.out.println(startX);
						System.out.println(startY);
						System.out.println(endX);
						utils.takeScreenShot();
						TouchAction action = new TouchAction(SetCapabilities.dr);
						action.press(new PointOption().withCoordinates(endX, startY)).waitAction()
								.moveTo(new PointOption().withCoordinates(startX, startY)).release().perform();
						Data--;
					}
				}
			}
			if (SMS > 0) {
				Result.fUpdateLog("Inside SMS Loop");
				while (SMS != 0) {
					if (SetCapabilities.dr.findElement(By.id(Xpath.BucketSMS)).isDisplayed()) {
						SetCapabilities.dr.findElement(By.id(Xpath.BucketSMS)).click();
						WebElement gauge = SetCapabilities.dr.findElement(By.id(Xpath.GaugeLayout));
						int startX = gauge.getLocation().getX() + 22;
						int startY = gauge.getLocation().getY() + 288;
						int endX = (startX + gauge.getSize().getWidth() - 27);
						System.out.println(startX);
						System.out.println(startY);
						System.out.println(endX);
						utils.takeScreenShot();
						TouchAction action = new TouchAction(SetCapabilities.dr);
						action.press(new PointOption().withCoordinates(endX, startY)).waitAction()
								.moveTo(new PointOption().withCoordinates(startX, startY)).release().perform();
						SMS--;
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public static String PreAddon_Activation() {
		String Test_OutPut = "", Status = "", MVA_PrepaidExtras = "";

		try {
			WebDriverWait wait = new WebDriverWait(SetCapabilities.dr, 10);
			MVA_PrepaidExtras = utils.fetchData("MVA_PrepaidExtras");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Xpath.BurgerMenu)));
			utils.takeScreenShot();
			SetCapabilities.dr.findElement(By.xpath(Xpath.BurgerMenu)).click();
			Scroll("My Products & Services");
			/*
			 * wait.until( ExpectedConditions.visibilityOfElementLocated(By.
			 * xpath("//*[contains(@text,'My Products & Services')]")));
			 * SetCapabilities.dr.findElement(By.
			 * xpath("//*[contains(@text,'My Products & Services')]")).click();
			 */
			Wait(Xpath.PS_Phone);
			if (TestCaseN.get().equalsIgnoreCase("Existing")) {
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//*[contains(@text,'Active extras')]")));
				SetCapabilities.dr.findElement(By.xpath("//*[contains(@text,'Active extras')]")).click();
				Thread.sleep(1000);
				utils.takeScreenShot();
			} else {
				wait.until(ExpectedConditions.visibilityOfElementLocated(
						By.xpath("//android.widget.TextView[contains(@text,'More extras')]")));
			}

			if (utils.fetchData("MVA_ProductType").toString().equals("Flex")) {
				Scroll("Flex");
				// wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@text,'Flex')]")));
				// SetCapabilities.dr.findElement(By.xpath("//*[contains(@text,'Flex')]")).click();
			} else if (utils.fetchData("MVA_ProductType").toString().equals("Internet")) {
				Scroll("Internet");
				// wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@text,'Internet')]")));
				// SetCapabilities.dr.findElement(By.xpath("//*[contains(@text,'Internet')]")).click();
			} else if (utils.fetchData("MVA_ProductType").toString().equals("Calls")) {
				Scroll("Calls");
				// wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@text,'Calls')]")));
				// SetCapabilities.dr.findElement(By.xpath("//*[contains(@text,'Calls')]")).click();
			} else if (utils.fetchData("MVA_ProductType").toString().equals("Roaming")) {
				Scroll("Roaming");
				// wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@text,'Roaming')]")));
				// SetCapabilities.dr.findElement(By.xpath("//*[contains(@text,'Roaming')]")).click();
			}
			utils.takeScreenShot();
			Scroll(MVA_PrepaidExtras);
			utils.takeScreenShot();

			if (TestCaseN.get().equalsIgnoreCase("Existing")) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@text='Activate Again']")));
				SetCapabilities.dr.findElement(By.xpath("//*[@text='Activate Again']")).click();
				Thread.sleep(1000);
				utils.takeScreenShot();
			} else {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@text='Activate']")));
				SetCapabilities.dr.findElement(By.xpath("//*[@text='Activate']")).click();
				Thread.sleep(1000);
			}

			/*
			 * int i = 0; do { Thread.sleep(1000); i = i + 1; } while (i < 4);
			 */
			Wait("//android.widget.TextView[contains(@text,'product has been activated')]");

			utils.takeScreenShot();
			Status = "PASS";

		} catch (Exception e) {
			Status = "FAIL";
			SetCapabilities.dr.quit();
			e.printStackTrace();
			Result.fUpdateLog("Exception Occured ");
		}
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public static String MVACookies() {
		@SuppressWarnings("unused")
		String Test_OutPut = "", Status = "", MVA_PrepaidExtras = "";

		try {

			WebDriverWait wait = new WebDriverWait(SetCapabilities.dr, 120);
			SetCapabilities.dr.findElement(By.id(Xpath.BurgerMenu)).click();
			SetCapabilities.dr.findElement(By.xpath(Xpath.MenuVFPoints)).click();

			wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@text,'Vodafone Points')]")));
			SetCapabilities.dr.findElement(By.xpath("//*[contains(@text,'Vodafone Points')]")).click();
			utils.takeScreenShot();
			String Mob1 = SetCapabilities.dr.findElement(By.xpath(Xpath.CookieValue)).getText().toString();
			String Cookie = Mob1.split(" ")[0].trim();
			// String SiebelName = "0";
			String SiebelName = Utlities.FetchStoredValue("Mcare_Cookies", "Mcare_Cookies", "MVACookie");
			Test_OutPut += "Cookies Value in Siebel is " + SiebelName + "</br>" + "Cookies Value in MCare is" + Cookie;
			if (Cookie.equalsIgnoreCase(SiebelName)) {
				Status = "PASS";
			} else {
				Status = "FAIL";
			}

		} catch (Exception e) {
			Status = "FAIL";
			SetCapabilities.dr.quit();
			e.printStackTrace();
			Result.fUpdateLog("Exception Occured ");
		}
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public static String PreAddon_DeActivation() {
		String Test_OutPut = "", Status = "", MVA_PrepaidExtras = "";

		try {

			WebDriverWait wait = new WebDriverWait(SetCapabilities.dr, 10);
			MVA_PrepaidExtras = utils.fetchData("MVA_PrepaidExtras");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Xpath.BurgerMenu)));
			utils.takeScreenShot();
			SetCapabilities.dr.findElement(By.xpath(Xpath.BurgerMenu)).click();
			Scroll("My Products & Services");
			Wait(Xpath.PS_Phone);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@text,'Active extras')]")));
			SetCapabilities.dr.findElement(By.xpath("//*[contains(@text,'Active extras')]")).click();
			utils.takeScreenShot();

			if (utils.fetchData("MVA_ProductType").toString().equals("Flex")) {
				Scroll("Flex");
			} else if (utils.fetchData("MVA_ProductType").toString().equals("Internet")) {
				Scroll("Internet");
			} else if (utils.fetchData("MVA_ProductType").toString().equals("Calls")) {
				Scroll("Calls");

			} else if (utils.fetchData("MVA_ProductType").toString().equals("Roaming")) {
				Scroll("Roaming");
			}
			utils.takeScreenShot();
			Scroll(MVA_PrepaidExtras);
			utils.takeScreenShot();
			utils.timestamp = new SimpleDateFormat("M/dd/YYYY hh:mm:ss aa").format(Calendar.getInstance().getTime());
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@text='Deactivate']")));
			SetCapabilities.dr.findElement(By.xpath("//*[@text='Deactivate']")).click();
			utils.takeScreenShot();

			int i = 0;
			do {
				Thread.sleep(1000);
				i = i + 1;
			} while (i < 4);
			Status = "PASS";

			utils.takeScreenShot();
			Wait(Xpath.Menu);
			SetCapabilities.dr.findElement(By.xpath(Xpath.Menu)).click();
			Wait(Xpath.Home);
			// swipeVertical((AppiumDriver) SetCapabilities.dr, 0.1, 0.9, 0.5, 3000);
			SetCapabilities.dr.findElement(By.xpath(Xpath.Home)).click();
			utils.takeScreenShot();
			/*
			 * Status = "PASS";
			 * 
			 * String MSG = SetCapabilities.dr .findElement( By.xpath(
			 * "//android.widget.TextView[contains(@content-desc,'ltr.activate_products.text')]"
			 * )) .getText().toString(); if
			 * (MSG.contains("has been deactivated and wait for confirmation by SMS")) {
			 * Status = "PartiallyPass"; } else { Status = "FAIL";
			 * Result.fUpdateLog("Production Deactivation failed "); }
			 */
		} catch (Exception e) {
			Status = "FAIL";
			SetCapabilities.dr.quit();
			e.printStackTrace();
			Result.fUpdateLog("Exception Occured ");
		}
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public static String PostAddon_Activation() {
		String Test_OutPut = "", Status = "", MVA_PostpaidExtras = "";

		try {
			WebDriverWait wait = new WebDriverWait(SetCapabilities.dr, 10);
			MVA_PostpaidExtras = utils.fetchData("MVA_PostpaidExtras");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Xpath.BurgerMenu)));
			utils.takeScreenShot();
			SetCapabilities.dr.findElement(By.xpath(Xpath.BurgerMenu)).click();
			Scroll("My Products & Services");
			Wait(Xpath.PS_Phone);
			if (TestCaseN.get().equalsIgnoreCase("Existing")) {
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//*[contains(@text,'Active extras')]")));
				SetCapabilities.dr.findElement(By.xpath("//*[contains(@text,'Active extras')]")).click();
				utils.takeScreenShot();
			} else {
				wait.until(ExpectedConditions.visibilityOfElementLocated(
						By.xpath("//android.widget.TextView[contains(@text,'More extras')]")));
			}

			if (utils.fetchData("MVA_ProductType").toString().equals("Flex")) {
				Scroll("Flex");
			} else if (utils.fetchData("MVA_ProductType").toString().equals("Internet")) {
				Scroll("Internet");
			} else if (utils.fetchData("MVA_ProductType").toString().equals("Calls")) {
				Scroll("Calls");

			} else if (utils.fetchData("MVA_ProductType").toString().equals("Roaming")) {
				Scroll("Roaming");
			}
			utils.takeScreenShot();
			Scroll(MVA_PostpaidExtras);
			utils.takeScreenShot();
			utils.timestamp = new SimpleDateFormat("M/dd/YYYY hh:mm:ss aa").format(Calendar.getInstance().getTime());

			if (TestCaseN.get().equalsIgnoreCase("Existing")) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@text='Activate Again']")));
				SetCapabilities.dr.findElement(By.xpath("//*[@text='Activate Again']")).click();
				utils.takeScreenShot();
			} else {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@text='Activate']")));
				SetCapabilities.dr.findElement(By.xpath("//*[@text='Activate']")).click();
			}

			int i = 0;
			do {
				Thread.sleep(1000);
				i = i + 1;
			} while (i < 4);
			Status = "PASS";

			utils.takeScreenShot();
			Wait(Xpath.Menu);
			SetCapabilities.dr.findElement(By.xpath(Xpath.Menu)).click();
			Wait(Xpath.Home);
			// swipeVertical((AppiumDriver) SetCapabilities.dr, 0.1, 0.9, 0.5, 3000);
			SetCapabilities.dr.findElement(By.xpath(Xpath.Home)).click();
			utils.takeScreenShot();

		} catch (Exception e) {
			Status = "FAIL";
			SetCapabilities.dr.quit();
			e.printStackTrace();
			Result.fUpdateLog("Exception Occured ");
		}
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public static String PostAddon_Deactivation() {
		String Test_OutPut = "", Status = "", MVA_PostpaidExtras = "";

		try {

			WebDriverWait wait = new WebDriverWait(SetCapabilities.dr, 10);
			MVA_PostpaidExtras = utils.fetchData("MVA_PostpaidExtras");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Xpath.BurgerMenu)));
			utils.takeScreenShot();
			SetCapabilities.dr.findElement(By.xpath(Xpath.BurgerMenu)).click();
			Scroll("My Products & Services");
			Wait(Xpath.PS_Phone);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@text,'Active extras')]")));
			SetCapabilities.dr.findElement(By.xpath("//*[contains(@text,'Active extras')]")).click();
			utils.takeScreenShot();

			if (utils.fetchData("MVA_ProductType").toString().equals("Flex")) {
				Scroll("Flex");
			} else if (utils.fetchData("MVA_ProductType").toString().equals("Internet")) {
				Scroll("Internet");
			} else if (utils.fetchData("MVA_ProductType").toString().equals("Calls")) {
				Scroll("Calls");

			} else if (utils.fetchData("MVA_ProductType").toString().equals("Roaming")) {
				Scroll("Roaming");
			}
			utils.takeScreenShot();
			Scroll(MVA_PostpaidExtras);
			utils.takeScreenShot();
			utils.timestamp = new SimpleDateFormat("M/dd/YYYY hh:mm:ss aa").format(Calendar.getInstance().getTime());
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@text='Deactivate']")));
			SetCapabilities.dr.findElement(By.xpath("//*[@text='Deactivate']")).click();
			utils.takeScreenShot();

			int i = 0;
			do {
				Thread.sleep(1000);
				i = i + 1;
			} while (i < 4);
			Status = "PASS";

			utils.takeScreenShot();
			Wait(Xpath.Menu);
			SetCapabilities.dr.findElement(By.xpath(Xpath.Menu)).click();
			Wait(Xpath.Home);
			// swipeVertical((AppiumDriver) SetCapabilities.dr, 0.1, 0.9, 0.5, 3000);
			SetCapabilities.dr.findElement(By.xpath(Xpath.Home)).click();
			utils.takeScreenShot();

		} catch (Exception e) {
			Status = "FAIL";
			SetCapabilities.dr.quit();
			e.printStackTrace();
			Result.fUpdateLog("Exception Occured ");
		}
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	@SuppressWarnings("unused")
	public static String SelfRecharge() {
		String Test_OutPut = "", Status = "", MSISDN = "", RechargeType = "", Amount = "";
		try {
			WebDriverWait wait = new WebDriverWait(SetCapabilities.dr, 120);
			SetCapabilities.dr.findElement(By.id(Xpath.BurgerMenu)).click();
			SetCapabilities.dr.findElement(By.xpath(Xpath.Recharge)).click();
			SetCapabilities.dr.findElement(By.id("qa.vodafone.myvodafone.devel.debug:id/" + TestCaseN.get() + ""))
					.click();
			/* ScrollScript */
			SetCapabilities.dr.findElement(By.xpath(Xpath.Payment)).click();

			// RechargeType = utils.fetchData("RechargeType");

		} catch (Exception e) {
			Status = "FAIL";
			e.printStackTrace();
			Result.fUpdateLog("Exception Occured ");
		}
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	@SuppressWarnings("unused")
	public static String McareBillPayment() {
		String Test_OutPut = "", Status = "", MSISDN = "", Amount = "";
		try {
			FileReader reader = new FileReader("Framework/config/config.properties");
			Properties p = new Properties();
			p.load(reader);
			switch (UseCaseName.get()) {
			case "Payment_Self":
				MSISDN = utils.fetchData("MSISDN");
				break;
			case "Payment_Others":
				MSISDN = utils.fetchData("Others_MSISDN");
				break;
			}
			MSISDN = MSISDN.substring(3, 11);
			Amount = utils.fetchData("Voucher_Amt");
			String DeviceName = utils.fetchData("DeviceName");
			WebDriverWait wait = new WebDriverWait(SetCapabilities.dr, 120);
			SetCapabilities.dr.findElement(By.id(Xpath.BurgerMenu)).click();
			SetCapabilities.dr.findElement(By.xpath(Xpath.BillingPayments)).click();
			SetCapabilities.dr.findElement(By.xpath(Xpath.QuickPay)).click();
			SetCapabilities.dr.findElement(By.xpath(Xpath.QuickPayMSISDN)).sendKeys("MSISDN");
			// SetCapabilities.dr.findElement(By.xpath(Xpath.QuickPayAmount)).clear();
			// SetCapabilities.dr.findElement(By.xpath(Xpath.QuickPayAmount)).sendKeys(Keys.BACKSPACE);
			SetCapabilities.dr.findElement(By.xpath(Xpath.QuickPayAmount)).sendKeys(Amount);
			// SetCapabilities.dr.findElement(By.xpath(Xpath.QuickPayByFalcon)).sendKeys(Keys.DELETE);
			String cmd = "adb -s " + p.getProperty(DeviceName + "_Id") + " shell input keyevent 4";
			Runtime run = Runtime.getRuntime();
			run.exec(cmd);
			SetCapabilities.dr.findElement(By.xpath(Xpath.QuickPayByFalcon)).click();
		} catch (Exception e) {
			Status = "FAIL";
			e.printStackTrace();
			Result.fUpdateLog("Exception Occured ");
		}
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public static String FlexBalance() {
		String Test_OutPut = "", Status = "", Flextext = "", Flex = "", FlexBal = "", FB = "";
		try {
			utils.takeScreenShot();
			if (SetCapabilities.dr
					.findElement(By
							.xpath("//*[@resource-id='qa.vodafone.myvodafone.devel.beta:id/dashboard_small_credits']"))
					.isDisplayed()) {
				Flextext = SetCapabilities.dr.findElement(By.xpath(
						"//*[@resource-id='qa.vodafone.myvodafone.devel.beta:id/balance' and @index='0']//android.widget.TextView[contains(@content-desc,'ltr.dashboard.credit.small.text')]"))
						.getText().toString();
				if (Flextext.contains("Flex")) {
					Flex = SetCapabilities.dr.findElement(By.xpath(
							"//android.widget.TextView[contains(@content-desc,'ltr.dashboard.credit.small.amount.text')]"))
							.getText().toString();
					utils.takeScreenShot();

				} else if (Flextext.contains("Credit")) {
					Flex = SetCapabilities.dr.findElement(By.xpath(
							"//*[@resource-id='qa.vodafone.myvodafone.devel.beta:id/dashboard_small_credits']//*[@class='android.support.v7.widget.LinearLayoutCompat' and @index='1']//android.widget.TextView[contains(@content-desc,'ltr.dashboard.credit.small.amount.text')]"))
							.getText().toString();
					utils.takeScreenShot();

				}

				FlexBal = Flex.split("FLEX")[1].trim();
				System.out.println(FlexBal);
				utils.takeScreenShot();
				if (TestCaseN.get().equals("Mcare_Post_FlexVerify")) {
					FB = Utlities.FetchStoredValue("Mcare_Post_FlexVerify", "Mcare_Post_FlexVerify", "Flexbalance");
				} else if (TestCaseN.get().equals("Mcare_Pre_FlexVerify")) {
					FB = Utlities.FetchStoredValue("Mcare_Pre_FlexVerify", "Mcare_Pre_FlexVerify", "Flexbalance");
				}
				if (FlexBal.contains(FB)) {

					Status = "PASS";
					Result.fUpdateLog("Flex bucket is verified with RTB");
				} else {
					Status = "FAIL";
					Result.fUpdateLog("Flex bucket is not as per RTB");
				}

			} else {
				Continue.set(false);
				Result.fUpdateLog("Flex bucket is not available");
			}

		} catch (Exception e) {
			Status = "FAIL";
			e.printStackTrace();
			Result.fUpdateLog("Exception Occured ");
		}
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public static String Test() {
		String Test_OutPut = "", Status = "";
		try {
			// Click on Accept button
			SetCapabilities.dr
					.findElement(By.xpath("//android.widget.Button[@resource-id='com.pcloudy.appiumdemo:id/accept']"))
					.click();
			utils.takeScreenShot();

			// Click on Flight button
			SetCapabilities.dr
					.findElement(
							By.xpath("//android.widget.Button[@resource-id='com.pcloudy.appiumdemo:id/flightButton']"))
					.click();
			utils.takeScreenShot();

			// Select from location
			SetCapabilities.dr
					.findElement(
							By.xpath("//android.widget.Spinner[@resource-id='com.pcloudy.appiumdemo:id/spinnerfrom']"))
					.click();
			utils.takeScreenShot();
			SetCapabilities.dr.findElement(By.xpath(
					"//android.widget.CheckedTextView[@resource-id='android:id/text1' and @text='Bangalore, India (BLR)']"))
					.click();
			utils.takeScreenShot();

			// Select to location
			SetCapabilities.dr
					.findElement(
							By.xpath("//android.widget.Spinner[@resource-id='com.pcloudy.appiumdemo:id/spinnerto']"))
					.click();
			utils.takeScreenShot();
			SetCapabilities.dr.findElement(By.xpath(
					"//android.widget.CheckedTextView[@resource-id='android:id/text1' and @text='Pune, India (PNQ)']"))
					.click();
			utils.takeScreenShot();

			// Select one way trip
			SetCapabilities.dr
					.findElement(By
							.xpath("//android.widget.RadioButton[@resource-id='com.pcloudy.appiumdemo:id/singleTrip']"))
					.click();

			// Select departure time
			SetCapabilities.dr
					.findElement(
							By.xpath("//android.widget.TextView[@resource-id='com.pcloudy.appiumdemo:id/txtdepart']"))
					.click();
			utils.takeScreenShot();
			SetCapabilities.dr
					.findElement(By.xpath("//android.widget.Button[@resource-id='android:id/button1' and @text='OK']"))
					.click();
			utils.takeScreenShot();

			// Click on search flights button
			SetCapabilities.dr
					.findElement(
							By.xpath("//android.widget.Button[@resource-id='com.pcloudy.appiumdemo:id/searchFlights']"))
					.click();
			utils.takeScreenShot();

			SetCapabilities.dr.quit();
			Status = "PASS";
			Result.fUpdateLog("Completed");
		} catch (Exception e) {
			Status = "FAIL";
			e.printStackTrace();
			Result.fUpdateLog("Exception Occured ");
		}
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public static void allowAppPermission() {
		int i = 0;
		while (SetCapabilities.dr.findElements(MobileBy.xpath("//*[@class='android.widget.Button'][2]")).size() > 0) {
			i += 1;
			System.out.println("permisiion : " + i);
			SetCapabilities.dr.findElement(MobileBy.xpath("//*[@class='android.widget.Button'][2]")).click();
		}
	}

}
