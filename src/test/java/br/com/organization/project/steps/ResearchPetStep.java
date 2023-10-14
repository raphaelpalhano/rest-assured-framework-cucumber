package br.com.organization.project.steps;

import br.com.organization.project.core.RequestManager;
import br.com.organization.project.model.Response;
import io.cucumber.java.pt.Dado;

public class ResearchPetStep {
	
	
	@Dado("^a requisicao de consulta por status \"(.*)\"$")
	public void buscando_por_uma_lista_de_pets(String stausValor) {
		Response.setResponse(RequestManager.getForSearch(stausValor));
	}

}
