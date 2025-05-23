package com.amproducts.baseClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.amproducts.utils.ExcelReader;

public class Hooks {

	public WebDriver driver;

	public WebDriver getDriver() throws Exception {
		if (getConfigData("browser").equalsIgnoreCase("chrome")) {
			return driver;
		} else {
			return driver;
		}
	}
	
	public  ExcelReader excelData() throws Exception {
		ExcelReader reader = new ExcelReader("/Files/TestData.xlsx", "Data");
		return reader;
	}

	public void launchBrowser() throws Exception {
//		ExcelReader reader = new ExcelReader("/Files/TestData.xlsx", "Data");
		String env = getConfigData("Environment");
		System.out.println("Env : " + env);
		String loginUrl = excelData().getCachedData(getConfigData("Environment"), "Login Url");
		System.out.println("URL : "+loginUrl);
		
		ChromeOptions options = new ChromeOptions();
		options.addArguments(Arrays.asList("disable-infobars", "ignore-certificate-errors", "start-maximized",
				"use-fake-ui-for-media-stream"));

		Map<String, Object> prefs = new HashMap<>();
		prefs.put("profile.default_content_setting_values.notifications", 1);
		prefs.put("profile.default_content_setting_values.media_stream_mic", 1);
		prefs.put("profile.default_content_setting_values.media_stream_camera", 1);
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);

		options.setExperimentalOption("prefs", prefs);

		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.get(loginUrl);
	}

	public String getConfigData(String propName) throws IOException {
		String propValue = "";
		Properties prop = new Properties();
		File f = new File(System.getProperty("user.dir") + "/config.properties");
		FileInputStream fis = new FileInputStream(f);
		prop.load(fis);
		propValue = prop.getProperty(propName);
		return propValue;
	}

}
