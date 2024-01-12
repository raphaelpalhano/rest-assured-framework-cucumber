package br.com.sulamerica.contasmedicas.services;

import br.com.sulamerica.contasmedicas.model.EnvObject;
import br.com.sulamerica.contasmedicas.model.Request;
import br.com.sulamerica.contasmedicas.model.ResponseAPI;
import br.com.sulamerica.contasmedicas.util.JsonUtil;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MsEntradaService {

    public static Response atualizandoProtocoloStatusMainframe(String condicao) throws Exception {
        String chaveForte = Request.getParam().get("numero_chave_lote");
        JsonUtil jsonUtil = new JsonUtil("payload-mainframe");
        JSONObject requestBody = jsonUtil.getKeyJsonObject(condicao);

        if(!condicao.equals("processado-sem-chave-guia")) {
            requestBody.getJSONArray("guias").getJSONObject(0).put("chaveDaGuia",  chaveForte + "001");

        }
        String path = String.format("/contas-medicas/%s/status", chaveForte);

        return RestServices.patch(path, requestBody.toString());
    }

    public static Response criarProtocolo(String condicao) throws Exception {
        //Arrange
        JsonUtil jsonUtil = new JsonUtil("create-protocol");
        String requestBody = jsonUtil.getKeyObject(condicao).toString();
        Request.setParam(new HashMap<String, String>());
        //Action
        Response response = RestServices.post(EnvObject.getPathUrls().get("protocolo"), requestBody);
        ResponseAPI.setResponse(response);
        if(response.statusCode() == 201) {
            Request.setParam("numero_chave_lote", response.jsonPath().get("numero_chave_lote").toString());
            Request.setParam("codigo-protocolo", response.jsonPath().get("codigo").toString());
            Request.setParam("codigo-prestador", response.jsonPath().get("prestador.codigo").toString());
            Request.setParam("id",  response.jsonPath().get("id").toString());
        }

        return  response;
    }

    public static Response atualizarProtocolo(String condicao) throws Exception {
        String chaveForte = condicao.equals("chave-forte-invalida") ? "21321431312312" : Request.getParam().get("numero_chave_lote");
        JsonUtil jsonUtil = new JsonUtil("update-protocol");
        JSONObject requestBody = condicao.equals("chave-forte-invalida") ? jsonUtil.getJsonWitKey("valido") : jsonUtil.getJsonWitKey(condicao);
        return RestServices.put(EnvObject.getPathUrls().get("protocolos"), chaveForte, requestBody.toString());

    }

    public static Response buscarProtocoloIdDocument(String idDocument) throws Exception {
        Response response = RestServices.getWithPathParam(EnvObject.getPathUrls().get("protocolo"), idDocument);
        ResponseAPI.setResponse(response);
        return response;
    }

    public static Response buscarDetalheECritica(String endpoint, String idDocument) throws Exception {
        Response response = RestServices.getWithPathParam(EnvObject.getPathUrls().get("protocolo"), String.format("%s/%s", idDocument, endpoint));
        ResponseAPI.setResponse(response);
        return response;
    }
    public static Response buscarProtocoloPrestador(String codPrestador, String codProtocolo) throws Exception {
        Response response = RestServices.getWithPathParam(EnvObject.getPathUrls().get("protocolo"), String.format("%s/%s", codPrestador, codProtocolo));
        ResponseAPI.setResponse(response);

        return response;
    }

    public static Response buscarProtocoloGrdPrestador(String codGrd, String codPrestador) throws Exception {
        Response response = RestServices.getWithPathParam(EnvObject.getPathUrls().get("protocolo") + "prestador/", String.format("%s/grd/%s", codPrestador, codGrd));
        ResponseAPI.setResponse(response);

        return response;
    }

    public static Response buscarProtocoloCodigo(String endpoint, String codProtocolo) throws Exception {
        Response response = RestServices.getWithPathParam(EnvObject.getPathUrls().get("protocolo") + endpoint, String.format("%s", codProtocolo));
        ResponseAPI.setResponse(response);

        return response;
    }

    public static Response buscarProtocoloComFiltro(String codPrestador, String filtro) throws Exception {
        Response response = RestServices.getWithQueryParams(EnvObject.getPathUrls().get("protocolo") + "prestador/" + codPrestador, filtro);
        JsonUtil jsonUtil = new JsonUtil();
        JSONObject res = jsonUtil.parseJson(response.getBody().asString());
        ResponseAPI.setResponse(response);


        if(filtro.contains("FATURADO")) {
            String grd = jsonUtil.parseJsonArray(res.get("conteudo").toString()).getJSONObject(0).get("numero-grd").toString();
            Request.setParam("grd", grd);

        }

        return response;
    }
}
