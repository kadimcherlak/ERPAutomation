/*===============================================================================================================================
        CLASS Name:    ERP_uploadFileToUcm
        CREATED BY:    Koushik Kadimcherla
        DATE CREATED:  Nov 2017
        DESCRIPTION:   Method to trigger ERP_uploadFileToUcm webservice                    
        PARAMETERS:                                                                  
        RETURNS:      
        COMMENTS:                                     
        Modification Log:
        Date                             Initials                                                Modification
        
-------------         ------------    ------------------------------------------------------------------------------------------------------------------------------*/

package OCTS_Finance_Webservices;
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

public class ERP_uploadFileToUcm {

	String resultuploadFile;

public String triggerUploadFileToUcm(String auth,String encStr,String cellvalue_ufilename, String cellvalue_uContentType, String cellvalue_uDocumentTitle, String cellvalue_uDocumentAuthor,  String cellvalue_uDocumentSecurityGroup,String cellvalue_uDocumentAccount, String cellvalue_uDocumentName, String cellvalue_uDocumentID) throws Exception
{
    //try {
	System.out.println("inside uploadFileToUCM");
	 MessageFactory factory = MessageFactory.newInstance();
     SOAPMessage soapmsg = factory.createMessage();
     SOAPPart soapPart = soapmsg.getSOAPPart();

       //  String authorization = "MjM1ODU5OldlbGNvbWUx";
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
     SOAPElement soapBodyElem = soapBody.addChildElement("uploadFileToUcm", myNamespace);
     SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("document", myNamespace);
     SOAPElement soapBodyElem2 = soapBodyElem1.addChildElement("Content", myNamespace1);
   //  soapBodyElem2.addTextNode("UEsDBBQAAAAIABsAbUsakRaWsQAAADYCAAAPAAAAR2xJbnRlcmZhY2UuY3N2tZDNCoJAEMfvQe+wDzDlzqykHhMNDDOIoPNigoJo6Pr+7bZGJAge6n8Y5nuGXxbfQHAr4ZFA9IA4ug4nB104q7LoRptkF1PyHO455MPeBJzDaxS0h4icxtDaWenura2fZDPImoVS5SUxJLgWvWLHdugaWaPNb1DAV56Rbnz7hzBKWNrKu37RKK2agkVFn3fVQ1Vtg9PbfhAgmZXzirNovcp+i2a3DM2Hzf/R0PT0UjRPUEsBAhQAFAAAAAgAGwBtSxqRFpaxAAAANgIAAA8AAAAAAAAAAQAgAAAAAAAAAEdsSW50ZXJmYWNlLmNzdlBLBQYAAAAAAQABAD0AAADeAAAAAAA=");
     soapBodyElem2.addTextNode(encStr);
     SOAPElement soapBodyElem3 = soapBodyElem1.addChildElement("FileName", myNamespace1);
    // soapBodyElem3.addTextNode("GlInterface.zip");
     soapBodyElem3.addTextNode(cellvalue_ufilename);
     SOAPElement soapBodyElem4 = soapBodyElem1.addChildElement("ContentType", myNamespace1);
   //  soapBodyElem4.addTextNode("zip");
     soapBodyElem4.addTextNode(cellvalue_uContentType);
     SOAPElement soapBodyElem5 = soapBodyElem1.addChildElement("DocumentTitle", myNamespace1);
   //  soapBodyElem5.addTextNode("Fusion1");
     soapBodyElem5.addTextNode(cellvalue_uDocumentTitle);
     SOAPElement soapBodyElem6 = soapBodyElem1.addChildElement("DocumentAuthor", myNamespace1);
    //soapBodyElem6.addTextNode("Fusion");
     soapBodyElem6.addTextNode(cellvalue_uDocumentAuthor);
     SOAPElement soapBodyElem7 = soapBodyElem1.addChildElement("DocumentSecurityGroup", myNamespace1);
    // soapBodyElem7.addTextNode("FAFusionImportExport");
     soapBodyElem7.addTextNode(cellvalue_uDocumentSecurityGroup);
     SOAPElement soapBodyElem8 = soapBodyElem1.addChildElement("DocumentAccount", myNamespace1);
    // soapBodyElem8.addTextNode("fin$/generalLedger$/import$");
     soapBodyElem8.addTextNode(cellvalue_uDocumentAccount);
     SOAPElement soapBodyElem9 = soapBodyElem1.addChildElement("DocumentName", myNamespace1);
     soapBodyElem9.addTextNode(cellvalue_uDocumentName);
     SOAPElement soapBodyElem10 = soapBodyElem1.addChildElement("DocumentId", myNamespace1);
     soapBodyElem10.addTextNode(cellvalue_uDocumentID);
     //soapBodyElem10.addTextNode("#NULL");

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
             resultuploadFile=list.item(0).getTextContent();

            soapConnection.close();
        } catch (Exception e) {
        	resultuploadFile="UploadFileToUcm: Error occurred while sending SOAP Request to Server";
            System.err.println("\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
            e.printStackTrace();
        }
		return resultuploadFile;
    }

}