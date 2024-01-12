package br.com.sulamerica.contasmedicas.services;

import br.com.sulamerica.contasmedicas.model.EnvObject;
import br.com.sulamerica.contasmedicas.model.Request;
import br.com.sulamerica.contasmedicas.model.ResponseAPI;
import br.com.sulamerica.contasmedicas.util.JsonUtil;
import br.com.sulamerica.contasmedicas.util.StringManager;
import br.com.sulamerica.contasmedicas.util.XmlContasMedicas;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.response.Response;
import org.apache.http.HttpResponse;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class MsArquivoIntegracao {

    public static HttpResponse updloadDeGuiasManipulado(String fileName , String xmlData) throws Exception {
        HttpResponse response = null;

        Map<String, String> dataXml = new HashMap<String, String>();
        String[] lines;

        if(!xmlData.isEmpty()) {
            lines = xmlData.split("\n");
            for (String line: lines) {
                String[] parts = line.split("=");

                if(parts.length == 2) {
                    dataXml.put(parts[0], parts[1]);
                }
            }

        }

        XmlContasMedicas.modificadorDeXmlGuias(dataXml, fileName);
        response = RestServices.putFileClient(Request.getUrl(), "xml/zipado", fileName);
        return response;
    }

    public static HttpResponse updloadDeGuias(String condicao, String fileName) throws Exception {
        HttpResponse response = null;

        if(condicao.equals("nao-zipado")) {
            response = RestServices.putFileClient(Request.getUrl(), "xml/guias", fileName);
        }

        return response;
    }

    public static Response getProtocoloStatus() {
        Response response = null;
        String status = "AGUARDANDO_ENVIO";
        int count = 0;
        while(status.equals("AGUARDANDO_ENVIO") || status.equals("EM_PROCESSAMENTO")) {
            response = RestServices.getWithPathParam(String.format("/v2/prestadores/%s/guias/arquivos/simplificado/",
                    Request.getParam().get("codigo-prestador")), Request.getParam().get("codigo-protocolo"));
            status = response.getBody().jsonPath().get("codigo-status").toString();



        }

        return response;
    }

    public static Response getProtocolo() {
        return RestServices.getWithPathParam(String.format("v2/prestadores/%s/guias/arquivos/simplificado/", Request.getParam().get("codigo-prestador")), Request.getParam().get("codigo-protocolo"));
    }


    public static Response generateUrlSigned(Map<String, String> params) throws Exception {
        Response response = RestServices.postWithQueryParams(EnvObject.getPathUrls().get("guias_arquivos"), params);
        ResponseAPI.setResponse(response);

       return response;


    }
}
