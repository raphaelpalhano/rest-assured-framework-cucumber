package br.com.sulamerica.contasmedicas.core;

import java.util.LinkedHashMap;

import br.com.sulamerica.contasmedicas.model.ScenarioObject;
import br.com.sulamerica.contasmedicas.util.YamlManager;

public class ReadEnvs {
	public static LinkedHashMap<?, ?> getEnvs() throws Exception {
		return LinkedHashMap.class.cast(LinkedHashMap.class.cast(YamlManager.readYamlFromResources("env.yaml").get(0))
				.get(ScenarioObject.getEnv_tag()));
	}
}
