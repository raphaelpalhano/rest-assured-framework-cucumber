package br.com.organization.project.steps;

import br.com.organization.project.core.RequestManager;
import br.com.organization.project.model.Response;
import io.cucumber.java.pt.Dado;

public class AlterantionPetStep {
	
	@Dado("^que realizo a alteração do pet com payload \"(.*?)\"$")
	public void alterando_informacao_do_pet(String jsonName) throws Exception {
		Response.setResponse(RequestManager.put(jsonName));
	}

}
