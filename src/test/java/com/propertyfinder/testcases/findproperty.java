package com.propertyfinder.testcases;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.propertyfinder.pages.HomePage;
import com.propertyfinder.utils.Driver;
import com.propertyfinder.utils.ReadExcelDataProvider;

public class findproperty extends Driver {
		
	private HomePage homePage = null;
	
	@BeforeClass
	public void setUp() {
		homePage = new HomePage();		
	}
	
	@Test(dataProvider="getExcelData", dataProviderClass = ReadExcelDataProvider.class, description ="Find property in and validation")
	public void propertyfinder(String category, String noOfBedroom, String searchInput , String propertyType){
		System.out.println("category=========="+category);
		System.out.println("noOfBedroom======"+noOfBedroom);
		System.out.println("searchInput======"+searchInput);
		homePage.doCategorySearch(category, noOfBedroom, searchInput,propertyType);
	}
}
