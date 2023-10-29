package br.com.sulamerica.contasmedicas.model;
import io.restassured.response.Response;

public class ResponseAPI {
	private static Response response;

	public static Response getResponse() {
		return response;
	}

	public static void setResponse(Response response) {
		ResponseAPI.response = response;
	}
}