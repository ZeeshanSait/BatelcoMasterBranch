package Libraries;

import javax.xml.soap.SOAPMessage;

import org.openqa.selenium.WebDriver;

import mobileUtilities.*;

public class KeyWord {

	
	Keyword_API KA = new Keyword_API();
	Keyword_Validations KV = new Keyword_Validations();
	Keyword_Putty KP = new Keyword_Putty();
	Keyword_DB KDB = new Keyword_DB();
	Keyword_OSM KO = new Keyword_OSM();
	Dialers KDialers = new Dialers();
	SetCapabilities KSetCapabilities = new SetCapabilities();
	MobileRTBCheck KRTB = new MobileRTBCheck();
	Charging KCharging = new Charging();
	Notification KNotification = new Notification();
	Handset KHandset = new Handset();
	USSD KUSSD = new USSD();
	Siebel KSiebel = new Siebel();
	Keyword_SiebelCRM KBSiebel = new Keyword_SiebelCRM();
	static SOAP_CashRegister SOAP_C=new SOAP_CashRegister();
	
	// ------------------Siebel Keyword CRM -------------------//

	
	public String BatelcoSiebelLogin() {
		return KBSiebel.BatelcoSiebelLogin();
	}

	public String BatelcoSiebelLogout() {
		return KBSiebel.BatelcoSiebelLogout();
	}

	public String BatelcoAddressCreation() {
		return KBSiebel.BatelcoAddressCreation();
	}

	public String BatelcoAccCreation() {
		return KBSiebel.BatelcoAccCreation();
	}

	public String BatelcoContactCreation() {
		return KBSiebel.BatelcoContactCreation();
	}

	public String BatelcoServiceTypeCreation() {
		return KBSiebel.BatelcoServiceTypeCreation();
	}

	public String BatelcoOrderCreation() {
		return KBSiebel.BatelcoOrderCreation();
	}

	public String BTOrderPayments() {
		return KBSiebel.BTOrderPayments();
	}

	public String Batelco_ModifyService() {
		return KBSiebel.Batelco_ModifyService();
	}
	
	public String BatelcoUpgradePackage() {
		return KBSiebel.BatelcoUpgradePackage();
	}

	public String BatelcoDowngradePackage() {
		return KBSiebel.BatelcoDowngradePackage();
	}

	public String BatelcoPreToPostMigration() {
		return KBSiebel.BatelcoPreToPostMigration();
	}

	public String BatelcoPostToPreMigration() {
		return KBSiebel.BatelcoPostToPreMigration();
	}

	public String BatelcoSuspend() {
		return KBSiebel.BatelcoSuspend();
	}
	
	public String BT_Resume() {
		return KBSiebel.BT_Resume();
	}

	public String BT_Disconnection() {
		return KBSiebel.BT_Disconnection();
	}

	// ---------------------Keyword API------------------------//

	public static String Soap_RequestResponse() throws Exception {
		return SOAP_C.Soap_RequestResponse();
	}
	
	
	public String RTB_Login() {
		return KA.RTB_Login();
	}

	public String RTB() {
		return KA.RTB();
	}

	public String RTB_Check() {
		return KA.RTB_Check();
	}

	public String RTB_Compare() {
		return KA.RTB_Compare();
	}

	// ---------------------Keyword API------------------------//

	// ---------------------Keyword Validation------------------------//

	public String RTB_Validation() {
		return KV.RTB_Validation();
	}

	// ---------------------Keyword Validation------------------------//


	// ---------------------Keyword Putty------------------------//

	public String LoginSSH() {
		return KP.LoginSSH();
	}

	public String LogoutSSH() {
		return KP.LogoutSSH();
	}

	public String BillGeneration_AccountLevel() {
		return KP.BillGeneration_AccountLevel();
	}

	public String BillGeneration_BillingProfile() {
		return KP.BillGeneration_BillingProfile();
	}

	public String Invoicegeneration() {
		return KP.Invoicegeneration();
	}

	public String Trial_BillRun() {
		return KP.Trial_BillRun();
	}

	public String Collections() {
		return KP.Collections();
	}

	public String GetZipFile() {
		return KP.GetZipFile();
	}

	// ---------------------Keyword_FixedLine------------------------//

	// ---------------------Keyword_OSM------------------------//

	public String OSM_Login() {
		return KO.OSM_Login();
	}

	public String OSM_SearchFL() {
		return KO.OSM_SearchFL();
	}

	public String OSM_Pearl_data() {
		return KO.OSM_Pearl_data();
	}

	public String RePush_SOM() {
		return KO.RePush_SOM();
	}

	public String RePush_COM() {
		return KO.RePush_COM();
	}

	public String RePush_TOM() {
		return KO.RePush_TOM();
	}

	public String OSM_Logout() {
		return KO.OSM_Logout();
	}

	// ---------------------Keyword_OSM DB------------------------//
	public String OSM_COM_ORDER_Status() throws Exception {
		return KDB.OSM_COM_ORDER_Status();
	}
	public String OSM_SOM_ORDER_Status() throws Exception {
		return KDB.OSM_SOM_ORDER_Status();
	}


	
	// ---------------------Keyword CRM/BRM DB------------------------//
	public String DBConnection() {
		return KDB.DBConnection();
	}
	
	public String CIO_Verification() throws Exception {
		return KDB.CIO_Verification("SIEBELUAT");
 }
	public String BillPoID() {
		return KDB.BillPoID();
	}

//	public String DBDisconnection() {
//		return KDB.DBDisconnection();
//	}

