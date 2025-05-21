package com.learn;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;



public class ActionClass {
	

	public static void main(String[] args) {
		// No WebDriverManager needed anymore
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.google.com");
        System.out.println("Title: " + driver.getTitle());

        driver.quit();
		
	}

}
