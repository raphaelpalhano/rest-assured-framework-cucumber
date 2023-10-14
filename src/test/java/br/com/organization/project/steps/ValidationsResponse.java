package br.com.organization.project.steps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.json.JSONException;
import org.json.simple.parser.ParseException;

import br.com.organization.project.core.RequestManager;
import br.com.organization.project.model.Response;
import br.com.organization.project.util.JsonSchemaGenerator;
import br.com.organization.project.util.JsonUtil;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Mas;
import io.restassured.module.jsv.JsonSchemaValidator;

public class ValidationsResponse {
	@Entao("^o status code deve ser \"(.*?)\"$")
	public void vaidateStatusCode(String expectedStatusCode) {
		assertEquals(expectedStatusCode, String.valueOf(Response.getResponse().getStatusCode()));
	}

	@E("^o status do pet deve estar \"(.*?)\"$")
	public void response_status_pet_sold(String statusPet) throws Exception {
		JsonUtil manipulator = new JsonUtil();
		String response = Response.getResponse().getBody().asString();
		assertEquals(statusPet, manipulator.getBodyString(response, "status"));
		//JsonSchemaGenerator.generateSchema("pets/pet_alteracao.json", manipulator.JsonResponse(response));

		Response.getResponse().then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(JsonSchemaGenerator.outputAsString(response)));
	
	}
	
	
	@Mas("^deve retornar uma lista de pets maior que \"(.*?)\"$")
	public void response_lista_pets(String quantidade) {
		JsonUtil manipulator = new JsonUtil();
		assertTrue(Integer.parseInt(quantidade) <= manipulator.getReponseListJSON(Response.getResponse().body().asString()).size());
		
	}
	
	
	@E("^deve retornar um pet com o nome \"(.*?)\"$")
	public void validando_response_nome_do_pet(String nomePet) {
		assertTrue(RequestManager.contains(Response.getResponse().body().asString(), nomePet));

	}
	
}
