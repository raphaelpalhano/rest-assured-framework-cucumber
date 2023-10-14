package br.com.organization.project.runner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;


@RunWith(Cucumber.class)
@CucumberOptions(glue = { "br.com.organization.project.steps", "br.com.organization.project.core" }, monochrome = true, plugin = { "pretty",
		"html:target/cucumber-html-report",
		"json:target/cucumber-json-report"}, 
		 tags = "", features = "src/test/resources/features/")
public class RunTests {

}
