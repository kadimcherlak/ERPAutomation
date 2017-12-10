package OCTS_Automation_Main_Modules;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadERPFinance_InputDataSheet {
	
	public static String zipFilePath = "C:\\Automation_OCTS\\Data\\GlInterface.zip";
	public static String destDir = "C:\\Automation_OCTS\\Data\\";
	static String csvFileObj = zipFilePath.replace("zip", "csv");
	
	public ArrayList<Cell> parseInputExcelFile(String excelPath,String columnWanted)
			throws IOException {
		Integer columnHeaderIndex = null;
		FileInputStream fileInputStream = new FileInputStream(new File(
				excelPath));
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
		XSSFSheet sheet = workbook.getSheetAt(0);
		ArrayList<Cell> cells = new ArrayList<Cell>();
		Row row = sheet.getRow(0);
		// Skim through Column header names
		for (Cell colHeader : row) {

			if (colHeader.getStringCellValue().equals(columnWanted)) {

				columnHeaderIndex = colHeader.getColumnIndex();
			}
		}
		if (columnHeaderIndex != null) {

			for (Row row3 : sheet) {
				if (row3.getRowNum() > 0) {

					Cell c = row3.getCell(columnHeaderIndex);

					if (c == null) {
						System.out.println("Nothing is present in the cell");
					} else {
						cells.add(c);

					}
				}
			}

		} else {
			System.out.println("could not find column " + columnWanted);
		}

		return cells;

	}
	public int parseGIInterfaceFile() throws IOException
	{
		//unzip the output file to enable comparision
		UnzipOutputFileGenerated unZip = new UnzipOutputFileGenerated();
		unZip.unzip(zipFilePath, destDir);
		
		BufferedReader bufferedReader = new BufferedReader(new FileReader(csvFileObj));
	     String input;
	     int noOfRows = 0;
	     while((input = bufferedReader.readLine()) != null)
	     {
	    	 noOfRows++;
	     }
		return noOfRows;
	}

	public String getCellFormattedStringValue(Cell cell, XSSFWorkbook workbook) {
		FormulaEvaluator formulaEvaluator = workbook.getCreationHelper()
				.createFormulaEvaluator();
		DataFormatter dataFormatter = new DataFormatter();
		String cellvalue = dataFormatter.formatCellValue(formulaEvaluator
				.evaluateInCell(cell));
		return cellvalue;

	}

}