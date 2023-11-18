package br.com.sulamerica.contasmedicas.steps;

import static org.junit.Assert.assertEquals;

import br.com.sulamerica.contasmedicas.core.RequestManager;
import br.com.sulamerica.contasmedicas.model.Request;
import io.cucumber.java.pt.Entao;
import io.restassured.response.Response;


public class AssertionsProtocolo {
	

	
	@Entao("retonar o codigo protocolo registrado")
	public void validarStatusProtocolo() throws Exception {
		Response res = RequestManager.getWithPathParam(String.format("%s/guias/arquivos/simplificado/", Request.getParam().get("codigo-prestador")), Request.getParam().get("codigo-protocolo"));
		String codigoProtocolo = res.getBody().jsonPath().get("codigo-protocolo").toString();
		assertEquals(Request.getParam().get("codigo-protocolo"), codigoProtocolo);

	}

	@Entao("retornar o codigo status do protocolo {string}")
	public void validarOcodigoProtocolo(String codigoStatusExpected) throws Exception {
		Thread.sleep(2000);
		Response res = RequestManager.getWithPathParam(String.format("%s/guias/arquivos/simplificado/", Request.getParam().get("codigo-prestador")), Request.getParam().get("codigo-protocolo")); 
		String codigoStatus = res.getBody().jsonPath().get("codigo-status").toString();
		assertEquals(codigoStatusExpected, codigoStatus);
	}

}
