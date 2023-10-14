package br.com.organization.project.steps;

import br.com.organization.project.core.RequestManager;
import br.com.organization.project.model.Response;
import io.cucumber.java.pt.Dado;

public class CreatePetStep {
	@Dado("^que realizo a inserção do pet com o payload \"(.*?)\"$")
	public void realizo_a_insercao_do_pet(String pet) throws Exception {
		Response.setResponse(RequestManager.post(pet));
	}
	
	
}
