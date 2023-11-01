package br.com.sulamerica.contasmedicas.model;

import java.util.Map;

public class Request {
	private static String path;
	private static String url;
	private static Map<String, String> param;


	public static String getPath() {
		return path;
	}

	public static void setPath(String pathUrl) {
		path = pathUrl;
	}

	public static String getUrl() {
		return url;
	}

	public static void setUrl(String url) {
		Request.url = url;
	}

	public static Map<String, String> getParam() {
		return param;
	}

	public static void setParam(Map<String, String> params) {
		Request.param = params;
	}
	public static void setParam(String key, String value) {
		param.put(key, value);
	}
}