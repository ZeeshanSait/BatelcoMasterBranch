package Libraries;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Keyword_SiebelCRM extends Driver {
	Common CO = new Common();
	Random R = new Random();
	Keyword_Validations KV = new Keyword_Validations();
	Keyword_API KA = new Keyword_API();
	Keyword_DB KDB = new Keyword_DB();
    SOAP_CashRegister SOAP_C=new SOAP_CashRegister();
    
	public static int COL_FUL_STATUS;
	public static String Account_Number;
	public static String Cust_Order_No;
	public static String ID_Type = pulldata("Contact_Identification_Type");
	public String randonNumber = CO.Random_IDGeneration(ID_Type);
	public static String AccountNumber;
	public static String MsisdnNumber;
	public static String ordernum;
	public static String Payment_ORDERID;
	public static String Payment_PAYMENTID;
	public static double PaymentAmount;
	
	/*--------------------------------------------------------------
	 * Method Name			: BatelcoSiebelLogin
	 * Arguments			: None
	 * Use 					: Opens a New Browser and logins to the Siebel CRM application
	 * Designed By			: Anusha
	 * Last Modified Date 	: 28-Aug-2019
	//---------------------------------------------------------------------------------------------------------*/
	
	public String BatelcoSiebelLogin() {
		String test="";

		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------Siebel Login Event Details------");
		try {

			if (!(getdata("Browser").equals(""))) {
				browser.set(getdata("Browser"));
			} else {
				browser.set("ie");
			}

			URL.set(getdata("URL/HOST"));
			Result.fUpdateLog("Enviroment: " + Environment.get());
			Result.fUpdateLog("Project_Name: " + Project.get());
			Result.fUpdateLog("Url: " + URL.get());
			Browser.OpenBrowser(browser.get(), URL.get());
			Result.fUpdateLog("Browser Opened Successfully");
			Thread.sleep(2000);
		
			// Entering Username
			Browser.WebEdit.Set("Login_User", getdata("UserName"));
			
			// Entering Password
			Browser.WebEdit.Set("Login_Pswd", getdata("Password"));
			Result.fUpdateLog("Opening Browser and navigating to the URL");
			Result.takescreenshot("Opening Browser and navigating to the URL");
				
			// Clicking On Login button.
			// Browser.WebButton.click("Login");
			cDriver.get().findElement(By.xpath("//a[@id='s_swepi_22']")).click();


            CO.ToWait();
			Thread.sleep(5000);
			Driver.Continue.set(true);
			
			CO.ToWait();
			if (Continue.get()) {
				Test_OutPut += "Successfully Logged In With : " + getdata("UserName");				
				Result.fUpdateLog("Login Successfully with user " + getdata("UserName"));
				Status = "PASS";
			} else {
				Test_OutPut += "Login Failed" + ",";
				Result.takescreenshot("Login Failed");
				Result.fUpdateLog("Login Failed");
				Status = "FAIL";
			}

		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Exception occurred" + ",";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------Siebel Login Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}
	
	/*-------------------------------------------------------------------------------------------------------
	* Method Name        : BatelcoSiebelLogout
	* Arguments          : None
	* Use                : Logout's from Seibel CRM Application
	* Designed By        : Soniya V
	* Last Modified Date : 12-Nov-2019
	//---------------------------------------------------------------------------------------------------------*/
	public String BatelcoSiebelLogout() {

	String Test_OutPut = "", Status = "";
	Result.fUpdateLog("------Siebel Logout Event Details------");
	try {

	if (!(getdata("Browser").equals(""))) {
	browser.set(getdata("Browser"));
	} else {
	browser.set("ie");
	}

	Browser.WebButton.waittillvisible("Settings_Tab");
	Method.Highlight("Settings_Tab", "WebButton");
	
	//Clicking on Settings Tab
	Browser.WebButton.click("Settings_Tab");
	Result.fUpdateLog("Clicking on Settings Tab");
	Browser.WebButton.waittillvisible("Logout_Button");
	Method.Highlight("Logout_Button", "WebButton");
	
	// Clicking On Logout button.
	Browser.WebButton.click("Logout_Button");
	//Result.takescreenshot("Clicking on Logout Button");
	Result.fUpdateLog("Clicking on Logout Button");

	CO.ToWait();
	Thread.sleep(5000);
	Driver.Continue.set(true);
	
	CO.ToWait();
	if (Continue.get()) {
	Test_OutPut += "Successfully Logged Out";
	Result.takescreenshot("Logout Successful");
	Result.fUpdateLog("Logout Successful");
	Status = "PASS";
	
	} else {
	Test_OutPut += "Logout Failed" + ",";
	Result.takescreenshot("Logout Failed");
	Result.fUpdateLog("Logout Failed");
	Status = "FAIL";
	}

	} catch (Exception e) {
	Continue.set(false);
	Test_OutPut += "Exception occurred" + ",";
	Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
	Status = "FAIL";
	e.printStackTrace();
	}
	Result.fUpdateLog("------Siebel Logout Event Details - Completed------");
	return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*----------------------------------------------------------------------------------------------------
	 * Method Name          : BatelcoContactCreation
	 * Arguments			: None
	 * Use 					: Creates a new contact with the specific data.
	 * Designed By			: Srikanth.D and Soniya.
	 * Last Modified Date 	: 30-Aug-2019
	--------------------------------------------------------------------------------------------------------*/
	public String BatelcoContactCreation() {
		String Test_OutPut = "", Status = "";

		Result.fUpdateLog("------Contact Creation Event Details------");
		try {

			Thread.sleep(20000);
			Result.takescreenshot("Login Successfully with user: " + getdata("UserName"));		
			Thread.sleep(4000);
			// Clicking on Contacts.
			CO.JavaScriptClick("Contacts", "WebLink");
			Result.takescreenshot("Clicking on Contacts.");
			Result.fUpdateLog("Clicking on Contacts.");
             
			// Clicking on MyContacts.
			Browser.WebLink.waittillvisible("MyContacts");
			Method.Highlight("MyContacts", "WebLink");
			CO.JavaScriptClick("MyContacts", "WebLink");

			// Clicking on add New Contact
			Method.Highlight("Add Contact:New", "WebButton");
			CO.JavaScriptClick("Add Contact:New", "WebButton");
			Result.fUpdateLog(" Creating New Contact");
			Thread.sleep(2000);
			
			// Selecting Identification_Type
			if (!(getdata("Account_Identification_Type").equals(""))) {
				Browser.WebTable.SetDataE("Contact_table", 2, 2, "BT_Identification_Type", getdata("Account_Identification_Type"));
				Result.fUpdateLog("Selecting Identification Type "+getdata("Account_Identification_Type"));
			} else {
				Browser.WebTable.SetDataE("Contact_table", 2, 2, "BT_Identification_Type", pulldata("Account_Identification_Type"));
				Result.fUpdateLog("Selecting Identification Type "+pulldata("Account_Identification_Type"));
			}
						
//			// Selecting Identification Type as Passport
//			Browser.WebTable.SetDataE("Contact_table", 2, 2, "BT_Identification_Type", pulldata("Account_Identification_Type"));
//			Result.takescreenshot("Selecting Identification Type ");
//			Result.fUpdateLog("Selecting Identification Type ");
//			Thread.sleep(2000);
						
			// Entering static Identification Number.
			if (!(getdata("Identification Number").equals(""))) {
				Browser.WebTable.SetDataE("Contact_table", 2, 3, "BT_Identification_Number", getdata("Identification Number"));
				Result.fUpdateLog("Entering Identification Number "+ getdata("Identification Number"));
			} else {
				Browser.WebTable.SetDataE("Contact_table", 2, 3, "BT_Identification_Number", randonNumber);
				Result.fUpdateLog("Entering Identification Number "+randonNumber);
			}
	
			
//			//Entering Dynamic Identification Number.
//			Browser.WebTable.click("Contact_table", 2, 3);
//			Browser.WebTable.SetDataE("Contact_table", 2, 3, "BT_Identification_Number", randonNumber);
//			Result.fUpdateLog("Entering Identification Number ");

			// Entering the First Name.
			String FirstName = "Auto" + CO.CallRandomName();
			Browser.WebTable.SetDataE("Contact_table", 2, 4, "First_Name", FirstName);
			Result.fUpdateLog("Entering the First Name."+FirstName);
			
			// Entering the Last Name.
			String BtLastName =  CO.CallRandomName() + "Maveric";
			Browser.WebTable.SetDataE("Contact_table", 2, 5, "Last_Name", BtLastName);
			Result.fUpdateLog("Entering the Last Name."+BtLastName);
			
			// Selecting Mr/Mrs
			if (!(getdata("Mr/Mrs").equals(""))) {
				Browser.WebTable.SetDataE("Contact_table", 2, 6, "M_M", getdata("Mr/Mrs"));
		
			} else {
				Browser.WebTable.SetDataE("Contact_table", 2, 6, "M_M", pulldata("Mr/Mrs"));
				Result.fUpdateLog("Selecting Mr/Mrs"+pulldata("Mr/Mrs"));
			}

			// Scroll into Gender Dropdown .
			CO.scroll("Gender_bat", "ListBox");

			// Storing Random Number in StoreDatabase.
			Utlities.StoreValue("Contact_Identification_Number", randonNumber);

			
			// Entering Nationality
			if (!(getdata("Nationality").equals(""))) {
				Browser.ListBox.listselect("Nationality", getdata("Nationality"));
		
			} else {
				Browser.ListBox.listselect("Nationality", pulldata("Nationality"));
				Result.fUpdateLog("entered Nationality" + pulldata("Nationality"));
			}
			

			// Entering Date of Birth
			if (!(getdata("Date_Of_Birth").equals(""))) {
				Browser.WebEdit.Set("Date_Of_Birth", getdata("Date_Of_Birth"));
			} else {
				Browser.WebEdit.Set("Date_Of_Birth", pulldata("Date_Of_Birth"));
				Result.fUpdateLog(" Entering Date of Birth" + pulldata("Date_Of_Birth"));
			}


			// Entering Email Address
			if (!(getdata("EmailAddress").equals(""))) {
				Browser.WebEdit.Set("EmailAddress", getdata("EmailAddress"));
			} else {
				Browser.WebEdit.Set("EmailAddress", pulldata("EmailAddress"));
			}
			Result.fUpdateLog("Entered Email address" + pulldata("EmailAddress"));

			// Selecting Identification_Type_Expiry Date
			if (!(getdata("IdentificationTypeExpiryDate").equals(""))) {
				Browser.WebEdit.Set("IdentificationTypeExpiryDate", getdata("Acc_IdentifiExpDate"));
			} else {
				Browser.WebEdit.Set("IdentificationTypeExpiryDate", pulldata("Acc_IdentifiExpDate"));
			}
			Result.fUpdateLog("Selecting Identification Type Expiry Date: " + pulldata("Acc_IdentifiExpDate"));
			
			// Selecting Gender
			if (!(getdata("Gender").equals(""))) {
				Browser.ListBox.listselect("Gender", getdata("Gender"));
			} else {
				Browser.ListBox.listselect("Gender", pulldata("Gender"));			
			}
			Result.fUpdateLog("Entered Gender: " + pulldata("Gender"));
            Thread.sleep(2000);
            
			// Entering MobileNo
			if (!(getdata("MobileNo").equals(""))) {
				Browser.WebEdit.Set("MobileNo", getdata("MobileNo"));
			} else {
				Browser.WebEdit.Set("MobileNo", pulldata("MobileNo"));
			}
			Result.takescreenshot("Entered Contact Details");
			Result.fUpdateLog("Entered Mobile Number: " + pulldata("MobileNo"));
            
			// Selecting Low Risk	
			// Clicking on Lowrisk button.
			Browser.WebButton.waittillvisible("Lowrisk_btn");
			Browser.WebButton.click("Lowrisk_btn");
			
			// Clicking on Lowrisk Search button.
			Browser.WebButton.waittillvisible("Lowrisk_searchbtn");
			Browser.WebButton.click("Lowrisk_searchbtn");
				
			// entering Low Risk Data in Search Box.
			CO.waitSeconds(2);
			Browser.WebTable.click("Occupation_type", 2, 4);
			Browser.WebTable.SetData("Occupation_type", 2, 4, "BT_Occupation_Type", "Low Risk");
			// Browser.WebButton.click("Occupation_gobtn");
			

			// click on ok button for saving the Occupation Type.
			Thread.sleep(4000);
			Browser.WebButton.waittillvisible("Occupation_okbtn");
			Browser.WebButton.click("Occupation_okbtn");	
		
			Thread.sleep(3000);
			
			// Save Contact
			// click on Setting Icon Button.
			Browser.WebButton.waittillvisible("Bat_Contact_Menu");
			Browser.WebButton.click("Bat_Contact_Menu");
			Thread.sleep(2000);
			
			// Selecting Save Record option.
			Method.Highlight("Saverecord", "WebLink");
			Browser.WebLink.waittillvisible("SaveRecord");
			Result.takescreenshot(" Save Contact");
			Result.fUpdateLog(" Save Contact");
			Browser.WebLink.click("SaveRecord");

			// ----------- Searching Identification Number ------------------
			Thread.sleep(2000);
			
			// Clicking on Contacts
			//Browser.WebLink.click("Contacts");
			CO.JavaScriptClick("Contacts", "WebLink");
			
			// Clicking on My contacts
			Browser.WebLink.click("MyContacts");
			Thread.sleep(1000);
			
			// Clicking on Search_Contact_button
    		CO.isClickable("Search_Contact_button");
			CO.ToWait();
			CO.JavaScriptClick("Search_Contact_button", "WebButton");
			Result.takescreenshot("Searching The Contact Using Identification Number: "+randonNumber);
		
			
			// Entering Identification Number.
			Browser.WebTable.click("Search_table", 2, 3);
			Browser.WebTable.SetData("Search_table", 2, 3, "BT_Identification_Number", randonNumber);
						
			// Fetching the Dynamic_Identification_Number and Stores in String Variable.
			String Dynamic_Identification_Number = Utlities.FetchStoredValue("ConsumerNewActivation",
					"NewActivation", "Contact_Identification_Number");

			System.out.println(Dynamic_Identification_Number);
			Result.fUpdateLog("Dynamic Identification Number is : "+Dynamic_Identification_Number);
			Thread.sleep(1000);

			// Scroll into Search Contact_Go Button.
			CO.scroll("Search_Contact_Go", "WebButton");

			// Clicking on Search Contact_Go Button.
			//Method.Highlight("Search_Contact_Go", "WebButton");
			//Browser.WebButton.click("Search_Contact_Go");
			//Result.fUpdateLog("Clicking on Search Contact Go Button.");
			Thread.sleep(4000);
			
			// fetching Existing Identification Number from Applet and Verifying with existing Identification Number.
			String INNo = Browser.WebTable.getCellData("BT_Identification_Number", 2, 3);
			if (INNo == randonNumber) {
				Result.fUpdateLog("Contact Created Successfully" + INNo);
				Test_OutPut = INNo;
			}

			CO.ToWait();
			if (Continue.get()) {
				Test_OutPut = Test_OutPut + " Contact Created Successfully And Identification Number Is: "+ Dynamic_Identification_Number;
				Result.takescreenshot("Contact Created Successfully: "+ Dynamic_Identification_Number);
				Status = "PASS";
			} else {
				Result.takescreenshot("Create_A/c button not exist");
				Test_OutPut += "Create_A/c button not exist" + ",";
				Result.fUpdateLog("Create_A/c button not exist");
				Status = "FAIL";
			}

		} catch (Exception e) {
			Continue.set(false);
			Result.takescreenshot("Exception occurred");
			Test_OutPut += "Exception occurred" + ",";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();

		}
		Result.fUpdateLog("------Contact Creation Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: BatelcoAccCreation
	 * Arguments			: None
	 * Use 					: Creates Account adding Contact created Earlier.
	 * Designed By			: Anusha
	 * Last Modified Date 	: 16-sep-2019
	--------------------------------------------------------------------------------------------------------*/
	public String BatelcoAccCreation() {

		
		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------Account Creation Event Details------");
		try {
			Thread.sleep(5000);

			// Fetching Identification Number.
			String Dynamic_Identification_Number = Utlities.FetchStoredValue("ConsumerNewActivation",
					"NewActivation", "Contact_Identification_Number");
			System.out.println("Dynic Identification Number : " + Dynamic_Identification_Number);
			Thread.sleep(5000);

			// Clicking on Account Weblink
			Browser.WebLink.waittillvisible("Address");
			CO.JavaScriptClick("Address", "WebLink");
			Thread.sleep(2000);

			// Clicking on Account List tab
			Browser.WebLink.waittillvisible("Address_list");
			CO.JavaScriptClick("Address_list", "WebLink");

			// clicking on plus for New Account Creation
			Browser.WebButton.waittillvisible("New_Address");
			Browser.WebButton.click("New_Address");
			Result.fUpdateLog("New Account Creation");
			Result.takescreenshot("New Account Creation");

			// Selecting Identification Type.
			Actions a = new Actions(cDriver.get());
			Browser.WebButton.click("AccountIdentification_Type");
			
			if (!(getdata("Account_Identification_Type").equals(""))) {
				Browser.WebEdit.Set("AccountIdentification_Type", getdata("Account_Identification_Type"));
				Result.fUpdateLog("Account_Identification_Type : " + getdata("Account_Identification_Type"));
			} 
			else if (!(pulldata("Account_Identification_Type").equals(""))) {
				Browser.WebEdit.Set("AccountIdentification_Type", pulldata("Account_Identification_Type"));
				Result.fUpdateLog("Selecting Identification Type.");
			}
			Thread.sleep(2000);

			// Generating Random Account Number
			String Bat_Identification_Number = CO.Random_IDGeneration(ID_Type);
			System.out.println(Bat_Identification_Number);

			// Entering Identification Number.
			Browser.WebTable.SetDataE("account_number", 2, 4, "BT_Identification_Number",
					Dynamic_Identification_Number);
			Result.fUpdateLog("Entering Identification Number: " + Dynamic_Identification_Number);

			// CO.isAlertExist();
			// CO.waitforload();

			// Entering Name
			if (!(getdata("Acc_name").equals(""))) {
				Browser.WebTable.SetDataE("account_number", 2, 5, "Name",  CO.CallRandomName() + getdata("Acc_name"));
				Result.fUpdateLog(" Entering Name: " + getdata("Acc_name"));


			} else if (!(pulldata("Acc_name").equals(""))) {
				Browser.WebTable.click("account_number", 2, 5);
				Thread.sleep(1000);
				Browser.WebTable.SetDataE("account_number", 2, 5, "Name",  CO.CallRandomName() + pulldata("Acc_name"));
				Result.fUpdateLog(" Entering Name: " + pulldata("Acc_name"));

			}
			// CO.isAlertExist();
			// CO.waitforload();

			// Selecting Account Type.
			if (!(getdata("Account_Type").equals(""))) {
				Browser.WebTable.SetDataE("account_number", 2, 7, "Type", getdata("Account_Type"));
				Result.fUpdateLog("Selecting Account Type: " + getdata("Account_Type"));
				Result.takescreenshot("Entered Account Details");
				
			} else if (!(pulldata("Account_Type").equals(""))) {
				Browser.WebTable.click("account_number", 2, 7);
				Browser.WebTable.SetDataE("account_number", 2, 7, "Type", pulldata("Account_Type"));
				Result.fUpdateLog("Selecting Account Type: " + pulldata("Account_Type"));
				Result.takescreenshot("Entered Account Details");
			}

			Browser.WebButton.click("last_name");

			// clicking on LastName Button.
			Browser.WebLink.click("Contactlastname");

			// Linking Contact To Address.
			Browser.WebButton.click("New_Query");

			// clicking on Search Button.
			Browser.WebButton.click("AddcontactQuery");

			// Entering Contact Number.
			Browser.WebTable.SetData("Idtype_seelction", 2, 7, "BT_Identification_Number",
					Dynamic_Identification_Number);
			System.out.println(Dynamic_Identification_Number);

			Result.fUpdateLog("Entering Identification Number in Account to Link a Contact:" + Dynamic_Identification_Number);
			Result.takescreenshot("Entering Identification Number in Account to Link a Contact:" + Dynamic_Identification_Number);

			// Clicking on GO Button.
			Method.Highlight("Mvg_Addcontact_go", "WebButton");
			Browser.WebButton.click("Mvg_Addcontact_go");
			Thread.sleep(1000);

			// Clicking on OK Button.
			Browser.WebButton.waitTillEnabled("Contactmg_add");
			Browser.WebButton.click("Contactmg_add");
			Result.fUpdateLog("Adding Contact to Account");
			Thread.sleep(3000);
			Result.takescreenshot("Adding Contact to Account");
			//Browser.WebButton.click("Batcontactok");
			CO.JavaScriptClick("Batcontactok", "WebButton");

			CO.waitforload();
			Thread.sleep(2000);
			// clicking on Setting Icon Button.
			Browser.WebButton.waittillvisible("Account_Menu");
			Browser.WebButton.click("Account_Menu");
			Thread.sleep(1000);

			// Save Account
			Method.Highlight("SaveRecord", "WebLink");
			Thread.sleep(3000);
			Browser.WebLink.waittillvisible("SaveRecord");
			Browser.WebLink.click("SaveRecord");

			CO.isAlertExist();
			Thread.sleep(1000);

			// Scroll into Acccount Number Link
			CO.scroll("account_number", "WebTable");

			System.out.println(Browser.WebTable.getCellData("account_number", 2, 2));
   
			// Fetching Account Number
			Utlities.StoreValue("Account_No", Browser.WebTable.getCellData("account_number", 2, 2));
			AccountNumber = Utlities.FetchStoredValue("ConsumerNewActivation", "NewActivation",
					"Account_No");  
 
			System.out.println("************************ Account Number :" + AccountNumber);
			CO.ToWait();
			if (Continue.get()) {
				Test_OutPut += "Account Created Successfully: " + AccountNumber;
				Result.fUpdateLog("Account Created Successfully: " + AccountNumber);
				Result.takescreenshot("Account Created Successfully: " + AccountNumber);
				Status = "PASS";
			} else {
				Test_OutPut += "Account Creation is Failed" + ",";
				Result.takescreenshot("Account Creation is Failed");
				Status = "FAIL";
			}
		}

		catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();

		}
		Result.fUpdateLog("------Account Creation Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public void Account_search() {
		String Dynamic_Identification_Number = "";
		try {
			CO.waitforload();
			Thread.sleep(8000);
			
			// Click on Account Tab.
			CO.JavaScriptClick("BT_Account_Button", "WebButton");
			Result.takescreenshot("Clicked On Accounts Tab");
			Result.fUpdateLog("Clicked On Accounts Tab");

			// Fetching Dynamic Identification Number
			Dynamic_Identification_Number = Utlities.FetchStoredValue("ConsumerNewActivation", "NewActivation",
					"Contact_Identification_Number");
            Thread.sleep(3000);
            
			if (!(Dynamic_Identification_Number.equals(""))) {
				// Entering Dynamic Identification Number.
				Browser.WebEdit.Set("Identification_Number", Dynamic_Identification_Number);
				Result.takescreenshot(
						"Entering Dynamic Identification Number to Get the Account: " +Dynamic_Identification_Number);
				Result.fUpdateLog(
						"Entering Dynamic Identification Number to Get the Account: " + Dynamic_Identification_Number);
			} else {

				// Entering Identification number in the search field
				Browser.WebEdit.Set("Identification_Number", getdata("Identification_Number"));
				Result.takescreenshot(
						"Entering Identification Number to Get the Account: " + getdata("Identification_Number"));
				Result.fUpdateLog(
						"Entering Identification Number to Get the Account: " + getdata("Identification_Number"));
			}

			// Clicking on Search_Go
			Method.Highlight("Search_Go", "WebButton");
			
			// Clicking on Search_Go
			Browser.WebButton.click("Search_Go");
			Result.takescreenshot("Clicked On Search Go");
			Result.fUpdateLog("Clicked On Search Go");

			// Entering into the Account
			Browser.WebTable.clickL("Account_search_table", 2, 2);
			Result.takescreenshot("Entering into the Account");
			Result.fUpdateLog("Entering into the Account");

			Thread.sleep(5000);

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: BatelcoAddressCreation
	 * Arguments			: None
	 * Use 					: Creates Account for the Contact created Earlier in Vanilla Journey
	 * Designed By			: Anusha
	 * Last Modified Date 	: 16-sep-2019
	--------------------------------------------------------------------------------------------------------*/
	public String BatelcoAddressCreation() {

		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------Address Creation Event Details------");
		try {
			
			
			if (!(getdata("ServiceType").equals("Mobile_Prepaid"))) {
				// Clicking on Account Number Link.
			    //Browser.WebButton.click("Bat_address_Go_button");
				
				Thread.sleep(3000);
				String Dynamic_Identification_Number = Utlities.FetchStoredValue("ConsumerNewActivation",
						"NewActivation", "Contact_Identification_Number");
				System.out.println("*****************************************" + Dynamic_Identification_Number);
				// clicking on Address List box
				Browser.WebLink.waittillvisible("Address");
				Thread.sleep(1000);
				Browser.WebLink.click("Address");
				CO.isAlertExist();
				// clicking on Address List Link.
				Browser.WebLink.waittillvisible("Address_list");
				Browser.WebLink.click("Address_list");
				Thread.sleep(1000);
				// clicking on Account Search Button.
				//Browser.WebButton.click("address_search");
				CO.JavaScriptClick("address_search", "WebButton");
				Thread.sleep(5000);
				// Entering Identification Number To Get The Account:
				Browser.WebTable.click("account_number", 2, 4);
				Browser.WebTable.SetData("account_number", 2, 4, "BT_Identification_Number",
						Dynamic_Identification_Number);
				Thread.sleep(3000);
				Result.fUpdateLog("Entering Identification Number to Get the Account:" + AccountNumber);
				Result.takescreenshot("Entering Identification Number to Get the Account: " + AccountNumber);
				Thread.sleep(6000);
				Browser.WebTable.clickL("account_number", 2, 2);
				Thread.sleep(3000);
				// CO.scroll("address_listbox", "ListBox");
				// Clicking on Menu Tab.
				Browser.WebButton.click("address_listbox");
				// Selecting Address Option.
				Browser.WebButton.click("address_button");
				Thread.sleep(3000);
				// clicking on New Address Button
				Browser.WebButton.click("address_new");
				Browser.WebButton.click("Bat_add_address_button");
				Result.fUpdateLog("Clicking on New Address button");
				Result.takescreenshot("Clicking on New Address button");
				// Entering GIS Status Value as 'Valid'
				Browser.WebTable.SetData("Bat_add_address_table", 2, 13, "BT_GIS_Status", "Valid");
				// clicking on GO Button.
				Method.Highlight("Bat_add_address_Go", "WebButton");
				Browser.WebButton.click("Bat_add_address_Go");
				CO.waitforload();
				// clicking on OK Button.
				Method.Highlight("Bat_add_address_ok", "WebButton");
				Result.takescreenshot(" Selecting Address ");
				Browser.WebButton.click("Bat_add_address_ok");
				Thread.sleep(1000);
				Result.fUpdateLog("Clicking on Ok button");
				Browser.WebTable.click("Bat_address_table", 2, 3);
				Browser.WebTable.click_che("Bat_address_table", 2, 3);
				Result.fUpdateLog("Legal Flag Checked");
				Thread.sleep(2000);
				// Clicking on Setting icon Button
				Browser.WebButton.waittillvisible("AddressMenu_Btn");
				Method.Highlight("AddressMenu_Btn", "WebButton");
				Browser.WebButton.click("AddressMenu_Btn");
				Thread.sleep(1000);
				// Saving Address.
				Method.Highlight("SaveRecord", "WebLink");
				Browser.WebLink.waittillvisible("SaveRecord");
				Browser.WebLink.click("SaveRecord");
			}
			Thread.sleep(1000);

			CO.ToWait();
			if (Continue.get()) {
				Status = "PASS";
				Test_OutPut += "Address Created And Linked Successfully";
				Result.fUpdateLog("Address Created and Linked Successfully");
				Result.takescreenshot("Address Created and Linked Successfully");

			} else {
				Test_OutPut += "Address Creation is Failed" + ",";
				Result.takescreenshot("Address Creation is Failed");
				Status = "FAIL";
			}
		}

		catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();

		}
		Result.fUpdateLog("------Address Creation Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: BatelcoServiceTypeCreation
	 * Arguments			: None
	 * Use 					: Creates ServiceType for the Account created Earlier
	 * Designed By			: Soniya V
	 * Last Modified Date 	: 19-Sep-2019
	 * 
	--------------------------------------------------------------------------------------------------------*/
	public String BatelcoServiceTypeCreation() {
		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------ServiceType Creation Event Details------");
		try {
			System.out.println("GetData Value :" + getdata("Service_Id"));
			System.out.println("GetData Value :" + getdata("SimNo"));
			System.out.println("GetData Value :" + getdata("ProductName"));

			Thread.sleep(10000);
			// Fetching Identification number
			String Dynamic_Identification_Number = Utlities.FetchStoredValue("ConsumerNewActivation",
					"NewActivation", "Contact_Identification_Number");

			// Clicking on Accounts button
			Browser.WebButton.click("BT_Account_Button");
			Thread.sleep(2000);

			// Clicking on Account_list tab
			Browser.WebButton.waittillvisible("BT_AccountsList_Tab");
			Browser.WebButton.click("BT_AccountsList_Tab");

			// clicking on Account search button
			// Browser.WebButton.click("ServiceType_AccountSearch");
			CO.JavaScriptClick("ServiceType_AccountSearch", "WebButton");
			Thread.sleep(1000);

			//Searching Account Using Identification Number for Service Type Selection
			 Browser.WebTable.SetData("Account_search_table", 2, 4,"BT_Identification_Number", Dynamic_Identification_Number);
			 Result.fUpdateLog("Searching Account Using Identification Number for Service Type Selection: "+Dynamic_Identification_Number);
			 Result.takescreenshot("Searching Account Using Identification Number for Service Type Selection: "+Dynamic_Identification_Number);

			Thread.sleep(2000);

			// Entering static Identification Number
//			Browser.WebTable.SetData("Account_search_table", 2, 4, "BT_Identification_Number", "AZ190190");
//			Thread.sleep(2000);

			// Scrolling Up
			CO.scroll("ServiceType_AccountGo", "WebButton");

			// Clicking on Search Contact_Go Button.
			Method.Highlight("ServiceType_AccountGo", "WebButton");
			Browser.WebButton.click("ServiceType_AccountGo");

			// Clicking on the Account Number link and Navigating to Account Summary
			Browser.WebTable.clickL("Account_search_table", 2, 2);

			Thread.sleep(4000);

			// Entering Identification Type Expiry Date
			if (!(getdata("Acc_IdentifiExpDate").equals(""))) {
				Browser.WebEdit.Set("ServiceType_IdentificationTypeExpiryDate", getdata("Acc_IdentifiExpDate"));
				Result.fUpdateLog("Entering Identification Expiry Date: " + getdata("Acc_IdentifiExpDate"));
			} else {
				Browser.WebEdit.Set("ServiceType_IdentificationTypeExpiryDate", pulldata("Acc_IdentifiExpDate"));
				Result.fUpdateLog("Entering Identification Expiry Date: " + pulldata("Acc_IdentifiExpDate"));
	
			}

			// Bypassing CIO Verification
			if (!(getdata("ServiceType_Bypassing").equals(""))) {
				Browser.WebEdit.Set("ServiceType_Bypassing", getdata("ServiceType_Bypass"));
				Result.fUpdateLog("Bypassing CIO Verification " + getdata("ServiceType_Bypass"));
				Result.takescreenshot("Bypassing CIO Verification: " + getdata("ServiceType_Bypass"));
			} else {
				Browser.WebEdit.Set("ServiceType_Bypassing", pulldata("ServiceType_Bypass"));
				Result.fUpdateLog("Bypassing CIO Verification " + pulldata("ServiceType_Bypass"));
				Result.takescreenshot("Bypassing CIO Verification: " + pulldata("ServiceType_Bypass"));
			}

			Thread.sleep(1000);

			// Clicking on order summary tab
			Browser.WebButton.click("OrderSummaryTab");

			Thread.sleep(5000);

			// Clicking on the new button
			Browser.WebButton.click("New_Service_Creation");

			// Entering "Postpaid Service"
			if (!(getdata("ServiceType").equals(""))) {
				Browser.WebEdit.Set("ServiceType", getdata("ServiceType"));
				Result.fUpdateLog("Entering Service" + getdata("ServiceType"));
			} else {
				Browser.WebEdit.Set("ServiceType", pulldata("ServiceType"));
				Result.fUpdateLog("Entering Service" + pulldata("ServiceType"));
		
			}

			// Entering "New Activation"
			if (!(getdata("ServiceType_Reason").equals(""))) {
				Browser.WebEdit.Set("ServiceType_Reason", getdata("ServiceType_Reason"));
				Result.fUpdateLog("Entering Order Reason " + getdata("ServiceType_Reason"));
				Result.takescreenshot("Entering Order Reason " + getdata("ServiceType_Reason"));
			} else {
				Browser.WebEdit.Set("ServiceType_Reason", pulldata("ServiceType_Reason"));
				Result.fUpdateLog("Entering Order Reason " + pulldata("ServiceType_Reason"));
				Result.takescreenshot("Entering Order Reason " + pulldata("ServiceType_Reason"));
			}
			Thread.sleep(2000);

			// Clicking on Ok button
			CO.JavaScriptClick("ServiceType_Ok", "WebButton");
			CO.isAlertExist();

			CO.ToWait();
			if (Continue.get()) {
				Test_OutPut += "ServiceType Created Successfully";
				Result.fUpdateLog("Service Type Created Successfully");
				Result.takescreenshot("Service Type Created Successfully");

				Status = "PASS";
			} else {
				Test_OutPut += "ServiceType Creation is Failed" + ",";
				Result.takescreenshot("ServiceType Creation is Failed");
				Status = "FAIL";
			}

		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();

		}
		Result.fUpdateLog("------ServiceType Creation Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";

	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: BatelcoOrderCreation
	 * Arguments			: None
	 * Use 					: Creates Order for the Account created Earlier
	 * Designed By			: Soniya V
	 * Last Modified Date 	: 19-Sep-2019
	--------------------------------------------------------------------------------------------------------*/
	public String BatelcoOrderCreation() {
		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------Order Creation Event Details------");
		try {
			Thread.sleep(3000);

			if (!(getdata("ServiceType").equals("Mobile_Prepaid"))) {
			
				// Clicking on Billing Profile New Button(+)
				CO.JavaScriptClick("BillingProfile_Search", "WebButton");
				Thread.sleep(5000);
				// Entering Contact Number
				Browser.WebTable.click("Billingprofile_table", 2, 9);
				if (!(getdata("BillingProfile_ContactNumber").equals(""))) {
					Browser.WebTable.SetDataE("Billingprofile_table", 2, 9, "BT_Primary_Contact_Phone_Number",
							getdata("BillingProfile_ContactNumber"));
				} else {

					Browser.WebTable.SetDataE("Billingprofile_table", 2, 9, "BT_Primary_Contact_Phone_Number",
							pulldata("BillingProfile_ContactNumber"));
				}
				// Entering Email Id
				if (!(getdata("BillingProfile_eMail").equals(""))) {
					Browser.WebTable.SetDataE("Billingprofile_table", 2, 10, "BT_eMail",
							getdata("BillingProfile_eMail"));
				} else {
					Browser.WebTable.SetDataE("Billingprofile_table", 2, 10, "BT_eMail",
							pulldata("BillingProfile_eMail"));
				}
				Thread.sleep(3000);
				Browser.WebTable.waittillvisible("Billingprofile_table");
				// Entering Additional Payments
				if (!(getdata("BillingProfile_PaymentMethod").equals(""))) {
					Browser.WebTable.SetDataE("Billingprofile_table", 2, 26, "BT_Additional_Payment_Method",
							getdata("BillingProfile_PaymentMethod"));
				} else {

					Browser.WebTable.SetDataE("Billingprofile_table", 2, 26, "BT_Additional_Payment_Method",
							pulldata("BillingProfile_PaymentMethod"));
				}
				Thread.sleep(2000);
				Method.Highlight("Associate_button", "WebButton");
				Result.fUpdateLog("Associate Billing Profile");
				Result.takescreenshot("Associate Billing Profile");
				// Clicking on Associate Button
				Browser.WebButton.click("Associate_button");
				Thread.sleep(1000);
				// Clicking on Billing Profile Go Button
				Browser.WebButton.click("OrderAssociateGoBtn");
				Thread.sleep(8000);
				CO.isAlertExist();
				// Clicking on Associate Button
				// Browser.WebButton.click("Associate_button");
				CO.JavaScriptClick("Associate_button", "WebButton");
				Thread.sleep(3000);
				CO.isAlertExist();
				Thread.sleep(1000);
				// Clicking On Billing Profile Menu ICon Button.
				Browser.WebButton.click("BillingProfile_MenuIcon");
				// Click on Save Billing Profile button.
				Browser.WebLink.click("BillProfile_Savebtn");
				Thread.sleep(3000);
				// Fetching created Billing Number.
				CO.scroll("BillingProfile_No", "WebEdit");
				String BiilingProfileNum = cDriver.get()
						.findElement(By.xpath("//table[@id='s_2_l']/tbody/tr[2]/td[3]/a")).getText();
				Result.fUpdateLog("Newly Created Billing Profile Number: " + BiilingProfileNum);
				Result.takescreenshot("Newly Created Billing Profile Number: " + BiilingProfileNum);
				// Clicking on Line Items Tab
				Method.Highlight("Line_Items", "WebButton");
				// Browser.WebButton.click("Line_Items");
				CO.JavaScriptClick("Line_Items", "WebButton");
			}
			Thread.sleep(3000);

			// Clicking on Line Items Add button
			Method.Highlight("Add_LineItem:New", "WebButton");
			CO.JavaScriptClick("Add_LineItem:New", "WebButton");

			Result.fUpdateLog("Clicking on Line Items Add button");
			Result.takescreenshot("Clicking on Line Items Add button");

			// Clicking on ProductName textbox.
			Browser.WebTable.click("Productselection_table", 2, 3);
			Thread.sleep(4000);

			// Clicking on Serach ICon.
			Browser.WebTable.click_S("Productselection_table", 2, 3);
		
//			cDriver.get()
//					.findElement(By.xpath(
//							"//table[contains(@summary,'List (Sales)')]//input[@name='Product']/parent::td/span"))
//					.click();

			// Entering the Product Name
			Browser.WebEdit.Set("ProductName", getdata("ProductName"));
			Result.fUpdateLog("Product Name " + getdata("ProductName"));
			Result.takescreenshot("Product Name " + getdata("ProductName"));
			Thread.sleep(3000);

			// Clicking on Go button.
			Browser.WebButton.click("Product_Gobutton");
			Thread.sleep(15000);
			
//			try {
//			Thread.sleep(3000);
//			Browser.WebButton.click("Order_Product:OK");
//			}
//			catch(Exception ee) {
//				System.out.println(ee);
//			}
//			
//			Thread.sleep(5000);

			
			
			
			if (getdata("ServiceType").equals("Mobile_Prepaid")) {
				// clicking Tree icon to enable query number
				Method.Highlight("OrderExpandTreeIcon_Prepaid", "WebButton");
				Browser.WebButton.click("OrderExpandTreeIcon_Prepaid");
				CO.scroll("querynumber_lineitems", "WebButton");
				Result.takescreenshot("Installed Assets Details");
			}else {
				// clicking Tree icon to enable query number
				Method.Highlight("OrderExpandTreeIcon", "WebButton");
				//Browser.WebButton.click("OrderExpandTreeIcon");
				CO.JavaScriptClick("OrderExpandTreeIcon", "WebButton");
				Result.fUpdateLog("clicking Tree icon");
				CO.scroll("querynumber_lineitems", "WebButton");
				Result.takescreenshot("Installed Assets Details");
			}
			
			Thread.sleep(5000);

			// Clicking query number lineitems
			Method.Highlight("querynumber_lineitems", "WebButton");
			Result.fUpdateLog("clicking querynumber_lineitems");
			Result.takescreenshot("clicking on Query Number");
			// Browser.WebButton.click("querynumber_lineitems");
			CO.JavaScriptClick("querynumber_lineitems", "WebButton");

			Thread.sleep(2000);
			// Wait for Query Numbe object.
			CO.waitforobj("querynumber_lineitems", "Querynumber_number");
			CO.waitforload();

			// Entring Query number
			Browser.WebTable.SetDataE("Querynumber", 2, 2, "BT_Service_Id", getdata("Service_Id"));
			MsisdnNumber = getdata("MSISDN");
			Result.fUpdateLog("Search New MSISDN: " + getdata("Service_Id"));
			Result.takescreenshot("Search New MSISDN: " + getdata("Service_Id"));

			Thread.sleep(2000);

			// Moving to Third Tab
			Browser.WebTable.click("Querynumber", 2, 4);

			// Clicking on Query Number Button
			// Method.Highlight("Querynumber_number","WebButton");
			CO.JavaScriptClick("Querynumber_number", "WebButton");

			// Moving to Third Tab
			Browser.WebTable.click("Querynumber", 2, 4);

			// Clicking on Query Number Button
			CO.JavaScriptClick("Querynumber_number", "WebButton");
			// Browser.WebButton.click("Querynumber_number");
			Thread.sleep(10000);

			// Clicking on Select Button
			Browser.WebButton.waittillvisible("Select");
			Result.fUpdateLog(" Clicking on Select Button");
			Result.takescreenshot("Clicking on Select Button");
			Browser.WebButton.click("Select");
			Thread.sleep(6000);

			// clicking Tree icon to enable query number
			//Browser.WebButton.click("expand_button");

			// Clicking on Expand Tree Icon.
			Method.Highlight("OrderExpandTreeIcon", "WebButton");
			Browser.WebButton.click("OrderExpandTreeIcon");
			Thread.sleep(8000);

			
			// Entering SIM Number
			
			if (getdata("ServiceType").equals("Mobile_Prepaid")) {
				Browser.WebTable.click("Productselection_table", 4, 7);
				Browser.WebTable.SetData("Productselection_table", 4, 7, "Service_Id", getdata("SimNo"));
			}else {
			Browser.WebTable.click("Productselection_table", 5, 7);
			Browser.WebTable.SetData("Productselection_table", 5, 7, "Service_Id", getdata("SimNo"));

			}
			
			
			Result.fUpdateLog("Entering SIM Number: " + getdata("SimNo"));
			Result.takescreenshot("Entering SIM Number: " + getdata("SimNo"));

			Thread.sleep(2000);

			// Scrolling up
			CO.scroll("OrderMenu_Btn", "WebButton");
			Browser.WebButton.waittillvisible("OrderMenu_Btn");

			// Clicking On Setting Button
			Browser.WebButton.click("OrderMenu_Btn");
			Thread.sleep(1000);

			// Saving the Order
			Method.Highlight("SaveRecord", "WebLink");
			Browser.WebLink.waittillvisible("SaveRecord");
			Result.takescreenshot("Order Saved");
			Browser.WebLink.click("SaveRecord");
			Result.fUpdateLog("Order Saved");
		

			Thread.sleep(5000);

			// Scrolling to Order Number Text Box
			CO.scroll("order_numfetch", "WebEdit");

			// Fetching order Number.

			ordernum = Browser.WebEdit.gettext("order_numfetch");
			System.out.println("********Fetching Order Number :   " + ordernum);

			// Storing Order Number in StoreDB.
			Utlities.StoreValue("Order_NUM", ordernum);

			CO.ToWait();
			if (Continue.get()) {
				Test_OutPut += "Order Created successfully And OrderId is:" + ordernum;
				Result.fUpdateLog("Order Created Successfully and Order ID is:  " + ordernum);
				Result.takescreenshot("Order Created Successfully and Order ID is:  " + ordernum);
				Status = "PASS";
			} else {
				Test_OutPut += "Order Creation is Failed" + ",";
				Result.fUpdateLog("Order Creation Creation is Failed");
				Result.takescreenshot("Order Creation Creation is Failed");
				Status = "FAIL";
			}

		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();

		}
		Result.fUpdateLog("------Order Creation Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";

	}
	/*---------------------------------------------------------------------------------------------------------
	 * Method Name            : BTOrderPayments
	 * Arguments              : None
	 * Use                    : To Perform Order payments in Siebel
	 * Modified By            : Srikanth.D
	 * Last Modified Date     : 15-01-2019
	--------------------------------------------------------------------------------------------------------*/
	public String BTOrderPayments() {
		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------Order Payments Event Details------");
		try {
			
			Thread.sleep(5000);	
		    // *******   Order Payment  **************
			Payment();
			Thread.sleep(5000);	
			// Clicking on Signature Capture
			CO.scroll("SignatureCapture_Button", "WebButton");
			Browser.WebButton.waittillvisible("SignatureCapture_Button");
			Method.Highlight("SignatureCapture_Button", "WebButton");
			Browser.WebButton.click("SignatureCapture_Button");
			CO.isAlertExist();

			// Perform Signature
			Thread.sleep(1000);

			// Browser.WebEdit.click("Signature_Pad");
			// CO.isAlertExist();
			Thread.sleep(5000);
			WebElement SigPad = cDriver.get()
					.findElement(By.xpath("//div[@class='mceGridField siebui-value mceField']//canvas"));
			SigPad.click();

			Browser.WebEdit.doSignature("Signature_Pad");

			Thread.sleep(6000);
			Browser.WebButton.waittillvisible("signatureSave");
			Result.fUpdateLog("Signature Captured");
			Browser.WebButton.click("signatureSave");
			Thread.sleep(3000);

			// Scroll to signature Capture Check Box
			CO.scroll("Validate", "WebButton");
			Result.takescreenshot("Signature Captured");
			Result.fUpdateLog("Signature Captured");

			// Clicking on Validate.
			Thread.sleep(2000);
			CO.scroll("Validate", "WebButton");
			Method.Highlight("Validate", "WebButton");
			Browser.WebButton.click("Validate");
			Thread.sleep(10000);

			// Clicking on Draft eFrom.
			Method.Highlight("DrafteForm_Btn", "WebButton");
			Browser.WebButton.click("DrafteForm_Btn");
			Result.takescreenshot("Clicked On DrafteForm");
			Result.fUpdateLog("Clicking On DrafteForm");
			Thread.sleep(25000);

			// Fetching Order Number.
			ordernum = Browser.WebEdit.gettext("order_numfetch");
			Result.fUpdateLog("Current Order Number " + ordernum);
			
            
			CO.isAlertExist();
			
			// Clicking on Submit button.
			CO.scroll("SubmitBtn", "WebButton");
			Method.Highlight("SubmitBtn", "WebButton");
			//Browser.WebButton.click("SubmitBtn");
			CO.JavaScriptClick("SubmitBtn", "WebButton");
			CO.waitforload();
			CO.isAlertExist();
			
			CO.JavaScriptClick("SubmitBtn", "WebButton");
			CO.isAlertExist();
			
			
			Result.fUpdateLog(" Clicking on Submit button");
			Result.takescreenshot(" Clicking on Submit button");
			
			// Refresh order.
			String OrderStatus = OrderRefresh();

			// Scroll down to Lineitems
			CO.scroll("Line_Items", "WebButton");
			// Clicking on Line Items Tab
			Method.Highlight("Line_Items", "WebButton");
			Thread.sleep(1000);
			Browser.WebButton.click("Line_Items");
			Thread.sleep(1000);

			// Expand the Order tree icon to capture screenshot of Installed
			// Assets*****************************
			Browser.WebButton.click("OrderExpandTreeIcon");

			Result.takescreenshot("Newly Installed Assets after Order Submission");
			Result.fUpdateLog("Newly Installed Assets after Order Submission");

			// Scroll to Account Summary
			CO.scroll("Acc_Summarybtn", "WebButton");
			Result.takescreenshot("After Refreshing the Browser Order Status: " + OrderStatus);
			Result.fUpdateLog("After Refreshing the Browser Order Status: " + OrderStatus);

			// Clicking on Account Summary button.
			Method.Highlight("Acc_Summarybtn", "WebButton");
			Browser.WebButton.click("Acc_Summarybtn");
			Result.fUpdateLog(" Clicking on Account Summary button");
			Result.takescreenshot(" Clicking on Account Summary button");

			Thread.sleep(3000);

			// Scroll to Account status Indicator
			CO.scroll("Accout_Status_Indicator", "WebEdit");
			Result.fUpdateLog("Account Summary Detail");
			Result.takescreenshot("Account Summary Detail");
			Thread.sleep(1000);

			// Scroll into Modify button.
			CO.scroll("Modify_button", "WebButton");
			Result.fUpdateLog("Installed Asset Detail");
			Result.takescreenshot("Installed Asset Detail");

			if (Continue.get()) {
				Test_OutPut += "Order payments is done Successfully "+OrderStatus;
				Result.fUpdateLog("Order payments is done Successfully "+OrderStatus);
				//Result.takescreenshot("Order Payment Success");
				Status = "PASS";
			} else {
				Test_OutPut += "Order  payment Failed" + ",";
				Result.takescreenshot("Order level payment Failed");
				Result.fUpdateLog("Order level payment Failed");
				Status = "FAIL";
			}
		} catch (Exception e) {
			Continue.set(false);
			Status = "FAIL";
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
		Result.fUpdateLog("------Order payments Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";

	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: BatelcoSuspend
	 * Arguments			: None
	 * Use 					: Creates Suspend order for the Account created Earlier
	 * Designed By			: Soniya V
	 * Last Modified Date 	: 10-Oct-2019
	--------------------------------------------------------------------------------------------------------*/
	public String BatelcoSuspend() {
		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------Suspend Event Details------");
		try {

			Thread.sleep(5000);
			
            Account_search();

			//Fetching the Account Number and Name
			CO.scroll("Account_Number", "WebEdit");
			Thread.sleep(5000);
			String AccountNo=Browser.WebEdit.gettext("Account_Number");
			
			Account_Number =AccountNo;
			String Account_Holder_Name=Browser.WebEdit.gettext("AccountHolder_Name");	
	
	    	Result.takescreenshot("Account Number: "+AccountNo +" and Name: " +Account_Holder_Name);
		    Result.fUpdateLog("Account Number: "+AccountNo +" and Name: " +Account_Holder_Name);

			//Click on suspend
			// span[contains(text(),'Suspend')]
			Browser.WebButton.click("BTSuspend");
			Result.takescreenshot("Clicked On Suspend Tab");
			Result.fUpdateLog("Clicked on Suspend Tab");
			
			
			 //clicking Tree icon to enable query number
	 		Browser.WebButton.click("OrderExpandTreeIcon");
	
	 		//fetching Action Code	
	 		String ActionCode="",prodcut="";
	 		int totalRows=Browser.WebTable.getRowCount("Productselection_table");
			
			Result.fUpdateLog("Number of Rows in the Table :"+totalRows);
			
			for(int i=2;i<=totalRows;i++) {

			 ActionCode = Browser.WebTable.getCellData("Productselection_table", i, 2);
			 prodcut = Browser.WebTable.getCellData("Productselection_table", i, 6);
		    
		    Result.fUpdateLog("Action Code: "+ActionCode+" Product Name: " + prodcut);
		   Thread.sleep(3000);
		   
			if(ActionCode.equals("Complete")) {
			    break;
			}
		   
		   }

			//Scroll down to Lineitems
			CO.scroll("Line_Items", "WebButton");
			Thread.sleep(1000);
			Result.takescreenshot("Action Code");
			
			
			
			// Customer Initiated
			// give reason for suspension
			
			Browser.WebEdit.Set("BTSuspensionReason", pulldata("SuspensionReason"));
			Result.takescreenshot("Entered Suspend Reason");
			Result.fUpdateLog("Entered Suspend Reason");
			CO.isAlertExist();

			//Clicking on Validate
			Browser.WebButton.click("Validate");
			Result.takescreenshot("Clicked On Validate");
			Result.fUpdateLog("Clicked On Validate");

			
			CO.waitforload();
			//Clicking on Signature Capture
			CO.scroll("SignatureCapture_Btn", "WebButton");
			Browser.WebButton.waittillvisible("SignatureCapture_Btn");
			Browser.WebButton.click("SignatureCapture_Btn");
			CO.isAlertExist();

			//Perform Signature
			Thread.sleep(1000);

			// Browser.WebEdit.click("Signature_Pad");
			// CO.isAlertExist();
			Thread.sleep(5000);
			WebElement SigPad = cDriver.get()
					.findElement(By.xpath("//div[@class='mceGridField siebui-value mceField']//canvas"));
			SigPad.click();

			Browser.WebEdit.doSignature("Signature_Pad");
	
			Thread.sleep(3000);
			Browser.WebButton.waittillvisible("signatureSave");
			Browser.WebButton.click("signatureSave");
			CO.isAlertExist();
			
			//Scroll  to signature Capture Checkbox
			CO.scroll("DrafteForm_Btn", "WebButton");
			Result.takescreenshot("Signature Captured");
			Result.fUpdateLog("Signature Captured");
			CO.isAlertExist();
			
			//Clicking on Draft eFrom.
			Method.Highlight("DrafteForm_Btn", "WebButton");
			Browser.WebButton.click("DrafteForm_Btn");
			Result.takescreenshot("Clicked On DrafteForm");
			Result.fUpdateLog("Clicked On DrafteForm");
			
			
			// Fetching Order Number 
			ordernum = Browser.WebEdit.gettext("order_numfetch");
			System.out.println("Current Order Number "+ordernum);
			
			//Clicking on Submit button.
			CO.scroll("SubmitBtn", "WebButton");
			Method.Highlight("SubmitBtn", "WebButton");

			Browser.WebButton.click("SubmitBtn");
			Result.takescreenshot("Clicked On Submit");
			Result.fUpdateLog("Clicked On Submit");
			
			// Refresh Order Status
		    String OrderStatus = OrderRefresh(); 
		    
			// Scroll down to Lineitems
			CO.scroll("Line_Items", "WebButton");

			// Clicking on Line Items Tab
			Method.Highlight("Line_Items", "WebButton");
			Thread.sleep(1000);
			Browser.WebButton.click("Line_Items");
			Thread.sleep(5000);
			Result.takescreenshot("Newly Installed Assets after Order Submission");
			Result.fUpdateLog("Newly Installed Assets after Order Submission");
			Thread.sleep(2000);
	 
			// Scroll to Account Summary
			CO.scroll("Acc_Summarybtn", "WebButton");
			Result.takescreenshot("After Refreshing the Browser Order Status: "+ OrderStatus );
			Result.fUpdateLog("After Refreshing the Browser Order Status: "+ OrderStatus );
		
			// Clicking on Account Summary button.
			Method.Highlight("Acc_Summarybtn", "WebButton");
			Browser.WebButton.click("Acc_Summarybtn");
			Result.fUpdateLog(" Clicking on Account Summary button");
			Result.takescreenshot(" Clicking on Account Summary button");

			Thread.sleep(3000);

			// Scroll to Account status Indicator
			CO.scroll("Accout_Status_Indicator", "WebEdit");
			
			Result.fUpdateLog("Account Summary Detail");
			Result.takescreenshot("Account Summary Detail");
			Thread.sleep(1000);

			// Scroll into Modify button.
			CO.scroll("Modify_button", "WebButton");
			Result.fUpdateLog("Installed Asset Detail");
			
			CO.ToWait();
			if (Continue.get()) {
				Test_OutPut += "Suspend Event is Passed for Account Number: "+AccountNo +" and Order: "+ordernum +" is submitted Successfully"; 
				Result.fUpdateLog("Suspend Event is Passed for Account Number: "+AccountNo +" and Order: "+ordernum +" is submitted Successfully");
				Result.takescreenshot("Suspend Event is Passed for Account Number: "+AccountNo +" and Order: "+ordernum +" is submitted Successfully");
				Status = "PASS";
			} else {
				Test_OutPut += "Suspend Failed" + ",";
				Result.takescreenshot("Suspend Failed");
				Result.fUpdateLog("Suspend Failed");
				Status = "FAIL";
			}
			
		} catch (Exception e) {
			Continue.set(false);
			Status = "FAIL";
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
		Result.fUpdateLog("------Suspend Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}
	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: BT_Resume
	 * Arguments			: None
	 * Use 					: Resume order for the Contact created Earlier.
	 * Designed By			: Srikanth D
	 * Last Modified Date 	: 14-Oct-2019
	--------------------------------------------------------------------------------------------------------*/
	public String BT_Resume() {
		String Test_OutPut = "", Status = "", Message = "";
		Result.fUpdateLog("------ Resume Order Event Details------");
		try {
			Thread.sleep(5000);

			// Entering Identification number in the search field
			Account_search();
			CO.waitforload();
            Thread.sleep(5000);
			
			// Scroll to Account Number.
			CO.scroll("Account_Number", "WebEdit");

			// Fetching Existing Account Number.
			String AccountNo = Browser.WebEdit.gettext("Account_Number");
			AccountNumber =   AccountNo;
			// Fetching Existing Account Holder Name.
			String Account_Holder_Name = Browser.WebEdit.gettext("AccountHolder_Name");
			Result.fUpdateLog("Account Number: " + AccountNo + " and Name: " + Account_Holder_Name);
			Result.takescreenshot("Account Number: " + AccountNo + " and Name: " + Account_Holder_Name);

			// Clicking on MSISDN Number.
			Browser.WebTable.click("FetchingServiceNo", 2, 32);
			Result.fUpdateLog("Clicking on ServiceNo");

			// Fetching MSISDN Number.
			String fetch_MSISDNNum = Browser.WebTable.getCellData("FetchingServiceNo", 2, 33);
			Thread.sleep(2000);
			Result.fUpdateLog("Current MSISDN Number: " + fetch_MSISDNNum);
			Result.takescreenshot("Current MSISDN Number: " + fetch_MSISDNNum);

			// clicking on Resume button
			CO.scroll("Resume_Button", "WebButton");
			Method.Highlight("Resume_Button", "WebButton");
			//Browser.WebButton.click("Resume_Button");
			CO.JavaScriptClick("Resume_Button", "WebButton");
			Result.fUpdateLog("clicking on Resume button");
			Result.takescreenshot("clicking on Resume button");

			Thread.sleep(8000);

			// clicking Tree icon to enable query number
			Browser.WebButton.click("OrderExpandTreeIcon");
            Thread.sleep(2000);
			
			// fetching Action Code
			String ActionCode="", prodcut="";
	
			int totalRows=Browser.WebTable.getRowCount("Productselection_table");
			
			Result.fUpdateLog("Number of Rows in the Table :"+totalRows);
			
			for(int i=2;i<=totalRows;i++) {
			 ActionCode = Browser.WebTable.getCellData("Productselection_table", i, 2);
			 prodcut = Browser.WebTable.getCellData("Productselection_table", i, 6);
		    Result.fUpdateLog("Action Code: "+ActionCode+" Product Name: " + prodcut);
			Thread.sleep(3000);
			 
		   }
			
		
			//Scroll down to Lineitems
			CO.scroll("Line_Items", "WebButton");
			Thread.sleep(1000);
			
			Result.takescreenshot("Product Name with Action Code ");
			

			// Customer Initiated
			// give reason for Resume
			Browser.WebEdit.Set("BTSuspensionReason", getdata("Resume_Reason"));
			Result.takescreenshot("Entered Resume Reason");
			Result.fUpdateLog("Entered Resume Reason");
			CO.isAlertExist();

			// Clicking on Validate
			// *[@aria-label='Sales Order:1-Validate']
			Browser.WebButton.click("Validate");
			Result.takescreenshot("Clicked On Validate");
			Result.fUpdateLog("Clicked On Validate");
			// *[contains(text(),'8-Submit')]

			CO.waitforload();
			// Clicking on Signature Capture
			CO.scroll("SignatureCapture_Btn", "WebButton");
			Browser.WebButton.waittillvisible("SignatureCapture_Btn");
			Browser.WebButton.click("SignatureCapture_Btn");
			CO.isAlertExist();

			// Perform Signature
			Thread.sleep(1000);

			// Browser.WebEdit.click("Signature_Pad");
			// Browser.WebEdit.click("Signature_Pad");
			// CO.isAlertExist();
			Thread.sleep(5000);
			WebElement SigPad = cDriver.get()
					.findElement(By.xpath("//div[@class='mceGridField siebui-value mceField']//canvas"));
			SigPad.click();

			Browser.WebEdit.doSignature("Signature_Pad");

			Thread.sleep(3000);
			Browser.WebButton.waittillvisible("signatureSave");
			Browser.WebButton.click("signatureSave");
			CO.isAlertExist();
			// Scroll to signature Capture Checkbox
			CO.scroll("DrafteForm_Btn", "WebButton");
			Result.takescreenshot("Signature Captured");
			Result.fUpdateLog("Signature Captured");
			CO.isAlertExist();
			// Clicking on Draft eFrom.
			Method.Highlight("DrafteForm_Btn", "WebButton");
			Browser.WebButton.click("DrafteForm_Btn");
			Result.takescreenshot("Clicked On DrafteForm");
			Result.fUpdateLog("Clicked On DrafteForm");

			// Fetching Order Number
			ordernum = Browser.WebEdit.gettext("order_numfetch");
			System.out.println("Current Order Number " + ordernum);

			// Clicking on Submit button.
			CO.scroll("SubmitBtn", "WebButton");
			Method.Highlight("SubmitBtn", "WebButton");


			Browser.WebButton.click("SubmitBtn");
			Result.takescreenshot("Clicked On Submit");
			Result.fUpdateLog("Clicked On Submit");

			// Refresh Order Status
		    String OrderStatus = OrderRefresh(); 
		    
			// Scroll down to Lineitems
			CO.scroll("Line_Items", "WebButton");

			// Clicking on Line Items Tab
			Method.Highlight("Line_Items", "WebButton");
			Thread.sleep(1000);
			Browser.WebButton.click("Line_Items");
			Thread.sleep(5000);
			Result.takescreenshot("Newly Installed Assets after Order Submission");
			Result.fUpdateLog("Newly Installed Assets after Order Submission");
			Thread.sleep(2000);
	 
			// Scroll to Account Summary
			CO.scroll("Acc_Summarybtn", "WebButton");
			Result.takescreenshot("After Refreshing the Browser Order Status: "+ OrderStatus );
			Result.fUpdateLog("After Refreshing the Browser Order Status: "+ OrderStatus );
		
			// Clicking on Account Summary button.
			Method.Highlight("Acc_Summarybtn", "WebButton");
			Browser.WebButton.click("Acc_Summarybtn");
			Result.fUpdateLog(" Clicking on Account Summary button");
			Result.takescreenshot(" Clicking on Account Summary button");

			Thread.sleep(3000);

			// Scroll to Account status Indicator
			CO.scroll("Accout_Status_Indicator", "WebEdit");
			
			Result.fUpdateLog("Account Summary Detail");
			Result.takescreenshot("Account Summary Detail");
			Thread.sleep(1000);

			// Scroll into Modify button.
			CO.scroll("Modify_button", "WebButton");
			Result.fUpdateLog("Installed Asset Detail");

			CO.ToWait();
			if (Continue.get()) {
				Test_OutPut += "Account Number " + AccountNo + " , Resume Event Passed" + ", " + ordernum;
				Result.takescreenshot("Account Number " + AccountNo + " , Resume Order is Passed");
				Status = "PASS";
				// Result.takescreenshot("Account Created Account_No : " + Account_No);
			} else {
				Test_OutPut += "Resume Order  is Failed" + ",";
				Result.takescreenshot("Resume Order  is Failed");
				Status = "FAIL";
			}

		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();

		}
		Result.fUpdateLog("------Resume Order  Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";

	}
	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: BT_Disconnection
	 * Arguments			: None
	 * Use 					: Disconnect order for the Contact created Earlier.
	 * Designed By			: Srikanth D
	 * Last Modified Date 	: 12-Decc-2019
	--------------------------------------------------------------------------------------------------------*/
	public String BT_Disconnection() {
		String Test_OutPut = "", Status = "", Message = "", IdentificationNumber="";
		Result.fUpdateLog("------ Disconnect Order Event Details------");
		try {
			Thread.sleep(5000);
				
		    Account_search();
		    
		    

			CO.waitforload();
			
			// Scroll to Account Number.
			CO.scroll("Account_Number", "WebEdit");
			//Fetching Existing Account Number.
			String AccountNo = Browser.WebEdit.gettext("Account_Number");
		
			//Fetching Existing Account Holder Name.
			String Account_Holder_Name = Browser.WebEdit.gettext("AccountHolder_Name");
			Result.fUpdateLog("Account Number: " + AccountNo + " and Name: " + Account_Holder_Name);
			Result.takescreenshot("Account Number: " + AccountNo + " and Name: " + Account_Holder_Name);
		
			//Clicking on MSISDN Number.
			Browser.WebTable.click("FetchingServiceNo", 2, 32);
			Result.fUpdateLog("Clicking on ServiceNo");
	
			//Fetching MSISDN Number.
			String fetch_MSISDNNum = Browser.WebTable.getCellData("FetchingServiceNo", 2, 33);
		
			
			// Storing MSISDN Number in StoreDatabase.
			Utlities.StoreValue("MSISDN_Number", fetch_MSISDNNum);
			
			// Fetching storing Msisdn Number.
			String MsisdnNum = Utlities.FetchStoredValue("Batelco_Order_Suspension",
					"BT_OrderSuspension", "MSISDN_Number");

			System.out.println("MSISDN Number : " + MsisdnNum);

			Thread.sleep(2000);
			Result.fUpdateLog("Current MSISDN Number: " +fetch_MSISDNNum);
			Result.takescreenshot("Current MSISDN Number: " + fetch_MSISDNNum);	
			
			// clicking on Disconnect button
			// button[@aria-label='Promotion:Disconnect']/span
			CO.scroll("Disconnect_btn", "WebButton");
			Method.Highlight("Disconnect_btn", "WebButton");
			Browser.WebButton.click("Disconnect_btn");

			Result.fUpdateLog("clicking on Disconnect button");
			Result.takescreenshot("clicking on Disconnect button");

			// Selecting Order Reason: Customer Initiated Disconnect
			// Selecting order reason
			String  Order_Reason= getdata("orderreason");
			Browser.WebEdit.Set("orderreason_modify", getdata("orderreason"));
			System.out.println(getdata("orderreason"));
			
			Result.takescreenshot("Selecting Order Reason: Customer Initiated Disconnect ");
			Result.fUpdateLog("Selecting Order Reason: Customer Initiated Disconnect ");

			// click on OK button
			CO.JavaScriptClick("ServiceType_Ok", "WebButton");

			Thread.sleep(8000);
			
			//********** Action Code  **********
			String ActionCode="", prodcut="";
	
			int totalRows=Browser.WebTable.getRowCount("Productselection_table");
			
			Result.fUpdateLog("Number of Rows in the Table :"+totalRows);
			
			for(int i=2;i<=totalRows;i++) {

			 ActionCode = Browser.WebTable.getCellData("Productselection_table", i, 2);
			 prodcut = Browser.WebTable.getCellData("Productselection_table", i, 6);
		    
		    Result.fUpdateLog("Action Code: "+ActionCode+" Product Name: " + prodcut);
		   Thread.sleep(3000);
	        
		   }
			
			Result.takescreenshot("Product Name with Action Code ");
			 
				
			// Scrolling up
			Browser.WebButton.waittillvisible("OrderMenu_Btn");
			CO.scroll("OrderMenu_Btn", "WebButton");
			
			// Clicking On Setting Button
			Browser.WebButton.click("OrderMenu_Btn");
			Result.fUpdateLog("Clicking On Setting Button.");
			Thread.sleep(1000);

			//Saving the Order 
			Method.Highlight("SaveRecord", "WebLink");
			Browser.WebLink.waittillvisible("SaveRecord");
			Browser.WebLink.click("SaveRecord");
			Thread.sleep(4000);		
			

			//Expand the Order tree icon to capture screenshot of Installed Assets*****************************
			Browser.WebButton.click("OrderExpandTreeIcon");
			Thread.sleep(3000);
			
			
			Result.fUpdateLog("Disconnect Order saved Successfully");
			Result.takescreenshot("Disconnect Order saved Successfully");

	

			Thread.sleep(5000);

			
			// Clicking on Signature Capture
			CO.scroll("SignatureCapture_Button", "WebButton");
			Browser.WebButton.waittillvisible("SignatureCapture_Button");
			Method.Highlight("SignatureCapture_Button", "WebButton");

			// Clicking on Signature Capture button.
			CO.JavaScriptClick("SignatureCapture_Button", "WebButton");
			CO.isAlertExist();

			// Perform Signature
			Thread.sleep(5000);
			WebElement SigPad = cDriver.get()
					.findElement(By.xpath("//div[@class='mceGridField siebui-value mceField']//canvas"));
			SigPad.click();
			Browser.WebEdit.doSignature("Signature_Pad");

			Thread.sleep(6000);

			Browser.WebButton.waittillvisible("signatureSave");
			Result.fUpdateLog("Signature Captured");
			// Clicking On Save Option.
			Browser.WebButton.click("signatureSave");
			Thread.sleep(2000);

			// Scroll to Validate
			CO.scroll("Validate", "WebButton");
			Result.takescreenshot("Signature Captured");
			Result.fUpdateLog("Signature Captured");

			// Clicking on Validate
			CO.waitforload();

			// Clicking on Validate.
			Thread.sleep(3000);
			Method.Highlight("Validate", "WebButton");
			Result.takescreenshot("Clicked On Validate");
			Result.fUpdateLog("Clicked On Validate");
			CO.JavaScriptClick("Validate", "WebButton");
			Thread.sleep(10000);

			// Clicking on Draft eFrom.
			Method.Highlight("DrafteForm_Btn", "WebButton");
			Browser.WebButton.click("DrafteForm_Btn");
			Result.takescreenshot("Clicked On DrafteForm");
			Result.fUpdateLog("Clicking On DrafteForm");
			Thread.sleep(25000);

			// Fetching Order Number.
			ordernum = Browser.WebEdit.gettext("order_numfetch");
			Result.fUpdateLog("Current Order Number " + ordernum);
			Result.takescreenshot("Current Order Number " + ordernum);

			// Clicking on Submit button.
			CO.scroll("SubmitBtn", "WebButton");
			Method.Highlight("SubmitBtn", "WebButton");
			//Browser.WebButton.click("SubmitBtn");
			CO.JavaScriptClick("SubmitBtn", "WebButton");
			
			Result.fUpdateLog(" Clicking on Submit button.");
			Result.takescreenshot("Clicking on Submit button.");

			//***********       Account Summary View.        ***************
			// Refresh Order Status
		    String OrderStatus = OrderRefresh(); 
		    
			// Scroll down to Lineitems
			CO.scroll("Line_Items", "WebButton");

			// Clicking on Line Items Tab
			Method.Highlight("Line_Items", "WebButton");
			Thread.sleep(1000);
			Browser.WebButton.click("Line_Items");
			Thread.sleep(5000);
			Result.takescreenshot("Newly Installed Assets after Order Submission");
			Result.fUpdateLog("Newly Installed Assets after Order Submission");
			Thread.sleep(2000);
	 
			// Scroll to Account Summary
			CO.scroll("Acc_Summarybtn", "WebButton");
			Result.takescreenshot("After Refreshing the Browser Order Status: "+ OrderStatus );
			Result.fUpdateLog("After Refreshing the Browser Order Status: "+ OrderStatus );
		
			// Clicking on Account Summary button.
			Method.Highlight("Acc_Summarybtn", "WebButton");
			Browser.WebButton.click("Acc_Summarybtn");
			Result.fUpdateLog(" Clicking on Account Summary button");
			Result.takescreenshot(" Clicking on Account Summary button");

			Thread.sleep(3000);

			// Scroll to Account status Indicator
			CO.scroll("Accout_Status_Indicator", "WebEdit");
			
			Result.fUpdateLog("Account Summary Detail");
			Result.takescreenshot("Account Summary Detail");
			Thread.sleep(1000);

			// Scroll into Modify button.
			CO.scroll("Modify_button", "WebButton");
			Result.fUpdateLog("Installed Asset Detail");
		
			System.out.println("********Fetching Order Number :   " + ordernum);
			String OrderDeatils = "Order Submitted successfully And ORDER ID:" + ordernum;

			Thread.sleep(10000);			
			
			
			CO.ToWait();
			if (Continue.get()) {
				Test_OutPut += "Account Number " +AccountNo+" , Disconnect Event Passed" + ", "+OrderDeatils;
				Result.fUpdateLog("Account Number " +AccountNo+" , Disconnect Event Passed" + ", "+OrderDeatils);
				Result.takescreenshot("Account Number " +AccountNo+" , Disconnect Event Passed" + ", "+OrderDeatils);
				Status = "PASS";
			//	Result.takescreenshot("Account Created Account_No : " + Account_No);
			} else {
				Test_OutPut += "Disconnect Order  is Failed" + ",";
				Result.takescreenshot("Disconnect Order  is Failed");
				Status = "FAIL";
			 }
			
			} catch (Exception e) {
		Continue.set(false);
		Test_OutPut += "Exception occurred" + ",";
		Result.takescreenshot("Exception occurred");
		Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
		Status = "FAIL";
		e.printStackTrace();

	}
	Result.fUpdateLog("------Disconnect Order  Event Details - Completed------");
	return Status + "@@" + Test_OutPut + "<br/>";
	     
		}
	/*---------------------------------------------------------------------------------------------------------
	 * Method Name         : Batelco_ModifyService
	 * Arguments           : None
	 * Use                 : ModifyService for (Change Billing Profile,MSISDN Change, Change of Ownership, 
	 *                                            Modify Service BoltOn ,SIM Swap ) these order journey's.
	 * Designed By         : Srikanth.D and Soniya.Y
	 * Last Modified Date  : 22-Oct-2019
	--------------------------------------------------------------------------------------------------------*/
	public String Batelco_ModifyService() {
		String Test_OutPut = "", Status = "";
		String Message = "",NewBiilingProfileNumber="",NumberMessage="";
		Result.fUpdateLog("------ Modify Service Creation Event Details------");
		try {
			Thread.sleep(10000);
		
    
    		Account_search();
    		
			CO.waitforload();
	
			// Scroll to Account status Indicator
			CO.scroll("Accout_Status_Indicator", "WebEdit");
			
			//Fetching Account Number
			String AccountNo=Browser.WebEdit.gettext("Account_Number");
			Utlities.StoreValue("Account_No", AccountNo);
			AccountNumber =	AccountNo;
			
			//Fetching Account Name
			String Account_Holder_Name=Browser.WebEdit.gettext("AccountHolder_Name");	
			
			// Scroll to Account status Indicator
			CO.scroll("Accout_Status_Indicator", "WebEdit");
	    	Result.takescreenshot("Account Number: "+AccountNo +" and Name: " +Account_Holder_Name);
		    Result.fUpdateLog("Account Number: "+AccountNo +" and Name: " +Account_Holder_Name);
	       
			//Scroll into Modify button.
			CO.scroll("Modify_button", "WebButton");
		    Result.fUpdateLog("Installed Assets ");
			Result.takescreenshot("Installed Assets ");
			
			// Fetching existing MSISDN Number.
			String fetch_MSISDNNumber = "";
			if(getdata("orderreason").equals("Change Number") || getdata("orderreason").equals("SIM Swap") ) {
			
				// fetching Existed MSISDN Number.
				Thread.sleep(3000);
				CO.scroll("FetchingServiceNo", "WebTable");
				Browser.WebTable.click("FetchingServiceNo", 2,32);
				fetch_MSISDNNumber=	Browser.WebTable.getCellData("FetchingServiceNo", 2,33);				
				Thread.sleep(2000);
				
				//Scroll into Modify button.
				CO.scroll("Modify_button", "WebButton");
			    Result.fUpdateLog("Current MSISDN Number: " + fetch_MSISDNNumber);
				Result.takescreenshot("Current MSISDN Number: " + fetch_MSISDNNumber);
			}
			
			// click on modify button in account screen
			Browser.WebButton.click("Modify_button");

			// Selecting order reason
			String Order_Reason = getdata("orderreason");
			Browser.WebEdit.Set("orderreason_modify", getdata("orderreason"));
			System.out.println(getdata("orderreason"));
			
			Result.takescreenshot("Selecting Order Reason: "+Order_Reason);
			Result.fUpdateLog("Selecting Order Reason: "+Order_Reason);
			// click on OK button
			CO.JavaScriptClick("ServiceType_Ok", "WebButton");

			// ********************************************************************************************************************************

			switch (Order_Reason) {

			case "Change Billing Profile":
				System.out.println(getdata("orderreason"));
				Thread.sleep(1000);
				CO.waitforload();

				// fetching Existed Billing Profile Number.
				String fetchBillNumber = Browser.WebTable.getCellData("Productselection_table", 3, 5);
				Thread.sleep(1000);
				Result.fUpdateLog("Current Billing Profile Number: " + fetchBillNumber);
				Result.takescreenshot("Current Billing Profile Number: " + fetchBillNumber);

				// Clicking on Billing Profile Link.
				CO.JavaScriptClick("BillingProfileTab", "WebLink");

				// Clicking on Billing Profile '+' button.
				Browser.WebButton.click("BillingProfile_Search");
				Thread.sleep(4000);

				// Entering Contact Number
				Browser.WebTable.click("Billingprofile_table", 2, 9);
				Browser.WebTable.click("Billingprofile_table", 2, 9);
				if (!(getdata("BillingProfile_ContactNumber").equals(""))) {
					Browser.WebTable.SetDataE("Billingprofile_table", 2, 9, "BT_Primary_Contact_Phone_Number",
							getdata("BillingProfile_ContactNumber"));
				} else {

					Browser.WebTable.SetDataE("Billingprofile_table", 2, 9, "BT_Primary_Contact_Phone_Number",
							pulldata("BillingProfile_ContactNumber"));
				}
				Thread.sleep(2000);

				// Entering Email Id
				if (!(getdata("BillingProfile_eMail").equals(""))) {
					Browser.WebTable.SetDataE("Billingprofile_table", 2, 10, "BT_eMail",
							getdata("BillingProfile_eMail"));
				} else {
					Browser.WebTable.SetDataE("Billingprofile_table", 2, 10, "BT_eMail",
							pulldata("BillingProfile_eMail"));
				}
				Thread.sleep(4000);

				Browser.WebTable.waittillvisible("Billingprofile_table");

				// Entering Additional Payments
				if (!(getdata("BillingProfile_PaymentMethod").equals(""))) {
					Browser.WebTable.SetDataE("Billingprofile_table", 2, 26, "BT_Additional_Payment_Method",
							getdata("BillingProfile_PaymentMethod"));
				} else {

					Browser.WebTable.SetDataE("Billingprofile_table", 2, 26, "BT_Additional_Payment_Method",
							pulldata("BillingProfile_PaymentMethod"));
				}
				Thread.sleep(2000);

				// Highlight Associate button.
				Method.Highlight("Associate_button", "WebButton");
				CO.waitforload();

				// Clicking on Associate Button
				Browser.WebButton.click("Associate_button");
				Thread.sleep(1000);

				// Clicking on Billing Profile Go Button
				Browser.WebButton.click("OrderAssociateGoBtn");
				Thread.sleep(8000);
				CO.isAlertExist();

				// Clicking on Associate Button
				Browser.WebButton.click("Associate_button");
				CO.isAlertExist();
				Thread.sleep(3000);

				// Clicking On Billing Profile Menu ICon Button.
				Browser.WebButton.click("BillingProfile_MenuIcon");

				// Click on Save Billing Profile button.
				Browser.WebLink.click("BillProfile_Savebtn");
				Thread.sleep(3000);

				// Fetching Newly created Billing Number.
				CO.scroll("BillingProfile_No", "WebEdit");
				NewBiilingProfileNumber = cDriver.get()
						.findElement(By.xpath("//table[@id='s_2_l']/tbody/tr[2]/td[3]/a")).getText();

				// Storing Newly Created Billing Profile Number in StoreDatabase.
				Utlities.StoreValue("New_BillingProfileNumber", NewBiilingProfileNumber);
				Result.fUpdateLog("Newly Created Billing Profile Number: " + NewBiilingProfileNumber);
				Result.takescreenshot("Newly Created Billing Profile Number: " + NewBiilingProfileNumber);
				Thread.sleep(3000);

				// Clicking on Line Items Tab
				Method.Highlight("Line_Items", "WebButton");
				CO.JavaScriptClick("Line_Items", "WebButton");

				Thread.sleep(5000);
				// Clicking the Billing Profile field, and open the pick applet.
				Browser.WebTable.click("Productselection_table", 3, 5);
				Browser.WebTable.click_S("Productselection_table", 3, 5);
				Thread.sleep(3000);

				// Clicking on OK button.
				boolean okButton = cDriver.get()
						.findElement(By.xpath("//button[@aria-label='Pick Billing Profile:OK']")).isEnabled();
				Thread.sleep(5000);
				System.out.println(okButton);
				if (okButton) {
					Result.takescreenshot("Clicking the Billing Profile field, and open the pick applet.");

					// Enter billing Number.
					Browser.WebEdit.Set("NewlyCreatedBillNo", NewBiilingProfileNumber);
					
					// Click on go button.
					Browser.WebButton.click("BillingProfile_AppletGO_Btn");
					Result.fUpdateLog("Selecting New Billing Profile Number from applet");
					Result.takescreenshot("Selecting New Billing Profile Number from applet");

				} else {
					// Clicking on Cancel Button.
					Browser.WebButton.click("ChangeBillingProfileCancelbtn");

					Thread.sleep(3000);
					Browser.WebTable.click("Productselection_table", 2, 5);
					Browser.WebTable.click_S("Productselection_table", 2, 5);
					Thread.sleep(5000);
					Result.takescreenshot("Clicking the Billing Profile field, and open the pick applet.");
					Browser.WebEdit.Set("NewlyCreatedBillNo", NewBiilingProfileNumber);

					// Click on go button.
					Browser.WebButton.click("BillingProfile_AppletGO_Btn");
					Result.fUpdateLog("Clicking on GO button");

					Result.fUpdateLog("Selecting New Billing Profile Number from applet");
					Result.takescreenshot("Selecting New Billing Profile Number from applet");

					// Clicking on OK button.
					// Browser.WebButton.click("BillingProfile_AppletOkBtn");

				}

				Result.fUpdateLog("Billing Profile Changed Successfully");

				// Compare with Existing Billing Number.
				if (NewBiilingProfileNumber.equals(fetchBillNumber)) {
					Result.fUpdateLog("Not selecting New Billing Profile Number: " + NewBiilingProfileNumber);
					Result.takescreenshot(
							"Not selecting New Billiing Profile Number for applet NO is: " + NewBiilingProfileNumber);
					System.out.println("Not Selecting New Billing Profile Number: " + NewBiilingProfileNumber);

				} else {
					Result.fUpdateLog("New Billing Profile Number is: " + NewBiilingProfileNumber);
					Result.takescreenshot("Selecting New Billing Profile: " + NewBiilingProfileNumber);
					System.out.println("New Billing Profile Number is: " + NewBiilingProfileNumber);
				}

				// checking Billing Status is Updated or not.
//				List<WebElement> totalRowNOs = cDriver.get()
//						.findElements(By.xpath("//table[contains(@summary,'List (Sales)')]/tbody/tr"));
			
				int totalRowNOs = Browser.WebTable.getRowCount("Productselection_table");
				
				String billStaus;
				for (int i = 2; i <= totalRowNOs; i++) {
					billStaus = Browser.WebTable.getCellData("Productselection_table", i, 2);

					if (billStaus.equals("Update")) {
						System.out.println("Row Number " + i + " Updated Done.");
						break;
					} else {
						System.out.println("we have issue is Update Billing Status Process........");
					}

				}
				Thread.sleep(3000);

				// Clicking On Line Item Menu.
				Browser.WebButton.click("LineItems_MenuBtn");

				// Select Save Record Option.
				Browser.WebLink.click("SaveRecord");
				Thread.sleep(2000);

				// clicking on Tree icon to enable query number
				Browser.WebButton.click("OrderExpandTreeIcon");

				// Scroll into Line Items Tab
				CO.scroll("Line_Items", "WebButton");

				Thread.sleep(4000);
				Result.fUpdateLog("Billing Profile Number Changed from " + fetchBillNumber + " to "
						+ NewBiilingProfileNumber + ", Updated Successfully in Form Applet");
				Result.takescreenshot("Billing Profile Number Changed from " + fetchBillNumber + " to "
						+ NewBiilingProfileNumber + ", Updated Successfully in Form Applet");
				
				Message = "Billing Profile Number Changed from " + fetchBillNumber + " to " + NewBiilingProfileNumber
						+ " ";

				break;

			case "Change Number":
				System.out.println(getdata("orderreason"));
				Thread.sleep(20000);

				// Verifying Service ID Number
				CO.waitforload();
				Browser.WebTable.click("Bat_add_address_table", 3, 7);
				String Service_ID = Browser.WebTable.getCellData("Bat_add_address_table", 3, 7);
				Result.fUpdateLog("***********************" + Service_ID);
				
				if (Service_ID.equals("")) {
					Result.fUpdateLog(" Service ID field is set to blank for Change MISISDN Number ");
					Result.takescreenshot(" Service ID field is set to blank ");

				} else {
					Result.fUpdateLog(" Service ID field is Not empty");
					Result.takescreenshot("Service ID field is Not empty ");
				}

				// Verifying Action Code gets updated to Update or Not.
				String Action_Status = cDriver.get()
						.findElement(By.xpath(
								"//table[@id='s_3_l']//tbody/tr[3]/td[contains(@aria-labelledby,'s_3_l_Action')]"))
						.getText();
				if (Action_Status.equals("Update")) {
					System.out.println("Action Code gets updated to Update");
					

				} else {
					System.out.println("Action Code gets updated to Update");
					
				}

				Thread.sleep(5000);

				// clicking on Tree icon to enable query number
				Browser.WebButton.click("OrderExpandTreeIcon");
				Result.takescreenshot("Action Code gets updated to Update");
				Result.fUpdateLog("clicking on Tree icon to enable query number");
				
				Thread.sleep(3000);

				// Clicking on query number tab
				CO.scroll("querynumber_lineitems", "WebButton");
				Method.Highlight("querynumber_lineitems", "WebButton");
				Result.fUpdateLog("Clicking on Query Number Lineitems tab");
				Result.takescreenshot("Clicking on Query Number Lineitems tab");
				Thread.sleep(2000);
				Browser.WebButton.click("querynumber_lineitems");

				// Entering Query Number
				Browser.WebTable.SetDataE("Querynumber", 2, 2, "BT_Service_Id", getdata("MSISDN"));
				String New_MsisdnNumber = getdata("MSISDN");
				Thread.sleep(2000);
				Result.fUpdateLog("Clicking on Query Number tab");
				Result.takescreenshot("Clicking on Query Number tab");

				// Moving to Third Tab
				Browser.WebTable.click("Querynumber", 2, 4);

				// Clicking on Query Number Button
				CO.JavaScriptClick("Querynumber_number", "WebButton");

				// Moving to Third Tab
				Browser.WebTable.click("Querynumber", 2, 4);

				// Clicking on Query Number Button
				CO.JavaScriptClick("Querynumber_number", "WebButton");

				Thread.sleep(15000);

				// Moving to Third Tab
				Browser.WebTable.click("Querynumber", 2, 4);

				// Clicking on Select Button
				Method.Highlight("Select", "WebButton");
				Browser.WebButton.waittillvisible("Select");
				Result.fUpdateLog("clicking on Select Button.");
				Result.takescreenshot("clicking on Select Button.");
				Browser.WebButton.click("Select");

				Thread.sleep(8000);

				// Clicking on OrderExpandTreeIcon.
				Browser.WebButton.click("OrderExpandTreeIcon");
				CO.scroll("querynumber_lineitems", "WebButton");
				Thread.sleep(5000);

				// Scroll into Order Menu button.
				Browser.WebButton.waittillvisible("OrderMenu_Btn");
				CO.scroll("OrderMenu_Btn", "WebButton");

				// Clicking On Setting Button
				CO.JavaScriptClick("OrderMenu_Btn", "WebButton");
				Thread.sleep(2000);

				// Saving the Order Creation
				Method.Highlight("SaveRecord", "WebLink");
				Browser.WebLink.waittillvisible("SaveRecord");
				Result.fUpdateLog("Saving the Order Creation");
				Result.takescreenshot("Saving the Order Creation");
				
                //Clicking on Save Record option
				Browser.WebLink.click("Saverecord");
				Thread.sleep(6000);
				
				// Clicking on OrderExpandTreeIcon.
				CO.scroll("SIMNO_CustomizeBtn", "WebButton");

				Browser.WebButton.click("OrderExpandTreeIcon");
				Thread.sleep(5000);

				// Scroll into to Customize Button.
				CO.scroll("SIMNO_CustomizeBtn", "WebButton");
				Result.fUpdateLog("LineItems Details");
				Result.takescreenshot("LineItems Details");
				Thread.sleep(3000);

				Message = "MSISDN Number Successfully Changed from " + fetch_MSISDNNumber + " to  " + New_MsisdnNumber;

				break;

			case "Change of Ownership":
	             System.out.println(getdata("orderreason"));
					
					// Clicking on Account Search text box.
					Browser.WebEdit.click("Ownership_AccountSearch");
					Result.takescreenshot("Clicked on Ownership_AccountSearch");
					Result.fUpdateLog("Clicked on Ownership_AccountSearch");
					
					// Clicking on Account Search icon Button.
					Browser.WebButton.click("Ownership_AcctSearch_ICon");
					Result.takescreenshot("Clicked on Ownership_AcctSearch_ICon");
					Result.fUpdateLog("Clicked on Ownership_AcctSearch_ICon");
					
					// Clicking on Dropdown list.
					Browser.WebEdit.Set("Ownership_Search_Type", pulldata("Ownership_Type"));
					Result.takescreenshot("Entered Ownership_Search_Type");
					Result.fUpdateLog("Entered Ownership_Search_Type");
					
					// Entering Text in Dropdown.
					Browser.WebEdit.Set("Ownership_Search_Value", pulldata("Ownership_Value"));
					Result.takescreenshot("Entered Ownership_Value");
					Result.fUpdateLog("Entered Ownership_Value");
					
					// Clicking on GO Button.
					Browser.WebButton.click("Ownership_GO_but");
					
					Result.fUpdateLog("Clicked on Ownership_GO Button");
					
					// Clicking on GO Button.
					Browser.WebButton.click("Ownership_OK_but");
					Result.takescreenshot("Clicked on Ownership_OK Button");
					Result.fUpdateLog("Clicked on Ownership_OK Button");
					
					//Clicking on Billing Profile
					Browser.WebEdit.click("Ownership_BillingProfile");
					Result.takescreenshot("Clicked on Ownership_BillingProfile ");
					Result.fUpdateLog("Clicked on Ownership_BillingProfile ");
					
					// Clicking on Account Search icon Button.
					Browser.WebButton.click("Ownership_BillingProfile_Icon");
					Result.takescreenshot("Clicked on Ownership_BillingProfile_Icon ");
					Result.fUpdateLog("Clicked on Ownership_BillingProfile_Icon ");
					
					// Clicking on GO Button.
					Browser.WebButton.click("BillingProfile_OK");
					Result.takescreenshot("Clicked on OK Button");
					Result.fUpdateLog("Clicked on OK Button");
					Thread.sleep(3000);
	
					//Scroll to Account Search box.
					CO.scroll("New_OwnerName", "WebEdit");
					
					//Fetching New Owner Name
					String New_OwnerName = Browser.WebEdit.gettext("New_OwnerName");
		            Result.fUpdateLog("New Account Owner Name: "+New_OwnerName);
					Thread.sleep(3000);

				    NumberMessage = "Ownership Changes from: " + Account_Holder_Name + " to " + New_OwnerName;
				    System.out.println(NumberMessage);

				break;

			 case "Modify Service":
				 System.out.println(getdata("orderreason"));
   
			    CO.isAlertExist();
					CO.waitforload();
					// click on expand button in account screen
					Browser.WebButton.click("Modify_expandbtn");
					// click on customize button in account screen
					Browser.WebButton.click("Modify_customizebtn");
					
					Thread.sleep(3000);
					
					Dictionary<String, String> d=TestData.get();
					
					Enumeration<String> enumValues=d.keys();
					
					//1.Counting Number of Packages.
					System.out.println("Number of Package are Selected : "+d.size());
					
				// Fetching Product Name.
				while (enumValues.hasMoreElements()) {

					Object key = enumValues.nextElement();
					String Key_Name = (String) key;
					String Key_Value = (String) d.get(key);

					if (Key_Name.equals("Identification_Number") || Key_Name.equals("orderreason")) {

						// this Key_name is not a Product Names.

					} else {
						Result.fUpdateLog(" ***********   Package Name : " + Key_Name
								+ "*****    #####   selected Products are : ##### " + Key_Value);

						// Scroll into Package Name.
						CO.scroll(Key_Name, "WebLink");

						// Clicking on Package Name
						CO.JavaScriptClick(Key_Name, "WebLink");
						Result.fUpdateLog("Clicking on " + Key_Name + " Package");
						Result.takescreenshot("Clicking on " + Key_Name + " Package");

						// Splitting Product Name based on " :: " Symbol.
						StringTokenizer tokens = new StringTokenizer(Key_Value, "::");

						// Looping One by one Product.
						while (tokens.hasMoreElements()) {
							
							String Product = (String) tokens.nextElement();

							// Scroll into Product Name
							CO.scroll(Product, "WebCheckBox");

							// Clicking on Product name.
							CO.JavaScriptClick(Product, "WebCheckBox");
							Result.fUpdateLog(Product + " Selected ");
							Result.takescreenshot(Product + " Selected ");
							Thread.sleep(2000);

						}
					}

				}
	
			        CO.waitforload();
					CO.isAlertExist();

					// click on done button at that top
					CO.scroll("modify_done", "WebButton");
					Method.Highlight("modify_done", "WebButton");
					Result.takescreenshot("Clicking on Done button.");
					Result.fUpdateLog("Clicking on Done button.");
					
					//Browser.WebButton.click("modify_done");
                    CO.JavaScriptClick("modify_done", "WebButton");
					CO.waitforload();

					// click on validate button
					Browser.WebButton.click("Validate");

					// Need to give the reason the in the order screen
					CO.scroll("Modify_reason", "ListBox");
					Method.Highlight("Modify_reason", "ListBox");
					
					// selecting Order Reason as Add Bundle
					Browser.ListBox.clear("Modify_reason");
					Browser.ListBox.setdropvalue("Modify_reason", "Add Bundle");
					
					WebElement ele = cDriver.get().findElement(By.xpath("//input[@aria-label='Order Cancel Reason']"));
					ele.sendKeys(Keys.TAB);
					Thread.sleep(1000);

					Result.takescreenshot("selecting Order Reason");
					Result.fUpdateLog("selecting Order Reason");
	
					// Clicking on Line Items Tab
					CO.scroll("Line_Items", "WebButton");
					Method.Highlight("Line_Items", "WebButton");
					Thread.sleep(2000);
					Browser.WebButton.click("Line_Items");
			
					// Scroll into Order Menu button.
					Browser.WebButton.waittillvisible("OrderMenu_Btn");
					//CO.scroll("OrderMenu_Btn", "WebButton");

					// Clicking On Setting Button
					CO.JavaScriptClick("OrderMenu_Btn", "WebButton");
					Thread.sleep(4000);

					// Saving the Order Creation
					Method.Highlight("SaveRecord", "WebLink");
					Browser.WebLink.waittillvisible("SaveRecord");
					Result.fUpdateLog("Saving the Order Creation");
					Result.takescreenshot("Saving the Order Creation");
					
	                //Clicking on Save Record option
					Browser.WebLink.click("Saverecord");
					Thread.sleep(5000);
                   
				 break;
			
			case "SIM Swap":
				System.out.println(getdata("orderreason"));

				// clicking Tree icon to enable query number and Customize button.
				Browser.WebButton.click("OrderExpandTreeIcon");
				Result.fUpdateLog("clicking Tree icon to enable query number");
				Result.takescreenshot("clicking Tree icon to enable query number");
				Thread.sleep(5000);

				// fetching Current SIM Number
				int SIMRowNo1 = Browser.WebTable.getRowWithCellText("Productselection_table", 6, "U-SIM MP");
				System.out.println(" U-SIM MP found in Row Number " + SIMRowNo1);
				String fetch_SIM_Number = Browser.WebTable.getCellData("Productselection_table", SIMRowNo1, 7);

				if (fetch_SIM_Number.equals("")) {

					// if SIM Number is Not Available in First Table then Clicking on NextRecord
					// button.
					Browser.WebButton.click("NextRecordBtn");
					CO.scroll("SIMNO_CustomizeBtn", "WebButton");
					Thread.sleep(3000);

					// Fetching SIM Number.
					int SIMRowNo11 = Browser.WebTable.getRowWithCellText("Productselection_table", 6, "U-SIM MP");
					System.out.println(" U-SIM MP found in Row Number " + SIMRowNo11);
					fetch_SIM_Number = Browser.WebTable.getCellData("Productselection_table", SIMRowNo11, 7);
					Result.fUpdateLog("fetching Current SIM Number " + fetch_SIM_Number);
					Result.takescreenshot("fetching Current SIM Number " + fetch_SIM_Number);

					// clicking on Previous Record button.
					Browser.WebButton.click("PreviousRecordBtn");
					CO.scroll("SIMNO_CustomizeBtn", "WebButton");

				} else {

					// if SIM Number is Available then fetch SIM Number
					Result.fUpdateLog("fetching Current SIM Number " + fetch_SIM_Number);
					Result.takescreenshot("fetching Current SIM Number " + fetch_SIM_Number);
				}

				// Clicking on Non-Smart Commercial Bundle.
				int CommercialBundleRowNo1 = Browser.WebTable.getRowWithCellText("Productselection_table", 3,
						"Non-Smart Commercial Bundle");
				System.out.println("Non-Smart Commercial Bundle found in  Row Number " + CommercialBundleRowNo1);
				Browser.WebTable.click("Productselection_table", CommercialBundleRowNo1, 6);
				Browser.WebTable.click("Productselection_table", CommercialBundleRowNo1, 6);
				Result.fUpdateLog("Clicking on Non-Smart Commercial Bundle.");
				Result.takescreenshot("Clicking on Non-Smart Commercial Bundle.");

				// Clicking on Customize Button.
				CO.scroll("SIMNO_CustomizeBtn", "WebButton");
				Browser.WebButton.click("SIMNO_CustomizeBtn");
				Result.fUpdateLog("Clicking on Customize Button.");
				Result.takescreenshot("clicking on Customize Button.");

				// ***************** Handling ERROR MESSAGE ***********************************

				try {
					Thread.sleep(2000);
					cDriver.get().findElement(By.xpath("//button[text()='Proceed']")).click();
					Thread.sleep(2000);
				} catch (NoSuchElementException e1) {
					// Do Nothing
				} catch (ElementNotInteractableException e2) {

					// Do Nothing
				}

				Thread.sleep(3000);

				// Clicking on SIM Swap Screen
				Browser.WebLink.click("SIMSWAP_Screen");
				Result.fUpdateLog("Clicking on SIM Swap Screen");
				Result.takescreenshot("Clicking on SIM Swap Screen.");
				Thread.sleep(5000);

				// clear Quantity.
				Browser.WebEdit.clear("SIMSWAP_Quantity");
				Result.fUpdateLog("clear Quantity.");
				Result.takescreenshot("clear Quantity.");

				// Clicking On Verify Button.
				Browser.WebButton.click("ChangeSIM_VerifyBtn");
				Result.fUpdateLog("Clicking On Verify Button.");

				// Handing Alert Message.
				CO.isAlertExist();
				Result.takescreenshot("clicking on Verify Button.");

				// Clicking On Done Button.
				CO.JavaScriptClick("ChangeSIM_DoneBtn", "WebButton");
				CO.isAlertExist();
				Thread.sleep(5000);

				// Clicking on ExpandTreeIcon.
				Browser.WebButton.click("OrderExpandTreeIcon");

				// ***** Verification: The old SIM will be present with Action Code as Delete
				// Clicking on U-SIM MP.
				int USIMRowNo2 = Browser.WebTable.getRowWithCellText("Productselection_table", 6, "U-SIM MP");
				System.out.println(" U-SIM MP found in Row Number " + USIMRowNo2);
				String SIM_Action_Status = Browser.WebTable.getCellData("Productselection_table", USIMRowNo2, 2);

				if (SIM_Action_Status.equals("")) {

					// clicking on NextRecord button.
					Method.Highlight("NextRecordBtn", "WebButton");
					CO.scroll("SIMNO_CustomizeBtn", "WebButton");
					Browser.WebButton.click("NextRecordBtn");

					CO.scroll("SIMNO_CustomizeBtn", "WebButton");
					Thread.sleep(3000);

					// Clicking on U-SIM MP.
					int USIMRowNo22 = Browser.WebTable.getRowWithCellText("Productselection_table", 6, "U-SIM MP");
					System.out.println(" U-SIM MP found in Row Number " + USIMRowNo22);
					SIM_Action_Status = Browser.WebTable.getCellData("Productselection_table", USIMRowNo22, 2);

					// Checking Action Status
					if (SIM_Action_Status.equals("Delete")) {
						System.out.println(" old Sim Action Code as Delete ");
						Result.fUpdateLog("Old SIM Action Status : " + SIM_Action_Status);
						Result.takescreenshot("Old SIM Action Status: " + SIM_Action_Status);

						for (int i = 2; i <= 11; i++) {

							String product = Browser.WebTable.getCellData("Productselection_table", i, 3);
							String status = Browser.WebTable.getCellData("Productselection_table", i, 2);

							if (product.equals("U-SIM MP") && status.equals("Delete")) {
								System.out.println(product + " Action Code as Delete *********************");
								System.out.println(" old Sim Action Code");
								Result.fUpdateLog("Old SIM Action Status  " + status);
								Result.takescreenshot("Old SIM Action Status " + status);
								break;
							} else if (status.equals("-")) {
								System.out.println(product + " Action Code as  - ");
							} else {
								System.out.println(product + " NO Action Code ");

							}
						}
						Thread.sleep(3000);

						// clicking on Previous Record button.
						// Browser.WebButton.click("PreviousRecordBtn");
						CO.JavaScriptClick("PreviousRecordBtn", "WebButton");
						Thread.sleep(2000);
						CO.scroll("SIMNO_CustomizeBtn", "WebButton");

					} else {
						System.out.println(" old Sim Action Code Updated");
						Result.fUpdateLog("Old SIM Action Status updated " + SIM_Action_Status);
						Result.takescreenshot("Old SIM Action Status updated " + SIM_Action_Status);
					}

				} else {

					// Checking Action Status
					int USIMRowNo23 = Browser.WebTable.getRowWithCellText("Productselection_table", 6, "U-SIM MP");
					SIM_Action_Status = Browser.WebTable.getCellData("Productselection_table", USIMRowNo23, 2);
					System.out.println(" old Sim Action Code as Delete ");
					Result.fUpdateLog("Old SIM Action Status : " + SIM_Action_Status);
					Result.takescreenshot("Old SIM Action Status: " + SIM_Action_Status);

					for (int i = 2; i <= 11; i++) {

						String product = Browser.WebTable.getCellData("Productselection_table", i, 3);
						String status = Browser.WebTable.getCellData("Productselection_table", i, 2);

						if (product.equals("U-SIM MP") && status.equals("Delete")) {
							System.out.println(product + " Action Code as Delete *********************");
							break;
						} else if (status.equals("-")) {
							System.out.println(product + " Action Code as  - ");
						} else {
							System.out.println(product + " NO Action Code ");

						}
					}

					System.out.println(" old Sim Action Code");
					Result.fUpdateLog("Old SIM Action Status " + SIM_Action_Status);
					Result.takescreenshot("Old SIM Action Status" + SIM_Action_Status);

				}

				Thread.sleep(2000);

				// Clicking on Non-Smart Commercial Bundle.
				int CommercialBundleRowNo2 = Browser.WebTable.getRowWithCellText("Productselection_table", 3,
						"Non-Smart Commercial Bundle");
				System.out.println("Non-Smart Commercial Bundle found in  Row Number " + CommercialBundleRowNo2);
				Browser.WebTable.click("Productselection_table", CommercialBundleRowNo2, 6);
				Browser.WebTable.click("Productselection_table", CommercialBundleRowNo2, 6);
				Result.fUpdateLog("Clicking on Non-Smart Commercial Bundle.");
				Result.takescreenshot("Clicking on Non-Smart Commercial Bundle.");

				// Clicking on Customize Button.
				CO.scroll("SIMNO_CustomizeBtn", "WebButton");
				Browser.WebButton.click("SIMNO_CustomizeBtn");
				Result.fUpdateLog("Clicking on Customize Button.");
				Result.takescreenshot("clicking on Customize  Button.");

				// ****************** Handling ERROR MESSAGE ***********************************
//				try {
//					cDriver.get().findElement(By.xpath("//button[text()='Proceed']")).click();
//					Thread.sleep(2000);
//				} catch (NoSuchElementException e1) {
//					// Do Nothing
//				} catch (ElementNotInteractableException e2) {
//
//					// Do Nothing
//				}

				// Clicking SIM Swap Screen
				Browser.WebLink.click("SIMSWAP_Screen");
				Thread.sleep(3000);

				// Again Enter Quantity.
				Browser.WebEdit.click("SIMSWAP_Quantity");
				Browser.WebEdit.SetE("SIMSWAP_Quantity", getdata("SIM_Quantity"));

				Result.fUpdateLog("Entering Quantity.");
				Result.takescreenshot("Entering Quantity.");

				// Clicking SIM Swap Screen
				Browser.WebLink.click("SIMSWAP_Screen");
				Thread.sleep(3000);

				// SIM Swap Screen Customize icon.
				Browser.WebButton.click("SIMSWAP_Customize_setting_ICon");
				Result.fUpdateLog("SIM Swap Screen Customize icon.");
				Result.takescreenshot("SIM Swap Screen Customize icon.");

				// Entering IMSI SIM Number
				Browser.WebEdit.clear("New_SimNO");
				Browser.WebEdit.Set("New_SIMNO", getdata("Sim_IMSI_NO"));
				Result.fUpdateLog("Entering IMSI SIM Number.");
				Result.takescreenshot("Entering IMSI SIM Number.");

				// Entering New PUK1 Code
				Browser.WebEdit.clear("PUK1_No");
				Browser.WebEdit.Set("PUK1_No", getdata("IMSI_PUK1_No"));
				Result.fUpdateLog("Entering New PUK1 Code.");
				Result.takescreenshot("Entering New PUK1 Code.");

				// Entering New PUK2 Code
				Browser.WebEdit.clear("PUK2_No");
				Browser.WebEdit.Set("PUK2_No", getdata("IMSI_PUK2_No"));
				Result.fUpdateLog("Entering New PUK2 Code.");
				Result.takescreenshot("Entering New PUK2 Code.");

				// Clicking On Verify Button.
				Browser.WebButton.click("ChangeSIM_VerifyBtn");
				Result.fUpdateLog("Clicking On Verify Button.");
				CO.isAlertExist();
				Result.takescreenshot("clicking on Verify Button.");

				// Clicking On Done Button.
				Browser.WebButton.click("ChangeSIM_DoneBtn");
				Result.takescreenshot("Clicking On Done Button");

				// clicking Tree icon to enable query number
				Browser.WebButton.click("OrderExpandTreeIcon");

				Result.fUpdateLog("clicking Tree icon to enable query number ");
				Result.takescreenshot("clicking Tree icon to enable query number");

				// clicking on NextRecord button.
				Browser.WebButton.click("NextRecordBtn");

				CO.scroll("SIMNO_CustomizeBtn", "WebButton");

				// ***** New SIM Line Item will automatically get added with Action Code as Add
				// and blank Service Id. ***

				// Entering New SIM Number
				for (int i = 2; i <= 11; i++) {
					String data = Browser.WebTable.getCellData("Productselection_table", i, 7);
					System.out.println("Row Number:" + i + "******************" + data);

					if (data.trim().equals("")) {
						Browser.WebTable.click("Productselection_table", i, 7);
						Browser.WebTable.click("Productselection_table", i, 7);
						System.out.println(" Clicked");

						// Entering New Number.
						Browser.WebTable.SetData("Productselection_table", i, 7, "Service_Id", getdata("SimNo"));
						Thread.sleep(3000);
						System.out.println(getdata("SimNo"));
						Result.fUpdateLog("Entering New SIM Number :" + getdata("SimNo"));
						Result.takescreenshot("Entering New SIM Number :" + getdata("SimNo"));
						break;
					} else {
						System.out.println(" Not Clicked ");

					}

				}
				Thread.sleep(5000);

				// Scrolling up
				Browser.WebButton.waittillvisible("OrderMenu_Btn");
				CO.scroll("OrderMenu_Btn", "WebButton");

				// Clicking On Setting Button
				Browser.WebButton.click("OrderMenu_Btn");
				Result.fUpdateLog("Clicking On Setting Button.");
				Thread.sleep(1000);

				// Saving the Order Creation
				Method.Highlight("SaveRecord", "WebLink");
				Browser.WebLink.waittillvisible("SaveRecord");
				Browser.WebLink.click("SaveRecord");
				Thread.sleep(4000);
				Result.takescreenshot("Change Number Order saved Successfully");

				NumberMessage = " SIM Number Successfully Changed from " + fetch_SIM_Number + " to  "
						+ getdata("SimNo");

				break;

			}

			Thread.sleep(5000);

			// *************** Payments ****************************
		    String OrderPaymentStatus=Payment();
		    
		    Thread.sleep(5000);
			
			// Clicking on Signature Capture
			CO.scroll("SignatureCapture_Button", "WebButton");
			Browser.WebButton.waittillvisible("SignatureCapture_Button");
			Method.Highlight("SignatureCapture_Button", "WebButton");

			// Clicking on Signature Capture button.
			CO.JavaScriptClick("SignatureCapture_Button", "WebButton");
			CO.isAlertExist();

			// Perform Signature
			Thread.sleep(5000);
			WebElement SigPad = cDriver.get()
					.findElement(By.xpath("//div[@class='mceGridField siebui-value mceField']//canvas"));
			SigPad.click();
			Browser.WebEdit.doSignature("Signature_Pad");

			Thread.sleep(6000);

			Browser.WebButton.waittillvisible("signatureSave");
			Result.fUpdateLog("Signature Captured");
			// Clicking On Save Option.
			Browser.WebButton.click("signatureSave");
			Thread.sleep(2000);

			// Scroll to Validate
			CO.scroll("Validate", "WebButton");
			Result.takescreenshot("Signature Captured");
			Result.fUpdateLog("Signature Captured");

			// Clicking on Validate.
			Thread.sleep(3000);
			Method.Highlight("Validate", "WebButton");
			CO.JavaScriptClick("Validate", "WebButton");
			Thread.sleep(10000);
			
			// Clicking on Draft eFrom.
			Method.Highlight("DrafteForm_Btn", "WebButton");
			Browser.WebButton.click("DrafteForm_Btn");
			Result.takescreenshot("Clicked On Draft eForm");
			Result.fUpdateLog("Clicking On Draft eForm");
			Thread.sleep(20000);
		    CO.isAlertExist();
			// Fetching Order Number.
			ordernum = Browser.WebEdit.gettext("order_numfetch");
			Result.fUpdateLog("Current Order Number " + ordernum);
			//Result.takescreenshot("Current Order Number " + ordernum);
		
			// Clicking on Submit button.
			CO.scroll("SubmitBtn", "WebButton");
			Method.Highlight("SubmitBtn", "WebButton");
			Browser.WebButton.click("SubmitBtn");
			Thread.sleep(10000);
		    CO.isAlertExist();
		
			
			Browser.WebButton.click("SubmitBtn");
		    CO.isAlertExist();
		    
			Result.fUpdateLog(" Clicking on Submit button.");
			Result.takescreenshot("Clicking on Submit button.");
			Thread.sleep(20000);

			// ************   Account Summary View    ***************
	 	
			
			// Refresh Order Status
		    String OrderStatus = OrderRefresh(); 
		    
			// Scroll down to Lineitems
			CO.scroll("Line_Items", "WebButton");

			// Clicking on Line Items Tab
			Method.Highlight("Line_Items", "WebButton");
			Thread.sleep(1000);
			Browser.WebButton.click("Line_Items");
			
			Thread.sleep(5000);
			// Expand the Order tree icon to capture screenshot of Installed Assets*****************************
			Browser.WebButton.click("OrderExpandTreeIcon");
			Result.takescreenshot("Newly Installed Assets after Order Submission");
			Result.fUpdateLog("Newly Installed Assets after Order Submission");
			
			Thread.sleep(2000);
	 
			// Scroll to Account Summary
			CO.scroll("Acc_Summarybtn", "WebButton");
			Result.takescreenshot("After Refreshing the Browser Order Status: "+ OrderStatus );
			Result.fUpdateLog("After Refreshing the Browser Order Status: "+ OrderStatus );
		
			// Clicking on Account Summary button.
			Method.Highlight("Acc_Summarybtn", "WebButton");
			Browser.WebButton.click("Acc_Summarybtn");
			Result.fUpdateLog(" Clicking on Account Summary button");
			Result.takescreenshot(" Clicking on Account Summary button");

			Thread.sleep(3000);

			// Scroll to Account status Indicator
			CO.scroll("Accout_Status_Indicator", "WebEdit");
			
			Result.fUpdateLog("Account Summary Detail");
			Result.takescreenshot("Account Summary Detail");
			Thread.sleep(1000);

			// Scroll into Modify button.
			CO.scroll("Modify_button", "WebButton");
			Result.fUpdateLog("Installed Asset Detail");
  
			// ***************  MSISDN Verification for Change Number and SIM Swap **************
			if (getdata("orderreason").equals("Change Number") || getdata("orderreason").equals("SIM Swap")) {

				// fetching Existed MSISDN Number.
				Thread.sleep(3000);
				CO.scroll("FetchingServiceNo", "WebTable");
				Browser.WebTable.click("FetchingServiceNo", 2, 32);
				Thread.sleep(3000);
				fetch_MSISDNNumber = Browser.WebTable.getCellData("FetchingServiceNo", 2, 33);
				Thread.sleep(3000);

				// Scroll into Modify button.
				CO.scroll("Modify_button", "WebButton");
				System.out.println("Current MSISDN Number: " + fetch_MSISDNNumber);
				Result.fUpdateLog("Current MSISDN Number: " + fetch_MSISDNNumber);
				Result.takescreenshot("Current MSISDN Number: " + fetch_MSISDNNumber);
			}

			
			//a[@title='abcd'] || //a[@class='uioppp']
	

			// *************** selecting Primary check for Newly created Billing Profile **************
  
			if (getdata("orderreason").equals("Change Billing Profile")) {
				Thread.sleep(3000);

				// Scroll into Billing Profile.
				CO.scroll("BillingProfile_GO_btn", "WebButton");

				// Entering Billing Number
			    Browser.WebTable.click("BillingProfileAccountLevel", 2, 3);
			    Browser.WebTable.click("BillingProfileAccountLevel", 2, 3);
				Browser.WebTable.SetData("BillingProfileAccountLevel", 2, 3, "Profile_Name", NewBiilingProfileNumber);

				Result.fUpdateLog(" Entering Billing Number" + NewBiilingProfileNumber);
				Result.takescreenshot(" Entering Billing Number" + NewBiilingProfileNumber);

				// Clicking on Billing Profile GO button.
			    Browser.WebTable.click("BillingProfileAccountLevel", 2, 2);
			    
				// Clicking on Go button.
				Browser.WebButton.click("BillingProfile_GO_btn");
				Result.fUpdateLog("Clicking On Go button.");
				//Result.takescreenshot("Clicking On Go button.");
				Thread.sleep(4000);

				// clicking on Primary CheckBox.
				WebElement primaryCheck = cDriver.get()
						.findElement(By.xpath("//td[contains(@id,'_l_SSA_Primary_Field')]/span"));
				primaryCheck.click();
	
				// verify Primary CheckBox is checked or unchecked.
				boolean pcheck = cDriver.get().findElement(By.xpath("//span//input[@name='SSA_Primary_Field']"))
						.isSelected();
				if (pcheck) {
					// Nothing.
				} else {
					// check CheckBox AccountLevelPrimaryCheckBox
					cDriver.get().findElement(By.xpath("//span//input[@name='SSA_Primary_Field']")).click();
					Result.fUpdateLog("Primary check box");
					Result.takescreenshot("Primary check box");
				}

				// clicking on Billing Menu ICon.
				Browser.WebButton.click("AccountLevelBilling_MenuIcon");
				Result.fUpdateLog(" clicking on Billing Menu ICon");
				Thread.sleep(3000);

				// Save billing profile.
				Method.Highlight("BillProfile_Savebtn", "WebLink");
				Result.takescreenshot("save Primary check billing profile.");
				Browser.WebLink.click("BillProfile_Savebtn");
				Result.fUpdateLog(" Saving billing profile ");
				Thread.sleep(4000);
				Result.takescreenshot("Saving billing profile");

				// Refreshing the Order Status
				cDriver.get().navigate().refresh();
				Thread.sleep(9000);

				// Scroll to Account status Indicator
				CO.scroll("Accout_Status_Indicator", "WebEdit");
				Result.fUpdateLog("Account Summary Detail");
				Result.takescreenshot("Account Summary Detail");

				// Scroll into Modify button.
				CO.scroll("Modify_button", "WebButton");
				Result.fUpdateLog("Installed Asset Detail");
				Result.takescreenshot("Installed Asset Detail");
  
				Thread.sleep(3000);
   
				Message = NumberMessage + " Primary check for Newly created Billing Profile Number: "
						+ NewBiilingProfileNumber;
			} else {

				// Nothing.
			}
			// ************************************************************************************************************************
			
			
			System.out.println(
					"Account Number " + AccountNo + " ,Modify " + getdata("orderreason") + " Order Reason Done.");
   

			Result.fUpdateLog("********Fetching Order Number :   " + ordernum);
			String OrderDeatils = "Order Submitted successfully and Order ID:" + ordernum;
			Thread.sleep(10000);
			
			
			CO.ToWait();
			if (Continue.get()) {
				Test_OutPut += "Modify " + getdata("orderreason") + " Event Passed for " + "Account Number: " +AccountNo+", "+OrderDeatils+","+OrderPaymentStatus+"," + Message +" || Order Status:" +OrderStatus;
				Result.fUpdateLog("Modify " + getdata("orderreason") + " Event Passed for " + "Account Number: " +AccountNo+", "+OrderDeatils+","+OrderPaymentStatus+"," + Message+" || Order Status: "+OrderStatus);
				Result.takescreenshot("Modify " + getdata("orderreason") + " Event Passed for " + "Account Number: " +AccountNo+", "+OrderDeatils+","+OrderPaymentStatus+"," + Message+" || Order Status: "+OrderStatus );
				
				Status = "PASS";
				// Result.takescreenshot("Account Created Account_No : " + Account_No);
			} else {
				Test_OutPut += "Account Number " +AccountNo+",Modify " + getdata("orderreason") + " Creation Failed" + ",";
				Result.takescreenshot("Modify " + getdata("orderreason") + " Creation Failed");
				Status = "FAIL";
	       	}
   	
			
		} 
	
			catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();

		}
		
		Result.fUpdateLog("------ Modify Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}
	
	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: BatelcoUpgradePackage
	 * Arguments			: None
	 * Use 					: Upgrades From current Promotion to a Higher Promotion
	 * Designed By			: Soniya V
	 * Last Modified Date 	: 12-Nov-2019
	//---------------------------------------------------------------------------------------------------------*/
	public String BatelcoUpgradePackage() {

		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------Siebel UpgradePackage Event Details------");
		try {

			Account_search();

    		CO.waitforload();
			
			//Fetching the Account Number and Name
			CO.scroll("Account_Number", "WebEdit");
			Thread.sleep(5000);
			Utlities.StoreValue("Account_No", Browser.WebTable.getCellData("account_number", 2, 2));
			AccountNumber = Utlities.FetchStoredValue("ChangePromotion_Upgrade", "UpgradePackage",
					"Account_No");
			String AccountNo=Browser.WebEdit.gettext("Account_Number");
			String Account_Holder_Name=Browser.WebEdit.gettext("AccountHolder_Name");	
			
			// Scroll to Account status Indicator
			CO.scroll("Accout_Status_Indicator", "WebEdit");
	
	    	Result.takescreenshot("Account Number: "+AccountNo +" and Name: " +Account_Holder_Name);
		    Result.fUpdateLog("Account Number: "+AccountNo +" and Name: " +Account_Holder_Name);
			
			
			CO.scroll("Accounts_ChangePromotion", "WebButton");
			
			//**********want to take Installed Assets Screenshot here*****************************
			
			Result.takescreenshot("Installed Assets");
		    Result.fUpdateLog("Installed Assets");
			
			//Clicking on Change Promotion Tab
			Method.Highlight("Accounts_ChangePromotion", "WebButton");
			Result.takescreenshot("Clicked On Change Promotion");
			Result.fUpdateLog("Clicked On Change Promotion");
			Browser.WebButton.click("Accounts_ChangePromotion");
		
			//Entering the Order Journey
			Method.Highlight("OrderJourney", "WebEdit");
			Browser.WebEdit.Set("OrderJourney", getdata("OrderJourney"));
			Result.takescreenshot("Entered Upgrade as Order Journey Reason");
			Result.fUpdateLog("Entered Upgrade as Order Journey Reason");

			//Entering the Order Reason
			Method.Highlight("OrderReason", "WebEdit");
			Browser.WebEdit.Set("OrderReason", pulldata("OrderReason"));
			Result.takescreenshot("Entered Change Package as Order Reason ");
			Result.fUpdateLog("Entered Change Package as Order Reason ");

			//Clicking on OK
			Method.Highlight("ServiceType_Ok", "WebButton");
			CO.JavaScriptClick("ServiceType_Ok", "WebButton");

			CO.waitforload();
			//Clicking on Upgrade_search 
			Method.Highlight("PromotionUpgrade_Search", "WebButton");
			CO.JavaScriptClick("PromotionUpgrade_Search", "WebButton");
			Result.takescreenshot("Clicked On Search button");
			Result.fUpdateLog("Clicked On Search button");
          
			//Entering Upgrade Promotion name
			Method.Highlight("UpgradePackagename", "WebEdit");
			Browser.WebEdit.Set("UpgradePackagename", pulldata("UpgradePackagename"));
			Result.takescreenshot("Search the Upgrade Promotion: " + pulldata("UpgradePackagename"));
			Result.fUpdateLog("Search the Upgrade Promotion: " + pulldata("UpgradePackagename"));

		    //Clicking on Upgrade_Go 
			Method.Highlight("PromotionUpgrade_Go", "WebButton");
			CO.JavaScriptClick("PromotionUpgrade_Go", "WebButton");
			Result.takescreenshot("Clicked On Go button");
			Result.fUpdateLog("Clicked On Go button");

			//Clicking on OK button
			Method.Highlight("UpgradeOk", "WebButton");
			CO.JavaScriptClick("UpgradeOk", "WebButton");
			Result.takescreenshot("Clicked On Ok button");
			Result.fUpdateLog("Clicked On Ok button");
			//CO.waitforload();
			Thread.sleep(15000);
			
			Method.Highlight("OrderExpandTreeIcon", "WebButton");
			Browser.WebButton.click("OrderExpandTreeIcon");
			Result.takescreenshot("Upgrading Asset Detail");
			Result.fUpdateLog("Upgrading Asset Detail");

			//Clicking on Payments Tab
			CO.scroll("Payments", "WebButton");
			Method.Highlight("Payments", "WebButton");
			Result.takescreenshot("Clicked On Payments");
			Result.fUpdateLog("Clicked On Payments");
			Browser.WebButton.click("Payments");
			CO.waitforload(); 

			//Clicking on Create Payment
			Method.Highlight("Payment Lines:New", "WebButton");
			Browser.WebButton.click("Payment Lines:New");
			Result.takescreenshot("Clicked On Create Payments");
			Result.fUpdateLog("Clicked On Create Payments");

			//Clicking on Settings button
			Method.Highlight("SavePayment_Btn", "WebButton");
			Browser.WebButton.click("SavePayment_Btn");
			Browser.WebButton.click("SavePayment_Btn");

			Thread.sleep(1000);

			//Clicking on Payment Save
			// Method.Highlight("Payment_saverecord", "WebLink");
			Browser.WebLink.waittillvisible("Payment_saverecord");
			Browser.WebLink.click("Payment_saverecord");
			Result.takescreenshot("Clicked on Save Record");
			Result.fUpdateLog("Clicked on Save Record");

			//Clicking on validate
			// *[@aria-label='Sales Order:1-Validate']
			Browser.WebButton.click("Validate");
			Result.takescreenshot("Clicked On Validate");
			Result.fUpdateLog("Clicked On Validate");
			// *[contains(text(),'8-Submit')]
			
			CO.waitforload();
			// Clicking on Signature Capture
			CO.scroll("SignatureCapture_Btn", "WebButton");
			Browser.WebButton.waittillvisible("SignatureCapture_Btn");
			Browser.WebButton.click("SignatureCapture_Btn");
			CO.isAlertExist();

			// Perform Signature
			Thread.sleep(1000);

			// Browser.WebEdit.click("Signature_Pad");
			// CO.isAlertExist();
			Thread.sleep(5000);
			WebElement SigPad = cDriver.get()
					.findElement(By.xpath("//div[@class='mceGridField siebui-value mceField']//canvas"));
			SigPad.click();

			Browser.WebEdit.doSignature("Signature_Pad");
		    //Result.takescreenshot("Captured Signature");
		    //Result.fUpdateLog("Captured Signature");
			Thread.sleep(3000);
			Browser.WebButton.waittillvisible("signatureSave");
			Browser.WebButton.click("signatureSave");
			CO.isAlertExist();
			//Scroll  to signature Capture Checkbox
			CO.scroll("DrafteForm_Btn", "WebButton");
			Result.takescreenshot("Signature Captured");
			Result.fUpdateLog("Signature Captured");
			
			// Clicking on Draft eFrom.
			Method.Highlight("DrafteForm_Btn", "WebButton");
			Browser.WebButton.click("DrafteForm_Btn");
			Result.takescreenshot("Clicked On Draft eForm");
			Result.fUpdateLog("Clicked On Draft eForm");
			CO.waitforload();
			
			Thread.sleep(24000);
		    CO.isAlertExist();
		    
			// Fetching Order Number.
			ordernum = Browser.WebEdit.gettext("order_numfetch");
			Result.fUpdateLog("Current Order Number " + ordernum);
			//Result.takescreenshot("Current Order Number " + ordernum);
		
			// Clicking on Submit button.
			CO.scroll("SubmitBtn", "WebButton");
			Method.Highlight("SubmitBtn", "WebButton");
			Browser.WebButton.click("SubmitBtn");
			Thread.sleep(10000);
		    CO.isAlertExist();
		
			
			Browser.WebButton.click("SubmitBtn");
		    CO.isAlertExist();
			
			// Refresh order.
			String OrderStatus = OrderRefresh();
			
			//Scroll down to Lineitems
			CO.scroll("Line_Items", "WebButton");
			// Clicking on Line Items Tab
			Method.Highlight("Line_Items", "WebButton");
			Thread.sleep(1000);
			Browser.WebButton.click("Line_Items");
			Thread.sleep(1000);
			
			//Expand the Order tree icon to capture screenshot of Installed Assets*****************************
			Browser.WebButton.click("OrderExpandTreeIcon");
			
			Result.takescreenshot("Newly Installed Assets after Order Submission");
			Result.fUpdateLog("Newly Installed Assets after Order Submission");
			
			// Scroll to Account Summary
			CO.scroll("Acc_Summarybtn", "WebButton");
			Result.takescreenshot("After Refreshing the Browser Order Status: " + OrderStatus);
			Result.fUpdateLog("After Refreshing the Browser Order Status: " + OrderStatus);
			
		    // Clicking on Account Summary button.
			Method.Highlight("Acc_Summarybtn", "WebButton");
		    Browser.WebButton.click("Acc_Summarybtn");
		    Result.fUpdateLog(" Clicking on Account Summary button");	
			Result.takescreenshot(" Clicking on Account Summary button");
		    
			Thread.sleep(3000);
		
			// Scroll to Account status Indicator
			CO.scroll("Accout_Status_Indicator", "WebEdit");
		    Result.fUpdateLog("Account Summary Detail");
		    Result.takescreenshot("Account Summary Detail");
			Thread.sleep(1000);
		    
		    //Scroll into Modify button.
			CO.scroll("Modify_button", "WebButton");
		    Result.fUpdateLog("Installed Asset Detail");
		    Result.takescreenshot("Installed Asset Detail");			
			
			CO.ToWait();
			Thread.sleep(5000);
			Driver.Continue.set(true);
			Result.takescreenshot("UpgradePackage Successful");
			Result.fUpdateLog("UpgradePackage Successful");
			
			CO.ToWait();
			if (Continue.get()) {
				Test_OutPut += "Upgrade Package Event is Passed for Account Number: "+AccountNo +" and Order: "+ordernum +" is submitted Successfully || Order Status:" +OrderStatus;
				Result.takescreenshot(
						"Upgrade Package Event is Passed for Account Number: "+AccountNo +" and Order: "+ordernum +" is submitted Successfully || Order Status:" +OrderStatus);
				Result.fUpdateLog(
						"Upgrade Package Event is Passed for Account Number: "+AccountNo +" and Order: "+ordernum +" is submitted Successfully || Order Status:" +OrderStatus);
				Status = "PASS";

			} else {
				Test_OutPut += "UpgradePackage Failed" + ",";
				Result.takescreenshot("UpgradePackage Failed");
				Result.fUpdateLog("UpgradePackage Failed");
				Status = "FAIL";
			}

		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Exception occurred" + ",";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------Siebel UpgradePackage Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}
	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: BatelcoDowngradePackage
	 * Arguments			: None
	 * Use 					: Downgrades From current Promotion to a Lower Promotion
	 * Designed By			: Soniya V
	 * Last Modified Date 	: 04-Dec-2019
	//---------------------------------------------------------------------------------------------------------*/
	public String BatelcoDowngradePackage() {

		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------Siebel DowngradePackage() Event Details------");
		try {
			
			Account_search();

			CO.waitforload();
			
			//Fetching the Account Number and Name
			CO.scroll("Account_Number", "WebEdit");
			Thread.sleep(5000);
			//store account number
			Utlities.StoreValue("Account_No", Browser.WebTable.getCellData("account_number", 2, 2));
			
			AccountNumber = Utlities.FetchStoredValue("ChangePromotion_Downgrade", "DowngradePackage",
					"Account_No");
			
			
			String AccountNo=Browser.WebEdit.gettext("Account_Number");
			String Account_Holder_Name=Browser.WebEdit.gettext("AccountHolder_Name");	
			
			// Scroll to Account Status Indicator
			CO.scroll("Accout_Status_Indicator", "WebEdit");
	
	    	Result.takescreenshot("Account Number: "+AccountNo +" and Name: " +Account_Holder_Name);
		    Result.fUpdateLog("Account Number: "+AccountNo +" and Name: " +Account_Holder_Name);
			
		    //Scroll to ChangePromotion Tab.
			CO.scroll("Accounts_ChangePromotion", "WebButton");
			Result.takescreenshot("Installed Assets");
		    Result.fUpdateLog("Installed Assets");
		    
			//Clicking on Change Promotion Tab
			Method.Highlight("Accounts_ChangePromotion", "WebButton");
			Result.takescreenshot("Clicked On Change Promotion");
			Result.fUpdateLog("Clicked On Change Promotion");		
			Browser.WebButton.click("Accounts_ChangePromotion");
			
			//Entering the Order Journey
			Method.Highlight("OrderJourney", "WebEdit");
			Browser.WebEdit.Set("OrderJourney", getdata("OrderJourney"));
			Result.takescreenshot("Entered Upgrade as OrderJourney Reason");
			Result.fUpdateLog("Entered Upgrade as OrderJourney Reason");

			//Entering the Order Reason
			Method.Highlight("OrderReason", "WebEdit");
			Browser.WebEdit.Set("OrderReason", pulldata("OrderReason"));
			Result.takescreenshot("Entered Change Package as Order Reason ");
			Result.fUpdateLog("Entered Change Package as Order Reason ");

			//Clicking on OK
			Method.Highlight("ServiceType_Ok", "WebButton");
			CO.JavaScriptClick("ServiceType_Ok", "WebButton");

			CO.waitforload();
			//Clicking on Upgrade_search 
			Method.Highlight("PromotionUpgrade_Search", "WebButton");
			CO.JavaScriptClick("PromotionUpgrade_Search", "WebButton");
			Result.takescreenshot("Clicked On Search button");
			Result.fUpdateLog("Clicked On Search button");
			
			//Entering Downgrade Promotion name
			Method.Highlight("UpgradePackagename", "WebEdit");
			Browser.WebEdit.Set("UpgradePackagename", pulldata("UpgradePackagename"));
			Result.takescreenshot("Search the Upgrade Promotion" + pulldata("UpgradePackagename"));
			Result.fUpdateLog("Search the Upgrade Promotion" + pulldata("UpgradePackagename"));

			//Clicking on Downgrade_Go 
			Method.Highlight("PromotionUpgrade_Go", "WebButton");
			CO.JavaScriptClick("PromotionUpgrade_Go", "WebButton");
			Result.takescreenshot("Clicked On Go button");
			Result.fUpdateLog("Clicked On Go button");

			//Clicking on OK button
			Method.Highlight("UpgradeOk", "WebButton");
			CO.JavaScriptClick("UpgradeOk", "WebButton");
			Result.takescreenshot("Clicked On Ok button");
			Result.fUpdateLog("Clicked On Ok button");

			Thread.sleep(16000);
			CO.waitforload();
			
			Method.Highlight("OrderExpandTreeIcon", "WebButton");
			Browser.WebButton.click("OrderExpandTreeIcon");
			Result.takescreenshot("Downgrading Asset Detail");
			Result.fUpdateLog("Downgrading Asset Detail");
	        //CO.waitforload();
			
			//Clicking on Payments Tab
			CO.scroll("Payments", "WebButton");
			Method.Highlight("Payments", "WebButton");
			Result.takescreenshot("Clicked On Payments");
			Result.fUpdateLog("Clicked On Payments");
			Browser.WebButton.click("Payments");
			CO.waitforload(); 

			//Clicking on Create Payment
			Method.Highlight("Payment Lines:New", "WebButton");
			Browser.WebButton.click("Payment Lines:New");
			Result.takescreenshot("Clicked On Create Payments");
			Result.fUpdateLog("Clicked On Create Payments");

			//Clicking on Settings button
			Method.Highlight("SavePayment_Btn", "WebButton");
			Browser.WebButton.click("SavePayment_Btn");
			//Browser.WebButton.click("SavePayment_Btn");

			Thread.sleep(1000);

			//Clicking on Payment Save
			// Method.Highlight("Payment_saverecord", "WebLink");
			Browser.WebLink.waittillvisible("Payment_saverecord");
			Browser.WebLink.click("Payment_saverecord");
			Result.takescreenshot("Clicked on Save Record");
			Result.fUpdateLog("Clicked on Save Record");

			//Clicking on validate
			Browser.WebButton.click("Validate");
			Result.takescreenshot("Clicked On Validate");
			Result.fUpdateLog("Clicked On Validate");

			
			CO.waitforload();
			// Clicking on Signature Capture
			CO.scroll("SignatureCapture_Btn", "WebButton");
			Browser.WebButton.waittillvisible("SignatureCapture_Btn");
			Browser.WebButton.click("SignatureCapture_Btn");
			CO.isAlertExist();

			// Perform Signature
			Thread.sleep(1000);

			// Browser.WebEdit.click("Signature_Pad");
			// CO.isAlertExist();
			Thread.sleep(5000);
			WebElement SigPad = cDriver.get()
					.findElement(By.xpath("//div[@class='mceGridField siebui-value mceField']//canvas"));
			SigPad.click();

			Browser.WebEdit.doSignature("Signature_Pad");
			Result.fUpdateLog("Captured Signature");
			Thread.sleep(3000);
			Browser.WebButton.waittillvisible("signatureSave");
			Browser.WebButton.click("signatureSave");
			CO.isAlertExist();
			//Scroll  to signature Capture Checkbox
			CO.scroll("DrafteForm_Btn", "WebButton");
			Result.takescreenshot("Signature Captured");
			Result.fUpdateLog("Signature Captured");
			
			// Clicking on Draft eFrom.
			Method.Highlight("DrafteForm_Btn", "WebButton");
			Browser.WebButton.click("DrafteForm_Btn");
			Result.takescreenshot("Clicked On DrafteForm");
			Result.fUpdateLog("Clicked On DrafteForm");
			CO.waitforload();
			
			Thread.sleep(24000);
		    CO.isAlertExist();
		    
			// Fetching Order Number.
			ordernum = Browser.WebEdit.gettext("order_numfetch");
			Result.fUpdateLog("Current Order Number " + ordernum);
			//Result.takescreenshot("Current Order Number " + ordernum);
		
			// Clicking on Submit button.
			CO.scroll("SubmitBtn", "WebButton");
			Method.Highlight("SubmitBtn", "WebButton");
			Browser.WebButton.click("SubmitBtn");
			Thread.sleep(10000);
		    CO.isAlertExist();
		
			
			Browser.WebButton.click("SubmitBtn");
		    CO.isAlertExist();
		    
	
			// Refresh order.
			String OrderStatus = OrderRefresh();
			
			// Scroll down to Lineitems
			CO.scroll("Line_Items", "WebButton");

			// Clicking on Line Items Tab
			Method.Highlight("Line_Items", "WebButton");
			Thread.sleep(1000);
			Browser.WebButton.click("Line_Items");
			Thread.sleep(5000);
			
			// Expand the Order tree icon to capture screenshot of Installed Assets*****************************
			Browser.WebButton.click("OrderExpandTreeIcon");
			
			Result.takescreenshot("Newly Installed Assets after Order Submission");
			Result.fUpdateLog("Newly Installed Assets after Order Submission");
			Thread.sleep(2000);

			// Scroll to Account Summary
			CO.scroll("Acc_Summarybtn", "WebButton");
			Result.takescreenshot("After Refreshing the Browser Order Status: " + OrderStatus);
			Result.fUpdateLog("After Refreshing the Browser Order Status: " + OrderStatus);

			// Clicking on Account Summary button.
			Method.Highlight("Acc_Summarybtn", "WebButton");
			Browser.WebButton.click("Acc_Summarybtn");
			Result.fUpdateLog(" Clicking on Account Summary button");
			Result.takescreenshot(" Clicking on Account Summary button");

			Thread.sleep(3000);
		
			// Scroll to Account status Indicator
			CO.scroll("Accout_Status_Indicator", "WebEdit");
		    Result.fUpdateLog("Account Summary Detail");
		    Result.takescreenshot("Account Summary Detail");
			Thread.sleep(1000);
		    
		  //Scroll into Modify button.
			CO.scroll("Modify_button", "WebButton");
		    Result.fUpdateLog("Installed Asset Detail");
		    Result.takescreenshot("Installed Asset Detail");
			
			
			CO.ToWait();
			
			Thread.sleep(5000);
			Driver.Continue.set(true);
			Result.takescreenshot("Downgrade Package Successful");
			Result.fUpdateLog("Downgrade Package Successful");
			CO.ToWait();
			if (Continue.get()) {
				Test_OutPut += "Downgrade Package Event is Passed for Account Number: "+AccountNo +" and Order: "+ordernum +" is submitted Successfully || Order Status "+OrderStatus;
				Result.takescreenshot(
						"Downgrade Package Event is Passed for Account Number: "+AccountNo +" and Order: "+ordernum +" is submitted Successfully");
				Result.fUpdateLog("Downgrade Package Event is Passed for Account Number: "+AccountNo +" and Order: "+ordernum +" is submitted Successfully ||Order Status "+OrderStatus);
			
				Status = "PASS";

			} else {
				Test_OutPut += "Downgrade Package Failed" + ",";
				Result.takescreenshot("Downgrade Package Failed");
				Result.fUpdateLog("Downgrade Package Failed");
				Status = "FAIL";
			}

		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Exception occurred" + ",";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------Siebel DowngradePackage Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}
	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: BatelcoPostToPreMigration
	 * Arguments			: None
	 * Use 					: Migrates From Postpaid to Prepaid
	 * Designed By			: Soniya V
	 * Last Modified Date 	: 12-Nov-2019
	//---------------------------------------------------------------------------------------------------------*/
	public String BatelcoPostToPreMigration() {

		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------Siebel PostToPreMigration Event Details------");
		try {
			Thread.sleep(6000);
			
			Account_search();
			
			Thread.sleep(1000);
			
			//Fetching the Account Number and Name
			CO.scroll("Account_Number", "WebEdit");
			Thread.sleep(6000);
			
			Utlities.StoreValue("Account_No", Browser.WebTable.getCellData("account_number", 2, 2));
			AccountNumber = Utlities.FetchStoredValue("Migration_PostToPre", "PostpaidToPrepaid",
					"Account_No");

			String AccountNo=Browser.WebEdit.gettext("Account_Number");
			String Account_Holder_Name=Browser.WebEdit.gettext("AccountHolder_Name");	
			
			// Scroll to Account status Indicator
			CO.scroll("Accout_Status_Indicator", "WebEdit");
	
	    	Result.takescreenshot("Account Number: "+AccountNo +" and Name: " +Account_Holder_Name);
		    Result.fUpdateLog("Account Number: "+AccountNo +" and Name: " +Account_Holder_Name);
		    
		    //Scroll to ChangePromotion Tab.
			CO.scroll("Accounts_ChangePromotion", "WebButton");
			Result.takescreenshot("Installed Assets");
		    Result.fUpdateLog("Installed Assets");
		    
			//Clicking on Change Promotion Tab
			Method.Highlight("Accounts_ChangePromotion", "WebButton");
			Result.takescreenshot("Clicked On Change Promotion");
			Result.fUpdateLog("Clicked On Change Promotion");
			Browser.WebButton.click("Accounts_ChangePromotion");
		
			//Entering the Order Journey
			Method.Highlight("OrderJourney", "WebEdit");
			Browser.WebEdit.Set("OrderJourney", pulldata("OrderJourney"));
			Result.takescreenshot("Entered Downgrade as Order Journey Reason");
			Result.fUpdateLog("Entered Downgrade as Order Journey Reason");
			
			//Entering the Order Reason
			Method.Highlight("OrderReason", "WebEdit");
			Browser.WebEdit.Set("OrderReason", pulldata("OrderReason"));
			Result.takescreenshot("Entered Post to Pre as Order Reason ");
			Result.fUpdateLog("Entered Post to Pre as Order Reason ");
			
			//Clicking on Order OK
			Method.Highlight("ServiceType_Ok", "WebButton");
			CO.JavaScriptClick("ServiceType_Ok", "WebButton");
			Result.takescreenshot("Clicked On Ok button");
			Result.fUpdateLog("Clicked On Ok button");
			CO.waitforload();
			
			
			
			// Clicking on Go button.
			Browser.WebButton.click("PromationUpgrade_GO_btn");
			
			//Clicking on OK button
			Method.Highlight("UpgradeOk", "WebButton");
			CO.JavaScriptClick("UpgradeOk", "WebButton");
			Thread.sleep(20000);
			
		   	
			try {
				if(cDriver.get().findElement(By.xpath("//button[@title='Promotion Upgrades:OK']")).isDisplayed()) {
				CO.JavaScriptClick("UpgradeOk", "WebButton");
				//Thread.sleep(15000);
			}else {
				// 
			}
			}catch(Exception e) {
			
				System.out.println(e);
			}  
			
			CO.waitforload();
	
			Result.takescreenshot("Clicked On Downgrade ");
			Result.fUpdateLog("Clicked On Downgrade");
			Thread.sleep(5000);
		
			// clicking Tree icon to enable query number
			// Method.Highlight("ChangePackageExpandTreeIcon", "WebButton");
			//Browser.WebButton.click("ChangePackageExpandTreeIcon");
			 
			Browser.WebTable.clickMigrationExpTreeIcon("Productselection_table", 6, 1);
			
			Result.takescreenshot("Clicked On Change Package Expand Tree Icon");
			Result.fUpdateLog("Clicked On Change Package Expand Tree Icon");
		
			Thread.sleep(4000);
			
			Browser.WebTable.SetDataE("Productselection_table", 7, 6, "Service_Id",getdata("SimNo"));

			Thread.sleep(2000);
			Result.takescreenshot("Entered SIM Number"+getdata("SimNo"));
			Result.fUpdateLog("Entered SIM Number"+getdata("SimNo"));
			
			// Scrolling up
			CO.scroll("OrderMenu_Btn", "WebButton");
			Browser.WebButton.waittillvisible("OrderMenu_Btn");

			// Clicking On Setting Button
			Browser.WebButton.click("OrderMenu_Btn");
			Thread.sleep(1000);

			// Saving the Order Creation
			Method.Highlight("SaveRecord", "WebLink");
			Browser.WebLink.waittillvisible("SaveRecord");
			Browser.WebLink.click("SaveRecord");
			Result.takescreenshot("Clicked On Save Record");
			Result.fUpdateLog("Clicked On Save Record");
			Thread.sleep(4000);
			
			//*******   Payment  ************** .
			String OrderPaymentStatus =Payment();
						
			//Clicking on validate
			Browser.WebButton.click("Validate");
			Result.takescreenshot("Clicked On Validate");
			Result.fUpdateLog("Clicked On Validate");
			
			CO.waitforload();
			// Clicking on Signature Capture
			CO.scroll("SignatureCapture_Btn", "WebButton");
			Browser.WebButton.waittillvisible("SignatureCapture_Btn");
			Browser.WebButton.click("SignatureCapture_Btn");
			CO.isAlertExist();

			// Perform Signature
			Thread.sleep(1000);
			
			// Browser.WebEdit.click("Signature_Pad");
			// CO.isAlertExist();
			Thread.sleep(5000);
			WebElement SigPad = cDriver.get()
					.findElement(By.xpath("//div[@class='mceGridField siebui-value mceField']//canvas"));
			SigPad.click();

			Browser.WebEdit.doSignature("Signature_Pad");
			
			Thread.sleep(3000);
			Browser.WebButton.waittillvisible("signatureSave");
			Browser.WebButton.click("signatureSave");
			CO.isAlertExist();
			//Scroll  to signature Capture Check Box
			CO.scroll("DrafteForm_Btn", "WebButton");
			Result.takescreenshot("Signature Captured");
			Result.fUpdateLog("Signature Captured");
			
			// Clicking on Draft eFrom.
			Method.Highlight("DrafteForm_Btn", "WebButton");
			Browser.WebButton.click("DrafteForm_Btn");
			Result.takescreenshot("Clicked On DrafteForm");
			Result.fUpdateLog("Clicked On DrafteForm");
			
	//		Thread.sleep(24000);
		    CO.isAlertExist();
		    
			// Clicking on Submit button.
			CO.scroll("SubmitBtn", "WebButton");
			Method.Highlight("SubmitBtn", "WebButton");
			Browser.WebButton.click("SubmitBtn");
			Thread.sleep(10000);
		    CO.isAlertExist();
		
			
			Browser.WebButton.click("SubmitBtn");
		    CO.isAlertExist();
		
			
			// Refresh order.
			String OrderStatus = OrderRefresh();
			
			// Scroll down to Lineitems
			CO.scroll("Line_Items", "WebButton");

			// Clicking on Line Items Tab
			Method.Highlight("Line_Items", "WebButton");
			Thread.sleep(1000);
			Browser.WebButton.click("Line_Items");
			Thread.sleep(5000);
			
			// Expand the Order tree icon to capture screenshot of Installed Assets*****************************
			Browser.WebButton.click("OrderExpandTreeIcon");
			
			Result.takescreenshot("Newly Installed Assets after Order Submission");
			Result.fUpdateLog("Newly Installed Assets after Order Submission");
			Thread.sleep(2000);

			// Scroll to Account Summary
			CO.scroll("Acc_Summarybtn", "WebButton");
			Result.takescreenshot("After Refreshing the Browser Order Status: " + OrderStatus);
			Result.fUpdateLog("After Refreshing the Browser Order Status: " + OrderStatus);

			// Clicking on Account Summary button.
			Method.Highlight("Acc_Summarybtn", "WebButton");
			Browser.WebButton.click("Acc_Summarybtn");
			Result.fUpdateLog(" Clicking on Account Summary button");
			Result.takescreenshot(" Clicking on Account Summary button");

			Thread.sleep(3000);
		
			// Scroll to Account status Indicator
			CO.scroll("Accout_Status_Indicator", "WebEdit");
		    Result.fUpdateLog("Account Summary Detail");
		    Result.takescreenshot("Account Summary Detail");
			Thread.sleep(1000);
		    
		  //Scroll into Modify button.
			CO.scroll("Modify_button", "WebButton");
		    Result.fUpdateLog("Installed Asset Detail");
		    Result.takescreenshot("Installed Asset Detail");
			Thread.sleep(3000);
		
			
			
			CO.ToWait();

			//Thread.sleep(5000);
			Driver.Continue.set(true);
			Result.takescreenshot("BatelcoPostToPreMigration Successful");
			Result.fUpdateLog("BatelcoPostToPreMigration Successful");
			CO.ToWait();

			if (Continue.get()) {
				Test_OutPut += "PostToPreMigration Event is Passed for Account Number: "+AccountNo +" and Order: "+ordernum +" is submitted Successfully and New Prepaid Sim Number "+getdata("SimNo")+" Updated Successfully and "+OrderPaymentStatus+" "+OrderStatus+"";
				Result.takescreenshot(
						"PostToPreMigration Event is Passed for Account Number: "+AccountNo +" and  Order: "+ordernum +" is submitted Successfully and New Prepaid Number "+getdata("SimNo")+" Updated Successfully and "+OrderPaymentStatus+" "+OrderStatus+"");
				Result.fUpdateLog(
						"PostToPreMigration Event is Passed for Account Number: "+AccountNo +" and  Order: "+ordernum +" is submitted Successfully and New Prepaid Number "+getdata("SimNo")+" Updated Successfully and "+OrderPaymentStatus+" "+OrderStatus+"");
				Status = "PASS";

			} else {
				Test_OutPut += "PostToPreMigration Failed" + ",";
				Result.takescreenshot("PostToPreMigration Failed");
				Result.fUpdateLog("PostToPreMigration Failed");
				Status = "FAIL";
			}

		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Exception occurred" + ",";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------Siebel PostToPreMigration Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: BatelcoPreToPostMigration
	 * Arguments			: None
	 * Use 					: Migrates From Prepaid to Postpaid
	 * Designed By			: Soniya V
	 * Last Modified Date 	: 04-Dec-2019
	//---------------------------------------------------------------------------------------------------------*/
	public String BatelcoPreToPostMigration() {

		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------Siebel PreToPostMigration Event Details------");
		try {

			Account_search();
			
			//Fetching the Account Number and Name

			// Fetching Account Number
			CO.scroll("Account_Number", "WebEdit");
			Thread.sleep(5000);
			Utlities.StoreValue("Account_No", Browser.WebTable.getCellData("account_number", 2, 2));
			AccountNumber = Utlities.FetchStoredValue("Migration_PreToPost", "PrepaidToPostpaid",
					"Account_No");
			String AccountNo=Browser.WebEdit.gettext("Account_Number");
			String Account_Holder_Name=Browser.WebEdit.gettext("AccountHolder_Name");	
	
			// Scroll to Account status Indicator
			CO.scroll("Accout_Status_Indicator", "WebEdit");
			
	    	Result.takescreenshot("Account Number: "+AccountNo +" and Name: " +Account_Holder_Name);
		    Result.fUpdateLog("Account Number: "+AccountNo +" and Name: " +Account_Holder_Name);
			
		    //Scroll to ChangePromotion Tab.
			CO.scroll("Accounts_ChangePromotion", "WebButton");
			Result.takescreenshot("Installed Assets");
		    Result.fUpdateLog("Installed Assets");
		    
			//Clicking on Change Promotion Tab
			Method.Highlight("Accounts_ChangePromotion", "WebButton");
			Browser.WebButton.click("Accounts_ChangePromotion");
			Result.takescreenshot("Clicked On Change Promotion");
			Result.fUpdateLog("Clicked On Change Promotion");
			
			//Entering the Order Journey
			Method.Highlight("OrderJourney", "WebEdit");
			Browser.WebEdit.Set("OrderJourney", pulldata("OrderJourney"));
			Result.takescreenshot("Entered Downgrade as OrderJourney Reason");
			Result.fUpdateLog("Entered Downgrade as OrderJourney Reason");

			//Entering the Order Reason
			Method.Highlight("OrderReason", "WebEdit");
			Browser.WebEdit.Set("OrderReason", pulldata("OrderReason"));
			Result.takescreenshot("Entered Post to Pre Migration as Order Reason ");
			Result.fUpdateLog("Entered Post to Pre Migration as Order Reason ");
			//Clicking on Order OK
			Method.Highlight("ServiceType_Ok", "WebButton");
			CO.JavaScriptClick("ServiceType_Ok", "WebButton");
			Result.takescreenshot("Clicked on OK button");
			Result.fUpdateLog("Clicked on OK button");
			CO.waitforload();
			//Clicking on Upgrade_search 
			Method.Highlight("PromotionUpgrade_Search", "WebButton");
			CO.JavaScriptClick("PromotionUpgrade_Search", "WebButton");
			Result.takescreenshot("Clicked On Search button");
			Result.fUpdateLog("Clicked On Search button");

			//Entering Upgrade Promotion name
			Method.Highlight("UpgradePackagename", "WebEdit");
			Browser.WebEdit.Set("UpgradePackagename", pulldata("UpgradePackagename"));
			Result.takescreenshot("Search the Upgrade Promotion" + pulldata("UpgradePackagename"));
			Result.fUpdateLog("Search the Upgrade Promotion" + pulldata("UpgradePackagename"));

			//Clicking on Upgrade_Go 
			Method.Highlight("PromotionUpgrade_Go", "WebButton");
			CO.JavaScriptClick("PromotionUpgrade_Go", "WebButton");
			Result.takescreenshot("Cliked On PromotionUpgrade_Go button");
			Result.fUpdateLog("Cliked On PromotionUpgrade_Go button");

			//Clicking on OK button
			Method.Highlight("UpgradeOk", "WebButton");
			CO.JavaScriptClick("UpgradeOk", "WebButton");
			Result.takescreenshot("Cliked On UpgradeOk button");
			Result.fUpdateLog("Cliked On UpgradeOk button");
			Thread.sleep(5000);
			/*
			 * // Entering Contact Number Browser.WebTable.click("Billingprofile_table", 2,
			 * 9); if (!(getdata("BillingProfile_ContactNumber").equals(""))) {
			 * Browser.WebTable.SetDataE("Billingprofile_table", 2, 9,
			 * "BT_Primary_Contact_Phone_Number", getdata("BillingProfile_ContactNumber"));
			 * } else {
			 * 
			 * Browser.WebTable.SetDataE("Billingprofile_table", 2, 9,
			 * "BT_Primary_Contact_Phone_Number", pulldata("BillingProfile_ContactNumber"));
			 * }
			 * 
			 * // Entering Email Id if (!(getdata("BillingProfile_eMail").equals(""))) {
			 * Browser.WebTable.SetDataE("Billingprofile_table", 2, 10, "BT_eMail",
			 * getdata("BillingProfile_eMail")); } else {
			 * Browser.WebTable.SetDataE("Billingprofile_table", 2, 10, "BT_eMail",
			 * pulldata("BillingProfile_eMail")); }
			 * 
			 * Thread.sleep(3000);
			 * 
			 * Browser.WebTable.waittillvisible("Billingprofile_table");
			 * 
			 * // Entering Additional Payments if
			 * (!(getdata("BillingProfile_PaymentMethod").equals(""))) {
			 * Browser.WebTable.SetDataE("Billingprofile_table", 2, 26,
			 * "BT_Additional_Payment_Method", getdata("BillingProfile_PaymentMethod")); }
			 * else {
			 * 
			 * Browser.WebTable.SetDataE("Billingprofile_table", 2, 26,
			 * "BT_Additional_Payment_Method", pulldata("BillingProfile_PaymentMethod")); }
			 * 
			 * Thread.sleep(2000);
			 */

			//Clicking on Associate Button
			Browser.WebButton.click("Associate_button");
			Thread.sleep(3000);

			//Adding a line Item
			Browser.WebButton.click("Line_Items");
			Result.takescreenshot("Cliked On Line_Items Tab");
			Result.fUpdateLog("Cliked On Line_Items Tab");
			Thread.sleep(10000);

			//Clicking Tree icon to enable query number
			Method.Highlight("MigrationExpandTreeIcon", "WebButton");
			Browser.WebButton.click("MigrationExpandTreeIcon");
			Result.takescreenshot("Cliked On MigrationExpandTreeIcon");
			Result.fUpdateLog("Cliked On MigrationExpandTreeIcon");
			
			//Entering SIM Number
			Browser.WebTable.SetData("Productselection_table", 7, 7, "Service_Id", getdata("SimNo"));
			Result.takescreenshot("Entered SIM Number");
			Result.fUpdateLog("Entered SIM Number");
			// Scrolling up
			CO.scroll("OrderMenu_Btn", "WebButton");
			Browser.WebButton.waittillvisible("OrderMenu_Btn");

			// Clicking On Setting Button
			Browser.WebButton.click("OrderMenu_Btn");
			Result.takescreenshot("Cliked On OrderMenu_Btn");
			Result.fUpdateLog("Cliked On OrderMenu_Btn");
			Thread.sleep(1000);

			// Saving the Order Creation
			Method.Highlight("SaveRecord", "WebLink");
			Browser.WebLink.waittillvisible("SaveRecord");
			Browser.WebLink.click("SaveRecord");
			Result.takescreenshot("Clicked On Save Record");
			Result.fUpdateLog("Clicked On Save Record");
			Thread.sleep(4000);

			//Clicking on Payments
			CO.scroll("Payments", "WebButton");
			Method.Highlight("Payments", "WebButton");
			Result.takescreenshot("Clicked On Payments");
			Result.fUpdateLog("Clicked On Payments");
			Browser.WebButton.click("Payments");
			CO.waitforload();

			//Clicking on Create Payments
			Method.Highlight("Payment Lines:New", "WebButton");
			Browser.WebButton.click("Payment Lines:New");
			Result.takescreenshot("Clicked On Create Payments");
			Result.fUpdateLog("Clicked On Create Payments");
			
			
			

			

			//*/
			//Clicking on Setting
			Method.Highlight("SavePayment_Btn", "WebButton");
			Browser.WebButton.click("SavePayment_Btn");

			Thread.sleep(1000);

			//Clicking on Payment Save
			// Method.Highlight("Payment_saverecord", "WebLink");
			Browser.WebLink.waittillvisible("Payment_saverecord");
			Browser.WebLink.click("Payment_saverecord");
			Result.takescreenshot("Clicked On Save Record");
			Result.fUpdateLog("Clicked On Save Record");

			//Clicking on Validate
			Browser.WebButton.click("Validate");
			Result.takescreenshot("Clicked On Validate");
			Result.fUpdateLog("Clicked On Validate");
			
			CO.waitforload();
			// Clicking on Signature Capture
			CO.scroll("SignatureCapture_Btn", "WebButton");
			Browser.WebButton.waittillvisible("SignatureCapture_Btn");
			Browser.WebButton.click("SignatureCapture_Btn");
			CO.isAlertExist();

			// Perform Signature
			Thread.sleep(1000);
			// Browser.WebEdit.click("Signature_Pad");
			// CO.isAlertExist();
			Thread.sleep(5000);
			WebElement SigPad = cDriver.get()
					.findElement(By.xpath("//div[@class='mceGridField siebui-value mceField']//canvas"));
			SigPad.click();

			Browser.WebEdit.doSignature("Signature_Pad");
			Thread.sleep(3000);
			Browser.WebButton.waittillvisible("signatureSave");
			Browser.WebButton.click("signatureSave");
			CO.isAlertExist();
			//Scroll  to signature Capture Checkbox
			CO.scroll("DrafteForm_Btn", "WebButton");
			Result.takescreenshot("Signature Captured");
			Result.fUpdateLog("Signature Captured");
			
			// Clicking on Draft eFrom.
			Method.Highlight("DrafteForm_Btn", "WebButton");
			Browser.WebButton.click("DrafteForm_Btn");
			Result.takescreenshot("Clicked On DrafteForm");
			Result.fUpdateLog("Clicked On DrafteForm");
			
			Thread.sleep(3000);
			
			// Fetching Order Number
			ordernum = Browser.WebEdit.gettext("order_numfetch");
			System.out.println("Current Order Number "+ordernum);
			
			// Clicking on Submit button.
			CO.scroll("SubmitBtn", "WebButton");
			Method.Highlight("SubmitBtn", "WebButton");
			Browser.WebButton.click("SubmitBtn");
			Result.takescreenshot("Clicked On Submit");
			Result.fUpdateLog("Clicked On Submit");
		    
			// Refresh order.
			String OrderStatus = OrderRefresh();

			// Scroll down to Lineitems
			CO.scroll("Line_Items", "WebButton");
			// Clicking on Line Items Tab
			Method.Highlight("Line_Items", "WebButton");
			Thread.sleep(1000);
			Browser.WebButton.click("Line_Items");
			Thread.sleep(1000);

			// Expand the Order tree icon to capture screenshot of Installed
			// Assets*****************************
			Browser.WebButton.click("OrderExpandTreeIcon");

			Result.takescreenshot("Newly Installed Assets after Order Submission");
			Result.fUpdateLog("Newly Installed Assets after Order Submission");

			// Scroll to Account Summary
			CO.scroll("Acc_Summarybtn", "WebButton");
			Result.takescreenshot("After Refreshing the Browser Order Status: " + OrderStatus);
			Result.fUpdateLog("After Refreshing the Browser Order Status: " + OrderStatus);

			// Clicking on Account Summary button.
			Method.Highlight("Acc_Summarybtn", "WebButton");
			Browser.WebButton.click("Acc_Summarybtn");
			Result.fUpdateLog(" Clicking on Account Summary button");
			Result.takescreenshot(" Clicking on Account Summary button");

			Thread.sleep(3000);

			// Scroll to Account status Indicator
			CO.scroll("Accout_Status_Indicator", "WebEdit");
			Result.fUpdateLog("Account Summary Detail");
			Result.takescreenshot("Account Summary Detail");
			Thread.sleep(1000);

			// Scroll into Modify button.
			CO.scroll("Modify_button", "WebButton");
			Result.fUpdateLog("Installed Asset Detail");
			Result.takescreenshot("Installed Asset Detail");    
		    
			CO.ToWait();

			Thread.sleep(5000);
			Driver.Continue.set(true);
			Result.takescreenshot("BatelcoPreToPostMigration Successful");
			Result.fUpdateLog("BatelcoPreToPostMigration Successful");
			CO.ToWait();

			if (Continue.get()) {
				Test_OutPut += "PreToPostMigration Event is Passed for Account Number: "+AccountNo +" and  Order : "+ordernum +" is submitted Successfully";
				Result.takescreenshot(
						"PreToPostMigration Event is Passed for Account Number: "+AccountNo +" and  Order : "+ordernum +" is submitted Successfully");
				Result.fUpdateLog(
						"PreToPostMigration Event is Passed for Account Number: "+AccountNo +" and  Order : "+ordernum +" is submitted Successfully");
				Status = "PASS";

			} else {
				Test_OutPut += "PreToPostMigration Failed" + ",";
				Result.takescreenshot("PreToPostMigration Failed");
				Result.fUpdateLog("PreToPostMigration Failed");
				Status = "FAIL";
			}

		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Exception occurred" + ",";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------Siebel PreToPostMigration Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}
	/*--------------------------------------------------------------
	 * Method Name			: OrderRefresh
	 * Arguments			: None
	 * Use 					: To Refresh Order Page 
	 * Designed By			: Srikanth.D
	 * Last Modified Date 	: 17-Jan-2020
	//---------------------------------------------------------------------------------------------------------*/
	public String OrderRefresh() {
    	int Wait=0, count = 0, RefreshNumber=0;
		String Order_Status="", EStatus="Complete";
		Date d1 = null,d2=null,d3=null;
		Calendar cal_OrderStausSubmit=null,cal_OrderStausCompleted=null,cal_OrderTerminatedStatus=null;
		String Order_Submitted_Time="",Order_Completed_Time="",OrderTerminationTime="",Time_Message="", ORDER_TIME="";
		long diff=0;
    	
		try {
    	
			  SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
				
		        cal_OrderStausSubmit = Calendar.getInstance();
			    DateFormat Dformat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
			    Order_Submitted_Time= Dformat.format(cal_OrderStausSubmit.getTime());
			    System.out.println("Start Time"+Order_Submitted_Time);
				d1 = cal_OrderStausSubmit.getTime();
			
		do {
		  
			
			// To refresh Page
			cDriver.get().navigate().refresh();
			Thread.sleep(25000);
			
			// Scroll down to Lineitems
			CO.scroll("Line_Items", "WebButton");
			// Clicking on Line Items Tab
			CO.JavaScriptClick("Line_Items", "WebButton");
			Thread.sleep(5000);
			
			//Clicking on Setting icon.
			CO.scroll("SettingsButton", "WebButton");
			Method.Highlight("SettingsButton", "WebButton");
			Thread.sleep(2000);
			Browser.WebButton.click("SettingsButton");
			
			//Clicking on Run Query button.
			Method.Highlight("RunQuery", "WebButton");
			Browser.WebButton.click("RunQuery");
			Thread.sleep(5000);
			
			Order_Status = Browser.WebEdit.gettext("OrderStatus");
			Thread.sleep(3000);
			
			// To Find the Complete Status
			if (EStatus.equalsIgnoreCase(Order_Status)) {
				count = 0;
				
				    cal_OrderStausCompleted = Calendar.getInstance();
				    Order_Completed_Time= Dformat.format(cal_OrderStausCompleted.getTime());
					d2 = cal_OrderStausCompleted.getTime();
				    diff = d2.getTime() - d1.getTime();
				    System.out.println("Order Completion Time: "+Order_Completed_Time);
				    
				    Time_Message=" Order Completion Time: ";
				    
			} else {
				count++;
			}
		Wait = count;
		RefreshNumber=count;
		
		if(RefreshNumber == 8 || Order_Status.equalsIgnoreCase("Failed")) {
			
			    cal_OrderTerminatedStatus = Calendar.getInstance();
			    d3 = cal_OrderTerminatedStatus.getTime();
			    diff = d3.getTime() - d1.getTime();
			    
			    OrderTerminationTime= Dformat.format(cal_OrderTerminatedStatus.getTime());
			    System.out.println("Order Terminate Time: "+OrderTerminationTime);
			    Time_Message="Order Terminate Time: ";
			   
			break;
		}
		// CO.waitforload();
		} while (Wait != 0);

		
		long diffSeconds = diff / 1000 % 60;
		long diffMinutes = diff / (60 * 1000) % 60;

		System.out.print(diffMinutes + " minutes, ");
		System.out.print(diffSeconds + " seconds.");

             
         ORDER_TIME = " and"+Time_Message+" " +diffMinutes + " minutes, "+diffSeconds + " seconds.";
     	Result.fUpdateLog("ORDER TIME "+Time_Message+" " +diffMinutes + " minutes, "+diffSeconds + " seconds.");
	

    
    } catch(Exception e) {
    	System.out.println(e);
    }
		return "Order Status:"+Order_Status+" "+ORDER_TIME;
   }	
	
	/*--------------------------------------------------------------
	 * Method Name			: Payment
	 * Arguments			: None
	 * Use 					: to do Payment.
	 * Designed By			: Srikanth.D
	 * Last Modified Date 	: 20-Feb-2020
	//---------------------------------------------------------------------------------------------------------*/
	public String Payment() {

		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------Order Payment Event Details------");
		try {
			
			//*********             Fetching Order Number       ***************
			CO.scroll("order_numfetch","WebEdit");
			ordernum = Browser.WebEdit.gettext("order_numfetch");
			System.out.println("Current Order Number "+ordernum);

			//Storing the Total amount#
			String Order_Total = Browser.WebElement.gettext("Order_Total");
			String Total[] = Order_Total.split("BD");
			Double totalvalue = (Double.parseDouble(Total[1]));
			System.out.println("Order_Total: " + totalvalue);
			
			if(totalvalue == 0 )
			{
				
				// Storing the Monthly Charges Total#
				 Order_Total = Browser.WebElement.gettext("Monthly_Charges_Total");
				 System.out.println("entered if");
				 String Totalmc[] = Order_Total.split("BD");
				 totalvalue = (Double.parseDouble(Totalmc[1]));
			}

			// Amount Need to Pay.
			PaymentAmount=totalvalue;
			System.out.println(totalvalue);

			Thread.sleep(2000);
  	
			//**************     Payments  ****************************
			CO.scroll("Payments", "WebLink");
			Browser.WebLink.waittillvisible("Payments");
			// Method.Highlight("Payments","WebLink");
			Thread.sleep(3000);

			// Clicking on Payments Tab.
			Browser.WebLink.click("Payments");
			Thread.sleep(3000);

			// Clicking on Create Payments Button.
			// Method.Highlight("Payment Lines:New","WebButton");
			Browser.WebButton.click("Payment Lines:New");
			Result.fUpdateLog("Clicking on Create Payment");
			Result.takescreenshot("Clicking on Create Payment");
			Thread.sleep(1000);
			CO.isAlertExist();
			// Fetching Payment Status Value.
			String paymentstatus = Browser.WebTable.getCellData("PaymentsTable", 2, 10);
			Thread.sleep(5000);
			// Verifying Payment Status.
			try {

				if (paymentstatus.equalsIgnoreCase("Paid")) {
					Thread.sleep(3000);					
					System.out.println(paymentstatus);
					Result.fUpdateLog(" Payment Status: "+paymentstatus);
					Result.takescreenshot(" Payment Status: "+paymentstatus);
					System.out.println("******** status**********" + paymentstatus);
				} else {
					// Clicking On Make Payments.
					Browser.WebButton.click("MakePaymentBtn");
					
					//Step1:-  Run Query for getting OrderID and PaymentID From SIEBEL CRM DB.
					
					String Orderr_IDandPaymentID =  KDB.fechingOrderRowID(ordernum);
					
					String a[]=Orderr_IDandPaymentID.split(":");
					String ORDER_ID   = a[0];
					String Payment_ID = a[1];
					
					Payment_ORDERID =ORDER_ID;
					Payment_PAYMENTID =Payment_ID;		
					
					SOAP_CashRegister.Soap_RequestResponse();
				
					Thread.sleep(60000);
					
					Result.fUpdateLog("  ********** Waiting Time is 1 Minutes  **********   SOAP API CALL COMPLETED *********** ");

					cDriver.get().navigate().refresh();
					Thread.sleep(5000);
					cDriver.get().navigate().refresh();
					CO.waitforload();
					
					paymentstatus = Browser.WebTable.getCellData("PaymentsTable", 2, 10);
					System.out.println(paymentstatus);
					Result.fUpdateLog(" After Payment:"+paymentstatus);
					Result.takescreenshot(" After Payment: "+paymentstatus);
					System.out.println("******** status**********" + paymentstatus);					
					
					
				}
			} catch (Exception e) {
				paymentstatus = "blank";
			}			

			
			//Clicking on Payment save
			Method.Highlight("SavePayment_Btn", "WebButton");
			Browser.WebButton.click("SavePayment_Btn");

			Thread.sleep(1000);

			// Method.Highlight("Payment_saverecord", "WebLink");
			Browser.WebLink.waittillvisible("Payment_saverecord");
			Browser.WebLink.click("Payment_saverecord");
			Result.takescreenshot("Clicked On Save Record");
			Result.fUpdateLog("Clicked On Save Record");
		
		
        CO.ToWait();
		Thread.sleep(5000);
		Driver.Continue.set(true);
		
		CO.ToWait();
		if (Continue.get()) {
			Test_OutPut += "Order Payment is Successfully completed : ";				
			Result.fUpdateLog("Order Payment is Successfully completed ");
			Status = "PASS";
		} else {
			Test_OutPut += "Order Payment Failed" + ",";
			Result.takescreenshot("Order Payment Failed");
			Result.fUpdateLog("Order Payment Failed");
			Status = "FAIL";
		}
	

	} catch (Exception e) {
		Continue.set(false);
		Test_OutPut += "Exception occurred" + ",";
		Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
		Status = "FAIL";
		e.printStackTrace();
	}
	Result.fUpdateLog("------Order Payment Event Details - Completed------");
	return Test_OutPut;
}
	
	
	
	
	
}
