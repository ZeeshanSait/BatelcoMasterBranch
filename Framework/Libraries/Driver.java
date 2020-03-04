package Libraries;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Dictionary;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.Session;

@SuppressWarnings("rawtypes")
public class Driver {

	public static ThreadLocal<String> WorkingDir = new ThreadLocal<String>();
	public static ThreadLocal<String> Base_Path = new ThreadLocal<String>();
	public static ThreadLocal<String> Storage_FLD = new ThreadLocal<String>();
	public static ThreadLocal<String> OR_File = new ThreadLocal<String>();
	public static ThreadLocal<String> StoreDB_File = new ThreadLocal<String>();
	public static ThreadLocal<String> Directory_FLD = new ThreadLocal<String>();
	public static ThreadLocal<String> Database_File = new ThreadLocal<String>();
	public static ThreadLocal<String> TestDataDB_File = new ThreadLocal<String>();
	public static ThreadLocal<String> Result_FLD = new ThreadLocal<String>();
	public static ThreadLocal<String> Templete_FLD = new ThreadLocal<String>();
	public static ThreadLocal<String> Temp_FLD = new ThreadLocal<String>();
	public static ThreadLocal<String> XMLfilepth = new ThreadLocal<String>();
	public static ThreadLocal<String> URL = new ThreadLocal<String>();
	public static ThreadLocal<String> logfilepth = new ThreadLocal<String>();
	public static ThreadLocal<String> UC = new ThreadLocal<String>();
	public static ThreadLocal<String> Project = new ThreadLocal<String>();
	public static int passUC = 0;
	public static int failUC = 0;
	public static int partialypassUC = 0;
	public static int totalUCount = 0;
	public static ThreadLocal<String> TestOutput = new ThreadLocal<String>();
	// public static String TestOutput;
	public static ThreadLocal<Boolean> Continue = new ThreadLocal<Boolean>();
	public static ThreadLocal<String> ExecutionStarttimestr = new ThreadLocal<String>();
	public static ThreadLocal<String> ExecutionEndtimestr = new ThreadLocal<String>();
	public static ThreadLocal<String> Environment = new ThreadLocal<String>();
	public static ThreadLocal<String> UseCaseName = new ThreadLocal<String>();

	public static ThreadLocal<String> TestCaseN = new ThreadLocal<String>();
	public static ThreadLocal<String> TestCaseData = new ThreadLocal<String>();
	public static ThreadLocal<String> ValidationData = new ThreadLocal<String>();
	public static ThreadLocal<String> TestCaseDes = new ThreadLocal<String>();
	public static ThreadLocal<String> UseCaseIDP = new ThreadLocal<String>();
	public static ThreadLocal<String> UseCaseDP = new ThreadLocal<String>();
	public static ThreadLocal<String> Dependancy = new ThreadLocal<String>();
	public static ThreadLocal<String> TestCaseIDP = new ThreadLocal<String>();
	public static ThreadLocal<String> TestCaseDP = new ThreadLocal<String>();
	public static ThreadLocal<String> currUCstatus = new ThreadLocal<String>();
	public static ThreadLocal<String> currKWstatus = new ThreadLocal<String>();
	public static ThreadLocal<String> currKW = new ThreadLocal<String>();
	public static ThreadLocal<String> currKW_DB = new ThreadLocal<String>();
	public static ThreadLocal<String> currKW_Des = new ThreadLocal<String>();
	public static ThreadLocal<String> browser = new ThreadLocal<String>();
	public static ThreadLocal<String> keywordstartdate = new ThreadLocal<String>();
	public static ThreadLocal<String> Planname = new ThreadLocal<String>();
	public static ThreadLocal<String> OrderDate = new ThreadLocal<String>();
	public static ThreadLocal<String> billDate = new ThreadLocal<String>();
	public static ThreadLocal<String> Def_Smart_limit = new ThreadLocal<String>();
	public static ThreadLocal<ChannelShell> nchannel = new ThreadLocal<ChannelShell>();
	public static ThreadLocal<Channel> channel = new ThreadLocal<Channel>();
	public static ThreadLocal<Session> nsession = new ThreadLocal<Session>();
	public static ThreadLocal<FileOutputStream> tergetFile = new ThreadLocal<FileOutputStream>();
	public static ThreadLocal<String> TCscreenfile = new ThreadLocal<String>();
	public static ThreadLocal<String> SalesOrder_No = new ThreadLocal<String>();
	public static ThreadLocal<String> contact = new ThreadLocal<String>();
	public static ThreadLocal<String> New_Account = new ThreadLocal<String>();
	public static ThreadLocal<String> InvoiceZip = new ThreadLocal<String>();
	public static ThreadLocal<String> Dunning = new ThreadLocal<String>();
	public static ThreadLocal<String> UCscreenfilepth = new ThreadLocal<String>();
	public static ThreadLocal<String> masterrephtml = new ThreadLocal<String>();
	public static ThreadLocal<String> Batchs = new ThreadLocal<String>();

