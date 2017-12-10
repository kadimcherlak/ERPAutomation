package OCTS_Finance_Automation_TestCases;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import com.aventstack.extentreports.Status;

import OCTS_Automation_Main_Modules.ReadERPFinance_InputDataSheet;
import Common_Utility.Logger;
import Common_Utility.ReporterBaseTest;

import org.apache.poi.ss.usermodel.Cell;
import org.testng.annotations.Test;

public class NumberOfLines extends ReporterBaseTest{

	// Required Variables to pull out the matching string
	static String periodName = "";
	//	static String zipFilePath = "C:\\Automation_OCTS\\Output\\71963.zip";  
		static String fileObj;
		static String destDir = "C:\\Automation_OCTS\\Output\\";
	//	static String fileObj = zipFilePath.replace("zip", "txt");
		static int numberOfLines;
		static String totaldebits;
		static String totalcredits;
		static String finaltotaldebits;
		static String finaltotalcredits;
		static String[] splitNewLine = {};
		static ArrayList<Cell> outputValue = new ArrayList<Cell>();
		static String finalOutputString = "";
		static List<String> finalOutputValues = new ArrayList<String>();
		static String excelPath;
		static String ColumnWanted = "SEJRRequestImport_PL2_JournalPeriodName";
		int flag=0;
		@Test
		public void numberOfLines() throws IOException {
			
			fileObj=ERP_Financial_Webservice_MainClass.zipFilePath.replace("zip", "txt");
			System.out.println(fileObj);
			 test=extent.createTest("Number of Lines");
			//unzip the output file to enable comparision
		//	UnzipOutputFileGenerated unZip = new UnzipOutputFileGenerated();
			//unZip.unzip(zipFilePath, destDir);
			//Get Number of Lines from GlInterface file
			 
			ReadERPFinance_InputDataSheet inputGIInterface = new ReadERPFinance_InputDataSheet();
			excelPath = ERP_Financial_Webservice_MainClass.FinanceInputDataFile.toString();
			outputValue = inputGIInterface.parseInputExcelFile(excelPath, ColumnWanted);
			numberOfLines = inputGIInterface.parseGIInterfaceFile();
			String noOfRows = Integer.toString(numberOfLines);
			periodName = outputValue.get(0).toString();
			getTotalNumberOfLines();
			getTotalDebits();
			getTotalCredits();
			finaltotaldebits=totaldebits;
			finaltotalcredits=totalcredits;
			
			for (int i = 0; i < finalOutputValues.size() - 1; i++) {
				if (finalOutputValues.get(3).equals(noOfRows)) {
				finalOutputString = finalOutputValues.get(3);
				flag=1;
				}
			}
			if(flag==1){
			System.out
					.println("Total number of Lines from the Output File is \n"
						  + finalOutputString);
			Common_Utility.ReporterBaseTest.test.log(Status.PASS, "Total number of Lines from the Output File is " +finalOutputString+ "\n" +"\n"+ " Journal has been created with Debit "+finaltotaldebits + " and Credit "+finaltotaldebits);
			Logger.logInfo(NumberOfLines.class, "Total number of Lines from the Output File is " +finalOutputString+ "\n" +"\n"+ " Journal has been created with Debit "+finaltotaldebits + " and Credit "+finaltotaldebits);
			}
			
			else
			{
		
				System.out.println("Please check if the Total Number of Lines entered is correct");
				Common_Utility.ReporterBaseTest.test.log(Status.FAIL, "Total number of Lines from the Output File "+finalOutputString+" is not matching with input file "+noOfRows);
				Logger.logError(NumberOfLines.class, "Total number of Lines from the Output File "+finalOutputString+" is not matching with input file "+noOfRows);
			}
		}

		// Method to parse the text file and pull out the Matching pattern to
		// validate the excel

		public static List<String> getTotalNumberOfLines() throws IOException {
			try {
				
				String line = null;
				String newLine = "";
				@SuppressWarnings("resource")
				BufferedReader bufRdr = new BufferedReader(new InputStreamReader(
						new FileInputStream(fileObj), "UTF-8"));

				line = line + bufRdr.readLine();
				
				while ((line = bufRdr.readLine()) != null) {
					//line = bufRdr.readLine();
					if (line.contains("TOTALS")) {
						//System.out.println("Before trimming spaces\n" + line);
						newLine = line.trim();
						//System.out.println("After trimming spaces\n" + newLine);
						splitNewLine = newLine.split("\\s");
						for (String matchString : splitNewLine) {
							if ( (matchString.length() != 0)
									&& (!matchString.matches("[^\\w.*]")))

							{

								finalOutputValues.add(matchString);
							}

						}
					}
				}

			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			return finalOutputValues;

		}

		// Method to parse the text file and pull out the Matching pattern to

				public static String getTotalDebits() throws IOException {
					try {
						
						String line = null;
					
						String newLine = "";
						@SuppressWarnings("resource")
						BufferedReader bufRdr = new BufferedReader(new InputStreamReader(
								new FileInputStream(fileObj), "UTF-8"));

						line = line + bufRdr.readLine();
						
						while ((line = bufRdr.readLine()) != null) {
							//line = bufRdr.readLine();
							if (line.contains(periodName)) {
								//System.out.println("Before trimming spaces\n" + line);
								newLine = line.trim();
							
								 totaldebits=line.substring(93, 112).trim();						}
						}
					} catch (NullPointerException e) {
						e.printStackTrace();
					}
					return totaldebits;

				}
				// Method to parse the text file and pull out the Matching pattern to

				public static String getTotalCredits() throws IOException {
					try {
						
						String line = null;
						
						String newLine = "";
						@SuppressWarnings("resource")
						BufferedReader bufRdr = new BufferedReader(new InputStreamReader(
								new FileInputStream(fileObj), "UTF-8"));

						line = line + bufRdr.readLine();
						
						while ((line = bufRdr.readLine()) != null) {
							//line = bufRdr.readLine();
							if (line.contains(periodName)) {
								//System.out.println("Before trimming spaces\n" + line);
								newLine = line.trim();
							
								 totalcredits=line.substring(113, 132).trim();						}
						}
					} catch (NullPointerException e) {
						e.printStackTrace();
					}
					return totalcredits;

				}

}