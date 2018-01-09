/*===============================================================================================================================
        CLASS Name:    FXMLController
        CREATED BY:    Raghavendran Ramasubramanian
        MODIFIED BY:   Koushik Kadimcherla
        DATE CREATED:  Nov 2017
        DESCRIPTION:   Controller to Run and handle inputs from UI                    
        PARAMETERS:                                                                  
        RETURNS:      
        COMMENTS:                                     
        Modification Log:
        Date                             Initials                                                Modification
        
-------------         ------------    ------------------------------------------------------------------------------------------------------------------------------*/

package OCTS_UI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Common_Utility.CommonUtilFunctions;
import Common_Utility.CompareExcel;
import Common_Utility.Logger;
import Common_Utility.ReporterBaseTest;
import Common_Utility.XmlToExcelConverter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import OCTS_Automation_Main_Modules.Invoke_TestNG_Classes;
import OCTS_HCM_Webservices.ERP_executeXMLQuery;
import OCTS_HCM_Webservices.ERP_getSessionID;
import Common_Utility.TextToExcel;

@SuppressWarnings({ "rawtypes", "deprecation","unused" })
public class FXMLController implements Initializable {

	// Reference to the main application

	private MainUIFX mainApp;
    
	String inputfile_fp;
	String module_fp;
	String testModule;
	Task copyWorker;
	String newLine = "\n";
	String Item;
	String sessionID;
	String xml;
	String rows;
	String[] ar;
	String txt2excel;
	String File1;
	String cmpxls;
	FileInputStream file = null;
	XSSFWorkbook workbook;
	String excelPath;
	
	@FXML
	AnchorPane root;

	@FXML
	Text textOutput;

	@FXML
	ComboBox<String> comboBox;

	@FXML
	ProgressBar progressBar;

	@FXML
	TextArea outputTextScreen;

	@FXML
	ScrollPane outputScreen;

	@FXML
	Button syncButton;

	@FXML
	Button fileButton1;

	@FXML
	Button fileButton2;

	@FXML
	Button executeWS;

	@FXML
	Button validate;

	@FXML
	Button sync1;

	@FXML
	Button sync2;

	@FXML
	Button validSync;

	@FXML
	TextField filePath1;

