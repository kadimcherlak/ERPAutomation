/*===============================================================================================================================
        CLASS Name:    ERP_submitESSJobRequest_Loader
        CREATED BY:    Koushik Kadimcherla
        DATE CREATED:  Nov 2017
        DESCRIPTION:   Method to trigger ERP_submitESSJobRequest_Loader webservice                    
        PARAMETERS:                                                                  
        RETURNS:      
        COMMENTS:                                     
        Modification Log:
        Date                             Initials                                                Modification
        
-------------         ------------    ------------------------------------------------------------------------------------------------------------------------------*/

package OCTS_HCM_Webservices;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.net.ssl.*;
import javax.xml.soap.*;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Vector;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.FileOutputStream;
import java.io.ByteArrayOutputStream;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.soap.*;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.*;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
//import com.sun.org.apache.xml.internal.security.utils.Base64;

public class ERP_submitESSJobRequest_Loader {

String resultsubmitESSJobRequest_Loader;

public String triggersubmitESSJobRequest_Loader(String auth,String cellvalue_ujobPackageName, String cellvalue_ujobDefinitionName, String cellvalue_uLoaderParamList1, String cellvalue_uLoaderParamList2, String cellvalue_uLoaderParamList3,String cellvalue_uLoaderParamList4,String cellvalue_uLoaderParamList5,String cellvalue_uLoaderParamList6,String cellvalue_uLoaderParamList7,String cellvalue_uLoaderParamList8,String cellvalue_uLoaderParamList9,String cellvalue_uLoaderParamList10,String cellvalue_uLoaderParamList11) throws Exception
{
    //try {
        MessageFactory factory = MessageFactory.newInstance();
        SOAPMessage soapmsg = factory.createMessage();
        SOAPPart soapPart = soapmsg.getSOAPPart();

         //   String authorization = "MjM1ODU5OldlbGNvbWUx";
            String authorization = auth;
            MimeHeaders hd = soapmsg.getMimeHeaders();
            hd.addHeader("Authorization", "Basic " + authorization);

        String myNamespaceURI = "http://xmlns.oracle.com/apps/financials/commonModules/shared/model/erpIntegrationService/types/";
        String myNamespace = "typ";
        String myNamespaceURI1 = "http://xmlns.oracle.com/apps/financials/commonModules/shared/model/erpIntegrationService/";
        String myNamespace1 = "erp";
        String destination = "https://ecqg-test.fin.us2.oraclecloud.com:443/publicFinancialCommonErpIntegration/ErpIntegrationService";



        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);
        envelope.addNamespaceDeclaration(myNamespace1, myNamespaceURI1);
        System.out.println("\n");
        SOAPHeader header = envelope.getHeader();
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("submitESSJobRequest", myNamespace);
        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("jobPackageName", myNamespace);
       // soapBodyElem1.addTextNode("/oracle/apps/ess/financials/commonModules/shared/common/interfaceLoader");
        soapBodyElem1.addTextNode(cellvalue_ujobPackageName);
        SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("jobDefinitionName", myNamespace);
        soapBodyElem2.addTextNode(cellvalue_ujobDefinitionName);
        SOAPElement soapBodyElem3 = soapBodyElem.addChildElement("paramList", myNamespace);
        soapBodyElem3.addTextNode(cellvalue_uLoaderParamList1);
        SOAPElement soapBodyElem4 = soapBodyElem.addChildElement("paramList", myNamespace);
        soapBodyElem4.addTextNode(cellvalue_uLoaderParamList2);
        SOAPElement soapBodyElem5 = soapBodyElem.addChildElement("paramList", myNamespace);
        soapBodyElem5.addTextNode(cellvalue_uLoaderParamList3);
        SOAPElement soapBodyElem6 = soapBodyElem.addChildElement("paramList", myNamespace);
        soapBodyElem6.addTextNode(cellvalue_uLoaderParamList4);
        SOAPElement soapBodyElem7 = soapBodyElem.addChildElement("paramList", myNamespace);
        soapBodyElem7.addTextNode(cellvalue_uLoaderParamList5);
        SOAPElement soapBodyElem8 = soapBodyElem.addChildElement("paramList", myNamespace);
        soapBodyElem8.addTextNode(cellvalue_uLoaderParamList6);
        SOAPElement soapBodyElem9 = soapBodyElem.addChildElement("paramList", myNamespace);
        soapBodyElem9.addTextNode(cellvalue_uLoaderParamList7);
        SOAPElement soapBodyElem10 = soapBodyElem.addChildElement("paramList", myNamespace);
        soapBodyElem10.addTextNode(cellvalue_uLoaderParamList8);
        SOAPElement soapBodyElem11 = soapBodyElem.addChildElement("paramList", myNamespace);
        soapBodyElem11.addTextNode(cellvalue_uLoaderParamList9);
        SOAPElement soapBodyElem12 = soapBodyElem.addChildElement("paramList", myNamespace);
        soapBodyElem12.addTextNode(cellvalue_uLoaderParamList10);
        SOAPElement soapBodyElem13 = soapBodyElem.addChildElement("paramList", myNamespace);
        soapBodyElem13.addTextNode(cellvalue_uLoaderParamList11);
        
        try {
            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            // Send SOAP Message to SOAP Server
            SOAPMessage soapResponse = soapConnection.call(soapmsg,destination);

            // Print the SOAP Response
            System.out.println("Response SOAP Message:");
            soapResponse.writeTo(System.out);
            System.out.println();
            
            Document document = soapResponse.getSOAPBody().extractContentAsDocument();
            NodeList list = document.getChildNodes();
            System.out.println(list.item(0).getTextContent());
            resultsubmitESSJobRequest_Loader=list.item(0).getTextContent();
            soapConnection.close();
        } catch (Exception e) {
        	resultsubmitESSJobRequest_Loader="submitESSJobRequest-Loader: Error occurred while sending SOAP Request to Server";
            System.err.println("\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
            e.printStackTrace();
        }
		return resultsubmitESSJobRequest_Loader;
    }
}