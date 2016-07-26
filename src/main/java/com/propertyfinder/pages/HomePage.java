package com.propertyfinder.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.propertyfinder.utils.Driver;
import com.propertyfinder.utils.WebDriverWaitUtils;

public class HomePage extends Driver {
	
	@FindBy(css ="button.ms-choice") public static WebElement categoryArrow ;
	@FindBy(css="div#bedroom_group button.ms-choice") public static WebElement bedArrow;
	@FindBy(css="button[type='submit']") public static WebElement submitButton;
	@FindBy(css="input[name='q']") public static WebElement searchInput;
	@FindBy(css="div.tt-suggestion.tt-selectable") public static WebElement input;
	@FindBy(xpath ="//span[contains(text(),'Property type')]/ancestor ::div[@class='ms-parent']") public static WebElement propertyTypeArrow;
	@FindBy(xpath="//div[@class='info-area']//div[@class='property-details']//span[2]") public static List<WebElement> bedroonDetails;
		
	public HomePage() {
		PageFactory.initElements(eventFiringWebDriver, this);
	}
	
	public void doCategorySearch(String category, String noOfBedroom, String searchInput , String propertyType) {
		WebDriverWaitUtils.waitElementIsVisible(driver, categoryArrow);	
		categoryArrow.click();
		WebElement categorySelect  = eventFiringWebDriver.findElement(By.xpath(String.format("//div[contains(@class,'category')]//div[@class='ms-drop ']//li[contains(text(),'%s')]",category)));
		WebDriverWaitUtils.waitElementIsVisible(eventFiringWebDriver, categorySelect);
		categorySelect.click();
		this.searchInput.sendKeys(searchInput);
		input.click();
		bedArrow.click();
		WebElement bedroomSelect =  eventFiringWebDriver.findElement(By.xpath(String.format("//div[@id='bedroom_group']//div[@class='ms-drop ']//li[contains(text(),'%s')]", noOfBedroom)));
		bedroomSelect.click();
		propertyTypeArrow.click();
		WebElement propertySelect = eventFiringWebDriver.findElement(By.xpath(String.format("//div[@class='pure-g']//li[contains(text(),'%s')]", propertyType)));
		propertySelect.click();
		submitButton.click();
		WebDriverWaitUtils.waitElementIsVisible(eventFiringWebDriver, bedroonDetails);
		WebDriverWaitUtils.waitForLoad(eventFiringWebDriver);
		for (WebElement elm : bedroonDetails) {
			WebDriverWaitUtils.waitElementIsVisible(eventFiringWebDriver, elm);		
//			System.out.println("Details1=="+elm.getText());
			if(elm.getText().trim().matches("(\\d+)")){
				System.out.println("Details=="+elm.getText());
				Assert.assertTrue(Integer.parseInt(elm.getText())>=2);
			}
		}				
	}
}