	public static ThreadLocal<Dictionary> TestData = new ThreadLocal<Dictionary>();
	public static ThreadLocal<Dictionary> ValidateDT = new ThreadLocal<Dictionary>();
	public static ThreadLocal<Dictionary> database = new ThreadLocal<Dictionary>();
	protected static ThreadLocal<WebDriver> cDriver = new ThreadLocal<WebDriver>();
	public static HashMap<String, String> RTBOutputData = new HashMap<String, String>();
	public static HashMap<String, String> LineItemData = new HashMap<String, String>();
	public static HashMap<String, String> DunningSchedule = new HashMap<String, String>();
	public static HashMap<String, String> BillSchedule = new HashMap<String, String>();
	public static ThreadLocal<String> Billprofile_No = new ThreadLocal<String>();
	public static ThreadLocal<String> Acc_Number = new ThreadLocal<String>();
	public static ThreadLocal<String> Total_DueAmt = new ThreadLocal<String>();
	public static ThreadLocal<String> activity = new ThreadLocal<String>();
	public static ThreadLocal<String> NRT_File = new ThreadLocal<String>();
	public static ThreadLocal<String> ResultDescription = new ThreadLocal<String>();
	public static ThreadLocal<Point> p = new ThreadLocal<Point>();
	public static ThreadLocal<Dimension> d = new ThreadLocal<Dimension>();
	public static ThreadLocal<String> SeqNo = new ThreadLocal<String>();
	public static DateFormat For = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
	public static Calendar cal = Calendar.getInstance();
	public static String SRT_Time = For.format(cal.getTime()).toString();
	public static String Execute;
	public static String[] Batches;
	public static int TotExeCount = 0;
	
