/*===============================================================================================================================
        CLASS Name:    CommonUtilFunctions
        CREATED BY:    Koushik Kadimcherla
        DATE CREATED:  Nov 2017
        DESCRIPTION:   Resuable functions                   
        PARAMETERS:                                                                  
        RETURNS:      
        COMMENTS:                                     
        Modification Log:s
        Date                             Initials                                                Modification
        
-------------         ------------    ------------------------------------------------------------------------------------------------------------------------------*/
package Common_Utility;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CommonUtilFunctions {



public  int random(int lowerBound, int upperBound) {
    return (lowerBound + (int) Math.round(Math.random()
            * (upperBound - lowerBound)));
  }

 public  ArrayList<Cell> columnIdentification(String columnWanted,XSSFSheet ips1){
Integer columnNo = null;
        //output all not null values to the list
        ArrayList<Cell> cells = new ArrayList<Cell>();
Row row_c = ips1.getRow(0);
for (Cell cell1 : row_c) {
                    // Column header names.
                 //   System.out.println("col cell value"+cell1.getStringCellValue());  
                    if (cell1.getStringCellValue().equals(columnWanted)){
                        columnNo = cell1.getColumnIndex();
                       // System.out.println("columnNo"+columnNo);
                    }
                }
if (columnNo != null){
                       // for (Row row1 : sheetName) {
for (Row row3 : ips1) {
                           Cell c = row3.getCell(columnNo);
                           if (c == null) {
                         //  if (c == null) {
                              // Nothing in the cell in this row, skip it
                           } else {
                              cells.add(c);
                            // System.out.println("C"+c);                           
                           }                        
                        }
                        
                    }else{
                       System.out.println("could not find column " + columnWanted );
                    }
return cells;

}
public  String getCellFormattedStringValue(Cell cell,XSSFWorkbook workbook) {
FormulaEvaluator formulaEvaluator = workbook.getCreationHelper()
.createFormulaEvaluator();
DataFormatter dataFormatter = new DataFormatter();
String cellvalue =dataFormatter.formatCellValue(formulaEvaluator
.evaluateInCell(cell));
return cellvalue;
}
}

