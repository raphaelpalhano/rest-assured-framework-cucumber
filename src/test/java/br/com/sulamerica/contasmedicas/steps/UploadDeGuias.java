package br.com.sulamerica.contasmedicas.steps;



import br.com.sulamerica.contasmedicas.model.ResponseAPI;
import br.com.sulamerica.contasmedicas.util.JsonUtil;
import io.cucumber.java.pt.Quando;


public class UploadDeGuias {
	
	@Quando("^o client faz upload do arquivo \"(.*?)\"$")
	public void alterando_informacao_do_pet(String condicao, String endpoint) throws Exception {
		JsonUtil manipulator = new JsonUtil();
		String urlSigned = ResponseAPI.getResponse().getBody().jsonPath().get("_result.url");
		if(condicao.equals("valido")) {
			
			

		}
		if(condicao.equals("prestador-invalido")) {
			
			
		}
		
		//response = RequestManager.postWithParams(endpoint, params);
		
		//assertEquals(200, response.statusCode());
		//Response.setResponse(response);


	}

}
