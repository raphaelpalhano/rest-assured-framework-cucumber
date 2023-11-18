package br.com.sulamerica.contasmedicas.steps;



import java.util.HashMap;
import java.util.Map;

import br.com.sulamerica.contasmedicas.core.RequestManager;
import br.com.sulamerica.contasmedicas.model.Request;
import br.com.sulamerica.contasmedicas.util.XmlContasMedicas;
import io.cucumber.java.pt.Quando;
import org.apache.http.HttpResponse;

import static org.junit.Assert.assertEquals;


public class ActionUploadGuias {
	
	@Quando("o client side faz upload do arquivo tipo {string} com os dados")
	public void client_arquivo_xml(String nomeFile , String xmlData) throws Exception {
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
		
		XmlContasMedicas.modificadorDeXmlGuias(dataXml, nomeFile);
		response = RequestManager.putFileClient(Request.getUrl(), "xml/zipado", nomeFile);
		assertEquals(200, response.getStatusLine().getStatusCode());

	}
	
	@Quando("o client faz upload do arquivo de um arquivo {string} do tipo {string}")
	public void client_arquivo_xml_sem_manipular(String condicao, String fileName) throws Exception {
		HttpResponse response = null;
		
		if(condicao.equals("nao-zipado")) {
			response = RequestManager.putFileClient(Request.getUrl(), "xml/guias", fileName);
		}

		assertEquals(200, response.getStatusLine().getStatusCode());

	}

}
