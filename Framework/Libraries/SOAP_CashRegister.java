package Libraries;

import java.awt.RenderingHints.Key;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Random;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import Libraries.Driver;

/**
* Description : Functional Test Script
* 
 * @author Srikanth
*/
public class SOAP_CashRegister extends Driver {
	
	static Common CF = new Common();
	//Keyword_SiebelCRM Kw = new Keyword_SiebelCRM();
                /**
                * Script Name : <b>FCA_Function</b> Generated : <b>Feb 13, 2020 10:39:11
                * AM</b> Description : Functional Test Script Original Host : WinNT Version
                * 6.1 Build 7600 ()
                * 
                 * @since 2020/02/13
                * @author Srikanth
                */

                public static String Soap_RequestResponse() throws Exception {

                                String Saop_Response = "";

                                try {
                                                // HashMap TestData = Utilities_Lib.setCon_TestData(g_globalval,
                                                // Driver.TD_File, SCName, TestDataBinder);
                                                // Create SOAP Connection
                                                SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
                                                SOAPConnection soapConnection = soapConnectionFactory.createConnection();

                                                // Send SOAP Message to SOAP Server

                                                System.out.println("Connection Established");
                                                String url = "http://10.6.5.185:8080/IntegrationServices/ProcessOrderPaymentCallBack";
                                                // String OrderNo,String PaymentNo, String Amount, String
                                                // PaymentRowID)
                                                SOAPMessage soapResponse = soapConnection.call(createSOAPRequest_FCA(), url);

                                                System.out.println("Connection Successfull");

                                                // Process the SOAP Response
                                                Saop_Response = printSOAPResponse(soapResponse);
                                                System.out.println("SOAP Response "+Saop_Response);
                                                soapConnection.close();

                                } catch (Exception e) {
                                                System.err.println("Error occurred while sending SOAP Request to Server");
                                                e.printStackTrace();

                                }
                                return Saop_Response;
                }

