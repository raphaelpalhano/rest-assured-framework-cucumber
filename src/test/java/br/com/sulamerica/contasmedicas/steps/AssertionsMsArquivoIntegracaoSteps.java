package br.com.sulamerica.contasmedicas.steps;

import br.com.sulamerica.contasmedicas.model.Request;
import br.com.sulamerica.contasmedicas.services.MsArquivoIntegracao;
import io.cucumber.java.pt.Entao;
import io.restassured.response.Response;

import static org.junit.Assert.assertEquals;

public class AssertionsMsArquivoIntegracaoSteps {

    @Entao("retonar o codigo protocolo registrado")
    public void validarStatusProtocolo() throws Exception {
        Response res = MsArquivoIntegracao.getProtocolo();
        String codigoProtocolo = res.getBody().jsonPath().get("codigo-protocolo").toString();
        assertEquals(Request.getParam().get("codigo-protocolo"), codigoProtocolo);

    }

    @Entao("retornar o codigo status do protocolo {string}")
    public void validarOcodigoProtocolo(String codigoStatusExpected) throws Exception {
        Response res = MsArquivoIntegracao.getProtocoloStatus();
        String codigoStatus = res.getBody().jsonPath().get("codigo-status").toString();
        assertEquals(codigoStatusExpected, codigoStatus);
    }

}
