package br.com.sulamerica.contasmedicas.steps;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import br.com.sulamerica.contasmedicas.core.RequestManager;
import br.com.sulamerica.contasmedicas.model.Request;
import br.com.sulamerica.contasmedicas.model.ResponseAPI;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.restassured.response.Response;


public class AssertMsIntegracao {
	
	@Entao("deve retornar o statuscode {int}")
	public void vaidateStatusCode(int expectedStatusCode) {
		assertEquals(expectedStatusCode, ResponseAPI.getStatuscode());
		// ResponseAPI.getResponse().then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(JsonSchemaGenerator.outputAsString(response)));

	}

	
	@E("retonar o codigo protocolo registrado")
	public void validarStatusProtocolo() throws Exception {
		Response res = RequestManager.getWithPathParam(String.format("%s/guias/arquivos/simplificado/", Request.getParam().get("codigo-prestador")), Request.getParam().get("codigo-protocolo")); 
		String codigoProtocolo = res.getBody().jsonPath().get("codigo-protocolo").toString();
		assertEquals(Request.getParam().get("codigo-protocolo"), codigoProtocolo);
		//JsonSchemaGenerator.generateSchema("guias/protocolo.json", res.getBody().asString());
		//File schema = FileManager.getRecursiveFiles(SCHEMA_PATH, "protocolo");
		//res.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(schema));

	}

	@E("retornar o codigo status do protocolo {string}")
	public void validarOcodigoProtocolo(String codigoStatusExpected) throws Exception {
		Thread.sleep(1500);
		Response res = RequestManager.getWithPathParam(String.format("%s/guias/arquivos/simplificado/", Request.getParam().get("codigo-prestador")), Request.getParam().get("codigo-protocolo")); 
		String codigoStatus = res.getBody().jsonPath().get("codigo-status").toString();
		assertEquals(codigoStatusExpected, codigoStatus);
	}

}
