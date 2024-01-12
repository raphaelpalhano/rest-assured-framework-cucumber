package br.com.sulamerica.contasmedicas.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class EnvObject {
	private static String baseUrl;
	private static LinkedHashMap<String, String> pathUrls;
	private static String authenticateUrl;
	private static String content_type;
	private static Map<String, String> headers;
	private static Map<String, String> token;
	private static Map<String, String> header;


	public static String getAuthenticateUrl() {
		return authenticateUrl;
	}

	public static void setAuthenticateUrl(String authenticateUrl) {
		EnvObject.authenticateUrl = authenticateUrl;
	}

	public static LinkedHashMap<?, ?> getAuthetication() {
		return authetication;
	}

	public static void setAuthetication(LinkedHashMap<?, ?> authetication) {
		EnvObject.authetication = authetication;
	}

	private static LinkedHashMap<?, ?> authetication;

	public static String getBaseUrl() {
		return baseUrl;
	}

	public static void setBaseUrl(String base_url) {
		EnvObject.baseUrl = base_url;
	}

	public static LinkedHashMap<String, String> getPathUrls() {
		return pathUrls;
	}

	public static void setPathUrls(LinkedHashMap<String, String> pathUrls) {
		EnvObject.pathUrls = pathUrls;
	}

	public static String getContentType() {
		return content_type;
	}

	public static void setContentType(String content_type) {
		EnvObject.content_type = content_type;
	}

	public static Map<String, String> getHeaders() {
		return headers;
	}

	public static void setHeaders(Map<String, String> headers) {
		EnvObject.headers = headers;
	}


	public static void setToken(Map<String,String> token1){
		token = token1;
	}
	
	public static Map<String, String> getToken(){
		return token;
	}

	
	
	public static void setHeader(Map<String, String> headers) {
		EnvObject.header = headers;
	}
	
	
	public static Map<String, String> getHeader() {
		return header;
	}
	
	

	public static void addHeaders(String key, String value) {
		if (EnvObject.headers == null) {
			EnvObject.headers = new LinkedHashMap<String, String>();
		}
		EnvObject.headers.put(key, value);
	}
	
	public static void removeHeaders(String key) {
		if (EnvObject.headers != null && EnvObject.headers.containsKey(key)) {
			EnvObject.headers.remove(key);
		}
	}
}
