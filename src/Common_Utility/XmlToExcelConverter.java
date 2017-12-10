/*===============================================================================================================================
        CLASS Name:    XmlToExcelConverter
        CREATED BY:    Raghavendran Ramasubramanian
        DATE CREATED:  Nov 2017
        DESCRIPTION:   Converts XML File contents to Excel Sheet for Data Sync Activity                   
        PARAMETERS:                                                                  
        RETURNS:      
        COMMENTS:                                     
        Modification Log:
        Date                             Initials                                                Modification
        
-------------         ------------    ------------------------------------------------------------------------------------------------------------------------------*/


package Common_Utility;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import OCTS_UI.FXMLController;

import org.w3c.dom.CharacterData;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileOutputStream;
import java.io.StringReader;

public class XmlToExcelConverter {
	private static XSSFWorkbook workbook;
	private static int rowNum;

	//private final static int RowNo = 0;
	private final static int Column00 = 0;
	private final static int Column01 = 1;
	private final static int Column02 = 2;
    String result;
	
	public String getAndReadXml(String xml) throws Exception {
		NodeList nList;
		try {
			System.out.println("getAndReadXml");
			Logger.logInfo(XmlToExcelConverter.class,"getAndReadXml");
			
			//File xmlFile = new File(filepath+"xml.txt");
			InputSource is1 = new InputSource(new StringReader(xml));
			
			initXls();

			Sheet sheet = workbook.getSheetAt(0);

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(is1);
			
			
			/*NodeList nodes = doc.getElementsByTagName("sawsoap:return");
			Element elementCDATA = (Element) nodes.item(0);
			NodeList title = elementCDATA.getElementsByTagName("sawsoap:rowset");
			Element line = (Element) title.item(0);
			String xmlCDATA = getCharacterDataFromElement(line);
			
			InputSource is = new InputSource(new StringReader(xmlCDATA));
			Document doc1 = dBuilder.parse(is);*/
			  
			nList = doc.getElementsByTagName("Row");
			for (int i = 0; i < nList.getLength(); i++) {
				System.out.println("Processing element " + (i + 1) + "/" + nList.getLength());
				Logger.logInfo(XmlToExcelConverter.class,"Processing element " + (i + 1) + "/" + nList.getLength());
				Node node = nList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					String column0 = element.getElementsByTagName("Column0").item(0).getTextContent();
					String column1 = element.getElementsByTagName("Column1").item(0).getTextContent();
					String column2 = element.getElementsByTagName("Column2").item(0).getTextContent();

							Row row = sheet.createRow(rowNum++);
							/*Cell cell = row.createCell(RowNo);
							cell.setCellValue("RowNo:"+(i+1));*/

							Cell cell = row.createCell(Column00);
							cell.setCellValue(column0);

							cell = row.createCell(Column01);
							cell.setCellValue(column1);
							
							cell = row.createCell(Column02);
							cell.setCellValue(column2);

						}
					}
				
			FileOutputStream fileOut = new FileOutputStream("C:\\Automation_OCTS\\Output\\HCMSyncTestData.xlsx");
			workbook.write(fileOut);
			workbook.close();
			fileOut.close();

			result = "getAndReadXml finished, processed " + nList.getLength() + " Rows!";
			/*if (xmlFile.exists()) {
				System.out.println("delete file-> " + xmlFile.getAbsolutePath());
				if (!xmlFile.delete()) {
					System.out.println("file '" + xmlFile.getAbsolutePath() + "' was not deleted!");
				}
			}*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Error"+e;
		}

		
		return result;
		//System.out.println("getAndReadXml finished, processed " + nList.getLength() + " Rows!");
	}

	/**
	 * Initializes the POI workbook and writes the header row
	 */
	private static void initXls() {
		workbook = new XSSFWorkbook();

		CellStyle style = workbook.createCellStyle();
		Font boldFont = workbook.createFont();
		boldFont.setBold(true);
		style.setFont(boldFont);

		Sheet sheet = workbook.createSheet();
		rowNum = 0;
		Row row = sheet.createRow(rowNum++);
		/*Cell cell = row.createCell(RowNo);
		cell.setCellValue("RowNo");
		cell.setCellStyle(style);*/

		Cell cell = row.createCell(Column00);
		cell.setCellValue("PersonNumber");
		cell.setCellStyle(style);

		cell = row.createCell(Column01);
		cell.setCellValue("NationalID");
		cell.setCellStyle(style);

		cell = row.createCell(Column02);
		cell.setCellValue("PersonType");
		cell.setCellStyle(style);

	}
		  public static String getCharacterDataFromElement(Element e) {
		    Node child = e.getFirstChild();
		    if (child instanceof CharacterData) {
		      CharacterData cd = (CharacterData) child;
		      return cd.getData();
		    }
		    return "";
		  }
}
		  
