package br.com.sulamerica.contasmedicas.core;

import static br.com.sulamerica.contasmedicas.constants.PathConstants.FEATURE_PATH;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import br.com.sulamerica.contasmedicas.model.ScenarioObject;
import br.com.sulamerica.contasmedicas.util.StringManager;
import io.cucumber.java.Scenario;

public class ScenarioManager {
	public static void loadSenarioName(Scenario cucumebrScenario) {
		ScenarioObject.setScenarioName(cucumebrScenario.getName());
	}

	public static void loadEnvTags() throws Exception {
		List<File> features = new LinkedList<File>();
		String tag = null;
		String path_url = null;
		boolean scenarioFinded = false;
		if (new File(FEATURE_PATH).isDirectory()) {
			features = Arrays.asList(new File(FEATURE_PATH).listFiles());
		} else {
			features.add(new File(FEATURE_PATH));
		}
		for (File feature : features) {
			if (scenarioFinded)
				break;
			String featureContent = new String(Files.readAllBytes(feature.toPath()));
			List<String> scenarioNameList = StringManager.getListMatcherByRegex(featureContent,
					"\\@.*\\r?\\n\\s*?.*\\:.*\\r?\\n");
			for (String scenarioName : scenarioNameList) {
				tag = StringManager.substringByRegex(scenarioName.split("\r?\n")[0].replaceAll("@", ""), "[Ss]ervice\\S*")
						.replaceAll("[Ss]ervice.?", "");
						String scenario = scenarioName.split("\r?\n")[1].split("\\s*\\:\\s*")[1];
				if (scenario.equals(ScenarioObject.getScenarioName())) {
					ScenarioObject.setServiceName(tag);
					scenarioFinded = true;
					break;
				} else {
					scenarioFinded = false;
				}
			}
		}
		if (!scenarioFinded)
			throw new Exception("Scenario não Encontrado: " + ScenarioObject.getScenarioName());
	}
}
