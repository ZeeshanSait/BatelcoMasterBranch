package Libraries;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.exception.ExceptionUtils;

public class Keyword_Validations extends Driver {
	Common CO = new Common();

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: RTB_Validation
	 * Use 					: To perform RTBValidation with the Line Items fetched from FetchProcuctCatalogData Method
	 * Argument				: Null
	 * Designed By			: Vinodhini Raviprasad
	 * Modified By			: Vinodhini Raviprasad
	 * Last Modified Date 	: 18-Oct-2017  
	--------------------------------------------------------------------------------------------------------*/
	public String RTB_Validation() {

		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------RTB Validation Event Details------");
		try {
			String RTB = Validatedata("RTB_Validation");
			if (RTB.equalsIgnoreCase("yes")) {
				String Surepay, Benefits, Product_Validity, Siebel_Desc, BucketValue, BucketUsageType;
				Test_OutPut += "RTB -- ";
				ArrayList<String> FetchProduct = Utlities.FetchProcuctCatalogData();
				for (int i = 0; i < FetchProduct.size(); i++) {
					String FetchPC[] = FetchProduct.get(i).split("\\|\\|");
					Surepay = FetchPC[0].trim();
					Benefits = FetchPC[1].trim();
					BucketValue = FetchPC[2].trim();
					BucketUsageType = FetchPC[3].trim();
					Product_Validity = FetchPC[4].trim();
					Siebel_Desc = FetchPC[7].trim();

					String BE = BE(Siebel_Desc, Benefits, Product_Validity, BucketValue, BucketUsageType);
					Result.fUpdateLog("Proration -- " + BE);
					if (BE.equals(RTBOutputData.get(Surepay))) {
						// Test_OutPut += "Proration -- "+BE + ",";
						Result.fUpdateLog(RTBOutputData.get(Surepay));
						Test_OutPut += RTBOutputData.get(Surepay) + ",";
					} else
						Continue.set(false);
				}

				CO.ToWait();
				if (Continue.get()) {
					Test_OutPut += "RTB Validation is done Successfully " + ",";
					Result.fUpdateLog("RTB Validation is done Successfully ");
					Status = "PASS";
				} else {
					Test_OutPut += "RTB Validation Failed" + ",";
					Result.fUpdateLog("RTB Validation Failed");
					Status = "FAIL";
				}
			}
		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Exception occurred" + ",";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------RTB Validation Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: BE
	 * Use 					: To find the Bundle Expiry Date for Siebel Description
	 * Argument				: Desc- Siebel Description,Benifit- Benifit, Product_Validity - Product Validity
	 * Designed By			: Vinodhini Raviprasad
	 * Modified By			: Vinodhini Raviprasad
	 * Last Modified Date 	: 17-Oct-2017  
	--------------------------------------------------------------------------------------------------------*/
	public String BE(String Desc, String Benifit, String Product_Validity, String BucketValue, String BucketUsageType) {
		try {
			DateFormat Date_Format = new SimpleDateFormat("dd-MMM-yyyy");
			String billcycledate, Expiry;
			// String orderdate = "07-11-2017";
			String orderdate = OrderDate.get();
			billcycledate = CO.FindBillingCycle();
			Calendar cals = Calendar.getInstance();
			cals.set(Calendar.YEAR, Integer.parseInt(orderdate.split("-")[2]));
			cals.set(Calendar.MONTH, Integer.parseInt(orderdate.split("-")[1]) - 1);
			cals.set(Calendar.DATE, Integer.parseInt(orderdate.split("-")[0]));
			Date startDate = Date_Format.parse(Date_Format.format(cals.getTime()));
			Date endDate = Date_Format.parse(billcycledate);
			int difInDays = (int) ((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24));
			int Total_Days;
			if (billcycledate.contains("15")) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
				Calendar cal2 = Calendar.getInstance();
				cal2.setTime(dateFormat.parse(billcycledate));
				cal2.add(Calendar.MONTH, -1);
				Total_Days = cal2.getActualMaximum(Calendar.DATE);
			} else {
				Total_Days = cals.getActualMaximum(Calendar.DATE);
			}
			String Prorate = "";

			Expiry = orderdate;

			switch (Product_Validity.toLowerCase()) {
			case "bill_cycle":
				// Prorate = CO.Prorated(Total_Days, difInDays, Benifit);
				Prorate = CO.Prorated(Total_Days, difInDays, Benifit, BucketValue, BucketUsageType);
				Expiry = billcycledate;
				break;
			case "daily":
				// Prorate = CO.Prorated(1, 1, Benifit);
				Prorate = CO.Prorated(1, 1, Benifit, BucketValue, BucketUsageType);
				Expiry = orderdate;
				break;
			case "monthly":
			case "continuous":
				// Prorate = CO.Prorated(Total_Days, Total_Days, Benifit);
				Prorate = CO.Prorated(Total_Days, difInDays, Benifit, BucketValue, BucketUsageType);
				cals.add(Calendar.DATE, Total_Days);
				Expiry = Date_Format.format(cals.getTime());// Total_Days+Month
				break;
			case "weekly":
				Prorate = CO.Prorated(7, 7, Benifit, BucketValue, BucketUsageType);
				cals.add(Calendar.DATE, 7);
				Expiry = Date_Format.format(cals.getTime());
				break;
			}
			if (!Product_Validity.equalsIgnoreCase("day")) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
				Calendar cal1 = Calendar.getInstance();
				cal1.setTime(dateFormat.parse(Expiry));
				cal1.add(Calendar.DATE, -1);
				Expiry = dateFormat.format(cal1.getTime());
			}
			Desc = Desc.replace("&CV", " " + Prorate + " ");
			Desc = Desc.replace("&BE", " " + Expiry + " ");
			return Desc;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

}
