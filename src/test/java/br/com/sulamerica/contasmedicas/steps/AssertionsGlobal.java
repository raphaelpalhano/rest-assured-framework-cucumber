package br.com.sulamerica.contasmedicas.steps;

import br.com.sulamerica.contasmedicas.model.ResponseAPI;
import br.com.sulamerica.contasmedicas.util.FileManager;
import br.com.sulamerica.contasmedicas.util.JsonSchemaGenerator;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.restassured.module.jsv.JsonSchemaValidator;

import java.io.File;

import static br.com.sulamerica.contasmedicas.constants.PathConstants.SCHEMA_PATH;
import static org.junit.Assert.assertEquals;


public class AssertionsGlobal {
	
	@Entao("deve retornar o statuscode {int}")
	public void validaStatusCode(int expectedStatusCode) {
		// ResponseAPI.getResponse().then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(JsonSchemaGenerator.outputAsString(response)));
		assertEquals(expectedStatusCode, ResponseAPI.getResponse().statusCode());
	}


	@E("respeitar o schema {string}")
	public void validaSchema(String nomeSchema) throws Exception {
		String[] fileAndFolder = nomeSchema.split(",");
		JsonSchemaGenerator.generateSchema(fileAndFolder[0], fileAndFolder[1], ResponseAPI.getResponse().getBody().asString());
		File schema = FileManager.getRecursiveFiles(SCHEMA_PATH, fileAndFolder[1]);
		ResponseAPI.getResponse().then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(schema));

	}
	

}