                private static SOAPMessage createSOAPRequest_FCA() throws Exception {

                                String Order_Number = "";
                                String Row_Number = "";
                                String Row_Number1 = "";
                                String Transaction_Amt = "";
                                // String Row_Number="";

                                // HashMap TestData = Utilities_Lib.setCon_TestData(g_globalval,
                                // Driver.TD_File, SCName, TestDataBinder);

                                MessageFactory messageFactory = MessageFactory.newInstance();
                                SOAPMessage soapMessage = messageFactory.createMessage();
                                SOAPPart soapPart = soapMessage.getSOAPPart();

                                String proc = "http://batelco.com/wsdl/ProcessOrderPaymentCallBack";
                                String com = "http://batelco.com/xsd/Common";
                                String proc1 = "http://batelco.com/xsd/ProcessOrderPaymentCallBack";

                                // SOAP Envelope
                                SOAPEnvelope envelope = soapPart.getEnvelope();
                                envelope.addNamespaceDeclaration("proc", proc);
                                envelope.addNamespaceDeclaration("com", com);
                                envelope.addNamespaceDeclaration("proc1", proc1);

                                SOAPHeader soapHeader = envelope.getHeader();
                              

                                SOAPBody soapBody = envelope.getBody();
                                SOAPElement Proc_ordPay = soapBody.addChildElement("OrderPaymentCallBackRequestMessage", "proc");
                                SOAPElement Proc_ReqHead = Proc_ordPay.addChildElement("RequestHeader", "proc");
                                SOAPElement Com_TranID = Proc_ReqHead.addChildElement("TransactionID", "com"); // com--parent'
            
                                 String Transaction_Ramdom_number=  CF.CallRandom();
                                 //  Com_TranID.addTextNode("1114");
                                     Com_TranID.addTextNode(Transaction_Ramdom_number);
                                 
                                SOAPElement Com_TranDT = Proc_ReqHead.addChildElement("TransactionDateTime", "com");
                                Com_TranDT.addTextNode("2019-10-15T00:00:00");
                                SOAPElement Com_SouName = Proc_ReqHead.addChildElement("SourceSystemName", "com");
                                Com_SouName.addTextNode("CRS");
                                SOAPElement Proc_PayLoad = Proc_ordPay.addChildElement("Payload", "proc");

                                // Fetch Order Number in Run Time
                                /*
                                * Order_Number= CF.fetchRuntimeValue(g_globalval,TestData.get(
                                * "Store_Dynamic_OrderNumber").toString(),"SalesOrderCreation");
                                * System.out.println(Order_Number);
                                */

                                // Order_Number=
                                // CF.fetchRuntimeValue(g_globalval,TestData.get("Store_Dynamic_OrderNumber").toString(),"OrderDetails");
                                 
                                
                                  Order_Number= Keyword_SiebelCRM.ordernum;
                                //  Order_Number= "20010000238";
                                     
                                // Order_Number = "17040396779";
//                                System.out.println("HIiii");
                                System.out.println("OrderNumber" + Order_Number);

                                SOAPElement Proc1_Ordno = Proc_PayLoad.addChildElement("OrderNumber", "proc1");
                                Proc1_Ordno.addTextNode(Order_Number);

                                // Fetch Payment Reciept Number in Run Time / Can be random
                                SOAPElement Proc1_PayReNo = Proc_PayLoad.addChildElement("PaymentReceiptNumber", "proc1");
                                Proc1_PayReNo.addTextNode("RAM/0141/"+Transaction_Ramdom_number+" ");

                                /*
                                * SOAPElement Order_ID = Proc_PayLoad.addChildElement("OrderID",
                                * "proc1"); Proc1_PayReNo.addTextNode(Order_Number);
                                */
                                Transaction_Amt= Double.toString(Keyword_SiebelCRM.PaymentAmount );                 
                                SOAPElement Proc1_RecPayAmunt = Proc_PayLoad.addChildElement("ReceivedPaymentAmount", "proc1");
                                Proc1_RecPayAmunt.addTextNode(Transaction_Amt);
                              
                                SOAPElement Proc1_OrderID = Proc_PayLoad.addChildElement("OrderID", "proc1");
                                //Order RowID
                                 Row_Number1 = Keyword_SiebelCRM.Payment_ORDERID;
                               // Row_Number1 ="1-1EP7T7N3";
                                Proc1_OrderID.addTextNode(Row_Number1);
                               System.out.println(Row_Number1);
                                SOAPElement Proc1_OrderRevision = Proc_PayLoad.addChildElement("OrderRevision", "proc1");
                                Proc1_OrderRevision.addTextNode("1");
                                SOAPElement Proc1_ActionType = Proc_PayLoad.addChildElement("ActionType", "proc1");
                                Proc1_ActionType.addTextNode("COL");
                                SOAPElement Proc1_PymtDetail = Proc_PayLoad.addChildElement("PaymentDetails", "proc1");
                                
                                
                                
                                //Payment Row ID
                                Row_Number = Keyword_SiebelCRM.Payment_PAYMENTID;   
                                //Row_Number ="1-1EP7TDBI";
                                
                                System.out.println("Finding element :::"+Row_Number);

                                SOAPElement Proc1_PymtID = Proc1_PymtDetail.addChildElement("PaymentID", "proc1");
                                Proc1_PymtID.addTextNode(Row_Number);

                                SOAPElement Proc1_PymtMode = Proc1_PymtDetail.addChildElement("PaymentMode", "proc1");
                                Proc1_PymtMode.addTextNode("Cash Register");

                                SOAPElement Proc1_PymtStatus = Proc1_PymtDetail.addChildElement("PaymentStatus", "proc1");
                                Proc1_PymtStatus.addTextNode("Paid");//  (OR)
                                // Proc1_PymtStatus.addTextNode("Cancelled");

                                // Fetch Transaction Amount in Run Time
                                // Transaction_Amt =Driver.readpropval(Driver.getdata("Store_Order_Total"));
                                Transaction_Amt =Double.toString(Keyword_SiebelCRM.PaymentAmount);
                                
                                //Transaction Money like BD7.000
                               // Transaction_Amt = "12";
                                System.out.println(Transaction_Amt);
                                SOAPElement Proc1_TransAmt = Proc1_PymtDetail.addChildElement("TransactionAmount", "proc1");
                                Proc1_TransAmt.addTextNode(Transaction_Amt);

                                SOAPElement Proc1_PaymentTransactionID = Proc1_PymtDetail.addChildElement("PaymentTransactionID", "proc1");
                                //Proc1_PaymentTransactionID.addTextNode("1114");
                                Proc1_PaymentTransactionID.addTextNode(Transaction_Ramdom_number);
                                
                                
                                MimeHeaders headers = soapMessage.getMimeHeaders();
                                headers.addHeader("SOAPAction", proc1 + "SubActivationRequestMsg");

                                soapMessage.saveChanges();

                                /* Print the request message */
                                System.out.print("Request SOAP Message = ");
                                soapMessage.writeTo(System.out);
                                System.out.println();
                                return soapMessage;

                }

                private static String printSOAPResponse(SOAPMessage soapResponse) throws Exception {
                                String output = "";
                                String Result = "";
                                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                                Transformer transformer = transformerFactory.newTransformer();
                                Source sourceContent = soapResponse.getSOAPPart().getContent();
                                System.out.print("\nResponse SOAP Message = ");
                                /*
                                * StreamResult result = new StreamResult(System.out);
                                * transformer.transform(sourceContent, result);
                                */

                                StringWriter writer = new StringWriter();
                                transformer.transform(sourceContent, new StreamResult(writer));
                                String out = writer.toString();
                                System.out.println(out);
                                String[] p = out.split("><");

                                for (int i = 0; i < p.length; i++) {
                                                if ((p[i]).contains("Success")) {
                                                                output = p[i];
                                                                System.out.println(p[i]);
                                                                Result = "PASS";
                                                } else if ((p[i]).contains("com:StatusDesc") && !(p[i]).contains("Success")) {

                                                                System.out.println(" Payment Failed ");
                                                                Result = "FAIL";
                                                }
                                }
                                return Result;

                }

              /*  public static void main(String argv[]) {
                                String s;
                                try {
                                                s = Soap_RequestResponse();
                                                System.out.println(s);
                                } catch (Exception e) {
                                                e.printStackTrace();
                                }

                }*/
}
