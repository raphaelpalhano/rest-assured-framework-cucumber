package br.com.sulamerica.contasmedicas.core;

import java.util.LinkedHashMap;

import br.com.sulamerica.contasmedicas.model.ScenarioObject;
import br.com.sulamerica.contasmedicas.util.YamlManager;

public class ReadEnvs {
	public static LinkedHashMap<?, ?> getServices(LinkedHashMap<?, ?> services) {
		return (LinkedHashMap<?, ?>) services
				.get(ScenarioObject.getServiceName());
	}

	public static LinkedHashMap<?, ?> getEnv() throws Exception {
		String environment = System.getProperty("ENV") != null ? System.getProperty("ENV") : "homolog";
		return LinkedHashMap.class.cast(LinkedHashMap.class.cast(YamlManager.readYamlFromResources("env.yaml").get(0))
				.get(environment));
	}
}
