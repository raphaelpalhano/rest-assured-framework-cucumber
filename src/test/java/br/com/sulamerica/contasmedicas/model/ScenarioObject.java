package br.com.sulamerica.contasmedicas.model;

import java.util.LinkedHashMap;

public class ScenarioObject {
	private static String serviceName;
	private static String scenario_name;
	private static String path_url;

	private static String envName;

	public static String getServiceName() {
		return serviceName;
	}

	public static void setServiceName(String serviceName) {
		ScenarioObject.serviceName = serviceName;
	}

	public static String getScenario_name() {
		return scenario_name;
	}

	public static void setScenario_name(String scenario_name) {
		ScenarioObject.scenario_name = scenario_name;
	}

	public static String getPath_url() {
		return path_url;
	}

	public static void setPath_url(String path_url) {
		ScenarioObject.path_url = path_url;
	}

	public static LinkedHashMap<String, ?> getQueryStringParams() {
		@SuppressWarnings("unchecked")
		LinkedHashMap<String, String> paramsMap = LinkedHashMap.class.cast(
				LinkedHashMap.class.cast(EnvObject.getPath_url().get(ScenarioObject.getPath_url())).get("params"));
		return paramsMap;
	}

	public static String getEnvName() {
		return envName;
	}

	public static void setEnvName(String envName) {
		ScenarioObject.envName = envName;
	}
}
