package br.com.sulamerica.contasmedicas.core;

import java.util.LinkedHashMap;

import br.com.sulamerica.contasmedicas.model.EnvObject;

public class EnvManager {
	private static final String baseUrl = "Base_Url";
	private static final String pathUrls = "Path_Urls";
	private static final String authenticateUrl = "Authenticate_Url";
	private static final String contentType = "Content_Type";
	private static final String headers = "Headers";
	private static final String authentication = "Authentication";

	public static void loadEnvs() throws Exception {

		LinkedHashMap<?, ?> envMap = ReadEnvs.getEnv();
		LinkedHashMap<?, ?> serviceMap = ReadEnvs.getServices(envMap);

		EnvObject.setBase_url(getEnv(baseUrl, serviceMap).toString());
		EnvObject.setPath_urls(getEnv(pathUrls, serviceMap) == null ? new LinkedHashMap<String, String>() : LinkedHashMap.class.cast(getEnv(pathUrls, serviceMap)));
		EnvObject.setAuthenticate_url(
				getEnv(authenticateUrl, serviceMap) == null ? "" : getEnv(authenticateUrl, serviceMap).toString());
		EnvObject.setContent_type(getEnv(contentType, serviceMap).toString());
		EnvObject.setHeaders(getEnv(headers, serviceMap) == null ? new LinkedHashMap<String, String>() : LinkedHashMap.class.cast(getEnv(headers, serviceMap)));
		EnvObject.setAuthetication(LinkedHashMap.class.cast(getEnv(authentication, serviceMap)));
	}

	private static Object getEnv(String env, LinkedHashMap<?, ?> envMap) throws Exception {
		return envMap.get(env);
	}
}
