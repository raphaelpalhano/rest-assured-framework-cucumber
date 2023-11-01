package br.com.sulamerica.contasmedicas.model;
import io.restassured.response.Response;

public class ResponseAPI {
	private static Response response;
	private static Integer statuscode;

	public static int getStatuscode() {
		return statuscode;
	}

	public static void setStatuscode(Integer statuscode) {
		ResponseAPI.statuscode = statuscode;
	}

	public static Response getResponse() {
		return response;
	}

	public static void setResponse(Response response) {
		ResponseAPI.response = response;
	}
	
}