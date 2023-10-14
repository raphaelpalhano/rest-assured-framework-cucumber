package br.com.organization.project.core;

import java.util.LinkedHashMap;

import br.com.organization.project.model.ScenarioObject;
import br.com.organization.project.util.YamlManager;

public class ReadEnvs {
	public static LinkedHashMap<?, ?> getEnvs() throws Exception {
		return LinkedHashMap.class.cast(LinkedHashMap.class.cast(YamlManager.readYamlFromResources("env.yaml").get(0))
				.get(ScenarioObject.getEnv_tag()));
	}
}
