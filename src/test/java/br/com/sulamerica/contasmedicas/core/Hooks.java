package br.com.sulamerica.contasmedicas.core;

import static br.com.sulamerica.contasmedicas.constants.TimeOutConstants.MAX_TIMEOUT;

import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.hamcrest.Matchers;

import br.com.sulamerica.contasmedicas.constants.AuthenticationType;
import br.com.sulamerica.contasmedicas.model.EnvObject;
import br.com.sulamerica.contasmedicas.model.Request;
import br.com.sulamerica.contasmedicas.model.ScenarioObject;
import br.com.sulamerica.contasmedicas.util.StringManager;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;

public class Hooks {

	private static String accessTokenField = "access_token";
	RequestSpecBuilder reqBuild = new RequestSpecBuilder();
	ResponseSpecBuilder resBuild = new ResponseSpecBuilder();

	@Before
	public void before(Scenario scenario) throws Exception {
		System.out.println("====> Scenario: " + scenario.getName());
		ScenarioManager.loadSenarioName(scenario);
		ScenarioManager.loadEnvTags();
		setup();
	}

	private void setup() throws Exception {
		EnvManager.loadEnvs();
		createRequestSpecification();
		createResponseSpecifications();
		loadToken();
		setRestAssuredSpecifications();
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
	}

	public void createRequestSpecification() {
		String pathUrl = LinkedHashMap.class.cast(EnvObject.getPath_url().get(ScenarioObject.getPath_url())).get("path_url").toString();
//		String queryStrings = ScenarioObject.getQueryStringParams();
		Request.setPath(pathUrl);
		reqBuild.setContentType(EnvObject.getContent_type());
		reqBuild.setBaseUri(EnvObject.getBase_url());
	}

	public void createResponseSpecifications() {
		resBuild.expectResponseTime(Matchers.lessThan(MAX_TIMEOUT));
	}

	public void setRestAssuredSpecifications() {
		if (EnvObject.getHeaders().containsKey(RequestManager.TOKEN_FIELD) || EnvObject.getHeaders().size() > 0) {
			reqBuild.addHeaders(EnvObject.getHeaders());
		}
		RestAssured.requestSpecification = reqBuild.build();
		RestAssured.responseSpecification = resBuild.build();
	}

	public void loadToken() throws Exception {
		if (!EnvObject.getAuthenticate_url().equals("") && !EnvObject.getAuthenticate_url().isEmpty()) {
			String token = new String();
			String authenticationType = EnvObject.getAuthetication().get("Authentication-Type").toString();
			switch (authenticationType) {
			case AuthenticationType.BASIC_AUTH:
				String basicCredentials = new String(Base64.getEncoder().encode("91b84af2-fb46-36ee-874c-897297a15706:982492a5-b659-306f-b2c4-288d11574c9a".getBytes("UTF-8")));
				EnvObject.addHeaders("Authorization", "Basic " + basicCredentials);
				setRestAssuredSpecifications();
				token = RequestManager.getToken();
				EnvObject.removeHeaders("Authorization");
				EnvObject.addHeaders("ACCESS_TOKEN", token);
				break;
			case AuthenticationType.BEARER_TOKEN:
				EnvObject.addHeaders("Authorization", "Bearer " + token);
				token =  RequestManager.getToken();
				break;
			case AuthenticationType.APPLICATION_X_ENCODED:
			token =  RequestManager.generateToken();
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
