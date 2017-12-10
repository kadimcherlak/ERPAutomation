/*===============================================================================================================================
        CLASS Name:    ERP_executeXMLQuery
        CREATED BY:    Raghavendran Ramasubramanian
        DATE CREATED:  Nov 2017
        DESCRIPTION:   Method to trigger ERP_executeXMLQuery webservice               
        PARAMETERS:                                                                  
        RETURNS:      
        COMMENTS:                                     
        Modification Log:
        Date                             Initials                                                Modification
        
-------------         ------------    ------------------------------------------------------------------------------------------------------------------------------*/

package OCTS_HCM_Webservices;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class ERP_executeXMLQuery {
	String resultgetXML;

	@SuppressWarnings({ "unused" })
public String getXMLQuery(String sessID) throws Exception
	{
	    
	        MessageFactory factory = MessageFactory.newInstance();
	        SOAPMessage soapmsg = factory.createMessage();
	        SOAPPart soapPart = soapmsg.getSOAPPart();

	        String myNamespaceURI = "http://schemas.xmlsoap.org/soap/envelope/";
	        String myNamespace = "soapenv";
	        String myNamespaceURI1 = "urn://oracle.bi.webservices/v7";
	        String myNamespace1 = "v7";
	        String destination = "https://ecqg-test.fs.us2.oraclecloud.com/analytics-ws/saw.dll?SoapImpl=xmlViewService";



	        SOAPEnvelope envelope = soapPart.getEnvelope();
	        envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);
	        envelope.addNamespaceDeclaration(myNamespace1, myNamespaceURI1);
	        System.out.println("\n");
	        SOAPHeader header = envelope.getHeader();
	        SOAPBody soapBody = envelope.getBody();
	        SOAPElement soapBodyElem = soapBody.addChildElement("executeXMLQuery", myNamespace1);
	        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("report", myNamespace1);
	        SOAPElement soapBodyElem2 = soapBodyElem1.addChildElement("reportPath", myNamespace1);
	        soapBodyElem2.addTextNode("/shared/Custom/HCMEMPEXT/Employee Data1");
	        SOAPElement soapBodyElem3 = soapBodyElem.addChildElement("outputFormat", myNamespace1);
	        SOAPElement soapBodyElem4 = soapBodyElem.addChildElement("executionOptions", myNamespace1);
	        SOAPElement soapBodyElem5 = soapBodyElem4.addChildElement("async", myNamespace1);
	        soapBodyElem5.addTextNode("false");
	        SOAPElement soapBodyElem6 = soapBodyElem4.addChildElement("maxRowsPerPage", myNamespace1);
	        soapBodyElem6.addTextNode("100");
	        SOAPElement soapBodyElem7 = soapBodyElem4.addChildElement("refresh", myNamespace1);
	        soapBodyElem7.addTextNode("false");
	        SOAPElement soapBodyElem8 = soapBodyElem4.addChildElement("presentationInfo", myNamespace1);
	        soapBodyElem8.addTextNode("false");
	        SOAPElement soapBodyElem9 = soapBodyElem.addChildElement("sessionID", myNamespace1);
	        soapBodyElem9.addTextNode(sessID);
	        
	        try {
	            // Create SOAP Connection
	            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
	            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

	            // Send SOAP Message to SOAP Server
	            SOAPMessage soapResponse = soapConnection.call(soapmsg,destination);
	           
	            // Print the SOAP Response
	            System.out.println("Response SOAP Message:");
	            //soapResponse.writeTo(System.out);
	            //System.out.println();
	           
	            Document document = soapResponse.getSOAPBody().extractContentAsDocument();
	            NodeList list = document.getChildNodes();
	            System.out.println(list.item(0).getTextContent());
	            resultgetXML =  list.item(0).getTextContent();
	            soapConnection.close();
	        } catch (Exception e) {
	        	resultgetXML="getSessionID: Error occurred while sending SOAP Request to Server";
	            System.err.println("\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
	            e.printStackTrace();
	            return "Error"+e;
	        }
			return resultgetXML;
	    }

}