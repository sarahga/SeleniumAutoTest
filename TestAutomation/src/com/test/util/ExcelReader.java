package com.test.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelReader {
	private String filePath;
	private String sheetName;
	private Workbook workBook;	
	private Sheet sheet;
	private List<String> columnHeaderList;
	private List<List<String>> listData;
	private List<Map<String,String>> mapData;
	private boolean flag;
	
	public ExcelReader(String filePath, String sheetName) {
		this.filePath = filePath;
		this.sheetName = sheetName;
		this.flag = false;
		this.load();
	}	
	
	/**
	 * 加载EXCEL文件内容，产生WorkBook对象，再产生Sheet对象
	 */
	private void load() {
		FileInputStream inStream = null;
		try {
			inStream = new FileInputStream(new File(filePath));
			workBook = WorkbookFactory.create(inStream);
			sheet = workBook.getSheet(sheetName);			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(inStream!=null){
					inStream.close();
				}				
			} catch (IOException e) {				
				e.printStackTrace();
			}
		}
	}
	
	private String getCellValue(Cell cell) {
		String cellValue = "";
		DataFormatter formatter = new DataFormatter();
		if (cell != null) {
			switch (cell.getCellType()) {
				case Cell.CELL_TYPE_NUMERIC:
					if (DateUtil.isCellDateFormatted(cell)) {
						cellValue = formatter.formatCellValue(cell);
					} else {
						double value = cell.getNumericCellValue();
						int intValue = (int) value;
						cellValue = value - intValue == 0 ? String.valueOf(intValue) : String.valueOf(value);
					}
					break;
				case Cell.CELL_TYPE_STRING:
					cellValue = cell.getStringCellValue();
					break;
				case Cell.CELL_TYPE_BOOLEAN:
					cellValue = String.valueOf(cell.getBooleanCellValue());
					break;
				case Cell.CELL_TYPE_FORMULA:
					cellValue = String.valueOf(cell.getCellFormula());
					break;
				case Cell.CELL_TYPE_BLANK:
					cellValue = "";
					break;
				case Cell.CELL_TYPE_ERROR:
					cellValue = "";
					break;
				default:
					cellValue = cell.toString().trim();
					break;
			}
		}
		return cellValue.trim();
	}
	
	public void getSheetData() {
		listData = new ArrayList<List<String>>();
		mapData = new ArrayList<Map<String, String>>();	
		columnHeaderList = new ArrayList<String>();
		int numOfRows = sheet.getLastRowNum() + 1;
		for (int i = 0; i < numOfRows; i++) {
			Row row = sheet.getRow(i);
			Map<String, String> map = new HashMap<String, String>();
			List<String> list = new ArrayList<String>();
			if (row != null) {
				for (int j = 0; j < row.getLastCellNum(); j++) {
					Cell cell = row.getCell(j);
					if (i == 0){
						columnHeaderList.add(getCellValue(cell));
					}
					else{						
						map.put(columnHeaderList.get(j), this.getCellValue(cell));
					}
					list.add(this.getCellValue(cell));
				}
			}
			if (i > 0){
				mapData.add(map);
			}
			listData.add(list);
		}
		flag = true;
	}
	
	public String getCellData(int row, int col){
		if(row<=0 || col<=0){
			return null;
		}
		if(!flag){
			this.getSheetData();
		}		
		if(listData.size()>=row && listData.get(row-1).size()>=col){
			return listData.get(row-1).get(col-1);
		}else{
			return null;
		}
	}

	public String getCellData(int row, String headerName){
		if(row<=0){
			return null;
		}
		if(!flag){
			this.getSheetData();
		}		
		if(mapData.size()>=row && mapData.get(row-1).containsKey(headerName)){
			return mapData.get(row-1).get(headerName);
		}else{
			return null;
		}
	}
	
	public List<Map<String,String>> getMapData(){
		this.getSheetData();
		return mapData;
	}

	public static void main(String[] args) {
		ExcelReader eh = new ExcelReader("test-data/day12.xls","Sheet1");
		System.out.println(eh.getCellData(2, 1));
		System.out.println(eh.getCellData(1, "testInput"));
	}
}
