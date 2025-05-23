package com.amproducts.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
	
	private XSSFWorkbook workbook;  // To handle closing
	private Map<String, Map<String, String>> excelDataCache;  // Cache to store environment-specific data
	
	public ExcelReader(String filePath, String sheetName) throws IOException {
		// Open the Excel file once
		File f = new File(System.getProperty("user.dir")+ filePath);
		FileInputStream fis = new FileInputStream(f);
		workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheet(sheetName);
		
		//Get the first row
		XSSFRow headerRow = sheet.getRow(0);
		//Fetch the column values and store it
		Map<String, Integer> columnValues = new HashMap<String, Integer>();
		for (int i = 0; i < headerRow.getLastCellNum(); i++) {
			columnValues.put(headerRow.getCell(i).getStringCellValue(), i); // Store column name to index mapping
		}
		
		excelDataCache = new HashMap<>();  // Initialize once
		
		for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
			XSSFRow row = sheet.getRow(rowIndex);
//			String environment = row.getCell(columnValues.get("Environment")).getStringCellValue();
			if (row == null) continue;

			XSSFCell envCell = row.getCell(columnValues.get("Environment"));
			String environment = getCellValueAsString(envCell);
			if (environment == null || environment.isEmpty()) continue;
			
			Map<String, String> rowData = new HashMap<String, String>();
			for (String columnName : columnValues.keySet()) {
				XSSFCell cell = row.getCell(columnValues.get(columnName));
				String cellValue = getCellValueAsString(cell);
				rowData.put(columnName, cellValue);  // Cache the value
			}
//			 excelDataCache = new HashMap<>();
			 excelDataCache.put(environment, rowData);  // Cache the entire row for this environment
		}
		fis.close();  // Close the file input stream after reading the Excel data
	}
	
	public void getData() throws IOException {
	File f = new File(System.getProperty("user.dir") + "/Files/TestData.xlsx");
	FileInputStream fis = new FileInputStream(f);
	XSSFWorkbook xssf = new XSSFWorkbook(fis);
	//Get the sheet
	XSSFSheet sheet = xssf.getSheet("Data");
	//Get row
	XSSFRow row = sheet.getRow(1);
	//Get cell
	XSSFCell cell = row.getCell(0);
	//Get Value
	String value = cell.getStringCellValue();
	System.out.println(value);
	
	
	}
	
	//Method to fetch the cached data
	public String getCachedData(String environment, String columnName) {
		Map<String, String> rowData = excelDataCache.get(environment);
		if (rowData != null) {
			return rowData.get(columnName);
		}
		return null;
	}
	
	// Helper method to handle different cell types
    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    // Return formatted date string
                    Date date = cell.getDateCellValue();
                    return new SimpleDateFormat("yyyy-MM-dd").format(date);
                } else {
                    double value = cell.getNumericCellValue();
                    // Check if it's actually an integer
                    if (value == (long) value) {
                        return String.valueOf((long) value);  // no decimal point
                    } else {
                        return String.valueOf(value);         // keep decimal
                    }
                }

            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());

            case FORMULA:
                return cell.getCellFormula(); // Or evaluate with FormulaEvaluator

            case BLANK:
                return "";

            default:
                return "";
        }
    }
    
 // Close the workbook when done
    public void close() throws IOException {
        if (workbook != null) {
            workbook.close();  // Close the workbook to release resources
        }
    }

}
