package br.com.sulamerica.contasmedicas.steps;

import br.com.sulamerica.contasmedicas.model.ResponseAPI;
import br.com.sulamerica.contasmedicas.util.FileManager;
import br.com.sulamerica.contasmedicas.util.JsonSchemaGenerator;
import br.com.sulamerica.contasmedicas.util.JsonUtil;
import io.cucumber.java.pt.E;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;

import java.io.File;

import static br.com.sulamerica.contasmedicas.constants.PathConstants.SCHEMA_PATH;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


public class AssertionsMsEntrada {
	
	@E("deve ter o historico de status ate {string}")
	public void validaStatusCode(String condicao) {
		if(!condicao.isEmpty()) {
			JsonUtil jsonUtil = new JsonUtil(condicao);
			String json = jsonUtil.getArrayObject().toString();
			// ResponseAPI.getResponse().then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(JsonSchemaGenerator.outputAsString(response)));
			String res = ResponseAPI.getResponse().asString();

		}

	}


}
