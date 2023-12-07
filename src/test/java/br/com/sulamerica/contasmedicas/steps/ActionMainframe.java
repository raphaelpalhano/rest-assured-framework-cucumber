package br.com.sulamerica.contasmedicas.steps;


import br.com.sulamerica.contasmedicas.core.RequestManager;
import br.com.sulamerica.contasmedicas.model.Request;
import br.com.sulamerica.contasmedicas.model.ResponseAPI;
import br.com.sulamerica.contasmedicas.util.JsonUtil;
import io.cucumber.java.pt.Dado;

import io.restassured.response.Response;
import org.json.JSONObject;

public class ActionMainframe {


	@Dado("que o client side faz request com payload do mainframe {string}")
	public void gerandoPayloadMainframe(String condicao) throws Exception {
		//Arrange
		JsonUtil jsonUtil = new JsonUtil("payload-mainframe");
		JSONObject requestBody = jsonUtil.getKeyJsonObject(condicao);
		requestBody.getJSONArray("guias").getJSONObject(0).put("chaveDaGuia", Request.getParam().get("numero_chave_lote") + "001");
		String path = String.format("/contas-medicas/%s/status", Request.getParam().get("numero_chave_lote"));
		//Action
		Response response = RequestManager.patch(path, requestBody.toString());
		ResponseAPI.setResponse(response);


	}


	@Dado("que o client side faz request com payload com erro do mainframe {string}")
	public void atualizandoProtocolo(String condicao) throws Exception {
		//Arrange
		JsonUtil jsonUtil = new JsonUtil("payload-mainframe");
		String chaveForte = Request.getParam().get("numero_chave_lote");
		JSONObject requestBody = jsonUtil.getKeyJsonObject(condicao);
		if(!condicao.equals("processado-sem-chave-guia")) {
			requestBody.getJSONArray("guias").getJSONObject(0).put("chaveDaGuia",  chaveForte + "001");

		}

		//Action
		Response response = RequestManager.patch(String.format("%s%s/status",Request.getPath(),chaveForte), requestBody.toString());
		ResponseAPI.setResponse(response);


	}



}
