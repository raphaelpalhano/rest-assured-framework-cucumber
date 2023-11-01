package br.com.sulamerica.contasmedicas.steps;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

import br.com.sulamerica.contasmedicas.core.RequestManager;
import br.com.sulamerica.contasmedicas.model.Request;
import br.com.sulamerica.contasmedicas.model.ResponseAPI;
import br.com.sulamerica.contasmedicas.util.XmlContasMedicas;
import io.cucumber.java.pt.Quando;


public class UploadDeGuias {
	
	@Quando("o client side faz upload do arquivo tipo {string} com os dados")
	public void client_arquivo_xml(String nomeFile , String xmlData) throws Exception {
		Map<String, String> dataXml = new HashMap<String, String>();
		String[] lines;
		
		if(xmlData.length() > 0) {
			lines = xmlData.split("\n");
			for (String line: lines) {
				String[] parts = line.split("=");
				
				if(parts.length == 2) {
					dataXml.put(parts[0], parts[1]);
				}
			}
			
		}
		
		XmlContasMedicas.modificadorDeXmlGuias(dataXml, nomeFile);
		ResponseAPI.setStatuscode(RequestManager.putFileClient(Request.getUrl(), nomeFile, "zipado"));

	}
	
	@Quando("o client faz upload do arquivo de um arquivo {string} do tipo {string}")
	public void client_arquivo_xml_sem_manipular(String condicao, String fileName) throws Exception {
		
		if(condicao.equals("nao-zipado")) {
			ResponseAPI.setStatuscode(RequestManager.putFileClient(Request.getUrl(), fileName, "guias"));

		}
		
	}

}
