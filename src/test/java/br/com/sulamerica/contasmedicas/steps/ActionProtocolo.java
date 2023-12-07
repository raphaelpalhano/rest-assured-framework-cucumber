package br.com.sulamerica.contasmedicas.steps;


import br.com.sulamerica.contasmedicas.core.RequestManager;
import br.com.sulamerica.contasmedicas.model.Request;
import br.com.sulamerica.contasmedicas.model.ResponseAPI;
import br.com.sulamerica.contasmedicas.util.JsonUtil;
import br.com.sulamerica.contasmedicas.util.XmlContasMedicas;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ActionProtocolo {


	@Dado("que o client side gera o protocolo {string}")
	public void gerandoProtocolo(String condicao) throws Exception {
		//Arrange
		JsonUtil jsonUtil = new JsonUtil("create-protocol");
		HashMap<String, String> params = new HashMap<String, String>();
		String requestBody = jsonUtil.getKeyObject(condicao).toString();

		//Action
		Response response = RequestManager.post(Request.getPath(), requestBody);
		ResponseAPI.setResponse(response);
		if(response.statusCode() == 201) {
			params.put("numero_chave_lote", response.jsonPath().get("numero_chave_lote").toString());
			params.put("codigo-protocolo", response.jsonPath().get("codigo").toString());
			params.put("codigo-prestador", response.jsonPath().get("prestador.codigo").toString());

			Request.setParam(params);
		}


	}


	@Quando("que o client side atualiza o protocolo {string}")
	public void atualizandoProtocolo(String condicao) throws Exception {
		JSONObject requestBody;
		Response response;
		String chaveForte = Request.getParam().get("numero_chave_lote");

		JsonUtil jsonUtil = new JsonUtil("update-protocol");
		if(condicao.equals("chave-forte-invalida")) {
			requestBody = jsonUtil.getJsonWitKey("valido");
			response = RequestManager.put("/lote/protocolos/","2131234234211", requestBody.toString());
		} else {
			requestBody = jsonUtil.getJsonWitKey(condicao);
			response = RequestManager.put("/lote/protocolos/", chaveForte, requestBody.toString());

		}

		ResponseAPI.setResponse(response);


	}



}
