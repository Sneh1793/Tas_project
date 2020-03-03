
package dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;

public class ProcessFileImpl implements ProcessFile {

	@Override
	public void process(String fileName) throws IOException {

		File file = new File(fileName);
		// Create an object of FileInputStream class to read excel file
		FileInputStream fis = new FileInputStream(file);

		Workbook addCatalog = null;
		String fileExtensionName = fileName.substring(fileName.indexOf("."));
		// Find the file extension by splitting file name in substring and getting only
		// extension name
		if (fileExtensionName.equals(".xsls")) {
			// If it is .xls file then create object of HSSFWorkbook class
			addCatalog = new HSSFWorkbook(fis);
		} else if (fileExtensionName.equals(".xlsx")) {
			// If it is .xlsx file then create object of XSSFWorkbook class
			addCatalog = new XSSFWorkbook(fis);
		}

		// Read Sheet

		HashMap<String, Integer> coloumnNameSheet2 = new HashMap<String, Integer>();
		HashMap<String, Integer> coloumnNameSheet1 = new HashMap<String, Integer>();

		Sheet sheet1 = addCatalog.getSheetAt(0);
		Sheet sheet2 = addCatalog.getSheetAt(1);

		getHeader(coloumnNameSheet1, sheet1, 0);
		getHeader(coloumnNameSheet2, sheet2, 1);

		// compare column
		Map<String, Integer> commonColumn = getCommonColums(coloumnNameSheet1, coloumnNameSheet2);
		System.out.println("common coloumn");
		commonColumn.keySet().stream().forEach(col -> System.out.println(col));

		// Get Column Mapping in sheet
		Map<Integer, Integer> commonColumnMapping = getCommonColumsMapping(commonColumn, coloumnNameSheet2);

		// update sheet
		updateSheet(fileName, addCatalog, commonColumnMapping, fis);

	}

// Getting columns names for comparison 
	private void getHeader(Map<String, Integer> coloumnNameSheet, Sheet sheet, int romNum) {

		Row row = sheet.getRow(romNum);

		System.out.println("Sheet Name is ****" + sheet.getSheetName());

		for (int i = 0; i < row.getLastCellNum(); i++) {
			Cell cell = row.getCell(i);

			System.out.println("sheet values are" + cell.getColumnIndex() + "" + cell.getStringCellValue());
			// Add all the cell values of a particular row
			coloumnNameSheet.put(cell.getStringCellValue(), cell.getColumnIndex());

			CellReference.convertNumToColString(cell.getColumnIndex());
		}
	}

	// getting common columns
	private Map<String, Integer> getCommonColums(Map<String, Integer> coloumnNameSheet1,
			Map<String, Integer> coloumnNameSheet2) {
		Map<String, Integer> commonMap = coloumnNameSheet1.entrySet().stream()
				.filter(x -> coloumnNameSheet2.containsKey(x.getKey().trim()))
				.collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue()));

		return commonMap;
	}

	private Map<Integer, Integer> getCommonColumsMapping(Map<String, Integer> commonColumnSheet1,
			Map<String, Integer> coloumnNameSheet2) {

		return commonColumnSheet1.entrySet().stream()
				.collect(Collectors.toMap(x -> x.getValue(), x -> coloumnNameSheet2.get(x.getKey().trim())));

	}

	// writing the common data into sheet2
	private void updateSheet(String fileName, Workbook addCatalog, Map<Integer, Integer> commonColumsMapping,
			FileInputStream fis) throws IOException {

		// Get second sheet
		Sheet sheet2 = addCatalog.getSheetAt(1);

		for (Row row : addCatalog.getSheetAt(0)) {

			if (row.getRowNum() == 0) {
				continue;
			}
			for (Cell cell : row) {

				if (commonColumsMapping.containsKey(cell.getColumnIndex())) {

					Row newRow = null;
					// check row already exist or not
					if (sheet2.getRow(row.getRowNum() + 2) == null) {
						// create new row
						// Specific row number
						newRow = sheet2.createRow(row.getRowNum() + 2);

					}

					else {
						newRow = sheet2.getRow(row.getRowNum() + 2);
					}

					// Specific cell number in sheet 2
					Cell newCell = newRow.createCell(commonColumsMapping.get(cell.getColumnIndex()));

					// putting value at specific position

					switch (cell.getCellType()) {
					case NUMERIC:
						newCell.setCellValue(cell.getNumericCellValue());
						break;
					case STRING:
						newCell.setCellValue(cell.getStringCellValue());
						break;
					}
				}
			}
		}

		File file = new File(fileName);
		FileOutputStream outputStream = new FileOutputStream(file);
		addCatalog.write(outputStream);

		//sssoutputStream.close();

		System.out.println("File Updated");
		addHeader(addCatalog, fis, fileName);
		outputStream.close();
		// FileInputStream fin = new FileInputStream(new File (fileName));
		// HSSFWorkbook workbook = new HSSFWorkbook(fin);
		// workbook = new HSSFWorkbook(fin);
		// Get first sheet from the workbook
		// HSSFSheet sheet = workbook.getSheetAt(1);

	}

	private void addHeader(Workbook addCatalog, FileInputStream fis, String fileName) throws IOException {
		
		URL urlLoader = ProcessFileImpl.class.getProtectionDomain().getCodeSource().getLocation();
		String loaderDir = urlLoader.getPath();
		System.out.printf("loaderDir", loaderDir);

		File file = new File(loaderDir + "/UnLimit.xlsx");

		FileInputStream inputStream = new FileInputStream(file);

		Workbook refBook = new XSSFWorkbook(inputStream);
		Sheet sheet = refBook.getSheetAt(0);
		System.out.println("Sheet Name is ****" + sheet.getSheetName());

		Sheet sheetManual = addCatalog.getSheetAt(1);
		// getting starting row in manual sheet
		Row rowManualSheet = sheetManual.getRow(0);
		// get the last column index
		int maxcell = rowManualSheet.getLastCellNum();
		System.out.println(maxcell);
		
		Row rowInputSheet =sheet.getRow(0);
		int lastcellIndex = rowInputSheet.getLastCellNum();
		System.out.println(lastcellIndex);
		
			  int i =0;
		Iterator<Row> rowIterator = sheet.iterator();
		while (rowIterator.hasNext()) {
			 rowInputSheet = rowIterator.next();
			// For each row, iterate through all the columns
			Iterator<Cell> cellIterator = rowInputSheet.cellIterator();

			while (cellIterator.hasNext()) {
				Cell celldata = cellIterator.next();

				celldata.getStringCellValue();
				
		Cell newCell = rowManualSheet.createCell(maxcell + 1);
		// getting starting cell from where to write in manual sheet
				 	newCell =  rowManualSheet.getCell(maxcell + 1);
		if (newCell == null)
				newCell.setCellValue(celldata.getStringCellValue());
				System.out.println(newCell.toString());
				maxcell++;
			}
			System.out.print(" ");
			sheetManual.getRow(i++);  
		}

		FileOutputStream out = new FileOutputStream(fileName);
		addCatalog.write(out);
		out.close();
		addCatalog.close();
		fis.close();
		inputStream.close();
	}
	}


