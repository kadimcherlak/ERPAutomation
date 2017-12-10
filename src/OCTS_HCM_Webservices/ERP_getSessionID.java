/*===============================================================================================================================
        CLASS Name:    ERP_getSessionID
        CREATED BY:    Raghavendran Ramasubramanian
        DATE CREATED:  Nov 2017
        DESCRIPTION:   Method to trigger ERP_getSessionID webservice               
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

public class ERP_getSessionID {
	String resultgetSessionID;

	@SuppressWarnings({ "unused" })
	public String getSessionID(String username,String password) throws Exception
	{
	    
	        MessageFactory factory = MessageFactory.newInstance();
	        SOAPMessage soapmsg = factory.createMessage();
	        SOAPPart soapPart = soapmsg.getSOAPPart();

	        String myNamespaceURI = "http://schemas.xmlsoap.org/soap/envelope/";
	        String myNamespace = "soapenv";
	        String myNamespaceURI1 = "urn://oracle.bi.webservices/v7";
	        String myNamespace1 = "v7";
	        String destination = "https://ecqg-test.fs.us2.oraclecloud.com/analytics-ws/saw.dll?SoapImpl=nQSessionService";



	        SOAPEnvelope envelope = soapPart.getEnvelope();
	        envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);
	        envelope.addNamespaceDeclaration(myNamespace1, myNamespaceURI1);
	        System.out.println("\n");
	        SOAPHeader header = envelope.getHeader();
	        SOAPBody soapBody = envelope.getBody();
	        SOAPElement soapBodyElem = soapBody.addChildElement("logon", myNamespace1);
	        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("name", myNamespace1);
	        soapBodyElem1.addTextNode(username);
	        SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("password", myNamespace1);
	        soapBodyElem2.addTextNode(password);
	       
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
	            resultgetSessionID =  list.item(0).getTextContent();
	            soapConnection.close();
	        } catch (Exception e) {
	        	resultgetSessionID="getSessionID: Error occurred while sending SOAP Request to Server";
	            System.err.println("\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
	            e.printStackTrace();
	            return "Error"+e;
	        }
			return resultgetSessionID;
	    }

}