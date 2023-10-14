package br.com.organization.project.model;

public class Request {
	private static Object request;

	public static Object getResponse() {
		return request;
	}

	public static void setResponse(Object request) {
		Request.request = request;
	}
}