package com.propertyfinder.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.DataProvider;

public class ReadExcelDataProvider {
	
	/**
	 * @author nim
	 * 
	 * @param File Name
	 * @param Sheet Name
	 * @return
	 */
	@DataProvider(name = "getExcelData")
	public static Object [][] getExcelData(ITestNGMethod methodName, ITestContext testContext) {
		String[][] arrayExcelData = null;
		try {
			String fileName = methodName.getRealClass().getCanonicalName().replaceAll("\\.", "/");
			String inputDataFile = "testdata/"+fileName+".xls";
			final String dir = System.getProperty("user.dir");
			Path path = Paths.get(dir, "src\\test\\java\\", inputDataFile);
			FileInputStream fs = new FileInputStream(path.toString());
			Workbook wb = Workbook.getWorkbook(fs);
			Sheet sh = wb.getSheet(methodName.getMethodName());
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
