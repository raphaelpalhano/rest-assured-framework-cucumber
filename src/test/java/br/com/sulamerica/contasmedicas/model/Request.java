package br.com.sulamerica.contasmedicas.model;

public class Request {
	private static String path;
	private static String url;


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
}