	public static void main(String[] args) {
		System.out.println("Intialization");
		killIEexeTask();

		try {
			WorkingDir.set(System.getProperty("user.dir").replace("\\", "/"));
			Base_Path.set(WorkingDir.get() + "/Framework");
			Storage_FLD.set(Base_Path.get() + "/Storage");
			OR_File.set(Storage_FLD.get() + "/ObjectRepository.xlsx");
			StoreDB_File.set(Storage_FLD.get() + "/StoreDB.xlsx");
			Database_File.set(Storage_FLD.get() + "/CommonDirectory.xlsx");
			Directory_FLD.set(Base_Path.get() + "/Database");
			TestDataDB_File.set(Directory_FLD.get() + "/TestDataDB.xlsx");
			NRT_File.set(Directory_FLD.get() + "/Mobile_Reservation_Token.csv");
			Result_FLD.set(WorkingDir.get() + "/Results");
			Templete_FLD.set(Base_Path.get() + "/Templates");
			Temp_FLD.set(Base_Path.get() + "/Temp");

			System.out.println(Temp_FLD.get());
			// To create result folder
			File resfold = new File(Result_FLD.get());
			if ((!resfold.exists()))
				resfold.mkdir();

			ExecutionStarttimestr.set(SRT_Time);
			System.out.println("Execution initiated at --- " + SRT_Time);

			ArrayList<String> BatchList = Utlities.floadbatches();

			Batches = new String[BatchList.size()];
			Batches = BatchList.toArray(Batches);
			createdynamicfiles(Batches);

			if (Batches.length > 1) {
				if (Batches != null) {
					ArrayList<RunnableDemo> bthArr = new ArrayList<RunnableDemo>();
					for (int currbatch = 0; currbatch < Batches.length; currbatch++) {
						// Result.fCreateReportFiles(currbatch+1,Batches[currbatch]);
						bthArr.add(new RunnableDemo(Batches[currbatch], currbatch));
					}
					for (RunnableDemo cbthArr : bthArr) {
						cbthArr.start();
					}
				}
			} else {
				if (Batches != null) {
					for (int currbatch = 0; currbatch < Batches.length; currbatch++) {
						ExecuteCases(Batches[currbatch], Integer.toString(currbatch));
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void ExecuteCases(String Batch, String batchcount) {
		Batchs.set(Batch);
		WorkingDir.set(System.getProperty("user.dir").replace("\\", "/"));
		Base_Path.set(WorkingDir.get() + "/Framework");
		Result_FLD.set(WorkingDir.get() + "/Results");
		Templete_FLD.set(Base_Path.get() + "/Templates");
		Temp_FLD.set(Base_Path.get() + "/Temp");

		Result_FLD.set(WorkingDir.get() + "/Results/");
		Database_File.set(Temp_FLD.get() + "/" + Batch + "/CommonDirectory.xlsx");
		OR_File.set(Temp_FLD.get() + "/" + Batch + "/ObjectRepository.xlsx");
		StoreDB_File.set(Temp_FLD.get() + "/" + Batch + "/StoreDB.xlsx");
		TestDataDB_File.set(Temp_FLD.get() + "/" + Batch + "/TestDataDB.xlsx");
		NRT_File.set(Temp_FLD.get() + "/" + Batch + "/Mobile_Reservation_Token.csv");
		Storage_FLD.set(Temp_FLD.get() + "/" + Batch);
		browser.set("Chrome");
		int totalUC = 0;
		try {
			ExecutionStarttimestr.set(SRT_Time);
			//System.out.println("Execution initiated at --- " + SRT_Time);

			ArrayList<String[]> addUsecase = Utlities.floadUseCases(Batch);
			String[] IDP = addUsecase.get(0);
			String[] totUseCases = addUsecase.get(1);
			String[] totTestCases = addUsecase.get(2);
			String[] totTestcase_Des = addUsecase.get(3);
			String[] totUseCases_data = addUsecase.get(4);
			String[] totvalidation_data = addUsecase.get(5);
			totalUC = totUseCases.length;
			TotExeCount = TotExeCount + totalUC;

			// createdynamicfiles(Batch);
			System.out.println("No of Scenario(s) to be executed in " + Batch + ":" + totalUC);
			for (int currUseCase = 0; currUseCase < totalUC; currUseCase++) {
				String DP = IDP[currUseCase];
				Dependancy.set(DP);
				if (Dependancy.get().equalsIgnoreCase("IDP")) {
					UseCaseIDP.set(totUseCases[currUseCase]);
					TestCaseIDP.set(totTestCases[currUseCase]);
				} else if (currUseCase != 0) {
					UseCaseDP.set(totUseCases[currUseCase - 1]);
					TestCaseDP.set(totTestCases[currUseCase - 1]);
				}

				System.out.println(Batch + "," + currUseCase + 1);
				//Runtime.getRuntime().exec("taskkill /im chrome.exe /f");

				UseCaseName.set(totUseCases[currUseCase]);
				TestCaseN.set(totTestCases[currUseCase]);
				TestCaseDes.set(totTestcase_Des[currUseCase]);
				TestCaseData.set(totUseCases_data[currUseCase]);
				ValidationData.set(totvalidation_data[currUseCase]);

				// TestOutput = "";
				TestOutput.set("");
				Result.fCreateReportFiles(currUseCase + 1, UseCaseName.get(), Batch);
				Result.createTCScreenshotFold();
				
				ArrayList<String[]> addresses = Utlities.floadKeywords(UseCaseName.get());
				String totKeywords[] = addresses.get(0);
				String DataBinding[] = addresses.get(1);
				String Description[] = addresses.get(2);
				String SequenceNo[] = addresses.get(3);
				

				Result.fUpdateLog("No of Keywords to be executed in " + UseCaseName.get() + ":" + totKeywords.length);

				if (DP.equalsIgnoreCase("DP") && currUCstatus.get().equalsIgnoreCase("Fail")) {
					currUCstatus.set("Fail");
					Continue.set(false);
					TestOutput.set("******* Interdependant Failure Blocked******* : " + UseCaseName.get() + "<br/>");
					Result.fUpdateLog("******* Interdependant Failure Blocked******");
				} else if (DP.equalsIgnoreCase("DP") && currUCstatus.get().equalsIgnoreCase("PartiallyPass")) {
					Continue.set(true);
					currUCstatus.set("PartiallyPass");
					database.set((Dictionary<?, ?>) Utlities.fdatabase(UseCaseName.get()));
				} else {
					Continue.set(true);
					currUCstatus.set("Pass");
					database.set((Dictionary<?, ?>) Utlities.fdatabase(UseCaseName.get()));
				}

				/*
				 * Continue.set(true); currUCstatus.set("Pass"); database.set((Dictionary<?, ?>)
				 * Utlities.fdatabase(UseCaseName.get()));
				 */

				Result.createTCScreenshotFold();
				ValidateDT.set((Dictionary<?, ?>) Utlities.freaddata(ValidationData.get()));
				for (int currKeyword = 0; currKeyword < totKeywords.length; currKeyword++) {
					String Keyword_Result = null;
					if (Continue.get() == true) {
						
						DateFormat currkeywordstartdate = new SimpleDateFormat("dd-MMM-yyyy");
						keywordstartdate.set(currkeywordstartdate.format(cal.getTime()).toString());
						SeqNo.set(SequenceNo[currKeyword]);
						currKW.set(totKeywords[currKeyword]);
						currKW_DB.set(DataBinding[currKeyword]);
						currKW_Des.set(Description[currKeyword]);
						
						System.out.println("Current Keyword ----> " + currKW.get()+ " And Sequence Number is " +SeqNo.get()+ currKW_Des.get());
						// Result.fUpdateLog("Current Keyword ----> " + currKW.get());
						currKWstatus.set("Pass");
						if (currKW_DB.get().toString().equalsIgnoreCase("Data")) {
							TestData.set((Dictionary<?, ?>) Utlities.freaddata(TestCaseData.get()));
							Environment.set(pulldata("Environment"));
							Project.set(pulldata("Project_Name"));
							
						} else {
							TestData.set((Dictionary<?, ?>) Utlities.freaddata_diff(currKW_DB.get()));
							//Environment.set(getdata("Environment"));
							//Project.set(getdata("Project_Name"));
							Environment.set(pulldata("Environment"));
							Project.set(pulldata("Project_Name"));
						}

						try {
							Class<?> cls = Class.forName("Libraries.KeyWord");
							Object obj = cls.newInstance();
							Method method = cls.getMethod(currKW.get());
							Keyword_Result = (String) method.invoke(obj);
						} catch (Exception e) {
							e.printStackTrace();
						}

						if (Keyword_Result != null) {
							String[] ResultandDes = Keyword_Result.split("@@");

							if (ResultandDes[0].equalsIgnoreCase("FAIL")) {
								currKWstatus.set("Fail");
								Continue.set(false);
							} else if (ResultandDes[0].equalsIgnoreCase("PartiallyPass")) {
								currKWstatus.set("PartiallyPass");
								Continue.set(true);
							} else {
								currKWstatus.set("Pass");
								Continue.set(true);
							}
							 if (TestOutput.get() == null) {
                                 if (!(ResultandDes[1].equals("<br/>"))) {
                                        TestOutput.set("<b>"+ "Step No: " + SeqNo.get() +" </b>"+  ResultandDes[1]);
                                 }
                          } else {
                                	 if (!(ResultandDes[1].equals("<br/>")) &&  UC.get().contains("DB_Connection")) {
     									TestOutput.set(TestOutput.get() + ResultandDes[1]);
     								}
                                 
                                   else {
                                     TestOutput.set(TestOutput.get() + "<tr><td> <b>"+ "Step No: " + SeqNo.get() +" </b>"+  ResultandDes[1]);
                                     TestOutput.set(""+TestOutput.get()+"</td></tr>");
                                     // System.out.println(TestOutput.get());
                    
                              }
                          }
																					
						}
					}

				}

				if (currKWstatus.get().equalsIgnoreCase("Fail")) {
					failUC = failUC + 1;
					currUCstatus.set("Fail");
				} else if (currKWstatus.get().equalsIgnoreCase("PartiallyPass")) {
					partialypassUC = partialypassUC + 1;
					currUCstatus.set("PartiallyPass");
				} else {
					passUC = passUC + 1;
					currUCstatus.set("Pass");
				}

				totalUCount = totalUCount + 1;

				Calendar cal1 = Calendar.getInstance();
				String End_Time = For.format(cal1.getTime()).toString();
				ExecutionEndtimestr.set(End_Time);
				System.out.println("Execution Completed at --- " + End_Time);
				
				Result.CreateExtentReport(currUseCase + 1, UseCaseName.get(), Batch);
				Result.fcreateMasterHTML(Batch);
				
			}

			if (TotExeCount == totalUCount) {
				// Result.DisplayHTMLReport();
				browser.set("Chrome");
				Browser.OpenBrowser(browser.get(), masterrephtml.get());
				killChromeexeTask();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getdata(String colname) {
		String c = "";
		try {
			c = TestData.get().get(colname).toString();
			return c;
		} catch (Exception e) {
			return c;
		}

	}

	public static String Validatedata(String colname) {
		String c = "";
		try {
			c = ValidateDT.get().get(colname).toString();
			return c;
		} catch (Exception e) {
			return c;
		}

	}

	public static String pulldata(String colname) {
		String c = "";
		try {
			c = database.get().get(colname).toString();
			return c;
		} catch (Exception e) {
			return c;
		}

	}

	private static void createdynamicfiles(String[] batches) {
		try {
			String ORsrc = Storage_FLD.get();
			File cORsrc = new File(ORsrc);
			String DataDBsrc = Directory_FLD.get();
			File cDataDBsrc = new File(DataDBsrc);
			String tempfold = Base_Path.get() + "/Temp";

			for (String batchname : batches) {
				File batchdatafold = new File(tempfold + "/" + batchname);
				if ((!batchdatafold.exists()))
					try {
						batchdatafold.mkdir();
					} catch (SecurityException se) {
						/* handle it */
					}
				FileUtils.copyDirectory(cDataDBsrc, batchdatafold);
				FileUtils.copyDirectory(cORsrc, batchdatafold);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void killChromeexeTask() {
		try {
			Runtime.getRuntime().exec("taskkill /im chrome.exe /f");
			Runtime.getRuntime().exec("taskkill /im chromedriver.exe /f");
			Runtime.getRuntime().exec("taskkill /im conhost.exe /f");			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void killIEexeTask() {
		try {			
			Runtime.getRuntime().exec("taskkill /im conhost.exe /f");
			Runtime.getRuntime().exec("taskkill /im IEDriverServer.exe /f");
			Runtime.getRuntime().exec("taskkill /im iexplore.exe /f");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void killFirefoxexeTask() {
		try {			
			 Runtime.getRuntime().exec("taskkill /im conhost.exe /f");
			Runtime.getRuntime().exec("taskkill /im geckodriver.exe /f");			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

class RunnableDemo implements Runnable {
	private Thread t;
	private String threadName;
	private String batchcount;

	RunnableDemo(String name, int rbatchcount) {
		threadName = name;
		batchcount = Integer.toString(rbatchcount);
		System.out.println("Creating " + threadName);
	}

	@Override
	public void run() {
		System.out.println("Running " + threadName);
		try {

			Driver.ExecuteCases(threadName, batchcount);
		} catch (Exception e) {
			System.out.println("Thread " + threadName + " interrupted.");
		}
		System.out.println("Thread " + threadName + " exiting.");
	}

	public void start() throws InterruptedException {
		System.out.println("Starting " + threadName);
		if (t == null) {
			t = new Thread(this, threadName);
			t.start();
		}
	}
}
