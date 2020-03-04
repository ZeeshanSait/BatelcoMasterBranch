package Libraries;

import io.appium.java_client.android.AndroidDriver;

import java.net.URL;
import java.util.Dictionary;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

@SuppressWarnings("rawtypes")
public class AndroidMobBrow extends Driver implements NewDriver {

	public static ThreadLocal<Dictionary> Data = new ThreadLocal<Dictionary>();

	AndroidDriver remoteDriver = null;

	public AndroidMobBrow() {
	}

	public WebDriver getNewDriver() {
		remoteDriver = getAppiumDriver();
		return remoteDriver;
	}

	private synchronized AndroidDriver getAppiumDriver() {
		DesiredCapabilities capabilities = DesiredCapabilities.android();

		Data.set((Dictionary<?, ?>) Utlities.freaddata_diff(TestCaseData.get()));

		System.out.println(Data.get().get("appiumVersion"));
		System.out.println(Data.get().get("deviceVersion"));
		System.out.println(Data.get().get("deviceName"));
		System.out.println(Data.get().get("appHost"));
		System.out.println(Data.get().get("selPort"));

		String appiumVersion = Data.get().get("appiumVersion").toString();
		String deviceVersion = Data.get().get("deviceVersion").toString();
		String deviceName = Data.get().get("deviceName").toString();

		String appHost = Data.get().get("appHost").toString();
		String selPort = Data.get().get("selPort").toString();
		try {
			System.out.println("SET CAps");

			capabilities.setCapability("appiumVersion", appiumVersion);
			capabilities.setCapability("deviceName", deviceName);
			capabilities.setCapability("version", deviceVersion);

			capabilities.setCapability("platformName", "Android");
			capabilities.setCapability("platform", Platform.ANDROID);
			capabilities.setCapability("automationName", "Appium");
			capabilities.setCapability("browserName", "chrome");

			capabilities.setCapability("newCommandTimeout", 60 * 5);

			System.out.println("Launch");
			remoteDriver = launchAndroidDriver(appHost, selPort, capabilities);

		} catch (Exception e) {

			e.printStackTrace();
			remoteDriver = null;
		}

		return remoteDriver;
	}

	private synchronized AndroidDriver launchAndroidDriver(String appHost, String selPort,
			DesiredCapabilities capabilities) {
		try {
			remoteDriver = new AndroidDriver(new URL("http://" + appHost + ":" + selPort + "/wd/hub"), capabilities);
		} catch (Exception e) {
			e.printStackTrace();
			remoteDriver = null;
		}
		return remoteDriver;
	}

}
