package Libraries;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class Keyword_API extends Driver {
	Common CO = new Common();

	public String RTB_Login() {

		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------RTB Login Event Details------");
		try {
			String RTB = Validatedata("RTB_Validation");
			if (RTB.equalsIgnoreCase("yes")) {
				URL.set(getdata("URL/HOST"));

				if (!URL.get().equals("")) {
					Result.fUpdateLog("Successfully set the End Point URL: " + URL.get());
					Status = "PASS";
				} else {
					Result.fUpdateLog("Failed to set the End Point URL");
					Status = "FAIL";
				}
			}
		} catch (Exception e) {
			Continue.set(false);
			Test_OutPut += "Failed at URL Fetch" + ",";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------RTB Login Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public String RTB() {

		String Test_OutPut = "", Status = "";
		String MSISDN, SOAP_Action, XMLResponse_Path = "", XMLRequest_Path = "";
		Result.fUpdateLog("------RTB Event Details------");

		try {
			String RTB = Validatedata("RTB_Validation");
			if (RTB.equalsIgnoreCase("yes")) {
				if (!(getdata("MSISDN").equals(""))) {
					MSISDN = getdata("MSISDN");
				} else {
					MSISDN = pulldata("MSISDN");
				}
				// MSISDN = "97478152935";
				if (!(getdata("SOAP_Action").equals(""))) {
					SOAP_Action = getdata("SOAP_Action");
				} else {
					SOAP_Action = pulldata("SOAP_Action");
				}

				String Templatefile = Templete_FLD.get() + "/XML/RTBQuery_Temp.xml";

				/* Print the request message */
				Result.fUpdateLog("Request SOAP Message = ");

				// Get and Store Request XML File Path
				String XMLfilepath = XMLfilepth.get();
				XMLRequest_Path = XMLfilepath + "/" + currKW.get() + "_" + MSISDN + "_Request.xml";

				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(new File(Templatefile));
				doc.getDocumentElement().normalize();

				// Get a set of the entries
				if (MSISDN != null) {
					String Tagname = "cmu:AccountId";
					CO.Setvalue(doc, Tagname, MSISDN);
				}

				// Save Request XMl into XMLRequest_Path
				Transformer xformer = TransformerFactory.newInstance().newTransformer();
				xformer.transform(new DOMSource(doc), new StreamResult(new File(XMLRequest_Path)));

				// Read the request XML File
				SOAPMessage message = CO.readSoapMessage(XMLRequest_Path, SOAP_Action);
				message.writeTo(System.out);

				// Establish SOAP Connection and send request to End Point URL
				SOAPMessage soapResponse = CO.XML_Request(message, URL.get());

				// Process the SOAP Response and store it
				XMLResponse_Path = XMLfilepath + "/" + currKW.get() + "_" + MSISDN + "_Response.xml";
				File Responsefile = new File(XMLResponse_Path);
				Responsefile.createNewFile();

				FileOutputStream fileOutputStream = new FileOutputStream(Responsefile);
				soapResponse.writeTo(fileOutputStream);
				fileOutputStream.flush();
				fileOutputStream.close();

				// Fetch Data from Soap Response
				DocumentBuilderFactory dbFactory1 = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder1 = dbFactory1.newDocumentBuilder();
				Document doc1 = dBuilder1.parse(new File(XMLResponse_Path));
				doc1.getDocumentElement().normalize();

				// Det count of Node : rtbrespabo:CmuBalanceSummaryVbc
				NodeList list = doc1.getElementsByTagName("rtbrespabo:CmuBalanceSummaryVbc");
				Result.fUpdateLog("Total of elements : " + list.getLength());

				for (int i = 0; i < list.getLength(); i++) {
					String Output = CO.getvalue(doc1, "rtbrespabo:CmuBalanceSummaryVbc", "rtbrespabo:BucketID", i);
					String Output1 = CO.getvalue(doc1, "rtbrespabo:CmuBalanceSummaryVbc", "rtbrespabo:AccountName", i);
					if (Output != "") {
						Result.fUpdateLog(Output + " : " + Output1);
						RTBOutputData.put(Output, Output1);
					}
				}

				if (Continue.get()) {
					Status = "PASS";
				} else
					Status = "FAIL";
			}
		} catch (Exception e) {
			Continue.set(false);
			Status = "FAIL";
			Test_OutPut += "Exception occurred" + ",";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
		Result.fUpdateLog("------RTB Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public String RTB_Check() {

		String Test_OutPut = "", Status = "";
		String MSISDN, SOAP_Action, XMLResponse_Path = "", XMLRequest_Path = "";
		Result.fUpdateLog("------RTB_Check Event Details------");

		try {
			String RTB = Validatedata("RTB_Validation");
			if (RTB.equalsIgnoreCase("yes")) {
				HashMap<String, String> RTBOutputData_Old = new HashMap<String, String>();
				RTBOutputData_Old.putAll(RTBOutputData);
				RTBOutputData.clear();

				if (!(getdata("MSISDN").equals(""))) {
					MSISDN = getdata("MSISDN");
				} else {
					MSISDN = pulldata("MSISDN");
				}
				// MSISDN = "97478152934";
				if (!(getdata("SOAP_Action").equals(""))) {
					SOAP_Action = getdata("SOAP_Action");
				} else {
					SOAP_Action = pulldata("SOAP_Action");
				}

				String Templatefile = Templete_FLD.get() + "/XML/RTBQuery_Temp.xml";

				/* Print the request message */
				Result.fUpdateLog("Request SOAP Message = ");

				// Get and Store Request XML File Path
				String XMLfilepath = XMLfilepth.get();
				XMLRequest_Path = XMLfilepath + "/" + currKW.get() + "_" + MSISDN + "_Request.xml";

				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(new File(Templatefile));
				doc.getDocumentElement().normalize();

				// Get a set of the entries
				if (MSISDN != null) {
					String Tagname = "cmu:AccountId";
					CO.Setvalue(doc, Tagname, MSISDN);
				}

				// Save Request XMl into XMLRequest_Path
				Transformer xformer = TransformerFactory.newInstance().newTransformer();
				xformer.transform(new DOMSource(doc), new StreamResult(new File(XMLRequest_Path)));

				// Read the request XML File
				SOAPMessage message = CO.readSoapMessage(XMLRequest_Path, SOAP_Action);
				message.writeTo(System.out);

				// Establish SOAP Connection and send request to End Point URL
				SOAPMessage soapResponse = CO.XML_Request(message, URL.get());

				// Process the SOAP Response and store it
				XMLResponse_Path = XMLfilepath + "/" + currKW.get() + "_" + MSISDN + "_Response.xml";
				File Responsefile = new File(XMLResponse_Path);
				Responsefile.createNewFile();

				FileOutputStream fileOutputStream = new FileOutputStream(Responsefile);
				soapResponse.writeTo(fileOutputStream);
				fileOutputStream.flush();
				fileOutputStream.close();

				// Fetch Data from Soap Response
				DocumentBuilderFactory dbFactory1 = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder1 = dbFactory1.newDocumentBuilder();
				Document doc1 = dBuilder1.parse(new File(XMLResponse_Path));
				doc1.getDocumentElement().normalize();

				// Det count of Node : rtbrespabo:CmuBalanceSummaryVbc
				NodeList list = doc1.getElementsByTagName("rtbrespabo:CmuBalanceSummaryVbc");
				Result.fUpdateLog("Total of elements : " + list.getLength());

				for (int i = 0; i < list.getLength(); i++) {
					String Output = CO.getvalue(doc1, "rtbrespabo:CmuBalanceSummaryVbc", "rtbrespabo:BucketID", i);
					String Output1 = CO.getvalue(doc1, "rtbrespabo:CmuBalanceSummaryVbc", "rtbrespabo:AccountName", i);
					if (Output != "") {
						if (RTBOutputData_Old.containsKey(Output)) {
							Continue.set(false);
						}
						Result.fUpdateLog(Output + " : " + Output1);
						RTBOutputData.put(Output, Output1);
					}

				}

				if (Continue.get()) {
					Status = "PASS";
				} else
					Status = "FAIL";
			}
		} catch (Exception e) {
			Continue.set(false);
			Status = "FAIL";
			Test_OutPut += "Exception occurred" + ",";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
		Result.fUpdateLog("------RTB_Check Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public String RTB_Compare() {

		String Test_OutPut = "", Status = "";
		String MSISDN, SOAP_Action, XMLResponse_Path = "", XMLRequest_Path = "";
		Result.fUpdateLog("------RTB_Compare Event Details------");

		try {
			String RTB = Validatedata("RTB_Validation");
			if (RTB.equalsIgnoreCase("yes")) {
				HashMap<String, String> RTBOutputData_Old = new HashMap<String, String>();
				RTBOutputData_Old.putAll(RTBOutputData);
				RTBOutputData.clear();

				if (!(getdata("NEW_MSISDN").equals(""))) {
					MSISDN = getdata("NEW_MSISDN");
				} else {
					MSISDN = pulldata("NEW_MSISDN");
				}
				// MSISDN = "97478152928";
				if (!(getdata("SOAP_Action").equals(""))) {
					SOAP_Action = getdata("SOAP_Action");
				} else {
					SOAP_Action = pulldata("SOAP_Action");
				}

				String Templatefile = Templete_FLD.get() + "/XML/RTBQuery_Temp.xml";

				/* Print the request message */
				Result.fUpdateLog("Request SOAP Message = ");

				// Get and Store Request XML File Path
				String XMLfilepath = XMLfilepth.get();
				XMLRequest_Path = XMLfilepath + "/" + currKW.get() + "_" + MSISDN + "_Request.xml";

				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(new File(Templatefile));
				doc.getDocumentElement().normalize();

				// Get a set of the entries
				if (MSISDN != null) {
					String Tagname = "cmu:AccountId";
					CO.Setvalue(doc, Tagname, MSISDN);
				}

				// Save Request XMl into XMLRequest_Path
				Transformer xformer = TransformerFactory.newInstance().newTransformer();
				xformer.transform(new DOMSource(doc), new StreamResult(new File(XMLRequest_Path)));

				// Read the request XML File
				SOAPMessage message = CO.readSoapMessage(XMLRequest_Path, SOAP_Action);
				message.writeTo(System.out);

				// Establish SOAP Connection and send request to End Point URL
				SOAPMessage soapResponse = CO.XML_Request(message, URL.get());

				// Process the SOAP Response and store it
				XMLResponse_Path = XMLfilepath + "/" + currKW.get() + "_" + MSISDN + "_Response.xml";
				File Responsefile = new File(XMLResponse_Path);
				Responsefile.createNewFile();

				FileOutputStream fileOutputStream = new FileOutputStream(Responsefile);
				soapResponse.writeTo(fileOutputStream);
				fileOutputStream.flush();
				fileOutputStream.close();

				// Fetch Data from Soap Response
				DocumentBuilderFactory dbFactory1 = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder1 = dbFactory1.newDocumentBuilder();
				Document doc1 = dBuilder1.parse(new File(XMLResponse_Path));
				doc1.getDocumentElement().normalize();

				// Det count of Node : rtbrespabo:CmuBalanceSummaryVbc
				NodeList list = doc1.getElementsByTagName("rtbrespabo:CmuBalanceSummaryVbc");
				Result.fUpdateLog("Total of elements : " + list.getLength());

				for (int i = 0; i < list.getLength(); i++) {
					String Output = CO.getvalue(doc1, "rtbrespabo:CmuBalanceSummaryVbc", "rtbrespabo:BucketID", i);
					String Output1 = CO.getvalue(doc1, "rtbrespabo:CmuBalanceSummaryVbc", "rtbrespabo:AccountName", i);
					if (Output != "") {
						if (!RTBOutputData_Old.containsKey(Output)) {
							Continue.set(false);
						}
						Result.fUpdateLog(Output + " : " + Output1);
						RTBOutputData.put(Output, Output1);

					}

				}

				if (Continue.get()) {
					Status = "PASS";
					Test_OutPut += "RTB Compare was succesful" + ",";
				} else {
					Status = "FAIL";
					Test_OutPut += "RTB Compare was not same" + ",";
				}
			}
		} catch (Exception e) {
			Continue.set(false);
			Status = "FAIL";
			Test_OutPut += "Exception occurred" + ",";
			Result.fUpdateLog("Exception occurred *** " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
		Result.fUpdateLog("------RTB_Compare Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

}
