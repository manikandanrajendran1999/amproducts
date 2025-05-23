package cucumber.options;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@RunWith(Cucumber.class)

@CucumberOptions(features = {"src/test/resources/Features" }, 
glue = "com.learn", 
tags = "@tag1", 
dryRun = false, 
monochrome = true)

public class TestRunner extends AbstractTestNGCucumberTests {

}
