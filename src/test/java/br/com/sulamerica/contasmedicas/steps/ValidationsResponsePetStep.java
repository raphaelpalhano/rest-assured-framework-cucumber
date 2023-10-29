package br.com.sulamerica.contasmedicas.steps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import br.com.sulamerica.contasmedicas.core.RequestManager;
import br.com.sulamerica.contasmedicas.model.ResponseAPI;
import br.com.sulamerica.contasmedicas.util.JsonSchemaGenerator;
import br.com.sulamerica.contasmedicas.util.JsonUtil;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Mas;
import io.restassured.module.jsv.JsonSchemaValidator;

public class ValidationsResponsePetStep {
	@Entao("^o status code deve ser \"(.*?)\"$")
	public void vaidateStatusCode(String expectedStatusCode) {
		assertEquals(expectedStatusCode, String.valueOf(ResponseAPI.getResponse().getStatusCode()));
	}

	@E("^o status do pet deve estar \"(.*?)\"$")
	public void response_status_pet_sold(String statusPet) throws Exception {
		JsonUtil manipulator = new JsonUtil();
		String response = ResponseAPI.getResponse().getBody().asString();
		assertEquals(statusPet, manipulator.getBodyString(response, "status"));
		//JsonSchemaGenerator.generateSchema("pets/pet_alteracao.json", manipulator.JsonResponse(response));

		ResponseAPI.getResponse().then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(JsonSchemaGenerator.outputAsString(response)));
	
	}
	
	
	@Mas("^deve retornar uma lista de pets maior que \"(.*?)\"$")
	public void response_lista_pets(String quantidade) {
		JsonUtil manipulator = new JsonUtil();
		assertTrue(Integer.parseInt(quantidade) <= manipulator.getReponseListJSON(ResponseAPI.getResponse().body().asString()).size());
		
	}
	
	
	@E("^deve retornar um pet com o nome \"(.*?)\"$")
	public void validando_response_nome_do_pet(String nomePet) {
		assertTrue(RequestManager.contains(ResponseAPI.getResponse().body().asString(), nomePet));

	}
	
}
