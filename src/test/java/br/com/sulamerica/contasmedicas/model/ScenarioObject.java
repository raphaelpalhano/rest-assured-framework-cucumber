package br.com.sulamerica.contasmedicas.model;

import java.util.LinkedHashMap;

public class ScenarioObject {
	private static String serviceName;
	private static String scenarioName;

	private static String envName;

	public static String getServiceName() {
		return serviceName;
	}

	public static void setServiceName(String serviceName) {
		ScenarioObject.serviceName = serviceName;
	}

	public static String getScenarioName() {
		return scenarioName;
	}

	public static void setScenarioName(String scenarioName) {
		ScenarioObject.scenarioName = scenarioName;
	}


	public static String getEnvName() {
		return envName;
	}

	public static void setEnvName(String envName) {
		ScenarioObject.envName = envName;
	}
}