	@FXML
	TextField filePath2;

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		List<String> list = new ArrayList<String>();
		list.add("HCM->Employee");
		list.add("HCM->Contractor");
		list.add("HCM->Payroll");
		list.add("Finance->Accounts_Payable");
		list.add("Finance->Accounts_Receivable");
		list.add("Finance->General_Ledger");
		ObservableList<String> obList = FXCollections.observableList(list);
		comboBox.setItems(obList);
		ReporterBaseTest initFolder = new ReporterBaseTest();
		initFolder.folderCreate();
	}

	public void setCombo() {
		Item = comboBox.getValue();
		System.out.println(Item);
		Logger.logInfo(FXMLController.class,"Process selected is:"+ Item);
		outputTextScreen.appendText("Process Selected : " + Item + newLine);
		if (Item.contains("HCM")) {
			sync1.setDisable(false);
			sync2.setDisable(true);
			validSync.setDisable(true);
			fileButton1.setDisable(true);
			if (Item.equals("HCM->Employee")) {
				testModule = "OCTS_HCM_Automation_TestCases.";
				excelPath="C:/Automation_OCTS/Data/HCM_TestCaseExecution.xlsx";
				module_fp="C:/Automation_OCTS/Data/Worker.dat";
			} else if (Item.equals("HCM->Contractor")) {
				testModule = "OCTS_HCM_Automation_TestCases.";
				excelPath="C:/Automation_OCTS/Data/HCM_TestCaseExecution.xlsx";
				module_fp="";
			} else {
				testModule = "";
				excelPath="C:/Automation_OCTS/Data/HCM_TestCaseExecution.xlsx";
				module_fp="";
			}
			System.out.println(excelPath);
		} else if (Item.contains("Finance")) {
			sync1.setDisable(true);
			sync2.setDisable(false);
			validSync.setDisable(true);
			fileButton1.setDisable(true);
			if (Item.equals("Finance->Accounts_Payable")) {
				testModule = "";
				excelPath="C:/Automation_OCTS/Data/ERP_TestCaseExecution.xlsx";
				module_fp="";
			} else if (Item.equals("Finance->Accounts_Receivable")) {
				testModule = "";
				excelPath="C:/Automation_OCTS/Data/ERP_TestCaseExecution.xlsx";
				module_fp="";
			} else {
				testModule = "OCTS_Finance_Automation_TestCases.";
				excelPath="C:/Automation_OCTS/Data/ERP_TestCaseExecution.xlsx";
				module_fp="C:/Automation_OCTS/Data/GlInterface.zip";
			}
			//System.out.println(excelPath);
		}
		
	}

	@FXML
	public void syncButton1(ActionEvent event) {
		outputTextScreen.appendText(newLine + "HCM Sync Inprogress..." + newLine);
		ERP_getSessionID sessID = new ERP_getSessionID();
		ERP_executeXMLQuery getXML = new ERP_executeXMLQuery();
		XmlToExcelConverter conv = new XmlToExcelConverter();
		TextToExcel tte = new TextToExcel();
		try {
			File1 = chooseFile();
			if (!(File1 == null))
			{
			ar = readExcel();
			sessionID = sessID.getSessionID(ar[0], ar[1]);
			xml = getXML.getXMLQuery(sessionID);
			xml = xml.substring(0, xml.length() - 11);
			FileUtils.writeStringToFile(new File("C:\\Automation_OCTS\\Data\\HCM_Employee_Output.xml"), xml);
			rows = conv.getAndReadXml(xml);
			txt2excel = tte.readTextFile(File1);
			}
			else
			{
				Logger.logError(FXMLController.class,"No Input Test File has been selected, Please click on Sync HCM button again to select a File");
				throw new Exception("No Input Test File has been selected, Please click on Sync HCM button again to select a File");
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			outputTextScreen.appendText(newLine + e + newLine);
		}
		if (sessionID.contains("Error") || xml.contains("Error") || rows.contains("Error") || txt2excel.contains("Error") || rows.contains("processed 0 Rows")) {
			outputTextScreen.appendText(newLine + "Session ID: " + sessionID + newLine);
			outputTextScreen.appendText(newLine + "HCM Sync xml: " +xml + newLine);
			outputTextScreen.appendText(newLine + "Xml To Excel: " +rows + newLine);
			outputTextScreen.appendText(newLine + txt2excel + newLine);
			outputTextScreen.appendText(newLine + "HCM Sync Failed" + newLine);
			Logger.logError(FXMLController.class,newLine + "Session ID: " + sessionID + newLine);
			Logger.logError(FXMLController.class,newLine + "HCM Sync xml: " +xml + newLine);
			Logger.logError(FXMLController.class,newLine + "Xml To Excel: " +rows + newLine);
			Logger.logError(FXMLController.class,newLine + txt2excel + newLine);
			Logger.logError(FXMLController.class,newLine + "HCM Sync Failed" + newLine);
			
		} else {
			outputTextScreen.appendText(newLine + "Xml To Excel: " +rows + newLine);
			outputTextScreen.appendText(newLine + txt2excel + newLine);
			outputTextScreen.appendText(newLine + "HCM Sync Complete" + newLine);
			validSync.setDisable(false);
			Logger.logInfo(FXMLController.class,newLine + "Xml To Excel: " +rows + newLine);
			Logger.logInfo(FXMLController.class,newLine + txt2excel + newLine);
			Logger.logInfo(FXMLController.class,newLine + "HCM Sync Complete" + newLine);
		}

	}

	@FXML
	public void syncButton2(ActionEvent event) {
		validSync.setDisable(false);
		outputTextScreen.appendText(newLine + "ERP Sync Complete" + newLine);
		Logger.logInfo(FXMLController.class,newLine + newLine + "ERP Sync Complete" + newLine);
	}

	@FXML
	public void validButton(ActionEvent event) {
       if(Item.contains("HCM"))
       {
		CompareExcel ce = new CompareExcel();
        try {		
			cmpxls = ce.readXLSXFile();
		} catch (Exception e) {
			e.printStackTrace();
			outputTextScreen.appendText(newLine + e + newLine);
			Logger.logError(FXMLController.class,newLine + e + newLine);
		}
		if (cmpxls.contains("Error")) {
			outputTextScreen.appendText(newLine + cmpxls + newLine);
			outputTextScreen.appendText(newLine + "Sync Validation Failed" + newLine);
			Logger.logError(FXMLController.class,newLine + cmpxls + newLine);
			Logger.logError(FXMLController.class,newLine + "Sync Validation Failed" + newLine);
		} else {
			
			outputTextScreen.appendText(newLine + cmpxls + newLine);
			outputTextScreen.appendText(newLine + "Sync Validation Passed" + newLine);
			Logger.logInfo(FXMLController.class,newLine + cmpxls + newLine);
			Logger.logInfo(FXMLController.class,newLine + "Sync Validation Passed" + newLine);
			fileButton1.setDisable(false);
		}
       }
       else
       {
    	   fileButton1.setDisable(false);
    	   outputTextScreen.appendText(newLine + "Sync Validation Passed" + newLine);
    	   Logger.logInfo(FXMLController.class,newLine + "Sync Validation Passed" + newLine);
       }
	}

	public void selectFilePath1(ActionEvent event) throws Exception {
		outputTextScreen.appendText(newLine);
		fileButton1.setDisable(true);
			File1 = chooseFile();
			if (!(File1 == null))
			{
		inputfile_fp = File1;
		filePath1.setText(inputfile_fp);
		outputTextScreen.appendText("Test Excel Sheet Selected: " + inputfile_fp + newLine);
		Logger.logInfo(FXMLController.class,"Test Excel Sheet Selected: " + inputfile_fp + newLine);
		fileButton1.setDisable(false);
		//fileButton2.setDisable(false);
		executeWS.setDisable(false);
			}
			else
			{
				fileButton1.setDisable(false);
				outputTextScreen.appendText(newLine + "No Test Case Sheet has been selected, Please click on Browse button again to select a File" + newLine);
				Logger.logError(FXMLController.class,newLine + "No Test Case Sheet has been selected, Please click on Browse button again to select a File" + newLine);
				throw new Exception("No Test Case Sheet has been selected, Please click on Browse button again to select a File");
				
			}
			
	}

	/*public void selectFilePath2(ActionEvent event) throws Exception {
		fileButton2.setDisable(true);
		File1 = chooseFile();
		if (!(File1 == null))
		{
		GIfile_fp = File1;
		filePath2.setText(GIfile_fp);
		outputTextScreen.appendText("Process Related Test Data Selected: " + GIfile_fp + newLine);
		Logger.logInfo(FXMLController.class,"Process Related Test Data Selected: " + GIfile_fp + newLine);
		fileButton2.setDisable(false);
		executeWS.setDisable(false);
		}
		else
		{
			fileButton2.setDisable(false);
			outputTextScreen.appendText(newLine + "No Process Test Data has been selected, Please click on Browse button again to select a File" + newLine);
			Logger.logError(FXMLController.class,newLine + "No Process Test Data has been selected, Please click on Browse button again to select a File" + newLine);
			throw new Exception("No Process Test Data has been selected, Please click on Browse button again to select a File");
		}
	}*/

	/**
	 *
	 * @param event
	 */
	@FXML
	public void executeWSButton(ActionEvent event) {
		outputTextScreen.appendText(newLine + "Webservice Execution for Process: " + Item + " Started" + newLine);
		Logger.logInfo(FXMLController.class,newLine + "Webservice Execution for Process: " + Item + " Started" + newLine);
		executeWS.setDisable(true);
		progressBar.setProgress(0);
		copyWorker = createWorker();
		if(testModule != "") {
			progressBar.progressProperty().unbind();
			progressBar.progressProperty().bind(copyWorker.progressProperty());
			new Thread(copyWorker).start();
			executeWS.setDisable(false);
		}
		else {
			outputTextScreen.appendText("Webservice Execution for Process: " + Item + " do not have any current TestMethods to Call, Hence stopping the test" + newLine);
			Logger.logError(FXMLController.class,newLine + "Webservice Execution for Process: " + Item + " do not have any current TestMethods to Call, Hence stopping the test" + newLine);
		}
	}


	public Task createWorker() {
		return new Task() {
			@Override
			protected Object call() throws Exception {
				
				Invoke_TestNG_Classes ws = new Invoke_TestNG_Classes();
				System.out.println(excelPath);
				String output = ws.iterateThroughTestCases(inputfile_fp, module_fp, testModule,excelPath);
				if(output.contains("Mark atleast"))
				{
					updateProgress(1.0, 1.0);
					outputTextScreen.appendText("Webservice Execution for Process: " + Item + " stopped" + newLine);
					outputTextScreen.appendText(
							"Please mark atleast one test case with flag as Y in Testcase Execution sheet for "+ Item + newLine);
					textOutput.setText("Test Execution Aborted");
					Logger.logError(FXMLController.class,"Please mark atleast one test case with flag as Y in Testcase Execution sheet for "+ Item + newLine);
					return true;
				}
				while (output.contains("Completed")) {
					updateProgress(1.0, 1.0);
					// progressBar.setAccessibleText("Completed");
					System.out.println(output);
					outputTextScreen.appendText("Webservice Execution for Process: " + Item + " Completed" + newLine);
					outputTextScreen.appendText(
							"Please check the output result generate at : C:/Automation_OCTS/Results" + newLine);
					Logger.logInfo(FXMLController.class,"Webservice Execution for Process: " + Item + " Completed" + newLine);
					Logger.logInfo(FXMLController.class,"Please check the output result generate at : C:/Automation_OCTS/Results" + newLine);
					
					textOutput.setText("Test Execution Completed");
					return true;
				
				}
				return true;
			}
			
		};
	}

	public String[] readExcel() throws Exception {
		try {
			ar = new String[2];
			file = new FileInputStream(new File("C:\\Automation_OCTS\\Data\\ERP_HCM_InputDatasheet.xlsx"));
			workbook = new XSSFWorkbook(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		CommonUtilFunctions cu = new CommonUtilFunctions();
		XSSFSheet ips1 = workbook.getSheetAt(0);
		for (int currentColumnNum = 0; currentColumnNum < 1; currentColumnNum++) {
			for (int currentRowNum = ips1.getFirstRowNum() + 1; currentRowNum <= ips1
					.getLastRowNum(); currentRowNum++) {
				System.out.println("RowNum" + currentRowNum);

				ArrayList<Cell> cells_uUserName = cu.columnIdentification("UserName", ips1);
				System.out.println(cells_uUserName);
				ar[0] = cu.getCellFormattedStringValue(cells_uUserName.get(currentRowNum), workbook);

				ArrayList<Cell> cells_uPassword = cu.columnIdentification("Password", ips1);
				System.out.println(cells_uPassword);
				ar[1] = cu.getCellFormattedStringValue(cells_uPassword.get(currentRowNum), workbook);

			}
		}
		return ar;
	}
	public String chooseFile() {
		FileChooser chooser = new FileChooser();
		File file = new File("C:\\Automation_OCTS\\");
		chooser.setInitialDirectory(file);
		file = chooser.showOpenDialog(fileButton1.getParent().getScene().getWindow());
		
		if (file == null)
		{
			return null;
		}
		else
		{
			File1 = file.getPath();
			return File1;	
		}
		
		
	}

	
}