package Libraries;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.xml.DOMConfigurator;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class Result extends Driver {

	public static String updatelogmsg = "";
	public static String updateExtentlogmsg = "";
	public static File tresfold;

	/*----------------------------------------------------------------------------------------------------
	 * Method Name			: fCreateReportFiles
	 * Arguments			: current usecase
	 * Use 					: to create all the report files
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 23-Aug-2017 
	--------------------------------------------------------------------------------------------------------*/
	public static void fCreateReportFiles(int SNo, String Usecase, String Batch) {

		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Calendar cal = Calendar.getInstance();
		try {

			File resfold = new File(Result_FLD.get() + "/" + dateFormat.format(cal.getTime()) + "/");
			if ((!resfold.exists()))
				resfold.mkdir();

			String timefold = ExecutionStarttimestr.get().replace(":", "-").replace(" ", "_");
			tresfold = new File(resfold + "/" + timefold + "/");
			if ((!tresfold.exists()))
				tresfold.mkdir();

			File batchfold = new File(resfold + "/" + timefold + "/" + Batch + "/");
			if ((!batchfold.exists()))
				batchfold.mkdir();

			UC.set(SNo + Usecase);
			UCscreenfilepth.set(batchfold + "/" + UC.get());
			File bthresfold = new File(batchfold + "/" + UC.get());
			if ((!bthresfold.exists()))
				bthresfold.mkdir();

			XMLfilepth.set(UCscreenfilepth.get() + "/XML");
			File XMLfilepth = new File(UCscreenfilepth.get() + "/XML");
			if ((!XMLfilepth.exists()))
				XMLfilepth.mkdir();

			String tempref = Templete_FLD.get();
			File scriptrepsource = new File(tempref + "/Scripts");
			File scriptfold = new File(tresfold + "/Scripts");
			if ((!scriptfold.exists())) {
				scriptfold.mkdir();
				FileUtils.copyDirectory(scriptrepsource, scriptfold);
			}

			logfilepth.set(bthresfold + "/Logs.txt");

			File logfile = new File(logfilepth.get());
			if (!logfile.exists()) {
				logfile.createNewFile();
			}

			// File masterhtml = new File(tempref + "/MasterReport.HTML");
			// FileUtils.copyFileToDirectory(masterhtml, tresfold);
			masterrephtml.set(tresfold.toString() + "\\MasterReport.HTML");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/*----------------------------------------------------------------------------------------------------
	 * Method Name			: CreateExtentReportFiles
	 * Arguments			: current usecase
	 * Use 					: to create all the report files
	 * Designed By			: Zeeshan
	 * Last Modified Date 	: 23-Sep-2019 
	--------------------------------------------------------------------------------------------------------*/
	public static void CreateExtentReport(int SNo, String Usecase, String Batch) {
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Calendar cal = Calendar.getInstance();
		try {

			File resfold = new File(Result_FLD.get() + "/" + dateFormat.format(cal.getTime()) + "/");
			if ((!resfold.exists()))
				resfold.mkdir();

			String timefold = ExecutionStarttimestr.get().replace(":", "-").replace(" ", "_");
			File tresfold = new File(resfold + "/" + timefold + "/");
			if ((!tresfold.exists()))
				tresfold.mkdir();

			File batchfold = new File(resfold + "/" + timefold + "/" + Batch + "/");
			if ((!batchfold.exists()))
				batchfold.mkdir();

			UC.set(SNo + Usecase);
			UCscreenfilepth.set(batchfold + "/" + UC.get());
			File bthresfold = new File(batchfold + "/" + UC.get());
			if ((!bthresfold.exists()))
				bthresfold.mkdir();

			XMLfilepth.set(UCscreenfilepth.get() + "/XML");
			File XMLfilepth = new File(UCscreenfilepth.get() + "/XML");
			if ((!XMLfilepth.exists()))
				XMLfilepth.mkdir();

			String tempref = Templete_FLD.get();
			File scriptrepsource = new File(tempref + "/Scripts");
			File scriptfold = new File(tresfold + "/Scripts");
			if ((!scriptfold.exists())) {
				scriptfold.mkdir();
				FileUtils.copyDirectory(scriptrepsource, scriptfold);
			}

			logfilepth.set(bthresfold + "/Logs.txt");

			File logfile = new File(logfilepth.get());
			if (!logfile.exists()) {
				logfile.createNewFile();
			}

			// File masterhtml = new File(tempref + "/MasterReport.HTML");
			// FileUtils.copyFileToDirectory(masterhtml, tresfold);
			//masterrephtml.set(tresfold.toString() + "\\MasterReport.HTML");
			ExtentReports extent = new ExtentReports();
			ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(tresfold + "\\ExtentMaster.html");
			extent.attachReporter(htmlReporter);
			ExtentTest test = extent.createTest(TestCaseN.get());
			updateExtentlogmsg = updateExtentlogmsg + Environment.get();
			updateExtentlogmsg = updateExtentlogmsg + Batches;
			updateExtentlogmsg = updateExtentlogmsg + "<td width = 12%><center><a href =.\\" + Batches + "\\" + UC.get() + "\\"
					+ TestCaseN.get() + ".docx" + ">" + TestCaseN.get() + "</a></center></td>";
			updateExtentlogmsg = updateExtentlogmsg + TestCaseDes.get();
			updateExtentlogmsg = updateExtentlogmsg + TestCaseData.get();

			if (currUCstatus.get().equals("Pass")) {
				 test.pass(updateExtentlogmsg + TestOutput.get() + "</td>");
				 updateExtentlogmsg = updateExtentlogmsg
						+ "<td width = 6% Style=\"color:#04D215\"><b><center>Pass</center></b></td></tr>";
			} else if (currUCstatus.get().equals("PartiallyPass")) {
				test.fail(updateExtentlogmsg + TestOutput.get() + "</td>");
				updateExtentlogmsg = updateExtentlogmsg
						+ "<td width = 6% Style=\"color:#0D8ECF\"><b><center>PartiallyPass</center></b></td></tr>";
			} else if (currUCstatus.get().equals("Fail")) {
				test.fail(updateExtentlogmsg + TestOutput.get() + "\n" + "Failed at " + currKW_Des.get() +"</td>");
				updateExtentlogmsg = updateExtentlogmsg
						+ "<td width = 6% Style=\\\"color:#FF0F00\\\"><b><center>Fail</center></b></td></tr>";
			}

			extent.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*----------------------------------------------------------------------------------------------------
	 * Method Name			: fUpdateLog
	 * Arguments			: logmessage
	 * Use 					: to update the log for each case
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 23-Aug-2017
	--------------------------------------------------------------------------------------------------------*/
	public static void fUpdateLog(String logmessage) {

		File logfile = new File(logfilepth.get());
		FileWriter fw;
		try {
			fw = new FileWriter(logfile.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			System.out.println(logmessage);
			bw.write(logmessage + "\r\n");
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/*----------------------------------------------------------------------------------------------------
	 * Method Name			: CreateDBHTMLReport
	 * Arguments			: logmessage
	 * Use 					: To Update HTML Content from DB
	 * Designed By			: Zeeshan
	 * Last Modified Date 	: 19-OCT-2019
	--------------------------------------------------------------------------------------------------------*/
	public static void CreateDBHTMLReport(String DBContent) {
		//UCscreenfilepth.get() + "/" + 
		File DBHTMLFile = new File(UCscreenfilepth.get() + "/" + TestCaseN.get() + ".html");
		System.out.println(DBHTMLFile);
		FileWriter fw;
		try {
			fw = new FileWriter(DBHTMLFile.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			System.out.println(DBContent);
			bw.write(DBContent + "\r\n");
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/*----------------------------------------------------------------------------------------------------
	 * Method Name			: Takescreenshot
	 * Arguments			: screenshot name
	 * Use 					: Take screenshot and save it in screen shots folder
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 23-Aug-2017
	--------------------------------------------------------------------------------------------------------*/
	public static void takescreenshot(String LogMessage) {
		try {
			File scrFile = ((TakesScreenshot) cDriver.get()).getScreenshotAs(OutputType.FILE);

			CustomXWPFDocument document = new CustomXWPFDocument(new FileInputStream(new File(TCscreenfile.get())));
			FileOutputStream fos = new FileOutputStream(new File(TCscreenfile.get()));
			String id = document.addPictureData(new FileInputStream(new File(scrFile.toString())),
					Document.PICTURE_TYPE_PNG);
			XWPFParagraph paragraph = document.createParagraph();
			XWPFRun run = paragraph.createRun();
			run.setText(LogMessage);
			document.createPicture(id, document.getNextPicNameNumber(Document.PICTURE_TYPE_PNG), 640, 360);
			document.write(fos);
			fos.flush();
			fos.close();
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*----------------------------------------------------------------------------------------------------
	 * Method Name			: fcreateMasterHTML
	 * Arguments			: None
	 * Use 					: Modified HTML Master report
	 * Updated By			: Zeeshan
	 * Last Modified Date 	: 18-OCT-2019
	--------------------------------------------------------------------------------------------------------*/
	public static void fcreateMasterHTML(String Batches) throws IOException {
		File logfile = new File(masterrephtml.get());
		FileWriter fw = new FileWriter(logfile.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		String logmessage = "<!-- saved from url=(0016)http://localhost -->" + "<html>" + "<head>"
				+ "<title>Execution Results</title>"
				+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">"
				+ "<link rel=\"stylesheet\" href=\"" + "Scripts\\style.css\" type=\"text/css\">" + "<script src=\""
				+ "Scripts\\amcharts.js\" type=\"text/javascript\"></script>" + "<style>" + "table {font-size: 12px;"
				+ "background:#E6E6E6;" + "}" + "</style>" + "<script>" + "var chart;" + "var chartData = [{"
				+ "Status: \"Pass\"," + "Count:" + passUC + "}, {" + "Status: \"PartiallyPass\"," + "Count:"
				+ partialypassUC + "}, {" + "Status: \"Fail\"," + "Count:" + failUC + "}];"
				+ "AmCharts.ready(function () {" + "chart = new AmCharts.AmPieChart();" +
				// "chart.addTitle(\"Execution Status\", 16);"+
				"chart.dataProvider = chartData;" + "chart.titleField = \"Status\";" + "chart.valueField = \"Count\";"
				+ "chart.sequencedAnimation = true;" + "chart.startEffect = \"elastic\";"
				+ "chart.innerRadius = \"30%\";" + "chart.startDuration = 2;" + "chart.labelRadius = 5;"
				+ "chart.depth3D = 12;" + "chart.angle = 20;" + "chart.write(\"chartdiv\");" + "});" + "</script>" +
				// "</script>"+
				"</head>" + "<body bgcolor = \"green\">" + "<div id = \"lastres\">" + "<table width='100%' border=2>"
				+ "<tr>" + "<td width =20% border='0'>" + "<img src ='" + "Scripts\\"
				+ "Client-logo.jpg' height = 70% width = 100%>" + "</td>" + "<td width =60% Style=\"color:green\">"
				+ "<center><h1> " + Project.get() + " - Master Report </h1>  </center>" + "</td>" + "<td width =20% border='0'>"
				+ "<img src ='" + "Scripts\\" + "maveric-logo.jpg' height = 30% width = 100%>" + "</td>" + "</tr>"
				+ "<table width='100%' border=2>" + "<tr>"
				+ "<td align=\"center\" width='50%' colspan=2><h3>Execution overview </h3></td>"
				+ "<td align=\"center\" width='50%' colspan=2><h3>Execution status </h3></td>" + "</tr>" + "<tr>"
				+ "<td width='50%' align=\"center\" colspan = 2><div id=\"chartdiv\" style=\"width:450px; height:150px;\"></div></td>"
				+ "<td valign ='top'>" + "<table border =1 width = 100%>" + "<tr>"
				+ "<td align=\"center\"><b>Total</b></td>" + "<td align=\"center\"><b>Pass</b></td>"
				+ "<td width = 25% align=\"center\"><b>PartiallyPass</b></td>" + "<td align=\"center\"><b>Fail</b></td>"
				+ "</tr>" + "<tr>" + "<td align=\"center\" id=\"tot\">" + totalUCount + "</td>"
				+ "<td align=\"center\" id=\"totpass\">" + passUC + "</td>"
				+ "<td width = 25% align=\"center\" id=\"totpartialypass\">" + partialypassUC + "</td>"
				+ "<td align=\"center\" id=\"totfail\">" + failUC + "</td>" + "</tr>" + "</table><br/><br/>"
				+ "<table border =1 width = 100%>" + " <tr>"
				+ "<td align=\"center\" Style=\"color:GoldenRod\"><b>StartTime</b></td>"
				+ "<td align=\"center\" Style=\"color:GoldenRod\"><b>EndTime</b></td>" + "</tr>" + "<tr>"
				+ "<td align=\"center\" id=\"starttime\">" + ExecutionStarttimestr.get() + "</td>"
				+ "<td align=\"center\" id=\"endtime\">" + ExecutionEndtimestr.get() + "</td>" + "</tr>" + "</table>"
				+ "</tr>" + "</table>" + "<table width='100%' border=2>" + "<tr>"
				+ "<td align=\"center\" width='50%' colspan=3 Style=\"color:blue\"><h3> Summary Report </h3></td>"
				+ "</tr>" + "<table border =1 width = 100%>" + "<tr>"
				+ "<td width = 7%><b><center>Environment</center></b></td>"
				+ "<td width = 7%><b><center>Batch ID</center></b></td>"
				+ "<td width = 12%><b><center>Usecase</center></b></td>"
				+ "<td width = 12%><b><center>TestCase</center></b></td>"
				+ "<td width = 12%><b><center>Testcase ID/Description</center></b></td>"
				+ "<td width = 20%><b><center>UserInputs</center></b></td>"
				+ "<td width = 25%><b><center>TestCase Output</center></b></td>"
				+ "<td width = 5%><b><center>Status</center></b></td>" + "</tr>";
		bw.write(logmessage + "\r\n");
		logmessage = "";
		updatelogmsg = updatelogmsg + "<tr>" + "<td width = 7%><center>" + Environment.get() + "</center></td>";
		updatelogmsg = updatelogmsg + "<td width = 7%><center>" + Batches + "</center></td>";
		updatelogmsg = updatelogmsg + "<td width = 12%><center>" + UC.get() + "</center></td>";
		updatelogmsg = updatelogmsg + "<td width = 12%><center>"+ TestCaseN.get() + "</center></td>";
		updatelogmsg = updatelogmsg + "<td width = 12%>" + TestCaseDes.get() + "</td>";
		updatelogmsg = updatelogmsg + "<td width = 20%>" + TestCaseData.get() + "</td>";
	
		if (currUCstatus.get().equals("Pass") && UC.get().contains("DB_Connection")) {
            updatelogmsg = updatelogmsg + "<td width = 28%>" + TestOutput.get() + "</td>";
            updatelogmsg = updatelogmsg + "<td width = 6% Style=\"color:#04D215\"><b><center><a Style=\"color:#04D215\" href =.\\" + Batches +"\\" + UC.get() + "\\"
            + TestCaseN.get() + ".html" + ">Pass</a></center></b></td></tr>";
          }
		else if (currUCstatus.get().equals("PartiallyPass") && UC.get().contains("DB_Connection")) { 
			updatelogmsg = updatelogmsg + "<td width = 28%>" + TestOutput.get() + "</td>";
			updatelogmsg = updatelogmsg + "<td width = 6% Style=\"color:#04D215\"><b><center><a Style=\"color:#0D8ECF\" href =.\\" + Batches +"\\" + UC.get() + "\\"
					+ TestCaseN.get() + ".html" + ">PartiallyPass</a></center></b></td></tr>";
   }
   else if (currUCstatus.get().equals("Fail") && UC.get().contains("DB_Connection")) {
	   updatelogmsg = updatelogmsg + "<td width = 28%>" + TestOutput.get() + "\n" + "Failed at " + currKW_Des.get()
                + "</td>";
	   updatelogmsg = updatelogmsg     + "<td width = 6% Style=\"color:#FF0F00\"><b><center><a Style=\"color:#FF0F00\" href =.\\" + Batches + "\\" + UC.get() + "\\"
                + TestCaseN.get() + ".html" + ">Fail</a></center></b></td></tr>";
   }
   else if (currUCstatus.get().equals("Pass")) {
	   
	
	   //**************************************************
	
	   //reading Text form TestOutput variable and divide text into muliple String based on the </br> tag.
//	   String str=TestOutput.get();
//       String patternString = "<br/>";
//	   Pattern pattern = Pattern.compile(patternString);
//	   String[] split = pattern.split(str);
//	   String[] s=new String[split.length];
//	      for(int p=0; p<s.length; p++){
//	      //    System.out.println("element = " + split[p]);
//	    	    s[p]="<tr><td width = 20%>" + split[p]+""+"<br/></td></tr>";
//	      }
//	      String finalStr="";
//	      for(String element : s){
//	    //    System.out.println("element = " + element);
//	         finalStr=finalStr.concat(element);
//	    } 
//	    System.out.println(finalStr); 
//	    
	//    TestOutput.set(finalStr);
	    
	    //*********************************************************************
          updatelogmsg = updatelogmsg + "<td width = 28%>"+ 
          		"<table border=1 width=100% ><tbody> " + TestOutput.get() + "</tbody></table></td>";
          updatelogmsg = updatelogmsg
                       + "<td width = 6% Style=\"color:#04D215\"><b><center><a Style=\"color:#04D215\" href =.\\" + Batches +"\\" + UC.get() +"\\"
                       + TestCaseN.get() + ".docx" + ">Pass</a></center></b></td></tr>"; 
          
   } else if (currUCstatus.get().equals("PartiallyPass")) {
          updatelogmsg = updatelogmsg + "<td width = 28%>" + TestOutput.get() + "</td>";
          updatelogmsg = updatelogmsg
                       + "<td width = 6% Style=\"color:#0D8ECF\"><b><center><a Style=\"color:#0D8ECF\" href =.\\" + Batches +"\\" + UC.get() +"\\"
                       + TestCaseN.get() + ".docx" + ">PartiallyPass</a></center></b></td></tr>";
   } else if (currUCstatus.get().equals("Fail")) {
          updatelogmsg = updatelogmsg + "<td width = 28%> <table border=2 width=100% ><tbody>" + TestOutput.get() + " <td> " + "Failed at " + currKW_Des.get()
                       + "</td></tbody></table></td>";
          updatelogmsg = updatelogmsg      + "<td width = 6% Style=\"color:#FF0F00\"><b><a Style=\"color:#FF0F00\" href =.\\" + Batches +"\\" + UC.get() +"\\"
                       + TestCaseN.get() + ".docx" + ">Fail</a><center></center></b></td></tr>";	
          }

		bw.write(updatelogmsg);
		
		System.out.println();
		bw.close();
	}

	/*----------------------------------------------------------------------------------------------------
	 * Method Name			: createTCScreenshotFold
	 * Arguments			: None
	 * Use 					: to create the screenshot folder
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 23-Aug-2017
	--------------------------------------------------------------------------------------------------------*/
	public static void createTCScreenshotFold() {
		File tcscreenfold = new File(UCscreenfilepth.get() + "/" + TestCaseN.get() + ".docx");
		TCscreenfile.set(tcscreenfold.toString());
		try {
			@SuppressWarnings("resource")
			XWPFDocument document = new XWPFDocument();
			// Write the Document in file system
			FileOutputStream out = new FileOutputStream(new File(TCscreenfile.get()));
			document.write(out);
			out.close();
		} catch (Exception e) {

		}
	}

	public static void DisplayHTMLReport() {
		System.setProperty("webdriver.chrome.driver", WorkingDir.get() + "\\Drivers\\chromedriver.exe");
		String url = masterrephtml.get();
		System.out.println(url);
		try {
			// killexeTask();
			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("profile.default_content_setting_values.notifications", 2);
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("useAutomationExtension", false);
			options.addArguments("--disable-extensions");
			options.setExperimentalOption("prefs", prefs);
			WebDriver driver = new ChromeDriver(options);
			driver.get(url);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		ExtentReports extent = new ExtentReports();
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "\\Master.html");
		System.setProperty("logfilename", System.getProperty("user.dir") + "\\Logs");
		DOMConfigurator.configure("log4j.xml");
		extent.attachReporter(htmlReporter);
		ExtentTest test = extent.createTest("Testing name");
		test.pass("testing");
		extent.flush();
	}
}
