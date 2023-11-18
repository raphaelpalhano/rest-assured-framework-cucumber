package br.com.sulamerica.contasmedicas.steps;


import br.com.sulamerica.contasmedicas.core.RequestManager;
import br.com.sulamerica.contasmedicas.model.Request;
import br.com.sulamerica.contasmedicas.model.ResponseAPI;
import br.com.sulamerica.contasmedicas.util.JsonUtil;
import br.com.sulamerica.contasmedicas.util.XmlContasMedicas;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ActionProtocolo {


	@Dado("que o client side gera o protocolo {string}")
	public void gerandoProtocolo(String condicao) throws Exception {
		//Arrange
		JsonUtil jsonUtil = new JsonUtil("create-protocol");
		String requestBody = jsonUtil.getKeyObject(condicao).toString();

		//Action
		Response response = RequestManager.post(requestBody);
		ResponseAPI.setResponse(response);
		if(response.statusCode() == 201) {
			Request.setPathParam(response.jsonPath().get("numero_chave_lote").toString());
		}


	}


	@Quando("que o client side atualiza o protocolo {string}")
	public void atualizandoProtocolo(String condicao) throws Exception {
		//Arrange
		JsonUtil jsonUtil = new JsonUtil("update-protocol");
		JSONObject requestBody = jsonUtil.getJsonWitKey(condicao);

		//Action
		Response response = RequestManager.put(Request.getPathParam(), requestBody.toString());
		ResponseAPI.setResponse(response);

	}



}
