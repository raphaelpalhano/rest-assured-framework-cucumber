package br.com.sulamerica.contasmedicas.steps;

import br.com.sulamerica.contasmedicas.model.Request;
import br.com.sulamerica.contasmedicas.services.MsArquivoIntegracao;
import br.com.sulamerica.contasmedicas.util.JsonUtil;
import br.com.sulamerica.contasmedicas.util.StringManager;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import org.apache.http.HttpResponse;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ActionsMsArquivoIntegracaoSteps {


    @Dado("^que o client side gera o protocolo \"(.*?)\" pela url assinada")
    public void gerandoProtocoloGuias(String condicao) throws Exception {
        JsonUtil jsonUtil = new JsonUtil("protocolo-integracao");
        String json = jsonUtil.getJsonWitKey(condicao).toString();
        Map<String, String> params = StringManager.conversorJsonToMap(json);

        Response response = MsArquivoIntegracao.generateUrlSigned(params);
        if(response.statusCode() == 200) {
            String signinUrl = response.getBody().jsonPath().getList("_result.url").get(0).toString();
            String protocoloCode = response.getBody().jsonPath().getList("_result.codigo-protocolo").get(0).toString();
            Request.setUrl(signinUrl);
            Request.setParam(params);
            Request.setParam("codigo-protocolo", protocoloCode);
        }


    }



    @Quando("o client side faz upload do arquivo tipo {string} com os dados")
    public void client_arquivo_xml(String nomeFile , String xmlData) throws Exception {
        HttpResponse response = MsArquivoIntegracao.updloadDeGuiasManipulado(nomeFile, xmlData);
        assertEquals(200, response.getStatusLine().getStatusCode());

    }

    @Quando("o client faz upload do arquivo de um arquivo {string} do tipo {string}")
    public void client_arquivo_xml_sem_manipular(String condicao, String fileName) throws Exception {
        HttpResponse response = MsArquivoIntegracao.updloadDeGuias(condicao, fileName);
        assertEquals(200, response.getStatusLine().getStatusCode());

    }


}
