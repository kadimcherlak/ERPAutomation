package OCTS_HCM_Automation_TestCases;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import Common_Utility.*;
import OCTS_Automation_Main_Modules.*;

@SuppressWarnings("unused")
public class EmployeePayTypeValidation extends ReporterBaseTest{
	
	static String workerTestDataPath = "C:\\Automation_OCTS\\Output\\WorkerTestData.xlsx";
	static HashMap<Integer, Cell> personNumber = new HashMap<Integer, Cell>();
	static HashMap<Integer, Cell> assignmentStatusType = new HashMap<Integer, Cell>();
	static String workerPersonNumberCol = "PersonNumber";
	static String workerHourlySalariedCodeCol = "HourlySalariedCode";
	static String actualPersonNumber = "STUDENT1_PERSON117";
	static String actualHourlySalariedCode = "S";
	static String expectedPersonNumber ="";
	static String expectedSalariedCode ="";

	static HashMap<Object,Cell> salariedCodeValidation = new HashMap<Object, Cell>();
	int flag = 0;

	public void getSalariedCode() {
		ReadHCMWorker_InputDataSheet workerFile = new ReadHCMWorker_InputDataSheet();

		try {
			personNumber = workerFile.parseWorkerExcel(workerTestDataPath,workerPersonNumberCol);
			assignmentStatusType = workerFile.parseWorkerExcel(workerTestDataPath,workerHourlySalariedCodeCol);
			for (Map.Entry<Integer, Cell> pNumber : personNumber.entrySet()) {
				for (Map.Entry<Integer, Cell> aNumber : assignmentStatusType.entrySet()) {
					if (pNumber.getKey().equals(aNumber.getKey())) {
						salariedCodeValidation.put(pNumber.getValue(),aNumber.getValue());
					}
				}
			}
			for (Map.Entry<Object, Cell> editPersonNumber : salariedCodeValidation.entrySet()){
				expectedPersonNumber = editPersonNumber.getKey().toString();
				expectedSalariedCode = editPersonNumber.getValue().toString();
				/*if(expectedPersonNumber.equals(actualPersonNumber) ) 
				{
					expectedSalariedCode = editPersonNumber.getValue().toString();
					if(expectedSalariedCode.equals(actualHourlySalariedCode)){
					flag=1;
					break;
					}
				}*/
				flag=1;
				break;
				
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(flag == 1)
		{
			System.out.println("Employee #"+ expectedPersonNumber + "" + " has been assigned to Salaried Code : "+ ""+expectedSalariedCode);
			Common_Utility.ReporterBaseTest.test.log(Status.PASS, "Employee #"+ expectedPersonNumber + "" + " has been assigned to Salaried Code : "+ ""+expectedSalariedCode+"\n");
			Logger.logInfo(EmployeePayTypeValidation.class, "Employee #"+ expectedPersonNumber + "" + " has been assigned to Salaried Code : "+ ""+expectedSalariedCode+"\n");
		}
		if(flag == 0)
		{
			System.out.println("Employee #"+ expectedPersonNumber + "" + " has not been assigned to Salaried Code : "+ ""+expectedSalariedCode);
			Common_Utility.ReporterBaseTest.test.log(Status.FAIL, "Employee #"+ expectedPersonNumber + "" + " has not been assigned to Salaried Code : "+ ""+expectedSalariedCode+"\n");
			Logger.logError(EmployeePayTypeValidation.class, "Employee #"+ expectedPersonNumber + "" + " has not been assigned to Salaried Code : "+ ""+expectedSalariedCode+"\n");
		}
		
	}
	
	@Test
	public void empAssignmentStatusValidation() {
		EmployeePayTypeValidation empSalCode = new EmployeePayTypeValidation();
		System.out.println(workerTestDataPath);
		test=extent.createTest("Employee Hourly Salaried Code Validation");
		System.out.println("\n Employee Hourly Salaried Code Validation \n");
		empSalCode.getSalariedCode();
	}
	//Commenting the below code; if required to test this code alone then uncomment the code
	/*	public static void main(String[] args) {
			EmployeePayTypeValidation empVal = new EmployeePayTypeValidation();
			empVal.getSalariedCode();
		}
		*/
}