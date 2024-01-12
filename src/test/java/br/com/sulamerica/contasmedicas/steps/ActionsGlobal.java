package br.com.sulamerica.contasmedicas.steps;

import br.com.sulamerica.contasmedicas.core.Hooks;
import br.com.sulamerica.contasmedicas.model.ScenarioObject;
import io.cucumber.java.pt.Dado;

public class ActionsGlobal {


    @Dado("que configuro o microsservico {string}")
    public void configurandoMicrosservico(String service) throws Exception {
        ScenarioObject.setServiceName(service);
        Hooks.setup();
    }
}
