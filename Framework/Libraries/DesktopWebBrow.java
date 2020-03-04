package Libraries;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class DesktopWebBrow extends Driver implements NewDriver {

	WebDriver remoteDriver = null;

	public DesktopWebBrow() {
	}

	public WebDriver getNewDriver() {
		remoteDriver = getRemoteDriver();
		return remoteDriver;
	}

	public WebDriver getRemoteDriver() {

		switch (browser.get().toLowerCase()) {
		case "chrome":
			System.setProperty("webdriver.chrome.driver", WorkingDir.get() + "/Drivers/chromedriver.exe");
			Map<String, Object> prefs = new HashMap<String, Object>();

			// Set the notification setting it will override the default setting
			prefs.put("profile.default_content_setting_values.notifications", 2);
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("useAutomationExtension", false);
			options.addArguments("--disable-extensions");
			options.setExperimentalOption("prefs", prefs);
			remoteDriver = new ChromeDriver(options);
			break;
		case "firefox":
			System.setProperty("webdriver.gecko.driver", WorkingDir.get() + "/Drivers/geckodriver.exe");
			// System.setProperty("webdriver.Firefoxdriver", Driver.basepth.get() +
			// "/geckodriver.exe");
			// File pathToBinary = new File("C:\\Users\\ImranH2\\AppData\\Local\\Mozilla
			// Firefox\\firefox.exe");
			// FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
//		//	FirefoxProfile firefoxProfile = new FirefoxProfile();
//			firefoxProfile.setPreference("network.proxy.type", 0);
//			firefoxProfile.setAcceptUntrustedCertificates(true);
//			firefoxProfile.setAssumeUntrustedCertificateIssuer(false);
//			remoteDriver = new FirefoxDriver();
			// remoteDriver = new FirefoxDriver();
			break;
		case "ie":
			System.setProperty("webdriver.ie.driver", WorkingDir.get() + "/Drivers/IEDriverServer.exe");
			DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
			caps.setCapability("ignoreZoomSetting", true);
			caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			caps.setCapability("requireWindowFocus", true);
			caps.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
			remoteDriver = new InternetExplorerDriver();
			break;
		}
		return remoteDriver;
	}

}