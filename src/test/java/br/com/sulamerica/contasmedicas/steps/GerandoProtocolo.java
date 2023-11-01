package br.com.sulamerica.contasmedicas.steps;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import br.com.sulamerica.contasmedicas.core.RequestManager;
import br.com.sulamerica.contasmedicas.model.Request;
import io.cucumber.java.pt.Dado;
import io.restassured.response.Response;

public class GerandoProtocolo {
	
	@Dado("^que o client side gera o protocolo \"(.*?)\" pelo endpoint \"(.*?)\"$")
	public void gerando_protocolo_guias(String condicao, String endpoint) throws Exception {
		Response response;
		Map<String, String> params = new HashMap<String, String>();

		if(condicao.equals("valido")) {
			params.put("codigo-prestador", "100000017475");
			params.put("quantidade-arquivos", "1");
			params.put("nomes-arquivos", UUID.randomUUID().toString() + ".zip");
			
			

		}
		if(condicao.equals("codigo-prestador-invalido")) {
			params.put("codigo-prestador", "100000017474");
			params.put("quantidade-arquivos", "1");
			params.put("nomes-arquivos", UUID.randomUUID().toString() + ".zip");
			
		}
		
		response = RequestManager.postWithQueryParams(endpoint, params);
		String signinUrl = response.getBody().jsonPath().getList("_result.url").get(0).toString();
		String protocoloCode = response.getBody().jsonPath().getList("_result.codigo-protocolo").get(0).toString();
		assertEquals(200, response.statusCode());
		Request.setUrl(signinUrl);
		Request.setParam(params);
		Request.setParam("codigo-protocolo", protocoloCode);


	}

}
