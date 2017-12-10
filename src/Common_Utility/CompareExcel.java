package Common_Utility;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CompareExcel {
	String person = "";

	@SuppressWarnings({ "rawtypes", "unchecked", "resource" })
	public String readXLSXFile() {

		try {
			InputStream ExcelFileToRead1 = new FileInputStream("C:\\Automation_OCTS\\Output\\WorkerTestData.xlsx");
			// POIFSFileSystem poifs1 = new POIFSFileSystem(ExcelFileToRead1);

			// NPOIFSFileSystem ExcelFileToRead1 = new NPOIFSFileSystem(new File("D:\\total
			// data\\Selenium\\eclipse\\amazon\\Source.xls"));

			InputStream ExcelFileToRead2 = new FileInputStream("C:\\Automation_OCTS\\Output\\HCMSyncTestData.xlsx");
			// POIFSFileSystem poifs2 = new POIFSFileSystem(ExcelFileToRead2);
			XSSFWorkbook wb1 = new XSSFWorkbook(ExcelFileToRead1);
			XSSFWorkbook wb2 = new XSSFWorkbook(ExcelFileToRead2);
			// ExcelFileToRead1.close();
			// NPOIFSFileSystem ExcelFileToRead2 = new NPOIFSFileSystem(new File("D:\\total
			// data\\Selenium\\eclipse\\amazon\\destination.xls"));

			// ExcelFileToRead2.close();

			XSSFSheet sheet1 = wb1.getSheetAt(0);
			int row_number1 = sheet1.getPhysicalNumberOfRows();
			int column_number1 = sheet1.getRow(0).getPhysicalNumberOfCells();

			XSSFSheet sheet2 = wb2.getSheetAt(0);

			ArrayList arrayList1 = new ArrayList();

			int row_number2 = sheet2.getPhysicalNumberOfRows();
			int column_number2 = sheet2.getRow(0).getPhysicalNumberOfCells();
			
			if (!(row_number1 == 1 || row_number2 == 1)) {
				

				// System.out.println("value of rowcount is " +row_number);
				// Iterator rows = sheet1.rowIterator();

				int i;
				int k = 0;
				for (i = 0; i < column_number1; i++) {
					XSSFCell s2 = sheet1.getRow(0).getCell(i);
					String s3 = s2.toString();

					if (s3.equalsIgnoreCase("PersonNumber")) {
						k = i;
						System.out.println("Column index of PersonNumber in Source File " + k);
						break;
					}
					// System.out.println("s3 value is " +s3);

				}

				int g;
				int a = 0;
				for (g = 0; g < column_number2; g++)

				{
					// System.out.println("coul number " +column_number1);
					XSSFCell s4 = sheet2.getRow(0).getCell(g);
					String s5 = s4.toString();
					// System.out.println(s5);
					if (s5.equalsIgnoreCase("PersonNumber")) {
						a = g;
						System.out.println("Column index of PersonNumber in Destination File " + a);
						break;
					}

				}

				int j = 1;
				int b;
				while (j < row_number1) {
					XSSFCell value1 = sheet1.getRow(j).getCell(k);
					for (b = 1; b < row_number2; b++) {
						XSSFCell value2 = sheet2.getRow(b).getCell(a);

						if (value1.getStringCellValue().equalsIgnoreCase(value2.getStringCellValue())) {

							arrayList1.add(value1.getStringCellValue());
							break;
						}

					}

					j++;

				}

				int retval = arrayList1.size();
				// Collections.reverse(arrayList1);
				for (int c = 0; c < retval; c++) {
					System.out.println("Person" + " " + arrayList1.get(c) + " is already present in Source file ");
					person = person +" , " + arrayList1.get(c);
				}
				wb1.close();
				wb2.close();
			} else {
					return "No Records present in either HCM Sync data or Worker Test Data, Please check the files";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "Error:" + e;
		}
		if (person != "") {
			person = person.substring(2, person.length());
			System.out.println("Error: Duplicates Records Found for : " + person + " PersonNumber(s) already present in HCM system");
			return "Error: Duplicates Records Found for : " + person + " PersonNumber(s) already present in HCM system";
		} else {
			System.out.println("No Duplicates Found");
			return "Comparing Completed: No Duplicates found";
		}

	}

	public static void main(String[] args) {

		CompareExcel myObj = new CompareExcel();

		myObj.readXLSXFile();

	}

}
