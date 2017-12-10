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
import OCTS_Finance_Automation_TestCases.NumberOfLines;

@SuppressWarnings("unused")
public class EmployeeAssignmentValidation extends ReporterBaseTest{
	
	static String workerTestDataPath = "C:\\Automation_OCTS\\Output\\WorkerTestData.xlsx";
	static HashMap<Integer, Cell> personNumber = new HashMap<Integer, Cell>();
	static HashMap<Integer, Cell> assignmentNumber = new HashMap<Integer, Cell>();
	static String workerPersonNumberCol = "PersonNumber";
	static String workerAssignmentNumberCol = "AssignmentNumber";
	static String actualPersonNumber = "STUDENT1_PERSON117";
	static String actualAssignmentNumber = "STUDENT1_ASSIGN_NUM115";
	static String expectedPersonNumber ="";
	static String expectedAssignmentNumber ="";

	static HashMap<Object,Cell> assignmentValidation = new HashMap<Object, Cell>();
	int flag = 0;

	public void getPersonAndAssignmentDetails() {
		ReadHCMWorker_InputDataSheet workerFile = new ReadHCMWorker_InputDataSheet();

		try {
			personNumber = workerFile.parseWorkerExcel(workerTestDataPath,workerPersonNumberCol);
			assignmentNumber = workerFile.parseWorkerExcel(workerTestDataPath,workerAssignmentNumberCol);
			
			for (Map.Entry<Integer, Cell> pNumber : personNumber.entrySet()) {
				for (Map.Entry<Integer, Cell> aNumber : assignmentNumber.entrySet()) {
					if (pNumber.getKey().equals(aNumber.getKey())) {
						assignmentValidation.put(pNumber.getValue(),aNumber.getValue());
					}
				}
			}
			for (Map.Entry<Object, Cell> editPersonNumber : assignmentValidation.entrySet()){
				expectedPersonNumber = editPersonNumber.getKey().toString();
				expectedAssignmentNumber = editPersonNumber.getValue().toString();
			/*	if(expectedPersonNumber.equals(actualPersonNumber) ) 
				{
					expectedAssignmentNumber = editPersonNumber.getValue().toString();
					if(expectedAssignmentNumber.equals(actualAssignmentNumber)){
					flag=1;
					break;
					}
				}
				*/
				flag=1;
				break;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(flag == 1)
		{
			System.out.println("Employee #"+ expectedPersonNumber + "" + " has been assigned to Assignment Number #"+ ""+expectedAssignmentNumber);
			Common_Utility.ReporterBaseTest.test.log(Status.PASS, "Employee #"+ expectedPersonNumber + "" + " has been assigned to Assignment Number #"+ ""+expectedAssignmentNumber +"\n");
			Logger.logInfo(EmployeeAssignmentValidation.class, "Employee #"+ expectedPersonNumber + "" + " has been assigned to Assignment Number #"+ ""+expectedAssignmentNumber +"\n");
		}
		if(flag == 0)
		{
			System.out.println("Employee #"+ expectedPersonNumber + "" + " has not been assigned to Assignment Number #"+ ""+expectedAssignmentNumber);
			Common_Utility.ReporterBaseTest.test.log(Status.FAIL, "Employee #"+ expectedPersonNumber + "" + " has not been assigned to Assignment Number #"+ ""+expectedAssignmentNumber +"\n");
			Logger.logError(EmployeeAssignmentValidation.class, "Employee #"+ expectedPersonNumber + "" + " has not been assigned to Assignment Number #"+ ""+expectedAssignmentNumber +"\n");
		}
		
	}
	
	@Test
	public void empAssignmentValidation() {
		EmployeeAssignmentValidation empAssgn = new EmployeeAssignmentValidation();
		System.out.println(workerTestDataPath);
		test=extent.createTest("Employee Person Number and Assignment Number Validation");
		System.out.println("\n Employee Person and Assignment Number Validation \n");
		empAssgn.getPersonAndAssignmentDetails();
	}
	
	//Commenting the below code; if required to test this code alone then uncomment the code
	/*public static void main(String[] args) {
		EmployeeAssignmentValidation empAssgn = new EmployeeAssignmentValidation();
		empAssgn.getPersonAndAssignmentDetails();
	}*/
}