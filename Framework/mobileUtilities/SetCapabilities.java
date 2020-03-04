package mobileUtilities;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.net.URL;
import org.openqa.selenium.remote.DesiredCapabilities;

import Libraries.Driver;
import Libraries.Result;
import io.appium.java_client.android.AndroidDriver;

@SuppressWarnings("rawtypes")
public class SetCapabilities extends Driver {

	static AndroidDriver dr;
	static String DeviceName;

	public String setDialerCapabilities() {
		String Test_OutPut = "", Status = "";
		try {
			if (!(getdata("DeviceName").equals(""))) {
				DeviceName = getdata("DeviceName");
				Result.fUpdateLog("Device Name is set to " + DeviceName);
				setDialerCapabilities1(DeviceName);
				Status = "PASS";
			} else {
				Result.fUpdateLog("Device " + DeviceName + " not found");
				Status = "FAIL";
			}
		} catch (Exception e) {
			Result.fUpdateLog("Capabilites are not set due to" + e);
			Status = "FAIL";
		}
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public static void setDialerCapabilities1(String DeviceName) {
		System.out.println("*** Setting Up Dialer Capabilities ***");
		try {
			FileReader reader = new FileReader("Framework/config/config.properties");
			Properties p = new Properties();
			p.load(reader);
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("deviceName", p.getProperty(DeviceName + "_Name"));
			capabilities.setCapability("udid", p.getProperty(DeviceName + "_Id"));
			capabilities.setCapability("platformVersion", p.getProperty(DeviceName + "_Android_Version"));
			capabilities.setCapability("platformName", "Android");
			capabilities.setCapability("appPackage", p.getProperty(DeviceName + "_AppPackage_DialerApp"));
			capabilities.setCapability("appActivity", p.getProperty(DeviceName + "_AppActivity_DialerApp"));
			dr = new AndroidDriver(new URL("http://127.0.0.1:" + p.getProperty(DeviceName + "_Port") + "/wd/hub"),
					capabilities);
			System.out.println("*** Dialer Capabilities are now Set ***");
		} catch (Exception e) {
			Result.fUpdateLog("Capabilites are not set due to" + e);
		}
	}

	public String setMessengerCapabilities() {
		String Test_OutPut = "", Status = "";
		try {
			if (!(getdata("DeviceName").equals(""))) {
				DeviceName = getdata("DeviceName");
				Result.fUpdateLog("Device Name is set to " + DeviceName);
				setMessengerCapabilities1(DeviceName);
				Status = "PASS";
			} else {
				Result.fUpdateLog("Device " + DeviceName + " not found");
				Status = "FAIL";
			}
		} catch (Exception e) {
			Result.fUpdateLog("Capabilites are not set due to" + e);
			Status = "FAIL";
		}
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public static void setMessengerCapabilities1(String DeviceName) throws IOException, InterruptedException {
		System.out.println("*** Setting Up Messenger Capabilities ***");
		try {
			FileReader reader = new FileReader("Framework/config/config.properties");
			Properties p = new Properties();
			p.load(reader);
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("deviceName", p.getProperty(DeviceName + "_Name"));
			capabilities.setCapability("udid", p.getProperty(DeviceName + "_Id"));
			capabilities.setCapability("platformVersion", p.getProperty(DeviceName + "_Android_Version"));
			capabilities.setCapability("platformName", "Android");
			capabilities.setCapability("appPackage", p.getProperty(DeviceName + "_AppPackage_MessageApp"));
			capabilities.setCapability("appActivity", p.getProperty(DeviceName + "_AppActivity_MessageApp"));
			dr = new AndroidDriver(new URL("http://127.0.0.1:" + p.getProperty(DeviceName + "_Port") + "/wd/hub"),
					capabilities);
			Result.fUpdateLog("*** Messenger Capabilities are now Set ***");
		} catch (Exception e) {
			Result.fUpdateLog("Capabilites are not set due to" + e);
		}
	}

	public String setMCareCapabilities() {
		String Test_OutPut = "", Status = "";
		try {
			if (!(getdata("DeviceName").equals(""))) {
				DeviceName = getdata("DeviceName");
				Result.fUpdateLog("Device Name is set to " + DeviceName);
				setMCareCapabilities1(DeviceName);
				// setMCareCapabilities_Pcloudy();
				Status = "PASS";
			} else {
				Result.fUpdateLog("Device " + DeviceName + " not found");
				Status = "FAIL";
			}
		} catch (Exception e) {
			Result.fUpdateLog("Capabilites are not set due to" + e);
			Status = "FAIL";
		}
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public static void setMCareCapabilities1(String DeviceName) throws IOException, InterruptedException {
		System.out.println("*** Setting Up MCare Capabilities ***");
		try {
			String Env = utils.fetchData("Env");
			FileReader reader = new FileReader("Framework/config/config.properties");
			Properties p = new Properties();
			p.load(reader);
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("deviceName", p.getProperty(DeviceName + "_Name"));
			capabilities.setCapability("udid", p.getProperty(DeviceName + "_Id"));
			if (Env.equals("Prod")) {
				capabilities.setCapability("app", WorkingDir.get() + "/APK/My-Vodafone-Prod.apk");
			} else {
				capabilities.setCapability("app", WorkingDir.get() + "/APK/My-Vodafone-Devel-Beta.apk");
			}
			capabilities.setCapability("platformVersion", p.getProperty(DeviceName + "_Android_Version"));
			capabilities.setCapability("platformName", "Android");
			capabilities.setCapability("appPackage", p.getProperty("MCare_" + Env + "_AppPackage"));
			capabilities.setCapability("appActivity", p.getProperty("MCare_" + Env + "_AppActivity"));
			capabilities.setCapability("autoGrantPermissions", "true");
			// capabilities.setCapability("autoAcceptAlerts", "true");
			activity.set(p.getProperty("MCare_" + Env + "_AppPackage"));
			dr = new AndroidDriver(new URL("http://127.0.0.1:" + p.getProperty(DeviceName + "_Port") + "/wd/hub"),
					capabilities);
			// dr.installApp("D:/Apk/My-Vodafone-Devel-Beta.apk");
			dr.resetApp();
			Result.fUpdateLog("*** MCare Capabilities are now Set ***");
		} catch (Exception e) {
			Result.fUpdateLog("Capabilites are not set due to" + e);
		}
	}

	public static void setMCareCapabilities_Pcloudy() throws IOException, InterruptedException {
		System.out.println("*** Setting Up MCare Pcloudy Capabilities ***");
		try {
			String Env = utils.fetchData("Env");
			FileReader reader = new FileReader("Framework/config/config.properties");
			Properties p = new Properties();
			p.load(reader);
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("pCloudy_Username", "haleemimranb@maveric-systems.com");
			capabilities.setCapability("pCloudy_ApiKey", "rrjqbt6d7z4x8kvjmprm4k9p");
			// capabilities.setCapability("pCloudy_AppPath",
			// "D:/Apk/My-Vodafone-Devel-Beta.apk");
			capabilities.setCapability("pCloudy_ApplicationName", "My-Vodafone-Devel-Beta.apk");
			capabilities.setCapability("pCloudy_DurationInMinutes", 7);
			capabilities.setCapability("pCloudy_DeviceManafacturer", utils.fetchData("Device"));
			capabilities.setCapability("pCloudy_DeviceVersion", utils.fetchData("Version"));
			// capabilities.setCapability("pCloudy_DeviceFullName",
			// "Samsung_GalaxyTabA_Android_7.1.1");
			capabilities.setCapability("newCommandTimeout", 600);
			capabilities.setCapability("launchTimeout", 90000);
			capabilities.setCapability("appPackage", p.getProperty("MCare_" + Env + "_AppPackage"));
			capabilities.setCapability("appActivity", p.getProperty("MCare_" + Env + "_AppActivity"));
			capabilities.setCapability("autoGrantPermissions", "true");
			activity.set(p.getProperty("MCare_" + Env + "_AppPackage"));
			dr = new AndroidDriver(new URL("https://device.pcloudy.com/appiumcloud/wd/hub"), capabilities);
			dr.resetApp();
			Result.fUpdateLog("*** MCare Pcloudy Capabilities are now Set ***");
		} catch (Exception e) {
			Result.fUpdateLog("MCare Pcloudy Capabilites are not set due to" + e);
		}
	}

}