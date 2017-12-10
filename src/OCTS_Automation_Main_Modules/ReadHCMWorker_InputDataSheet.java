package OCTS_Automation_Main_Modules;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@SuppressWarnings("unused")
public class ReadHCMWorker_InputDataSheet {
	
	
	
	// parse Worker Test Data Sheet
	public HashMap<Integer, Cell> parseWorkerExcel(String workerTestDataPath, String columnWanted)
			throws IOException {
		Integer columnHeaderIndex = null;
		FileInputStream fileInputStream = new FileInputStream(new File(
				workerTestDataPath));
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
		XSSFSheet sheet = workbook.getSheetAt(0);
		HashMap<Integer, Cell> cells = new HashMap<Integer, Cell>();
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
					int rowindex = c.getRowIndex();

					if (c == null) {
						System.out.println("Nothing is present in the cell");
					} else {
						cells.put(rowindex,c);

					}
				}
			}

		} else {
			System.out.println("could not find column " + columnWanted);
		}

		return cells;

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