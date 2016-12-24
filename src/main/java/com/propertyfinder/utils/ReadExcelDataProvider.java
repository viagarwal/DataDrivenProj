package com.propertyfinder.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.testng.annotations.DataProvider;

public class ReadExcelDataProvider {
	
	/**
	 * @author nim
	 * 
	 * @param File Name
	 * @param Sheet Name
	 * @return
	 */
	@DataProvider(name="getExcelData")
	public static Object [][] getExcelData(Method methodName) {
		String[][] arrayExcelData = null;
		try {
			String fileName = methodName.getDeclaringClass().getCanonicalName().replaceAll(methodName.getDeclaringClass().getPackage().getName()+".", "");
			Workbook wb = Workbook.getWorkbook(ReadExcelDataProvider.class.getClassLoader().getResourceAsStream(fileName+".xls"));
			Sheet sh = wb.getSheet(methodName.getName());
			int totalNoOfCols = sh.getColumns();
			int totalNoOfRows = sh.getRows();
			arrayExcelData = new String[totalNoOfRows-1][totalNoOfCols];		
			for (int i= 1 ; i < totalNoOfRows; i++) {
				for (int j=0; j < totalNoOfCols; j++) {
					arrayExcelData[i-1][j] = sh.getCell(j, i).getContents();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		}
		return arrayExcelData;
	}
}