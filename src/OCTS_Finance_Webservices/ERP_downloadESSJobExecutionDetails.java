/*===============================================================================================================================
        CLASS Name:    ERP_downloadESSJobExecutionDetails
        CREATED BY:    Koushik Kadimcherla
        DATE CREATED:  Nov 2017
        DESCRIPTION:   Method to trigger ERP_downloadESSJobExecutionDetails webservice                    
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
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.activation.DataHandler;
import javax.imageio.ImageIO;
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
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
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

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.sun.xml.internal.ws.util.StringUtils;

import Common_Utility.Logger;
import OCTS_Finance_Automation_TestCases.ERP_Financial_Webservice_MainClass;
import sun.misc.BASE64Decoder;

public class ERP_downloadESSJobExecutionDetails {

String resultdownloadESSJobExecutionDetails;

public String triggerdownloadESSJobExecutionDetails(String auth,String resultgetESSJobStatus_ReqImport,int resultsubmitESSJobRequest_ReqImport_New) throws Exception
{
    //try {
	String returnstatus = null;
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
        SOAPElement soapBodyElem = soapBody.addChildElement("downloadESSJobExecutionDetails", myNamespace);
        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("requestId", myNamespace);       
        soapBodyElem1.addTextNode(resultgetESSJobStatus_ReqImport);
        SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("fileType", myNamespace);     
        soapBodyElem2.addTextNode("Out");
       
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
         //   System.out.println(list.item(0).getTextContent());
              
            System.out.println(soapResponse.countAttachments()+"count");
            Iterator attachmentsIterator = soapResponse.getAttachments();
            while (attachmentsIterator.hasNext()) {
                AttachmentPart attachment = (AttachmentPart) attachmentsIterator.next();
             //   System.out.println("ATTACHMENT base 64=" + attachment.getBase64Content());
                DataHandler dh = attachment.getDataHandler();
            /*    System.out.println("ATTACHMENT CONTENT ID=" + attachment.getContentId());
                System.out.println("ATTACHMENT CONTENT LOCATION="+ attachment.getContentLocation());
                System.out.println("ATTACHMENT CONTENT ="+ attachment.getContent());
                System.out.println("ATTACHMENT TO_STRING ="+ attachment.toString());*/
                FileOutputStream fileStream = null;
             

                fileStream = new FileOutputStream("C:\\Automation_OCTS\\Output\\"+resultsubmitESSJobRequest_ReqImport_New+".zip");

                dh.writeTo(fileStream);

                fileStream.flush();
                returnstatus="true";
            }
            
            soapConnection.close();
        } catch (Exception e) {
        	 returnstatus ="DownloadESSJobExcutionDetails:Error occurred while sending SOAP Request to Server";
            System.err.println("\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
            e.printStackTrace();
        }
		return returnstatus;
    }



}