	// PUBLIC STRING BRMCHECKACCOUNTSTATUS() THROWS EXCEPTION {
	// RETURN KDB.BRMCHECKACCOUNTSTATUS("1001005913","97370002646");
	// }
	

	public String  BRMCheckBucketBalance() throws Exception {
		return KDB.BRMCheckBucketBalance("16136359620","97470899280");
	}
  	
	public String BRM_Account_Status() throws Exception
	{		
    	//return KDB.BRM_AccountStatus(KBSiebel.Account_Number,KBSiebel.MsisdnNumber);
		//return KDB.BRM_Account_Status("1001005916","97370002664");
		return KDB.BRM_Account_Status();
	}
	
	
	public String  Account_BillingProfileStatus() throws Exception {
		//return KDB.Account_BillingProfileStatus("1001006065");
		return KDB.Account_BillingProfileStatus();
	}
	
	public String  Account_MSISDN_Status() throws Exception {
	//	return KDB.Account_MSISDN_Status("1001006074");
		return KDB.Account_MSISDN_Status();
	}
	
	
	public String Suspend_Status() throws Exception {
		return KDB.Suspend_Status();
	}
	
	public String fechingOrderRowID(String Order_Number) throws Exception {
		return KDB.fechingOrderRowID(Order_Number);
	}
	

	
	
	
	// ---------------------Keyword CRM/BRM DB------------------------//

	// ---------------------Keyword Mobile Usages Starts------------------------//
	public String Dialer() {
		return KDialers.Dialer();
	}

	public String CheckUnBarringCall() {
		return KDialers.CheckUnBarringCall();
	}

	public String CheckBarringCall() {
		return KDialers.CheckBarringCall();
	}

	public String smsSender() {
		return KDialers.smsSender();
	}

	public String BalanceCheckDialer() {
		return KDialers.BalanceCheckDialer();
	}

	public String RechargeDialer() {
		return KDialers.RechargeDialer();
	}

	public String BillEnquiryDialler() {
		return KDialers.BillEnquiryDialler();
	}

	public String setMessengerCapabilities() {
		return KSetCapabilities.setMessengerCapabilities();
	}

	public String SetCallCapabilities() {
		return KSetCapabilities.setDialerCapabilities();
	}

	public String setMCareCapabilities() {
		return KSetCapabilities.setMCareCapabilities();
	}

	public String PrevCheckBalance() {
		return KRTB.PrevCheckBalance();
	}

	public String PostCheckBalance() {
		return KRTB.PostCheckBalance();
	}

	public String LocalCallCharging() {
		return KCharging.LocalCallCharging();
	}

	public String LocalSMSCharging() {
		return KCharging.LocalSMSCharging();
	}

	public String InternationalCallCharging() {
		return KCharging.InternationalCallCharging();
	}

	public String InternationalSMSCharging() {
		return KCharging.InternationalSMSCharging();
	}

	public String BalanceCheck() {
		return KNotification.BalanceCheck();
	}

	public String FlexBalanceCheck() {
		return KNotification.FlexBalanceCheck();
	}

	public String ProductInfoNotification() {
		return KNotification.ProductInfoNotification();
	}

	public String ConfigureSMSC() {
		return KHandset.ConfigureSMSC();
	}

	public String RestartMobile() {
		return KHandset.RestartMobile();
	}

	public String VerifyRecharge() {
		return KUSSD.VerifyRecharge();
	}

	public String USSDJourney() {
		return KUSSD.USSDJourney();
	}

	public String InvokeUSSDMenu() {
		return KUSSD.InvokeUSSDMenu();
	}

	public String USSDCleaner() {
		return KUSSD.USSDCleaner();
	}

	public String PunchRechargePIN() {
		return KUSSD.PunchRechargePIN();
	}

	public String VerifyProductActivationPrepaid() {
		return KUSSD.VerifyProductActivationPrepaid();
	}

	public String VerifyProductDeActivationPrepaid() {
		return KUSSD.VerifyProductDeActivationPrepaid();
	}

	public String SearchAsset() {
		return KSiebel.SearchAsset();
	}

	public String CheckOrder() {
		return KSiebel.CheckOrder();
	}

	public String Cookies() {
		return KSiebel.Cookies();
	}

	// ---------------------Mobile Usages Ends------------------------//

	// ---------------------MCare Keywords Starts------------------------//
	public String installMyVodafoneApp() {
		return MCare.installMyVodafoneApp();
	}

	public String verifyMCareLogin() {
		return MCare.verifyMCareLogin();
	}

	public String verifyPlanNameMCare() {
		return MCare.verifyPlanNameMCare();
	}

	public String billEnquiryMCare() {
		return MCare.billEnquiryMCare();
	}

	public String PreAddon_Activation() {
		return MCare.PreAddon_Activation();
	}

	public String PreAddon_DeActivation() {
		return MCare.PreAddon_DeActivation();
	}

	public String PostAddon_Activation() {
		return MCare.PostAddon_Activation();
	}

	public String PostAddon_Deactivation() {
		return MCare.PostAddon_Deactivation();
	}

	public String MVACookies() {
		return MCare.MVACookies();
	}

	public String verifyMCareBuckets() {
		return MCare.verifyMCareBuckets();
	}

	public String McareBillPayment() {
		return MCare.McareBillPayment();
	}

	public String SelfRecharge() {
		return MCare.SelfRecharge();
	}

	public String MenuVerify() {
		return MCare.MenuVerify();
	}

	public String OTP_MCareLogin() {
		return MCare.OTP_MCareLogin();
	}

	public String Test() {
		return MCare.Test();
	}

	public String Billpay() {
		return MCare.Billpay();
	}

}
