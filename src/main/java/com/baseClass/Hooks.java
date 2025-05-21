package com.baseClass;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Hooks {
	
	public WebDriver driver;
	
	public WebDriver getDriver() {
		return driver;
	}
	
	public void launchDriver() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments(Arrays.asList("disable-infobars","ignore-certificate-errors", "start-maximized",
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
		driver.get("https://www.google.com/");
	}

}
