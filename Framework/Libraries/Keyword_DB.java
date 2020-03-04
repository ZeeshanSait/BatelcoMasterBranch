package Libraries;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

//import com.aventstack.extentreports.ExtentReports;
//import com.aventstack.extentreports.ExtentTest;
//import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class Keyword_DB extends Driver {

	public static ThreadLocal<Connection> con = new ThreadLocal<Connection>();
	public Connection conn = null;
	//Keyword_SiebelCRM KSBL=new Keyword_SiebelCRM();
	
	// public static String DBConnectionOutput = "";
	public static String DBHtml = "";
	public static String HeaderValues = "";
	public static String RowValues = "";
	public static String AccountNumber;
	public static String MsisdnNumber;
	public static WebDriver driver;
	public static String OrderID;
	public static String PaymentID;
	
	

	/*------------------------------------------------------------------------------------------------------
	 * Function Name: CIO_Verification
	 * Use : Passing the query and validating the status of the fields
	 * Designed By: Zeeshan
	 * Last Modified Date : 09-Sep-2019
	--------------------------------------------------------------------------------------------------------*/

	public String CIO_Verification(String Environment) throws Exception {
		String Test_OutPut = "";
		Connection conn = null;
		Statement stmt = null;
		String connString = "";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Result.fUpdateLog("Entered in --- " + Environment);

			switch (Environment) {
			case "SIEBELUAT":
				connString = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=10.6.6.10)(PORT=1564))(CONNECT_DATA=(SERVER = DEDICATED)(SERVICE_NAME=SBLUAT)))";
				conn = DriverManager.getConnection(connString, "MAVERICKSBL", "Msbl3214");
				Result.fUpdateLog("connected to DB");
				String Query1 = "select Order_num from siebel.s_order where row_id in "
						+ "(select Order_id from siebel.s_order_item where service_num = '97371000035' and status_cd = 'Pending')";
				Result.fUpdateLog(Query1);
				stmt = conn.createStatement();
				ResultSet rs1 = stmt.executeQuery(Query1);
				while (rs1.next()) {
					// Test_OutPut = "";
					// System.out.println(Test_OutPut);
					// Utlities.StoreValue("Order_num", rs1.getString(1));
					String no = rs1.getString(1);
					System.out.println("Order Number " + no);
				}
				break;

			case "BRMUAT":
				connString = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=10.6.4.145)(PORT=1564))(CONNECT_DATA=(SERVER = DEDICATED)(SERVICE_NAME=BRMUAT2)))";
				conn = DriverManager.getConnection(connString, "MAVERICBRM", "Mbrm3214");
				Result.fUpdateLog("connected to DB");
				String Query = "SELECT A.ACCOUNT_NO, DECODE(A.STATUS, '10100', 'Active', '10102', 'Inactive', '10103', 'Closed') ACC_STATUS, B.BILL_INFO_ID,\r\n"
						+ "DECODE(B.STATUS, '10100', 'Active', '10102', 'Inactive', '10103', 'Closed') BP_STATUS,\r\n"
						+ "B.ACTG_CYCLE_DOM BillCycle,\r\n"
						+ "SER.NAME, P.NAME PRODUCT_NAME, PPT.PURCHASE_START_T PURCHASE_DATE, PPT.USAGE_START_T, PPT.CYCLE_START_T,\r\n"
						+ "ppt.cycle_end_t cycle_end,\r\n" + "PPT.STATUS,PPT.PRICE_LIST_NAME\r\n"
						+ "  FROM PIN1.ACCOUNT_T A, pin1.PURCHASED_PRODUCT_T PPT, pin1.PRODUCT_T P, pin1.service_alias_list_t SER, pin1.billinfo_t B\r\n"
						+ " WHERE     PPT.PRODUCT_OBJ_ID0 = P.POID_ID0\r\n"
						+ "       AND A.POID_ID0 = PPT.ACCOUNT_OBJ_ID0\r\n"
						+ "       AND A.ACCOUNT_NO = '1001005916'\r\n" + "       AND A.POID_ID0 = B.ACCOUNT_OBJ_ID0\r\n"
						+ "       AND SER.NAME = '97370002664'\r\n" + "       order by PPT.STATUS";
				Result.fUpdateLog(Query);
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(Query);
				while (rs.next()) {
					Test_OutPut = "ACC_STATUS: " + rs.getString(2) + " BILL_INFO_ID: " + rs.getString(4) + ",";
					System.out.println(Test_OutPut);
					Utlities.StoreValue("ACC_STATUS", rs.getString(2));
				}
				break;
			case "AIAUAT":
				connString = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=10.6.6.10)(PORT=1564))(CONNECT_DATA=(SERVER = DEDICATED)(SERVICE_NAME=AIAUAT)))";
				conn = DriverManager.getConnection(connString, "MAVERICAIA", "Maia3214");
				break;
			case "OSMCUAT":
				connString = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=10.6.6.10)(PORT=1564))(CONNECT_DATA=(SERVER = DEDICATED)(SERVICE_NAME=OSMCUAT)))";
				conn = DriverManager.getConnection(connString, "MAVERICOSMC", "Mosmc3214");
				break;
			case "OSMSUAT":
				connString = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=10.6.6.10)(PORT=1564))(CONNECT_DATA=(SERVER = DEDICATED)(SERVICE_NAME=OSMSUAT)))";
				conn = DriverManager.getConnection(connString, "MAVERICOSMS", "Mosms3214");
				break;
			}

			System.out.println("Executed Query");
			stmt.close();
			conn.commit();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return Test_OutPut + "<br/>";

	}

	public String DBConnection() {
		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------DB connection Event Details------");
		String ServiceName = "";
		String connString = "";

		if (getdata("Application_Details").equals("SIEBELUAT")) {
			ServiceName = "SBLUAT";
		} else if (getdata("Application_Details").equals("CRM_DB")) {
			ServiceName = "CRMPROD";
		}
		String Username = getdata("VQ_Login_User").trim();
		String password = getdata("VQ_Login_Pswd").trim();
		String Host = getdata("URL/HOST");
		int Port = 1564;

		String driver = "oracle.jdbc.OracleDriver";
		try {
			Class.forName(driver);
			// connString ="jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=" +
			// Host + ")(PORT=" + Port + "))(CONNECT_DATA=(SERVER =
			// DEDICATED)(SERVICE_NAME="+ ServiceName+")))";
			// conn = DriverManager.getConnection(connString,Username,password);
			con.set(DriverManager.getConnection(
					"jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=" + Host + ")(PORT=" + Port
							+ "))(CONNECT_DATA=(SERVER = DEDICATED)(SERVICE_NAME=" + ServiceName + ")))",
					Username, password));

			// con.set(DriverManager.getConnection("jdbc:oracle:thin:@" + Host + ":" + Port
			// + ":" + ServiceName, Username,password));
			Result.fUpdateLog("connected to DB");

			Result.fUpdateLog("Connected to DB: " + Host);
			Test_OutPut += "Connected to DB: " + Host + ",";
			Status = "PASS";
		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Unable to connect to DB: " + Host + ServiceName + ",";
			Result.fUpdateLog("Unable to connect to DB *** " + e.getMessage());
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------DB connection Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public String DBDisconnection() {
		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------DB Disconnection Event Details------");
		try {
			con.get().close();
			Result.fUpdateLog("DB disconnected successfully");
			Test_OutPut += "DB disconnected successfully" + ",";
			Status = "PASS";
		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Failed to disconnect DB" + ",";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------DB Disconnection Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public String BillPoID() {
		String Test_OutPut = "", Status = "", AccountNo = "";
		if (Continue.get()) {

			String BillingProfileName = "";
			Result.fUpdateLog("------BillPoID Event Details------");

			/*
			 * AccountNo = getdata("AccountNo");
			 * 
			 * if (!(getdata("BillingProfile").equals(""))) { BillingProfileName =
			 * getdata("BillingProfile"); } else { BillingProfileName =
			 * Utlities.FetchStoredValue(UseCaseName.get(), TestCaseN.get(),
			 * "BillingProfileName"); }
			 */
			try {
				Statement statement = con.get().createStatement();
				String queryString = "SELECT POID_ID0, ACCOUNT_NO, ACCOUNT_TYPE, CUST_SEG_LIST, NAME, STATUS, STATUS_FLAGS, VAT_CERT, BUSINESS_TYPE, EFFECTIVE_T FROM PIN.ACCOUNT_T";
				ResultSet rs = statement.executeQuery(queryString);
				while (rs.next()) {
					Test_OutPut = "BillPoID: " + rs.getString(2) + " BillingProfileName: " + rs.getString(3) + ",";
					Utlities.StoreValue("BillPoID", rs.getString(2));
				}
				Status = "PASS";

			} catch (Exception e) {
				Continue.set(false);
				Test_OutPut += "Failed to BillPoID" + ",";
				Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
				Status = "FAIL";
				e.printStackTrace();
			}
			Result.fUpdateLog("------BillPoID Event Details - Completed------");
		} else {

			Status = "FAIL";
		}
		return Status + "@@" + Test_OutPut + "<br/>";

	}

	public String AccPoID_BillPoID(String AccountNo) {
		Continue.set(true);
		String Test_OutPut = "";
		if (Continue.get()) {
			Result.fUpdateLog("------AccPoID_BillPoID Event Details------");
			Test_OutPut = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + "<BusinessConfiguration\r\n"
					+ "xmlns=\"http://www.portal.com/schemas/BusinessConfig\"\r\n"
					+ "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\r\n"
					+ "xsi:schemaLocation=\"http://www.portal.com/schemas/BusinessConfig BusinessConfiguration.xsd\">\r\n"
					+ "<!-- Sample input file for pin_bill_accts containing parameters for bill run management -->\r\n"
					+ "<!-- Modify according to guidelines -->\r\n" + "<BillRunConfiguration>\r\n"
					+ "<!-- List of Billinfo to be billed -->\r\n";
			try {
				Statement statement = con.get().createStatement();
				String queryString = "Select a.poid_id0 ,b.poid_id0 from pin.account_t a,pin.billinfo_t b where a.account_no IN ('"
						+ AccountNo + "') and b.account_obj_id0=a.poid_id0 AND NOT b.PAYINFO_OBJ_TYPE LIKE '%prepaid%'";
				Result.fUpdateLog(queryString);
				ResultSet rs = statement.executeQuery(queryString);
				while (rs.next()) {
					Test_OutPut += "        <BillingList>\r\n" + "                <Account>" + rs.getString(1)
							+ "</Account>\r\n" + "                <Billinfo>" + rs.getString(2) + "</Billinfo>\r\n"
							+ "        </BillingList>\r\n";
				}

				Test_OutPut += "</BillRunConfiguration>\r\n" + "</BusinessConfiguration>";
			} catch (Exception e) {
				Continue.set(false);
				Test_OutPut += "Failed to AccPoID_BillPoID" + ",";
				Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
				e.printStackTrace();
			}

		} else {
			Result.fUpdateLog("Failed at intial did not entered in AccPoID_BillPoID");
			Continue.set(false);
		}
		Result.fUpdateLog("------AccPoID_BillPoID Event Details - Completed------");
		return Test_OutPut;
	}

	public String AccPoID_BillPoID(String AccountNo, String BillingProf) {
		Continue.set(true);
		String Test_OutPut = "";
		if (Continue.get()) {
			Result.fUpdateLog("------AccPoID_BillPoID Event Details------");
			Test_OutPut = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + "<BusinessConfiguration\r\n"
					+ "xmlns=\"http://www.portal.com/schemas/BusinessConfig\"\r\n"
					+ "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\r\n"
					+ "xsi:schemaLocation=\"http://www.portal.com/schemas/BusinessConfig BusinessConfiguration.xsd\">\r\n"
					+ "<!-- Sample input file for pin_bill_accts containing parameters for bill run management -->\r\n"
					+ "<!-- Modify according to guidelines -->\r\n" + "<BillRunConfiguration>\r\n"
					+ "<!-- List of Billinfo to be billed -->\r\n";
			try {
				Statement statement = con.get().createStatement();
				String queryString = "Select a.poid_id0 ,b.poid_id0 from pin.account_t a,pin.billinfo_t b where a.account_no IN ('"
						+ AccountNo + "') and b.bill_info_id IN ('" + BillingProf
						+ "')and b.account_obj_id0=a.poid_id0 AND NOT b.PAYINFO_OBJ_TYPE LIKE '%prepaid%'";
				Result.fUpdateLog(queryString);
				ResultSet rs = statement.executeQuery(queryString);
				while (rs.next()) {
					Test_OutPut += "        <BillingList>\r\n" + "                <Account>" + rs.getString(1)
							+ "</Account>\r\n" + "                <Billinfo>" + rs.getString(2) + "</Billinfo>\r\n"
							+ "        </BillingList>\r\n";
				}

				Test_OutPut += "</BillRunConfiguration>\r\n" + "</BusinessConfiguration>";
			} catch (Exception e) {
				Continue.set(false);
				Test_OutPut += "Failed to AccPoID_BillPoID" + ",";
				Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
				e.printStackTrace();
			}

		} else {
			Result.fUpdateLog("Failed at intial did not entered in AccPoID_BillPoID");
			Continue.set(false);
		}
		Result.fUpdateLog("------AccPoID_BillPoID Event Details - Completed------");
		return Test_OutPut;
	}

	public String BillPoID(String AccountNo) {
		Continue.set(true);
		String Test_OutPut = "";
		if (Continue.get()) {
			Result.fUpdateLog("------BillPoID Event Details------");
			Test_OutPut = "";
			try {
				Statement statement = con.get().createStatement();
				String queryString = "select b.poid_id0 from pin.bill_t b , pin.account_t a, pin.billinfo_t bi where b.account_obj_id0 = a.poid_id0 and b.billinfo_obj_id0 = bi.poid_id0 and "
						+ "bi.pay_type in ('10001','15003','15004') and b.invoice_obj_id0 = 0 and b.bill_no is not null and a.account_no in ('"
						+ AccountNo + "')";
				Result.fUpdateLog(queryString);
				ResultSet rs = statement.executeQuery(queryString);
				int i = 0;
				while (rs.next()) {
					Test_OutPut += "0 PIN_FLD_RESULTS     ARRAY [" + i + "]\r\n"
							+ "1    PIN_FLD_POID        POID [0] 0.0.0.1 /bill " + rs.getString(1) + "\r\n";
					i++;
				}
			} catch (Exception e) {
				Continue.set(false);
				Test_OutPut += "Failed to BillPoID" + ",";
				Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
				e.printStackTrace();
			}

		} else {
			Result.fUpdateLog("Failed at intial did not entered in BillPoID");
			Continue.set(false);
		}
		Result.fUpdateLog("------BillPoID Event Details - Completed------");
		return Test_OutPut;

	}

	public ArrayList<String> ACCPoID(String AccountNo) {
		Continue.set(true);
		ArrayList<String> arr = new ArrayList<String>();
		if (Continue.get()) {
			Result.fUpdateLog("------ACCPoID Event Details------");
			try {
				Statement statement = con.get().createStatement();
				String queryString = "Select a.poid_id0 from pin.account_t a where a.account_no IN ('" + AccountNo
						+ "')";
				Result.fUpdateLog(queryString);
				ResultSet rs = statement.executeQuery(queryString);
				while (rs.next()) {
					arr.add(rs.getString(1));
				}
			} catch (Exception e) {
				Continue.set(false);
				Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
				e.printStackTrace();
			}

		} else {
			Result.fUpdateLog("Failed at intial did not entered in ACCPoID");
			Continue.set(false);
		}
		Result.fUpdateLog("------ACCPoID Event Details - Completed------");
		return arr;

	}

	/*------------------------------------------------------------------------------------------------------
	 * Function Name: OSM_COM_ORDER_Status
	 * Use :          Passing the query and validating the status of the fields
	 * Designed By:   Srikanth.D
	 * Last Modified Date : 28-10-2019
	--------------------------------------------------------------------------------------------------------*/
	public String OSM_COM_ORDER_Status() throws Exception {
		String Test_OutPut = "", Status = "";
		Connection conn = null;
		Statement stmt = null;
		String connString = "";

		try {

			Class.forName("oracle.jdbc.driver.OracleDriver");
			Result.fUpdateLog("Entered in --- " + Environment);
			connString = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=10.6.6.10)(PORT=1564))(CONNECT_DATA=(SERVER = DEDICATED)(SERVICE_NAME=OSMCUAT)))";
			conn = DriverManager.getConnection(connString, getdata("UserName"), getdata("Password"));
			Result.fUpdateLog("connected to OSM COM DB");
			String Query = "SELECT * from all_tables";
			Result.fUpdateLog(Query);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(Query);

			int rowcount = 1;
			while (rs.next()) {
				System.out.print(rs.getString(1));
				System.out.print(rs.getString(2));
				System.out.print(rs.getString(3));
				System.out.print(rs.getString(4));
				System.out.print(rs.getString(5));
				System.out.print(rs.getString(6));

				Test_OutPut = "OWNER: " + rs.getString(1) + "," + "TABLE_NAME: " + rs.getString(2) + ","
						+ "TABLESPACE_NAME: " + rs.getString(3) + "," + "CLUSTER_NAME: " + rs.getString(4) + ","
						+ "IOT_STATUS: " + rs.getString(5) + "," + "STATUS : " + rs.getString(6);
				CreateDBReport(Test_OutPut);
				Utlities.StoreValue("OWNER", rs.getString(1));
				Utlities.StoreValue("TABLE_NAME", rs.getString(2));
				Utlities.StoreValue("TABLESPACE_NAME", rs.getString(3));
				Utlities.StoreValue("CLUSTER_NAME", rs.getString(4));
				Utlities.StoreValue("IOT_STATUS", rs.getString(5));
				Utlities.StoreValue("STATUS", rs.getString(6));
				if (rowcount == 1)
					break;

				rowcount++;
			}

			System.out.println("Executed Query");
			stmt.close();
			conn.commit();
			conn.close();

			if (Continue.get()) {
				Test_OutPut += " Validated OSM COM ORDER STATUS DB";
				Status = "PASS";
			} else {
				Test_OutPut += " OSM COM ORDER STATUS DB Failed";
				Status = "FAIL";
			}

		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Exception occurred" + ",";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------OSM COM DB Validation - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*------------------------------------------------------------------------------------------------------
	 * Function Name: OSM_SOM_ORDER_Status
	 * Use :          Passing the query and validating the status of the fields
	 * Designed By:   Srikanth.D
	 * Last Modified Date : 28-10-2019
	--------------------------------------------------------------------------------------------------------*/
	public String OSM_SOM_ORDER_Status() throws Exception {
		String Test_OutPut = "", Status = "";
		Connection conn = null;
		Statement stmt = null;
		String connString = "";

		try {

			Class.forName("oracle.jdbc.driver.OracleDriver");
			Result.fUpdateLog("Entered in --- " + Environment);
			connString = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=10.6.6.10)(PORT=1564))(CONNECT_DATA=(SERVER = DEDICATED)(SERVICE_NAME=OSMSUAT)))";
			conn = DriverManager.getConnection(connString, getdata("UserName"), getdata("Password"));

			Result.fUpdateLog("connected to OSM SOM DB");
			String Query = "SELECT * from all_tables";
			Result.fUpdateLog(Query);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(Query);

			int rowcount = 1;
			while (rs.next()) {
				System.out.print(rs.getString(1));
				System.out.print(rs.getString(2));
				System.out.print(rs.getString(3));
				System.out.print(rs.getString(4));
				System.out.print(rs.getString(5));
				System.out.print(rs.getString(6));

				Test_OutPut = "OWNER: " + rs.getString(1) + "," + "TABLE_NAME: " + rs.getString(2) + ","
						+ "TABLESPACE_NAME: " + rs.getString(3) + "," + "CLUSTER_NAME: " + rs.getString(4) + ","
						+ "IOT_STATUS: " + rs.getString(5) + "," + "STATUS : " + rs.getString(6);
				CreateDBReport(Test_OutPut);
				Utlities.StoreValue("OWNER", rs.getString(1));
				Utlities.StoreValue("TABLE_NAME", rs.getString(2));
				Utlities.StoreValue("TABLESPACE_NAME", rs.getString(3));
				Utlities.StoreValue("CLUSTER_NAME", rs.getString(4));
				Utlities.StoreValue("IOT_STATUS", rs.getString(5));
				Utlities.StoreValue("STATUS", rs.getString(6));
				if (rowcount == 1)
					break;

				rowcount++;
			}

			System.out.println("Executed Query");
			stmt.close();
			conn.commit();
			conn.close();

			if (Continue.get()) {
				Test_OutPut += " Validated OSM SOM ORDER STATUS DB";
				Status = "PASS";
			} else {
				Test_OutPut += " OSM SOM ORDER STATUS DB Failed";
				Status = "FAIL";
			}

		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Exception occurred" + ",";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------OSM SOM DB Validation - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*------------------------------------------------------------------------------------------------------
	 * Function Name: BRMCheckBucketBalance
	 * Use : Passing the query and validating the status of the fields
	 * Designed By: Soniya
	 * Last Modified Date : 15-10-2019
	--------------------------------------------------------------------------------------------------------*/
	public String BRMCheckBucketBalance(String account_no, String name) throws Exception {
		String Test_OutPut = "", Status = "";
		Connection conn = null;
		Statement stmt = null;
		String connString = "";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Result.fUpdateLog("Entered in --- " + Environment);
			connString = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=10.6.4.145)(PORT=1564))(CONNECT_DATA=(SERVER = DEDICATED)(SERVICE_NAME=BRMUAT2)))";
			conn = DriverManager.getConnection(connString, "MAVERICBRM", "Mbrm3214");
			Result.fUpdateLog("connected to DB");
			String Query = "select a.account_no, sub.current_bal, p.name, ser.NAME, ppt.status\r\n"
					+ "       from pin1.BAL_GRP_SUB_BALS_T sub, pin1.PURCHASED_PRODUCT_T ppt, pin1.product_t p, pin1.SERVICE_ALIAS_LIST_T ser, pin1.account_t a, pin1.service_t s\r\n"
					+ "       where \r\n" + "       ppt.poid_id0 = sub.grantor_obj_id0 \r\n"
					+ "       and p.poid_id0 = ppt.product_obj_id0 \r\n" + "       and ser.obj_id0 = s.poid_id0\r\n"
					+ "       and a.poid_id0 = s.account_obj_id0\r\n"
					+ "       and ppt.ACCOUNT_OBJ_ID0 = a.poid_id0\r\n" + "       and ppt.status = 1\r\n"
					+ "       and ppt.service_obj_type like '%/service/telco/gsm/telephony%'\r\n"
					+ "       and a.account_no = '" + account_no + "'\r\n" + "       and ser.name = '" + name + "'";
			Result.fUpdateLog(Query);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(Query);

			int rowcount = 1;
			while (rs.next()) {
				System.out.print(rs.getString(2));
				System.out.print(rs.getString(4));
				System.out.print(rs.getString(6));
				System.out.print(rs.getString(7));
				System.out.print(rs.getString(8));
				Test_OutPut = "account_no: " + rs.getString(2) + "," + "current_bal: " + rs.getString(4) + ","
						+ "name: " + rs.getString(6) + "," + "NAME: " + rs.getString(7) + "," + "status: "
						+ rs.getString(8);
				Utlities.StoreValue("account_no", rs.getString(2));
				Utlities.StoreValue("current_bal", rs.getString(4));
				Utlities.StoreValue("name", rs.getString(6));
				Utlities.StoreValue("NAME", rs.getString(7));
				Utlities.StoreValue("status", rs.getString(8));
				if (rowcount == 1)
					break;

				rowcount++;
			}

			System.out.println("Executed Query");
			stmt.close();
			conn.commit();
			conn.close();

			if (Continue.get()) {
				Test_OutPut += "Validated BRM DB";
				Status = "PASS";
			} else {
				Test_OutPut += "BRM Validation Failed";
				Status = "FAIL";
			}

		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Exception occurred" + ",";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------DB Validation - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*------------------------------------------------------------------------------------------------------
	 * Function Name: OSMCCheckOrderStatus
	 * Use : Passing the query and validating the status of the fields
	 * Designed By: Soniya
	 * Last Modified Date : 15-10-2019
	--------------------------------------------------------------------------------------------------------*/
	public String OSMCCheckOrderStatus(String AccountNo, String Msisdn) throws Exception {
		String Test_OutPut = "", Status = "";
		Connection conn = null;
		Statement stmt = null;
		String connString = "";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Result.fUpdateLog("Entered in --- " + Environment);
			connString = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=10.6.6.10)(PORT=1564))(CONNECT_DATA=(SERVER = DEDICATED)(SERVICE_NAME=OSMCUAT)))";
			conn = DriverManager.getConnection(connString, "MAVERICOSMC", "Mosmc3214");
			Result.fUpdateLog("connected to DB");
			String Query = " ";
			Result.fUpdateLog(Query);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(Query);

			int rowcount = 1;
			while (rs.next()) {
				System.out.print(rs.getString(2));
				System.out.print(rs.getString(4));
				System.out.print(rs.getString(6));
				System.out.print(rs.getString(7));
				System.out.print(rs.getString(8));
				Test_OutPut = "account_no: " + rs.getString(2) + "," + "current_bal: " + rs.getString(4) + ","
						+ "name: " + rs.getString(6) + "," + "NAME: " + rs.getString(7) + "," + "status: "
						+ rs.getString(8);
				Utlities.StoreValue("account_no", rs.getString(2));
				Utlities.StoreValue("current_bal", rs.getString(4));
				Utlities.StoreValue("name", rs.getString(6));
				Utlities.StoreValue("NAME", rs.getString(7));
				Utlities.StoreValue("status", rs.getString(8));
				if (rowcount == 1)
					break;

				rowcount++;
			}

			System.out.println("Executed Query");
			stmt.close();
			conn.commit();
			conn.close();

			if (Continue.get()) {
				Test_OutPut += "Validated BRM DB";
				Status = "PASS";
			} else {
				Test_OutPut += "BRM Validation Failed";
				Status = "FAIL";
			}

		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Exception occurred" + ",";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------DB Validation - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*------------------------------------------------------------------------------------------------------
	 * Function Name: OSMSCheckOrderStatus
	 * Use : Passing the query and validating the status of the fields
	 * Designed By: Soniya
	 * Last Modified Date : 15-10-2019
	--------------------------------------------------------------------------------------------------------*/
	public String OSMSCheckOrderStatus(String AccountNo, String Msisdn) throws Exception {
		String Test_OutPut = "", Status = "";
		Connection conn = null;
		Statement stmt = null;
		String connString = "";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Result.fUpdateLog("Entered in --- " + Environment);
			connString = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=10.6.6.10)(PORT=1564))(CONNECT_DATA=(SERVER = DEDICATED)(SERVICE_NAME=OSMSUAT)))";
			conn = DriverManager.getConnection(connString, "MAVERICOSMS", "Mosms3214");
			Result.fUpdateLog("connected to DB");
			String Query = "";
			Result.fUpdateLog(Query);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(Query);

			int rowcount = 1;
			while (rs.next()) {
				System.out.print(rs.getString(2));
				System.out.print(rs.getString(4));
				System.out.print(rs.getString(6));
				System.out.print(rs.getString(7));
				System.out.print(rs.getString(8));
				Test_OutPut = "account_no: " + rs.getString(2) + "," + "current_bal: " + rs.getString(4) + ","
						+ "name: " + rs.getString(6) + "," + "NAME: " + rs.getString(7) + "," + "status: "
						+ rs.getString(8);
				Utlities.StoreValue("account_no", rs.getString(2));
				Utlities.StoreValue("current_bal", rs.getString(4));
				Utlities.StoreValue("name", rs.getString(6));
				Utlities.StoreValue("NAME", rs.getString(7));
				Utlities.StoreValue("status", rs.getString(8));
				if (rowcount == 1)
					break;

				rowcount++;
			}

			System.out.println("Executed Query");
			stmt.close();
			conn.commit();
			conn.close();

			if (Continue.get()) {
				Test_OutPut += "Validated BRM DB";
				Status = "PASS";
			} else {
				Test_OutPut += "BRM Validation Failed";
				Status = "FAIL";
			}

		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Exception occurred" + ",";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------DB Validation - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public static String CreateDBReport(String DBConnectionOutput) {
		System.out.println("Creating HTML");
		// DBConnectionOutput = "ACC_STATUS: Active , BILL_INFO_ID: Active ,
		// Service_Name: SOM , Product_Name: BD12 , Purchase_Date: 12/12/12 , Usage:
		// Nothing , Cycle_Start: 12/14/19";
		DBHtml = "<html><head><title>DB Execution Results</title>" +

				"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">" +

				"<style>table {font-size: 12px;background:#E6E6E6;}</style>" +

				"</head><body bgcolor = \"green\"><div id = \"lastres\">" + "<table width='100%' border=2>" + "<tr>"
				+ "<td width =100% Style=\"color:green\">"

				+ "<center><h1> " + TestCaseN.get() + " - Report </h1>  </center>"

				+ "<table width='100%' border=2><tr>";

		String splitdboutput[] = DBConnectionOutput.split(",");
		int splitdblength = splitdboutput.toString().length();
		System.out.println(splitdblength);
		for (String splitlength : splitdboutput) {
			String Header[] = splitlength.split(":");
			HeaderValues = Header[0].trim();
			DBHtml = DBHtml + "<td width = 7%><b><center>" + HeaderValues + "</center></b></td>";
		}
		DBHtml = DBHtml + "</tr><tr>";
		for (String splitlengthanother : splitdboutput) {
			String Header1[] = splitlengthanother.split(":");

			RowValues = Header1[1].trim();
			DBHtml = DBHtml + "<td width = 7%><b><center>" + RowValues + "</center></b></td>";
		}

		DBHtml = DBHtml + "</tr>";
		System.out.println(DBHtml);
		Result.CreateDBHTMLReport(DBHtml);

		return DBConnectionOutput;

	}

	/*------------------------------------------------------------------------------------------------------
	 * Function Name: BRMCheckAccountStatus
	 * Use : Passing the query and validating the status of the fields
	 * Designed By: Zeeshan
	 * Last Modified Date : 15-10-2019
	--------------------------------------------------------------------------------------------------------*/
	public String BRM_Account_Status() throws Exception {
		String Test_OutPut = "", Status = "";
		Connection conn = null;
		Statement stmt = null;
		String connString = "", Account_Num="",AccNum=null;
		
		try {
			//Fetching Account Number form CRM.
		     AccNum=Keyword_SiebelCRM.AccountNumber;
		     
			//  Entering Account Number.
			if(!(AccNum.equals(""))){
				Account_Num=AccNum;
				Result.fUpdateLog("Entering Dynamic Account Number "+Account_Num);
			}else {
				Account_Num=getdata("Account_Number");
				Result.fUpdateLog("Entering Design sheet Account Number "+Account_Num);
			} 	
			
		}catch(Exception e) {
			Account_Num=getdata("Account_Number");
			Result.fUpdateLog("Entering Design sheet Account Number "+Account_Num);
		}
		System.out.println("Account number "+ AccNum);
		

		
		try {

			Class.forName("oracle.jdbc.driver.OracleDriver");
			Result.fUpdateLog("Entered in --- " + Environment);
			connString = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=10.6.4.145)(PORT=1564))(CONNECT_DATA=(SERVER = DEDICATED)(SERVICE_NAME=BRMUAT2)))";
			conn = DriverManager.getConnection(connString, getdata("UserName"), getdata("Password"));
			// ="jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=10.6.4.145)(PORT=1564))(CONNECT_DATA=(SERVER
			// = DEDICATED)(SERVICE_NAME=BRMUAT2)))";
			// conn = DriverManager.getConnection(connString,"MAVERICBRM","Mbrm3214");

			Result.fUpdateLog("connected to DB");
	
			String Query="SELECT A.ACCOUNT_NO, DECODE(A.STATUS, '10100', 'Active', '10102', 'Inactive', '10103', 'Closed') ACC_STATUS, B.BILL_INFO_ID, " + 
					"DECODE(B.STATUS, '10100', 'Active', '10102', 'Inactive', '10103', 'Closed') BP_STATUS, " + 
					"B.ACTG_CYCLE_DOM BillCycle, " + 
					"SER.package_name, ser.primary_msisdn, P.NAME PRODUCT_NAME, PPT.PURCHASE_START_T PURCHASE_DATE, PPT.USAGE_START_T, PPT.CYCLE_START_T, " + 
					"ppt.cycle_end_t cycle_end, " + 
					"PPT.STATUS,PPT.PRICE_LIST_NAME " + 
					"  FROM PIN1.ACCOUNT_T A, pin1.PURCHASED_PRODUCT_T PPT, pin1.PRODUCT_T P, pin1.service_media_t SER, pin1.billinfo_t B " + 
					" WHERE     PPT.PRODUCT_OBJ_ID0 = P.POID_ID0 " + 
					"       AND PPT.ACCOUNT_OBJ_ID0 = A.POID_ID0 " + 
					"       AND A.ACCOUNT_NO = '"+Account_Num+"' " + 
					"       AND A.POID_ID0 = B.ACCOUNT_OBJ_ID0 " + 
					"       and ser.obj_id0 = ppt.service_obj_id0 " + 
					"       order by PPT.STATUS" ;
			
			
			Result.fUpdateLog("Account Number : " + getdata("Account_Number"));
			
			Result.fUpdateLog(Query);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(Query);

			int rowcount = 1;
			Test_OutPut = "ACCOUNT_NO:                 , ACC_STATUS:                 , BILL_INFO_ID:                 , BP_STATUS:                 , BILLCYCLE:                 , PACKAGE_NAME:                 , PRIMARY_MSISDN:                 , PRODUCT_NAME:                 , PURCHASE_DATE:                 , USAGE_START_T:                 , CYCLE_START_T:                 , CYCLE_END:                 , STATUS:                 , PRICE_LIST_NAME:                 " ;
			
			while (rs.next()) {
				System.out.print(rs.getString(2));
				System.out.print(rs.getString(4));
				System.out.print(rs.getString(6));
				System.out.print(rs.getString(7));
				System.out.print(rs.getString(8));
				System.out.print(rs.getString(9));
				System.out.println(rs.getString(10));
		
				Test_OutPut = "ACCOUNT_NO: " + rs.getString(1) + "," +"ACC_STATUS: " + rs.getString(2) + "," + "BILL_INFO_ID: " + rs.getString(3) + ","
						    + "BP_STATUS: " + rs.getString(4) + "," + "BILLCYCLE: " + rs.getString(5) + ","+ "PACKAGE_NAME: " + rs.getString(6) + "," 
						   + "PRIMARY_MSISDN: " + rs.getString(7) + ","+ "PRODUCT_NAME: " + rs.getString(8)+","+ "PURCHASE_DATE: " + rs.getString(9)+","
					       + "USAGE_START_T: " + rs.getString(10)+","+ "CYCLE_START_T: " + rs.getString(11) +","+ "CYCLE_END: " + rs.getString(12)+","
						   + "STATUS: " + rs.getString(13)+ ","+ "PRICE_LIST_NAME: " + rs.getString(14);
		
		
				Utlities.StoreValue("ACC_STATUS", rs.getString(2));
				Utlities.StoreValue("BILL_INFO_ID", rs.getString(4));
				Utlities.StoreValue("Service_Name", rs.getString(6));
				Utlities.StoreValue("Product_Name", rs.getString(7));
				Utlities.StoreValue("Purchase_Date", rs.getString(8));
				Utlities.StoreValue("Usage", rs.getString(9));
				Utlities.StoreValue("Cycle_Start", rs.getString(10));
				if (rowcount == 1)
					break;

				rowcount++;
			}
		
			System.out.println("Executed Query");
			CreateDBReport(Test_OutPut);
			stmt.close();
			conn.commit();
			conn.close();

			if (Continue.get()) {
				Test_OutPut += " Validated BRM DB";
				Status = "PASS";
			} else {
				Test_OutPut += " BRM Validation Failed";
				Status = "FAIL";
			}

		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Exception occurred" + ",";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------DB Validation - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*------------------------------------------------------------------------------------------------------
	 * Function Name : Verifying Account_BillingProfile Status in BRM DB
	 * Use           : Passing the query and validating the Billing Profile status of the fields
	 * Designed By   : Srikanth.D
	 * Last Modified Date : 28-Nov-2019
	--------------------------------------------------------------------------------------------------------*/
	public String Account_BillingProfileStatus() throws Exception {
		String Test_OutPut = "", Status = "";
		Connection conn = null;
		Statement stmt = null;
		String connString = "",Account_Num="",AccNum=null;
		
		try {
			// Fetching Account Number form CRM.
			AccNum = Keyword_SiebelCRM.AccountNumber;

			// Entering Account Number.
			if (!(AccNum.equals(""))) {
				Account_Num = AccNum;
				Result.fUpdateLog("Entering Dynamic Account Number " + Account_Num);
			} else {
				Account_Num = getdata("Account_Number");
				Result.fUpdateLog("Entering Design sheet Account Number " + Account_Num);
			}

		} catch (Exception e) {
			Account_Num = getdata("Account_Number");
			Result.fUpdateLog("Entering Design sheet Account Number " + Account_Num);
		}
		System.out.println("Account number " + AccNum);

		// Fetching Dynamic Generated Bill Number.
		String Dynamic_BillingProfile_Number = Utlities.FetchStoredValue("Modify_ChangeBillingProfile",
				"Modify_ChangeBillingProfile", "New_BillingProfileNumber");

		System.out.println(" New Billing Profile Number is: " + Dynamic_BillingProfile_Number);

		try {

			Class.forName("oracle.jdbc.driver.OracleDriver");
			Result.fUpdateLog("Entered in --- " + Environment.get());
			connString = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=10.6.4.145)(PORT=1564))(CONNECT_DATA=(SERVER = DEDICATED)(SERVICE_NAME=BRMUAT2)))";
			conn = DriverManager.getConnection(connString, getdata("UserName"), getdata("Password"));

			Result.fUpdateLog("connected to DB");
			String Query = "select a.account_no, DECODE(a.status,'10100', 'Active', '10102', 'Inactive', '10103', 'Closed') as \"ACCOUNT STATUS\","
					+ "b.bill_info_id,DECODE(b.status,'10100', 'Active', '10102', 'Inactive', '10103', 'Closed') as \"BILL INFO STATUS\","
					+ "b.actg_cycle_dom,b.payinfo_obj_type as \"PAYMENT METHOD\",b.pay_type, b.created_t,"
					+ "b.billing_state,"
					+ "b.billing_status, b.effective_t, b.billing_status_flags, b.actg_next_t as \"NEXT BILL DATE\" "
					+ "from pin1.account_t a, pin1.BILLINFO_T b " + "where a.poid_id0 = b.account_obj_id0  " + "and  "
					+ "a.account_no =  '" + Account_Num + "'"
					+" and  b.bill_info_id ='"+ Dynamic_BillingProfile_Number +"'";

			Result.fUpdateLog("Account Number : " + Account_Num);
			Result.fUpdateLog(Query);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(Query);

			System.out.println(
					"ACCOUNT_NO           ACCOUNT STATUS           BILL_INFO_ID          BILL INFO STATUS          ACTG_CYCLE_DOM         PAYMENT METHOD"
							+ "         PAY_TYPE             CREATED_T                BILLING_STATE         BILLING_STATUS            EFFECTIVE_T            BILLING_STATUS_FLAFS "
							+ "         NEXT BILL DATE");

			int rowcount = 1;
			Test_OutPut = "ACCOUNT_NO:             ,ACCOUNT STATUS:                ,BILL_INFO_ID:              ,BILL INFO STATUS:     "
					+ "               ,ACTG_CYCLE_DOM:             ,PAYMENT METHOD:           ,"
					+"PAY_TYPE:                ,CREATED_T:                   ,"
					+"BILLING_STATE:           ,BILLING_STATUS:                 ,"
					+"EFFECTIVE_T:             ,BILLING_STATUS_FLAFS:             ,"
					+"NEXT BILL DATE:           ";
			while (rs.next()) {
				System.out.print(rs.getString(1) + "          ");
				System.out.print(rs.getString(2) + "          ");
				System.out.print(rs.getString(3) + "          ");
				System.out.print(rs.getString(4) + "          ");
				System.out.print(rs.getString(5) + "          ");
				System.out.print(rs.getString(6) + "          ");
				System.out.print(rs.getString(7) + "          ");
				System.out.print(rs.getString(8) + "          ");
				System.out.print(rs.getString(9) + "          ");
				System.out.print(rs.getString(10) + "          ");
				System.out.print(rs.getString(11) + "          ");
				System.out.print(rs.getString(12) + "          ");
				System.out.print(rs.getString(13) + "          ");

				System.out.println("");
				Test_OutPut = "ACCOUNT_NO: " + rs.getString(1) + "," + "ACCOUNT STATUS: " + rs.getString(2) + ","
						+ "BILL_INFO_ID: " + rs.getString(3) + "," + "BILL INFO STATUS: " + rs.getString(4) + ","
						+ "ACTG_CYCLE_DOM: " + rs.getString(5) + "," + "PAYMENT METHOD: " + rs.getString(6) + ","
						+ "PAY_TYPE: " + rs.getString(7) + "," + "CREATED_T: " + rs.getString(8) + ","
						+ "BILLING_STATE: " + rs.getString(9) + "," + "BILLING_STATUS: " + rs.getString(10) + ","
						+ "EFFECTIVE_T: " + rs.getString(11) + "," + "BILLING_STATUS_FLAFS: " + rs.getString(12) + ","
						+ "NEXT BILL DATE: " + rs.getString(13);
				CreateDBReport(Test_OutPut);

				if (rowcount == 1)
					break;
			}
			rowcount++;
			// if(rowcount==10) {
			// break;
			// }

			System.out.println("Executed Query");
			stmt.close();
			conn.commit();
			conn.close();

			if (Continue.get()) {
				Test_OutPut += " Validated Billing Profile Status in BRM DB";
				Status = "PASS";
			} else {
				Test_OutPut += " BRM Validation Failed";
				Status = "FAIL";
			}

		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Exception occurred" + ",";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------DB Validation - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*------------------------------------------------------------------------------------------------------
	 * Function Name: Account_ Change MSISDN Status
	 * Use : Passing the query and validating the MSISDN status of the fields
	 * Designed By   : Srikanth.D
	 * Last Modified Date : 28-Nov-2019
	--------------------------------------------------------------------------------------------------------*/
	public String Account_MSISDN_Status() throws Exception {
		String Test_OutPut = "", Status = "";
		Connection conn = null;
		Statement stmt = null;
		String connString = "";
		String AccountNumber = getdata("Account_Number");
		Result.fUpdateLog("Account Number : " + AccountNumber);

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Result.fUpdateLog("Entered in --- " + Environment.get());
			connString = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=10.6.4.145)(PORT=1564))(CONNECT_DATA=(SERVER = DEDICATED)(SERVICE_NAME=BRMUAT2)))";
			conn = DriverManager.getConnection(connString, getdata("UserName"), getdata("Password"));
			Result.fUpdateLog("connected to DB");

			String Query = "select a.account_no, DECODE(a.status,'10100', 'Active', '10102', 'Inactive', '10103', 'Closed') as \"ACCOUNT STATUS\", "
					+ "sm.pack_id, sm.pack_code, sm.package_name, sm.primary_MSISDN, DECODE(s.status,'10100', 'Active', '10102', 'Inactive', '10103', 'Closed') as \"SERVICE STATUS\", "
					+ "s.login, s.effective_t as \"MSISDN EFFECTIVE DATE\" "
					+ "from pin1.account_t a, pin1.service_media_t sm, pin1.service_t s   "
					+ "where s.account_obj_id0 = a.poid_id0 and  " + "sm.obj_id0 = s.poid_id0 and " + "a.account_no = '"
					+ getdata("Account_Number") + "'";
			Result.fUpdateLog(Query);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(Query);

			System.out.println(
					"ACCOUNT_NO           STATUS           PACK_ID       PACK_CODE       PACKAGE_NAME          PRIMARY_MSISDN         SERVICE STATUS          "
							+ "LOGIN               MSISDN EFFECTIVE DATE   ");

			int rowcount = 1;
			while (rs.next()) {

				System.out.print(rs.getString(1) + "          ");
				System.out.print(rs.getString(2) + "          ");
				System.out.print(rs.getString(3) + "          ");
				System.out.print(rs.getString(4) + "          ");
				System.out.print(rs.getString(5) + "          ");
				System.out.print(rs.getString(6) + "          ");
				System.out.print(rs.getString(7) + "          ");
				System.out.print(rs.getString(8) + "          ");
				System.out.print(rs.getString(9) + "          ");

				System.out.println("");
				Test_OutPut = "ACCOUNT_NO: " + rs.getString(1) + "," + "ACCOUNT STATUS: " + rs.getString(2) + ","
						+ "PACK_ID: " + rs.getString(3) + "," + "PACK_CODE: " + rs.getString(4) + "," + "PACKAGE_NAME: "
						+ rs.getString(5) + "," + "PRIMARY_MSISDN: " + rs.getString(6) + "," + "SERVICE STATUS: "
						+ rs.getString(7) + "," + "LOGIN: " + rs.getString(8) + "," + "MSISDN EFFECTIVE DATE: "
						+ rs.getString(9);
				CreateDBReport(Test_OutPut);

				if (rowcount == 1)
					break;
			}
			rowcount++;
			// if(rowcount==10) {
			// break;
			// }

			System.out.println("Executed Query");
			stmt.close();
			conn.commit();
			conn.close();

			if (Continue.get()) {
				Test_OutPut += " Validated MSISDN Status in BRM DB";
				Status = "PASS";
			} else {
				Test_OutPut += " BRM Validation Failed";
				Status = "FAIL";
			}

		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Exception occurred" + ",";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------DB Validation - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}
	
	/*------------------------------------------------------------------------------------------------------
	 * Function Name: Suspend_Status
	 * Use : TO Check Suspend Status in BRM DB
	 * Designed By   : Srikanth.D
	 * Last Modified Date : 13-Dec-2019
	--------------------------------------------------------------------------------------------------------*/
	public String Suspend_Status() throws Exception {
		String Test_OutPut = "", Status = "";
		Connection conn = null;
		Statement stmt = null;
		String connString = "",MsisdnNum="",MSISDN="";
		String AccountNumber = getdata("Account_Number");
		Result.fUpdateLog("Account Number : " + AccountNumber);

		try {
			// Fetching MSISDN Number.
			MsisdnNum =Utlities.FetchStoredValue("Batelco_Order_Suspension",
					"BT_OrderSuspension", "MSISDN_Number");
			System.out.println(MsisdnNum);
			}catch (Exception e) {
				System.out.println(e);
			}
			
			//  Entering Dynamic Fetching MSISDN Number.
			if(!(MsisdnNum.equals(""))){
				MSISDN=MsisdnNum;
				Result.fUpdateLog("Entering Dynamic Fetching Account MSISDN "+MSISDN);
			}else {
				//  Entering Design Sheet MSISDN Number.
				MSISDN=getdata("MSISDN_NUMBER");
				Result.fUpdateLog("Entering Design sheet Account MSISDN "+MSISDN);
			} 
				
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Result.fUpdateLog("Entered in --- " + Environment.get());
			connString = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=10.6.4.145)(PORT=1564))(CONNECT_DATA=(SERVER = DEDICATED)(SERVICE_NAME=BRMUAT2)))";
			conn = DriverManager.getConnection(connString, getdata("UserName"), getdata("Password"));
			Result.fUpdateLog("connected to DB");

			String Query = "SELECT DECODE (S.STATUS, " + 
					"               '10100', 'Active', " + 
					"               '10102', 'Inactive', " + 
					"               '10103', 'Closed') " + 
					"          STATUS, " + 
					"       TT.NAME " + 
					"  FROM pin1.SERVICE_T S, pin1.SERVICE_ALIAS_LIST_T TT " + 
					"WHERE S.POID_ID0 = TT.OBJ_ID0 AND TT.NAME in ('"+ MSISDN+ "')";
	
			Result.fUpdateLog(Query);
			
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(Query);

			System.out.println("STATUS          NAME ");

			int rowcount = 1;
			Test_OutPut = "STATUS:                 , NAME:                 " ;
			
			while (rs.next()) {

				System.out.print(rs.getString(1) + "          ");
				System.out.print(rs.getString(2) + "          ");

				System.out.println("");
				Test_OutPut = "STATUS: " + rs.getString(1) + "," + "NAME: " + rs.getString(2);

				if (rowcount == 1)
					break;
			}
			rowcount++;
			
			// if(rowcount==10) {
			// break;
			// }

			System.out.println("Executed Query");
			CreateDBReport(Test_OutPut);
			
			
			stmt.close();
			conn.commit();
			conn.close();

			if (Continue.get()) {
				Test_OutPut += " Validated "+TestCaseN.get()+" in BRM DB";
				Status = "PASS";
			} else {
				Test_OutPut += " BRM Validation Failed";
				Status = "FAIL";
			}

		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Exception occurred" + ",";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------DB Validation - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}
	
	
	
	/*------------------------------------------------------------------------------------------------------
	 * Function Name : fechingOrderRowID
	 * Use           : Fetching ROW_ID for Order from BRM DB
	 * Designed By   : Srikanth.D
	 * Last Modified Date : 13-Feb-2020
	 * CRM DB 
	--------------------------------------------------------------------------------------------------------*/
	public String fechingOrderRowID(String Order_Number) throws Exception {
		String Test_OutPut = "", Status = "";
		Connection conn = null;
		Statement stmt1 = null,stmt2=null;
		String connString = "";
		
		//Fetching Order Number.
		//String Order_Number =KSBL.ordernum;
	   //	String Order_Number ="20010000238";
		

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Result.fUpdateLog("Entered in --- " + Environment.get());
			connString = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=10.6.6.10)(PORT=1564))(CONNECT_DATA=(SERVER = DEDICATED)(SERVICE_NAME=SBLUAT)))";
			conn = DriverManager.getConnection(connString, "MAVERICKSBL", "Msbl3214");

			Result.fUpdateLog("connected to DB");
			String OrderID_Query = "select row_id from siebel.s_order where order_num = '"+Order_Number+"'";
		
			Result.fUpdateLog("Order Number : " +Order_Number);
			Result.fUpdateLog(OrderID_Query);
			stmt1 = conn.createStatement();
			ResultSet rs1 = stmt1.executeQuery(OrderID_Query);
			System.out.println("");
			System.out.println("--------------------------  ORDER_ID Details       --------------------");
			System.out.println(" ORDER_ID ");
			
			while (rs1.next()) {
				 OrderID = rs1.getString(1);
			System.out.println(OrderID);
				System.out.println("---------------------------------------------------------------------------");
			}
	
			
			System.out.println("Executed Query");
			stmt1.close();
			
			String ORDER_OrderID=OrderID;
			
			String PaymentID_Query = "Select PAY.ROW_ID,PAY.CREATED,PAY.PAY_STAT_CD,PAY.PAY_TYPE_CD,PAY.PAY_AMT From siebel.S_SRC_PAYMENT PAY" + 
					" Where PAY.ORDER_ID = '"+ORDER_OrderID+"'";
			Result.fUpdateLog("Order ID : " +ORDER_OrderID);
			
			Result.fUpdateLog(PaymentID_Query);
			stmt2 = conn.createStatement();
			ResultSet rs2 = stmt2.executeQuery(PaymentID_Query);
			System.out.println("");
			System.out.println("----------------------------     ORDER PAYMENT ID Details    -------------------------------------");
			System.out.println(" ROW_ID            CREATED             PAY_STAT_CD            PAY_TYPE_CD             PAY_AMT ");

			
			while (rs2.next()) {
					PaymentID =  rs2.getString(1);
				String CREATED =  rs2.getString(2) ;
				String PAY_STAT_CD =rs2.getString(3);
				String PAY_TYPE_CD = rs2.getString(4);
				String PAY_AMT = rs2.getString(5) ;
			if(PAY_STAT_CD.equals("Submitted")) {
		    	System.out.println( PaymentID+"        "+CREATED+"          "+PAY_STAT_CD+"       "+ PAY_TYPE_CD+"          "+ PAY_AMT);
				System.out.println("---------------------------------------------------------------------------------------------------");
				break;
			}
			
			
			}
			Test_OutPut ="OrderID "+ OrderID +"PaymentID "+PaymentID;
			stmt2.close();
			conn.commit();
			conn.close();

//			if (Continue.get()) {
//				Test_OutPut += " Fetching Order OrderID from BRM DB";
//				Status = "PASS";
//			} else {
//				Test_OutPut += " Fetching Order OrderID from BRM DB";
//				Status = "FAIL";
//			}

		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Exception occurred" + ",";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();
		}
		 return  OrderID+":"+PaymentID;
		//return Status + "@@" + Test_OutPut + "<br/>";
	}
	public static void main(String[] args) {

		// CreateDBReport("ACC_STATUS: Active , BILL_INFO_ID: Active , Service_Name: SOM
		// , Product_Name: BD12 , Purchase_Date: 12/12/12 , Usage: Nothing ,
		// Cycle_Start: 12/14/19");
		// System.setProperty("webdriver.chrome.driver",
		// "C:\\Users\\c.srikanthd\\Desktop\\ProjectSpace\\Workspace\\Telecom-Master\\Telecom-Master\\Drivers\\chromedriver78.exe");
		// driver= new ChromeDriver();
		//// driver=new HtmlUnitDriver();
		// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// driver.manage().window().maximize();
		//

	}
}