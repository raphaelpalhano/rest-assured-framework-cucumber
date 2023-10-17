package br.com.organization.project.runner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;


@RunWith(Cucumber.class)
@CucumberOptions(glue = { "br.com.organization.project.steps", "br.com.organization.project.core" }, monochrome = true, plugin = { "pretty",
		"html:target/report/surefire-reports/html/cucumber.html",
		"json:target/report/surefire-reports/cucumber/cucumber.json"}, 
		 tags = "", features = {"classpath:features/"})
public class RunTestsIT {

}
