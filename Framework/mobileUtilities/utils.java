package mobileUtilities;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import Libraries.Driver;

public class utils extends Driver {
	public static String timestamp;

	public static String ResultFilePath = UCscreenfilepth.get() + "\\result.xlsx";

	public static void copyResultTemplate() {
		File source = new File("Framework\\Templates\\MobileResults\\result.xlsx");
		File dest = new File(UCscreenfilepth.get() + "\\result.xlsx");
		try {
			FileUtils.copyFile(source, dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void takeScreenShot() throws IOException, InterruptedException {
		// Set folder name to store screenshots.
		String destDir = UCscreenfilepth.get() + "/MobileScreenshots";
		// Capture screenshot.
		// Thread.sleep(100);
		File scrFile = ((TakesScreenshot) SetCapabilities.dr).getScreenshotAs(OutputType.FILE);
		// Set date format to set It as screenshot file name.
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy__hh_mm_ssaa");
		// Create folder under project with name "screenshots" provided to destDir.
		new File(destDir).mkdirs();
		// Set file name using current date time.
		String destFile = dateFormat.format(new Date()) + ".png";

		try {
			// Copy paste file at destination folder location
			FileUtils.copyFile(scrFile, new File(destDir + "/" + destFile));
			// Thread.sleep(100);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static String getPriority(String BucketId) throws FilloException {
		Fillo fillo = new Fillo();
		Connection connection = fillo.getConnection("Framework\\db\\priority.xlsx");
		String strQuery = "Select * from sheet1 where Bucket_ID='" + BucketId + "'";
		Recordset recordset = connection.executeQuery(strQuery);
		String a;
		recordset.next();
		a = recordset.getField("Priority");
		// System.out.println(a);
		connection.close();
		return a;

	}

	public static String getType(String BucketId) throws FilloException {
		Fillo fillo = new Fillo();
		Connection connection = fillo.getConnection("Framework\\db\\priority.xlsx");
		String strQuery = "Select * from sheet1 where Bucket_ID='" + BucketId + "'";
		Recordset recordset = connection.executeQuery(strQuery);
		String a;
		recordset.next();
		a = recordset.getField("Type");
		System.out.println(a);
		connection.close();
		return a;
	}

	public static String findpriority(String Type) throws FilloException, ParseException {
		int counter = 0;
		String BuckId = null;
		Fillo fillo = new Fillo();
		// Connection connection = fillo.getConnection("Framework\\db\\result.xlsx");
		Connection connection = fillo.getConnection(UCscreenfilepth.get() + "\\result.xlsx");
		String strQuery = "Select * from pre where Type Like '%" + Type
				+ "%' and Value !='0' and Expiry !='Date Not Given'";
		// String strQuery = "Select * from pre where Type Like '%" + Type + "%' and
		// Value !='0'";
		Recordset recordset = connection.executeQuery(strQuery);
		TreeMap<Date, String> expiry = new TreeMap<Date, String>();
		while (recordset.next()) {
			Date date1 = new SimpleDateFormat("dd-MMM-yyyy").parse(recordset.getField(3).value());
			expiry.put(date1, recordset.getField(0).value());
		}
		Date firstKey = expiry.keySet().iterator().next();
		for (Date key : expiry.keySet()) {
			System.out.println(key);
			if (expiry.containsKey(firstKey))
				counter++;
		}
		// System.out.println("Counter -->" + counter);
		if (counter == 1) {
			System.out.println("All Buckets are expiring on the Same Day!!!");
			recordset = connection.executeQuery(strQuery);
			TreeMap<Integer, String> priority = new TreeMap<Integer, String>();
			while (recordset.next()) {
				int a = Integer.parseInt(recordset.getField(4).value());
				priority.put(a, recordset.getField(0).value());
			}
			// To Print the Buckets Priority Order
			for (Map.Entry<Integer, String> entry : priority.entrySet()) {
				Integer key = entry.getKey();
				String value = entry.getValue();
				System.out.println("Bucket:" + value + "   " + "Priority:" + key);
			}

			Map.Entry<Integer, String> pri = priority.entrySet().iterator().next();
			BuckId = pri.getValue();
		} else
			BuckId = expiry.get(firstKey);
		return BuckId;
	}

	public static double balance(String Bucket, String type) throws FilloException {
		double Balance;
		try {
			Fillo fillo = new Fillo();
			// Connection connection = fillo.getConnection("Framework\\db\\result.xlsx");
			Connection connection = fillo.getConnection(UCscreenfilepth.get() + "\\result.xlsx");
			String strQuery = "Select * from " + type + " where Bucket_Id='" + Bucket + "'";
			Recordset recordset = connection.executeQuery(strQuery);
			for (int i = 0; i <= recordset.getCount(); i++)
				recordset.next();
			String str = recordset.getField(2).value();
			Balance = convBal(str);
		} catch (Exception e) {
			Balance = 0;
		}
		return Balance;
	}

	public static String Bal(String a) {
		a = a.substring(0, a.indexOf(" "));
		return a;
	}

	public static double convBal(String a) {
		double val;
		val = Double.parseDouble(a);
		return val;
	}

	public static double getCharging(String Bucket, String Type) throws FilloException, IOException {
		double charging = 0;
		if (!(Bucket.contentEquals("PRMBAL"))) {
			// if(Bucket!="PRMBAL") {
			Fillo fillo = new Fillo();
			Connection connection = fillo.getConnection("Framework\\db\\priority.xlsx");
			String strQuery = "Select * from sheet1 where Bucket_Id='" + Bucket + "'";
			Recordset recordset = connection.executeQuery(strQuery);
			for (int i = 0; i <= recordset.getCount(); i++)
				recordset.next();
			String str = recordset.getField(3).value();
			charging = Double.parseDouble(str);
		} else {
			FileReader reader = new FileReader("Framework/config/config.properties");
			Properties p = new Properties();
			p.load(reader);
			charging = Double.parseDouble(p.getProperty("PRMBAL_" + Type));
		}
		return charging;
	}

	public static void clenaup() throws FilloException {
		Fillo fillo = new Fillo();
		// Connection connection = fillo.getConnection("Framework\\db\\result.xlsx");
		Connection connection = fillo.getConnection(UCscreenfilepth.get() + "\\result.xlsx");
		String strQuery = "Update pre Set Bucket_Id = '', Name='',Value='',Expiry='',Priority='',Type='',SubType=''";
		connection.executeUpdate(strQuery);
		connection.close();
		connection = fillo.getConnection("Framework\\db\\result.xlsx");
		strQuery = "Update post Set Bucket_Id = '', Name='',Value='',Expiry='',Priority='',Type='',SubType=''";
		connection.executeUpdate(strQuery);
		connection.close();

	}

	public static String getSubType(String BucketId) throws FilloException {
		String SubType;
		Fillo fillo = new Fillo();
		// Connection connection = fillo.getConnection("Framework\\db\\result.xlsx");
		Connection connection = fillo.getConnection(UCscreenfilepth.get() + "\\result.xlsx");
		String strQuery = "Select * from pre where Bucket_ID='" + BucketId + "'";
		Recordset recordset = connection.executeQuery(strQuery);
		recordset.next();
		SubType = recordset.getField("SubType");
		// System.out.println(SubType);
		connection.close();
		return SubType;
	}

	public static double getInternationalCallCharging(String Bucket, String Country) throws FilloException {
		String SubType = getSubType(Bucket);
		System.out.println(SubType);
		double charging = 0;
		if (SubType.equalsIgnoreCase("QAR") || SubType.equalsIgnoreCase("FLEX")) {
			Fillo fillo = new Fillo();
			Connection connection = fillo.getConnection("Framework\\db\\priority.xlsx");
			String strQuery = "Select * from International where Country='" + Country + "'";
			System.out.println(strQuery);
			Recordset recordset = connection.executeQuery(strQuery);
			if (SubType.equalsIgnoreCase("FLEX")) {
				for (int i = 0; i <= recordset.getCount(); i++)
					recordset.next();
				String str = recordset.getField(2).value();
				charging = Double.parseDouble(str);
			} else {
				for (int i = 0; i <= recordset.getCount(); i++)
					recordset.next();
				String str = recordset.getField(3).value();
				charging = Double.parseDouble(str);
			}
		} else {
			Fillo fillo = new Fillo();
			Connection connection = fillo.getConnection("Framework\\db\\priority.xlsx");
			String strQuery = "Select * from sheet1 where Bucket_Id='" + Bucket + "'";
			Recordset recordset = connection.executeQuery(strQuery);
			for (int i = 0; i <= recordset.getCount(); i++)
				recordset.next();
			String str = recordset.getField(3).value();
			charging = Double.parseDouble(str);
		}
		return charging;

	}

	public static double getInternationalSMSCharging(String Bucket, String Country) throws FilloException {
		String SubType = getSubType(Bucket);
		System.out.println(SubType);
		double charging = 0;
		if (SubType.equalsIgnoreCase("QAR") || SubType.equalsIgnoreCase("FLEX")) {
			Fillo fillo = new Fillo();
			Connection connection = fillo.getConnection("Framework\\db\\priority.xlsx");
			String strQuery = "Select * from International where Country='" + Country + "'";
			System.out.println(strQuery);
			Recordset recordset = connection.executeQuery(strQuery);
			if (SubType.equalsIgnoreCase("FLEX")) {
				for (int i = 0; i <= recordset.getCount(); i++)
					recordset.next();
				String str = recordset.getField(4).value();
				charging = Double.parseDouble(str);
			} else {
				for (int i = 0; i <= recordset.getCount(); i++)
					recordset.next();
				String str = recordset.getField(5).value();
				charging = Double.parseDouble(str);
			}
		} else {
			Fillo fillo = new Fillo();
			Connection connection = fillo.getConnection("Framework\\db\\priority.xlsx");
			String strQuery = "Select * from sheet1 where Bucket_Id='" + Bucket + "'";
			Recordset recordset = connection.executeQuery(strQuery);
			for (int i = 0; i <= recordset.getCount(); i++)
				recordset.next();
			String str = recordset.getField(3).value();
			charging = Double.parseDouble(str);
		}
		return charging;

	}

	public static String fetchData(String Name) {
		String Value = null;
		try {
			if (!(getdata(Name).equals(""))) {
				Value = getdata(Name);
			}
		} catch (Exception e) {
			System.out.println("Unable to get " + Name + " due to:" + e);
		}
		return Value;
	}

	public static void restartMobile(String DeviceName) throws IOException {
		FileReader reader = new FileReader("Framework/config/config.properties");
		Properties p = new Properties();
		p.load(reader);
		Runtime run = Runtime.getRuntime();
		run.exec("adb reboot " + p.getProperty(DeviceName + "_Id"));
	}

	public static double getDueNowUSSDBillEnquiry(String MSG) {
		int a = MSG.indexOf("QR");
		int b = MSG.indexOf("Your", MSG.indexOf("Your") + 1);
		String str = MSG.substring(a + 3, b - 2);
		double DueNow = Double.parseDouble(str);
		return DueNow;
	}

	public static double getUnbilledUSSDBillEnquiry(String MSG) {
		int index1 = MSG.indexOf("QR", MSG.indexOf("QR") + 1);
		int index2 = MSG.indexOf(",");
		String test = MSG.substring(index1 + 3, index2);
		double BillAmount = Double.parseDouble(test);
		return BillAmount;
	}

	public static boolean verifyObjectexist(String identify, String path) {
		String vis = "false";
		int countval = 1;
		Exception Error = null;
		while (countval < 2) {
			int i = 0;
			if (path.equalsIgnoreCase("xpath")) {

				try {
					cDriver.get().findElement(By.xpath(identify)).isDisplayed();
					vis = "true";
					break;
				} catch (Exception e) {
					i++;
					Error = e;
				}
			} else if (path.equalsIgnoreCase("id")) {
				try {
					cDriver.get().findElement(By.id(identify)).isDisplayed();
					vis = "true";
					break;

				} catch (Exception e) {
					i++;
					Error = e;
				}
			}
			if (i == 1) {
				vis = "false";
				countval++;
			}
		}
		if (vis == "false") {
			Error.printStackTrace();
			return false;
		} else {
			return true;
		}

	}

	@SuppressWarnings("unused")
	public static int getBucketTypeCount(String Type) {
		int count = -1;
		int counter = 0;
		try {
			String BuckId = null;
			Fillo fillo = new Fillo();
			// Connection connection = fillo.getConnection("Framework\\db\\result.xlsx");
			Connection connection = fillo.getConnection(UCscreenfilepth.get() + "\\result.xlsx");
			String strQuery = "Select * from pre where Type Like '%" + Type
					+ "%' and Value !='0' and Expiry !='Date Not Given'";
			// String strQuery = "Select * from pre where Type Like '%" + Type + "%' and
			// Value !='0'";
			Recordset recordset = connection.executeQuery(strQuery);
			count = recordset.getCount();
		} catch (Exception e) {
			// Result.fUpdateLog("Exception Occurred"+e);
			count = -1;
		}
		return count;
	}

}
