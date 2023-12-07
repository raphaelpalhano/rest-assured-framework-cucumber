package br.com.sulamerica.contasmedicas.util;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import br.com.sulamerica.contasmedicas.constants.PathConstants;

public class SimpleJsonManager {
	

	private static String JsonBody;
	
	private static JSONParser leitor;
	
	private static JSONArray jsonArray;
	
	public static String getJsonBody() {
		return JsonBody;
	}

	public static void setJsonBody(String jsonBody) {
		JsonBody = jsonBody;
	}

	public static JSONArray getJson() {
		return jsonArray;
	}

	public static void setJsonArray(JSONArray json) {
		jsonArray = json;
	}
	
	
	
	public static JSONArray JsonArray(String jsonFile) throws Exception {
		leitor = new JSONParser();
		jsonArray = new JSONArray();
		File payload = FileManager.getRecursiveFiles(PathConstants.FIXTURES_PATH, jsonFile);
		jsonArray  = (JSONArray) leitor.parse(new FileReader(payload));
		return jsonArray;
	}
	

	public static JSONObject getJsonObject(File jsonFile) throws Exception {
		String jsonStr = new String(Files.readAllBytes(jsonFile.toPath()));
		JSONParser jsonParser = new JSONParser();
		return (JSONObject) jsonParser.parse(jsonStr);
	}

	public static String getElementJson(int numElement) throws Exception {
		String valorElement = jsonArray.get(numElement).toString();
		return valorElement;
	}
	
	
	public static String getJsonAsString(File jsonFile) throws Exception {
		String jsonStr = new String(Files.readAllBytes(jsonFile.toPath()));
		JSONParser jsonParser = new JSONParser();
		return jsonParser.parse(jsonStr).toString();
	}
}
