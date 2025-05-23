package com.learn;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.amproducts.baseClass.Hooks;
import io.cucumber.java.en.Given;

public class Testing {
	public Hooks base;

	public Testing(Hooks base) {
		this.base = base;
	}
	
	@Given("Launch the browser")
	public void launch_the_browser() throws Exception {
	    base.launchBrowser();
	}
	
	@Given("Login as a faculty")
	public void login_as_a_faculty() throws Exception {
	    WebElement email = base.getDriver().findElement(By.xpath("//input[@placeholder='Enter Email Id']"));
	    WebElement password = base.getDriver().findElement(By.xpath("//input[@placeholder='Enter Password']"));
	    email.sendKeys(base.excelData().getCachedData(base.getConfigData("Environment"), "Faculty Email"));
	    password.sendKeys(base.excelData().getCachedData(base.getConfigData("Environment"), "Faculty Password"));
	    
	}
}
