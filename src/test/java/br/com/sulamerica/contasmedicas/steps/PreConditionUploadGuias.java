package br.com.sulamerica.contasmedicas.steps;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import br.com.sulamerica.contasmedicas.core.RequestManager;
import br.com.sulamerica.contasmedicas.model.Request;
import br.com.sulamerica.contasmedicas.model.ResponseAPI;
import br.com.sulamerica.contasmedicas.util.JsonUtil;
import br.com.sulamerica.contasmedicas.util.StringManager;
import io.cucumber.java.pt.Dado;
import io.restassured.response.Response;

public class PreConditionUploadGuias {
	
	@Dado("^que o client side gera o protocolo \"(.*?)\" pelo endpoint \"(.*?)\"$")
	public void gerandoProtocoloGuias(String condicao, String endpoint) throws Exception {
		Response response;
		JsonUtil jsonUtil = new JsonUtil("protocolo-integracao");
		String json = jsonUtil.getJsonWitKey(condicao).toString();
		Map<String, String> params = StringManager.conversorJsonToMap(json);
		
		response = RequestManager.postWithQueryParams(endpoint, params);
		ResponseAPI.setResponse(response);

		if(response.statusCode() == 200) {
			String signinUrl = response.getBody().jsonPath().getList("_result.url").get(0).toString();
			String protocoloCode = response.getBody().jsonPath().getList("_result.codigo-protocolo").get(0).toString();
			Request.setUrl(signinUrl);
			Request.setParam(params);
			Request.setParam("codigo-protocolo", protocoloCode);
		}



	}




}
