package br.com.sulamerica.contasmedicas.core;

import static br.com.sulamerica.contasmedicas.constants.TimeOutConstants.MAX_TIMEOUT;

import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashMap;

import br.com.sulamerica.contasmedicas.services.RestServices;
import org.hamcrest.Matchers;

import br.com.sulamerica.contasmedicas.constants.AuthenticationType;
import br.com.sulamerica.contasmedicas.model.EnvObject;
import br.com.sulamerica.contasmedicas.model.Request;
import br.com.sulamerica.contasmedicas.model.ScenarioObject;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;

public class Hooks {

	private static final String accessTokenField = "access_token";
	static RequestSpecBuilder reqBuild = new RequestSpecBuilder();
	static ResponseSpecBuilder resBuild = new ResponseSpecBuilder();

	//@Before
	public void before(Scenario scenario) throws Exception {
		System.out.println("====> Scenario: " + scenario.getName());
		//ScenarioManager.loadSenarioName(scenario);
		//ScenarioManager.loadEnvTags();
		setup();
	}

	public static void  setup() throws Exception {
		EnvManager.loadEnvs();
		createRequestSpecification();
		createResponseSpecifications();
		loadToken();
		setRestAssuredSpecifications();
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
	}

	private static void createRequestSpecification() {
		reqBuild.setContentType(EnvObject.getContentType());
		reqBuild.setBaseUri(EnvObject.getBaseUrl());
	}

	private static void createResponseSpecifications() {
		resBuild.expectResponseTime(Matchers.lessThan(MAX_TIMEOUT));
	}

	private static void setRestAssuredSpecifications() {
		if (EnvObject.getHeaders().containsKey(RestServices.TOKEN_FIELD) || !EnvObject.getHeaders().isEmpty()) {
			reqBuild.addHeaders(EnvObject.getHeaders());
		}
		RestAssured.requestSpecification = reqBuild.build();
		RestAssured.responseSpecification = resBuild.build();
	}

	private static void loadToken() throws Exception {
		if (!EnvObject.getAuthenticateUrl().isEmpty()) {
			String token = "";
			String authenticationType = EnvObject.getAuthetication().get("Authentication-Type").toString();
			switch (authenticationType) {
			case AuthenticationType.BASIC_AUTH:
				String basicCredentials = new String(Base64.getEncoder().encode("91b84af2-fb46-36ee-874c-897297a15706:982492a5-b659-306f-b2c4-288d11574c9a".getBytes("UTF-8")));
				EnvObject.addHeaders("Authorization", "Basic " + basicCredentials);
				setRestAssuredSpecifications();
				token = RestServices.getToken();
				EnvObject.removeHeaders("Authorization");
				EnvObject.addHeaders("ACCESS_TOKEN", token);
				break;
			case AuthenticationType.BEARER_TOKEN:
				EnvObject.addHeaders("Authorization", "Bearer " + token);
				token =  RestServices.getToken();
				break;
			case AuthenticationType.APPLICATION_X_ENCODED:
				token =  RestServices.generateToken();
				HashMap<String,String> headerMap = new HashMap<>();
				headerMap.put("Authorization", "Bearer " + token);
				EnvObject.setToken(headerMap);
	//			EnvObject.setHeader(StringManager.conversorStringToMap(EnvObject.getAuthetication().get("Header").toString()));
				break;
			}
			System.setProperty(accessTokenField, token);
		}
	}
}
