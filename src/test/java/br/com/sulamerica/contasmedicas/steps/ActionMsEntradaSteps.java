package br.com.sulamerica.contasmedicas.steps;

import br.com.sulamerica.contasmedicas.model.Request;
import br.com.sulamerica.contasmedicas.model.ResponseAPI;
import br.com.sulamerica.contasmedicas.services.MsEntradaService;
import br.com.sulamerica.contasmedicas.util.JsonUtil;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;


public class ActionMsEntradaSteps {



    @E("que o client side faz request com payload do mainframe {string}")
    public void gerandoPayloadMainframe(String condicao) throws Exception {
        //Arrange
        Response response = MsEntradaService.atualizandoProtocoloStatusMainframe(condicao);
        ResponseAPI.setResponse(response);
    }

    @Dado("que o client side faz request com payload com erro do mainframe {string}")
    public void atualizandoProtocoloMainframe(String condicao) throws Exception {
        Response response = MsEntradaService.atualizandoProtocoloStatusMainframe(condicao);
        ResponseAPI.setResponse(response);
    }


    @Dado("que o client side gera o protocolo {string}")
    public void gerandoProtocolo(String condicao) throws Exception {
        MsEntradaService.criarProtocolo(condicao);
    }


    @Quando("que o client side atualiza o protocolo {string}")
    public void atualizandoProtocolo(String condicao) throws Exception {
        Response response = MsEntradaService.atualizarProtocolo(condicao);
        ResponseAPI.setResponse(response);

    }

    @E("que o client side busca o protocolo pelo idDocument {string}")
    public void buscandoProtocoloId(String idDocument) throws Exception {
        if(idDocument.isEmpty()) {
            idDocument = Request.getParam().get("id");
        }
        MsEntradaService.buscarProtocoloIdDocument(idDocument);
    }

    @Dado("que o client side busca o protocolo pelo protocolo {string} e prestador {string}")
    public void buscandoProtocoloPrestadorEProtocolo(String protocolo, String prestador) throws Exception {
        String protocoloCod = protocolo.equals("valido") ? Request.getParam().get("codigo-protocolo") : "123124214211";
        JsonUtil jsonUtil = new JsonUtil("prestadores");
        String prestadorCod = jsonUtil.getJsonWitKey(prestador).get("codigo-prestador").toString();

        MsEntradaService.buscarProtocoloPrestador(prestadorCod, protocoloCod);
    }
    @Dado("que o client side busca o protocolo usando o {string} com filtro {string}")
    public void buscandoProtocoloPorFiltro(String condicao, String filtro) throws Exception {
        JsonUtil jsonUtil = new JsonUtil("prestadores");
        String prestador = jsonUtil.getJsonWitKey(condicao).get("codigo-prestador").toString();
        Response response = MsEntradaService.buscarProtocoloComFiltro(prestador,filtro);
    }

    @Dado("que o client side faz request pelo endpoint {string} com protocolo {string}")
    public void buscandoHistoricoDeStatusEDownloadXml(String endpoint, String protocolo) throws Exception {
        if(protocolo.isEmpty()) {
            protocolo = Request.getParam().get("codigo-protocolo");
        }
        Response response = MsEntradaService.buscarProtocoloCodigo(endpoint, protocolo);
    }

    @Quando("o client side faz request com grd {string} com prestador {string}")
    public void buscandoProtocoloCodigoGrd(String grd, String prestador) throws Exception {
        if(grd.isEmpty()) {
            grd = Request.getParam().get("grd");
        }
        Response response = MsEntradaService.buscarProtocoloGrdPrestador(grd, prestador);
    }


    @Dado("o client side faz request no endpoint {string} com idDocument {string}")
    public void buscandoDetalhamentoGuias(String endpoint, String idDocument) throws Exception {
        if(idDocument.isEmpty()) {
            idDocument = Request.getParam().get("id");
        }
        Response response = MsEntradaService.buscarDetalheECritica(endpoint,idDocument);
    }




}
